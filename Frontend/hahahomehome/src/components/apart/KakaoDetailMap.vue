<template>
  <div class="map-wrap">
    <TheFilter />
    <div id="map"></div>
    <TheCalculator />
  </div>
</template>

<script>
import TheFilter from './TheFilter.vue'
import TheCalculator from './TheCalculator.vue'
export default {
  name: 'KakaoMap',
  data() {
    return {
      markerPositions1: [
        [33.452278, 126.567803],
        [33.452671, 126.574792],
        [33.451744, 126.572441]
      ],
      markerPositions2: [
        [37.499590490909185, 127.0263723554437],
        [37.499427948430814, 127.02794423197847],
        [37.498553760499505, 127.02882598822454],
        [37.497625593121384, 127.02935713582038],
        [37.49629291770947, 127.02587362608637],
        [37.49754540521486, 127.02546694890695],
        [37.49646391248451, 127.02675574250912]
      ],
      markers: [],
      infowindow: null
    }
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap()
    } else {
      const script = document.createElement('script')
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap)
      script.src =
        '//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=' +
        import.meta.env.VITE_KAKAO_OPEN_API_JAVASCIPRT_KEY +
        '&libraries=clusterer,drawing,services'
      document.head.appendChild(script)
    }
  },
  methods: {
    initMap() {
      const container = document.getElementById('map')
      const options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 5
      }
      //지도 객체를 등록합니다.
      //지도 객체는 반응형 관리 대상이 아니므로 initMap에서 선언합니다.
      this.map = new kakao.maps.Map(container, options)
    }
  },
  components: { TheFilter, TheCalculator }
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
