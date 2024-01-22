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
      { common_question_id: 0, my_question_id: 202, question: '내가 만든 질문' },
      { common_question_id: 600, my_question_id: 200, question: 'OOP 정의' },
      { common_question_id: 601, my_question_id: 201, question: '클래스와 객체의 차이점' },
      { common_question_id: 0, my_question_id: 202, question: '내가 만든 질문' },
      { common_question_id: 600, my_question_id: 200, question: 'OOP 정의' },
      { common_question_id: 601, my_question_id: 201, question: '클래스와 객체의 차이점' },
      { common_question_id: 0, my_question_id: 202, question: '내가 만든 질문' },
      { common_question_id: 600, my_question_id: 200, question: 'OOP 정의' },
      { common_question_id: 601, my_question_id: 201, question: '클래스와 객체의 차이점' },
      { common_question_id: 0, my_question_id: 202, question: '내가 만든 질문' },
      { common_question_id: 600, my_question_id: 200, question: 'OOP 정의' },
      { common_question_id: 601, my_question_id: 201, question: '클래스와 객체의 차이점' },
      { common_question_id: 0, my_question_id: 202, question: '내가 만든 질문' },
      { common_question_id: 600, my_question_id: 200, question: 'OOP 정의' },
      { common_question_id: 601, my_question_id: 201, question: '클래스와 객체의 차이점' },
      { common_question_id: 0, my_question_id: 202, question: '내가 만든 질문' },
      { common_question_id: 600, my_question_id: 200, question: 'OOP 정의' },
      { common_question_id: 601, my_question_id: 201, question: '클래스와 객체의 차이점' },
      { common_question_id: 0, my_question_id: 202, question: '내가 만든 질문' },
      { common_question_id: 600, my_question_id: 200, question: 'OOP 정의' },
      { common_question_id: 601, my_question_id: 201, question: '클래스와 객체의 차이점' },
      { common_question_id: 0, my_question_id: 202, question: '내가 만든 질문' },
      { common_question_id: 600, my_question_id: 200, question: 'OOP 정의' },
      { common_question_id: 601, my_question_id: 201, question: '클래스와 객체의 차이점' },
      { common_question_id: 0, my_question_id: 202, question: '내가 만든 질문' },
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
  <v-layout class="question-view d-flex row justify-center" style="height: 94vh">
    <v-col cols="2" class="common-question-list my-5 overflow-y-auto">
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
    </v-col>

    <v-col cols="8" class="my-question-list my-5">
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
