<script setup>
import { Chart, Grid, Line, Tooltip } from 'vue3-charts'
import { ref, onMounted, watch, computed } from 'vue'

const props = defineProps({
  psyIndexList: Array,
  selectedCity: String
})

// 차트 y 축 최대값을 받아오기 위해 0으로 설정(최소: 0)
let max = 0
// 차트 y 축 최솟값을 받아오기 위해 200으로 설정(최대: 200)
let min = 200

/**
 * 부동산 소비자심리지수 리스트를 받아옵니다.
 */
const dataList = computed(() => {
  console.log('computed 실행 !!')
  return props.psyIndexList.map((element) => {
    // 리스트를 돌면서 y축 최대값 설정
    if (max < element[props.selectedCity]) {
      max = element[props.selectedCity]
    }
    // 리스트를 돌면서 x축 최솟값 설정
    if (min > element[props.selectedCity]) {
      min = element[props.selectedCity]
    }
    // 차트에 넣기 위해 데이터를 전처리합니다.
    let preProcessing = {
      // x축: 날짜
      date: element.date,
      // y축: 심리지수
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
