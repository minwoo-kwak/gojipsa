<template>
  <div class="map-wrap">
    <TheFilter />
    <div id="map"></div>
    <div v-for="marker in apartMarker" :key="marker.aptCode">마커임</div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import TheFilter from './TheFilter.vue'
import TheCalculator from './TheCalculator.vue'
import { storeToRefs } from 'pinia'
import { useApartStore } from '../../stores/apart'
import { useFilterStore } from '../../stores/filter'

var map
const positions = ref([])
const markers = ref([])

const marketMarkers = ref([])
const convenienceMarkers = ref([])
const schoolMarkers = ref([])
const subwayMarkers = ref([])
const bankMarkers = ref([])
const hospitalMarkers = ref([])

const apartStore = useApartStore()
const { apartMarker } = storeToRefs(apartStore)

const filterStore = useFilterStore()
const { market, convenience, school, subway, bank, hospital } = storeToRefs(filterStore)

watch(market, (newMarket, oldMarket) => {
  // 지도에 마커를 표시한다.

  // 체크되어 있는 상태
  if (market.value) {
    //console.log('위치를 보여줘야함')

    var ps = new kakao.maps.services.Places(map)
    ps.categorySearch('MT1', (data, status, pagination) => {
      if (status === kakao.maps.services.Status.OK) {
        console.log('지도에 그리기')
        for (var i = 0; i < data.length; i++) {
          var marker = new kakao.maps.Marker({
            map: map,
            position: new kakao.maps.LatLng(data[i].y, data[i].x)
          })
          marketMarkers.value.push(marker)
        }
        map.setLevel(8)
      }
    })
  }
  // 체크 해제되어 있는 상태
  else {
    console.log('위치를 숨겨야 함')
    if (marketMarkers.value.length > 0) {
      marketMarkers.value.forEach((marker) => marker.setMap(null))
      marketMarkers.value = []
    }
  }
})

watch(
  apartMarker,
  (newMarker, oldMarker) => {
    // Map을 다시 그린다.

    // 아파트 정보 결과가 있다면
    if (apartMarker.value.length > 0) {
      deleteMarkers()
      // 이동할 위도 경도 위치를 생성한다.
      var moveLatLon = new kakao.maps.LatLng(apartMarker.value[0].lat, apartMarker.value[0].lng)

      // 마커를 생성한다.
      markers.value = []
      apartMarker.value.forEach((info) => {
        var markerPosition = new kakao.maps.LatLng(info.lat, info.lng)

        const marker = new kakao.maps.Marker({
          map: map,
          position: markerPosition,
          title: info.apartmentName,
          clickable: true,
          removable: true
        })

        markers.value.push(marker)
      })
      map.panTo(moveLatLon)
      map.setLevel(3)
    }
  },
  { deep: true }
)

const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null))
  }
}

// 키워드 검색 완료 시 호출되는 콜백 함수
function placesSearchCB(data, status, pagination) {
  if (status === kakao.maps.services.Status.OK) {
    for (var i = 0; i < data.length; i++) {
      var marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(data[i].y, data[i].x)
      })
    }
  }
}
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
    level: 2
  }
  map = new kakao.maps.Map(container, options)
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
