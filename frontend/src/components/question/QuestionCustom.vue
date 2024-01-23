<script setup>
import draggable from 'vuedraggable'
import { ref, onMounted } from 'vue'

// store
import { storeToRefs } from 'pinia'
import { useQuestionStore } from '@/stores/question.js'
const questionStore = useQuestionStore()
const { myQuestionList } = storeToRefs(questionStore)

onMounted(() => {
  const questionStore = useQuestionStore()
  questionStore.requestMyQuestionList()
})

const log = function () {
  console.log('drag n drop my question list')
  console.log(myQuestionList.value)
}

const selectFolder = ref([])
const selectQuestion = ref([])

const checkFolder = function (folder) {
  console.log(folder)
}

const checkAll = function () {}

const enableEditing = function (element) {
  element.isEditing = true
  element.editableQuestion = element.question
}

const disableEditing = function (element) {
  element.isEditing = false
  element.question = element.editableQuestion
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
    <v-expansion-panels variant="accordion" multiple>
      <v-expansion-panel v-for="folder in myQuestionList" :key="folder.myQuestionFolderId">
        <v-expansion-panel-title>
          <v-row>
            <v-col cols="auto">
              <v-checkbox
                @click.stop="checkFolder(folder)"
                v-model="selectFolder"
                :value="folder.myQuestionFolderId"
              ></v-checkbox>
            </v-col>
            <v-col class="d-flex align-center">
              {{ folder.myQuestionFolder }}
            </v-col>
          </v-row>
        </v-expansion-panel-title>

        <draggable
          :list="folder.myQuestionList"
          group="question"
          @change="log"
          item-key="myQuestionId"
        >
          <template #item="{ element }">
            <v-expansion-panel-text>
              <v-row>
                <v-col cols="auto"></v-col>
                <v-col cols="auto">
                  <v-checkbox v-model="selectQuestion" :value="element.myQuestionId"></v-checkbox>
                </v-col>
                <v-col class="d-flex align-center" @dblclick="enableEditing(element)">
                  <template v-if="element.isEditing">
                    <v-text-field
                      v-model="element.editableQuestion"
                      @blur="disableEditing(element)"
                      @keyup.enter="disableEditing(element)"
                    ></v-text-field>
                  </template>
                  <template v-else>
                    {{ element.question }}
                  </template>
                </v-col>
              </v-row>
            </v-expansion-panel-text>
          </template>
        </draggable>
      </v-expansion-panel>
      <v-expansion-panel>
        <v-expansion-panel-title>폴더 추가</v-expansion-panel-title>
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
