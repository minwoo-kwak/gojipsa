<script setup>
import axios from 'axios'
import { ref } from 'vue'
import { useUserStore } from '../../stores/user'
import { useRouter } from 'vue-router'

const userStore = useUserStore()
const router = useRouter()

const userId = ref('')
const password = ref('')

async function login() {
  await userStore.login(userId.value, password.value)

  router.push('/')
}
</script>

<template>
  <v-form class="login-form">
    <v-container>
      <v-row><v-text-field label="아이디" v-model="userId" /></v-row>
      <v-row><v-text-field label="패스워드" v-model="password" /></v-row>
      <v-row><v-btn @click="login">로그인</v-btn></v-row>
    </v-container>
  </v-form>
</template>

<style scoped>
.login-form {
  min-width: 500px;
}
</style>
