<script setup>
import { ref, inject } from 'vue'
import TheCheckList from '../apart/TheCheckList.vue'
import TheApartCard from '@/components/apart/TheApartCard.vue'
import PageNavigation from '@/components/common/PageNavigation.vue'
const axios = inject('axios')
const userInfo = ref({
  userId: ref(''),
  name: ref(''),
  currentPassword: ref(''),
  newPassword: ref('')
})

{
  axios.get('/user/mypage').then((response) => {
    userInfo.value = response.data

    console.log(sessionStorage.getItem('userStore'))
  })
}

const items = ref([
  {
    aptCode: 1111111,
    apartName: '안녕디지몬아파트',
    year: 2018,
    dong: '친구들 모두안녕동',
    roadName: '부릉로',
    jibun: '1313',
    lat: '232323',
    lng: '23232323'
  },
  {
    aptCode: 1111111,
    apartName: '안녕디지몬아파트',
    year: 2018,
    dong: '친구들 모두안녕동',
    roadName: '부릉로',
    jibun: '1313',
    lat: '232323',
    lng: '23232323'
  }
])

function onApartCardClick(aptCode) {
  console.log('클릭 aptCode == ', aptCode)
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
    </v-form>
    <div id="checklist-container">
      <h1>체크리스트</h1>
      <div class="apart-card-list">
        <div v-if="items.length == 0">저장해놓은 찜이 없습니다.</div>

        <div v-for="item in items.value" :key="item.aptCode">
          <TheApartCard
            v-if="items.value !== null"
            :aptCode="item.aptCode"
            :apartName="item.apartmentName"
            :year="item.buildYear"
            :dong="item.dong"
            :roadName="item.roadName"
            :jibun="item.jibun"
            :lat="item.lat"
            :lng="item.lng"
            @click="onApartCardClick(item.aptCode)"
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
  display: block;
}
</style>
