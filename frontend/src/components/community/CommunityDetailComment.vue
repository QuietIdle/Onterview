<script setup>
import { ref, computed } from 'vue'
import CommunityComment from '@/components/community/CommunityComment.vue'

const comments = ref({
  commentCount: 2,
  comments: [
    {
      parentComment: {
        parentCommentId: null,
        commentId: 1,
        writerNickname: '이경민',
        content:
          '제가 횡설수설 하지 않고 잘 말하고 있는지 궁금합니다. \n피드백 정말 환영합니다^^ \n03:38부터 보시면 됩니다!',
        writtenDate: '1999/04/19',
        isMyComment: true
      },
      childCommentList: [
        {
          parentCommentId: null,
          commentId: 1,
          writerNickname: '최경민',
          content:
            '제가 횡설수설 하지 않고 잘 말하고 있는지 궁금합니다. \n피드백 정말 환영합니다^^ \n03:38부터 보시면 됩니다!',
          writtenDate: '1999/04/19',
          isMyComment: true
        },
        {
          parentCommentId: null,
          commentId: 1,
          writerNickname: '아이경민',
          content: '댓글1',
          writtenDate: '1999/04/19',
          isMyComment: true
        },
        {
          parentCommentId: null,
          commentId: 1,
          writerNickname: '김경민',
          content: '댓글1',
          writtenDate: '1999/04/19',
          isMyComment: true
        }
      ]
    },
    {
      parentComment: {
        parentCommentId: null,
        commentId: 1,
        writerNickname: '이경민',
        content: '댓글2',
        writtenDate: '1999/04/19',
        isMyComment: true
      },
      childCommentList: [
        {
          parentCommentId: null,
          commentId: 1,
          writerNickname: '둘경민',
          content: '댓글1',
          writtenDate: '1999/04/19',
          isMyComment: true
        },
        {
          parentCommentId: null,
          commentId: 1,
          writerNickname: '셋경민',
          content: '댓글1',
          writtenDate: '1999/04/19',
          isMyComment: true
        },
        {
          parentCommentId: null,
          commentId: 1,
          writerNickname: '넷경민',
          content: '댓글1',
          writtenDate: '1999/04/19',
          isMyComment: true
        }
      ]
    },
    {
      parentComment: {
        parentCommentId: null,
        commentId: 1,
        writerNickname: '이경민',
        content: '댓글3',
        writtenDate: '1999/04/19',
        isMyComment: false
      },
      childCommentList: [
        {
          parentCommentId: null,
          commentId: 1,
          writerNickname: '이경민',
          content: '댓글1',
          writtenDate: '1999/04/19',
          isMyComment: true
        },
        {
          parentCommentId: null,
          commentId: 1,
          writerNickname: '이경민',
          content: '댓글1',
          writtenDate: '1999/04/19',
          isMyComment: true
        },
        {
          parentCommentId: null,
          commentId: 1,
          writerNickname: '이경민',
          content: '댓글1',
          writtenDate: '1999/04/19',
          isMyComment: true
        }
      ]
    }
  ]
})
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
