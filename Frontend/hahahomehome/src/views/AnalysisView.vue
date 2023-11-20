<script setup>
import TheHeading from '../components/common/TheHeading.vue'
import TheNationalMap from '../components/apart/TheNationalMap.vue'
import TheWordCloud from '../components/info/TheWordCloud.vue'
import { getPsyIndexList } from '@/api/info'
import { ref, onMounted, watch } from 'vue'

const psyIndexList = ref([])
// 초기값은 배열에서 가장 최근의 날짜로 설정
const selectedDate = ref()

const filteredPsyIndex = ref([])

onMounted(() => {
  getPsyIndexList(
    ({ data }) => {
      console.log('psyIndexdata == ', data)
      // date 속성을 Year-Month 형식으로 변경하여 저장
      psyIndexList.value = data.map((item) => ({
        ...item,
        date: formatYearMonth(new Date(item.date))
      }))
      console.log('psyIndex data == ', psyIndexList.value)
      selectedDate.value = getLatestDate()
      onSelectedDateChange()
    },
    (error) => {
      console.log('psy index error', error)
    }
  )
})

// Year-Month 형식으로 날짜를 포맷하는 함수
const formatYearMonth = (date) => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1 // 월은 0부터 시작하므로 1을 더합니다.

  // 두 자리로 표현하기 위해 padStart 함수 사용
  const formattedMonth = month.toString().padStart(2, '0')

  return `${year}-${formattedMonth}`
}

// 최근 날짜 가져오기
function getLatestDate() {
  if (psyIndexList.value.length > 0) {
    const latestDate = psyIndexList.value[psyIndexList.value.length - 1].date
    return latestDate
  }
}

// watch를 사용하여 selectedDate가 변경될 때마다 호출될 함수 정의
const onSelectedDateChange = () => {
  // 선택된 날짜에 해당하는 psyIndexList 배열을 가져오기
  filteredPsyIndex.value = psyIndexList.value.filter((item) => item.date === selectedDate.value)

  // TheNationalMap 컴포넌트에 전달
  // 만약 배열 전체가 아니라 첫 번째 요소만 전달하려면 [0]을 사용
  TheNationalMap.props.psyIndex = filteredPsyIndex.value[0]
}

// watch를 사용하여 selectedDate의 변경을 감지하고 함수 호출
watch(selectedDate, onSelectedDateChange)
</script>

<template>
  <TheHeading />
  <div class="analysis">
    <select v-model="selectedDate">
      <option v-for="psyIndex in psyIndexList" :key="psyIndex.date" :value="psyIndex.date">
        {{ psyIndex.date }}
      </option>
    </select>
    <TheNationalMap :psyIndex="filteredPsyIndex" />
    <TheWordCloud />
  </div>
</template>

<style scoped>
.analysis {
  padding-top: 65px;
}
</style>
