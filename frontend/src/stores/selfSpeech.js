import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useSelfSpeechStore = defineStore('selfSpeech', () => {
  const display = ref(true);
  const listIdx = ref(1);
  const selectedQuestion = ref(-1);
  const questionData = ref({
    answser: null,
    question: "질문을 선택하세요",
    videos: [],
  });

  return { display,listIdx,selectedQuestion,questionData }
})
