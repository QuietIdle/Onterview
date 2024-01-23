<script setup>
import { ref } from 'vue';
import { useSelfSpeechStore } from '@/stores/selfSpeech';
import videoFeedback from '@/components/video/VideoFeedback.vue';
import VideoThumbnail from "@/components/video/VideoThumbnail.vue";

const selfSpeechStore = useSelfSpeechStore();

const items = ref([
  {
    title: '나의 답변',
    id: 1,
  },
  {
    title: '영상 보관함',
    id: 2,
  },
  {
    title: '자가 진단',
    id: 3,
  },
])

const switchTab = function(page) {
  selfSpeechStore.listIdx = page;
}

</script>

<template>
    <div class="d-flex align-center justify-center w-100 h-25">
      <v-card class="text-center" min-width="120" max-height="120" variant="text">
        <template v-for="item in items" :key="item.id">
          <v-list-item @click="switchTab(item.id)" v-if="item.id==3 && selfSpeechStore.display" disabled>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
          <v-list-item @click="switchTab(item.id)" v-else>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
        </template>
      </v-card>

      <div class="content-container w-100 h-100 bg-yellow pa-5">
        <div v-if="selfSpeechStore.listIdx==1">
          {{selfSpeechStore.questionData.answer}}
        </div>
        <div v-else-if="selfSpeechStore.listIdx==2">
          <VideoThumbnail />
        </div>
        <div v-else-if="selfSpeechStore.listIdx==3">
          <videoFeedback />
        </div>
      </div>
    </div>
</template>

<style scoped>

</style>