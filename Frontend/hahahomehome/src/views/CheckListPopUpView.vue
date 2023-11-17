<script setup>
import { ref } from 'vue'

const tickLabels = {
  0: '나쁨',
  1: '약간 나쁨',
  2: '보통',
  3: '약간 좋음',
  4: '좋음'
}
const scores = {
  0: ref(2),
  1: ref(1),
  2: ref(2),
  3: ref(3),
  4: ref(2),
  5: ref(1),
  6: ref(0),
  7: ref(1),
  8: ref(2),
  9: ref(3),
  10: ref(2)
}

const saveScore = () => {
  let result = ''
  for (var scoreIdx = 0; scoreIdx <= 10; scoreIdx++) {
    result += scores[scoreIdx].value + ','
  }

  // 저장된 값을 DB에 저장한다.

  // 만약 사용자가 이미 작성한 글이 있는 경우(put)

  // 사용자가 새로 작성한 경우(post)
}
console.log(window.opener)
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
            :model-value="scores['0'].value"
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
            :model-value="scores['1'].value"
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
            :model-value="scores['2'].value"
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
            :model-value="scores['3'].value"
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
            :model-value="scores['4'].value"
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
            :model-value="scores['5'].value"
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
            :model-value="scores['6'].value"
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
            :model-value="scores['7'].value"
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
            :model-value="scores['8'].value"
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
            :model-value="scores['9'].value"
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
            :model-value="scores['10'].value"
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
            rows="4"
            row-height="30"
            shaped
          ></v-textarea>
        </div>
      </div>
    </div>
    <div class="d-flex justify-center">
      <v-btn @click="saveScore">저장</v-btn>
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
