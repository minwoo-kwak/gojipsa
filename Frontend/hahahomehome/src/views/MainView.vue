<script setup>
import BoardNotice from '../components/board/BoardNotice.vue'
import BoardNews from '../components/board/BoardNews.vue'
import TheSearch from '../components/common/TheSearch.vue'
import TheHeading from '../components/common/TheHeading.vue'
</script>

<template>
  <TheHeading />
  <div>
    <div class="img-box">
      <div class="main-img"></div>
    </div>
    <div class="contents">
      <BoardNotice />
      <BoardNews />
    </div>
  </div>
</template>

<style scoped lang="scss">
.contents {
  display: flex;
  justify-content: space-around;
  align-items: center; /* 가운데 정렬을 위해 추가 */
}

.img-box {
  .main-img {
    height: 75vh;
    width: 100vw;
    background-image: url('../assets/img/apartment.jpg');
    background-repeat: no-repeat;
    background-size: cover;
    position: relative;
  }
}
</style>
