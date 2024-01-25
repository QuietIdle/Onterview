<script setup>
import draggable from 'vuedraggable'
import { ref, onMounted } from 'vue'

import createButton from '@/assets/question/createButton.svg'
import deleteButton from '@/assets/question/deleteButton.svg'
import folderImage from '@/assets/question/folderImage.svg'

import QuestionModalCreate from '@/components/question/QuestionModalCreate.vue'

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

const panel = ref([])
</script>

<template>
  <div class="question-title pa-3">나의 면접 문항 목록</div>
  <div class="bg-white pa-3 d-flex">
    <div class="me-auto d-flex align-center"></div>
    <QuestionModalCreate />
  </div>
  <div style="max-height: 80%; overflow-y: auto">
    <v-expansion-panels variant="accordion" multiple>
      <v-expansion-panel v-for="folder in myQuestionList" :key="folder.myQuestionFolderId">
        <v-expansion-panel-title>
          <v-col cols="auto">
            <v-img width="20" :src="folderImage"></v-img>
          </v-col>
          <v-col>
            {{ folder.myQuestionFolder }}
          </v-col>
          <v-col cols="auto" class="d-flex align-center">
            <v-img @click.stop width="30" :src="createButton"></v-img>
            <v-img @click.stop width="30" :src="deleteButton"></v-img>
          </v-col>
        </v-expansion-panel-title>

        <draggable
          :list="folder.myQuestionList"
          group="question"
          @change="log"
          item-key="myQuestionId"
        >
          <template #item="{ element }">
            <v-expansion-panel-text>
              <v-row class="d-flex">
                <v-col cols="auto"></v-col>
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
                <v-col cols="auto">
                  <v-img width="30" :src="deleteButton"></v-img>
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

.v-expansion-panel-title__icon {
  /* display: none !important; */
}

.v-expansion-panel-title {
  padding: 0 !important;
  padding-left: 10px !important;
  padding-right: 10px !important;
}
</style>
