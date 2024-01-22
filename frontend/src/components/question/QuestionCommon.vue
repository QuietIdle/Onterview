<script setup>
import draggable from 'vuedraggable'

// store
import { storeToRefs } from 'pinia'
import { useQuestionStore } from '@/stores/question.js'
const questionStore = useQuestionStore()
const { commonQuestionList } = storeToRefs(questionStore)

const cloneList = function (data) {
  const questionObject = {
    common_question_id: data.common_question_id,
    my_question_id: data.common_question_id + 1,
    question: data.question
  }
  return questionObject
}
</script>

<template>
  <div class="question-title pa-3">빈출 면접 문항 목록</div>
  <div style="max-height: 80%; overflow-y: auto">
    <v-expansion-panels
      variant="accordion"
      v-for="folder in commonQuestionList"
      :key="folder.common_question_folder_id"
    >
      <v-expansion-panel :title="folder.folder" :value="folder.common_question_folder_id">
        <draggable
          :list="folder.question"
          :group="{ name: 'question', pull: 'clone', put: false }"
          :clone="cloneList"
          item-key="common_question_id"
        >
          <template #item="{ element }">
            <v-expansion-panel-text>{{ element.question }}</v-expansion-panel-text>
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
</style>
