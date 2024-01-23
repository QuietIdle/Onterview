<script setup>
import { ref } from 'vue';
import { useSelfSpeechStore } from '@/stores/selfSpeech';
import { apiMethods } from '@/api/video';

const selfSpeechStore = useSelfSpeechStore();
const model = ref(null); // 썸네일

const videos = selfSpeechStore.questionData.videos;

const selectVideo = async function(v_id) {
  selfSpeechStore.display = false;
  const res = await apiMethods.getVideo(v_id);
  selfSpeechStore.videoData = res.data;
}
</script>

<template>
<v-sheet class="mx-auto" elevation="8" min-width="200" max-width="800">
  <v-slide-group v-model="model" show-arrows center-active>
    <v-slide-group-item v-for="video in videos" :key="video.videoId" v-slot="{ isSelected, toggle }">
      <v-card :color="isSelected ? 'primary' : 'grey-lighten-1'" class="ma-2" height="100" width="150" @click="toggle(), selectVideo(video.videoId)">
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
