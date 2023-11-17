<script setup>
import ToggleFavorite from '../common/ToggleFavorite.vue'
import TheCalculator from './TheCalculator.vue'
import TheCheckList from './TheCheckList.vue'
import { ref } from 'vue'

const props = defineProps({
  apartDetail: Object
})
</script>

<template>
  <div class="container">
    <h1 class="name">{{ apartDetail.apartmentName }}</h1>
    <TheCheckList />
  </div>
  <div class="detail">
    <div>
      주소 : {{ apartDetail.dong }} {{ apartDetail.roadName }} {{ apartDetail.apartmentName }}
    </div>
    <div>건축년도 : {{ apartDetail.buildYear }}년</div>
    <div>가격추이 :</div>
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
