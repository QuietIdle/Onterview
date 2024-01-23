<script setup>
import { ref } from 'vue';
import { apiMethods } from "@/api/video.js";
import { useStorageStore } from '@/stores/storage.js';

const pinia = useStorageStore();
const selectedId = ref([]);
const videoDt = pinia.storageData.value;

const deleteVideo = async function () {
  try {
    const result = await apiMethods.deleteVideos({
      videos: selectedId.value
    });
    console.log(result.data);
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
    console.log(result.data);
  } catch (error) {
    console.log(error);
  }
}

const selectAll = function () {
  for (const item of pinia.storageData.value) {
    if (!selectedId.value.includes(item.videoId)) {
      selectedId.value.push(item.videoId)
    }
  }
}
</script>

<template>
    <div class="pa-10 d-flex justify-center bg-green w-screen">
    <div class="w-75 bg-white">
        <div class="tool-bar d-flex align-center">
        <v-btn variant="tonal" @click="selectAll">
          전체 선택
        </v-btn>
        <v-btn variant="tonal" @click="deleteVideo">
          삭제
        </v-btn>

        <v-btn class="ml-auto" variant="outlined" @click="pinia.switchDisplay">
          리스트 보기
        </v-btn>
      </div>

      <div class="pa-2">
        <v-container>
          <v-row>
            <v-col 
            v-for="(dt, n) in videoDt"
            :key="n"
            cols="auto"
            >
            <v-card height="150" width="200" class="d-flex flex-column align-center justify-center">
              <div class="card">
                <v-img class="img-container" :src="dt.thumbnailUrl.saveFilename" width="180" height="120">
                  <div class="number">{{ n+1 }}</div>
                  <v-icon 
                  v-show="!dt.bookmark" 
                  class="icon"
                  color="purple" 
                  size="32" 
                  icon="mdi-bookmark-outline"
                  @click="markVideo(dt.videoId, dt.bookmark), dt.bookmark=!dt.bookmark"
                  >
                  </v-icon>
                  <v-icon 
                  v-show="dt.bookmark" 
                  class="icon"
                  color="purple" 
                  size="32" 
                  icon="mdi-bookmark-check"
                  @click="markVideo(dt.videoId, dt.bookmark), dt.bookmark=!dt.bookmark"
                  >
                  </v-icon>
                  <v-checkbox
                  class="check-box"
                  v-model="selectedId"
                  :value="dt.videoId"
                  ></v-checkbox>
                </v-img>
                <div>{{ dt.title }}</div>
              </div>
            </v-card>
            </v-col>
          </v-row>
        </v-container>
      </div>
    </div>
  </div>
</template>

<style scoped>
.tool-bar>*{
  margin: 8px;
}
.number{
  position: absolute;
}
.icon{
  position: absolute;
  right: 0;
}
.check-box{
  position: absolute;
  bottom: -20%;
  right: 0;
}
</style>
