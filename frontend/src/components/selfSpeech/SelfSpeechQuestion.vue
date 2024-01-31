<script setup>
import draggable from 'vuedraggable'
import { onMounted } from 'vue'
import { apiMethods } from "@/api/video"

// store
import { storeToRefs } from 'pinia'
import { useQuestionStore } from '@/stores/question.js'
import { useSelfSpeechStore } from "@/stores/selfSpeech"

const questionStore = useQuestionStore()
const selfSpeechStore = useSelfSpeechStore()
const { myQuestionList } = storeToRefs(questionStore)

onMounted(() => {
  questionStore.requestMyQuestionList()
})

const selectQuestion = async function(ele) {
  selfSpeechStore.selectedQuestion = ele.myQuestionId
  try {
    const result = await apiMethods.getVideoAll(selfSpeechStore.selectedQuestion);
    selfSpeechStore.questionData = result.data;
    console.log(result.data)
  } catch (error) {
    console.log(error)
  }
}
</script>

<template>
  <div class="question-title pa-3">나의 면접 문항 목록</div>
  <div style="max-height: 80%; overflow-y: auto">
    <v-expansion-panels variant="accordion">
      <v-expansion-panel
        v-for="folder in myQuestionList"
        :key="folder.myQuestionFolderId"
        :title="folder.myQuestionFolder"
        :value="folder.myQuestionFolderId"
      >
        <draggable
          :list="folder.myQuestionList"
          :group="{ name: 'question', pull: 'clone', put: false }"
          item-key="myQuestionId"
          :disabled="true"
        >
          <template #item="{ element }">
            <v-expansion-panel-text class="my-question" @click="selectQuestion(element)">
              {{ element.question }}
            </v-expansion-panel-text>
          </template>
        </draggable>
      </v-expansion-panel>
    </v-expansion-panels>
  </div>
</template>

<style scoped>
.question-title {
  background-color: #7d797f;
  color: white;
}
.my-question{
    cursor: pointer;
    font-size: 12px;
}
</style>
