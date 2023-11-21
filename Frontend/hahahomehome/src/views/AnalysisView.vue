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
    `부동산시장 소비심리지수는 가격과 거래에 대한 정보를/ 가지고 있으며, 0~200 사이의 값으로 나타냅니다./
     100보다 높으면 전월에 비해 가격이나 거래가 상승 또는 /증가 하였다는 응답자가 많음을 의미합니다.//`,
    '전국, 수도권, 비수도권별로/ 그리고 각 지역별로 부동산시장 소비심리지수의/ 연간 동향을 그래프로도 파악할 수 있습니다.',
    '국토연구원이 조사한 부동산시장/ 2023년 보고서에 대한 내용을/ 키워드별로 추출하여 /워드클라우드로 나타내었습니다'
  ]

  // 텍스트를 자동 타이핑하는 함수
  const typeText = (element, text, speed) => {
    let i = 0

    const interval = setInterval(() => {
      if (text[i] === '/') {
        element.appendChild(document.createElement('br'))
      } else {
        const char = document.createTextNode(text[i])
        element.appendChild(char)
      }

      i++

      if (i === text.length) {
        // Image 객체 생성
        const img = document.createElement('IMG')
        img.width = 600
        img.height = 250

        // src 속성에 파일 주소 지정
        img.src = 'src/assets/img/psyIndexWeather.gif' // Adjust the path as needed

        // 클래스 추가
        img.classList.add('animation-init')
        // 요소에 삽입
        element.appendChild(img)

        // 애니메이션 효과
        setTimeout(() => {
          img.classList.add('animation-fade')
        }, 30)

        clearInterval(interval)
      }
    }, speed)
  }
  // 텍스트를 자동 타이핑하는 함수
  const typeText2 = (element, text, speed) => {
    let i = 0

    const interval = setInterval(() => {
      if (text[i] === '/') {
        element.appendChild(document.createElement('br'))
      } else {
        const char = document.createTextNode(text[i])
        element.appendChild(char)
      }

      i++

      if (i === text.length) {
        clearInterval(interval)
      }
    }, speed)
  }

  // 각 이미지에 대해 텍스트 적용
  const image1 = document.getElementById('bubble1')
  typeText(image1.querySelector('#typing-text1'), texts[0], 25)
  // 각 이미지에 대해 텍스트 적용
  const image2 = document.getElementById('bubble2')
  typeText2(image2.querySelector('#typing-text2'), texts[1], 25)
  // 각 이미지에 대해 텍스트 적용
  const image3 = document.getElementById('bubble3')
  typeText2(image3.querySelector('#typing-text3'), texts[2], 25)

  // 페이지 스크롤 이벤트 감지
  window.addEventListener('scroll', () => {
    // 이미지가 화면에 나타나면 설명을 나타냄
    const rect1 = image1.getBoundingClientRect()
    if (rect1.top >= 0 && rect1.bottom <= window.innerHeight) {
      image1.style.opacity = 1
    } else {
      image1.style.opacity = 0
    }

    const rect2 = image2.getBoundingClientRect()
    if (rect2.top >= 0 && rect2.bottom <= window.innerHeight) {
      image2.style.opacity = 1
    } else {
      image2.style.opacity = 0
    }
    const rect3 = image3.getBoundingClientRect()
    if (rect3.top >= 0 && rect3.bottom <= window.innerHeight) {
      image3.style.opacity = 1
    } else {
      image3.style.opacity = 0
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
        <div id="bubble1">
          <p id="typing-text1" class="typing-text"></p>
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
      <div id="img-bubble2-container">
        <img
          src="@/assets/img/logo.png"
          class="head"
          style="width: 15rem; height: 15rem"
          alt="logo"
        />
        <div id="bubble2" class="description-bubble">
          <p id="typing-text2" class="typing-text"></p>
        </div>
      </div>
    </div>
    <div class="cloud-container">
      <div id="img-bubble3-container">
        <img src="@/assets/img/logo.png" style="width: 15rem; height: 15rem" alt="logo" id="jump" />
        <div id="bubble3" class="description-bubble">
          <p id="typing-text3" class="typing-text"></p>
        </div>
      </div>
      <v-card class="cloud-card rounded-xl" title="워드클라우드" elevation="16" hover>
        <TheWordCloud />
      </v-card>
    </div>
  </div>
</template>

<style scoped>
.animation-init {
  opacity: 0;
  transform: translateY(20px);
  transition:
    opacity 1s ease-in-out,
    transform 1s ease-in-out;
}

.animation-fade {
  opacity: 1;
  transform: translateY(0);
}

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

@keyframes ball {
  0% {
    top: -100px;
  }
  95% {
  }
  to {
    top: 100px;
    height: 200px;
  }
}

#jump {
  position: relative;
  top: 0;
  animation: ball 1s ease-in Infinite Alternate;
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
  width: 40rem;
  text-align: left;
}

#bubble1 {
  position: absolute;
  background-color: rgba(255, 255, 255, 0.8); /* 투명한 흰색 배경 */
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0px 0px 10px 0px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
  opacity: 1; /* 초기에 투명하게 설정 */
  transition: opacity 0.5s ease-in-out; /* 부드러운 투명도 전환을 위한 트랜지션 */
  text-align: center;
  width: 45rem;
  margin-left: 1rem;
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
#img-bubble2-container {
  display: flex;
  flex-direction: column;
}
#img-bubble3-container {
  display: flex;
  flex-direction: row;
  align-items: center;
}

#bubble2 {
  position: relative; /* 상대 위치 설정 */
  margin-top: 1rem; /* 이미지와의 간격 조절 */
  width: 45rem;
}
#bubble3 {
  position: relative; /* 상대 위치 설정 */
  margin-top: 1rem; /* 이미지와의 간격 조절 */
  width: 30rem;
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
