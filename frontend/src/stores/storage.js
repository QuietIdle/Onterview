import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { defineStore } from 'pinia'
import { apiMethods } from '@/api/video'

export const useStorageStore = defineStore('storage', () => {
  const storageData = ref([
    {
      "videoId": 86,
      "thumbnailUrl": {
        "fileId": 4,
        "originFilename": "c",
        "saveFilename": "cc"
      },
      "title": "444",
      "question": "testQuestion"
    },
    {
      "videoId": 87,
      "thumbnailUrl": {
        "fileId": 6,
        "originFilename": "d",
        "saveFilename": "ddd"
      },
      "title": "555",
      "question": "testQuestion"
    },
    {
      "videoId": 88,
      "thumbnailUrl": {
        "fileId": 7,
        "originFilename": "e",
        "saveFilename": "ee"
      },
      "title": "66",
      "question": "testQuestion"
    },
    {
      "videoId": 89,
      "thumbnailUrl": {
        "fileId": 7,
        "originFilename": "e",
        "saveFilename": "ee"
      },
      "title": "66",
      "question": "testQuestion"
    },
    {
      "videoId": 90,
      "thumbnailUrl": {
        "fileId": 7,
        "originFilename": "e",
        "saveFilename": "ee"
      },
      "title": "66",
      "question": "testQuestion"
    },
    {
      "videoId": 91,
      "thumbnailUrl": {
        "fileId": 7,
        "originFilename": "e",
        "saveFilename": "ee"
      },
      "title": "66",
      "question": "testQuestion"
    },
    {
      "videoId": 92,
      "thumbnailUrl": {
        "fileId": 7,
        "originFilename": "e",
        "saveFilename": "ee"
      },
      "title": "66",
      "question": "testQuestion"
    },
    {
      "videoId": 93,
      "thumbnailUrl": {
        "fileId": 7,
        "originFilename": "e",
        "saveFilename": "ee"
      },
      "title": "66",
      "question": "testQuestion"
    },
    {
      "videoId": 94,
      "thumbnailUrl": {
        "fileId": 7,
        "originFilename": "e",
        "saveFilename": "ee"
      },
      "title": "66",
      "question": "testQuestion"
    },
    {
      "videoId": 95,
      "thumbnailUrl": {
        "fileId": 7,
        "originFilename": "e",
        "saveFilename": "ee"
      },
      "title": "66",
      "question": "testQuestion"
    },
    {
      "videoId": 96,
      "thumbnailUrl": {
        "fileId": 7,
        "originFilename": "e",
        "saveFilename": "ee"
      },
      "title": "66",
      "question": "testQuestion"
    },
    {
      "videoId": 97,
      "thumbnailUrl": {
        "fileId": 7,
        "originFilename": "e",
        "saveFilename": "ee"
      },
      "title": "66",
      "question": "testQuestion"
    },
    {
      "videoId": 98,
      "thumbnailUrl": {
        "fileId": 7,
        "originFilename": "e",
        "saveFilename": "ee"
      },
      "title": "66",
      "question": "testQuestion"
    },
  ]);
      
  const videoData = ref({
    feedback: "자가진단진단",
    bookmark: false,
    videoUrl: {
      saveFilename: "",
    }
  });

  const requestUserVideoAll = async function () {
    try {
      const result = await apiMethods.getUserVideoAll()
      storageData.value = result.data
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