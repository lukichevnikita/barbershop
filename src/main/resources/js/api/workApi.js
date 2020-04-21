import vue from 'vue'

export default {
    createService: service => vue.http.post('work/createService', service),
    getServices: () => vue.http.get('work/getServices'),
    getService: id => vue.http.post('work/getService', id),
    createWorkWeek: () => vue.http.get('work/createWorkWeek'),
    getWorkWeeks: () => vue.http.get('work/getWorkWeeks'),
    setClient: props => vue.http.post('work/setClient', props),
    getRecordsForClient: () => vue.http.get('work/getRecordsForClient'),
    getRecordsForMaster: () => vue.http.get('work/getRecordsForMaster'),
    checkRecord: props => vue.http.put('work/checkRecord', props)
}