<script setup>
import SelfSpeechRecord from '../components/selfSpeech/SelfSpeechRecord.vue';
import SelfSpeechTab from '../components/selfSpeech/SelfSpeechTab.vue';
import VideoPlay from '../components/video/VideoPlay.vue';
import { useSelfSpeechStore } from '@/stores/selfSpeech.js';
import { useRouter } from 'vue-router';

const pinia = useSelfSpeechStore();
const router = useRouter();

function goSelfSpeechMain() {
  router.push({name: 'selfspeech-main'})
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
        3
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
