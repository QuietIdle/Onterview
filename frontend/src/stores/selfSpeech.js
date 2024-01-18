import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useSelfSpeechStore = defineStore('selfSpeech', () => {
  const display = ref(true);

  return { display }
})
