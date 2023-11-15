<script setup>
import TheSearch from '../common/TheSearch.vue'
import TheApartCard from './TheApartCard.vue'
import { useApartStore } from '../../stores/apart'
import { storeToRefs } from 'pinia'
import { getApartListAPI } from '@/api/apartment'
import { onMounted, ref, onUpdated, watch, toRaw, reactive } from 'vue'

const props = defineProps({
  dongcode: String
})
const apartStore = useApartStore()
const { dongcode } = storeToRefs(apartStore)
const page = ref(1)
const apartList = reactive([])
const pageInfo = reactive({})
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
      page: page.value
    },
    ({ data }) => {
      console.log(data)
      apartList.value = []
      //console.log(data.data.length)
      //console.log(apartList.value)
      for (var idx = 0; idx < data.data.length; idx++) {
        apartList.value.push(data.data[idx])
      }
      //console.log(apartList.value)
      pageInfo.value = data.pageInfo
      console.log(pageInfo.value)
    }
  ),
    (err) => {
      console.log(err)
    }
}
onMounted(() => {
  console.log('mounted')
})
</script>

<!--아파트 정보를 표시하는 사이드 바-->
<template>
  <div class="apart-info">
    <div class="search">
      <TheSearch />
    </div>

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
}
</style>
