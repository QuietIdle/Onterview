<script setup>
import { ref, watch, onMounted } from 'vue'

const mediaVideo = ref(null)
const isAcceptedPermission = ref(true)
const isWebcamOn = ref(false)
const isMicrophoneOn = ref(false)
const dialogRequestPermissionMedia = ref(false) // 처음 권한을 요청할 때
const dialogDeniedPermissionMedia = ref(false)  // 권한 요청이 거부되었을 때

// 미디어 권한 요청 (마이크 & 카메라)
const requestPermissionMedia = function () {

  navigator.permissions.query({ name: 'camera' })
    .then(permissionStatus => {
      if (permissionStatus.state === 'prompt') {
        dialogRequestPermissionMedia.value = true
      }
    })

  const stream = navigator.mediaDevices
    .getUserMedia({
      video: { width: 1280, height: 720 },
      audio: true,
    })
    .then((stream) => {
      mediaVideo.value.srcObject = stream
      isWebcamOn.value = true
      isMicrophoneOn.value = true
      dialogRequestPermissionMedia.value = false
      isAcceptedPermission.value = true
    })
    .catch((error) => {
      dialogRequestPermissionMedia.value = false
      dialogDeniedPermissionMedia.value = true
    })
}

// 카메라 ON & OFF
const setupWebcam = function () {
  const stream = mediaVideo.value.srcObject
  isWebcamOn.value = !isWebcamOn.value
  stream.getVideoTracks().forEach(track => {
    track.enabled = isWebcamOn.value
  })
}

// 마이크 ON & OFF
const setupMicrophone = async function () {
  const stream = mediaVideo.value.srcObject
  isMicrophoneOn.value = !isMicrophoneOn.value
  stream.getAudioTracks().forEach(track => {
    track.enabled = isMicrophoneOn.value
  })
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

console.log(isAcceptedPermission)
</script>

<template>
  <div class="container bg-grey-darken-4 text-grey-lighten-5">
    <div class="d-flex justify-center my-15">
      <h1>면접 환경을 세팅해주세요!</h1>
    </div>

    <v-row class="text-center">
      <div class="offset-1 v-col-3">타이머 등</div>
      <div class="video-container offset-1 v-col-6">
        <video ref="mediaVideo" autoplay></video>

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
          <v-btn v-if="!isWebcamOn" icon="mdi-video-off" size="small" class="mx-1" @click="setupWebcam"></v-btn>
          <v-btn v-else icon="mdi-video" size="small" class="mx-1" @click="setupWebcam"></v-btn>
          <v-btn v-if="!isMicrophoneOn" icon="mdi-microphone-off" size="small" class="mx-1"
            @click="setupMicrophone"></v-btn>
          <v-btn v-else icon="mdi-microphone" size="small" class="mx-1" @click="setupMicrophone"></v-btn>
          <!-- <v-btn icon size="small" class="mx-1" @click="setupMicrophone">
            <img src="@/assets/icons/microphone-off.png" style="width: 50%;" />
          </v-btn> -->
        </v-col>
        <!-- <button class="text-white" @click="setupWebcam">웹캠/마이크 활성화</button>
        <button class="text-white" @click="setupMicrophone">마이크 활성화</button> -->
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