<template>
    <v-container fluid fill-height id="log" style="top: -56px">
        <v-layout align-center justify-center>
            <v-flex xs9 sm7 md5 lg3 class="log-reg-form">
                <login v-if="!isRegistration"
                       @reg="isRegistration = true"
                       @login="initLogin"></login>
                <registration v-else
                              @login="isRegistration = false"
                              @regUser="initReg"></registration>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import {mapActions} from 'vuex'
    import Login from "./Login.vue";
    import Registration from "./Registration.vue";

    export default {
        name: "Auth",
        components: {Registration, Login},
        data: () => ({
            isRegistration: false
        }),
        methods: {
            ...mapActions(['regUser', 'login']),
            async initReg(user) {
                const result = await this.regUser(user);
                if (result) {
                    this.isRegistration = false;
                }
            },
            initLogin(formData) {
                this.login(formData);
                setTimeout(() => {
                    window.location.assign(window.location.href.replace('login', ''))
                }, 1000);
            }
        }
    }
</script>

<style scoped>
    .log-reg-form {
        max-width: 50% !important;
    }
</style>