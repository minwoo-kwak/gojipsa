<script setup>
import { ref, inject, onMounted, onBeforeMount } from 'vue'
import TheApartCard from '@/components/apart/TheApartCard.vue'
import PageNavigation from '@/components/common/PageNavigation.vue'
import { showAllChecklist, showDetailChecklist, deleteChecklist } from '@/api/checklist'
import { useUserStore } from '../../stores/user'
import { useRouter } from 'vue-router'

const userStore = useUserStore()
const router = useRouter()

const axios = inject('axios')
const userInfo = ref({
  userId: '',
  name: '',
  currentPassword: '',
  newPassword: '',
  password: ''
})

// 현재 페이지 수
const currentPage = ref(1)
// 총 페이지 수
const totalPage = ref(0)

const isEmpty = ref(false)

const checklistArray = ref([])
{
  axios.get('/user/mypage').then((response) => {
    userInfo.value = response.data

    console.log(sessionStorage.getItem('userStore'))
  })
}

onMounted(async () => {
  await getChecklistArray()
})

const getChecklistArray = () => {
  showAllChecklist(
    currentPage.value,
    ({ data }) => {
      console.log('getChecklist data : ', data)

      checklistArray.value = ref(data.data)

      if (checklistArray.value.value.length >= 1) {
        isEmpty.value = true
      }
      totalPage.value = data.pageInfo.totalPageCnt
    },
    (error) => {
      console.log('체크리스트 가져오는 데 에러발생', error)
    }
  )
}

function onApartCardClick(aptCode) {
  console.log('클릭 aptCode == ', aptCode)
  window.open(`/apart/detail/checklist/${aptCode}`, '_blank', 'width=700, height=720')
}

const onPageChange = (page) => {
  currentPage.value = page
  getChecklistArray()
}

const selectedChecklistIds = ref([]) // 선택된 체크리스트 아이디를 저장할 배열

// 삭제 버튼 클릭 시 호출되는 함수
const onDeleteButtonClick = async () => {
  // 선택된 체크리스트 아이디들을 서버로 전달하여 삭제
  await selectedChecklistIds.value.forEach((chlistId) => {
    deleteChecklist(
      chlistId,
      () => {
        console.log(chlistId, '삭제 성공')
        const idx = checklistArray.value.findIndex(function (checklist) {
          return checklist.chlistId === chlistId
        })
        if (idx > -1) {
          checklistArray.value.splice(idx, 1)
        }
      },
      (error) => {
        console.error('체크리스트 삭제 중 에러 발생', error)
      }
    )
  })

  // 삭제 성공 시, 다시 체크리스트 목록을 갱신
  getChecklistArray()

  router.push('/user/mypage')
  // 선택된 아이디들 초기화
  selectedChecklistIds.value = []
}

const onUpdateButtonClick = async () => {
  const isCurrentPasswordValid = await checkCurrentPassword() // 서버에 현재 비밀번호 유효성 검사 요청

  if (!isCurrentPasswordValid) {
    // 현재 비밀번호가 유효하지 않은 경우 처리
    alert('현재 비밀번호가 일치하지 않습니다.')
    return
  }

  // 유효성 검사가 통과한 경우 서버로 회원 정보 수정 요청 보내기

  if (userInfo.value.newPassword == null) {
    console.log('뉴 패스워드 없음!!!')
    userInfo.value.password = userInfo.value.currentPassword
  } else {
    console.log('뉴 패스워드 있다..')
    userInfo.value.password = userInfo.value.newPassword
  }

  console.log('userInfo.value.password', userInfo.value.password)

  const requestBody = ref({
    userId: userInfo.value.userId,
    name: userInfo.value.name,
    password: userInfo.value.password
  })

  try {
    const response = await axios.put('/user/update', requestBody.value)
    alert(response.data.message)

    // 회원 정보 수정 성공 시, 현재 비밀번호 초기화
    userInfo.value.currentPassword = ''
    userInfo.value.newPassword = ''

    // 추가로 필요한 로직 수행...
  } catch (error) {
    // 서버 요청 실패 시, 에러 처리
    console.error('회원 정보 수정 실패', error)
  }
}

const checkCurrentPassword = async () => {
  try {
    // 서버로 현재 비밀번호 유효성 검사 요청
    const response = await axios.post('/user/checkPassword', {
      userId: userInfo.value.userId,
      currentPassword: userInfo.value.currentPassword
    })

    return response.data.isValid
  } catch (error) {
    console.error('비밀번호 확인 중 에러 발생', error)
    return false // 에러가 발생한 경우 혹은 서버에서 유효성 검사 실패한 경우 false 반환
  }
}

const onDeleteAccountClick = async () => {
  const confirmed = confirm('정말로 탈퇴하시겠습니까?')

  if (!confirmed) {
    return
  }

  try {
    userStore.logout() //로그아웃
    router.push('/')
    await axios.delete('/user/delete') // 서버의 탈퇴 API 엔드포인트로 요청

    // 탈퇴 성공 시, 추가로 필요한 로직 수행
    alert('탈퇴되었습니다.')

    // 탈퇴 성공 시 로그인 페이지로 이동하는 예시
    // this.$router.push('/login');
  } catch (error) {
    // 서버 요청 실패 시, 에러 처리
    console.error('탈퇴 요청 실패', error)
    alert('탈퇴 요청에 실패했습니다. 다시 시도해주세요.')
  }
}
</script>

<template>
  <div id="mypage-container" class="d-flex align-items-center">
    <div
      id="myinfo-container"
      class="d-flex flex-column align-items-center justify-content-between"
    >
      <h1 class="mb-5 mt-5">내 정보 수정</h1>
      <v-form class="user-form d-flex flex-column align-items-center">
        <v-container grid-list-xs>
          <v-row class="mb-3"
            ><v-text-field label="아이디" v-model="userInfo.userId" readonly
          /></v-row>
          <v-row class="mb-3"><v-text-field label="이름" v-model="userInfo.name" /></v-row>
          <v-row class="mb-3"
            ><v-text-field label="현재 패스워드" v-model="userInfo.currentPassword"
          /></v-row>
          <v-row class="mb-3"
            ><v-text-field label="새로운 패스워드" v-model="userInfo.newPassword"
          /></v-row>
        </v-container>
      </v-form>
      <div class="mt-1 mb-3">
        <v-btn color="deep-purple-lighten-4" @click="onUpdateButtonClick" class="me-5"
          >수정하기</v-btn
        >
        <v-btn color="indigo-lighten-4" @click="onDeleteAccountClick">탈퇴하기</v-btn>
      </div>
    </div>
    <div id="checklist-container" class="d-flex flex-column align-items-center">
      <div id="checklist-contents" class="d-flex flex-column align-items-center">
        <div class="d-flex align-items-center">
          <h1 class="me-5 mt-5">체크리스트</h1>
          <v-btn color="red-lighten-4 mt-3" @click="onDeleteButtonClick"> 선택삭제</v-btn>
        </div>
        <div class="apart-card-list mb-3">
          <div v-if="isEmpty == false" class="d-flex flex-column align-items-center">
            <img
              src="../../assets/img/not-found-icon.png"
              style="width: 100px; height: 100px"
              alt="빈 박스"
            />
            <p class="mt-5">등록된 체크리스트가 없습니다</p>
          </div>
          <div class="checklist" v-for="checklist in checklistArray.value" :key="checklist.aptCode">
            <v-checkbox
              v-model="selectedChecklistIds"
              :value="checklist.chlistId"
              color="indigo"
            ></v-checkbox>
            <TheApartCard
              :aptCode="checklist.aptCode"
              :apartName="checklist.apartmentName"
              :year="checklist.buildYear"
              :dong="checklist.dong"
              :roadName="checklist.roadName"
              :jibun="checklist.jibun"
              :lat="''"
              :lng="''"
              @click="onApartCardClick(checklist.aptCode)"
              class="apart-card"
            />
          </div>
        </div>
        <PageNavigation
          :current-page="currentPage"
          :total-page="totalPage"
          @pageChange="onPageChange"
        />
      </div>
    </div>
  </div>
</template>

<style scoped>
#mypage-container {
  width: 100%;
  height: 100%;
}
#myinfo-container {
  width: 50%;
  height: 70%;
  border-radius: 5%;
  box-shadow: 5px 3px 5px 3px gray;
  margin: 0px 30px;
}
.user-form {
  width: 70%;
}

#checklist-container {
  width: 50%;
  border-radius: 5%;
  margin: 0px 30px;
  box-shadow: 5px 3px 5px 3px gray;
}

.checklist {
  display: flex;
  justify-content: center;
}
.apart-card {
  width: 20rem;
}
.v-checkbox-btn {
  display: inline;
}

.apart-card-list {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-wrap: wrap;
  height: 530px;
}
</style>
