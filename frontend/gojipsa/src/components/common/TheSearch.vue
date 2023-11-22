<script setup>
import { ref, onMounted, watch } from 'vue'
import {
  getSido,
  getGugun,
  getDongNameListFromLocalAPI,
  getDongCodeFromLocalAPI
} from '../../api/apartment'
import { storeToRefs } from 'pinia'
import { useApartStore } from '@/stores/apart'
const apartStore = useApartStore()
const { dongcode } = storeToRefs(apartStore)

const sidoList = ref([])
const gugunList = ref([])
const dongNameList = ref([])
const selectedSido = ref('')
const selectedGugun = ref('')
const selectedDongName = ref('')
const selectedDongCode = ref('')

const param = ref({})

/**
 * 시작하자마자 sido리스트 가져오기
 */
onMounted(() => {
  getSidoList()
})

/**
 * sido리스트를 로컬api에 요청해서 가져오기
 */
const getSidoList = () => {
  getSido(
    ({ data }) => {
      sidoList.value = data
    },
    (error) => {
      console.log(error)
    }
  )
}

/**
 * selectedSido값이 변경될 경우 실행하여 gugunList 가져오기
 */
const onSidoChange = () => {
  getGugunList()
}

/**
 * 로컬 API에서 gugunList 가져오기
 */
const getGugunList = () => {
  getGugun(
    selectedSido.value,
    ({ data }) => {
      console.log('구군 데이터 : ', data)
      // 가져온 데이터를 gugunList에 할당
      gugunList.value = data
    },
    (error) => {
      console.log(error)
    }
  )
}

/**
 * selectedGugun값이 변경될 경우 실행하여 dongNameList 가져오기
 */
const onGugunChange = () => {
  getDongNameList()
}

/**
 * 선택된 시도와 구군에 해당하는 동이름 리스트를 가져옵니다.
 */
const getDongNameList = () => {
  getDongNameListFromLocalAPI(
    {
      gugunName: selectedGugun.value,
      sidoName: selectedSido.value
    },
    ({ data }) => {
      dongNameList.value = data
    },
    (error) => {
      console.log(error)
    }
  )
}

/**
 * 동이름이 변경될 경우 API에 보낼 parameter을 설정하고getDongCode()함수를 불러옵니다.
 */
const onDongNameChange = () => {
  param.value = {
    sidoName: selectedSido.value,
    gugunName: selectedGugun.value,
    dongName: selectedDongName.value
  }
  getDongCode()
}

/**
 * 선택된 시도, 구군, 동이름으로 동코드를 가져옵니다.
 */
const getDongCode = () => {
  getDongCodeFromLocalAPI(
    param.value,
    ({ data }) => {
      selectedDongCode.value = data.dongCode
      dongcode.value = selectedDongCode.value
    },
    (error) => {
      console.log(error)
    }
  )
}

// selectedSido가 변경될 시
watch(selectedSido, onSidoChange)
// selectedGugun 변경될 시
watch(selectedGugun, onGugunChange)
// selectedDongName 변경될 시
watch(selectedDongName, onDongNameChange)
</script>

<template>
  <v-select label="시도 선택" :items="sidoList" variant="solo" v-model="selectedSido"></v-select>
  <v-select label="구군 선택" :items="gugunList" variant="solo" v-model="selectedGugun"></v-select>
  <v-select
    label="동 선택"
    :items="dongNameList"
    variant="solo"
    v-model="selectedDongName"
  ></v-select>
</template>

<style scoped>
.v-select {
  width: 8rem;
  margin: 2rem;
}
</style>
