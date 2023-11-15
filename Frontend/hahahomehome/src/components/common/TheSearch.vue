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

const getDongNameList = () => {
  getDongNameListFromLocalAPI(
    selectedGugun.value,
    ({ data }) => {
      dongNameList.value = data
    },
    (error) => {
      console.log(error)
    }
  )
}

const onDongNameChange = () => {
  param.value = {
    sidoName: selectedSido.value,
    gugunName: selectedGugun.value,
    dongName: selectedDongName.value
  }
  getDongCode()
}

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
watch(selectedGugun, onGugunChange)
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
  <button style="background-color: white">검색 버튼</button>
</template>

<style scoped>
.v-select {
  width: 8rem;
  margin: 2rem;
}
</style>
