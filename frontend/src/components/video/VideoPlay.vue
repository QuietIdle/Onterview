<script setup>
import { useSelfSpeechStore } from '@/stores/selfSpeech.js';
import videojs from "video.js";
import { onBeforeUnmount, onMounted, ref } from "vue";
import { fileServer } from "@/api/selfSpeechVideo"; 

const pinia = useSelfSpeechStore();

const backToRecording = function() {
  pinia.display = true;
  pinia.listIdx = 1;
}

let player;
const videoPlayer = ref(null);
const videoLoaded = ref(false);

const requestVideo = async function () {
  videoLoaded.value = true;
  try {
    // const response = await axios.get(`http://70.12.247.60:8080/api/chunk/stream/${pinia.videoData.videoUrl.saveFilename}`);
    // console.log(response.data);
    // const recordingPlayer = document.querySelector('#recordedVideo');
    // recordingPlayer.src = 'http://70.12.247.60:8080/stream?filePath=f7e694f9-6f83-4e52-9ac3-712a055df47d.mp4';
    //recordingPlayer.play();
    
    const response = await fileServer.playVideo(pinia.videoData.videoUrl.saveFilename);

    console.log(response.data);
    const blob = new Blob([response.data], { type: 'video/mp4' });
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
  catch (error) {
    console.log(error);
  }
}
onBeforeUnmount(() => {
  if (player) {
    player.dispose();
  }
})
// function playRecording() {
//     const recordingPlayer = document.querySelector("#recordedVideo");
//     const recordedBlob = new Blob(recordedChunks, { type: "video/webm" });
//     recordingPlayer.src = URL.createObjectURL(recordedBlob);
//     recordingPlayer.play();
//     downloadButton.href = recordingPlayer.src;
//     downloadButton.download = `recording_${new Date()}.webm`;
//     console.log(recordedChunks);
// }

</script>

<template>
  <div v-if="!videoLoaded" class="empty-player-container ma-auto">
    <button @click="requestVideo" class="play-button">Play</button>
  </div>
  <div v-else class="d-flex justify-center">
    <video ref="videoPlayer" class="video-js vjs-big-play-centered vjs-layout-medium" data-setup='{}' autoplay controls preload="auto" width="640" height="360"></video>
  </div>
  <div class="btn-container w-100 d-flex align-center">
    <v-btn class="ma-3" @click="requestVideo" variant="outlined">북마크</v-btn>
    <v-btn class="ma-3" variant="outlined">삭제</v-btn>
    <v-btn class="ma-3 ml-auto bg-red" @click="backToRecording" variant="outlined">녹화하러가기</v-btn>
  </div>
</template>

<style scoped>
.empty-player-container {
  width: 640px;
  height: 360px;
  background-color: black;
  position: relative;
}

.play-button {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #3498db;
  color: #fff;
  border: none;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
}
</style>
