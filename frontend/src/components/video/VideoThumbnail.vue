<script setup>
import { ref } from 'vue';
import { useSelfSpeechStore } from '@/stores/selfSpeech.js';

const pinia = useSelfSpeechStore();
const model = ref(null); // 썸네일
const test = ref("A");

const videos = pinia.questionData.videos;

const aaa = function(temp) {
  pinia.display = false;
  if (!temp) {
    test.value = "B";
  } 
  else {
    test.value = "A";
  }
}
</script>

<template>
<v-sheet class="mx-auto" elevation="8" min-width="200" max-width="800">
  <v-slide-group v-model="model" show-arrows center-active>
    <v-slide-group-item v-for="video in videos" :key="video.videoId" v-slot="{ isSelected, toggle }">
      <v-card :color="isSelected ? 'primary' : 'grey-lighten-1'" class="ma-2" height="100" width="150" @click="toggle(), aaa(isSelected)">
        <div class="d-flex fill-height align-center justify-center">
          {{ video.videoId }}-{{ test }}
          <v-scale-transition>
            <v-icon v-if="isSelected" color="white" size="48" icon="mdi-close-circle-outline"></v-icon>
          </v-scale-transition>
        </div>
      </v-card>
    </v-slide-group-item>
  </v-slide-group>
</v-sheet>
</template>

<style scoped></style>
