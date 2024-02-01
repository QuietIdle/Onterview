<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue';
import { apiMethods, fileServer } from "@/api/video";
import { useSelfSpeechStore } from "@/stores/selfSpeech";
import { useUserStore } from "@/stores/user";
import { useRouter } from 'vue-router'
import { v4 as uuidv4 } from 'uuid'

const router = useRouter()

const dialog = ref(false); // 모달 창
const TTSscript = ref("")
const synth = ref(window.speechSynthesis);

const mediaToggle = ref({
  video: true,
  audio: true,
  play: false,
})

const time = ref(0); // 타이머
let timerId;

const filename = ref("")

const selfSpeechStore = useSelfSpeechStore();
const userStore = useUserStore()
const uploadData = ref(null);

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

const startVideo = function () {
  
  navigator.mediaDevices.getUserMedia({
    audio: true,
    video: {
      width: { min: 150, ideal: 360, max: 600 },
      height: { min: 100, ideal: 240, max: 390 },
    },
  })
    .then(stream => {
      const previewPlayer = document.querySelector("#my-video");
      previewPlayer.srcObject = stream;
      // previewPlayer.width = 640;
      // previewPlayer.height = 360;

      //startRecording(previewPlayer.captureStream())
    })
}

const startRecording = function () {
  const stream = document.querySelector("#my-video").captureStream()
  mediaToggle.value.play = true;
  TTSscript.value = selfSpeechStore.questionData.question
  TTS()
  filename.value = uuidv4();
  flag.value = 0;
  let idx = 0; // chunk 갯수
  recordedChunks.length = 0
  recorder = new MediaRecorder(stream);
  recorder.ondataavailable = (e) => {
    if (e.data.size > 0) {
      recordedChunks.push(e.data);
      //console.log(idx);
      //console.log(flag);
      //console.log(e.data.type);
      idx++;
      if (idx >= 50) { // 녹화시간 150초 제한
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

    const jsonData = {
      filename: filename.value,
      username: userStore.email || "null",
      chunkNumber: idx,
      endOfChunk: flag.value,
    }
    formData.append('jsonData', new Blob([JSON.stringify(jsonData)], {
      type: "application/json",
    }))
    // axios를 사용하여 POST 요청을 서버로 보냄
    const response = await fileServer.uploadVideo(formData);
    //console.log('Chunk sent successfully!', response);
    if (response.status === 200) {
      console.log('upload success', response.data);
      uploadData.value = response.data;
    }
  } catch (error) {
    console.error('Error sending chunk to server:', error);
  }
}

const stopRecording = function () {
  mediaToggle.value.play = false;
  flag.value = 1;
  dialog.value = true;
  const previewPlayer = document.querySelector("#my-video");
  previewPlayer.srcObject.getTracks().forEach(track => track.stop());
  recorder.stop();
  recordedChunks.length = 0
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

// const submitTitle = function () {
//   dialog.value = false
//   dialog2.value = true
// }

const saveRecording = async function () {
  const date = new Date().toLocaleString()
  const req_body = {
    questionId : selfSpeechStore.selectedQuestion,
    videoLength : time.value,
    title : `${selfSpeechStore.questionData.question}-${date}`,
    videoInformation : {
        saveFilename: `${filename.value}.mkv`,
        originFilename: `${filename.value}.mkv`,
    },
    thumbnailInformation : {
        saveFilename: `${filename.value}.png`,
        originFilename: `${filename.value}.png`,
    }
  }
  try {
    const response = await apiMethods.saveVideo(req_body)
    console.log('save successfully!', response.data)
  } catch (error) {
    console.log(error)
  }
  dialog.value = false
  startVideo()
}

const cancelRecording = async function () {
  try {
    dialog.value = false
    const res = await fileServer.cancelUpload(userStore.email || "null", filename.value)
    console.log(res.data)
  } catch (error) {
    console.log(error)
  }
  startVideo()
}

const controlMedia = function (com) {
  const myStream = document.querySelector('#my-video').captureStream()

  if (com === 0) {
    mediaToggle.value.video = !mediaToggle.value.video
    myStream.getVideoTracks().forEach(track => {
      track.enabled = mediaToggle.value.video;
    });
  }
  else if (com === 1) {
    mediaToggle.value.audio = !mediaToggle.value.audio
    myStream.getAudioTracks().forEach(track => {
      track.enabled = mediaToggle.value.audio
    });
  }
}

// 음성 출력
const TTS = function () {
  const utterance = new SpeechSynthesisUtterance(TTSscript.value);
  synth.value.speak(utterance);
}

onMounted(() => {
  startVideo()
})

onBeforeUnmount(() => {
  const previewPlayer = document.querySelector("#my-video")
  if (previewPlayer.srcObject) {
      const tracks = previewPlayer.srcObject.getTracks();
      tracks.forEach(track => track.stop());
      previewPlayer.srcObject = null;
  }
})
</script>

<template>
<div class="container h-100 d-flex flex-column justify-space-between">
  <div class="nav-bar d-flex align-center">
    <div class="ma-1">{{ selfSpeechStore.questionData.question }}</div>
    <v-icon class="exit-btn ma-1 ml-auto" color="black" size="32" icon="mdi-close-circle-outline" @click="goSelfSpeechMain"></v-icon>
  </div>

  <div class="w-100 text-center pa-1">
    <video id="my-video" autoplay></video>
  </div>

  <div class="btn-container w-100 d-flex align-center">
    <v-btn class="ma-3" @click="controlMedia(0)" v-if="mediaToggle.video" icon="mdi-video"></v-btn>
    <v-btn class="ma-3" @click="controlMedia(0)" v-else icon="mdi-video-off" color="blue"></v-btn>
    <v-btn class="ma-3" @click="controlMedia(1)" v-if="mediaToggle.audio" icon="mdi-microphone"></v-btn>
    <v-btn class="ma-3" @click="controlMedia(1)" v-else icon="mdi-microphone-off" color="blue"></v-btn>
    <v-btn class="ma-3" @click="startRecording" v-if="!mediaToggle.play" icon="mdi-play" color="red" :disabled="!mediaToggle.video||!mediaToggle.audio||selfSpeechStore.selectedQuestion===-1"></v-btn>
    <v-btn class="ma-3" variant="tonal" @click="stopRecording" v-else icon="mdi-stop" color="red"></v-btn>
    <div class="timer ml-10" v-if="!mediaToggle.play"></div>
    <div class="timer ml-10" v-else-if="(time%60)>=10">{{ Math.floor(time/60) }}:{{ time%60 }}</div>
    <div class="timer ml-10" v-else>{{ Math.floor(time/60) }}:0{{ time%60 }}</div>
  </div>
</div>

<v-dialog v-model="dialog" width="auto">
  <v-card>
    <v-card-text>
      저장 하시겠습니까?
    </v-card-text>
    <div class="d-flex">
      <v-card-actions>
        <v-btn color="primary" block @click="saveRecording">저장</v-btn>
      </v-card-actions>
      <v-card-actions>
        <v-btn color="warning" block @click="cancelRecording">다시 연습</v-btn>
      </v-card-actions>
    </div>
  </v-card>
</v-dialog>

<!-- <v-dialog v-model="dialog2" width="auto">
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
</v-dialog> -->
</template>

<style scoped>
.nav-bar{
  border: 1px solid black;
  background-color: #f0f0f0;
}
.container{
  background-color: black;
}
#my-video{
  width: 100%;
  height: 390px;
  background-color: black;
}
.timer{
  color: white;
}
</style>
