<script setup>
import { ref } from 'vue';

const text = ref("");
const items = ref([
  {
    title: '나의 답변',
    value: 1,
  },
  {
    title: '영상 보관함',
    value: 2,
  },
  {
    title: '자가 진단',
    value: 3,
  },
])

function dd(a) {
  console.log(a)
}
</script>

<template>
    <div class="d-flex w-100 h-25">
      <v-card class="w-auto" min-width="120">
        <v-list :items="items" item-title="title" item-value="value" @click="dd(item-value);" class="pa-0"></v-list>
      </v-card>
      <div class="content-container w-100 h-auto bg-yellow">
        {{ text }}
      </div>
    </div>
</template>

<style scoped>

</style>