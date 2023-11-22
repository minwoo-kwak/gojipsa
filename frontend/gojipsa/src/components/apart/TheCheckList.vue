<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const props = defineProps({
  aptcode: String
})

/**
 * 체크리스트를 보여주는 함수
 */
const showCheckList = () => {
  // 로그인 정보가 없으면
  if (
    sessionStorage.getItem('userStore') === null ||
    JSON.parse(sessionStorage.getItem('userStore'))['Authorization'] === null
  ) {
    // 현재 페이지의 경로
    const currentPath = router.currentRoute.value.path
    alert('체크리스트를 이용하려면 로그인을 해야합니다.')

    // 로그인 창으로 이동
    router.push(`/user/login?redirect=${currentPath}`)
  } else {
    // 새 창으로 이동
    window.open(`/apart/detail/checklist/${props.aptcode}`, '_blank', 'width=700, height=720')
  }
}
</script>

<template>
  <v-btn @click="showCheckList" color="indigo-lighten-2" dark class="checklist-btn"
    ><v-icon>mdi-pencil</v-icon
    ><v-tooltip activator="parent" location="end">체크리스트 작성하기</v-tooltip></v-btn
  >
</template>

<style scoped>
.checklist-btn {
  border-radius: 50%;
  width: 50px;
  height: 60px;
}
</style>
