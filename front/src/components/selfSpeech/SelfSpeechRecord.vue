<script setup>
import { ref } from 'vue';

const dialog = ref(false); // 모달 창
const time = ref(0); // 타이머
let timerId;

function startTimer() {
  time.value++;
  stopTimer();
  timerId = setTimeout(startTimer, 1000);
}

function stopTimer() {
  if (timerId !== null) {
    clearTimeout(timerId);
  }
}

let recorder;
let recordedChunks;

function videoStart() {
    navigator.mediaDevices.getUserMedia({ video: true, audio: true })
        .then(stream => {
            const previewPlayer = document.querySelector("#myVideo");
            previewPlayer.srcObject = stream;
            startRecording(previewPlayer.captureStream())
        })
}

function startRecording(stream) {
  recordedChunks = [];
  recorder = new MediaRecorder(stream);
  recorder.ondataavailable = (e) => {
      recordedChunks.push(e.data)
      console.log(recordedChunks);
  }
  recorder.start(3000);
  startTimer();
}

function stopRecording() {
  dialog.value = true;
  const previewPlayer = document.querySelector("#myVideo");
  //const downloadButton = document.querySelector(".download-button");
  previewPlayer.srcObject.getTracks().forEach(track => track.stop());
  recorder.stop();
  //const recordedBlob = new Blob(recordedChunks, { type: "video/webm" });
  //downloadButton.href = URL.createObjectURL(recordedBlob);
  //downloadButton.download = `recording_${new Date()}.webm`;
  //console.log(recordedChunks);
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

function downloadRecording() {
  const downloadButton = document.querySelector(".download-button");
  const recordedBlob = new Blob(recordedChunks, { type: "video/webm" });
  downloadButton.href = URL.createObjectURL(recordedBlob);
  downloadButton.download = `recording_${new Date()}.webm`;
  dialog.value = false;
}
</script>

<template>
  <div class="h-75">
    <div class="w-auto">
      <div class="ma-3">질문을 선택하세요</div>
    </div>
    <div class="w-100 h-75 text-center">
      <video id="myVideo" width="450" autoplay></video>
    </div>
    <div class="btn-container w-100 d-flex align-center">
      <v-btn class="ma-3" @click="videoStart" variant="outlined">START</v-btn>
      <v-btn class="ma-3" @click="stopRecording" variant="outlined">STOP</v-btn>
      <!-- <v-btn @click="playRecording">PLAY</v-btn> -->
      <div class="timer ml-10">{{ Math.floor(time/60) }}:{{ time%60 }}</div>
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
  </div>
</template>

<style scoped></style>
