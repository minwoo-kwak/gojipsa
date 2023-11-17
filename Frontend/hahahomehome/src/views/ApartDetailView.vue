<script setup>
import TheHeading from '../components/common/TheHeading.vue'
import KakaoDetailMap from '../components/apart/KakaoDetailMap.vue'
import TheApartDetail from '../components/apart/TheApartDetail.vue'
import TheApartChart from '../components/apart/TheApartChart.vue'
import { ref } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

const apartcode = ref(route.params.apartcode)
</script>

<template>
  <TheHeading />
  <div class="apart-detail-view">
    <!-- <KakaoDetailMap /> -->
    <KakaoDetailMap />
    <div>
      <TheApartDetail :aptCode="apartcode" />
      <TheApartChart :aptCode="apartcode" />
    </div>
  </div>
</template>

<style scoped>
.apart-detail-view {
  padding-top: 65px;
  width: 100%;
}
</style>
