<script setup>
import { ref } from 'vue';
import { useSelfSpeechStore } from '@/stores/selfSpeech.js';
import { apiMethods } from '@/api/selfSpeechVideo.js';

const pinia = useSelfSpeechStore();
const model = ref(null); // 썸네일

const videos = pinia.questionData.videos;

const selectVideo = async function() {
  pinia.display = false;
  const res = await apiMethods.getVideo(4);
  pinia.videoData = res.data;
}
</script>

<template>
<v-sheet class="mx-auto" elevation="8" min-width="200" max-width="800">
  <v-slide-group v-model="model" show-arrows center-active>
    <v-slide-group-item v-for="video in videos" :key="video.videoId" v-slot="{ isSelected, toggle }">
      <v-card :color="isSelected ? 'primary' : 'grey-lighten-1'" class="ma-2" height="100" width="150" @click="toggle(), selectVideo()">
        <div class="d-flex flex-column align-center justify-center">
          <!-- {{ video.videoId }}-{{ test }} -->
          <v-img :src="video.thumbnailUrl.saveFilename" width="120" height="80" class="img-container"></v-img>
          <div>{{video.title}}</div>
        </div>
      </v-card>
    </v-slide-group-item>
  </v-slide-group>
</v-sheet>
</template>

<style scoped></style>
