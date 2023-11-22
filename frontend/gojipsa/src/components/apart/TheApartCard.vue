<script setup>
const props = defineProps({
  aptCode: String,
  apartName: String,
  year: String,
  dong: String,
  roadName: String,
  jibun: String,
  lat: String,
  lng: String
})
</script>

<template>
  <div>
    <v-card class="mx-auto apart-card m-4" width="90%" prepend-icon="mdi-home" variant="elevated">
      <template v-slot:title> {{ apartName }} </template>
      <v-card-subtitle class="m-1"
        >건물 위치 : {{ dong }} {{ roadName }} {{ jibun }}</v-card-subtitle
      >
      <v-card-subtitle class="ms-1 mb-5">건축 연도 : {{ year }}</v-card-subtitle>
    </v-card>
  </div>
</template>

<style scoped>
.apart-desc {
  color: black;
}
.apart-price {
  color: red;
  font-size: large;
}
.like-btn {
  position: relative;
  top: -165px;
  left: 600px;
}
</style>
