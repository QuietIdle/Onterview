<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import { apiMethods } from '@/api/video'
import { useStorageStore } from '@/stores/storage'

const storageStore = useStorageStore()

const selectedId = ref([])
const isSelectedAll = ref(false)
const route = useRoute()

onMounted(async () => {
  await storageStore.requestInterviewList(route.params.roomType)
})

watch(
  () => route.params.roomType,
  async (newValue, oldValue) => {
    await storageStore.requestInterviewList(newValue)
  }
)

const deleteInterview = async function () {
  try {
    const result = await apiMethods.deleteInterview({
      interviewRoomIdList: selectedId.value
    })
    console.log(result.data)
    selectedId.value = []
    await storageStore.requestInterviewList(route.params.roomType)
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
  if (selectedId.value.length === storageStore.storageData.content.length) {
    selectedId.value = []
  } else {
    for (const item of storageStore.storageData.content) {
      if (!selectedId.value.includes(item.interviewRoomId)) {
        selectedId.value.push(item.interviewRoomId)
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
  <!-- list (추후에 vuetify data tables 컴포넌트 변경?)-->
  <div class="pa-10 d-flex justify-center w-screen h-screen">
    <div class="w-75 bg-white overflow-auto">
      <div class="tool-bar d-flex align-center">
        <v-btn variant="tonal" @click="selectAll"> 전체 선택 </v-btn>
        <v-btn variant="tonal" @click="deleteInterview"> 삭제 </v-btn>
      </div>

      <div class="pa-2">
        <v-table fixed-header height="">
          <thead>
            <tr>
              <th class="text-left">선택</th>
              <th class="text-left">번호</th>
              <th class="text-left">유형</th>

              <th class="text-left">일시</th>
            </tr>
          </thead>
          <tbody
            v-for="(dt, n) in storageStore.storageData.content"
            :key="n"
            hover
          >
            <tr
              class="list-item"
              @click="
                storageStore.goStorageVideoPlayInterview(
                  route.params.roomType,
                  dt.interviewRoomId
                )
              "
            >
              <td>
                <v-checkbox
                  @click.stop
                  v-model="selectedId"
                  :value="dt.interviewRoomId"
                ></v-checkbox>
              </td>
              <td>{{ n + 1 }}</td>
              <td>
                {{ dt.questionType }}
              </td>

              <td>{{ dt.createAt }}</td>
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

.list-item:hover {
  background-color: rgb(242, 242, 242);
}

:deep(.v-input__details) {
  display: none;
}
</style>
