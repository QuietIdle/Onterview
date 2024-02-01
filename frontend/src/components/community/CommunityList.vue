<script setup>
import { ref, computed, onMounted } from 'vue'
import searchButton from '@/assets/community/searchButton.svg'

import useCommunityStore from '@/stores/community'

const communityStore = useCommunityStore()

onMounted(() => {
  // communityStore.requestPostList()
  postList.value = communityStore.allPostList.postList
})

const postList = ref([])
const search = ref('')

const page = ref(1)
const itemsPerPage = ref(10)
const headers = ref([
  {
    align: 'start',
    key: 'writerNickname',
    sortable: false,
    title: '작성자'
  },
  { title: '면접 질문', key: 'title', sortable: false },
  { title: '추천수', key: 'likeCount', sortable: false },
  { title: '조회수', key: 'commentCount', sortable: false },
  { title: '작성날짜', key: 'writtenDate', sortable: false }
])

const pageCount = computed(() => {
  return Math.ceil(postList.value.length / itemsPerPage.value)
})
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
    <!-- 게시판 -->
    <v-data-table
      v-model:page="page"
      :headers="headers"
      :items="postList"
      :items-per-page="itemsPerPage"
      hover
    >
      <template v-slot:bottom>
        <div class="text-center pt-2">
          <v-pagination
            v-model="page"
            :length="pageCount"
            :total-visible="8"
            rounded="circle"
            prev-icon="mdi-menu-left"
            next-icon="mdi-menu-right"
            active-color="#BB66FF"
            density="comfortable"
          ></v-pagination>
        </div>
      </template>
    </v-data-table>
    <div class="pagination"></div>
  </v-container>
</template>

<style scoped>
.search-button {
  align-self: center;
  max-height: 40px;
}
</style>
