<script setup>
import TheHeading from '../components/common/TheHeading.vue'
import KakaoDetailMap from '../components/apart/KakaoDetailMap.vue'
import TheApartDetail from '../components/apart/TheApartDetail.vue'
import TheApartChart from '../components/apart/TheApartChart.vue'
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getApartDetailFromLocalAPI } from '@/api/apartment'

const route = useRoute()

const apartcode = ref(route.params.apartcode)
const apartDetail = ref(null)
const lat = ref(null)
const lng = ref(null)
const apartmentName = ref(null)

onMounted(() => {
  getApartDetailFromLocalAPI(
    apartcode.value,
    ({ data }) => {
      console.log('apart detail data == ', data)
      apartDetail.value = data
      lat.value = apartDetail.value.lat
      lng.value = apartDetail.value.lng
      apartmentName.value = apartDetail.value.apartmentName
    },
    (error) => {
      console.log('ApartDetailView error입니다 : ', error)
    }
  )
})
</script>

<template>
  <TheHeading />
  <div class="apart-detail-view">
    <!-- <KakaoDetailMap /> -->
    <KakaoDetailMap
      v-if="apartDetail !== null"
      :lat="lat"
      :lng="lng"
      :apartmentName="apartmentName"
    />
    <div>
      <TheApartDetail v-if="apartDetail !== null" :apartDetail="apartDetail" />
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
