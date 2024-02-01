import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useMeetingMultiStore = defineStore('meetingMulti', () => {
  const dialog = ref({
    match: false,
    help: false,
  });
  const choice = ref({
    people: 'solo',
    type: '인성면접',
    typeDetail: undefined
  })
  const stompType = computed(() => {
    if (choice.value.type === '인성면접') return 1
    else if (choice.value.typeDetail === 'backend') return 2
    else return 3
  })

  return { dialog, choice, stompType }
})