<script setup>
import QuestionCommon from '@/components/question/QuestionCommon.vue'
import QuestionCustom from '@/components/question/QuestionCustom.vue'
</script>

<template>
  <v-layout class="question-view d-flex row justify-center" style="height: 94vh">
    <v-col cols="2" class="common-question-list my-5">
      <QuestionCommon />
    </v-col>
    <v-col cols="8" class="my-question-list my-5">
      <QuestionCustom />
    </v-col>
  </v-layout>
</template>

<style scoped>
.question-view {
  background-color: #efe6ef;
}
</style>
