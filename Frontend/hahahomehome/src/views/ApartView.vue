<script setup>
import axios from 'axios'
import KakaoMap from '../components/apart/KakaoMap.vue'
import TheApartList from '../components/apart/TheApartList.vue'
import HeaderComponent from '../components/common/HeaderComponent.vue'
import { onBeforeMount, ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'

// main page에서 들어온 매개변수
const route = useRoute()
const dongcode = ref(route.params.code)

// 바로 실거래가 조회로 들어온 경우
// 1. 현재 위치의 법정동 코드를 구해야 한다.
onBeforeMount(() => {
  if (dongcode.value == '') {
    // 현재 좌표를 구한다.
    navigator.geolocation.getCurrentPosition((position) => {
      //console.log(position.coords.latitude)   위도
      //console.log(position.coords.longitude)  경도
      const latitude = position.coords.latitude
      const longitude = position.coords.longitude
      if (latitude && longitude) {
        axios
          .get(
            `https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?x=127.03958123605&y=37.5012647456244`,
            {
              headers: {
                Authorization: `KakaoAK ${import.meta.env.VITE_KAKAO_OPEN_API_RESTAPI_KEY}`,
                'Content-Type': 'application/json;charset=UTF-8'
              }
            }
          )
          .then((result) => {
            dongcode.value = result.data.documents[0].code
            console.log(dongcode.value)
          })
          .catch((e) => {
            dongcode.value = '1168010100'
          })
      }
    })
  }
})
</script>

<template>
  <HeaderComponent />
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
