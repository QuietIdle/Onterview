import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useInterviewStore = defineStore('interview', () => {
  const dialog = ref({
    match: false,
    help: false,
  });
  const choice = ref({
    people: 'SINGLE',
    type: '인성면접',
    typeDetail: 'FIT'
  })
  const stompType = computed(() => {
    if (choice.value.type === '인성면접') return 1
    else if (choice.value.typeDetail === 'BACKEND') return 2
    else return 3
  })
  const mediaToggle = ref({
    video: true,
    audio: true,
    volume: true,
  })

  const script = ref({
    enter: "다인 모의 면접장입니다.",
    start: "시작합니다",
    proceeding: "다음 사람 준비해 주세요",
    timeout: "",
    finish: "자리가 변경됩니다",
    end: "수고 하셨습니다",
  })

  const TTS = function (script) {
    return new Promise((resolve, reject) => {
      mediaToggle.value.audio = false
      const synth = window.speechSynthesis
      const utterance = new SpeechSynthesisUtterance(script)
  
      utterance.onend = function () {
        resolve()
      };
  
      utterance.onerror = function (error) {
        reject(error)
      };
  
      synth.speak(utterance)
      mediaToggle.value.audio = true
    })
  }

  return { dialog, choice, stompType, mediaToggle, script, TTS }
})

export const useWebsocketStore = defineStore('websocket', () => {
  const roomData = ref({
    token: undefined,
    sessoinId: undefined,
    index: undefined,
  })
  const stomp = ref(undefined)

  const flag = ref({
    interviewer: true,
    room: true,
  })
  const message = ref({
    type: undefined,
  })
  const now = ref({
    turn: -1,
    question: {
      id: 0,
      content: "1분 자기소개",
    },
    orders: [],
    people: 3,
  })
  const myTurn = computed(() => {
    if(now.value.turn === -1) return false
    return roomData.value.index === now.value.orders[now.value.turn]
  })

  return { roomData, stomp, flag, message, now, myTurn }
}, ) 