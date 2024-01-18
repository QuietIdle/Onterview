<script setup>
import { ref } from 'vue'

const items = ref([
  {
    title: '나의 답변',
    value: 1
  },
  {
    title: '영상 보관함',
    value: 2
  },
  {
    title: '자가 진단',
    value: 3
  }
])
</script>

<template>
  <v-card class="mr-auto" max-width="300">
    <v-list :items="items" class="pa-0"></v-list>
  </v-card>
  <div class="content-container w-75 h-auto bg-yellow"></div>
</template>

<style scoped></style>
