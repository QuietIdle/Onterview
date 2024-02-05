<script setup>
import { ref, watch, onMounted } from 'vue'
import { v4 as uuidv4 } from 'uuid'
import TimerComponent from '@/components/meeting/Timer.vue'
import { apiMethods, fileServer } from "@/api/video"
import { useUserStore } from "@/stores/user"
import { useMeetingMultiStore } from "@/stores/meetingMulti"
import { postInterviewQuestions } from '@/api/interview'

const userStore = useUserStore()
const interviewStore = useMeetingMultiStore()
const mediaVideo = document.createElement('video')  // 비디오+오디오 스트리밍 영상(저장용)
const mediaOnlyVideo = ref(null)                    // 비디오 스트리밍 영상(송출용)
const filename = ref('')
const endOfChunk = ref(0)  // chunk 전송 완료 여부 {0: 전송중, 1: 마지막 chunk}
const isAcceptedPermission = ref(true)
const isWebcamOn = ref(false)
const isMicrophoneOn = ref(false)
const isAbleMeeting = ref(false)
const isInterviewInProgress = ref(false)
const dialogRequestPermissionMedia = ref(false) // 처음 권한을 요청할 때
const dialogDeniedPermissionMedia = ref(false)  // 권한 요청이 거부되었을 때

const watchWebcamOn = watch(isWebcamOn, () => {
  if (isWebcamOn.value === true) {
    if (isMicrophoneOn.value === true) {
      isAbleMeeting.value = true
    }
  } else {
    isAbleMeeting.value = false
  }
})

const watchMicrophoneOn = watch(isMicrophoneOn, () => {
  if (isMicrophoneOn.value === true) {
    if (isWebcamOn.value === true) {
      isAbleMeeting.value = true
    }
  } else {
    isAbleMeeting.value = false
  }
})

// 미디어 권한 요청 (마이크 & 카메라)
const requestPermissionMedia = function () {

  navigator.permissions.query({ name: 'camera' })
    .then(permissionStatus => {
      if (permissionStatus.state === 'prompt') {
        dialogRequestPermissionMedia.value = true
      }
    })

  navigator.mediaDevices
    .getUserMedia({
      video: { width: 1280, height: 720 },
      audio: true,
    })
    .then((stream) => {
      mediaVideo.srcObject = stream
      mediaOnlyVideo.value.srcObject = new MediaStream([stream.getVideoTracks()[0]])
      isWebcamOn.value = true
      isMicrophoneOn.value = true
      dialogRequestPermissionMedia.value = false
      isAcceptedPermission.value = true
    })
    .catch((error) => {
      console.error(error)
      dialogRequestPermissionMedia.value = false
      dialogDeniedPermissionMedia.value = true
    })
}

// 카메라 ON & OFF
const setupWebcam = function () {
  const stream = mediaVideo.srcObject
  isWebcamOn.value = !isWebcamOn.value
  stream.getVideoTracks().forEach(track => {
    track.enabled = isWebcamOn.value
  })
}

// 마이크 ON & OFF
const setupMicrophone = function () {
  const stream = mediaVideo.srcObject
  isMicrophoneOn.value = !isMicrophoneOn.value
  stream.getAudioTracks().forEach(track => {
    track.enabled = isMicrophoneOn.value
  })
}

const TTS = function (script) {
  const synth = window.speechSynthesis
  const utterance = new SpeechSynthesisUtterance(script)
  synth.speak(utterance)
}


const introduceSoloMeeting = function () {
  const TTSscript = `
    지금부터 모의 면접을 시작하겠습니다. \n
    모의 면접은 자기소개부터 마지막 맺음말까지 총 5개의 면접 문항이 주어집니다. \n
    주어지는 면접 문항을 듣고 각각 면접 답변을 진행하세요, \n
    `
  TTS(TTSscript)
}

const sendToServer = async function (chunk, idx) {
  try {
    // FormData를 생성하고 녹화된 데이터를 추가
    const formData = new FormData()
    formData.append('chunk', chunk)

    const jsonData = {
      filename: filename.value,
      username: userStore.email || "null",
      chunkNumber: idx,
      endOfChunk: endOfChunk.value,
    }
    formData.append('jsonData', new Blob([JSON.stringify(jsonData)], {
      type: "application/json",
    }))
    // axios를 사용하여 POST 요청을 서버로 보냄
    const response = await fileServer.uploadVideo(formData)
    //console.log('Chunk sent successfully!', response)
    if (response.status === 200) {
      console.log('upload success', response.data);
      uploadData.value = response.data
    }
  } catch (error) {
    console.error('Error sending chunk to server:', error)
  }
}

let recordedChunks = []
const startRecord = function () {
  const stream = mediaVideo.captureStream()
  filename.value = uuidv4()
  endOfChunk.value = 0
  let idx = 0  // chunk 개수
  recordedChunks.length = 0
  const recorder = new MediaRecorder(stream)
  recorder.ondataavailable = (e) => {
    if (e.data.size > 0) {
      recordedChunks.push(e.data)
      idx++
      if (idx >= 50) {
        stopRecord()
      }
    }
    sendToServer(e.data, idx)
  }
  recorder.start(3000)
}

const startInterview = function () {
  introduceSoloMeeting()
  for (let i = 0; i < 5; i++) {
    startRecord()
  }
  isInterviewInProgress.value = true
}

const finishInterview = function () {
  isInterviewInProgress.value = false
}

const requestInterviewQuestions = function () {

  const payload = {
    questionType: interviewStore.choice.value.type,
    // roomType: interviewStore.choice.value.

  }

  const success = function (response) {

  }

  const error = function (error) {

  }

  postInterviewQuestions(payload, success, error)
}

onMounted(() => {
  navigator.permissions.query({ name: 'camera' })
    .then(permissionStatus => {
      if (permissionStatus.state === 'granted') {
        requestPermissionMedia()
      } else {
        isAcceptedPermission.value = false
      }
    })
    .catch(error => {
      console.error('Error checking camera permission:', error)
    })
})

</script>

<template>
  <div class="container bg-grey-darken-4 text-grey-lighten-5">
    <div class="d-flex justify-center my-15">
      <h1>면접 환경을 세팅해주세요!</h1>
    </div>

    <v-row class="text-center">
      <div class="d-flex flex-column align-center my-auto offset-1 v-col-3 py-0 px-0">
        <TimerComponent :start-timer="isInterviewInProgress" /><br>
        <div v-if="!isInterviewInProgress">
          <v-btn :disabled="!isAbleMeeting" rounded="xl" size="x-large" class="active-btn mt-4 mx-2 px-15"
            @click="startInterview">면접 시작</v-btn>
        </div>
        <div v-else>
          <v-btn rounded="xl" size="x-large" class="active-btn mt-4 mx-2 px-15" @click="finishInterview">답변 완료</v-btn>
        </div>
      </div>
      <div class="video-container offset-1 v-col-6">
        <video ref="mediaOnlyVideo" autoplay></video>

        <div v-if="!isAcceptedPermission" class="video-activate text-black">
          <h3 class="mb-5">마이크와 카메라를 활성화 하면<br>모의 면접을 진행할 수 있어요!</h3>
          <v-btn class="bg-primary" @click="requestPermissionMedia">마이크 및 카메라 활성화</v-btn>
        </div>
      </div>
    </v-row>
    <div class="offset-5 v-col-6">
      <div class="d-flex justify-center mt-3 text-black">
        <!-- 웹캠/마이크 활성화 버튼 -->
        <v-col cols="auto">
          <v-btn v-if="!isWebcamOn" icon="mdi-video-off" size="large" class="bg-error mx-2" @click="setupWebcam"></v-btn>
          <v-btn v-else icon="mdi-video" size="large" class="mx-2" @click="setupWebcam"></v-btn>
          <v-btn v-if="!isMicrophoneOn" icon="mdi-microphone-off" size="large" class="bg-error mx-2"
            @click="setupMicrophone"></v-btn>
          <v-btn v-else icon="mdi-microphone" size="large" class="mx-2" @click="setupMicrophone"></v-btn>
        </v-col>
      </div>
    </div>
  </div>

  <!-- dialogRequestPermissionMedia -->
  <v-dialog v-model="dialogRequestPermissionMedia" width="auto">
    <v-card class="py-5">
      <v-card-title class="text-primary text-center">
        권한을 허용해주세요 👀
      </v-card-title>
      <v-card-text>
        ✔ 마이크와 카메라를 활성화 하면 모의 면접을 진행할 수 있어요!
      </v-card-text>
      <v-card-actions>
        <v-btn color="primary" block @click="dialogRequestPermissionMedia = false">닫기</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>

  <!-- dialogDeniedPermissionMedia -->
  <v-dialog v-model="dialogDeniedPermissionMedia" width="auto">
    <v-card class="py-5">
      <v-card-title class="text-primary text-center">
        권한이 차단 되어 있어요😂
      </v-card-title>
      <v-card-text>
        ✔ 주소 입력창 좌측 '사이트 정보 보기' 아이콘을 클릭하여 권한을 변경해주세요!<br>
        ✔ 마이크와 카메라를 활성화 하면 모의 면접을 진행할 수 있어요!
      </v-card-text>
      <v-card-actions>
        <v-btn color="primary" block @click="dialogDeniedPermissionMedia = false">닫기</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<style scoped>
html,
body {
  width: 100%;
  height: 100%;
}

.active-btn {
  background-color: #8747AE;
  color: white;
}

.container {
  position: fixed;
  width: 100%;
  height: 100%;
}

.video-container {
  width: 100%;
  aspect-ratio: 16 / 9;
  background-color: white;
  position: relative;
  border-radius: 10px;
}

.video-container video {
  width: 100%;
  aspect-ratio: 16 / 9;
  position: absolute;
  /* padding: 5px; */
  top: 0;
  left: 0;
  border-radius: 10px;
}

.video-activate {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
</style>