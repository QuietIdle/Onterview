<script setup>
import SelfSpeechRecord from '@/components/selfSpeech/SelfSpeechRecord.vue';
import SelfSpeechTab from '@/components/selfSpeech/SelfSpeechTab.vue';
import VideoPlay from '@/components/video/VideoPlay.vue';
import SelfSpeechQuestion from "@/components/selfSpeech/SelfSpeechQuestion.vue";
import { useSelfSpeechStore } from '@/stores/selfSpeech';

const selfSpeechStore = useSelfSpeechStore();

</script>

<template>
  <div class="main-container w-screen h-screen d-flex ma-5">

    <div class="w-25">
      <SelfSpeechQuestion />
    </div>

    <div class="w-75">
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
/* *{
  border: 1px solid black;
} */
</style>