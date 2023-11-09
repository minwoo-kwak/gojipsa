<script setup>
import { ref, inject } from 'vue'
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

const items = [
  {
    id: 1,
    name: '하하 아파트111',
    address: '서울특별시 강남구 역삼로 5층',
    date: '2023년 10월',
    price: 20
  },
  {
    id: 2,
    name: '하하 아파트22',
    address: '서울특별시 강남구 역삼로 5층',
    date: '2023년 10월',
    price: 20
  },
  {
    id: 3,
    name: '하하 아파트33',
    address: '서울특별시 강남구 역삼로 5층',
    date: '2023년 10월',
    price: 20
  }
]
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
  </div>
  <div>
    <h2>찜목록</h2>
    <div class="wishlist" v-for="item in items" :key="item.id">
      {{ item.name }} <br />
      {{ item.address }} <br />
      거래일: {{ item.date }}<br />
      <div class="price">{{ item.price }}억</div>
    </div>
  </div>
</template>

<style scoped>
.user-form {
  min-height: 300px;
  min-width: 960px;
}
.price {
  font-size: 5rem;
  color: red;
}
.wishlist {
  border: 0.1rem solid black;
}
</style>
