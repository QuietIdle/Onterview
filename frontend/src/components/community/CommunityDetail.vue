<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getPostDetail } from '@/api/community'

onMounted(() => {
  // postDetail.value = requestPostDetail()
})

const requestPostDetail = async function () {
  try {
    const route = useRoute()
    const articleId = ref(route.params.articleId)

    const response = await getPostDetail(articleId)
    console.log('response post detail', response)
  } catch (error) {
    alert('게시글을 조회하지 못했습니다.')
  }
}

const postDetail = ref({
  articleId: 5,
  writerNickname: '이경민',
  title: '성격의 장단점',
  likeCount: 3,
  writtenDate: '1999/4/19',

  content: '고민 내용',
  isWriter: true,
  isLike: true
})
</script>

<template>
  <div>{{ postDetail }}</div>
</template>

<style scoped></style>
