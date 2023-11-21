<script setup>
import { ref, inject } from 'vue'

const axios = inject('axios')

const passwordVisible = ref(false)
const passwordCheckVisible = ref(false)
const passwordCheck = ref('')

const userInfo = ref({
  userId: '',
  password: '',
  name: ''
})

const signUp = async () => {
  if (userInfo.value.password !== passwordCheck.value) {
    alert('비밀번호가 일치하지 않습니다.')
    return
  }

  // 아이디 중복 체크
  const isIdDuplicated = await checkId()

  await axios.post('/user/signup', userInfo.value).then((response) => {
    alert(response.data.message)
  })
}

const checkId = async () => {
  try {
    const response = await axios.get(`/user/checkId/${userInfo.value.userId}`)
    if (response.status === 200) {
      return true
    }
  } catch (error) {
    if (error.response.status === 409) {
      alert('이미 사용 중인 아이디입니다.')
      return false
    }
  }

  // return response.data.message === '이미 사용 중인 아이디입니다.'
}
</script>

<template>
  <!-- https://vuetifyjs.com/en/components/text-fields/#login-form -->
  <v-card class="signUp-form mx-auto pa-8" elevation="8" rounded="lg">
    <div class="text-subtitle-1 text-medium-emphasis">계정</div>

    <v-text-field
      density="compact"
      placeholder="아이디"
      prepend-inner-icon="mdi-email-outline"
      variant="outlined"
      v-model="userInfo.userId"
    ></v-text-field>

    <div class="text-subtitle-1 text-medium-emphasis">이름</div>

    <v-text-field
      density="compact"
      placeholder="이름"
      prepend-inner-icon="mdi-account-tag"
      variant="outlined"
      v-model="userInfo.name"
    ></v-text-field>

    <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
      비밀번호
    </div>

    <v-text-field
      :append-inner-icon="passwordVisible ? 'mdi-eye-off' : 'mdi-eye'"
      :type="passwordVisible ? 'text' : 'password'"
      density="compact"
      placeholder="비밀번호"
      prepend-inner-icon="mdi-lock-outline"
      variant="outlined"
      @click:append-inner="passwordVisible = !passwordVisible"
      v-model="userInfo.password"
    ></v-text-field>

    <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
      비밀번호 확인
    </div>

    <v-text-field
      :append-inner-icon="passwordCheckVisible ? 'mdi-eye-off' : 'mdi-eye'"
      :type="passwordCheckVisible ? 'text' : 'password'"
      density="compact"
      placeholder="비밀번호 확인"
      prepend-inner-icon="mdi-lock-outline"
      variant="outlined"
      @click:append-inner="passwordCheckVisible = !passwordCheckVisible"
      v-model="passwordCheck"
    ></v-text-field>

    <v-btn block class="mb-8" color="green" size="large" variant="tonal" @click="signUp">
      회원가입
    </v-btn>

    <v-btn
      block
      class="mb-8"
      color="red"
      size="large"
      variant="tonal"
      @click="$router.push({ name: 'main' })"
    >
      취소
    </v-btn>
  </v-card>
</template>

<style scoped>
.signUp-form {
  width: 30rem;
  max-height: 35rem;
}
</style>
