<script setup>
import TheHeading from '../components/common/TheHeading.vue'
import TheNationalMap from '../components/apart/TheNationalMap.vue'
import TheWordCloud from '../components/info/TheWordCloud.vue'
import { getPsyIndexList } from '@/api/info'
import { ref, onMounted, watch } from 'vue'
import ThePsyIndexChart from '../components/apart/ThePsyIndexChart.vue'

const psyIndexList = ref([])
// 초기값은 배열에서 가장 최근의 날짜로 설정
const selectedDate = ref()

const filteredPsyIndex = ref([])

const selectedCity = ref('whole')

const countryValues = [
  { label: '전국', value: 'whole' },
  { label: '수도권', value: 'capital' },
  { label: '서울', value: 'seoul' },
  { label: '인천', value: 'incheon' },
  { label: '경기', value: 'gyeonggi' },
  { label: '비수도권', value: 'non_capital' },
  { label: '부산', value: 'busan' },
  { label: '대구', value: 'daegu' },
  { label: '광주', value: 'gwangju' },
  { label: '대전', value: 'daejeon' },
  { label: '울산', value: 'ulsan' },
  { label: '세종', value: 'sejong' },
  { label: '강원', value: 'gangwon' },
  { label: '충북', value: 'chungbuk' },
  { label: '충남', value: 'chungnam' },
  { label: '전북', value: 'jeonbuk' },
  { label: '전남', value: 'jeonnam' },
  { label: '경북', value: 'gyeongbuk' },
  { label: '경남', value: 'gyeongnam' },
  { label: '제주', value: 'jeju' }
]

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

  // 이미지에 표시될 텍스트 배열
  const texts = [
    `부동산 심리지수 지도 설명 부동산 심리지수 (Real Estate Consumer Confidence Index)는  부동산 시장에 대한 소비자의 신뢰와 기대를 측정하는 지표입니다.
     이 지표는 소비자들이 현재 부동산 시장에 대해 어떻게 생각하고 있는지, 미래에 대한 전망은 어떠한지 등을 파악하기 위해 사용됩니다.
     주로 정부, 금융기관, 부동산 전문가, 투자자 등이 부동산 시장의 흐름을 예측하고 판단하는 데에 활용됩니다.
     부동산 심리지수는 다양한 경제적 지표 중 하나로, 주택가격의 상승 또는 하락, 투자 여건, 시장의 안정성 등을 반영합니다.
    이를 통해 부동산 시장의 건강 상태와 소비자들의 행동에 대한 통찰력을 얻을 수 있습니다.`,
    '부동산 심리지수 그래프 설명',
    '부동산 관련 워드클라우드 설명'
  ]

  // 텍스트를 자동 타이핑하는 함수
  // const typeText = (element, text, speed) => {
  //   let i = 0
  //   const interval = setInterval(() => {
  //     element.innerHTML += text[i]
  //     i++
  //     if (i % 20 == 0) {
  //       element.innerHTML += `<br>`
  //     }
  //     if (i === text.length) {
  //       clearInterval(interval)
  //     }
  //   }, speed)
  // }
  // 텍스트를 자동 타이핑하는 함수
  const typeText = (element, text, speed) => {
    let i = 0
    const container = document.createElement('div')
    element.appendChild(container)

    const interval = setInterval(() => {
      const char = document.createTextNode(text[i])
      container.appendChild(char)

      i++

      if (i % 20 === 0) {
        container.appendChild(document.createElement('br'))
      }

      if (i === text.length) {
        clearInterval(interval)
      }
    }, speed)
  }

  // 각 이미지에 대해 텍스트 적용
  const image1 = document.getElementById('bubble1')
  typeText(image1.querySelector('.typing-text'), texts[0], 25)

  // 페이지 스크롤 이벤트 감지
  window.addEventListener('scroll', () => {
    // 이미지가 화면에 나타나면 설명을 나타냄
    const rect = image1.getBoundingClientRect()
    if (rect.top >= 0 && rect.bottom <= window.innerHeight) {
      image1.style.opacity = 1
    } else {
      image1.style.opacity = 0
    }
  })
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
    <div class="map-container">
      <div class="image-container">
        <img
          src="@/assets/img/logo.png"
          class="head"
          style="width: 15rem; height: 15rem"
          alt="logo"
        />
        <div class="description-bubble1" id="bubble1">
          <p class="typing-text"></p>
        </div>
      </div>
      <v-card class="map-card rounded-xl" title="부동산 소비자 심리지수" elevation="16" hover>
        <div class="selectBox">
          <label for="selectDate">날짜 :</label>
          <select id="selectDate" v-model="selectedDate" class="form-select">
            <option v-for="psyIndex in psyIndexList" :key="psyIndex.date" :value="psyIndex.date">
              {{ psyIndex.date }}
            </option>
          </select>
        </div>
        <TheNationalMap :psyIndex="filteredPsyIndex" />
      </v-card>
    </div>
    <div class="chart-container">
      <v-card class="chart-card rounded-xl" title="부동산 소비자 심리지수" elevation="16" hover>
        <select v-model="selectedCity" class="form-select">
          <option v-for="country in countryValues" :key="country.label" :value="country.value">
            {{ country.label }}
          </option>
        </select>
        <ThePsyIndexChart
          :psyIndexList="psyIndexList"
          :selectedCity="selectedCity"
          :key="selectedCity"
        />
      </v-card>
      <img
        src="@/assets/img/logo.png"
        class="head"
        style="width: 15rem; height: 15rem"
        alt="logo"
      />
    </div>
    <div class="cloud-container">
      <img
        src="@/assets/img/logo.png"
        class="head"
        style="width: 15rem; height: 15rem"
        alt="logo"
      />
      <v-card class="cloud-card rounded-xl" title="워드클라우드" elevation="16" hover>
        <TheWordCloud />
      </v-card>
    </div>
  </div>
</template>

<style scoped>
@keyframes headShake {
  0% {
    transform: rotate(0deg);
  }
  25% {
    transform: rotate(-15deg);
  }
  50% {
    transform: rotate(15deg);
  }
  75% {
    transform: rotate(-15deg);
  }
  100% {
    transform: rotate(0deg);
  }
}

.head {
  /* width: 100px; */
  /* height: 100px; */
  /* margin: 50px auto; */
  animation: headShake 2s ease-in-out infinite;
  transform-origin: bottom center; /* 머리의 회전 중심을 설정 */
}

.typing-text {
  overflow: hidden;
  white-space: nowrap;
  border-right: 4px solid white; /* 커서 효과를 나타내기 위한 선 */
  font-size: 1.5rem;
  margin: 0;
  padding: 20px;
}

.description-bubble1 {
  position: absolute;
  background-color: rgba(255, 255, 255, 0.8); /* 투명한 흰색 배경 */
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0px 0px 10px 0px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
  opacity: 1; /* 초기에 투명하게 설정 */
  transition: opacity 0.5s ease-in-out; /* 부드러운 투명도 전환을 위한 트랜지션 */
  text-align: center;
}
.description-bubble {
  position: absolute;
  background-color: rgba(255, 255, 255, 0.8); /* 투명한 흰색 배경 */
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0px 0px 10px 0px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
  opacity: 0; /* 초기에 투명하게 설정 */
  transition: opacity 0.5s ease-in-out; /* 부드러운 투명도 전환을 위한 트랜지션 */
  text-align: center;
}

.map-container {
  display: flex;
  justify-content: space-between;
}

.chart-container {
  display: flex;
}

.cloud-container {
  display: flex;
  justify-content: space-between;
}

.analysis {
  padding-top: 65px;
  width: 100%;
  background-color: #fafbfd;
}

.map-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 1rem;
  min-width: 40rem;
  width: 80rem;
  height: 80rem;
}

.chart-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  min-width: 40rem;
  margin: 1rem;
  padding: 1rem;
  width: 80rem;
  height: 40rem;
}

.cloud-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 80rem;
  margin: 1rem;
  height: 40rem;
}

.form-select {
  display: inline;
  width: 8rem;
}

.selectBox {
  display: flex;
  flex-direction: row;
  align-items: center;
}
</style>
