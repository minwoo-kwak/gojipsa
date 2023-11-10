<script setup></script>

<template>
  <div class="calculator-wrap">
    <v-layout class="d-flex flex-column align-center">
      <h2>대출 이자 계산기</h2>
      <p>
        본 계산기의 원리금상환액은 실제 금융기관에 납부하셔야 하는 원리금과 다소 차이가 날 수
        있습니다.
      </p>
      <v-container fluid>
        <v-row>
          <v-col>
            <v-list-subheader>대출금</v-list-subheader>
          </v-col>

          <v-col>
            <v-text-field model-value="0" suffix="만원"></v-text-field>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-list-subheader>대출금리</v-list-subheader>
          </v-col>

          <v-col>
            <v-text-field model-value="10.00" prefix="연" suffix="%"></v-text-field>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-list-subheader>대출기간</v-list-subheader>
          </v-col>

          <v-col>
            <v-text-field model-value="0" suffix="개월"></v-text-field>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-list-subheader>거치기간</v-list-subheader>
          </v-col>

          <v-col>
            <v-text-field model-value="0" suffix="개월"></v-text-field>
          </v-col>
        </v-row>
      </v-container>
      <v-row> </v-row>
    </v-layout>
    <v-btn>결과 보기</v-btn>
  </div>
</template>

<style scoped>
.calculator-wrap {
  width: 100%;
  height: 100%;
}
</style>
