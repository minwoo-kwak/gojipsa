<script setup>
import TheHeading from '../components/common/TheHeading.vue';
</script>

<template>
  <TheHeading />
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
