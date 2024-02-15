import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { defineStore } from 'pinia'
import { apiMethods } from '@/api/video'

export const useStorageStore = defineStore('storage', () => {
  const storageData = ref([]);
  const videoData = ref({});

  const requestUserVideoAll = async function (roomType) {
    try {
      const result = await apiMethods.getUserVideoAll(roomType)
      storageData.value = result.data
      console.log('request user video all', roomType, storageData.value)
    } catch (error) {
      console.log(error)
    }
  }

  const requestInterviewList = async function (roomType) {
    try {
      const result = await apiMethods.getInterviewList(roomType)
      storageData.value = result.data
      console.log(roomType, result.data)
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
  const goStorageVideoPlayInterview = function (roomType, interviewRoomId) {
    router.push({ name: 'video-play-interview', params: {roomType:roomType, interviewRoomId: interviewRoomId} })
  }
  const goStorageVideoListInterview = function (roomType) {
    router.push({ name: 'video-list-interview', params: {roomType:roomType} })
  }

  return {
    storageData,
    videoData,
    requestUserVideoAll,
    goStorageVideoList,
    goStorageVideoGrid,
    goStorageVideoPlay,
    goStorageVideoPlayInterview,
    goStorageVideoListInterview,
    requestInterviewList,
  }
})