<template>
    <div :style="{backgroundColor: chekHour() ? 'lightGreen' : 'red'}"
        @click="$emit('setClient')">
        {{hour.time}}
    </div>
</template>

<script>
    export default {
        name: "Record",
        props: ['hour'],
        methods: {
            chekHour() {
                return this.hour.masterIds && this.hour.masterIds.some(id => id === null)
            }
        }
    }
</script>

<style scoped>

</style>