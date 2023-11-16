<script setup>
import MultiRangeSlider from 'multi-range-slider-vue'
import { ref } from 'vue'
import { storeToRefs } from 'pinia'
import { useFilterStore } from '../../stores/filter'

const filterStore = useFilterStore()
const { market, convenience, school, subway, bank, hospital } = storeToRefs(filterStore)
</script>

<template>
  <v-container fluid class="filter">
    <div class="category">
      <v-checkbox label="대형마트" color="success" v-model="market"></v-checkbox>
      <v-checkbox label="편의점" color="secondary" v-model="convenience"></v-checkbox>
      <v-checkbox label="학교" color="orange" v-model="school"></v-checkbox>
      <v-checkbox label="지하철역" color="info" v-model="subway"></v-checkbox>
      <v-checkbox label="은행" color="primary" v-model="bank"></v-checkbox>
      <v-checkbox label="병원" color="red" v-model="hospital"></v-checkbox>
    </div>
  </v-container>
</template>

<style lang="scss" scoped>
#price-unit {
  font-size: 0.7rem;
}

.filter-btn-group {
  width: 11rem;
  display: flex;
  justify-content: space-around;
  align-items: center;
  margin-left: 5px;
}
.filter {
  display: flex;
  flex-direction: column;
  position: fixed;
  padding: 3px;
  z-index: 2;
  margin: 1rem;
  width: 35rem;
  height: 4rem;
  background-color: white;
  border-radius: 10px;
  border: solid 1px black;
  .category {
    display: flex;
  }
}
</style>
