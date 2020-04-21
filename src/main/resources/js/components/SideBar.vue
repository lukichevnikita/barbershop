<template>
    <v-card>
        <v-navigation-drawer class="deep-purple accent-4" dark permanent>
            <v-list-group value="true"
                          v-for="item in items"
                          :key="item.title"
                          v-model="item.active"
                          no-action>
                <template #activator>
                    <v-list-item-title v-text="item.title"
                                       style="color:white"></v-list-item-title>
                </template>

                <v-list-item link v-for="(subItem, subIndex) in item.subItems" :key="subIndex">
                    <v-list-item-title v-text="subItem.title"
                                       style="color:white"
                                       @click="$router.push(subItem.url)"></v-list-item-title>
                </v-list-item>
            </v-list-group>
            <v-list-item v-for="(item, index) in items2" :key="index" link @click="$router.push(item.url)">
                <v-list-item-title v-text="item.title"
                                   v-if="item.show"></v-list-item-title>
            </v-list-item>

            <template #append>
                <div class="pa-2">
                    <v-btn block @click="logout">Выйти</v-btn>
                </div>
            </template>
        </v-navigation-drawer>
    </v-card>
</template>

<script>
    import {mapState} from 'vuex'

    export default {
        name: "SideBar",
        data: () => ({
            items: [
                {
                    title: 'Запись',
                    active: false,
                    subItems: [
                        {
                            title: 'Сотрудники',
                            url: 'employees'
                        },
                        {
                            title: 'Услуга',
                            url: 'service'
                        },
                        {
                            title: 'Дата',
                            url: 'record'
                        }
                    ]
                }
            ],
            items2: [
                {
                    title: 'Профиль',
                    url: '/',
                    show: () => true
                },
                {
                    title: 'О нас',
                    url: '/info',
                    show: () => true
                },
                {
                    title: 'Панель администратора',
                    url: '/admin',
                    show: () => this.context.admin
                }
            ]
        }),
        computed: {
            ...mapState(['context'])
        },
        methods: {
            logout() {
                this.$router.push('/logout');
                this.$router.go(0);
            }
        }
    }
</script>

<style scoped>

</style>