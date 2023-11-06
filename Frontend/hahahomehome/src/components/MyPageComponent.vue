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
    })
}
    
</script>

<template>
    <v-form class="user-form">
        <v-container grid-list-xs>
            <v-row><v-text-field label="아이디" v-model="userInfo.userId"/></v-row>
            <v-row><v-text-field label="이름" v-model="userInfo.name"/></v-row>
            <v-row><v-text-field label="현재 패스워드" v-model="userInfo.currentPassword"/></v-row>
            <v-row><v-text-field label="새로운 패스워드" v-model="userInfo.newPassword"/></v-row>
        </v-container>
    </v-form>
</template>

<style scoped>
.user-form {
    min-height: 300px;
    min-width: 960px;
}
</style>