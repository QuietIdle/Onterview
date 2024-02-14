import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { defineStore } from 'pinia'
import { apiMethods } from '@/api/video'

export const useStorageStore = defineStore('storage', () => {
  const storageData = ref([]);
      
  const videoData = ref({});

  const requestUserVideoAll = async function (speechType) {
    try {
      const result = await apiMethods.getUserVideoAll(speechType)
      storageData.value = result.data
      console.log(speechType, storageData.value)
    } catch (error) {
      console.log(error)
    }
  }

  const router = useRouter()

  const goStorageVideoList = function () {
    router.push({name: 'video-list'})
  }
  
  const goStorageVideoGrid = function () {
    router.push({name: 'video-grid'})
  }
  
  const goStorageVideoPlay = function (videoId) {
    router.push({ name: 'video-play', params: {videoId: videoId} })
  }

  return {
    storageData,
    videoData,
    requestUserVideoAll,
    goStorageVideoList,
    goStorageVideoGrid,
    goStorageVideoPlay,
  }
})