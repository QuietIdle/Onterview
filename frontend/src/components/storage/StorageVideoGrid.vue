<script setup>
import { ref, onMounted } from 'vue'
import { apiMethods } from '@/api/video'
import { useStorageStore } from '@/stores/storage'

onMounted(() => {
  storageStore.requestUserVideoAll()
})

const storageStore = useStorageStore()
const selectedId = ref([])
const isSelectedAll = ref(false)

const deleteVideo = async function () {
  try {
    const result = await apiMethods.deleteVideos({
      videos: selectedId.value
    })
    console.log(result.data)
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

      <div class="pa-2">
        <v-container>
          <v-row>
            <v-col
              v-for="(dt, n) in storageStore.storageData"
              :key="n"
              cols="auto"
            >
              <v-card height="200" width="200">
                <div class="image-container w-100 h-100">
                  <div class="number">{{ n + 1 }}</div>
                  <v-icon
                    v-show="!dt.bookmark"
                    class="icon"
                    color="purple"
                    size="32"
                    icon="mdi-bookmark-outline"
                    @click="
                      markVideo(dt.videoId, dt.bookmark),
                        (dt.bookmark = !dt.bookmark)
                    "
                  >
                  </v-icon>
                  <v-icon
                    v-show="dt.bookmark"
                    class="icon"
                    color="purple"
                    size="32"
                    icon="mdi-bookmark-check"
                    @click="
                      markVideo(dt.videoId, dt.bookmark),
                        (dt.bookmark = !dt.bookmark)
                    "
                  >
                  </v-icon>

                  <v-card
                    class="image-card"
                    :image="dt.thumbnailUrl.saveFilename"
                    width="180"
                    height="135"
                    @click="
                      storageStore.goStorageVideoPlay(), selectVideo(dt.videoId)
                    "
                  >
                  </v-card>
                  <v-checkbox
                    class="check-box"
                    v-model="selectedId"
                    :value="dt.videoId"
                  ></v-checkbox>
                  <div class="text-container">
                    {{ dt.title }}
                  </div>
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
</style>
