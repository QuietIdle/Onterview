import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { defineStore } from 'pinia'
import { apiMethods } from '@/api/video'

export const useStorageStore = defineStore('storage', () => {
  const storageData = ref([]);
      
  const videoData = ref({
    feedback: "자가진단진단",
    bookmark: false,
    videoUrl: {
      saveFilename: "",
    }
  });

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
  
  const goStorageVideoPlay = function () {
    router.push({name: 'video-play'})
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