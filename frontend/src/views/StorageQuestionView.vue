<script setup>
import draggable from 'vuedraggable'

// store
import { storeToRefs } from 'pinia'
import { useQuestionStore } from '@/stores/question.js'
const questionStore = useQuestionStore()
const { commonQuestionList, myQuestionList } = storeToRefs(questionStore)

const log = () => {
  console.log('change')
}
</script>

<template>
  <v-layout class="question-view d-flex row justify-center" style="height: 94vh">
    <v-col cols="2" class="common-question-list my-5 overflow-y-auto">
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
              @change="log"
              item-key="common_question_id"
            >
              <template #item="{ element }">
                <v-expansion-panel-text>{{ element.question }}</v-expansion-panel-text>
              </template>
            </draggable>
          </v-expansion-panel>
        </v-expansion-panels>
      </div>
    </v-col>

    <v-col cols="8" class="my-question-list my-5">
      <div class="question-title pa-3">나의 면접 문항 목록</div>
      <div style="max-height: 80%; overflow-y: auto">
        <div class="bg-white pa-3 d-flex">
          <div class="me-auto d-flex align-center">
            <v-btn class="mr-3" variant="elevated" color="purple-accent-4">전체 선택</v-btn>
            <div style="color: gray">질문 *개 선택됨</div>
          </div>
          <v-btn variant="elevated" color="red-accent-2">삭제</v-btn>
          <v-btn class="ml-3" variant="elevated" color="blue-accent-2">폴더 추가</v-btn>
        </div>
        <v-expansion-panels
          variant="accordion"
          v-for="folder in myQuestionList"
          :key="folder.my_question_folder_id"
        >
          <v-expansion-panel :title="folder.folder" :value="folder.my_question_folder_id">
            <draggable
              :list="folder.question"
              group="question"
              @change="log"
              item-key="my_question_id"
            >
              <template #item="{ element }">
                <v-expansion-panel-text>{{ element.question }}</v-expansion-panel-text>
              </template>
            </draggable>
          </v-expansion-panel>
        </v-expansion-panels>
      </div>
    </v-col>
  </v-layout>
</template>

<style scoped>
.question-view {
  background-color: #efe6ef;
}

.common-question-list .my-question-list {
  background-color: white;
}
.question-title {
  background-color: #4f2960;
  color: white;
}
</style>
