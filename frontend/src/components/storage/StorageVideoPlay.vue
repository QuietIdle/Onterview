<script setup>
import { useStorageStore } from '@/stores/storage'
import videojs from 'video.js'
import { onUpdated, onBeforeUnmount, ref, onMounted } from 'vue'
import { fileServer } from '@/api/video'
import { apiMethods } from '@/api/video'

const storageStore = useStorageStore()

let player
const videoPlayer = ref(undefined)
const maxCounter = ref(20)

const rules = ref([
  (v) => {
    if (v === undefined) return true
    return (
      v.length <= maxCounter.value ||
      `${maxCounter.value}글자 이하로 작성해주세요`
    )
  }
])

const saveFeedback = async function () {
  try {
    const result = await apiMethods.patchVideo(storageStore.videoData.videoId, {
      feedback: storageStore.videoData.feedback
    })
    console.log(result.data)
  } catch (error) {
    console.log(error)
  }
}

// video
const isCompleted = ref(false)
const urlRef = ref(null)

const getAllChunks = async function (filename) {
  isCompleted.value = false

  const chunkSize = 1024 * 1024 // 1MB 단위로 청크를 받음
  let start = 0
  let end = chunkSize - 1

  let chunks = []

  while (!isCompleted.value) {
    try {
      const response = await fileServer.playVideo(
        filename,
        'test@test.com',
        start,
        end
      )

      console.log(response)

      if (response.status === 206) {
        console.log(`Received chunk ${start}-${end}`)
        chunks.push(response.data)
        start = end + 1
        end = start + chunkSize - 1
      } else if (response.status === 200) {
        console.log('Received the last chunk')
        chunks.push(response.data)
        isCompleted.value = true
        const blob = new Blob(chunks, { type: 'video/mp4' })
        const url = URL.createObjectURL(blob)

        urlRef.value = url
        // if (com === 0) renderVideo(url)
        // else reRenderVideo(url)
      }
    } catch (error) {
      console.error('영상 가져오기 실패', error)
      break
    }
  }
}

onMounted(async () => {
  try {
    await getAllChunks('59755822-3226-49f0-bac9-2ee7c44b5de2.mkv', 0)
    // await getAllChunks(postDetail.value.videoInfo.videoUrl.saveFilename, 0)
  } catch (error) {
    console.warn(error)
  }
})

onBeforeUnmount(() => {
  if (player) {
    player.dispose()
  }
})
</script>

<template>
  <div class="main-container d-flex justify-center">
    <div class="w-75">
      <div class="pa-2">
        <v-btn @click="storageStore.switchDisplay(1)">목록 보기</v-btn>
      </div>

      <div class="pa-2">
        <div class="empty-player-container d-flex justify-center align-center">
          <video
            ref="videoPlayer"
            class="video-js vjs-big-play-centered"
            id="my-video"
            data-setup='{"width": 640}'
            controls
            width="640"
            height="360"
          ></video>
        </div>
      </div>

      <v-container fluid>
        <v-textarea
          counter="20"
          :counter-max="maxCounter"
          label="자가진단"
          :rules="rules"
          v-model="storageStore.videoData.feedback"
          no-resize
          @blur="saveFeedback"
        >
          {{ storageStore.videoData.feedback }}
        </v-textarea>
      </v-container>
    </div>
  </div>
</template>

<style scoped>
.main-container {
  background-color: #efe6ef;
}
.empty-player-container {
  width: 100%;
  height: 360px;
  background-color: #f0f0f0;
  position: relative;
}
#my-video {
  width: 640px;
  height: 360px;
  background-color: #f0f0f0;
}
</style>
