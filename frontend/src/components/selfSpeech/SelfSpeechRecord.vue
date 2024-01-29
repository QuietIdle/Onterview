<script setup>
import { ref } from 'vue';
import { apiMethods, fileServer } from "@/api/video";
import { useSelfSpeechStore } from "@/stores/selfSpeech";
import {useRouter} from 'vue-router'

const router = useRouter()

const dialog = ref(false); // 모달 창
const dialog2 = ref(false); // 저장 모달 창
const time = ref(0); // 타이머
let timerId;

const selfSpeechStore = useSelfSpeechStore();
const uploadData = ref(null);
const videoTitle = ref("");

const flag = ref(0); // chunk 전송 완료 여부

const goSelfSpeechMain = function () {
  router.push({name: 'selfspeech-main'})
}

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
      const previewPlayer = document.querySelector("#my-video");
      previewPlayer.srcObject = stream;
      // previewPlayer.width = 640;
      // previewPlayer.height = 360;

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
      //console.log(e.data.type);
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
    const response = await fileServer.uploadVideo(idx, flag.value, formData);
    //const response = await axios.post(`http://70.12.247.60:8080/api/chunk/upload?&chunkNumber=${idx}&endOfChunk=${flag.value}`, formData);
    //console.log('Chunk sent successfully!', response);
    if (response.status === 200) {
      console.log(response.data);
      uploadData.value = response.data;
    }
  } catch (error) {
    console.error('Error sending chunk to server:', error);
  }
}

const stopRecording = function() {
  flag.value = 1;
  dialog.value = true;
  const previewPlayer = document.querySelector("#my-video");
  previewPlayer.srcObject.getTracks().forEach(track => track.stop());
  recorder.stop();
  stopTimer();
  time.value = 0;
}

// const downloadRecording = function() {
//   const downloadButton = document.querySelector(".download-button");
//   const recordedBlob = new Blob(recordedChunks, { type: "video/webm" });
//   downloadButton.href = URL.createObjectURL(recordedBlob);
//   downloadButton.download = `recording_${new Date()}.webm`;
//   dialog.value = false;
// }

const submitTitle = function () {
  dialog.value = false
  dialog2.value = true
}

const saveRecording = async function () {
  const req_body = {
    questionId : selfSpeechStore.selectedQuestion,
    videoLength : time.value,
    title : videoTitle.value,
    videoInformation : {
        saveFilename: uploadData.value.videoUrl,
        originFilename: uploadData.value.videoUrl
    },
    thumbnailInformation : {
        saveFilename: uploadData.value.thumbnailUrl,
        originFilename: uploadData.value.thumbnailUrl
    }
  }
  try {
    const response = await apiMethods.saveVideo(req_body)
    console.log('save successfully!', response.data)
  } catch (error) {
    console.log(error)
  }
  dialog2.value = false
}

const cancelRecording = async function () {
  dialog.value = false
  await fileServer.cancelUpload(uploadData.value.videoUrl)
}
</script>

<template>
<div class="h-100 d-flex flex-column justify-space-between">
  <div class="d-flex align-center">
    <div class="ma-1">{{ selfSpeechStore.questionData.question }}</div>
    <v-icon class="exit-btn ma-1 ml-auto" color="black" size="32" icon="mdi-close-circle-outline" @click="goSelfSpeechMain"></v-icon>
  </div>

  <div class="w-100 text-center">
    <video id="my-video" autoplay></video>
  </div>

  <div class="btn-container w-100 d-flex align-center">
    <v-btn class="ma-3" @click="videoStart" variant="outlined">START</v-btn>
    <v-btn class="ma-3" @click="stopRecording" variant="outlined">STOP</v-btn>
    <!-- <v-btn @click="playRecording">PLAY</v-btn> -->
    <div class="timer ml-10" v-if="(time%60)>=10">{{ Math.floor(time/60) }}:{{ time%60 }}</div>
    <div class="timer ml-10" v-else>{{ Math.floor(time/60) }}:0{{ time%60 }}</div>
  </div>
</div>

<v-dialog v-model="dialog" width="auto">
  <v-card>
    <v-card-text>
      녹화 완료!
    </v-card-text>
    <div class="d-flex">
      <v-card-actions>
        <v-btn color="primary" block @click="submitTitle"><a class="download-button">저장하기</a></v-btn>
      </v-card-actions>
      <v-card-actions>
        <v-btn color="warning" block @click="cancelRecording">다시 연습</v-btn>
      </v-card-actions>
    </div>
  </v-card>
</v-dialog>

<v-dialog v-model="dialog2" width="auto">
  <v-card>
    <v-sheet width="500px" class="mx-auto">
      <v-form validate-on="submit lazy">
        <v-text-field
          v-model="videoTitle"
          :rules="rules"
          label="영상 제목을 입력하세요"
        ></v-text-field>

        <v-card-actions>
          <v-btn
            :loading="loading"
            type="submit"
            block
            class="mt-2"
            text="저장"
            @click="saveRecording"
          ></v-btn>
        </v-card-actions>
      </v-form>
    </v-sheet>
  </v-card>
</v-dialog>
</template>

<style scoped>
#my-video{
  width: 640px;
  height: 360px;
  background-color: black;
}
</style>
