import { ref, inject } from 'vue'
import { defineStore } from "pinia"

export const useUserStore = defineStore('userStore', () => {
    // main.js 에서 app.provie한 객체 가져오기
    const axios = inject('axios')
    const Authorization = ref(null)   
    const menuList = ref([
        { name: '로그인', loginStatus: true, routeName: 'login' },
        { name: '회원가입', loginStatus: true, routeName: 'signup' },
        { name: '게시판', loginStatus: false, routeName: 'login' },
        { name: '마이페이지', loginStatus: false, routeName: 'mypage' },
        { name: '로그아웃', loginStatus: false, routeName: 'login' },
    ])

    async function login(userId, password) {
        await axios.post('/user/login', { userId, password })
            .then((response) => {
                Authorization.value = response.data.Authorization;
                console.log(Authorization.value)
            })
        
        // 로그인 성공시
        if (Authorization.value !== null) {
            axios.defaults.headers.common['Authorization'] = Authorization.value;

            // 토글
            menuList.value.forEach(element => {
                element.loginStatus = !element.loginStatus;
            })
        }
    }

    return {
        Authorization,
        menuList,
        login
    }
},
    {
        persist: {
        storage: sessionStorage
    }
})
