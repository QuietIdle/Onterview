<script setup>
import { ref } from 'vue';
import { apiMethods } from "@/api/video.js";
import { useSelfSpeechStore } from '@/stores/selfSpeech.js';
import identity from "bue/lib/utility/identity";

const pinia = useSelfSpeechStore();
const storageData = ref([]);
const selectedVideoId = ref([]);

const storageDisplay = async function() {
  try {
    const result = await apiMethods.getUserVideoAll();
    storageData.value = result.data;
  } catch (error) {
    console.log(error);
  }
}

const del = async function () {
  try {
    const result = await apiMethods.deleteVideos({videos:selectedVideoId.value});
    storageDisplay();
  } catch (error) {
    console.log(error);
  }
}

const markVideo = async function (id, bool) {
  try {
    const req_body = {
      bookmark: !bool
    }
    const result = await apiMethods.patchVideo(id, req_body)
    storageDisplay();
  } catch (error) {
    console.log(error);
  }
}

storageDisplay();
</script>

<template>
  <!-- list -->
  <div class="pa-10 d-flex justify-center bg-green w-screen">
    <div class="w-75 bg-white">
      <div class="tool-bar">
        <v-btn variant="tonal">
          전체 선택
        </v-btn>
        <v-btn variant="tonal" @click="del">
          삭제
        </v-btn>
      </div>

      <div class="pa-2">
        <v-table
          fixed-header
          height=""
        >
          <thead>
            <tr>
              <th class="text-left">
                선택
              </th>
              <th class="text-left">
                번호
              </th>
              <th class="text-left">
                질문
              </th>
              <th class="text-left">
                녹화 시간
              </th>
              <th class="text-left">
                영상 길이
              </th>
              <th class="text-left">
                북마크
              </th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="dt in storageData"
              :key="dt.videoId"
            >
              <td><v-checkbox
                v-model="selectedVideoId"
                :value="dt.videoId"
                ></v-checkbox></td>
              <td>{{ dt.videoId }}</td>
              <td>{{ dt.title }}</td>
              <td></td>
              <td></td>
              <td><v-icon 
                v-if="!dt.bookmark" 
                color="purple" 
                size="32" 
                icon="mdi-bookmark-outline"
                @click="markVideo(dt.videoId, dt.bookmark)"
                >
              </v-icon>
              <v-icon 
                v-else 
                color="purple" 
                size="32" 
                icon="mdi-bookmark-check"
                @click="markVideo(dt.videoId, dt.bookmark)"
                >
              </v-icon></td>
            </tr>
          </tbody>
        </v-table>
      </div>
    </div>
  </div>

  <div>
    
  </div>
</template>

<style scoped>
.tool-bar>*{
  margin: 8px;
}
</style>
