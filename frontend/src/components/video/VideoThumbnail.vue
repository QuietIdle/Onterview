<script setup>
import { ref } from 'vue';
import { useSelfSpeechStore } from '@/stores/selfSpeech';
import { apiMethods } from '@/api/video';

const selfSpeechStore = useSelfSpeechStore();
const model = ref(null); // 썸네일

//const videos = selfSpeechStore.questionData.videos;
const videos = ref([
  {
    videoId: 1,
    title: "testest",
    thumbnailUrl: {
      saveFilename: "https://picsum.photos/200",
    },
  },
  {
    videoId: 2,
    title: "TT",
    thumbnailUrl: {
      saveFilename: "https://picsum.photos/200",
    },
  }
])

const selectVideo = async function (v_id) {
  selfSpeechStore.display = false;
  try {
    const res = await apiMethods.getVideo(v_id);
    selfSpeechStore.videoData = res.data;
  } catch (error) {
    console.log(error)
  }
}
</script>

<template>
<div class="h-100">
  <v-sheet class="h-100" elevation="1" min-width="200">
    <v-slide-group class="h-100" v-model="model" show-arrows center-active>
      <v-slide-group-item class="h-100" v-for="video in videos" :key="video.videoId" v-slot="{ isSelected, toggle }">
        <v-card :color="isSelected ? 'primary' : 'grey-lighten-1'" class="ma-3 my-auto" width="150" @click="toggle(), selectVideo(video.videoId)">
          <div class="d-flex flex-column align-center justify-center pa-1">
            <!-- {{ video.videoId }}-{{ test }} -->
            <v-img :src="video.thumbnailUrl.saveFilename" width="120" height="80" class="img-container"></v-img>
            <div>{{video.title}}</div>
          </div>
        </v-card>
      </v-slide-group-item>
    </v-slide-group>
  </v-sheet>
</div>
</template>

<style scoped>

</style>
