<template>
    <v-card class="mx-auto"
            width="70%"
            height="max-content"
            jusify="space-around"
            style="border-radius: 0; padding-top: 10px">

   <v-row>
                <v-card v-for="item in getServices"
                        class="mx-auto my-12"
                        max-width="250">
                    <v-img height="200"
                           :src="`/img/${item.photo}`"></v-img>

                    <v-card-title>{{item.name}}</v-card-title>

                    <v-divider class="mx-4"></v-divider>

                    <v-row class="pa-2 ma-0">
                        <v-spacer></v-spacer>
                        <b>{{item.price}} руб.</b>
                    </v-row>
                </v-card></v-row>
    </v-card>
</template>

<script>
    import {mapGetters, mapActions} from 'vuex'
    export default {
        name: "Services",
        data: () => ({
            headers: [
                {
                    text: 'Фото причёски',
                    align: 'start',
                    sortable: false,
                    value: 'photo'
                },
                {
                    text: 'Название',
                    align: 'start',
                    sortable: false,
                    value: 'name'
                },
                {
                    text: 'Цена',
                    align: 'start',
                    sortable: false,
                    value: 'price'
                }
            ],
            isLoading: true
        }),
        computed: {
            ...mapGetters(['getServices'])
        },
        methods: {
            ...mapActions(['loadServices'])
        },
        async mounted() {
            this.isLoading = true;
            await this.loadServices();
            this.isLoading = false;
        }
    }
</script>

<style scoped>

</style>