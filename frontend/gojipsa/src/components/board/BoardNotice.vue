<script setup>
import { ref, onMounted, onBeforeMount, inject } from 'vue'
import { listNotice, modifyNotice, deleteNotice, writeNotice, updateHit } from '@/api/board'
import PageNavigation from '@/components/common/PageNavigation.vue'

// 공지사항 글 리스트
const notices = ref([])
// 현재 페이지
const currentPage = ref(1)
// 총 페이지 수
const totalPage = ref(0)

// 다이얼로그 상태를 저장하는 객체
const dialogStates = ref({})

const axios = inject('axios')

const isAdmin = ref(false)

const editNow = ref(false)

const writeNow = ref(false)

// 공지사항 제목과 내용을 담은 객체
const article = ref({
  title: '',
  content: ''
})

/**
 * Mount 되기 전 관리자인지 아닌지 권한 인증부터 합니다.
 */
onBeforeMount(() => {
  // 권한 인증
  axios.get('/user/valid').then((response) => {
    if (response.status === 202) {
      // 권한이 "관리자"인 경우
      console.log('사용자는 관리자입니다.')
      isAdmin.value = true
    } else if (response.status === 200) {
      // 권한이 "관리자"가 아닌 경우
      console.log('사용자는 권한이 없습니다.')
      isAdmin.value = false
    }
  })
})

/**
 * MainPage 들어올 때 list를 가지고 시작
 */
onMounted(() => {
  getNoticeList()
})

/**
 * 리스트 가져오기
 */
const getNoticeList = () => {
  listNotice(
    currentPage,
    // then
    ({ data }) => {
      notices.value = data.data

      // 페이지 네이션 정보 받아오기
      totalPage.value = data.pageInfo.totalPageCnt

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

/**
 * 공지사항 작성하기
 */
const onWriteSubmit = () => {
  console.log('공지사항 작성')
  console.log('article = ', article.value)
  writeNotice(
    article.value,
    (response) => {
      console.log(response)
    },
    (error) => {
      console.log(error)
    }
  )
}

/**
 * 공지사항 수정하는 메서드
 * @param {String} user_id
 * @param {String} board_no
 * @param {String} title
 * @param {String} content
 */
const onModifySubmit = (user_id, board_no, title, content) => {
  // 공지사항 수정 정보
  const article = {
    user_id: user_id,
    board_no: board_no,
    title: title,
    content: content
  }
  // local API 수정하기
  modifyNotice(
    article,
    (response) => {
      console.log(response)
    },
    (error) => {
      console.log(error)
    }
  )
}

/**
 * 공지사항 삭제하는 메서드
 * @param {number} board_no
 */
const onDeleteSubmit = (board_no) => {
  // 확인 메시지를 띄우고 사용자가 확인하면 삭제를 진행
  if (window.confirm('정말로 삭제하시겠습니까?')) {
    // local API 공지사항 삭제하기
    deleteNotice(
      board_no,
      (response) => {
        console.log(response)
        // 목록 다시 불러오기
        getNoticeList()
      },
      (error) => {
        console.log(error)
      }
    )
  } else {
    // 사용자가 확인을 취소하면 아무 동작도 하지 않음
    console.log('삭제가 취소되었습니다.')
  }
}

/**
 * 공지사항 클릭했을 시에 조회수 증가
 * @param {number} board_no
 */
async function onClickNotice(board_no) {
  await updateHit(board_no)

  dialogStates.value[`dialog${board_no}`] = true
}

/**
 * 페이지 네이션 : 해당 페이지로 이동
 * @param {*} page : 해당 페이지
 */
const onPageChange = (page) => {
  console.log(page + '번 페이지로 이동 준비 끝!!!')
  currentPage.value = page
  getNoticeList()
}
</script>

<template>
  <div class="d-flex align-items-center notice-container">
    <div class="d-flex align-items-center notice-img me-15">
      <p>
        고집사의<br />
        공지사항을<br />
        확인해보세요
      </p>
    </div>
    <div class="notice d-flex flex-column justify-content-between">
      <div class="notice-content">
        <h2 class="mb-10">
          공지사항
          <span v-if="isAdmin" class="mdi mdi-plus-box-outline" @click="writeNow = !writeNow">
            <v-dialog v-model="writeNow" width="auto">
              <v-card>
                <v-card-text>
                  <form @submit="onWriteSubmit()">
                    <label for="title">제목 : </label>
                    <input id="title" v-model="article.title" /><br />
                    <label for="content">내용 : </label><br />
                    <textarea
                      id="content"
                      v-model="article.content"
                      rows="10"
                      cols="100"
                    ></textarea>
                    <hr />
                    <v-btn color="primary" type="submit">작성하기</v-btn>
                  </form>
                </v-card-text>

                <v-card-actions>
                  <v-btn color="primary" block @click="writeNow = false">닫기</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog></span
          >
        </h2>
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
            <!-- Start : NoticeItem -->
            <tr
              v-for="notice in notices"
              :key="notice.board_no"
              @click="onClickNotice(notice.board_no)"
            >
              <th scope="row">{{ notice.board_no }}</th>
              <td>{{ notice.title }}</td>
              <td>{{ notice.user_id }}</td>
              <td>{{ notice.register_time }}</td>
              <td>{{ notice.hit }}</td>
              <!-- 수정할 경우 -->
              <div v-if="editNow">
                <v-dialog v-model="dialogStates[`dialog${notice.board_no}`]" width="auto">
                  <v-card>
                    <v-card-text>
                      <form
                        @submit="
                          onModifySubmit(
                            notice.user_id,
                            notice.board_no,
                            notice.title,
                            notice.content
                          )
                        "
                      >
                        {{ notice.board_no }}. <input v-model.lazy="notice.title" /><br />
                        <textarea v-model="notice.content" rows="10" cols="100"></textarea>
                        <hr />
                        <div class="d-flex justify-content-end align-items-center">
                          <div class="pe-4">작성일: {{ notice.register_time }}</div>
                          <v-btn color="indigo-lighten-2" type="submit">수정하기</v-btn>
                        </div>
                      </form>
                    </v-card-text>

                    <v-card-actions>
                      <v-btn
                        color="primary"
                        block
                        @click="dialogStates[`dialog${notice.board_no}`] = false"
                        >닫기</v-btn
                      >
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </div>
              <!-- 평상시 -->
              <div v-else>
                <v-dialog v-model="dialogStates[`dialog${notice.board_no}`]" width="720px">
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
                    <div v-if="isAdmin" class="d-flex justify-content-start ps-5">
                      <v-btn
                        color="indigo-lighten-2"
                        class="me-1"
                        v-model="editNow"
                        @click="editNow = !editNow"
                        >수정</v-btn
                      >
                      <v-btn
                        color="deep-purple-lighten-2"
                        outlined
                        @click="onDeleteSubmit(notice.board_no)"
                        >삭제</v-btn
                      >
                    </div>
                    <v-card-actions>
                      <v-btn
                        color="primary"
                        block
                        @click="dialogStates[`dialog${notice.board_no}`] = false"
                        >닫기</v-btn
                      >
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </div>
            </tr>
            <!-- End : NoticeItem -->
          </tbody>
        </table>
      </div>

      <PageNavigation
        :current-page="currentPage"
        :total-page="totalPage"
        @pageChange="onPageChange"
      />
    </div>
  </div>
</template>

<style scoped lang="scss">
.notice-container {
  height: 100vh;
  width: 100%;
  .notice-img {
    width: 50%;
    height: 65%;
    background-image: url('../../assets/img/main-apartment-2.jpg');
    background-size: cover;
    border-radius: 10%;
    margin: 1rem;
    font-size: 5.5rem;
    font-weight: bold;
    color: white;
    padding: 5rem;
  }
  .notice {
    height: 50%;
    width: 50%;

    .notice-content {
      padding: 0 3rem;
      td {
        font-size: 17px;
      }
    }
  }
}
</style>
