<script setup>
import { ref, onMounted, inject } from 'vue'
import {getNews} from '@/api/news'

const axios = inject("axios");

const newsList = ref("");

/**
 * MainPage 들어올 때 newslist를 가지고 시작
 */
 onMounted(() => {
  getNewsList()
})

const getNewsList = () => {
  getNews(({data}) => {
    console.log("news == ", data);
    newsList.value = data.data;
  },
  (error) => {
    console.log(error);
  })
}

const navigateToNews = (url) => {
  window.open(url, '_blank')
}
</script>

<template>
  <div class="news">
    <h2>부동산 뉴스</h2>
    <table class="table table-hover">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">제목</th>
          <th scope="col">언론사</th>
          <th scope="col">날짜</th>
          <th scope="col">조회수</th>
        </tr>
      </thead>
      <tbody>
        <tr @click="navigateToNews('https://www.mk.co.kr/news/realestate/10870756')">
          <th scope="row">1</th>
          <td>뉴스 제목입니다.</td>
          <td>매일경제</td>
          <td>2023-11-09 17:02:41</td>
          <td>@mdo</td>
        </tr>
        <tr>
          <th scope="row">2</th>
          <td>Jacob</td>
          <td>Thornton</td>
          <td>2023-11-09 17:02:41</td>
          <td>@fat</td>
        </tr>
        <tr>
          <th scope="row">3</th>
          <td colspan="2">Larry the Bird</td>
          <td>2023-11-09 17:02:41</td>
          <td>@twitter</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.news {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 40%;
}
</style>
