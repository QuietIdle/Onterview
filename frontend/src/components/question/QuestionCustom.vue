<script setup>
import draggable from 'vuedraggable'
import { ref } from 'vue'

// store
import { storeToRefs } from 'pinia'
import { useQuestionStore } from '@/stores/question.js'
const questionStore = useQuestionStore()
const { myQuestionList } = storeToRefs(questionStore)

const log = function () {
  console.log('drag n drop my question list')
  console.log(myQuestionList.value)
}

const selectFolder = ref([])
const selectQuestion = ref([])

const checkFolderQuestion = function (folder) {
  console.log(folder)
}
</script>

<template>
  <div class="question-title pa-3">나의 면접 문항 목록</div>
  <div class="bg-white pa-3 d-flex">
    <div class="me-auto d-flex align-center">
      <v-btn class="mr-3" variant="elevated" color="purple-accent-4">전체 선택</v-btn>
      <div style="color: gray">질문 *개 선택됨</div>
    </div>
    <v-btn variant="elevated" color="red-accent-2">삭제</v-btn>
    <v-btn class="ml-3" variant="elevated" color="blue-accent-2">폴더 추가</v-btn>
  </div>
  <div style="max-height: 80%; overflow-y: auto">
    <v-expansion-panels
      variant="accordion"
      v-for="folder in myQuestionList"
      :key="folder.my_question_folder_id"
    >
      <v-expansion-panel>
        <v-expansion-panel-title>
          <v-row>
            <v-col cols="auto">
              <v-checkbox
                @click.stop
                @click="checkFolderQuestion(folder)"
                v-model="selectFolder"
                :value="folder.my_question_folder_id"
              ></v-checkbox>
            </v-col>
            <v-col class="d-flex align-center">
              {{ folder.folder }}
            </v-col>
          </v-row>
        </v-expansion-panel-title>

        <draggable :list="folder.question" group="question" @change="log" item-key="my_question_id">
          <template #item="{ element }">
            <v-expansion-panel-text>
              <v-row>
                <v-col cols="auto">
                  <v-checkbox v-model="selectQuestion" :value="element.my_question_id"></v-checkbox>
                </v-col>
                <v-col class="d-flex align-center">
                  {{ element.question }}
                </v-col>
              </v-row>
            </v-expansion-panel-text>
          </template>
        </draggable>
      </v-expansion-panel>
    </v-expansion-panels>
  </div>
</template>

<style>
.question-title {
  background-color: #4f2960;
  color: white;
}

.checkbox-22-messages {
  display: none !important;
}

.v-input__details {
  display: none !important;
}
</style>
