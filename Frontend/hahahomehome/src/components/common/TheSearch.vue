<script setup></script>

<template>
  <v-select
    label="시도 선택"
    :items="['California', 'Colorado', 'Florida', 'Georgia', 'Texas', 'Wyoming']"
    variant="solo"
  ></v-select>
  <v-select
    label="구군 선택"
    :items="['California', 'Colorado', 'Florida', 'Georgia', 'Texas', 'Wyoming']"
    variant="solo"
  ></v-select>
  <v-select
    label="동 선택"
    :items="['California', 'Colorado', 'Florida', 'Georgia', 'Texas', 'Wyoming']"
    variant="solo"
  ></v-select>
  <button style="background-color: white">검색 버튼</button>
</template>

<style scoped>
.v-select {
  width: 8rem;
  margin: 2rem;
}
</style>
