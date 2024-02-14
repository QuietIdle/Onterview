import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { defineStore } from 'pinia'
import { apiMethods } from '@/api/video'

export const useStorageStore = defineStore('storage', () => {
  const roomType = ref('self')

  const storageData = ref([]);
      
  const videoData = ref({});

  const requestUserVideoAll = async function () {
    router.push({ name: 'video-list' })

    try {
      const result = await apiMethods.getUserVideoAll(roomType.value)
      storageData.value = result.data
      console.log('request user video all', roomType.value, storageData.value)
    } catch (error) {
      console.log(error)
    }
  }

  const requestInterviewList = async function () {
    router.push({ name: 'video-list-interview' })

    try {
      const result = await apiMethods.getInterviewList(roomType.value)
      storageData.value = result.data
      console.log(roomType.value, result.data)
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
  const goStorageVideoPlayInterview = function (interviewRoomId) {
    router.push({ name: 'video-play-interview', params: {interviewRoomId: interviewRoomId} })
  }

  return {
    roomType,
    storageData,
    videoData,
    requestUserVideoAll,
    goStorageVideoList,
    goStorageVideoGrid,
    goStorageVideoPlay,
    goStorageVideoPlayInterview,
    requestInterviewList,
  }
})