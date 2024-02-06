<script setup>
import { ref } from 'vue'
import selectButton from '@/assets/community/selectButton.svg'

const videoList = ref([
  {
    videoId: 1,
    thumbnailUrl: {
      fileId: 2,
      originFilename: 'aaaa',
      saveFilename:
        'https://www.urbanbrush.net/web/wp-content/uploads/edd/2022/11/urbanbrush-20221108214712319041.jpg'
    },
    title: '제목입니다',
    question: 'testCommonQuestion'
  },
  {
    videoId: 2,
    thumbnailUrl: {
      fileId: 3,
      originFilename: 'bbb',
      saveFilename:
        'https://png.pngtree.com/thumb_back/fh260/background/20230609/pngtree-three-puppies-with-their-mouths-open-are-posing-for-a-photo-image_2902292.jpg'
    },
    title: '제목입니다',
    question: 'testCommonQuestion'
  },
  {
    videoId: 3,
    thumbnailUrl: {
      fileId: 2,
      originFilename: 'aaaa',
      saveFilename:
        'https://www.urbanbrush.net/web/wp-content/uploads/edd/2022/11/urbanbrush-20221108214712319041.jpg'
    },
    title: '제목입니다',
    question: 'testCommonQuestion'
  },
  {
    videoId: 4,
    thumbnailUrl: {
      fileId: 3,
      originFilename: 'bbb',
      saveFilename:
        'https://png.pngtree.com/thumb_back/fh260/background/20230609/pngtree-three-puppies-with-their-mouths-open-are-posing-for-a-photo-image_2902292.jpg'
    },
    title: '제목이 몹시 길다아아아아테스트데이터가 테스트',
    question: 'testCommonQuestion'
  },
  {
    videoId: 88,
    thumbnailUrl: {
      fileId: 2,
      originFilename: 'aaaa',
      saveFilename:
        'https://www.urbanbrush.net/web/wp-content/uploads/edd/2022/11/urbanbrush-20221108214712319041.jpg'
    },
    title: '제목이 몹시 길다아아아아테스트데이터가 테스트',
    question: 'testCommonQuestion'
  },
  {
    videoId: 89,
    thumbnailUrl: {
      fileId: 3,
      originFilename: 'bbb',
      saveFilename:
        'https://png.pngtree.com/thumb_back/fh260/background/20230609/pngtree-three-puppies-with-their-mouths-open-are-posing-for-a-photo-image_2902292.jpg'
    },
    title: '제목이 몹시 길다아아아아테스트데이터가 테스트',
    question: 'testCommonQuestion'
  },
  {
    videoId: 90,
    thumbnailUrl: {
      fileId: 2,
      originFilename: 'aaaa',
      saveFilename:
        'https://www.urbanbrush.net/web/wp-content/uploads/edd/2022/11/urbanbrush-20221108214712319041.jpg'
    },
    title: '제목이 몹시 길다아아아아테스트데이터가 테스트',
    question: 'testCommonQuestion'
  },
  {
    videoId: 91,
    thumbnailUrl: {
      fileId: 3,
      originFilename: 'bbb',
      saveFilename:
        'https://png.pngtree.com/thumb_back/fh260/background/20230609/pngtree-three-puppies-with-their-mouths-open-are-posing-for-a-photo-image_2902292.jpg'
    },
    title: '제목이 몹시 길다아아아아테스트데이터가 테스트',
    question: 'testCommonQuestion'
  },
  {
    videoId: 92,
    thumbnailUrl: {
      fileId: 2,
      originFilename: 'aaaa',
      saveFilename:
        'https://www.urbanbrush.net/web/wp-content/uploads/edd/2022/11/urbanbrush-20221108214712319041.jpg'
    },
    title: '제목이 몹시 길다아아아아테스트데이터가 테스트',
    question: 'testCommonQuestion'
  },
  {
    videoId: 93,
    thumbnailUrl: {
      fileId: 3,
      originFilename: 'bbb',
      saveFilename:
        'https://png.pngtree.com/thumb_back/fh260/background/20230609/pngtree-three-puppies-with-their-mouths-open-are-posing-for-a-photo-image_2902292.jpg'
    },
    title: '제목이 몹시 길다아아아아테스트데이터가 테스트',
    question: 'testCommonQuestion'
  },
  {
    videoId: 94,
    thumbnailUrl: {
      fileId: 2,
      originFilename: 'aaaa',
      saveFilename:
        'https://www.urbanbrush.net/web/wp-content/uploads/edd/2022/11/urbanbrush-20221108214712319041.jpg'
    },
    title: '제목이 몹시 길다아아아아테스트데이터가 테스트',
    question: 'testCommonQuestion'
  },
  {
    videoId: 95,
    thumbnailUrl: {
      fileId: 3,
      originFilename: 'bbb',
      saveFilename:
        'https://png.pngtree.com/thumb_back/fh260/background/20230609/pngtree-three-puppies-with-their-mouths-open-are-posing-for-a-photo-image_2902292.jpg'
    },
    title: '제목입니다',
    question: 'testCommonQuestion'
  },
  {
    videoId: 96,
    thumbnailUrl: {
      fileId: 2,
      originFilename: 'aaaa',
      saveFilename:
        'https://www.urbanbrush.net/web/wp-content/uploads/edd/2022/11/urbanbrush-20221108214712319041.jpg'
    },
    title: '제목입니다',
    question: 'testCommonQuestion'
  },
  {
    videoId: 97,
    thumbnailUrl: {
      fileId: 3,
      originFilename: 'bbb',
      saveFilename:
        'https://png.pngtree.com/thumb_back/fh260/background/20230609/pngtree-three-puppies-with-their-mouths-open-are-posing-for-a-photo-image_2902292.jpg'
    },
    title: '제목입니다',
    question: 'testCommonQuestion'
  }
])

const emit = defineEmits(['selectVideo'])
const selectVideoId = ref(null)

const selectVideo = function (video) {
  selectVideoId.value = video.videoId
  emit('selectVideo', video)
}
</script>

<template>
  <div class="layout px-3 py-2 d-flex flex-wrap">
    <div cols="auto" v-for="video in videoList" :key="video.videoId">
      <v-card class="mx-3 my-2" width="200" @click="selectVideo(video)">
        <v-img
          class="thumbnail"
          :src="video.thumbnailUrl.saveFilename"
          height="105px"
          cover
        >
          <v-img
            v-if="selectVideoId == video.videoId"
            :src="selectButton"
          ></v-img>
        </v-img>
        <v-card-subtitle class="pa-1">
          {{ video.title }}
        </v-card-subtitle>
        <v-tooltip activator="parent" location="top" open-delay="1000">{{
          video.title
        }}</v-tooltip>
      </v-card>
    </div>
  </div>
</template>

<style scoped>
.layout {
  background-color: #f0e8f6;
  height: 42vh;
  max-height: 100%;
  overflow-y: auto;
}

.thumbnail {
  padding-left: 85%;
}
</style>
