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
      const imgSrc = '/src/assets/marker/marketMarker.png'
      const imgSize = new kakao.maps.Size(40, 45)
      const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize)
      if (status === kakao.maps.services.Status.OK) {
        console.log('지도에 그리기')
        for (var i = 0; i < data.length; i++) {
          var marker = new kakao.maps.Marker({
            map: map,
            position: new kakao.maps.LatLng(data[i].y, data[i].x),
            image: markerImage
          })
          marketMarkers.value.push(marker)
        }
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

watch(convenience, (newConvenience, oldConvenience) => {
  if (convenience.value) {
    var ps = new kakao.maps.services.Places(map)
    ps.categorySearch('CS2', (data, status, pagination) => {
      if (status === kakao.maps.services.Status.OK) {
        const imgSrc = '/src/assets/marker/convenienceMarker.png'
        const imgSize = new kakao.maps.Size(40, 45)
        const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize)
        for (var i = 0; i < data.length; i++) {
          var marker = new kakao.maps.Marker({
            map: map,
            position: new kakao.maps.LatLng(data[i].y, data[i].x),
            image: markerImage
          })
          convenienceMarkers.value.push(marker)
        }
      }
    })
  } else {
    if (convenienceMarkers.value.length > 0) {
      convenienceMarkers.value.forEach((marker) => marker.setMap(null))
      convenienceMarkers.value = []
    }
  }
})
watch(school, (newSchool, oldSchool) => {
  if (school.value) {
    var ps = new kakao.maps.services.Places(map)
    ps.categorySearch('SC4', (data, status, pagination) => {
      if (status === kakao.maps.services.Status.OK) {
        const imgSrc = '/src/assets/marker/schoolMarker.png'
        const imgSize = new kakao.maps.Size(40, 45)
        const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize)
        for (var i = 0; i < data.length; i++) {
          var marker = new kakao.maps.Marker({
            map: map,
            position: new kakao.maps.LatLng(data[i].y, data[i].x),
            image: markerImage
          })
          schoolMarkers.value.push(marker)
        }
      }
    })
  } else {
    if (schoolMarkers.value.length > 0) {
      schoolMarkers.value.forEach((marker) => marker.setMap(null))
      schoolMarkers.value = []
    }
  }
})

watch(subway, (newSubway, oldSubway) => {
  if (subway.value) {
    var ps = new kakao.maps.services.Places(map)
    ps.categorySearch('SW8', (data, status, pagination) => {
      if (status === kakao.maps.services.Status.OK) {
        const imgSrc = '/src/assets/marker/subwayMarker.png'
        const imgSize = new kakao.maps.Size(40, 45)
        const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize)
        for (var i = 0; i < data.length; i++) {
          var marker = new kakao.maps.Marker({
            map: map,
            position: new kakao.maps.LatLng(data[i].y, data[i].x),
            image: markerImage
          })
          subwayMarkers.value.push(marker)
        }
      }
    })
  } else {
    if (subwayMarkers.value.length > 0) {
      subwayMarkers.value.forEach((marker) => marker.setMap(null))
      subwayMarkers.value = []
    }
  }
})
watch(bank, (newBank, oldBank) => {
  if (bank.value) {
    var ps = new kakao.maps.services.Places(map)
    ps.categorySearch('BK9', (data, status, pagination) => {
      if (status === kakao.maps.services.Status.OK) {
        const imgSrc = '/src/assets/marker/bankMarker.png'
        const imgSize = new kakao.maps.Size(40, 45)
        const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize)
        for (var i = 0; i < data.length; i++) {
          var marker = new kakao.maps.Marker({
            map: map,
            position: new kakao.maps.LatLng(data[i].y, data[i].x),
            image: markerImage
          })
          bankMarkers.value.push(marker)
        }
      }
    })
  } else {
    if (bankMarkers.value.length > 0) {
      bankMarkers.value.forEach((marker) => marker.setMap(null))
      bankMarkers.value = []
    }
  }
})
watch(hospital, (newHospital, oldHospital) => {
  if (hospital.value) {
    var ps = new kakao.maps.services.Places(map)
    ps.categorySearch('HP8', (data, status, pagination) => {
      if (status === kakao.maps.services.Status.OK) {
        const imgSrc = '/src/assets/marker/hospitalMarker.png'
        const imgSize = new kakao.maps.Size(40, 45)
        const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize)
        for (var i = 0; i < data.length; i++) {
          var marker = new kakao.maps.Marker({
            map: map,
            position: new kakao.maps.LatLng(data[i].y, data[i].x),
            image: markerImage
          })
          hospitalMarkers.value.push(marker)
        }
      }
    })
  } else {
    if (hospitalMarkers.value.length > 0) {
      hospitalMarkers.value.forEach((marker) => marker.setMap(null))
      hospitalMarkers.value = []
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
      const imgSrc = '/src/assets/marker/buildingMarker.png'
      const imgSize = new kakao.maps.Size(40, 50)
      const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize)
      markers.value = []
      apartMarker.value.forEach((info) => {
        var markerPosition = new kakao.maps.LatLng(info.lat, info.lng)

        const marker = new kakao.maps.Marker({
          map: map,
          position: markerPosition,
          title: info.apartmentName,
          clickable: true,
          removable: true,
          image: markerImage
        })

        markers.value.push(marker)

        var content =
          '<div class="wrap">' +
          '    <div class="info">' +
          '        <div class="title">' +
          '            카카오 스페이스닷원' +
          '            <div class="close" onclick="closeOverlay()" title="닫기"></div>' +
          '        </div>' +
          '        <div class="body">' +
          '            <div class="img">' +
          '                <img src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/thumnail.png" width="73" height="70">' +
          '           </div>' +
          '            <div class="desc">' +
          '                <div class="ellipsis">제주특별자치도 제주시 첨단로 242</div>' +
          '                <div class="jibun ellipsis">(우) 63309 (지번) 영평동 2181</div>' +
          '                <div><a href="https://www.kakaocorp.com/main" target="_blank" class="link">홈페이지</a></div>' +
          '            </div>' +
          '        </div>' +
          '    </div>' +
          '</div>'
        var overlay = new kakao.maps.CustomOverlay({
          content: content,
          map: map,
          position: marker.getPosition()
        })
        // 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
        kakao.maps.event.addListener(marker, 'click', function () {
          // 마커 위에 커스텀오버레이를 표시합니다
          overlay.setMap(map)
        })
      })

      map.setLevel(3)
      map.panTo(moveLatLon)
    }
  },
  { deep: true }
)

const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null))
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
.wrap {
  position: absolute;
  left: 0;
  bottom: 40px;
  width: 288px;
  height: 132px;
  margin-left: -144px;
  text-align: left;
  overflow: hidden;
  font-size: 12px;
  font-family: 'Malgun Gothic', dotum, '돋움', sans-serif;
  line-height: 1.5;
}
.wrap * {
  padding: 0;
  margin: 0;
}
.wrap .info {
  width: 286px;
  height: 120px;
  border-radius: 5px;
  border-bottom: 2px solid #ccc;
  border-right: 1px solid #ccc;
  overflow: hidden;
  background: #fff;
}
.wrap .info:nth-child(1) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.info .title {
  padding: 5px 0 0 10px;
  height: 30px;
  background: #eee;
  border-bottom: 1px solid #ddd;
  font-size: 18px;
  font-weight: bold;
}
.info .close {
  position: absolute;
  top: 10px;
  right: 10px;
  color: #888;
  width: 17px;
  height: 17px;
  background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png');
}
.info .close:hover {
  cursor: pointer;
}
.info .body {
  position: relative;
  overflow: hidden;
}
.info .desc {
  position: relative;
  margin: 13px 0 0 90px;
  height: 75px;
}
.desc .ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.desc .jibun {
  font-size: 11px;
  color: #888;
  margin-top: -2px;
}
.info .img {
  position: absolute;
  top: 6px;
  left: 5px;
  width: 73px;
  height: 71px;
  border: 1px solid #ddd;
  color: #888;
  overflow: hidden;
}
.info:after {
  content: '';
  position: absolute;
  margin-left: -12px;
  left: 50%;
  bottom: 0;
  width: 22px;
  height: 12px;
  background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png');
}
.info .link {
  color: #5085bb;
}
</style>
