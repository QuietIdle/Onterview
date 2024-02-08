<script setup>
// lib
import { ref, onMounted, computed, onUpdated } from 'vue'
import { useRoute } from 'vue-router'

// api
import { getPostDetail, patchLikePost } from '@/api/community'
import { fileServer } from '@/api/video'

// assets
import unlikeButton from '@/assets/community/unlikeButton.svg'
import likeButton from '@/assets/community/likeButton.svg'

// component
import CommunityModalUpdate from '@/components/community/modal/CommunityModalUpdate.vue'
import CommunityModalDelete from '@/components/community/modal/CommunityModalDelete.vue'

onMounted(async () => {
  await requestPostDetail()

  try {
    // await getAllChunks('59755822-3226-49f0-bac9-2ee7c44b5de2.mkv', 0)
    await getAllChunks(postDetail.value.videoInfo.videoUrl.saveFilename, 0)
  } catch (error) {
    console.warn(error)
  }
})

const route = useRoute()
const articleId = route.params.articleId

const requestPostDetail = async function () {
  try {
    const response = await getPostDetail(articleId)
    postDetail.value = response.data
  } catch (error) {
    alert('게시글을 조회하지 못했습니다.')
  }
}

const requestLikePost = async function () {
  if (postDetail.value.isMyArticle) return

  try {
    await patchLikePost(articleId)
    requestPostDetail()
  } catch (error) {
    console.error('좋아요 업데이트 실패', error)
  }
}

const postDetail = ref({
  content: ''
})

const content = computed(() => {
  if (postDetail.value.content.includes('\n')) {
    return postDetail.value.content.replaceAll('\n', '<br />')
  } else {
    return postDetail.value.content
  }
})

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
</script>

<template>
  <v-container class="detail pt-16">
    <!-- 영상 제목 -->
    <v-row>
      <v-col cols="12" class="text-left">
        <h2 class="title">{{ postDetail.title }}</h2>
      </v-col>
    </v-row>
    <v-divider :thickness="2"></v-divider>

    <!-- 작성자 정보 -->
    <v-row class="px-3 pt-3">
      <v-col cols="6">
        <span class="writernickname">{{ postDetail.writerNickname }}</span>
      </v-col>
      <v-col cols="6" class="text-right">
        <span class="writtendate">{{ postDetail.writtenDate }}</span>
      </v-col>
    </v-row>

    <!-- 수정 삭제 버튼 -->
    <v-col v-if="postDetail.isMyArticle" cols="12" class="text-right">
      <CommunityModalUpdate
        :articleId="articleId"
        :content="postDetail.content"
        :writerNickname="postDetail.writerNickname"
        @request-post-detail="requestPostDetail()"
      />
      <CommunityModalDelete :articleId="articleId" :title="postDetail.title" />
    </v-col>

    <v-col v-else class="mb-10"> </v-col>

    <!-- 영상 컴포넌트 -->
    <v-row>
      <v-col cols="12">
        <div class="d-flex justify-center">
          <video
            id="my-video"
            :src="urlRef"
            controls="true"
            style="max-width: 80%; min-width: 100px"
          ></video>
        </div>
      </v-col>
    </v-row>

    <!-- 작성자 닉네임 및 내용 -->
    <v-row class="pt-8">
      <v-col cols="12">
        <div class="writer-and-content">
          <div class="mb-3">
            <span>{{ postDetail.writerNickname }}</span>
            <span class="text-grey">님의 고민</span>
          </div>
          <div v-html="content" style="font-family: Pretendard-Regular"></div>
        </div>
      </v-col>
    </v-row>

    <!-- 추천 -->
    <v-row>
      <v-col cols="12" class="like=image d-flex justify-end align-center mb-3">
        <div class="like mr-2" @click="requestLikePost()">
          추천 {{ postDetail.likeCount }}
        </div>
        <div class="like" @click="requestLikePost()">
          <v-img v-if="postDetail.isLiked" width="20" :src="likeButton">
          </v-img>
          <v-img v-else width="20" :src="unlikeButton"
            ><v-tooltip
              v-if="postDetail.isMyArticle"
              activator="parent"
              location="top"
              >내 게시글에는 좋아요를 할 수 없습니다.</v-tooltip
            ></v-img
          >
        </div>
      </v-col>
    </v-row>

    <v-divider :thickness="2"></v-divider>
  </v-container>
</template>

<style scoped>
.title {
  font-size: 24px; /* 제목 크기 */
  font-weight: bold;
  margin-bottom: 8px;
}

.like:hover {
  cursor: pointer;
}
</style>