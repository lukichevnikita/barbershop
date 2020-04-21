import Vue from 'vue'
import './api/resource'
import '@babel/polyfill'
import lodash from 'lodash'
import store from './store/mainStore'
import VueResource from 'vue-resource'
import Auth from './pages/auth/Auth.vue'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import to from './functionLib/to.js'

import { library }         from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { fas }             from '@fortawesome/free-solid-svg-icons'
library.add(fas);
Vue.component('font-awesome-icon', FontAwesomeIcon);

Vue.use(Vuetify, VueResource, lodash, to);

new Vue({
    el: '#auth',
    vuetify: new Vuetify({
        icons: {
            iconfont: 'mdiSvg' || 'faSvg', // 'mdi' || 'mdiSvg' || 'md' || 'fa' || 'fa4'
        },
    }),
    store,
    render: a => a(Auth)
});