<template>
    <v-card class="mx-auto"
            width="70%"
            height="100%"
            jusify="space-around"
            style="border-radius: 0; padding-top: 10px">
        <v-card-text>
            <v-data-table :headers="headers"
                          :items="getMasters"
                          class="elevation-1"
                          :loading="isLoading"
                          hide-default-footer>
                <template #item.photo="{ item }">
                    <v-img :src="`/img/${item.photo}`"
                           aspect-ratio="1"
                           max-width="40"
                           max-height="40"
                           class="grey lighten-2">
                        <template #placeholder>
                            <v-row class="fill-height ma-0"
                                   align="center"
                                   justify="center">
                                <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                            </v-row>
                        </template>
                    </v-img>
                </template>
            </v-data-table>
        </v-card-text>
    </v-card>
</template>

<script>
    import {mapGetters, mapActions} from 'vuex'
    export default {
        name: "Employees",
        data: () => ({
            headers: [
                {
                    text: 'Фото',
                    align: 'start',
                    sortable: false,
                    value: 'photo'
                },
                {
                    text: 'Имя',
                    align: 'start',
                    sortable: true,
                    value: 'name',
                },
                {
                    text: 'Услуга мастера',
                    align: 'start',
                    sortable: false,
                    value: 'functions'
                }
            ],
            isLoading: true
        }),
        computed: {
            ...mapGetters(['getMasters'])
        },
        methods: {
            ...mapActions(['loadMasters'])
        },
        async mounted() {
            this.isLoading = true;
            await this.loadMasters();
            this.isLoading = false;
        }
    }
</script>

<style scoped>

</style>