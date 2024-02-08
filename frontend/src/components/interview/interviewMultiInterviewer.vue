<script setup>
import { ref, watch } from "vue";
import mainImg from '@/assets/interview/interviewMainIcon.png'
import { useInterviewStore, useWebsocketStore } from "@/stores/interview";
import interviewMultiHelpModal from "@/components/interview/interviewMultiHelpModal.vue"
import TimerComponent from '@/components/interview/Timer.vue'

const interviewStore = useInterviewStore()
const websocketStore = useWebsocketStore()

const isActiveTimer = ref(false)
const logMessages = ref([])

const addLog = function (text) {
  logMessages.value.push({
    message: text,
  })
}

const closeLog = function (idx) {
  logMessages.value.splice(idx, 1)
}

const controlMedia = function (com) {
  if (com === 0) {
    interviewStore.mediaToggle.video = !interviewStore.mediaToggle.video
  }
  else if (com === 1) {
    interviewStore.mediaToggle.audio = !interviewStore.mediaToggle.audio
  }
  else if (com === 2) {
    interviewStore.mediaToggle.volume = !interviewStore.mediaToggle.volume
  }
}

const openHelp = function () {
  interviewStore.dialog.help = true
}

const finishAnswer = async function () {
  
  // websocketStore.stomp.send(`/server/answer/${websocketStore.roomData.sessionId}`, {},
  //   JSON.stringify({
  //     type: 'PROCEEDING',
  //     index: websocketStore.roomData.index,
  //   })
  // )
  await sendMessage('PROCEEDING', websocketStore.now.turn)
}

const goTimer = function () {
  isActiveTimer.value = true
}

const sendMessage = async function (type, idx) {
  
  await websocketStore.stomp.send(`/server/answer/${websocketStore.roomData.sessionId}`, {}, JSON.stringify({
    type: type,
    index: idx,  
  }))
  
}

watch(() => websocketStore.flag.interviewer, async () => {
  switch (websocketStore.message.type) {
    case 'ENTER':
      addLog("다인 모의 면접에 입장하셨습니다.")
      break;

    case 'START':
      addLog(`${websocketStore.now.question}번 질문 시작!`)
      websocketStore.now.turn = 0
      if(websocketStore.now.myTurn) addLog("당신의 차례입니다.") 
      //await interviewStore.TTS(interviewStore.script.start)
      setTimeout(goTimer, 2000)
      break;

    case 'PROCEEDING':
      isActiveTimer.value = false
      addLog(`${websocketStore.now.turn}번 째 참가자 답변 완료`)
      if(websocketStore.now.myTurn) addLog("당신의 차례입니다.") 
      //await interviewStore.TTS(interviewStore.script.proceeding)
      setTimeout(goTimer, 2000)
      break;

    case 'TIMEOUT':
      break;

    case 'FINISH':
      isActiveTimer.value = false
      addLog(`${websocketStore.now.turn}번 째 참가자 답변 완료`)
      addLog(`${websocketStore.now.question}번 질문 종료...`)
      websocketStore.now.question += 1;
      websocketStore.now.turn = -1
      break;

    case 'END':
      isActiveTimer.value = false
      websocketStore.now.turn = -1
      alert('면접 종료!!!')
      break;
  }}
)

</script>

<template>
  <div class="w-100 h-100 d-flex justify-space-between align-center">
    
    <div class="h-100" style="width: 30%;">
      
      <div class="w-100 d-flex justify-space-between pa-2 ma-2" style="border: 1px solid white; border-radius: 12px;" v-for="(log, idx) in logMessages" :key="idx">
        <v-icon icon="mdi-alert-outline" color="#FF8911"></v-icon>
        <div class="w-100 mx-1">
          <div>{{ log.message }}</div>
        </div>
        <v-icon  @click="closeLog(idx)" icon="mdi-close"></v-icon>
      </div>

    </div>

    <div class="h-100 d-flex align-center justify-space-between" style="width: 60%;">
      <div class="d-flex justify-center align-center">
        <div class="">
          <v-img :src="mainImg" width="200" @click="console.log(websocketStore.myTurn)"></v-img>
        </div>
      </div>

      <div class="d-flex flex-column align-center my-auto offset-1 v-col-3 py-0 px-0">
        <TimerComponent :start-timer="isActiveTimer" style="width: 150px; height: 150px;" />
        <v-btn 
          v-if="websocketStore.myTurn"
          @click="finishAnswer" 
          :disabled="!isActiveTimer" 
          rounded="xl" 
          size="x-large" 
          class="active-btn mt-4 mx-2 px-15"
        >답변 완료</v-btn>
      </div>

      <div>
        <div>
          현재 턴 : {{ websocketStore.now.turn }}
        </div>
        <div>
          현재 발화자 : {{ websocketStore.now.orders[websocketStore.now.turn] }}
        </div>
        <div>
          내 번호 : {{ websocketStore.roomData.index }}
        </div>
      </div>

      <div class="btn-container d-flex flex-column h-100">
        <v-btn class="ma-3" @click="controlMedia(0)" v-if="interviewStore.mediaToggle.video" icon="mdi-video" color="grey-lighten-1"></v-btn>
        <v-btn class="ma-3" @click="controlMedia(0)" v-else icon="mdi-video-off" color="grey-lighten-1"></v-btn>
        <v-btn class="ma-3" @click="controlMedia(1)" v-if="interviewStore.mediaToggle.audio" icon="mdi-microphone" color="grey-lighten-1"></v-btn>
        <v-btn class="ma-3" @click="controlMedia(1)" v-else icon="mdi-microphone-off" color="grey-lighten-1"></v-btn>
        <v-btn class="ma-3" @click="controlMedia(2)" v-if="interviewStore.mediaToggle.volume" icon="mdi-volume-high" color="grey-lighten-1"></v-btn>
        <v-btn class="ma-3" @click="controlMedia(2)" v-else icon="mdi-volume-off" color="grey-lighten-1"></v-btn>
        <v-btn class="ma-3" @click="openHelp" icon="mdi-help" color="grey-lighten-1"></v-btn>
      </div>
    </div>

  </div>

  <interviewMultiHelpModal />
</template>

<style scoped>
</style>