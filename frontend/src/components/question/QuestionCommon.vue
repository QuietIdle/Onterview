<script setup>
import draggable from 'vuedraggable'
import { onMounted } from 'vue'

// store
import { storeToRefs } from 'pinia'
import { useQuestionStore } from '@/stores/question.js'
const questionStore = useQuestionStore()
const { commonQuestionList } = storeToRefs(questionStore)

onMounted(() => {
  questionStore.requestCommonQuestionList()
})

const cloneList = function (data) {
  const questionObject = {
    commonQuestionId: data.commonQuestionId,
    commonQuestion: data.commonQuestion
  }
  return questionObject
}

const log = function () {
  // questionStore.requestCommonQuestionList()
}
</script>

<template>
  <div class="question-title pa-3">빈출 면접 문항 목록</div>
  <div style="max-height: 80%; overflow-y: auto">
    <v-expansion-panels
      variant="accordion"
      v-for="folder in commonQuestionList"
      :key="folder.commonQuestionFolderId"
    >
      <v-expansion-panel
        :title="folder.commonQuestionFolder"
        :value="folder.commonQuestionFolderId"
      >
        <draggable
          :list="folder.commonQuestionList"
          :group="{ name: 'question', pull: 'clone', put: false }"
          :clone="cloneList"
          item-key="common_question_id"
          @change="log"
        >
          <template #item="{ element }">
            <v-expansion-panel-text>{{ element.commonQuestion }}</v-expansion-panel-text>
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
