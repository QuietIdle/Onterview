<script setup>
import SelfSpeechRecord from '../components/selfSpeech/SelfSpeechRecord.vue';
import SelfSpeechTab from '../components/selfSpeech/SelfSpeechTab.vue';
import VideoPlay from '../components/video/VideoPlay.vue';
import { useSelfSpeechStore } from '@/stores/selfSpeech.js';

const pinia = useSelfSpeechStore();

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
</style>
