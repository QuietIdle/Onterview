<script setup>
import { ref, onUnmounted } from "vue";
import meetingMultiConfig from "./meetingMultiConfig.vue";
import meetingMultiHelp from "@/components/meetingMulti/meetingMultiHelp.vue"
import { useMeetingMultiStore } from "@/stores/meetingMulti";
import Stomp from "stompjs"
import { useUserStore } from "@/stores/user";

const userStore = useUserStore()
const authToken = userStore.accessToken

const meetingMultiStore = useMeetingMultiStore()
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
  console.log(authToken)

  const socket = new WebSocket('ws://70.12.247.51:8080/api/meeting/matching');
  stomp = Stomp.over(socket);
  
  stomp.connect({
        Authorization: `${authToken}`
    }, () => {
    stomp.subscribe(`/sub/${meetingMultiStore.stompType}`, function (message) {
      console.log(message.body);
    });
    stomp.subscribe(`/user/sub/${meetingMultiStore.stompType}`, function (message) {
      console.log(message.body);
    });
    
    stomp.send("/pub/enter", {}, JSON.stringify({
    type: "ENTER",
	  roomId: meetingMultiStore.stompType,
	  matchCount: 4,
    }))
    stomp.send("/pub/match", {}, JSON.stringify({
    type: "MATCH",
	  roomId: meetingMultiStore.stompType,
	  matchCount: 4,
    }))
  }, error => {
    console.error(error);
  })
}

const stopMatch = async function () {
  meetingMultiStore.dialog.wait = false
  stopTimer()
  time.value.match = false
  time.value.second = 0
  try {
    await stomp.send("/pub/leave", {}, JSON.stringify({
      type: "LEAVE",
      roomId: meetingMultiStore.stompType,
      matchCount: 4,
    }))
  } catch (error) {
    console.log(error)
  }
  stomp.disconnect()
}
</script>

<template>
  <v-dialog 
    v-model="meetingMultiStore.dialog.wait" 
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
        <div v-if="!meetingMultiStore.choice.typeDetail">{{ meetingMultiStore.choice.type }}</div>
        <div v-else>{{ meetingMultiStore.choice.type }} - {{ meetingMultiStore.choice.typeDetail }}</div>
      </div>

      <v-card-text class="bg-white">
        <div v-show="selectedTab===0" class="w-100 h-100">
          <meetingMultiConfig />
        </div>

        <div v-show="selectedTab===1" class="w-100 h-100">
          <meetingMultiHelp />
        </div>
      </v-card-text>

      <v-card-actions class="d-flex flex-row-reverse btns">
        <v-btn @click="stopMatch" class="ma-1" rounded elevation="4" size="x-large" style="background-color: #9B9B9B;">매칭취소</v-btn>
        <v-btn v-if="!time.match" @click="startMatch" class="ma-1" rounded elevation="4" size="x-large" style="background-color: #A069B3;">매칭시작</v-btn>
        <v-btn v-else class="ma-1" rounded elevation="4" size="x-large" style="background-color: #A069B3;" disabled>매칭 대기 시간 {{ String(Math.floor(time.second/60)).padStart(2,'0') }} : {{ String(time.second%60).padStart(2,'0') }}</v-btn>
      </v-card-actions>

    </v-card>
  </v-dialog>
</template>

<style scoped>

</style>