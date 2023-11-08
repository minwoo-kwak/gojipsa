<script setup>
import TheNotice from '../components/main/TheNotice.vue'
import TheNews from '../components/main/TheNews.vue'
</script>

<template>
  <div>
    <div class="main-img"></div>
    <div class="contents">
      <TheNotice />
      <TheNews />
    </div>
  </div>
</template>

<style scoped lang="scss">
.main-img {
  height: 75vh;
  width: 100vw;
  background-image: url('../assets/img/apartment.jpg');
  background-repeat: no-repeat;
  background-size: cover;
}

.contents {
  display: flex;
  justify-content: space-around;
}
</style>
