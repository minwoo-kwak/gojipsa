<script setup>
import ToggleFavorite from '../common/ToggleFavorite.vue'
</script>

<template>
  <div>
    <v-card class="mx-auto apart-card m-4" width="90%" prepend-icon="mdi-home" variant="elevated">
      <div></div>
      <template v-slot:title> apartmentName </template>
      <v-card-subtitle>경희궁길(roadName) 9(jibun)</v-card-subtitle>
      <v-card-subtitle>1 (floor) 층</v-card-subtitle>
      <v-card-subtitle>평수</v-card-subtitle>
      <v-card-text class="apart-price">평균 거래액: 18억</v-card-text>
      <ToggleFavorite class="like-btn" />
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
