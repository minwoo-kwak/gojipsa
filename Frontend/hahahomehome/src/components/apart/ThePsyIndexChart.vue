<script setup>
import { Chart, Grid, Line, Tooltip } from 'vue3-charts'
import { ref, onMounted, watch, computed } from 'vue'

const props = defineProps({
  psyIndexList: Array,
  selectedCity: String
})

let max = 0
let min = 200

const dataList = computed(() => {
  console.log('computed 실행 !!')
  return props.psyIndexList.map((element) => {
    if (max < element[props.selectedCity]) {
      max = element[props.selectedCity]
    }
    if (min > element[props.selectedCity]) {
      min = element[props.selectedCity]
    }
    let preProcessing = {
      date: element.date,
      psyIndex: element[props.selectedCity]
    }

    return preProcessing
  })
})
</script>

<template>
  <div>
    <Chart
      class="mb-5"
      :size="{ width: 1000, height: 500 }"
      :data="dataList"
      :margin="margin"
      :direction="'horizontal'"
      :axis="{
        primary: {
          type: 'band'
        },
        secondary: {
          domain: [min - 10, max + 10],
          type: 'linear',
          ticks: 2
        }
      }"
    >
      <template #layers>
        <Line :data-keys="['date', 'psyIndex']" :line-style="{ fill: '#FFCD4B' }" :maxWidth="50" />
      </template>
      <template #widgets>
        <Tooltip
          borderColor="#1F1717"
          :config="{
            name: { color: '#1F1717' },
            interest: { color: '#1F1717' }
          }"
          :style="{ fontSize: '10px' }"
          :hide-line="true"
        />
      </template>
    </Chart>
  </div>
</template>

<style scoped></style>
