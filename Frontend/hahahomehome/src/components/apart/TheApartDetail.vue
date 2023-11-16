<script setup>
import ToggleFavorite from '../common/ToggleFavorite.vue'
import TheCalculator from './TheCalculator.vue'
</script>

<template>
  <div class="container">
    <h1 class="name">하하 아파트</h1>
    <v-btn prepend-icon="mdi-pencil-remove-outline" stacked>체크리스트</v-btn>
  </div>
  <div class="detail">
    <div>서울특별시 강남구 역삼로 5층</div>
    <div>거래일: 2023년 10월</div>
    <div>가격: 20억</div>
    <div>건축년도: 2023</div>
  </div>
  <TheCalculator />
</template>

<style scoped>
.container {
  display: flex;
  align-items: center;
  margin-top: 1rem;
  margin-left: 1rem;
}

.detail {
  display: flex;
  flex-direction: column;
  padding-left: 3rem;
  font-size: large;
}
.name {
  display: inline;
}
</style>
