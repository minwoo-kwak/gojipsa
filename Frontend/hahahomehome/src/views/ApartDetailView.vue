<script setup>
import TheHeading from '../components/common/TheHeading.vue'
import KakaoDetailMap from '../components/apart/KakaoDetailMap.vue'
import TheApartDetail from '../components/apart/TheApartDetail.vue'

const props = defineProps({
  apartcode: String
})
console.log(props)
const aptcode = '111111'
</script>

<template>
  <TheHeading />
  <div class="apart-detail-view">
    <!-- <KakaoDetailMap /> -->
    <KakaoDetailMap />
    <TheApartDetail />
  </div>
</template>

<style scoped>
.apart-detail-view {
  padding-top: 65px;
  width: 100%;
}
</style>
