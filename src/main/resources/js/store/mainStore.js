import Vue from 'vue'
import Vuex from 'vuex'

import to from '../functionLib/to.js'
import userApi from "../api/userApi";
import photoApi from "../api/photoApi";
import workApi from "../api/workApi";

Vue.use(Vuex);

const store = new Vuex.Store({
    modules: {},
    state: {
        context: {
            id: null,
            phoneNumber: '',
            name: '',
            user: '',
            admin: false,
            master: false,
            authorized: false
        },
        records: [],
        masters: [],
        recordsForClient: [],
        recordsForMaster: [],
        services: [],
        selectedHour: null,
        selectedDay: null
    },
    getters: {
        records: state => state.records,
        recordsForClient: state => state.recordsForClient,
        recordsForMaster: state => state.recordsForMaster,
        getMasters: state => state.masters,
        getServices: state => state.services
    },
    mutations: {
        updateAuthStatus(state, value) {
            const {authorized, admin, user, name, phoneNumber, master, id} = value;
            state.context.authorized = authorized;
            state.context.admin = admin;
            state.context.user = user;
            state.context.name = name;
            state.context.phoneNumber = phoneNumber;
            state.context.master = master;
            state.context.id = id;
        },
        pullMasters(state, masters) {
            state.masters = masters;
        },
        pullServices(state, services) {
            state.services = services;
        },
        pullWorkWeeks(state, workWeeks) {
            state.records = workWeeks;
        },
        setClient(state, master) {
            const filter = (arr, day) => arr.filter(el => el.day === day.day);
            state.records.filter(record =>
                filter(record.days, state.selectedDay).some(el => el.hours.some(({id}) => id === state.selectedHour.id))
            ).forEach(record => {
                filter(record.days, state.selectedDay)[0].hours.forEach(el => {
                    if (el.id === state.selectedHour.id) {
                        const index = el.masterIds.indexOf(null);
                        el.masterIds.splice(index, 1, master.id)
                    }
                })
            })
        },
        setSelectedHour(state, hour){
            state.selectedHour = hour;
        },
        setSelectedDay(state, day){
            state.selectedDay = day
        },
        pullRecordsForClient(state, records) {
            state.recordsForClient = records;
        },
        pullRecordsForMaster(state, records) {
            state.recordsForMaster = records;
        },
        setStatusRecord(state, {id, status}) {
            state.recordsForMaster.forEach(record => {
                if (record.id === id) {
                    record.statusId = status;
                }
            })
        }
    },
    actions: {
        async regUser({}, user) {
            await to(userApi.createUser(user));
        },
        async login({commit, state}, user) {
            const {err, res} = await to(Vue.http.post('/login', user));
            if (!err) {
                setTimeout(((ok) => state.isLogin = ok)(res.ok), 3000);
                Vue.http.get('/')
            } else {
                const isStatus404 = err.status === 404;
                state.isLogin = isStatus404 ? true : err.ok;
            }
        },
        async getContext({commit}) {
            const {err, res} = await to(Vue.http.get('/getContext'));
            if (!err) {
                commit('updateAuthStatus', await res.json())
            }
        },
        changeName({}, name) {
            userApi.changeName(name);
        },
        changeUsername({}, username) {
            userApi.changeUsername(username);
        },
        changePassword({}, password) {
            userApi.changePassword(password)
        },
        changePhoneNumber({}, phoneNumber) {
            userApi.changePhoneNumber(phoneNumber);
        },
        async getRecords({commit}) {
            const {res} = await to(workApi.getWorkWeeks());

            const fromServer = await res.json();
            const records = [];
            const some = (arr, el) => arr.some(({start}) => start === el.start);

            fromServer.forEach(el => {
                if (some(records, el)) {
                    records.filter(({start}) => start === el.start)
                        .forEach(record => {
                            record.days.forEach(day => {
                                const eDay = el.days.find(eDay => eDay.day === day.day);
                                day.hours.forEach(hour => {
                                    const eHour = eDay.hours.find(eHour => eHour.time === hour.time);
                                    if (!hour.hasOwnProperty('masterIds')) {
                                        hour.masterIds = [];
                                        const {masterId} = hour;
                                        hour.masterIds.push(masterId);
                                    }

                                    const {masterId} = eHour;
                                    hour.masterIds.push(masterId);
                                })
                            })
                        })
                } else {
                    records.push(el);
                }
            });

            commit('pullWorkWeeks', records);
        },
        async loadMasters({commit}) {
            const {res} = await to(userApi.getMasters());
            commit('pullMasters' , await res.json())
        },
        async loadServices({commit}) {
            const {res} = await to(workApi.getServices());
            commit('pullServices' , await res.json())
        },
        createMaster({}, data) {
            userApi.createMaster(data);
        },
        async savePhoto({}, photo) {
            const {res} = await to(photoApi.savePhoto(photo));
            return res.bodyText;
        },
        createService({}, service) {
            workApi.createService(service);
        },
        createWorkWeek() {
            workApi.createWorkWeek();
        },
        async setClient({commit, state}, master) {

            const props = {
                startWeekDate: state.records.find(record => record.days.some(({id}) => id === state.selectedDay.id)).start,
                day: state.selectedDay.day,
                hour: state.selectedHour.time,
                master: master.id,
                client: state.context.id
            };

            const {res} = await to(workApi.setClient(props));

            if (res.bodyText) {
                    commit('setClient', master);
            }
        },
        async getRecordsForClient({commit}) {
            const {res} = await to(workApi.getRecordsForClient());

            commit('pullRecordsForClient', await res.json());
        },
        changePhoto({}, photoName) {
            userApi.changePhoto(photoName);
        },
        changeFunctions({}, functions) {
            userApi.changeFunctions(functions);
        },
        async getFunctions() {
            const {res} = await to(userApi.getFunctions());
            return res.bodyText
        },
        async getRecordsForMaster({commit}) {
            const {res} = await to(workApi.getRecordsForMaster());

            commit('pullRecordsForMaster', await res.json());
        },
        checkRecord({commit}, props) {
            commit('setStatusRecord', props);
            workApi.checkRecord(props);
        },
        async getService({}, id) {
            const {res} = await to(workApi.getService(id));
            return await res.json();
        }
    }
});

export default store;