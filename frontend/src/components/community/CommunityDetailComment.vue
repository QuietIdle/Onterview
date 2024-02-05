<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getCommentDetail } from '@/api/community'
import CommunityComment from '@/components/community/CommunityComment.vue'

onMounted(() => {
  requestCommentDetail()
})

const requestCommentDetail = async function () {
  try {
    const route = useRoute()
    const articleId = route.params.articleId

    const response = await getCommentDetail(articleId)
    comments.value = response.data
    console.log('request comment detail', response)
  } catch (error) {
    alert('댓글 목록을 불러오지 못했습니다. ')
  }
}

const comments = ref({})
</script>

<template>
  <v-container>
    <div>
      <div class="mb-3">댓글 쓰기</div>

      <v-row>
        <v-col cols="11">
          <v-textarea
            placeholder="피드백 내용을 작성해주세요"
            :no-resize="true"
            variant="outlined"
          >
          </v-textarea>
        </v-col>
        <v-col cols="1">
          <v-btn height="150">작성</v-btn>
        </v-col>
      </v-row>
    </div>

    <div>
      <div class="mb-6">댓글 {{ comments.commentCount }}</div>
      <div v-for="(comment, idx) in comments.comments" :key="idx">
        <CommunityComment :is-parent="true" :comment="comment.parentComment" />
        <div
          v-for="(child, idxChild) in comment.childCommentList"
          :key="idxChild"
        >
          <CommunityComment :is-parent="false" :comment="child" />
        </div>
        <v-row class="pt-3">
          <v-col cols="1" style="font-size: 0.9rem">대댓글 쓰기</v-col>
          <v-col cols="10">
            <v-textarea rows="2" :no-resize="true" variant="outlined">
            </v-textarea>
          </v-col>
          <v-col cols="1">
            <v-btn>작성</v-btn>
          </v-col>
        </v-row>
      </div>
    </div>
  </v-container>
</template>

<style scoped>
.parent-comment {
  background-color: #faf3ff;
}

.child-comment {
  background-color: #f0f0f0;
}
</style>
