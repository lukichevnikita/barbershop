import store from '../store/mainStore.js'

export default function to(promise) {
    store.commit('LOADER', true);
    return promise.then(res => {
        store.commit('LOADER', false);
        return {err: null, res};
    }).catch(err => {
        store.commit('LOADER', false);
        return {err, res: null}
    });
}