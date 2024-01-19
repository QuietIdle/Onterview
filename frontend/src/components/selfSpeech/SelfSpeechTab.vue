<script setup>
import { ref } from 'vue';
import { useSelfSpeechStore } from '@/stores/selfSpeech.js';

const pinia = useSelfSpeechStore();
const model = ref(null); // 썸네일

const test = ref("A");

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

const switchTab = function(page) {
  pinia.listIdx = page;
}

const aaa = function(temp) {
  pinia.display = false;
  if (!temp) {
    test.value = "B";
  } 
  else {
    test.value = "A";
  }
}
</script>

<template>
    <div class="d-flex align-center justify-center w-100 h-25">
      <v-card class="text-center" width="120" max-height="120" variant="text">
        <template v-for="item in items" :key="item.id">
          <v-list-item @click="switchTab(item.id)" v-if="item.id==3 && pinia.display" disabled>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
          <v-list-item @click="switchTab(item.id)" v-else>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
        </template>
      </v-card>

      <div class="content-container w-100 h-100 bg-yellow pa-5">
        <div v-if="pinia.listIdx==1">
          나의 답변 페이지
        </div>
        <div v-else-if="pinia.listIdx==2">
          <v-sheet class="mx-auto" elevation="8" min-width="200" max-width="800">
            <v-slide-group v-model="model" show-arrows center-active>
              <v-slide-group-item v-for="n in 15" :key="n" v-slot="{ isSelected, toggle }">
                <v-card :color="isSelected ? 'primary' : 'grey-lighten-1'" class="ma-2" height="100" width="150" @click="toggle(), aaa(isSelected)">
                  <div class="d-flex fill-height align-center justify-center">
                    {{ n }}-{{ test }}
                    <v-scale-transition>
                      <v-icon v-if="isSelected" color="white" size="48" icon="mdi-close-circle-outline"></v-icon>
                    </v-scale-transition>
                  </div>
                </v-card>
              </v-slide-group-item>
            </v-slide-group>
          </v-sheet>
        </div>
        <div v-else-if="pinia.listIdx==3">
          자가 진단진단진단
        </div>
      </div>
    </div>
</template>

<style scoped>

</style>