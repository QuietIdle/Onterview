<script setup>
import { ref } from 'vue';
import axios from 'axios';
import { useSelfSpeechStore } from '@/stores/selfSpeech.js';

const pinia = useSelfSpeechStore();
const dialog = ref(false); // 모달 창
const time = ref(0); // 타이머
let timerId;

const flag = ref(0); // chunk 전송 완료 여부

const startTimer = function() {
  time.value++;
  stopTimer();
  timerId = setTimeout(startTimer, 1000); // 스탑워치 주기 1초
}

const stopTimer = function() {
  if (timerId !== null) {
    clearTimeout(timerId);
  }
}

let recorder;
let recordedChunks = [];


const videoStart = function() {
  navigator.mediaDevices.getUserMedia({ video: true, audio: true })
    .then(stream => {
      const previewPlayer = document.querySelector("#myVideo");
      previewPlayer.srcObject = stream;
      previewPlayer.width = 640;
      previewPlayer.height = 360;

      startRecording(previewPlayer.captureStream())
    })
}

const startRecording = function(stream) {
  flag.value = 0;
  let idx = 0; // chunk 갯수
  recordedChunks = [];
  recorder = new MediaRecorder(stream);
  recorder.ondataavailable = (e) => {
    if (e.data.size > 0) {
      recordedChunks.push(e.data);
      //console.log(idx);
      //console.log(flag);
      //console.log(e.data);
      idx++;
      if (idx >= 100) { // 녹화시간 300초 제한
        stopRecording();
      }
    }
    sendToServer(e.data, idx);
  }
  recorder.start(3000);
  startTimer();
}

const sendToServer = async function(chunk, idx) {
  try {
    // FormData를 생성하고 녹화된 데이터를 추가
    const formData = new FormData();
    formData.append('chunk', chunk);

    // axios를 사용하여 POST 요청을 서버로 보냄
    const response = await axios.post(`http://70.12.247.60:8080/api/chunk/upload?&chunkNumber=${idx}&endOfChunk=${flag.value}`, formData);
    console.log('Chunk sent successfully!', response);
  } catch (error) {
    console.error('Error sending chunk to server:', error);
  }
}

const stopRecording = function() {
  flag.value = 1;
  dialog.value = true;
  const previewPlayer = document.querySelector("#myVideo");
  previewPlayer.srcObject.getTracks().forEach(track => track.stop());
  recorder.stop();
  stopTimer();
  time.value = 0;
}

// function playRecording() {
//     const recordingPlayer = document.querySelector("#recordedVideo");
//     const downloadButton = document.querySelector(".download-button");
//     const recordedBlob = new Blob(recordedChunks, { type: "video/webm" });
//     recordingPlayer.src = URL.createObjectURL(recordedBlob);
//     recordingPlayer.play();
//     downloadButton.href = recordingPlayer.src;
//     downloadButton.download = `recording_${new Date()}.webm`;
//     console.log(recordedChunks);
// }

const downloadRecording = function() {
  const downloadButton = document.querySelector(".download-button");
  const recordedBlob = new Blob(recordedChunks, { type: "video/webm" });
  downloadButton.href = URL.createObjectURL(recordedBlob);
  downloadButton.download = `recording_${new Date()}.webm`;
  dialog.value = false;
}

</script>

<template>
<!-- <div class="w-auto">
  <div class="ma-3">{{ pinia.questionData.question }}</div>
</div> -->
<div class="w-100 h-75 text-center">
  <video id="myVideo" width="480" autoplay></video>
</div>
<div class="btn-container w-100 d-flex align-center">
  <v-btn class="ma-3" @click="videoStart" variant="outlined">START</v-btn>
  <v-btn class="ma-3" @click="stopRecording" variant="outlined">STOP</v-btn>
  <!-- <v-btn @click="playRecording">PLAY</v-btn> -->
  <div class="timer ml-10" v-if="(time%60)>=10">{{ Math.floor(time/60) }}:{{ time%60 }}</div>
  <div class="timer ml-10" v-else>{{ Math.floor(time/60) }}:0{{ time%60 }}</div>
</div>

<v-dialog v-model="dialog" width="auto">
  <v-card>
    <v-card-text>
      녹화 완료!
    </v-card-text>
    <div class="d-flex">
      <v-card-actions>
        <v-btn color="primary" block @click="downloadRecording"><a class="download-button">저장하기</a></v-btn>
      </v-card-actions>
      <v-card-actions>
        <v-btn color="warning" block @click="dialog = false">다시 연습</v-btn>
      </v-card-actions>
    </div>
  </v-card>
</v-dialog>
</template>

<style scoped>
#myVideo{
  width: 640px;
  height: 360px;
  background-color: black;
}
</style>
