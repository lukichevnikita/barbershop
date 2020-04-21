<template>
    <v-card class="mx-auto"
            width="70%"
            height="max-content"
            jusify="space-around"
            style="border-radius: 0;">
        <v-tabs v-model="tab"
                background-color="deep-purple accent-4"
                color="white"
                grow>
            <v-tab v-for="record in records"
                   :key="record.id">
                {{ record.range }}
            </v-tab>
        </v-tabs>

        <v-tabs-items v-model="tab">
            <v-tab-item v-for="record in records" :key="record.id + 't'">
                <v-card flat>
                    <v-card-text>
                        <v-row>
                            <v-col cols="1"></v-col>
                            <v-col cols="2"
                                   v-for="day in record.days"
                                   :key="day.id">
                                <div style="text-align: center;">
                                    <b>{{day.day}}</b>
                                </div>
                                <record v-for="hour in day.hours"
                                        :key="hour.id"
                                        :hour="hour"
                                        class="record"
                                        @setClient="openModal(hour, day)">
                                </record>
                                <v-dialog v-model="dialog"
                                          max-width="290">
                                    <v-card>
                                        <v-card-title class="headline">Выбор мастера</v-card-title>

                                        <v-card-text>
                                            <v-autocomplete
                                                    v-model="master"
                                                    :items="filteredMasters"
                                                    :loading="isLoading"
                                                    @click="search"
                                                    color="white"
                                                    hide-no-data
                                                    hide-selected
                                                    item-text="name"
                                                    label="Мастера"
                                                    prepend-icon="mdi-database-search"
                                                    return-object
                                            ></v-autocomplete>
                                        </v-card-text>

                                        <v-card-actions>
                                            <v-spacer></v-spacer>

                                            <v-btn color="error darken-1"
                                                   text
                                                   @click="dialog = false">
                                                Отмена
                                            </v-btn>

                                            <v-btn color="green darken-1"
                                                   text
                                                   @click="initSetClient">
                                                Подтвердить
                                            </v-btn>
                                        </v-card-actions>
                                    </v-card>
                                </v-dialog>
                            </v-col>
                        </v-row>
                    </v-card-text>
                </v-card>
            </v-tab-item>
        </v-tabs-items>
    </v-card>
</template>

<script>
    import Record from "../components/Record.vue";
    import {mapActions, mapState} from 'vuex'

    export default {
        name: "Records",
        data: () => ({
            hours: [
                '12:00', '12:30', '13:00', '13:30', '14:00', '14:30'
            ],
            tab: null,
            tabs: [],
            dialog: false,
            master: null,
            isLoading: false
        }),
        components: {Record},
        methods:{
            ...mapActions(['getRecords', "loadMasters"]),
            openModal(hour, day) {
                this.$store.commit('setSelectedDay', day);
                this.$store.commit('setSelectedHour', hour);
                this.dialog = true;
            },
            initSetClient() {
                const {master} = this;
                this.$store.dispatch('setClient', master);
                this.master = null;
                this.dialog = false;
            },
            async search() {
                this.isLoading = true;
                await this.loadMasters();
                this.isLoading = false;
            }
        },
        computed: {
            ...mapState(['records', "masters", "selectedDay", "selectedHour"]),
            filteredMasters() {
                if (this.selectedHour) {
                    const {masterIds} = this.selectedHour;
                    return this.masters.filter(el => {
                        return !masterIds.includes(el.id);
                    })
                } else {
                    return this.masters;
                }
            }
        },
        mounted() {
            this.getRecords()
        }
    }
</script>

<style scoped>
    .record {
        border: 0.5px solid lightgray;
        text-align: center;
    }
</style>