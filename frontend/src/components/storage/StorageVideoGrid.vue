<script setup>
import { ref, onMounted } from 'vue'
import { apiMethods } from '@/api/video'
import { useStorageStore } from '@/stores/storage'

const storageStore = useStorageStore()
const selectedId = ref([])
const isSelectedAll = ref(false)

const deleteVideo = async function () {
  try {
    const result = await apiMethods.deleteVideos({
      videos: selectedId.value
    })
    console.log(result.data)

    storageStore.requestUserVideoAll('self')
  } catch (error) {
    console.log(error)
  }
}

const markVideo = async function (id, bool) {
  try {
    const req_body = {
      bookmark: !bool
    }
    const result = await apiMethods.patchVideo(id, req_body)
    console.log(result.data)
  } catch (error) {
    console.log(error)
  }
}

const selectAll = function () {
  if (isSelectedAll.value == true) {
    selectedId.value = []
    isSelectedAll.value = false
  } else {
    for (const item of storageStore.storageData) {
      if (!selectedId.value.includes(item.videoId)) {
        selectedId.value.push(item.videoId)
        isSelectedAll.value = true
      }
    }
  }
}

const selectVideo = async function (v_id) {
  try {
    const res = await apiMethods.getVideo(v_id)
    storageStore.videoData = res.data
  } catch (error) {
    console.log(error)
  }
}
</script>

<template>
  <div class="pa-10 d-flex justify-center w-screen h-screen">
    <div class="w-75 bg-white overflow-auto">
      <div class="tool-bar d-flex align-center">
        <v-btn variant="tonal" @click="selectAll"> 전체 선택 </v-btn>
        <v-btn variant="tonal" @click="deleteVideo"> 삭제 </v-btn>

        <v-btn
          class="ml-auto"
          variant="outlined"
          @click="storageStore.goStorageVideoList()"
        >
          리스트 보기
        </v-btn>
      </div>

      <div class="px-3 py-2 d-flex flex-wrap">
        <div
          cols="auto"
          v-for="video in storageStore.storageData"
          :key="video.videoId"
        >
          <v-card class="mx-3 my-2" width="230" @click="selectVideo(video)">
            <v-img
              class="thumbnail"
              :src="`${video.thumbnailUrl.saveFilename}`"
              height="150px"
              cover
              @click.stop="storageStore.goStorageVideoPlay(video.videoId)"
            >
              <v-icon
                v-show="!video.bookmark"
                class="icon"
                color="purple"
                size="32"
                icon="mdi-bookmark-outline"
                @click.stop="
                  markVideo(video.videoId, video.bookmark),
                    (video.bookmark = !video.bookmark)
                "
              >
              </v-icon>
              <v-icon
                v-show="video.bookmark"
                class="icon"
                color="purple"
                size="32"
                icon="mdi-bookmark-check"
                @click.stop="
                  markVideo(video.videoId, video.bookmark),
                    (video.bookmark = !video.bookmark)
                "
              >
              </v-icon>
            </v-img>

            <div class="pa-1" style="height: 60px">
              <input
                type="checkbox"
                :name="video.videoId"
                v-model="selectedId"
                :value="video.videoId"
                class="mx-1 pt-3"
              />
              <label :for="video.videoId" style="font-size: 0.8em">{{
                video.title
              }}</label>
            </div>
            <v-tooltip activator="parent" location="top" open-delay="1000">{{
              video.title
            }}</v-tooltip>
          </v-card>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.tool-bar > * {
  margin: 8px;
}
.image-container {
  position: relative;
}
.number {
  position: absolute;
  top: 0%;
  left: 0%;
}
.icon {
  position: absolute;
  top: 0%;
  right: 0%;
}
.image-card {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.check-box {
  position: absolute;
  bottom: -16%;
  left: 0%;
}
.text-container {
  position: absolute;
  bottom: -3%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.thumbnail {
  padding-left: 85%;
}
</style>
