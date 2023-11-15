<script setup>
import axios from 'axios'
import KakaoMap from '../components/apart/KakaoMap.vue'
import TheApartList from '../components/apart/TheApartList.vue'
import { onBeforeMount, ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import TheHeading from '../components/common/TheHeading.vue'
import { getApartListAPI } from '@/api/apartment'

// main page에서 들어온 매개변수
const route = useRoute()
const dongcode = ref(route.params.code)
const page = ref(1)
const apartData = ref({})

// 바로 실거래가 조회로 들어온 경우
// 1. 현재 위치의 법정동 코드를 구해야 한다.
onBeforeMount(() => {
  if (dongcode.value == '') {
    // 현재 좌표를 구한다.
    navigator.geolocation.getCurrentPosition((position) => {
      const x = position.coords.longitude
      const y = position.coords.latitude

      if (x && y) {
        axios
          .get(`https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?x=${x}&y=${y}`, {
            headers: {
              Authorization: `KakaoAK ${import.meta.env.VITE_KAKAO_OPEN_API_RESTAPI_KEY}`,
              'Content-Type': 'application/json;charset=UTF-8'
            }
          })
          .then((result) => {
            dongcode.value = result.data.documents[0].code
            getApartInfos()
          })
          .catch((e) => {
            dongcode.value = '1168010100'
            getApartInfos()
          })
      }
    })
  } else {
    getApartInfos()
  }
})

// api로부터 apartment 정보를 가져온다.
const getApartInfos = () => {
  getApartListAPI(
    {
      dongcode: dongcode.value,
      page: page.value
    },
    ({ data }) => {
      // 아파트 목록 정보를 ref 변수에 저장
      //console.log(data)
      apartData.value = data
      console.log(apartData.value)
    }
  ),
    (err) => {
      console.log(err)
    }
}
</script>

<template>
  <TheHeading />
  <div class="apart-view">
    <!--param으로 아파트 목록을 전달-->
    <TheApartList :apartData="apartData" />
    <KakaoMap />
  </div>
</template>

<style scoped>
.apart-view {
  display: flex;
  padding-top: 65px;
}
</style>
