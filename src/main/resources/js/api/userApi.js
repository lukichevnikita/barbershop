import vue from 'vue'

export default {
    createUser: user => vue.http.post('/auth/registration', user),
    changeName: name => vue.http.put('user/changeName', name),
    changeUsername: username => vue.http.put('user/changeUsername', username),
    changePassword: password => vue.http.put('user/changePassword', password),
    changePhoneNumber: phoneNumber => vue.http.put('user/changePhoneNumber', phoneNumber),
    createMaster: data => vue.http.post('user/createMaster', data),
    getMasters: () => vue.http.get('user/getMasters'),
    changePhoto: photo => vue.http.put('user/changePhoto', photo),
    changeFunctions: func => vue.http.put('user/changeFunctions', func),
    getFunctions: () => vue.http.get('user/getFunctions'),
}