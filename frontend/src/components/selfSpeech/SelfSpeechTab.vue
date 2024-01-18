<script setup>
import { ref } from 'vue';

const text = ref("");
const listIdx = ref(1);
const items = ref([
  {
    title: '나의 답변',
    id: 1,
  },
  {
    title: '영상 보관함',
    id: 2,
  },
  {
    title: '자가 진단',
    id: 3,
  },
])

function switchDisplay(page) {
  listIdx.value = page;
}
</script>

<template>
    <div class="d-flex align-center justify-center w-100 h-25">
      <v-card class="text-center" min-width="120" max-height="120" variant="text">
        <template v-for="item in items" :key="item.id">
          <v-list-item @click="switchDisplay(item.id)">
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
          <!-- <v-list :items="items" item-title="title" item-value="id"  class="pa-0"></v-list> -->
        </template>

        <!-- <button><v-list v-for="item in items" :items="items" item-title="title" item-value="id" @click="dd(item)"></v-list></button> -->
        
        <!-- <v-list :items="items" item-title="title" item-value="id" @click="dd" class="pa-0">
          
        </v-list> -->
      </v-card>
      <div class="content-container w-100 h-100 bg-yellow pa-5">
        <div v-if="listIdx==1">
          나의 답변 페이지
        </div>
        <div v-else-if="listIdx==2">
          영상 리스트~~~
        </div>
        <div v-else-if="listIdx==3">
          자가 진단진단진단
        </div>
      </div>
    </div>
</template>

<style scoped>

</style>