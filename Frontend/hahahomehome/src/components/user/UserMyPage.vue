<script setup>
import { ref, inject, onMounted, onBeforeMount } from 'vue'
import TheApartCard from '@/components/apart/TheApartCard.vue'
import PageNavigation from '@/components/common/PageNavigation.vue'
import { showAllChecklist, showDetailChecklist, deleteChecklist } from '@/api/checklist'
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
      console.log('checklistArray.value', checklistArray.value)

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
      },
      (error) => {
        console.error('체크리스트 삭제 중 에러 발생', error)
      }
    )
  })

  // 삭제 성공 시, 다시 체크리스트 목록을 갱신
  getChecklistArray()
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
  if (userInfo.value.newPassword == '') {
    userInfo.value.password = userInfo.value.currentPassword
  } else {
    userInfo.value.password = userInfo.value.newPassword
  }

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
  <div id="myinfo">
    <v-form class="user-form">
      <v-container grid-list-xs>
        <v-row><v-text-field label="아이디" v-model="userInfo.userId" /></v-row>
        <v-row><v-text-field label="이름" v-model="userInfo.name" /></v-row>
        <v-row><v-text-field label="현재 패스워드" v-model="userInfo.currentPassword" /></v-row>
        <v-row><v-text-field label="새로운 패스워드" v-model="userInfo.newPassword" /></v-row>
      </v-container>
      <v-btn @click="onUpdateButtonClick">회원 정보 수정</v-btn>
      <v-btn @click="onDeleteAccountClick">탈퇴하기</v-btn>
    </v-form>
    <div id="checklist-container">
      <h1>체크리스트</h1>
      <v-btn @click="onDeleteButtonClick" prepend-icon="mdi-check-circle">
        <template v-slot:prepend> <v-icon color="danger"></v-icon> </template>삭제하기</v-btn
      >
      <div class="apart-card-list">
        <div v-if="checklistArray.length === 0">저장해놓은 찜이 없습니다.</div>

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
  <br />
</template>

<style scoped>
.user-form {
  min-height: 300px;
  min-width: 960px;
}
#myinfo {
  display: flex;
  align-items: center;
  flex-direction: column;
}

#checklist-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.checklist {
  display: flex;
  border: 1px solid gray;
}
.v-checkbox-btn {
  display: inline;
}

.apart-card-list {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-wrap: wrap;
  width: 80%;
}
</style>
