<script setup>
import { ref, watch, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { v4 as uuidv4 } from 'uuid'
import TimerComponent from '@/components/interview/Timer.vue'
import { apiMethods, fileServer } from "@/api/video"
import { useUserStore } from "@/stores/user"
import { useInterviewStore } from "@/stores/interview"
import { postInterviewQuestions } from '@/api/interview'

const router = useRouter()
const userStore = useUserStore()
const interviewStore = useInterviewStore()

const startTime = ref(Date.now())
const timeDifference = ref(0)
const timerId = ref(null)

const questionList = ref([])
const mediaVideo = document.createElement('video')  // 비디오+오디오 스트리밍 영상(저장용)
const mediaOnlyVideo = ref(null)                    // 비디오 스트리밍 영상(송출용)
const filename = ref('')
const endOfChunk = ref(0)  // chunk 전송 완료 여부 {0: 전송중, 1: 마지막 chunk}
const isAcceptedPermission = ref(true)
const isWebcamOn = ref(false)
const isMicrophoneOn = ref(false)
const isActiveTimer = ref(false)
const isAbleInterview = ref(false)
const isInterviewInProgress = ref(false)
const dialogRequestPermissionMedia = ref(false) // 처음 권한을 요청할 때
const dialogDeniedPermissionMedia = ref(false)  // 권한 요청이 거부되었을 때

const watchWebcamOn = watch(isWebcamOn, () => {
  if (isWebcamOn.value === true) {
    if (isMicrophoneOn.value === true) {
      isAbleInterview.value = true
    }
  } else {
    isAbleInterview.value = false
  }
})

const watchMicrophoneOn = watch(isMicrophoneOn, () => {
  if (isMicrophoneOn.value === true) {
    if (isWebcamOn.value === true) {
      isAbleInterview.value = true
    }
  } else {
    isAbleInterview.value = false
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

const updateTime = function () {
  timeDifference.value = Math.floor((Date.now() - startTime.value) / 1000)
}

const formatTime = function (seconds) {
  // 초를 'mm:ss' 형식의 문자열로 변환
  const minutes = Math.floor(seconds / 60)
  const remainingSeconds = seconds % 60
  return `${minutes < 10 ? '0' + minutes : minutes}:${remainingSeconds < 10 ? '0' + remainingSeconds : remainingSeconds}`
}

const sleep = function (ms) {
  return new Promise(resolve => setTimeout(resolve, ms))
}

// text to speech
const TTS = function (script) {
  return new Promise((resolve, reject) => {
    setupMicrophone()  // 전자 음성이 나오는 동안 마이크 음소거
    const synth = window.speechSynthesis
    const utterance = new SpeechSynthesisUtterance(script)

    utterance.onend = function () {
      resolve()
    };

    utterance.onerror = function (error) {
      reject(error)
    };

    synth.speak(utterance)
    setupMicrophone()  // 전자 음성 종료 후 면접 답변을 위한 마이크 활성화
  })
}

const TTScancel = function () {
  if ('speechSynthesis' in window) {
    window.speechSynthesis.cancel();
  }
}

// 1인 면접 시작 전자 음성
const introduceInterviewSolo = async function () {
  const TTSscript = `
    지금부터 모의 면접을 시작하겠습니다. \n
    `
  // 모의 면접은 자기소개부터 마지막 맺음말까지 총 5개의 면접 문항이 주어집니다. \n
  // 주어지는 면접 문항을 듣고 각각 면접 답변을 진행하세요, \n
  await TTS(TTSscript)
}

// 면접 답변 진행 (타이머 On)
const answerInterviewSolo = async function (script) {
  isActiveTimer.value = true

  // 이벤트가 발생할 때까지 무한 루프로 대기
  while (isActiveTimer.value) {
    await sleep(100)  // 짧은 간격으로 확인
  }
}

// 1인 면접 종료 전자 음성
const closingInterviewSolo = async function () {
  const TTSscript = `
    수고하셨습니다. 모의 면접을 종료합니다.
    `
  await TTS(TTSscript)
}

// 하나의 면접 문항에 대한 인터뷰 진행
const interviewOneQuestion = async function (script) {
  await TTS(script)
  startRecord()
  await answerInterviewSolo()
  saveRecording()
}

// 면접 문항 조기 종료 (답변 완료)
const finishOneQuestion = async function () {
  isActiveTimer.value = false
}

// 인터뷰 시작
const startInterview = async function () {
  let isPossibie = true
  mediaVideo.srcObject.getVideoTracks().forEach(track => {
    if (track.readyState != "live") {
      alert(`비디오가 활성화 되어 있지 않다면 면접을 진행할 수 없어요 😂`)
      isPossibie = false
      isWebcamOn.value = false
    }
  })

  if (!isPossibie) {
    return
  }

  try {
    // 면접 질문 요청을 기다립니다.
    const response = await requestInterviewQuestions()

    // 면접이 불가능한 경우 처리합니다.
    if (!response) {
      console.log(response)
      alert(`알 수 없는 이유로 모의 면접을 진행할 수 없습니다. \n관리자에게 문의해주세요.`)
      return
    }

    // 스톱워치 실행
    startTime.value = Date.now()
    timerId.value = setInterval(updateTime, 1000)

    isInterviewInProgress.value = true
    await introduceInterviewSolo()
    for (let i = 0; i < 5; i++) {
      await interviewOneQuestion(questionList.value[i].commonQuestion)
      if (i < 4) {
        await TTS(`다음 질문입니다.`)
      }
    }
    await closingInterviewSolo()
    stopInterview()
  } catch (error) {
    alert(`알 수 없는 이유로 모의 면접을 진행할 수 없습니다. \n관리자에게 문의해주세요.`)
    console.error('오류 발생:', error)
  }
}

const stopInterview = async function () {
  isInterviewInProgress.value = false
  TTScancel()
  timerId.value = null
}

let recorder
let recordedChunks = []

// 녹화 시작(면접 문항 제시부터)
const startRecord = async function () {
  const stream = mediaVideo.captureStream()
  filename.value = uuidv4()
  endOfChunk.value = 0
  let idx = 0  // chunk 개수
  recordedChunks.length = 0

  recorder = new MediaRecorder(stream)
  recorder.ondataavailable = async (e) => {
    idx++
    if (e.data.size > 0) {
      recordedChunks.push(e.data)
    }
    sendToServer(e.data, idx)

    if (!isActiveTimer.value | idx > 25) {
      console.log(recorder)
      console.log(e.data, idx, endOfChunk.value, !isActiveTimer.value)
      stopRecord()
    }
  }
  recorder.start(3000)
}

// 녹화 종료
const stopRecord = async function () {
  endOfChunk.value = 1
  await recorder.stop()
  recordedChunks.length = 0
  finishOneQuestion()
}

// 녹화 영상 저장
const saveRecording = async function () {
  const date = new Date().toLocaleString()
  const req_body = {
    // questionId : 현재 면접 문항 id,
    videoLength: 0,  // TODO
    title: `${"현재 면접 문항 id"}-${date}`,
    videoInformation: {
      saveFilename: `${filename.value}.mkv`,
      originFilename: `${filename.value}.mkv`,
    },
    thumbnailInformation: {
      saveFilename: `${filename.value}.png`,
      originFilename: `${filename.value}.png`,
    },
    category: 2,
  }
  try {
    const response = await apiMethods.saveVideo(req_body)
    console.log('save successfully!', response.data)
  } catch (error) {
    console.log(error)
  }
}

const requestInterviewQuestions = function () {
  return new Promise((resolve, reject) => {
    const payload = {
      questionType: interviewStore.choice.typeDetail,
      roomType: interviewStore.choice.people,
      numToSelect: 5,

    }

    const success = function (response) {
      questionList.value = response.data
      // console.log(response.data)
      resolve(response); // 응답 데이터를 반환합니다.
    }

    const error = function (error) {
      console.error(error)
      reject(error); // 오류를 반환합니다.
    }

    postInterviewQuestions(payload, success, error)
  })
}

// 스트리밍 영상 청크 단위 전송(to 파일 서버)
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
      // uploadData.value = response.data
    }
  } catch (error) {
    console.error('Error sending chunk to server:', error)
  }
}

const toInterviewMain = function () {
  router.push({ name: 'interview' })
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

onUnmounted(() => {
  // stopRecord()
})

</script>

<template>
  <div class="container bg-grey-darken-4 text-grey-lighten-5">
    <div class="d-flex align-center my-0 py-0" style="border-bottom: 1px solid white;">
      <div class="m-auto px-4 py-2">{{ interviewStore.choice.type }}</div>
      <div class="m-auto px-4 py-2">
        <v-icon icon="mdi-radiobox-marked" class="mx-1" color="red"></v-icon>
        {{ formatTime(timeDifference) }}
      </div>
      <div class="mx-auto"></div>
      <div v-if="isInterviewInProgress" class="px-4 py-2" style="border-left: 1px solid white;">
        <v-btn variant="plain" @click="stopInterview">면접종료</v-btn>
      </div>
      <div class="px-4 py-2" style="border-left: 1px solid white; border-right: 1px solid white;">
        <v-btn variant="plain" @click="toInterviewMain">나가기</v-btn>
      </div>
    </div>

    <div class="d-flex justify-center my-15">
      <h1>면접 환경을 세팅해주세요!</h1>
    </div>

    <v-row class="text-center">
      <div class="d-flex flex-column align-center my-auto offset-1 v-col-3 py-0 px-0">
        <TimerComponent :start-timer="isActiveTimer" @finish-timer="isActiveTimer = false" /><br>
        <div v-if="!isInterviewInProgress">
          <v-btn :disabled="!isAbleInterview" rounded="xl" size="x-large" class="active-btn mt-4 mx-2 px-15"
            @click="startInterview">면접 시작</v-btn>
        </div>
        <div v-else>
          <v-btn :disabled="!isActiveTimer" rounded="xl" size="x-large" class="active-btn mt-4 mx-2 px-15"
            @click="finishInterview">답변 완료</v-btn>
        </div>
      </div>
      <div class="video-container offset-1 v-col-6">
        <video ref="mediaOnlyVideo" autoplay></video>

        <div v-if="!isAcceptedPermission" class="video-activate text-black">
          <h3 class="mb-5">마이크와 카메라를 활성화 하면<br>모의 면접을 진행할 수 있어요!</h3>
          <v-btn class="bg-primary" @click="requestPermissionMedia">마이크 및 카메라 활성화</v-btn>
        </div>

        <div class="d-flex justify-center mt-3 text-black">
          <!-- 웹캠/마이크 활성화 버튼 -->
          <v-col cols="auto">
            <v-btn v-if="!isWebcamOn" icon="mdi-video-off" size="large" class="bg-error mx-2"
              @click="setupWebcam"></v-btn>
            <v-btn v-else icon="mdi-video" size="large" class="mx-2" @click="setupWebcam"></v-btn>
            <v-btn v-if="!isMicrophoneOn" icon="mdi-microphone-off" size="large" class="bg-error mx-2"
              @click="setupMicrophone"></v-btn>
            <v-btn v-else icon="mdi-microphone" size="large" class="mx-2" @click="setupMicrophone"></v-btn>
          </v-col>
        </div>
      </div>
    </v-row>
    <div class="offset-5 v-col-6">

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

video {
  transform: rotateY(180deg);
  -webkit-transform: rotateY(180deg);
  /* Safari and Chrome */
  -moz-transform: rotateY(180deg);
  /* Firefox */
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
  position: relative;
  overflow: hidden;
  border-radius: 10px;
}

.video-container video {
  width: 100%;
  height: auto;
  display: block;
  border-radius: 10px;
}

.video-activate {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.border-white {
  border: 2px solid white;
  /* 흰색 테두리 추가 */
  box-sizing: border-box;
  /* 내부 패딩과 테두리를 요소의 크기에 포함시킴 */
}
</style>