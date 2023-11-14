<script setup>
import TheHeading from '../components/common/TheHeading.vue'
import KakaoDetailMap from '../components/apart/KakaoDetailMap.vue'
</script>

<template>
  <TheHeading />
  <div class="apart-detail-view">
    <TheApartList />
    <KakaoDetailMap />
  </div>
</template>

<style scoped>
.apart-detail-view {
  display: flex;
  padding-top: 65px;
}
</style>
