<template>
    <v-app>
        <v-content>
            <top-menu style="margin-left: 256px"/>
            <side-bar class="side-bar"/>
            <v-layout fill-height class="page-body">
                <router-view/>
            </v-layout>
        </v-content>
    </v-app>
</template>

<script>
    import {mapActions} from 'vuex'
    import TopMenu from "../components/TopMenu.vue";
    import SideBar from "../components/SideBar.vue";
    export default {
        name: "App",
        components: {SideBar, TopMenu},
        methods: {
            ...mapActions(['getContext'])
        },
        mounted() {
            this.getContext()
        }
    }
</script>

<style lang="scss">
    @import '../../styles/index';
</style>