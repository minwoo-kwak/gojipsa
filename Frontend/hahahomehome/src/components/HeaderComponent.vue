<script setup>
import { storeToRefs } from "pinia";
import { useUserStore } from "../stores/user";

const userStore = useUserStore();
// "userStore"에서 "menuList"를 가져와서 reactive reference로 사용.
const { menuList } = storeToRefs(userStore);

/**
 * 로그아웃 함수
 */
function logout() {
    // 세션 스토리지에서 "userStore"를 가져와서 "menuList" 추출
    const menuList = JSON.parse(sessionStorage.getItem('userStore'))['menuList'];

    // 각 메뉴의 "loginStatus"를 토글.
    for (let menu of menuList.value) {
        menu.loginStatus = !menu.loginStatus;
    }
    
    // 서버에서 로그아웃 성공 응답을 받으면 로컬 스토리지의 토큰을 삭제.
    sessionStorage.removeItem("userStore");
}
</script>

<template>
    <v-app-bar>
        <v-app-bar-title text="HaHaHomeHome" class="v-col-3" @click="$router.push('/')"></v-app-bar-title>
        <v-list class="v-col-8">
            <v-row class="justify-end">
                <!-- "menuList"에 대한 루프를 수행하여 메뉴 항목을 생성합니다. -->
                <template v-for="menu in menuList">
                    <list-item v-if="menu.loginStatus" :key="menu.name">
                        <!-- "menu.name"이 '로그아웃'인 경우 로그아웃 링크를 생성합니다. -->
                        <template v-if="menu.name == '로그아웃'">
                            <router-link to="/" @click:prevent="logout()">{{ menu.name }}</router-link>
                        </template>
                        <!-- 그 외 메뉴 항목은 해당 routeName으로 라우팅 링크를 생성합니다. -->
                        <template v-else>
                            <router-link :to="{name: menu.routeName}">{{ menu.name }}</router-link>
                        </template>
                    </list-item>
                </template>
            </v-row>
        </v-list>
    </v-app-bar>
</template>

<style scoped>
</style>
