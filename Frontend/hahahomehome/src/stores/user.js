import { ref, inject } from 'vue'
import { defineStore } from "pinia"

// useUserStore 스토어 정의
export const useUserStore = defineStore('userStore', () => {
    // main.js 에서 app.provide로 주입된 axios 객체를 가져옵니다.
    const axios = inject('axios');

    // Authorization 및 menuList를 ref로 생성합니다.
    const Authorization = ref(null);
    const userName = ref();
    const menuList = ref([
        { name: '로그인', loginStatus: true, routeName: 'login' },
        { name: '회원가입', loginStatus: true, routeName: 'signup' },
        { name: '', loginStatus: false, routeName: "hi"},
        { name: '마이페이지', loginStatus: false, routeName: 'mypage' },
        { name: '로그아웃', loginStatus: false, routeName: 'login' },
    ]);

    // 로그인 함수 정의
    async function login(userId, password) {
        // axios를 사용하여 '/user/login' 엔드포인트에 POST 요청을 보냅니다.
        await axios.post('/user/login', { userId, password })
            .then((response) => {
                Authorization.value = response.data.Authorization;
                userName.value = response.data.username;
                menuList.value[2].name = `${userName.value}님 안녕하세요`
                console.log(Authorization.value);
            });
        
        // 로그인 성공시
        if (Authorization.value !== null) {
            // axios의 기본 헤더에 Authorization 토큰을 설정합니다.
            axios.defaults.headers.common['Authorization'] = Authorization.value;
            sessionStorage.setItem('userStore', {Authorization : Authorization.value})

            // 메뉴 항목의 로그인 상태를 토글합니다.
            menuList.value.forEach(element => {
                element.loginStatus = !element.loginStatus;
            });
        }
    }

    async function logout() {
        Authorization.value = null
        userName.value = null;

        if (Authorization.value === null) {
            menuList.value.forEach(element => {
                element.loginStatus = !element.loginStatus;
            });
        }
        
    }

    return {
        Authorization,
        menuList,
        login,
        logout
    }
},
{
    // 상태 유지 설정
    persist: {
        storage: sessionStorage // 세션 스토리지를 사용하여 상태를 유지합니다.
    }
})
