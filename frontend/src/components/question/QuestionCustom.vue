<script setup>
// lib
import draggable from 'vuedraggable'
import { onMounted } from 'vue'

// api
import {
  patchUpdateMyQuestionFolder,
  patchUpdateMyQuestion,
  postCreateMyQuestion,
  deleteDeleteMyQuestion
} from '@/api/question'

// assets
import QuestionModalCreate from '@/components/question/modal/QuestionModalCreate.vue'
import QuestionModalDelete from '@/components/question/modal/QuestionModalDelete.vue'
import editImage from '@/assets/question/editImage.png'

// store
import { storeToRefs } from 'pinia'
import { useQuestionStore } from '@/stores/question.js'
const questionStore = useQuestionStore()
const { myQuestionList } = storeToRefs(questionStore)

onMounted(() => {
  questionStore.requestMyQuestionList()
})

const log = async function (event, folder) {
  if (event.added) {
    try {
      const payload = {
        myQuestionFolderId: folder.myQuestionFolderId,
        commonQuestionId: event.added.element.commonQuestionId,
        question: event.added.element.question
          ? event.added.element.question
          : event.added.element.commonQuestion
      }

      const response = await postCreateMyQuestion(payload)
      console.log('response create my question', response)
    } catch (error) {
      console.log('error create my question', error)
    }
  }
  if (event.removed) {
    try {
      const response = await deleteDeleteMyQuestion(event.removed.element.myQuestionId)
      console.log('reponse delete my question', response)
    } catch (error) {
      console.log('error delete my question', error)
    }
  }

  questionStore.requestMyQuestionList()
}

const enableEditingMyQuestion = function (element) {
  element.isEditing = true
  element.editableQuestion = element.question
}

const requestUpdateMyQuestion = async function (element) {
  element.isEditing = false
  // element.question = element.editableQuestion

  try {
    const payload = { question: element.editableQuestion }
    const response = await patchUpdateMyQuestion(element.myQuestionId, payload)
    console.log('response upadte my question', response)

    questionStore.requestMyQuestionList()
  } catch (error) {
    console.log('error update my question', error)
  }
}

const enableEditingMyQuestionFolder = function (folder) {
  folder.isEditing = true
  folder.editableQuestion = folder.myQuestionFolder
}

const requestUpdateMyQuestionFolder = async function (folder) {
  folder.isEditing = false
  // element.question = element.editableQuestion

  try {
    const payload = { myQuestionFolder: folder.editableQuestion }
    const response = await patchUpdateMyQuestionFolder(folder.myQuestionFolderId, payload)
    console.log('response upadte my question folder', response)

    questionStore.requestMyQuestionList()
  } catch (error) {
    console.log('error update my question folder', error)
  }
}
</script>

<template>
  <div class="question-title pa-3">나의 면접 문항 목록</div>
  <div class="bg-white pa-3 d-flex">
    <div class="me-auto d-flex align-center"></div>
    <QuestionModalCreate content="폴더" />
  </div>
  <div style="max-height: 80%; overflow-y: auto">
    <v-expansion-panels variant="accordion" multiple>
      <v-expansion-panel v-for="folder in myQuestionList" :key="folder.myQuestionFolderId">
        <v-hover v-slot="{ isHovering, props }">
          <v-expansion-panel-title v-bind="props">
            <v-col cols="auto">
              <v-img
                v-if="isHovering"
                width="20"
                :src="editImage"
                @click.stop="enableEditingMyQuestionFolder(folder)"
              ></v-img>
              <v-img width="20"></v-img>
            </v-col>
            <v-col class="d-flex align-center">
              <template v-if="folder.isEditing">
                <v-text-field
                  v-model="folder.editableQuestion"
                  @blur="requestUpdateMyQuestionFolder(folder)"
                  @keyup.enter="requestUpdateMyQuestionFolder(folder)"
                ></v-text-field>
              </template>
              <template v-else>
                {{ folder.myQuestionFolder }}
              </template>
            </v-col>
            <v-col cols="auto" class="d-flex align-center">
              <QuestionModalCreate
                content="질문"
                :my-question-folder-id="folder.myQuestionFolderId"
              />
              <QuestionModalDelete
                content="폴더"
                :my-question-folder-id="folder.myQuestionFolderId"
                :my-question-folder="folder.myQuestionFolder"
              />
            </v-col>
          </v-expansion-panel-title>

          <draggable
            :list="folder.myQuestionList"
            group="question"
            @change="(event) => log(event, folder)"
            item-key="myQuestionId"
          >
            <template #item="{ element }">
              <v-expansion-panel-text>
                <v-row class="d-flex">
                  <v-col cols="auto"></v-col>
                  <v-col class="d-flex align-center" @dblclick="enableEditingMyQuestion(element)">
                    <template v-if="element.isEditing">
                      <v-text-field
                        v-model="element.editableQuestion"
                        @keyup.enter="requestUpdateMyQuestion(element)"
                        @blur="requestUpdateMyQuestion(element)"
                      ></v-text-field>
                    </template>
                    <template v-else>
                      {{ element.question }}
                    </template>
                  </v-col>
                  <v-col cols="auto">
                    <QuestionModalDelete content="파일" :my-question="element" />
                  </v-col>
                </v-row>
              </v-expansion-panel-text>
            </template>
          </draggable>
        </v-hover>
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

.v-expansion-panel-title {
  padding: 0 !important;
  padding-left: 10px !important;
  padding-right: 10px !important;
}
</style>
