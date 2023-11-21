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
  <div class="basic-container">
    <div class="basic-info">
      <div class="basic-info-title d-flex align-items-center">
        <h1 class="name">{{ apartDetail.apartmentName }}</h1>
        <TheCheckList :aptcode="props.apartDetail.aptCode" />
      </div>
      <div class="detail">
        <div>
          주소 : {{ apartDetail.dong }} {{ apartDetail.roadName }} {{ apartDetail.apartmentName }}
        </div>
        <div>건축 연도 : {{ apartDetail.buildYear }}년</div>
      </div>
    </div>
  </div>
  <TheCalculator />
</template>

<style scoped>
.basic-container {
  width: 83%;
}
.basic-info {
  border-radius: 1rem;
  box-shadow: 3px 3px 3px 3px gray;
  padding: 1rem;
  margin: 1rem;

  .basic-info-title {
    .name {
      font-size: 70px;
      margin-right: 10px;
    }
  }
  .detail {
    font-size: 25px;
    margin-top: 15px;
  }
}
</style>
