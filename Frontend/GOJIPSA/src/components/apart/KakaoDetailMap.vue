<template>
  <div id="container" class="row">
    <div id="mapWrapper">
      <div id="map" class="col-md-6"></div>
      <div id="roadviewControl" @click="setRoadviewRoad"></div>
    </div>
    <div id="rvWrapper">
      <div id="roadview" class="col-md-6"></div>
      <!-- <div id="close" title="로드뷰닫기" @click="closeRoadview"><span class="img"></span></div> -->
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
var overlayOn = false
let map
let rv
let rvClient

let container
var marker

const props = defineProps({
  lat: String,
  lng: String,
  apartmentName: String
})
onMounted(() => {
  container = document.getElementById('container')

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
  var mapCenter = new kakao.maps.LatLng(props.lat, props.lng)

  const mapContainer = document.getElementById('map')
  const options = {
    center: mapCenter,
    level: 3
  }
  map = new kakao.maps.Map(mapContainer, options)

  const rvContainer = document.getElementById('roadview')

  // 로드뷰 객체를 생성합니다
  rv = new kakao.maps.Roadview(rvContainer)

  // 좌표로부터 로드뷰 파노라마 ID를 가져올 로드뷰 클라이언트 객체를 생성합니다
  rvClient = new kakao.maps.RoadviewClient()

  // 특정 위치의 좌표와 가까운 로드뷰의 panoId를 추출하여 로드뷰를 띄운다.
  rvClient.getNearestPanoId(mapCenter, 130, function (panoId) {
    rv.setPanoId(panoId, mapCenter) //panoId와 중심좌표를 통해 로드뷰 실행
  })

  // 마커를 생성한다.
  const imgSrc = '/src/assets/marker/buildingMarker.png'
  const imgSize = new kakao.maps.Size(40, 50)
  const buildingMarkerImage = new kakao.maps.MarkerImage(imgSrc, imgSize)

  const buildingMarker = new kakao.maps.Marker({
    map: map,
    position: mapCenter,
    title: props.apartmentName,
    clickable: true,
    removable: true,
    image: buildingMarkerImage
  })

  // 로드뷰에 좌표가 바뀌었을 때 발생하는 이벤트를 등록합니다
  kakao.maps.event.addListener(rv, 'position_changed', function () {
    // 현재 로드뷰의 위치 좌표를 얻어옵니다
    var rvPosition = rv.getPosition()

    // 지도의 중심을 현재 로드뷰의 위치로 설정합니다
    map.setCenter(rvPosition)

    // 지도 위에 로드뷰 도로 오버레이가 추가된 상태이면
    if (overlayOn) {
      // 마커의 위치를 현재 로드뷰의 위치로 설정합니다
      marker.setPosition(rvPosition)
    }
  })

  // 마커 이미지를 생성합니다
  var markImage = new kakao.maps.MarkerImage(
    'https://t1.daumcdn.net/localimg/localimages/07/2018/pc/roadview_minimap_wk_2018.png',
    new kakao.maps.Size(26, 46),
    {
      // 스프라이트 이미지를 사용합니다.
      // 스프라이트 이미지 전체의 크기를 지정하고
      spriteSize: new kakao.maps.Size(1666, 168),
      // 사용하고 싶은 영역의 좌상단 좌표를 입력합니다.
      // background-position으로 지정하는 값이며 부호는 반대입니다.
      spriteOrigin: new kakao.maps.Point(705, 114),
      offset: new kakao.maps.Point(13, 46)
    }
  )

  // 드래그가 가능한 마커를 생성합니다
  marker = new kakao.maps.Marker({
    image: markImage,
    position: mapCenter,
    draggable: true
  })

  // 마커에 dragend 이벤트를 등록합니다
  kakao.maps.event.addListener(marker, 'dragend', function (mouseEvent) {
    // 현재 마커가 놓인 자리의 좌표입니다
    var position = marker.getPosition()

    // 마커가 놓인 위치를 기준으로 로드뷰를 설정합니다
    toggleRoadview(position)
  })

  //지도에 클릭 이벤트를 등록합니다
  kakao.maps.event.addListener(map, 'click', function (mouseEvent) {
    // 지도 위에 로드뷰 도로 오버레이가 추가된 상태가 아니면 클릭이벤트를 무시합니다
    if (!overlayOn) {
      return
    }

    // 클릭한 위치의 좌표입니다
    var position = mouseEvent.latLng

    // 마커를 클릭한 위치로 옮깁니다
    marker.setPosition(position)

    // 클락한 위치를 기준으로 로드뷰를 설정합니다
    toggleRoadview(position)
  })
} // END : InitMap

const toggleRoadview = (position) => {
  rvClient.getNearestPanoId(position, 50, function (panoId) {
    // 파노라마 ID가 null 이면 로드뷰를 숨깁니다
    if (panoId === null) {
      toggleMapWrapper(true, position)
    } else {
      toggleMapWrapper(false, position)

      // panoId로 로드뷰를 설정합니다
      rv.setPanoId(panoId, position)
    }
  })
}

// 지도를 감싸고 있는 div의 크기를 조정하는 함수입니다
const toggleMapWrapper = (active, position) => {
  if (active) {
    // 지도를 감싸고 있는 div의 너비가 100%가 되도록 class를 변경합니다
    container.className = ''

    // 지도의 크기가 변경되었기 때문에 relayout 함수를 호출합니다
    map.relayout()

    // 지도의 너비가 변경될 때 지도중심을 입력받은 위치(position)로 설정합니다
    map.setCenter(position)
  } else {
    // 지도만 보여지고 있는 상태이면 지도의 너비가 50%가 되도록 class를 변경하여
    // 로드뷰가 함께 표시되게 합니다
    if (container.className.indexOf('view_roadview') === -1) {
      container.className = 'view_roadview'

      // 지도의 크기가 변경되었기 때문에 relayout 함수를 호출합니다
      map.relayout()

      // 지도의 너비가 변경될 때 지도중심을 입력받은 위치(position)로 설정합니다
      map.setCenter(position)
    }
  }
}

// 지도 위의 로드뷰 도로 오버레이를 추가,제거하는 함수입니다
const toggleOverlay = (active) => {
  if (active) {
    overlayOn = true

    // 지도 위에 로드뷰 도로 오버레이를 추가합니다
    map.addOverlayMapTypeId(kakao.maps.MapTypeId.ROADVIEW)

    // 지도 위에 마커를 표시합니다
    marker.setMap(map)

    // 마커의 위치를 지도 중심으로 설정합니다
    marker.setPosition(map.getCenter())

    // 로드뷰의 위치를 지도 중심으로 설정합니다
    toggleRoadview(map.getCenter())
  } else {
    overlayOn = false

    // 지도 위의 로드뷰 도로 오버레이를 제거합니다
    map.removeOverlayMapTypeId(kakao.maps.MapTypeId.ROADVIEW)

    // 지도 위의 마커를 제거합니다
    marker.setMap(null)
  }
}

// 지도 위의 로드뷰 버튼을 눌렀을 때 호출되는 함수입니다
const setRoadviewRoad = () => {
  var control = document.getElementById('roadviewControl')

  // 버튼이 눌린 상태가 아니면
  if (control.className.indexOf('active') === -1) {
    control.className = 'active'

    // 로드뷰 도로 오버레이가 보이게 합니다
    toggleOverlay(true)
  } else {
    control.className = ''

    // 로드뷰 도로 오버레이를 제거합니다
    toggleOverlay(false)
  }
}

// 로드뷰에서 X버튼을 눌렀을 때 로드뷰를 지도 뒤로 숨기는 함수입니다
const closeRoadview = () => {
  var position = marker.getPosition()
  toggleMapWrapper(true, position)
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
#container {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  width: 100%;
  height: 90%;
  margin-top: 1rem;
  font-size: 20px;
}

#mapWrapper {
  width: 100%;
  height: 50%;
  padding: 0;
  margin-bottom: 1rem;
}

#rvWrapper {
  height: 45%;
  width: 100%;
  padding: 0;
}

#map {
  width: 100%;
  height: 23rem;
}
#roadview {
  width: 100%;
  height: 23rem;
}

#roadviewControl {
  position: absolute;
  top: 5rem;
  right: 2rem;
  width: 42px;
  height: 42px;
  z-index: 1;
  cursor: pointer;
  background: url(https://t1.daumcdn.net/localimg/localimages/07/2018/pc/common/img_search.png) 0 -450px
    no-repeat;
}
#roadviewControl.active {
  background-position: 0 -350px;
}
#close {
  position: absolute;
  padding: 4px;
  top: 5.5rem;
  right: 2rem;
  cursor: pointer;
  background: #fff;
  border-radius: 4px;
  border: 1px solid #c8c8c8;
  box-shadow: 0px 1px #888;
  z-index: 1;
}
#close .img {
  display: block;
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/rv_close.png) no-repeat;
  width: 14px;
  height: 14px;
}
</style>
