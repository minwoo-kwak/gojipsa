<script setup>
import TheHeading from '../components/common/TheHeading.vue'
import KakaoDetailMap from '../components/apart/KakaoDetailMap.vue'
</script>

<template>
  <TheHeading />
  <div class="apart-detail-view">
    <!-- <KakaoDetailMap /> -->
    <KakaoDetailMap />
  </div>
</template>

<style scoped>
.apart-detail-view {
  padding-top: 65px;
  width: 100%;
}
</style>
