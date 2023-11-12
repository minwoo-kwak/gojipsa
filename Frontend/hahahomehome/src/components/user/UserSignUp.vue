<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const userName = ref('')
const userId = ref('')
const password = ref('')
const passwordCheck = ref('')

function registUser() {
  router.push('/')
}
function checkId() {
  // TODO:아이디 중복 체크
}
</script>

<template>
  <v-form class="regist-user-form">
    <v-container>
      <v-row><v-text-field label="이름" v-model="userName" /></v-row>
      <v-row
        ><v-text-field label="아이디" v-model="userId" />
        <v-btn @click="checkId">중복확인</v-btn></v-row
      >
      <v-row><v-text-field type="password" label="비밀번호" v-model="password" /></v-row>
      <v-row><v-text-field type="password" label="비밀번호 확인" v-model="passwordCheck" /></v-row>
      <v-row><v-btn @click="registUser">회원가입</v-btn></v-row>
    </v-container>
  </v-form>
</template>

<style scoped>
.regist-user-form {
  min-width: 500px;
}
</style>
