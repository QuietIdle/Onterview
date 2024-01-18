<script setup>
import axios from 'axios';
import { useSelfSpeechStore } from '@/stores/selfSpeech.js';

const pinia = useSelfSpeechStore();

function backToRecording() {
  pinia.display = true;
}

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
  <div class="w-auto">
    <div class="ma-3">질문을 선택하세요</div>
  </div>
  <div class="w-100 h-75 text-center">
    <video id="recordedVideo" width="450" autoplay></video>
  </div>
  <div class="btn-container w-100 d-flex align-center">
    <v-btn class="ma-3" @click="" variant="outlined">북마크</v-btn>
    <v-btn class="ma-3" @click="" variant="outlined">삭제</v-btn>
    <v-btn class="ma-3 ml-auto bg-red" @click="backToRecording" variant="outlined">녹화하러가기</v-btn>
  </div>
</template>

<style scoped></style>
