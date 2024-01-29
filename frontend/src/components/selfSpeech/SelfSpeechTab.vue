<script setup>
import { ref } from 'vue';
import { useSelfSpeechStore } from '@/stores/selfSpeech';
import VideoThumbnail from "@/components/video/VideoThumbnail.vue";
import { apiMethods } from "@/api/video";

const selfSpeechStore = useSelfSpeechStore();
const maxCounter = ref(20);

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
  selfSpeechStore.listIdx = page;
}
const rules = ref([
  v => {
    if (v === undefined) return true;
    return v.length <= maxCounter.value || `${maxCounter.value}글자 이하로 작성해주세요`
  }
])

const saveFeedback = async function () {
  try {
    const result = await apiMethods.patchVideo(selfSpeechStore.videoData.videoId, {
      feedback: selfSpeechStore.videoData.feedback,
    })
    console.log(result.data)
  } catch (error) {
    console.log(error)
  }
}
</script>

<template>
    <div class="d-flex align-center justify-center w-100 h-25">
      <!-- 전환 탭 -->
      <v-card class="text-center" min-width="120" max-height="120" variant="text">
        <template v-for="item in items" :key="item.id">
          <v-list-item @click="switchTab(item.id)" v-if="item.id==3 && selfSpeechStore.display" disabled>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
          <v-list-item @click="switchTab(item.id)" v-else>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
        </template>
      </v-card>

      <!-- 답변 스크립트 -->
      <div class="content-container w-100 h-100">
        <div v-if="selfSpeechStore.listIdx===1">
          <v-container fluid>
            <v-textarea
              counter="20"
              :counter-max="maxCounter"
              label="답변"
              :rules="rules"
              v-model="selfSpeechStore.questionData.answer"
              no-resize
              @blur="console.log(selfSpeechStore.questionData.answer)"
            >
            {{ selfSpeechStore.questionData.answer }}
            </v-textarea>
          </v-container>
        </div>

        <!-- 썸네일 -->
        <div v-else-if="selfSpeechStore.listIdx===2" class="h-100">
          <VideoThumbnail />
        </div>

        <!-- 자가 진단 -->
        <div v-else-if="selfSpeechStore.listIdx===3">
          <v-container fluid>
            <v-textarea
              counter="20"
              :counter-max="maxCounter"
              label="자가진단"
              :rules="rules"
              v-model="selfSpeechStore.videoData.feedback"
              no-resize
              @blur="saveFeedback"
            >
            {{ selfSpeechStore.videoData.feedback }}
            </v-textarea>
          </v-container>
        </div>
      </div>
    </div>
</template>

<style scoped>

</style>