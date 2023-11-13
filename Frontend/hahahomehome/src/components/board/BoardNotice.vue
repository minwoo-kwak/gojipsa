<script setup>
import { ref, onMounted, onBeforeMount, inject } from 'vue'
import { listNotice, modifyNotice, deleteNotice } from '@/api/board'
import PageNavigation from '@/components/common/PageNavigation.vue'
import { useRouter } from 'vue-router'

const router = useRouter();

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

/**
 * MainPage 들어올 때 list를 가지고 시작
 */
onMounted(() => {
  getNoticeList()
})

onBeforeMount(() => {
  // 권한 인증
  axios.get('/user/valid')
  .then((response) => {
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
 * @param {*} board_no 
 */
const onDeleteSubmit = (board_no) => {

 // 확인 메시지를 띄우고 사용자가 확인하면 삭제를 진행
  if (window.confirm('정말로 삭제하시겠습니까?')) {
    // local API 공지사항 삭제하기
    deleteNotice(
      board_no,
      (response) => {
        console.log(response);
        // 목록 다시 불러오기
        getNoticeList();
      },
      (error) => {
        console.log(error);
      }
    );
  } else {
    // 사용자가 확인을 취소하면 아무 동작도 하지 않음
    console.log('삭제가 취소되었습니다.');
  }
};




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
        <!-- Start : NoticeItem -->
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
          <!-- 수정할 경우 -->
          <div v-if="editNow">
            <v-dialog v-model="dialogStates[`dialog${notice.board_no}`]" width="auto">
              <v-card>
                <v-card-text>
                  <form
                    @submit="
                      onModifySubmit(notice.user_id, notice.board_no, notice.title, notice.content)
                    "
                  >
                    {{ notice.board_no }}. <input v-model.lazy="notice.title" /><br />
                    <textarea v-model="notice.content" rows="10" cols="100"></textarea>
                    <hr />
                    <div>작성일: {{ notice.register_time }}</div>
                    <v-btn color="primary" type="submit">수정하기</v-btn>
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
                <div v-if="isAdmin">
                  <v-btn color="success" v-model="editNow" @click="editNow = !editNow">수정</v-btn>
                  <v-btn color="danger" @click="onDeleteSubmit(notice.board_no)">삭제</v-btn>
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
    <PageNavigation
      :current-page="currentPage"
      :total-page="totalPage"
      @pageChange="onPageChange"
    />
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
