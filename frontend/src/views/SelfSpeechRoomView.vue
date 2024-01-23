<script setup>
import SelfSpeechRecord from '@/components/selfSpeech/SelfSpeechRecord.vue';
import SelfSpeechTab from '@/components/selfSpeech/SelfSpeechTab.vue';
import VideoPlay from '@/components/video/VideoPlay.vue';
import { useSelfSpeechStore } from '@/stores/selfSpeech';
import { useRouter } from 'vue-router';
import { apiMethods } from '@/api/video';

const selfSpeechStore = useSelfSpeechStore();
const router = useRouter();

const goSelfSpeechMain = function () {
  router.push({name: 'selfspeech-main'})
}

const selectQuestion = async function () {
  selfSpeechStore.selectedQuestion = 9;
  const result = await apiMethods.getVideoAll(selfSpeechStore.selectedQuestion);
  selfSpeechStore.questionData = result.data;
  console.log(result.data);
}
</script>

<template>
  <div class="main-container w-screen h-screen d-flex">
    <div class="w-25 ma-5">
      <div class="nav-bar ma-3">
        1
      </div>
      <div class="search-box ma-3">
        2
      </div>
      <div class="question-list ma-3">
        <button @click="selectQuestion">질문1</button>
      </div>
    </div>

    <div class="w-75 ma-5">
      <div class="h-75">
        <div class="d-flex align-center">
          <div class="ma-1">{{ selfSpeechStore.questionData.question }}</div>
          <v-icon class="exit-btn ma-1 ml-auto" color="black" size="32" icon="mdi-close-circle-outline" @click="goSelfSpeechMain"></v-icon>
        </div>
        <div v-if="selfSpeechStore.display">
          <SelfSpeechRecord />
        </div>
        <div v-else>
          <VideoPlay />
        </div>
      </div>

      <SelfSpeechTab />
    </div>
  </div>
</template>

<style scoped>
.main-container>*{
  border: 1px solid black;
}
*{
  border: 1px solid black;
}
</style>