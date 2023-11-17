<script setup>
const props = defineProps({
  aptcode: String
})
const showCheckList = () => {
  window.open(`/apart/detail/checklist/${props.aptcode}`, '_blank', 'width=700, height=720')
}
</script>

<template>
  <v-btn prepend-icon="mdi-pencil-remove-outline" stacked @click="showCheckList">체크리스트</v-btn>
</template>

<style scoped></style>
