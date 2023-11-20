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
  <div class="d-flex algin-items-center justify-content-between news-container">
    <div class="news d-flex flex-column justify-content-between">
      <div class="news-content">
        <h2 class="mb-10">부동산 뉴스</h2>
        <table class="table table-hover">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">제목</th>
              <th scope="col">날짜</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="news in newsList" :key="news.id" @click="navigateToNews(news.link)">
              <!-- 5×(page−1) + news.id -->
              <th scope="row">{{ news.id + 5 * (currentPage - 1) }}</th>
              <td v-html="news.title"></td>
              <td>{{ news.pubDate }}</td>
            </tr>
          </tbody>
        </table>
      </div>

      <PageNavigation
        :current-page="currentPage"
        :total-page="totalPage"
        @pageChange="onPageChange"
      />
    </div>
    <div class="d-flex news-img me-15">
      <p>
        최근<br />
        부동산 뉴스를<br />
        확인해보세요
      </p>
    </div>
  </div>
</template>

<style scoped>
.news-container {
  height: 100vh;
  width: 100%;
  .news {
    height: 70%;
    padding: 6rem;
  }
  .news-img {
    width: 50%;
    height: 70%;
    background-color: #7685ad;
    border-radius: 10%;
    margin: 1rem;
    font-size: 6rem;
    font-weight: bold;
    color: white;
    padding: 5rem;
  }
}
</style>
