<script setup>
import { ref } from 'vue';
import { useSelfSpeechStore } from '@/stores/selfSpeech';
import { apiMethods } from '@/api/video';

const selfSpeechStore = useSelfSpeechStore();
const model = ref(null); // 썸네일

const videos = selfSpeechStore.questionData.videos;

const markVideo = async function (id, bool) {
  try {
    const req_body = {
      bookmark: !bool
    }
    const result = await apiMethods.patchVideo(id, req_body)
    console.log(result.data);
  } catch (error) {
    console.log(error);
  }
}

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
        <v-card :color="isSelected ? 'primary' : 'grey-lighten-1'" class="ma-3 my-auto" width="150">
          <div class="d-flex flex-column align-center justify-center pa-1">
            <div class="thumbnail-container">
              <v-img :src="video.thumbnailUrl.saveFilename" width="120" height="80" class="img-container" @click="toggle(), selectVideo(video.videoId)"></v-img>
              <div>{{video.title}}</div>
              <v-icon 
                v-show="!video.bookmark" 
                class="icon"
                color="purple" 
                size="32" 
                icon="mdi-bookmark-outline"
                @click="markVideo(video.videoId, video.bookmark), video.bookmark=!video.bookmark"
              >
              </v-icon>
              <v-icon 
                v-show="video.bookmark" 
                class="icon"
                color="purple" 
                size="32" 
                icon="mdi-bookmark-check"
                @click="markVideo(video.videoId, video.bookmark), video.bookmark=!video.bookmark"
              >
              </v-icon>
            </div>
          </div>
        </v-card>
      </v-slide-group-item>
    </v-slide-group>
  </v-sheet>
</div>
</template>

<style scoped>
.img-container{
  cursor: pointer;
}
  .thumbnail-container{
    position: relative;
  }
  .icon{
    position: absolute;
    top: -5%;
    left: -15%;
  }
</style>
