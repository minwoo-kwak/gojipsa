<script setup>
import TheSearch from '../common/TheSearch.vue'
import TheApartCard from './TheApartCard.vue'
import { useApartStore } from '../../stores/apart'
import { storeToRefs } from 'pinia'
import { getApartListAPI } from '@/api/apartment'
import { onMounted, ref, onUpdated, watch, toRaw } from 'vue'

const props = defineProps({
  dongcode: String
})
const apartStore = useApartStore()
const { dongcode } = storeToRefs(apartStore)
const page = ref(1)
const apartList = ref([])
watch(
  dongcode,
  (newCode, oldCode) => {
    console.log(dongcode.value)
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
      apartList.value = []
      console.log(data.data.length)
      console.log(apartList.value)
      for (var idx = 0; idx < data.data.length; idx++) {
        apartList.value.push(data.data[idx])
      }
      console.log(apartList.value)
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
      {{ apart.aptCode }}
      {{ apart.dongCode }}
    </div>
    <TheApartCard />
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
