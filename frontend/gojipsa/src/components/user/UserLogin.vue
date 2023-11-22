<script setup>
import { ref } from 'vue'
import { useUserStore } from '../../stores/user'
import { useRouter } from 'vue-router'

const userStore = useUserStore()
const router = useRouter()

const passwordVisible = ref(false)

const userId = ref('')
const password = ref('')

async function login() {
  await userStore.login(userId.value, password.value)

  // 로그인 이전에 있었던 경로
  const redirect = router.currentRoute.value.query.redirect
  console.log('redirect ==', redirect)
  console.log('redirect type ==', typeof redirect)
  // 로그인 이전에 경로가 있었으면 그 경로로 가고 없으면 해당 페이지로 이동
  if (redirect) {
    console.log('리다이렉트 있을 경우 !!')
    console.log('Redirecting to:', redirect)
    router.push(`${redirect}`).catch((err) => console.error(err))
  } else {
    router.push('/')
  }
}
</script>

<template>
  <!--
            https://vuetifyjs.com/en/components/text-fields/#login-form
        -->
  <v-card class="login-form mx-auto pa-8" elevation="8" rounded="lg">
    <div class="text-subtitle-1 text-medium-emphasis">계정</div>

    <v-text-field
      density="compact"
      placeholder="이메일"
      prepend-inner-icon="mdi-email-outline"
      variant="outlined"
      v-model="userId"
    ></v-text-field>

    <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
      비밀번호

      <a
        class="text-caption text-decoration-none text-blue"
        href="#"
        rel="noopener noreferrer"
        target="_blank"
      >
        비밀번호를 잊으셨나요?</a
      >
    </div>

    <v-text-field
      :append-inner-icon="passwordVisible ? 'mdi-eye-off' : 'mdi-eye'"
      :type="passwordVisible ? 'text' : 'password'"
      density="compact"
      placeholder="비밀번호"
      prepend-inner-icon="mdi-lock-outline"
      variant="outlined"
      @click:append-inner="passwordVisible = !passwordVisible"
      v-model="password"
    ></v-text-field>

    <v-btn block class="mb-8" color="blue" size="large" variant="tonal" @click="login">
      로그인
    </v-btn>

    <v-btn
      block
      class="mb-8"
      color="green"
      size="large"
      variant="tonal"
      @click="$router.push({ name: 'signup' })"
    >
      회원가입
    </v-btn>
  </v-card>
</template>

<style scoped>
.login-form {
  min-width: 500px;
}
</style>
