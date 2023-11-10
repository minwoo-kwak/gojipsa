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

      // 동적으로 dialog 속성 추가
      notices.value.forEach((notice) => {
        const dialogKey = `dialog${notice.board_no}`
        dialogStates.value[dialogKey] = false
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
              <v-card-text>{{ notice.content }}</v-card-text>
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
        <tr>
          <th scope="row">1</th>
          <td>Mark</td>
          <td>Otto</td>
          <td>2023-00-00</td>
          <td>@mdo</td>
        </tr>
        <tr @click="dialog1 = true">
          <th scope="row">2</th>
          <td>
            <div class="text-center">
              공지사항 제목
              <v-dialog v-model="dialog1" width="auto">
                <v-card>
                  <v-card-text>
                    공지사항 내용내용내용 공지사항 내용내용내용 <br />
                    공지사항 내용내용내용 공지사항<br />
                    내용내용내용
                  </v-card-text>
                  <v-card-actions>
                    <v-btn color="primary" block @click="dialog1 = false">Close Dialog</v-btn>
                  </v-card-actions>
                </v-card>
              </v-dialog>
            </div>
          </td>
          <td>Thornton</td>
          <td>2023-00-00</td>
          <td>@fat</td>
        </tr>
        <tr>
          <th scope="row">3</th>
          <td colspan="2">Larry the Bird</td>
          <td>2023-00-00</td>
          <td>@twitter</td>
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
