<script setup>
import { Chart, Grid, Line, Tooltip } from 'vue3-charts'
import { ref, onMounted } from 'vue'
import { getApartDealFromLocalAPI } from '@/api/apartment'

const props = defineProps({
  aptCode: String
})

onMounted(() => {
  getApartDealFromLocalAPI(
    props.aptCode,
    ({ data }) => {
      console.log('data == ', data)
      dataList.value = data.map((element) => {
        let preProcessed = {
          day: `${element.dealYear}.${element.dealMonth}.${element.dealDay}`,
          // 콤마 제거
          dealAmount: Number(element.dealAmount.replace(/,/g, ''))
        }
        return preProcessed
      })
      console.log('dataList == ', dataList.value)
    },
    (error) => {
      console.log('아파트 거래 에러', error)
    }
  )
})

const dataList = ref([])
</script>

<template>
  <div class="deal-amount-chart-container">
    <p>거래 내역 그래프 (단위:천만원)</p>
    <Chart
      class="mb-5"
      :size="{ width: 900, height: 400 }"
      :data="dataList"
      :margin="margin"
      :direction="'horizontal'"
    >
      <template #layers>
        <Line :data-keys="['day', 'dealAmount']" :line-style="{ fill: '#FFCD4B' }" :maxWidth="50" />
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

<style scoped>
.deal-amount-chart-container {
  margin: 1rem;
  border: 3px solid #979caa;
  border-radius: 1rem;
  padding: 2rem;
  width: 90%;
  font-size: 20px;
}
</style>
