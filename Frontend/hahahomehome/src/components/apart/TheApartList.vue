<script setup>
import TheSearch from '../common/TheSearch.vue'
import TheApartCard from './TheApartCard.vue'

const props = defineProps({
  apartData: Object
})
console.log(props)
</script>

<!--아파트 정보를 표시하는 사이드 바-->
<template>
  <div class="apart-info">
    <div class="search">
      <TheSearch />
    </div>
    <div class="search-list" v-for="apartInfo in apartData.data" :key="apartInfo.aptCode">
      <!--axios를 통해 얻어온 아파트를 보여준다(for문)-->
      <TheApartCard />
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
