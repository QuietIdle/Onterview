<script setup>
import draggable from 'vuedraggable'
import { ref } from 'vue'

const commonQuestionList = ref([
  {
    common_question_folder_id: 5,
    folder: '인성 질문',
    question: [{ common_question_id: 500, question: '성격의 장단점' }]
  },
  {
    common_question_folder_id: 6,
    folder: '기술 질문',
    question: [
      { common_question_id: 600, question: 'OOP 정의' },
      { common_question_id: 601, question: '클래스와 객체의 차이점' }
    ]
  }
])

const myQuestionList = ref([
  {
    my_question_folder_id: 1,
    folder: '인성 질문',
    question: [{ common_question_id: 500, my_question_id: 100, question: '성격의 장단점' }]
  },
  {
    my_question_folder_id: 2,
    folder: '기술 질문',
    question: [
      { common_question_id: 600, my_question_id: 200, question: 'OOP 정의' },
      { common_question_id: 601, my_question_id: 201, question: '클래스와 객체의 차이점' },
      { common_question_id: 0, my_question_id: 202, question: '내가 만든 질문' }
    ]
  }
])

const log = () => {
  console.log('change')
}
</script>

<template>
  <div class="row">
    <div class="col-3">
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

    <div class="col-3">
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
  </div>
</template>

<style scoped></style>
