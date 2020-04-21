import vue from 'vue'

export default {
    savePhoto: photo => vue.http.post('/photo/save', photo, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}