<script setup>
import { ref, onMounted } from 'vue'
import { apiMethods } from '@/api/video'
import { useStorageStore } from '@/stores/storage'

onMounted(() => {
  storageStore.requestUserVideoAll()
})

const storageStore = useStorageStore()
const selectedId = ref([])

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
  for (const item of storageStore.storageData) {
    if (!selectedId.value.includes(item.videoId)) {
      selectedId.value.push(item.videoId)
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
  <!-- list (추후에 vuetify data tables 컴포넌트 변경?)-->
  <div class="pa-10 d-flex justify-center w-screen h-screen">
    <div class="w-75 bg-white overflow-auto">
      <div class="tool-bar d-flex align-center">
        <v-btn variant="tonal" @click="selectAll"> 전체 선택 </v-btn>
        <v-btn variant="tonal" @click="deleteVideo"> 삭제 </v-btn>

        <v-btn
          class="ml-auto"
          variant="outlined"
          @click="storageStore.goStorageVideoGrid()"
        >
          그리드 보기
        </v-btn>
      </div>

      <div class="pa-2">
        <v-table fixed-header height="">
          <thead>
            <tr>
              <th class="text-left">선택</th>
              <th class="text-left">번호</th>
              <th class="text-left">질문</th>
              <th class="text-left">녹화 시간</th>
              <th class="text-left">영상 길이</th>
              <th class="text-left">북마크</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(dt, n) in storageStore.storageData" :key="n">
              <td>
                <v-checkbox
                  v-model="selectedId"
                  :value="dt.videoId"
                ></v-checkbox>
              </td>
              <td>{{ n + 1 }}</td>
              <td
                @click="
                  storageStore.goStorageVideoPlay(), selectVideo(dt.videoId)
                "
                class="list-item"
              >
                {{ dt.title }}
              </td>
              <td></td>
              <td></td>
              <td>
                <v-icon
                  v-show="!dt.bookmark"
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
                  color="purple"
                  size="32"
                  icon="mdi-bookmark-check"
                  @click="
                    markVideo(dt.videoId, dt.bookmark),
                      (dt.bookmark = !dt.bookmark)
                  "
                >
                </v-icon>
              </td>
            </tr>
          </tbody>
        </v-table>
      </div>
    </div>
  </div>
</template>

<style scoped>
.tool-bar > * {
  margin: 8px;
}
.list-item {
  cursor: pointer;
}
</style>
