<script setup>
import { ref } from 'vue'
import CommunityStorageVideo from '@/components/community/CommunityStoageVideo.vue'

const tab = ref(1)
const title = ref(null)
const content = ref('')
</script>

<template>
  <v-container>
    <div class="text-grey">제목</div>
    <div class="title mb-3" v-if="title === null">영상을 선택해주세요</div>
    <div class="title mb-3" v-else>{{ title }}</div>

    <!-- Tab -->
    <v-tabs v-model="tab" color="deep-purple-accent-4">
      <v-tab :value="1">셀프 스피치</v-tab>
      <v-tab :value="2">1인 모의면접</v-tab>
      <v-tab :value="3">다인 모의면접</v-tab>
    </v-tabs>

    <v-window v-model="tab">
      <v-window-item :value="1">
        <CommunityStorageVideo @select-video-title="(data) => (title = data)" />
      </v-window-item>
      <v-window-item :value="2">
        <CommunityStorageVideo />
      </v-window-item>
      <v-window-item :value="3">
        <CommunityStorageVideo />
      </v-window-item>
    </v-window>

    <!-- 고민 내용 -->
    <label for="content" class="text-grey mt-3">고민 내용</label>
    <textarea
      name="content"
      rows="4"
      v-model="content"
      placeholder="피드백 받고 싶은 내용을 작성해주세요"
    ></textarea>

    <!-- 버튼 -->
    <div class="d-flex justify-center justify-space-evenly mt-2">
      <v-btn color="grey" variant="tonal" @click="goCommunityList()"
        >취소</v-btn
      >
      <v-btn
        color="#BB66FF"
        variant="tonal"
        @click="requestCreateMyPost()"
        :disabled="title == null || content == ''"
        >글쓰기</v-btn
      >
    </div>
  </v-container>
</template>

<style scoped>
label {
  display: block;
}

textarea,
.title {
  resize: none;
  padding: 10px;
  border: 5px solid #f0e8f6;
  width: 100%;
  outline-color: #f0e8f6;
}

textarea::placeholder {
  color: #9e9e9e;
}
</style>
