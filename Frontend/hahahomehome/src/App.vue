<script setup>
import HeaderComponent from "./components/HeaderComponent.vue";
</script>

<template>
<v-layout>
    <HeaderComponent/>
    <!-- 라우터 경로에 따라서 보여지는 화면 -->
    <RouterView></RouterView>
</v-layout>
</template>

<style scoped>

</style>
