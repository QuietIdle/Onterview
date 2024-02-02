<script setup>
import { useSelfSpeechStore } from '@/stores/selfSpeech';
import videojs from "video.js";
import { onUpdated, onBeforeUnmount, ref } from "vue";
import { fileServer } from "@/api/video";
import { useRouter } from 'vue-router'
import { apiMethods } from "@/api/video";

const router = useRouter()

const selfSpeechStore = useSelfSpeechStore();

const goSelfSpeechMain = function () {
  router.push({name: 'selfspeech-main'})
}

const backToRecording = function() {
  selfSpeechStore.display = true;
  selfSpeechStore.listIdx = 1;
}

let player;
const videoPlayer = ref(undefined);

// const requestVideo = async function () {
//   videoLoaded.value = true;
//   let idx = 1024 * 1024;

//   try {
//     //const response = await fileServer.playVideo(selfSpeechStore.videoData.videoUrl.saveFilename);
//     const response = await fileServer.playVideo("058e0ced-14e4-4b0b-9069-2c056623141d.mkv", 0, idx-1);
    
//     const blob = new Blob([response.data], { type: 'video/mp4' });
//     const url = URL.createObjectURL(blob);
//     console.log(blob)

//     player = videojs(videoPlayer.value, {
//       sources: [{
//         src: url,
//         type: 'video/mp4'
//       }]
//     }, function onPlayerReady() {
//       console.log('Your player is ready!');
//       this.play();
//     });
//   }
//   catch (error) {
//     console.log(error);
//   }
// }

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

const isCompleted = ref(false)

async function getAllChunks(filename) {
  const chunkSize = 1024 * 1024; // 1MB 단위로 청크를 받음
  let start = 0;
  let end = chunkSize - 1;
  
  let chunks = [];
  
  while (!isCompleted.value) {
    try {
      const response = await fileServer.playVideo(filename, start, end)

      console.log(response)

      if (response.status === 206) {
        console.log(`Received chunk ${start}-${end}`);
        chunks.push(response.data);
        start = end + 1;
        end = start + chunkSize - 1;
      } else if (response.status === 200) {
        console.log('Received the last chunk');
        chunks.push(response.data);
        isCompleted.value = true;
        const blob = new Blob(chunks, { type: 'video/mp4' });
        const url = URL.createObjectURL(blob);

        player = videojs(videoPlayer.value, {
        sources: [{
          src: url,
          type: 'video/mp4'
          }]
        }, function onPlayerReady() {
        console.log('Your player is ready!');
        this.play();
      });  
      }
    } catch (error) {
      console.error('호제야...', error);
      break;
    }
  }          
}

onUpdated(getAllChunks(selfSpeechStore.videoData.videoUrl.saveFilename))

onBeforeUnmount(() => {
  if (player) {
    player.dispose();
  }
})
</script>

<template>
  <div class="container h-100 d-flex flex-column justify-space-between">
    <div class="d-flex align-center">
      <div class="ma-1">{{ selfSpeechStore.questionData.question }}</div>
      <v-icon class="exit-btn ma-1 ml-auto" color="black" size="32" icon="mdi-close-circle-outline" @click="goSelfSpeechMain"></v-icon>
    </div>

    <div class="pa-2">
      <div class="empty-player-container d-flex justify-center align-center">
        <video 
          ref="videoPlayer" 
          class="video-js vjs-big-play-centered"
          id="my-video"
          data-setup='{"width": 640}'
          controls
          width="640" height="360"></video>
      </div>
    </div>

    <div class="btn-container w-100 d-flex align-center">
      <v-btn class="mx-3 my-5" @click="markVideo(selfSpeechStore.videoData.videoId,selfSpeechStore.videoData.bookmark)">
        <v-icon color="purple" size="32" icon="mdi-bookmark-outline" v-if="!selfSpeechStore.videoData.bookmark"></v-icon>
        <v-icon color="purple" size="32" icon="mdi-bookmark-check" v-else></v-icon>
        <div v-if="!selfSpeechStore.videoData.bookmark">북마크 추가</div>
        <div v-else>북마크 해제</div>
      </v-btn>
      <v-btn class="mx-3 my-5">
        <v-icon color="black" size="32" icon="mdi-trash-can-outline" @click="goSelfSpeechMain"></v-icon>
        <div>삭제</div>
      </v-btn>
      <v-btn class="mx-3 my-5 ml-auto bg-red" @click="backToRecording" variant="outlined">녹화하러가기</v-btn>
    </div>
  </div>
</template>

<style scoped>
.container{
  background-color: #bb66ff;
}
.empty-player-container {
  width: 100%;
  height: 360px;
  background-color: #f0f0f0;
  position: relative;
}
#my-video{
  width: 640px;
  height: 360px;
  background-color: #f0f0f0;
}
.play-button {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
</style>
