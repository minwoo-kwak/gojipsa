<template>
  <div class="map-wrap">
    <div id="map"></div>
    <div id="roadview" style="width: 100%; height: 300px"></div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import TheFilter from './TheFilter.vue'
import TheCalculator from './TheCalculator.vue'

let map
let rv
let rvClient
const positions = ref([])
const markers = ref([])

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap()
  } else {
    const script = document.createElement('script')
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${
      import.meta.env.VITE_KAKAO_OPEN_API_JAVASCIPRT_KEY
    }&libraries=services,clusterer`
    /* global kakao */
    script.onload = () => kakao.maps.load(() => initMap())
    document.head.appendChild(script)
  }
})

const initMap = () => {
  const container = document.getElementById('map')
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3
  }
  map = new kakao.maps.Map(container, options)

  const rvContainer = document.getElementById('roadview')

  // 로드뷰 객체를 생성합니다
  rv = new kakao.maps.Roadview(rvContainer)

  // 좌표로부터 로드뷰 파노라마 ID를 가져올 로드뷰 클라이언트 객체를 생성합니다
  rvClient = new kakao.maps.RoadviewClient()

  var position = new kakao.maps.LatLng(33.450701, 126.570667)

  // 특정 위치의 좌표와 가까운 로드뷰의 panoId를 추출하여 로드뷰를 띄운다.
  rvClient.getNearestPanoId(position, 130, function (panoId) {
    rv.setPanoId(panoId, position) //panoId와 중심좌표를 통해 로드뷰 실행
  })
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#map {
  top: -50px;
  width: 90rem;
  height: 58.3rem;
}

button {
  margin: 0 3px;
}
</style>
