<script setup>
import { ref,reactive } from 'vue'
import { useRoute } from 'vue-router'
import {writeChecklist} from '@/api/checklist'
const route=useRoute()
const apartcode=route.params.apartcode;

const tickLabels = {
  0: '나쁨',
  1: '약간 나쁨',
  2: '보통',
  3: '약간 좋음',
  4: '좋음'
}
const scores =[ref(2),ref(2),ref(2),ref(2),ref(2),ref(2),ref(2),ref(2),ref(2),ref(2),ref(2)]
const description=ref('')

const postChecklist = () => {
  let score = ''
  for (var scoreIdx = 0; scoreIdx <= 10; scoreIdx++) {
    score += scores[scoreIdx].value + ','
  }
  const newChecklist={
    aptCode:apartcode,
    userId:'',
    score:score,
    description:description.value
  }
  console.log('write checklist')
  // 저장된 값을 DB에 저장한다.
  // axios post
  writeChecklist(
    newChecklist,
    (response)=>{
      console.log(response)
      alert('체크리스트 등록 성공!')
      window.close()
    },
    (error)=>{
      console.log(error)
      alert('체크리스트 등록 실패!')
    }
  )
}

</script>

<template>
  <div class="check-list p-2">
    <div class="title mb-15">
      <h1>매물 체크리스트</h1>
    </div>
    <div class="content">
      <div class="about-outdoor mb-15">
        <h3 class="mb-5">건물 내/외부</h3>
        <div class="question">
          <p>1. 관리실이 있나요</p>
          <v-slider
            class="score-slider"
            :ticks="tickLabels"
            :max="4"
            v-model="scores[0].value"
            show-ticks="always"
            step="1"
            tick-size="3"
          ></v-slider>
        </div>
        <div class="question">
          <p>2. 주 출입구에 방범시설이 있나요</p>
          <v-slider
            class="score-slider"
            :ticks="tickLabels"
            :max="4"
            v-model="scores[1].value"
            show-ticks="always"
            step="1"
            tick-size="3"
          ></v-slider>
        </div>
        <div class="question">
          <p>3. CCTV가 있나요</p>
          <v-slider
            class="score-slider"
            :ticks="tickLabels"
            :max="4"
            v-model="scores[2].value"
            show-ticks="always"
            step="1"
            tick-size="3"
          ></v-slider>
        </div>
        <div class="question">
          <p>4. 주변에 편의시설이 있나요</p>
          <v-slider
            class="score-slider"
            :ticks="tickLabels"
            :max="4"
            v-model="scores[3].value"
            show-ticks="always"
            step="1"
            tick-size="3"
          ></v-slider>
        </div>
      </div>
      <div class="about-indoor mb-10">
        <h3>집안 체크</h3>
        <div class="question">
          <p>1. 현관의 잠금장치가 잘 되어 있나요</p>
          <v-slider
            class="score-slider"
            :ticks="tickLabels"
            :max="4"
            v-model="scores[4].value"
            show-ticks="always"
            step="1"
            tick-size="3"
          ></v-slider>
        </div>
        <div class="question">
          <p>2. 수납공간은 넉넉한가요</p>
          <v-slider
            class="score-slider"
            :ticks="tickLabels"
            :max="4"
            v-model="scores[5].value"
            show-ticks="always"
            step="1"
            tick-size="3"
          ></v-slider>
        </div>
        <div class="question">
          <p>3. 창문의 크기 및 위치, 채광상태는 좋은가요</p>
          <v-slider
            class="score-slider"
            :ticks="tickLabels"
            :max="4"
            v-model="scores[6].value"
            show-ticks="always"
            step="1"
            tick-size="3"
          ></v-slider>
        </div>
        <div class="question">
          <p>4. 에어컨 근처나 방구석에 곰팡이가 있나요</p>
          <v-slider
            class="score-slider"
            :ticks="tickLabels"
            :max="4"
            v-model="scores[7].value"
            show-ticks="always"
            step="1"
            tick-size="3"
          ></v-slider>
        </div>
        <div class="question">
          <p>5. 창문마다 방충망과 방범창이 있나요</p>
          <v-slider
            class="score-slider"
            :ticks="tickLabels"
            :max="4"
            v-model="scores[8].value"
            show-ticks="always"
            step="1"
            tick-size="3"
          ></v-slider>
        </div>
        <div class="question">
          <p>6. 난방은 잘 되어 있나요</p>
          <v-slider
            class="score-slider"
            :ticks="tickLabels"
            :max="4"
            v-model="scores[9].value"
            show-ticks="always"
            step="1"
            tick-size="3"
          ></v-slider>
        </div>
        <div class="question">
          <p>7. 수압 및 배수상태는 양호한가요</p>
          <v-slider
            class="score-slider"
            :ticks="tickLabels"
            :max="4"
            v-model="scores[10].value"
            show-ticks="always"
            step="1"
            tick-size="3"
          ></v-slider>
        </div>
      </div>
      <div class="specific-content">
        <h3>특이 사항</h3>
        <div class="question">
          <v-textarea
            class="text-area"
            variant="filled"
            label="파손된 가구 등 특이사항을 작성해주세요"
            v-model="description"
            rows="4"
            row-height="30"
            shaped
          ></v-textarea>
        </div>
      </div>
    </div>
    <div class="d-flex justify-center">
      <v-btn @click="postChecklist">저장</v-btn>
    </div>
  </div>
</template>

<style scoped>
.content {
  width: 45rem;
}
.question {
  margin: 2rem 0;
}
.score-slider {
  width: 35rem;
}
.text-area {
  width: 36rem;
}
</style>
