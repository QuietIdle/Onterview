<script setup>
import { ref } from "vue";
import interviewMultiConfig from "./interviewMultiConfig.vue";
import interviewMultiHelp from "@/components/interview/interviewMultiHelp.vue"
import { useInterviewStore, useWebsocketStore } from "@/stores/interview";
import { useUserStore } from "@/stores/user";
import Stomp from "stompjs"
import { useRouter } from "vue-router";

const router = useRouter()
const userStore = useUserStore()
const authToken = userStore.accessToken

const interviewStore = useInterviewStore()
const websocketStore = useWebsocketStore()
const selectedTab = ref(0)

let stomp

const time = ref({
  second: 0,
  match: false,
}) // 대기 시간, 매칭 여부
let timerId;

const startTimer = function() {
  time.value.second++;
  stopTimer();
  timerId = setTimeout(startTimer, 1000); // 스탑워치 주기 1초
}

const stopTimer = function() {
  if (timerId !== null) {
    clearTimeout(timerId);
  }
}

const startMatch = function () {
  startTimer()
  time.value.match = true

  const socket = new WebSocket('ws://70.12.247.51:8081/api/meeting/matching');
  stomp = Stomp.over(socket);
  const header = {
        Authorization: `${authToken}`
    }
  stomp.connect(header, () => {
    stomp.subscribe(`/sub/${interviewStore.stompType}`, function (message) {
      //console.log(message.body);
    });
    stomp.subscribe(`/user/sub/${interviewStore.stompType}`, function (message) {
      websocketStore.token = JSON.parse(message.body).token
      console.log('token accepted successfully!')
      stomp.disconnect()

      interviewStore.dialog.wait = false
      stopTimer()
      time.value.match = false
      time.value.second = 0
      
      router.push({name: 'interview-multi'})
    });
    
    stomp.send("/pub/enter", {}, JSON.stringify({
    type: "ENTER",
	  roomId: interviewStore.stompType,
	  matchCount: 4,
    }))
    // stomp.send("/pub/match", {}, JSON.stringify({
    // type: "MATCH",
	  // roomId: interviewStore.stompType,
	  // matchCount: 4,
    // }))
  }, error => {
    console.error(error);
  })
}

const stopMatch = function () {
  interviewStore.dialog.wait = false

  if (time.value.match) {
    stopTimer()
    time.value.match = false
    time.value.second = 0
    
    stomp.disconnect()
  }
}

const goRoom = function () {
  interviewStore.dialog.wait = false
  router.push({name: 'interview-multi'})
}
</script>

<template>
  <v-dialog 
    v-model="interviewStore.dialog.wait" 
    fullscreen
  >
    <v-card class="bg-purple-lighten-4 pa-5">
      <v-card-title class="text-center">
        다인 모의 면접 대기실
      </v-card-title>
      <v-divider class="border-opacity-100"></v-divider>
      <div class="mt-5 d-flex justify-space-between">
        <v-btn-toggle 
          v-model="selectedTab"
          mandatory
          color="purple-darken-3"
          class="btn-container"
        >
          <v-btn elevation="2">
            환경설정
          </v-btn>
          <v-btn elevation="2">
            도움말
          </v-btn>
        </v-btn-toggle>
        <div v-if="!interviewStore.choice.typeDetail">{{ interviewStore.choice.type }}</div>
        <div v-else>{{ interviewStore.choice.type }} - {{ interviewStore.choice.typeDetail }}</div>
      </div>

      <v-card-text class="bg-white">
        <div v-show="selectedTab===0" class="w-100 h-100">
          <interviewMultiConfig />
        </div>

        <div v-show="selectedTab===1" class="w-100 h-100">
          <interviewMultiHelp />
        </div>
      </v-card-text>

      <v-card-actions class="d-flex flex-row-reverse btns">
        <v-btn @click="stopMatch" class="ma-1" rounded elevation="4" size="x-large" style="background-color: #9B9B9B;">매칭취소</v-btn>
        <v-btn v-if="!time.match" @click="startMatch" class="ma-1" rounded elevation="4" size="x-large" style="background-color: #A069B3;">매칭시작</v-btn>
        <v-btn v-else class="ma-1" rounded elevation="4" size="x-large" style="background-color: #A069B3;" disabled>매칭 대기 시간 {{ String(Math.floor(time.second/60)).padStart(2,'0') }} : {{ String(time.second%60).padStart(2,'0') }}</v-btn>
        <v-btn @click="goRoom" class="ma-1" rounded elevation="4" size="x-large" style="background-color: #9B9B9B;">강제 입장</v-btn>
      </v-card-actions>

    </v-card>
  </v-dialog>
</template>

<style scoped>

</style>