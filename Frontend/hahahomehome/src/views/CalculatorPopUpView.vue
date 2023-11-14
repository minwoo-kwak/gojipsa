<script setup>
import { ref } from 'vue'
import { Chart, Grid, Bar, Tooltip } from 'vue3-charts'
const amount = ref(0) // 대출 금액
const interestRate = ref(0.0) // 연 이자
const duration = ref(0) // 대출 기간
const data = ref([])

const margin = ref({
  left: 0,
  top: 20,
  right: 20,
  bottom: 0
})

const calculate = () => {
  let resultInterest = []
  let totalPrice = Number(amount.value) * 10000
  let period = Number(duration.value)
  let interestPerYear = Number(interestRate)
  resultInterest.push({
    name: '원금균등상환',
    interest: updateMontlyRepayments(totalPrice, period)
  })
  resultInterest.push({
    name: '원리금균등상환',
    interest: equalRepaymentInterest(totalPrice, period)
  })
  resultInterest.push({ name: '원금만기일시상환', interest: straightInterest(totalPrice, period) })
  data.value = resultInterest
  showdesc.value = true
}

// 원금 균등 상환
const updateMontlyRepayments = (totalLoanPrice, repaymentsPeriodMonth) => {
  let result = 0
  for (var inRepayNum = 1; inRepayNum <= duration.value; inRepayNum++) {
    let repaymentsPerMonth = Math.floor(totalLoanPrice / repaymentsPeriodMonth)

    let repayedPrice = Number(repaymentsPerMonth * (inRepayNum - 1)) // 지금까지 납입한 원금

    let remainLoanPrice = Number(totalLoanPrice - repayedPrice)
    let interestPerYear = Number(remainLoanPrice * (interestRate.value * 0.01))

    let interestPerMonth = Number(interestPerYear / 12)

    let resultMontlyRepayments = Number(repaymentsPerMonth + interestPerMonth)

    result += interestPerMonth
  }
  return result
}

// 원리금균등상환
const equalRepaymentInterest = (totalLoanPrice, repaymentsPeriodMonth) => {
  let result = 0
  //let totalLoanPrice = Number(amount.value) * 10000
  let loanRatePerMonth = (Number(interestRate.value) * 0.01) / 12

  // 매달 납부 금액 공식 = {원금 * 월금리 * (1+월금리)^개월 수 }/(1+월금리)^개월수 -1
  // 공식의 분자 계산
  let formulaNumerator =
    totalLoanPrice * loanRatePerMonth * Math.pow(1 + loanRatePerMonth, repaymentsPeriodMonth)
  // 공식의 분모 계산
  let formulaDenominator = Math.pow(1 + loanRatePerMonth, repaymentsPeriodMonth) - 1

  // 매달 원리금 최종 계산
  let monthlyRepayments = formulaNumerator / formulaDenominator

  let remainLoanPrice = totalLoanPrice
  var interestCurMonth = 0
  var principalCurMonth = 0

  for (var num = 0; num < duration.value; num++) {
    // 회차 이자 계산
    interestCurMonth = remainLoanPrice * loanRatePerMonth

    // 회차 원금 계산
    principalCurMonth = monthlyRepayments - interestCurMonth
    remainLoanPrice -= principalCurMonth

    result += interestCurMonth
  }
  return result
}

// 원금만기일시상환
const straightInterest = (totalLoanPrice, repaymentsPeriodMonth) => {
  //let totalLoanPrice = Number(amount.value) * 10000
  let loanRateMonth = (Number(interestRate.value) * 0.01) / 12
  let interestPerMonth = totalLoanPrice * loanRateMonth
  return interestPerMonth * repaymentsPeriodMonth
}
</script>

<template>
  <div class="calculator-wrap">
    <v-layout class="d-flex flex-column align-center">
      <h2>대출 이자 계산기</h2>
      <p>
        본 계산기의 원리금상환액은 실제 금융기관에 납부하셔야 하는 원리금과 다소 차이가 날 수
        있습니다. <b>원</b>단위로 표시됩니다.
      </p>
      <v-container fluid>
        <v-row>
          <v-col>
            <v-list-subheader>대출금</v-list-subheader>
          </v-col>

          <v-col>
            <v-text-field v-model="amount" suffix="만원"></v-text-field>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-list-subheader>대출금리</v-list-subheader>
          </v-col>

          <v-col>
            <v-text-field v-model="interestRate" prefix="연" suffix="%"></v-text-field>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-list-subheader>대출기간</v-list-subheader>
          </v-col>

          <v-col>
            <v-text-field v-model="duration" suffix="개월"></v-text-field>
          </v-col>
        </v-row>
        <v-row>
          <v-col class="d-flex justify-space-around">
            <v-btn @click.prevent="calculate">결과 보기</v-btn>
          </v-col>
        </v-row>
      </v-container>
    </v-layout>

    <Chart
      class="mb-5"
      :size="{ width: 500, height: 400 }"
      :data="data"
      :margin="margin"
      :direction="'horizontal'"
    >
      <template #layers>
        <Bar :data-keys="['name', 'interest']" :bar-style="{ fill: '#FFCD4B' }" :maxWidth="50" />
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
.calculator-wrap {
  width: 100%;
  height: 100%;
}
</style>
