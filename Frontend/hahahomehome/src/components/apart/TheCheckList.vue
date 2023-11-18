<script setup>

import { useRouter } from 'vue-router';

const router = useRouter();

const props = defineProps({
  aptcode: String
})
const showCheckList = () => {
  // 로그인 정보가 없으면
  if(sessionStorage.getItem('userStore') === null || JSON.parse(sessionStorage.getItem('userStore'))['Authorization'] === null) {
    // 현재 페이지의 경로
    const currentPath = router.currentRoute.value.path;
    alert("체크리스트를 이용하려면 로그인을 해야합니다.")

    // 로그인 창으로 이동
    router.push(`/user/login?redirect=${currentPath}`);


  } else {
    // 새 창으로 이동
    window.open(`/apart/detail/checklist/${props.aptcode}`, '_blank', 'width=700, height=720')

  }

}
</script>

<template>
  <v-btn prepend-icon="mdi-pencil-remove-outline" stacked @click="showCheckList">체크리스트</v-btn>
</template>

<style scoped></style>
