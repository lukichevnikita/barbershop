import Vue from 'vue'
import VueRouter from 'vue-router'
import Admin from "../pages/Admin.vue";
import Info from "../pages/Info.vue";
import Profile from "../pages/Profile.vue";
import Employees from "../pages/Employees.vue";
import Services from "../pages/Services.vue";
import Records from "../pages/Records.vue";

Vue.use(VueRouter);

const routes = [
    {
        path: '/admin',
        name: 'admin',
        component: Admin
    },
    {
        path: '/info',
        name: 'info',
        component: Info
    },
    {
        path: '/',
        name: 'profile',
        component: Profile
    },
    {
        path: '/employees',
        name: 'employees',
        component: Employees
    },
    {
        path: '/service',
        name: 'service',
        component: Services
    },
    {
        path: '/record',
        name: 'record',
        component: Records
    }
];

const router = new VueRouter({
    mode: "history",
    hash: false,
    routes
});

export default router;