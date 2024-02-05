<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRoute } from 'vue-router'
import { getPostDetail, patchLikePost } from '@/api/community'
import CommunityModalUpdate from '@/components/community/modal/CommunityModalUpdate.vue'
import CommunityModalDelete from '@/components/community/modal/CommunityModalDelete.vue'

// assets
import tempThumbnail from '@/assets/main/introduceImage2.png'
import unlikeButton from '@/assets/community/unlikeButton.svg'
import likeButton from '@/assets/community/likeButton.svg'

onMounted(() => {
  requestPostDetail()
})

const route = useRoute()
const articleId = route.params.articleId

const requestPostDetail = async function () {
  try {
    const response = await getPostDetail(articleId)
    // console.log('response post detail', response)
    postDetail.value = response.data
  } catch (error) {
    alert('게시글을 조회하지 못했습니다.')
  }
}

const requestLikePost = async function () {
  if (postDetail.value.isMyArticle) {
    alert('내 게시글에는 좋아요를 할 수 없습니다.')
    return
  }

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
        <div class="video d-flex justify-center">
          <v-img
            max-width="800"
            gradient=""
            :src="tempThumbnail"
            cover
            aspect-ratio="16/9"
            class="grey lighten-2"
          ></v-img>
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
      <v-col cols="12" class="d-flex justify-end align-center mb-3">
        <div class="mr-2">추천 {{ postDetail.likeCount }}</div>
        <div>
          <v-img
            v-if="postDetail.isLiked"
            width="20"
            :src="likeButton"
            class="like-image"
            @click="requestLikePost()"
          ></v-img>
          <v-img
            v-else
            width="20"
            :src="unlikeButton"
            class="like-image"
            @click="requestLikePost()"
          ></v-img>
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

.like-image:hover {
  cursor: pointer;
}
</style>
