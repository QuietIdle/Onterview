<script setup>
import SelfSpeechRecord from '../components/selfSpeech/SelfSpeechRecord.vue';
import SelfSpeechTab from '../components/selfSpeech/SelfSpeechTab.vue';
import VideoPlay from '../components/video/VideoPlay.vue';
import { useSelfSpeechStore } from '@/stores/selfSpeech.js';
import { useRouter } from 'vue-router';
import { apiMethods } from '@/api/video.js';

const pinia = useSelfSpeechStore();
const router = useRouter();

const goSelfSpeechMain = function () {
  router.push({name: 'selfspeech-main'})
}

const logData = {
  ss: function () {
    console.log('성공');
  },
  err: function () {
    console.warn('에러');
  },
}
const ttt = function () {
  pinia.selectedQuestion = 1;
  const response = apiMethods.getVideo(pinia.selectedQuestion, logData.ss, logData.err);
  console.log(response);
}
</script>

<template>
  <div class="main-container w-screen h-screen d-flex">
    <div class="w-25 ma-5">
      <div class="nav-bar ma-3">
        1
      </div>
      <div class="seach-box ma-3">
        2
      </div>
      <div class="question-list ma-3">
        <button @click="ttt">질문1</button>
      </div>
    </div>
    <div class="w-75 ma-5">
      <v-icon class="exit-btn" color="black" size="32" icon="mdi-close-circle-outline" @click="goSelfSpeechMain"></v-icon>
      <div class="h-75" v-if="pinia.display">
        <SelfSpeechRecord />
      </div>
      <div class="h-75" v-else>
        <VideoPlay />
      </div>
      <SelfSpeechTab />
    </div>
  </div>
</template>

<style scoped>
*{
  border: 1px solid black;
}
.exit-btn{
  position: fixed;
  right: 20px;
}
</style>