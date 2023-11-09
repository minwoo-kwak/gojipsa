<script setup>
import KakaoMap from '../components/apart/KakaoMap.vue'
import TheApartList from '../components/apart/TheApartList.vue'
</script>

<template>
  <div class="apart-view">
    <TheApartList />
    <KakaoMap />
  </div>
</template>

<style scoped>
.apart-view {
  display: flex;
  padding-top: 65px;
}
</style>
