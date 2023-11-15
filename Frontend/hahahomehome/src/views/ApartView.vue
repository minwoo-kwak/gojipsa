<script setup>
import axios from 'axios'
import KakaoMap from '../components/apart/KakaoMap.vue'
import TheApartList from '../components/apart/TheApartList.vue'
import { onBeforeMount, onUpdated, ref, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import TheHeading from '../components/common/TheHeading.vue'
import { getApartListAPI } from '@/api/apartment'

// main page에서 들어온 매개변수
const route = useRoute()
const dongcode = ref(route.params.code)
const page = ref(1)

// api로부터 apartment 정보를 가져온다.
const getApartInfos = () => {
  getApartListAPI(
    {
      dongcode: dongcode.value,
      page: page.value
    },
    ({ data }) => {
      console.log(data.data)
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
    <TheApartList :code="dongcode" />
    <KakaoMap />
  </div>
</template>

<style scoped>
.apart-view {
  display: flex;
  padding-top: 65px;
}
</style>
