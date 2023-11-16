<script setup>
import TheSearch from '../common/TheSearch.vue'
import TheApartCard from './TheApartCard.vue'
import { useApartStore } from '../../stores/apart'
import { storeToRefs } from 'pinia'
import { getApartListAPI } from '@/api/apartment'
import { onMounted, ref, onUpdated, watch, toRaw, reactive } from 'vue'
import PageNavigation from '../common/PageNavigation.vue'

const props = defineProps({
  dongcode: String
})
const apartStore = useApartStore()
const { dongcode } = storeToRefs(apartStore)
const { apartMarker } = storeToRefs(apartStore)
// 현재 페이지 수
const currentPage = ref(1)
// 총 페이지 수
const totalPage = ref(0)
const apartList = reactive([])
//const pageInfo = reactive({})
watch(
  dongcode,
  (newCode, oldCode) => {
    //console.log(dongcode.value)
    getApartInfos()
  },
  { deep: true }
)
// api로부터 apartment 정보를 가져온다.
const getApartInfos = () => {
  getApartListAPI(
    {
      dongcode: dongcode.value,
      page: currentPage.value
    },
    ({ data }) => {
      console.log(data)
      apartList.value = []
      apartMarker.value = []
      for (var idx = 0; idx < data.data.length; idx++) {
        console.log(data.data[idx])
        apartList.value.push(data.data[idx])
        apartMarker.value.push(data.data[idx])
      }
      //console.log(apartList.value)
      totalPage.value = data.pageInfo.totalPageCnt
    }
  ),
    (err) => {
      console.log(err)
    }
}
const onPageChange = (page) => {
  currentPage.value = page
  getApartInfos()
}
</script>

<!--아파트 정보를 표시하는 사이드 바-->
<template>
  <div class="apart-info">
    <div class="search">
      <TheSearch />
    </div>

    <div class="apart-card-list">
      <div v-if="apartMarker.length == 0">아파트 거래 정보가 없습니다</div>

      <div v-for="apart in apartList.value" :key="apart.aptCode">
        <TheApartCard
          :aptCode="apart.aptCode"
          :apartName="apart.apartmentName"
          :year="apart.buildYear"
          :dong="apart.dong"
          :roadName="apart.roadName"
          :jibun="apart.jibun"
          :lat="apart.lat"
          :lng="apart.lng"
        />
      </div>
    </div>
    <PageNavigation
      :current-page="currentPage"
      :total-page="totalPage"
      @pageChange="onPageChange"
    />
  </div>
</template>

<style scoped lang="scss">
.search {
  display: flex;
  width: 42rem;
}
.search-list {
  overflow-y: scroll;
  height: 100%;
}
.apart-info {
  width: 45rem;
  height: 750px;
  .apart-card-list {
    height: 40rem;
    overflow-y: scroll;
  }
}
</style>
