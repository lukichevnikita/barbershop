<template>
    <v-card class="elevation-12 login-form">
        <v-card-title>Авторизация</v-card-title>
        <v-divider></v-divider>
        <v-flex xs10 offset-xs1 pb-2 pt-3>
            <form id="loginForm">
                <v-text-field required
                              v-model="username"
                              ref="username"
                              name="username"
                              label="Логин">
                </v-text-field>
                <v-text-field v-model="password"
                              :append-icon="showPass ? 'visibility' : 'visibility_off'"
                              :type="showPass ? 'text' : 'password'"
                              @click:append="showPass = !showPass"
                              ref="password"
                              name="password"
                              label="Пароль"
                              required>
                </v-text-field>
                <v-layout row justify-space-between>
                    <a href="" @click.prevent="$emit('reg')">Зарегестрироваться?</a>
                    <v-spacer/>
                    <v-btn @click="onSubmit" class="login-btn">Вход</v-btn>
                </v-layout>
            </form>
        </v-flex>
    </v-card>
</template>

<script>
    export default {
        name: "Login",
        data: () => ({
            username: undefined,
            password: undefined,
            showPass: false
        }),
        methods: {
            onSubmit() {
                if (this.username !== '' && this.password !== '') {
                    const formData = new FormData();
                    formData.append('username', this.username);
                    formData.append('password', this.password);
                    this.$emit('login', formData);
                }
            }
        }
    }
</script>

<style scoped>
    .login-btn {
        color: white !important;
        background-color: #0D47A1 !important;
        margin-bottom: 10px;
    }

    .login-form {
        width: 500px !important;
        position: absolute;
        left: calc(100% - 50% - 250px);
        top: calc(100% - 50% - 125px);
    }
</style>
