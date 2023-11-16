import { ref, inject,reactive } from 'vue'
import { defineStore } from "pinia"

export const useFilterStore = defineStore('filterStore', () => {
    
    const market=ref(false)
    const convenience=ref(false)
    const school=ref(false)
    const subway=ref(false)
    const bank=ref(false)
    const hospital=ref(false)
    

    return {
        market,
        convenience,
        school,
        subway,
        bank,
        hospital
    }
},
{
    // 상태 유지 설정
    persist: {
        storage: sessionStorage // 세션 스토리지를 사용하여 상태를 유지합니다.
    }
})