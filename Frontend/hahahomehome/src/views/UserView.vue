<script setup>
import HeaderComponent from '../components/common/HeaderComponent.vue'
</script>

<template>
  <HeaderComponent />
  <v-main class="user-form d-flex justify-center align-center">
    <router-view />
  </v-main>
</template>

<style scoped>
.user-form {
  min-width: 960px;
  min-height: 960px;
}
</style>
