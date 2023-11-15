<script setup>
import MultiRangeSlider from 'multi-range-slider-vue'
import { ref } from 'vue'
const barMin = ref(0.5)
const barMax = ref(100)
const barMinValue = ref(10)
const barMaxValue = ref(90)

function updateValue(e) {
  console.log(e)
  barMinValue.value = e.minValue
  barMaxValue.value = e.maxValue
}
</script>

<template>
  <div class="apart-filter">
    <div class="filter-btn-group">
      <v-btn color="light-green-lighten-4">편의점</v-btn>
      <v-btn color="light-green-lighten-4">경찰서</v-btn>
    </div>
  </div>
</template>

<style scoped>
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
.apart-filter {
  display: flex;
  justify-content: center;
  position: fixed;
  padding: 2px;
  z-index: 2;
  margin: 1rem;
  width: 15rem;
  height: 6rem;
  background-color: white;
  border-radius: 10px;
  border: solid 1px black;
}
</style>
