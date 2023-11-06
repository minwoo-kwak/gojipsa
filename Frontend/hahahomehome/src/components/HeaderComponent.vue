<script setup>
import { storeToRefs } from "pinia";
import { useUserStore } from "../stores/user";

const userStore = useUserStore();
const { menuList } = storeToRefs(userStore)


import { useRouter } from "vue-router";
const router = useRouter();

function logout() {
    const menuList = JSON.parse(sessionStorage.getItem('userStore'))['menuList']

    // 메인 페이지로 이동합니다.
    // router.push("/");
    // 로그아웃 후 loginStatus를 토글합니다.
    for (let menu of menuList.value) {
        menu.loginStatus = !menu.loginStatus;
    }
    // 서버에서 로그아웃 성공 응답을 받으면 로컬 스토리지의 토큰을 삭제합니다.
    sessionStorage.removeItem("userStore");
 
}
</script>

<template>
    <v-app-bar>
        <v-app-bar-title text="HaHaHomeHome" class="v-col-3" @click="$router.push('/')"></v-app-bar-title>
            <v-list class="v-col-8">
                <v-row class="justify-end">
                    <template v-for="menu in menuList">
                        <list-item v-if="menu.loginStatus" :key="menu.name">
                            <template v-if="menu.name == '로그아웃'">
                                <router-link to="/" @click="logout()">{{ menu.name }}</router-link>
                            </template>
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