import { ref, inject } from 'vue'
import { defineStore } from "pinia"


export const useApartStore = defineStore('apartStore', () => {
    
    const dongcode=ref('')

    const setDongcode=function(code){
        
        dongcode.value=code;
        console.log('동코드',dongcode.value)
    }


    return {
        dongcode,
        setDongcode
    }
},
{
    // 상태 유지 설정
    persist: {
        storage: sessionStorage // 세션 스토리지를 사용하여 상태를 유지합니다.
    }
})
