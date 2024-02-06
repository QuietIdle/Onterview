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
    typeDetail: ''
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

  return { dialog, choice, stompType, mediaToggle }
})

export const useWebsocketStore = defineStore('websocket', () => {
  const OVToken = ref(undefined)
  const sessionId = ref(null)
  const stomp = ref(undefined)

  return { OVToken, sessionId, stomp }
}, ) 