<script setup>
import { ref } from 'vue';
import SelfSpeechRecord from '@/components/selfSpeech/SelfSpeechRecord.vue';
import SelfSpeechTab from '@/components/selfSpeech/SelfSpeechTab.vue';
import VideoPlay from '@/components/video/VideoPlay.vue';
import SelfSpeechQuestion from "@/components/selfSpeech/SelfSpeechQuestion.vue";
import { useSelfSpeechStore } from '@/stores/selfSpeech';
import { apiMethods } from '@/api/video';

const selfSpeechStore = useSelfSpeechStore();

const q_id = ref(-1);

const selectQuestion = async function () {
  selfSpeechStore.selectedQuestion = q_id.value;
  const result = await apiMethods.getVideoAll(selfSpeechStore.selectedQuestion);
  selfSpeechStore.questionData = result.data;
}
</script>

<template>
  <div class="main-container w-screen h-screen d-flex">

    <div class="w-25 ma-5">
      <!-- <div class="nav-bar ma-3">
        1
      </div>
      <div class="search-box ma-3">
        2
      </div>
      <div class="question-list ma-3">
        <input type="number" v-model="q_id">
        <button @click="selectQuestion">질문1</button>
      </div> -->

      <SelfSpeechQuestion />
    </div>

    <div class="w-75 ma-5">
      <div class="h-75">
        <div v-if="selfSpeechStore.display" class="h-100">
          <SelfSpeechRecord />
        </div>
        <div v-else class="h-100">
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