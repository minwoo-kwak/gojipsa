<script setup>
import { ref, onMounted } from 'vue'
import { listNotice } from '@/api/board'

const notices = ref([])
const currentPage = ref(1)
const param = ref({
  pgno: currentPage.value,
  key: '',
  word: ''
})

/**
 * MainPage 들어올 때 list를 가지고 시작
 */
onMounted(() => {
  getNoticeList()
})

// 다이얼로그 상태를 저장하는 객체
const dialogStates = ref({})

/**
 * 리스트 가져오기
 */
const getNoticeList = () => {
  listNotice(
    param.value,
    ({ data }) => {
      console.log(data)
      notices.value = data.data
      console.log('notices = ', notices.value)

      // 데이터를 받아올 때마다 dialogStates 초기화
      dialogStates.value = {}

      notices.value.forEach((notice) => {
        // 동적으로 dialog 속성 추가
        const dialogKey = `dialog${notice.board_no}`
        dialogStates.value[dialogKey] = false

        // 날짜 변환
        notice.register_time = new Date(notice.register_time).toLocaleString()
      })
    },
    (error) => {
      console.log(error)
    }
  )
}
</script>

<template>
  <div class="notice">
    <h2>공지사항</h2>
    <table class="table table-hover">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">제목</th>
          <th scope="col">작성자</th>
          <th scope="col">작성일</th>
          <th scope="col">조회수</th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="notice in notices"
          :key="notice.board_no"
          @click="dialogStates[`dialog${notice.board_no}`] = true"
        >
          <th scope="row">{{ notice.board_no }}</th>
          <td>{{ notice.title }}</td>
          <td>{{ notice.user_id }}</td>
          <td>{{ notice.register_time }}</td>
          <td>{{ notice.hit }}</td>
          <v-dialog v-model="dialogStates[`dialog${notice.board_no}`]" width="auto">
            <v-card>
              <v-card-text>
                <h2>{{ notice.board_no }}. {{ notice.title }}</h2>
                <hr />
                <div>
                  {{ notice.content }}
                </div>
                <hr />
                <div>작성일: {{ notice.register_time }}</div>
              </v-card-text>
              <v-card-actions>
                <v-btn
                  color="primary"
                  block
                  @click="dialogStates[`dialog${notice.board_no}`] = false"
                  >Close Dialog</v-btn
                >
              </v-card-actions>
            </v-card>
          </v-dialog>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped lang="scss">
.notice {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 40%;
}
</style>
