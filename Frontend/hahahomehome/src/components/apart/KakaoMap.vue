<template>
  <div class="map-wrap">
    <TheFilter />
    <div id="map"></div>
    <TheCalculator />
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import TheFilter from './TheFilter.vue'
import TheCalculator from './TheCalculator.vue'

var map
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
