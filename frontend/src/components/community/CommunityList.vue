<script setup>
import { ref, onMounted } from 'vue'
import searchButton from '@/assets/community/searchButton.svg'

import useCommunityStore from '@/stores/community'

const communityStore = useCommunityStore()

onMounted(() => {
  communityStore.requestPostList()
  postList.value = communityStore.allPostList
})

const postList = ref({})
const search = ref('')
</script>

<template>
  <v-container>
    <!-- 전체보기, 내가 쓴 게시글 보기 토글 -->
    <v-row justify="end">
      <v-btn text small :href="'#전체보기'">전체 보기</v-btn>
      <v-btn text small :href="'#내가쓴글'">내가 쓴 게시글 보기</v-btn>
    </v-row>
    <v-row>
      <v-col cols="12" md="6" class="d-flex align-start">
        <v-select
          label="Select"
          :items="['제목', '내용']"
          single-line
          variant="solo"
          density="compact"
          hide-details
        ></v-select>
        <v-text-field
          v-model="search"
          label="검색어를 입력해주세요"
          single-line
          variant="solo"
          density="compact"
          hide-details
        ></v-text-field>
        <v-img class="search-button" :src="searchButton"></v-img>
      </v-col>
      <v-spacer></v-spacer>
      <v-col cols="12" md="6" class="d-flex justify-end">
        <!-- 필터 영역 -->
        <v-select
          label="Filter"
          :items="['추천순', '내용']"
          single-line
          variant="solo"
          density="compact"
          hide-details
        ></v-select>
        <!-- 글쓰기 버튼 -->
        <v-btn color="primary" class="ml-2">글쓰기</v-btn>
      </v-col>
    </v-row>

    <div class="board"></div>
    <div class="pagination"></div>
  </v-container>
</template>

<style scoped>
.search-button {
  align-self: center;
  max-height: 40px;
}
</style>
