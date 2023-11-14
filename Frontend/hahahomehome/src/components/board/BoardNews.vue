<script setup>
import { ref, onMounted, inject, computed } from 'vue'
import { getNews } from '@/api/news'
import PageNavigation from '@/components/common/PageNavigation.vue'

const axios = inject('axios')

// 뉴스 리스트
const newsList = ref(null)

// 현재 페이지
const currentPage = ref(1)
// 뉴스 시작 위치
const start = computed(() => {
  return currentPage.value * 5 - 4
})

// 총 페이지 수
const totalPage = ref(50)

/**
 * MainPage 들어올 때 newslist를 가지고 시작
 */
onMounted(() => {
  getNewsList()
})

const getNewsList = () => {
  getNews(
    start.value,
    ({ data }) => {
      console.log('news == ', data)

      newsList.value = data

      // 이스케이프 태그 처리
      newsList.value.forEach((news) => {
        news.title = new DOMParser().parseFromString(news.title, 'text/html').body.textContent
        news.pubDate = new Date(news.pubDate).toLocaleString()
      })
      console.log('newsList == ', newsList)
    },
    (error) => {
      console.log(error)
    }
  )
}

/**
 * 페이지 네이션 : 해당 페이지로 이동
 * @param {*} page : 해당 페이지
 */
const onPageChange = (page) => {
  console.log(page + '번 페이지로 이동 준비 끝!!!')
  currentPage.value = page
  getNewsList()
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
          <!-- 5×(page−1) + news.id -->
          <th scope="row">{{ news.id + 5 * (currentPage - 1) }}</th>
          <td v-html="news.title"></td>
          <td>{{ news.pubDate }}</td>
          <td>0</td>
        </tr>
      </tbody>
    </table>
    <PageNavigation
      :current-page="currentPage"
      :total-page="totalPage"
      @pageChange="onPageChange"
    />
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
