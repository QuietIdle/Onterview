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

  return { dialog, choice, stompType }
})

export const useWebsocketStore = defineStore('websocket', () => {
  const OVToken = ref(undefined)

  return { OVToken }
}, {persist: true}) 