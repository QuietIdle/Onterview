import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useMeetingMultiStore = defineStore('meetingMulti', () => {
  const dialog = ref({
    match: false,
    help: false,
  });
  const choice = ref({
    people: 'solo',
    type: '인성면접',
    typeDetail: 'backend'
  })

  return { dialog, choice }
})