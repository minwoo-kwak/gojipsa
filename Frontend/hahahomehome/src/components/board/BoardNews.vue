<script setup>
import { ref, onMounted, inject } from 'vue'
import { getNews } from '@/api/news'

const axios = inject('axios')

const newsList = ref('')

/**
 * MainPage 들어올 때 newslist를 가지고 시작
 */
onMounted(() => {
  getNewsList()
})

const getNewsList = () => {
  getNews(
    ({ data }) => {
      console.log('news == ', data)

      newsList.value = data

      // 이스케이프 태그 처리
      newsList.value.forEach((news) => {
        news.title = new DOMParser().parseFromString(news.title, 'text/html').body.textContent
      })
      console.log('newsList == ', newsList)
    },
    (error) => {
      console.log(error)
    }
  )
}

/**
 * 뉴스 창 띄우기
 * @param {string} url
 */
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
          <th scope="col">날짜</th>
          <th scope="col">조회수</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="news in newsList" :key="news.id" @click="navigateToNews(news.link)">
          <th scope="row">{{ news.id }}</th>
          <td v-html="news.title"></td>
          <td>{{ news.pubDate }}</td>
          <td>0</td>
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
