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
      <div class="search">
        <TheSearch />
      </div>
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
  .search {
    position: absolute; /* 절대 위치 설정 */
    top: 50%; /* 이미지 상단에서 중앙으로 이동 */
    left: 50%; /* 이미지 가운데로 이동 */
    transform: translate(-50%, 50%); /* 수평으로는 가운데, 수직으로는 아래로 이동 */
    display: flex; /* 가로 정렬을 위해 추가 */
    justify-content: space-between; /* 가로로 나란히 정렬 */
    align-items: center; /* 세로 가운데 정렬을 위해 추가 */
    width: 80%;
  }
}
</style>
