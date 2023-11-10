<script setup>
import TheSearch from '../common/TheSearch.vue'
</script>

<!--아파트 정보를 표시하는 사이드 바-->
<template>
  <div class="apart-info">
    <div class="search">
      <TheSearch />
    </div>
  </div>
</template>

<style scoped lang="scss">
.search {
  display: flex;
  width: 42rem;
}
.apart-info {
  width: 45rem;
  height: 100%;
}
</style>
