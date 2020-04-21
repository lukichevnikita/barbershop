<template>
    <v-card class="mx-auto"
            width="70%"
            height="100%"
            align="center"
            jusify="space-around"
            style="border-radius: 0">
        <v-tabs v-model="tab"
                background-color="deep-purple accent-4"
                color="white"
                grow>
            <v-tab v-for="item in items"
                    :key="item">
                {{ item }}
            </v-tab>
        </v-tabs>

        <v-tabs-items v-model="tab">
            <v-tab-item>
                <v-card flat>
                    <v-card-text>
                        <v-row style="align-items: center">
                            <v-col cols="5" class="py-0">
                                <v-text-field label="Имя"
                                              v-model="context.name"
                                              :disabled="!isEditName"></v-text-field>
                            </v-col>
                            <v-btn icon
                                   @click="initChangeName">
                                <v-icon>{{isEditName ? 'save' : 'create'}}</v-icon>
                            </v-btn>
                            <v-btn icon
                                   @click="isEditName = false"
                                   v-if="isEditName">
                                <v-icon>clear</v-icon>
                            </v-btn>

                            <v-col v-if="isMaster" cols="5" class="py-0">
                                <v-textarea v-model="master.functions"
                                            label="Функции"
                                            :disabled="!isEditFunctions"></v-textarea>
                            </v-col>
                            <v-btn icon v-if="isMaster"
                                   @click="initChangeFunctions">
                                <v-icon>{{isEditFunctions ? 'save' : 'create'}}</v-icon>
                            </v-btn>
                            <v-btn icon
                                   @click="isEditFunctions = false"
                                   v-if="isEditFunctions && isMaster">
                                <v-icon>clear</v-icon>
                            </v-btn>
                        </v-row>
                        <v-row style="align-items: center">
                            <v-col cols="5" class="py-0">
                                <v-text-field label="Логин"
                                              v-model="context.user"
                                              :disabled="!isEditUsername"></v-text-field>
                            </v-col>
                            <v-btn icon
                                   @click="initChangeUsername">
                                <v-icon>{{isEditUsername ? 'save' : 'create'}}</v-icon>
                            </v-btn>
                            <v-btn icon
                                   @click="isEditUsername = false"
                                   v-if="isEditUsername">
                                <v-icon>clear</v-icon>
                            </v-btn>

                            <v-col v-if="isMaster" cols="5" class="py-0">
                                <v-file-input label="Фото"
                                              v-model="master.photo"
                                              filled prepend-icon="camera_alt" accept="/img/**"></v-file-input>
                            </v-col>
                        </v-row>
                        <v-row style="align-items: center">
                            <v-col cols="5" class="py-0">
                                <v-text-field label="Пароль"
                                              v-model="password"
                                              :disabled="!isEditPassword"></v-text-field>
                            </v-col>
                            <v-btn icon
                                   @click="initChangePassword">
                                <v-icon>{{isEditPassword ? 'save' : 'create'}}</v-icon>
                            </v-btn>
                            <v-btn icon
                                   @click="isEditPassword = false"
                                   v-if="isEditPassword">
                                <v-icon>clear</v-icon>
                            </v-btn>
                        </v-row>
                        <v-row style="align-items: center">
                            <v-col cols="5" class="py-0">
                                <v-text-field label="Номер телефона"
                                              v-model="context.phoneNumber"
                                              :disabled="!isEditPhoneNumber"></v-text-field>
                            </v-col>
                            <v-btn icon
                                   @click="initChangePhoneNumber">
                                <v-icon>{{isEditPhoneNumber ? 'save' : 'create'}}</v-icon>
                            </v-btn>
                            <v-btn icon
                                   @click="isEditPhoneNumber = false"
                                   v-if="isEditPhoneNumber">
                                <v-icon>clear</v-icon>
                            </v-btn>
                        </v-row>
                    </v-card-text>
                </v-card>
            </v-tab-item>
            <v-tab-item>
                <v-card flat>
                    <v-card-text>
                        <v-simple-table>
                            <template #default>
                                <thead>
                                <tr>
                                    <th class="text-left">Дата</th>
                                    <th class="text-left">Время</th>
                                    <th class="text-left">Статус</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(record, index) in recordsForClient" :key="index">
                                    <td :style="{textDecoration: record.statusId > 2 ? 'line-through' : 'none'}">
                                        {{ record.startWeekDate }}
                                    </td>
                                    <td :style="{textDecoration: record.statusId > 2 ? 'line-through' : 'none'}">
                                        {{ record.hour }}
                                    </td>
                                    <td :style="{color: getColor(record.statusId)}">{{ getStatus(record.statusId) }}</td>
                                </tr>
                                </tbody>
                            </template>
                        </v-simple-table>
                    </v-card-text>
                </v-card>
            </v-tab-item>
            <v-tab-item>
                <v-card flat>
                    <v-card-text>
                        <v-simple-table>
                            <template #default>
                                <thead>
                                <tr>
                                    <th class="text-left">Дата</th>
                                    <th class="text-left">Время</th>
                                    <th class="text-left">Действия</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(record, index) in recordsForMaster" :key="index">
                                    <td :style="{textDecoration: record.statusId > 2 ? 'line-through' : 'none'}">
                                        {{ record.startWeekDate }}
                                    </td>
                                    <td :style="{textDecoration: record.statusId > 2 ? 'line-through' : 'none'}">
                                        {{ record.hour }}
                                    </td>
                                    <td v-if="![3,4].includes(record.statusId)">
                                        <v-btn color="success" @click="checkRecord({id:record.id, status:3})">Завершить</v-btn>
                                        <v-btn color="error" @click="checkRecord({id:record.id, status:4})">Пропущено</v-btn>
                                    </td>
                                </tr>
                                </tbody>
                            </template>
                        </v-simple-table>
                    </v-card-text>
                </v-card>
            </v-tab-item>
        </v-tabs-items>
    </v-card>
</template>

<script>
    import {mapState, mapActions, mapGetters} from 'vuex'
    export default {
        name: "Profile",
        data: () => ({
            tab: null,
            items: ['Моя инфорамция', 'Мои записи'],
            password: null,
            isEditName: false,
            isEditUsername: false,
            isEditPassword: false,
            isEditPhoneNumber: false,
            isEditFunctions: false,
            isMaster: false,
            master: {
                functions: '',
                photo: null
            }
        }),
        computed: {
            ...mapState(['context']),
            ...mapGetters(['recordsForClient', 'recordsForMaster'])
        },
        watch: {
            async 'master.photo'(value) {
                if (value != null) {
                    const formData = new FormData();
                    formData.append('imageFile', value);
                    this.changePhoto(await this.savePhoto(formData));
                }
            },
        },
        methods: {
            ...mapActions([
                'changeName', 'changeUsername', 'changePassword', 'getRecordsForClient', 'changePhoneNumber',
                'getRecordsForMaster', 'changeFunctions', 'changePhoto', 'getFunctions', 'checkRecord'
            ]),
            initChangeName() {
                if (this.isEditName) {
                    this.isEditName = false;

                    this.changeName(this.context.name)
                } else {
                    this.isEditName = true;
                }
            },
            initChangeUsername() {
                if (this.isEditUsername) {
                    this.isEditUsername = false;

                    this.changeUsername(this.context.user)
                } else {
                    this.isEditUsername = true;
                }
            },
            initChangePassword() {
                if (this.isEditPassword) {
                    this.isEditPassword = false;

                    this.changePassword(this.password);
                    this.password = null;
                } else {
                    this.isEditPassword = true;
                }
            },
            initChangePhoneNumber() {
                if (this.isEditPhoneNumber) {
                    this.isEditPhoneNumber = false;

                    this.changePhoneNumber(this.context.phoneNumber);
                } else {
                    this.isEditPhoneNumber = true;
                }
            },
            initChangeFunctions() {
                if (this.isEditFunctions) {
                    this.isEditFunctions = false;

                    this.changeFunctions(this.master.functions);
                } else {
                    this.isEditFunctions = true;
                }
            },
            getStatus(statusId) {
                switch (statusId) {
                    case 1: return 'Ожидание одобрения';
                    case 2: return 'Одобрено';
                    case 3: return 'Пройдено';
                    default: return 'Пропущено';
                }
            },
            getColor(statusId) {
                switch (statusId) {
                    case 1: return 'lightgreen';
                    case 2: return 'green';
                    case 3: return 'lightgray';
                    default: return 'red';
                }
            },
            async checkMaster() {
                this.items.push('Мои клиенты');
                this.isMaster = true;
                this.master.functions = await this.getFunctions();
                this.getRecordsForMaster()
            }
        },
        mounted() {
            this.getRecordsForClient();
            this.checkMaster()
        }
    }
</script>

<style scoped>

</style>