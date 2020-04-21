<template>
    <v-card class="mx-auto"
            width="70%"
            height="max-content"
            jusify="space-around"
            style="border-radius: 0;">
        <v-expansion-panels>
            <v-expansion-panel>
                <v-expansion-panel-header>Новый мастер</v-expansion-panel-header>
                <v-expansion-panel-content>
                    <v-text-field v-model="master.username" label="username"></v-text-field>
                    <v-text-field v-model="master.password" label="Пароль"></v-text-field>
                    <v-text-field v-model="master.name" label="Имя"></v-text-field>
                    <v-textarea v-model="master.functions" label="Функции"></v-textarea>
                    <v-file-input label="Фото"
                                  v-model="master.photo"
                                  filled prepend-icon="camera_alt" accept="/img/**"></v-file-input>

                    <v-row>
                        <v-spacer/>
                        <v-btn color="primary" @click="initCreateMaster">Добавить</v-btn>
                    </v-row>
                </v-expansion-panel-content>
            </v-expansion-panel>
            <v-expansion-panel>
                <v-expansion-panel-header>Новая рабочая неделя</v-expansion-panel-header>
                <v-expansion-panel-content>
                    <v-row>
                        <v-spacer/>
                        <v-btn color="primary" @click="initCreateWorkWeek">Добавить</v-btn>
                        <v-spacer/>
                    </v-row>
                </v-expansion-panel-content>
            </v-expansion-panel>
            <v-expansion-panel>
                <v-expansion-panel-header>Услуга</v-expansion-panel-header>
                <v-expansion-panel-content>
                    <v-switch label="Редактировние?"
                              :value="isEditService"
                              v-model="isEditService"></v-switch>
                    <v-col cols="2" class="py-0">
                        <v-text-field v-if="isEditService"
                                      v-model="service.id"
                                      @change="getService"
                                      label="id"
                                      type="number"></v-text-field>
                    </v-col>
                    <v-file-input label="Фото"
                                  v-model="service.photo"
                                  filled prepend-icon="camera_alt" accept="/img/**"></v-file-input>
                    <v-text-field v-model="service.name" label="Название"></v-text-field>
                    <v-col cols="3" class="py-0">
                        <v-text-field v-model="service.price"
                                      label="Цена"
                                      prepend-icon="account_balance_wallet"></v-text-field>
                    </v-col>
                    <v-row>
                        <v-spacer/>
                        <v-btn color="primary" @click="initCreateService">Добавить</v-btn>
                    </v-row>
                </v-expansion-panel-content>
            </v-expansion-panel>
        </v-expansion-panels>
    </v-card>
</template>

<script>
    import {mapActions} from 'vuex'

    export default {
        name: "Admin",
        components: {},
        data: () => ({
            master: {
                username: null,
                password: null,
                name: null,
                photo: null,
                functions: null,
                photoName: ''
            },
            service: {
                id: null,
                photo: null,
                name: '',
                price: '',
                photoName: ''
            },
            isEditService: false,
            timeoutService: null
        }),
        watch: {
            async 'master.photo'(value) {
                if (value != null) {
                    const formData = new FormData();
                    formData.append('imageFile', value);
                    this.master.photoName = await this.savePhoto(formData);
                }
            },
            async 'service.photo'(value) {
                if (value != null) {
                    const formData = new FormData();
                    formData.append('imageFile', value);
                    this.service.photoName = await this.savePhoto(formData);
                }
            }
        },
        methods: {
            ...mapActions(['createMaster', 'savePhoto', 'createService', 'createWorkWeek']),
            initCreateMaster() {
                this.createMaster({
                    registrationData: {
                        username: this.master.username,
                        password: this.master.password
                    },
                    name: this.master.name,
                    functions: this.master.functions,
                    photo: this.master.photoName
                });

                this.master = {
                    username: null,
                    password: null,
                    name: null,
                    photo: '',
                    functions: null,
                    photoName: ''
                }
            },
            initCreateWorkWeek() {
                this.createWorkWeek();
            },
            initCreateService() {
                this.createService({
                    id: this.service.id,
                    name: this.service.name,
                    price: this.service.price,
                    photo: this.service.photoName
                });

                this.service = {
                    id: null,
                    photo: '',
                    name: '',
                    price: '',
                    photoName: ''
                }
            },
            getService() {
                clearTimeout(this.timeoutService);

                setTimeout(async () => {
                    const service = await this.$store.dispatch('getService', this.service.id);
                    this.service.name = service.name;
                    this.service.price = service.price;
                }, 500)
            }
        }
    }
</script>