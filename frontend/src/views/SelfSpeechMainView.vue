<script setup>
import testImg from '@/assets/selfSpeech/SelfSpeechMainIcon.png'
import { useRouter } from 'vue-router'

const router = useRouter()

function startSelfSpeech() {
  router.push({ name: 'selfspeech-room' })
}
function goStorage() {
  router.push({ name: 'storage-question' })
}
</script>

<template>
  <div class="pa-10 w-100 h-50" style="background-color: #eed8e7">
    <div class="container pa-10 d-flex justify-center align-center">
      <div class="img-container ml-16 w-25 h-100">
        <v-img :src="testImg" :width="200"></v-img>
      </div>
      <div class="content-container w-75 h-100 text-center">
        <div class="pa-5 text-h3">1문 1답<br />셀프 스피치</div>
        <div class="pa-5">
          면접 문항과 답변을 등록하고 녹화해서<br />나의 답변 내용, 시간, 발음, 표정을 점검해보세요
        </div>
        <div class="btn-container pa-5">
          <v-btn variant="outlined" class="ma-3" @click="startSelfSpeech"> 시작하기 </v-btn>
          <v-btn variant="outlined" class="ma-3" @click="goStorage"> 면접 문항 관리 </v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
