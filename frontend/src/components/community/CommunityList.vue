<script setup>
// lib
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'

// api
import { getAllPostList, getMyPostList } from '@/api/community'

// asset
import searchButton from '@/assets/community/searchButton.svg'

// store
import { useUserStore } from '@/stores/user'

onMounted(() => {
  requestAllPostList()
})

const requestAllPostList = async function () {
  try {
    const response = await getAllPostList()
    postList.value = response.data
  } catch (error) {
    alert(`전체 게시글을 조회하지 못했습니다.`)
  }
}

const requestMyPostList = async function () {
  try {
    const response = await getMyPostList()
    postList.value = response.data
  } catch (error) {
    alert('내가 쓴 게시글을 조회하지 못했습니다.')
  }
}
// 게시판
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
  { title: '추천수', key: 'likeCount', sortable: false, align: 'center' },
  { title: '댓글수', key: 'commentCount', sortable: false, align: 'center' },
  { title: '작성날짜', key: 'writtenDate', sortable: false }
])

const pageCount = computed(() => {
  return Math.ceil(postList.value.length / itemsPerPage.value)
})

// 라우터
const router = useRouter()

const goCommunityDetail = function (articleId) {
  router.push({ name: 'community-detail', params: { articleId: articleId } })
}

const isLogin = function () {
  const userStore = useUserStore()
  return userStore.accessToken === null
}
</script>

<template>
  <v-container>
    <!-- 전체보기, 내가 쓴 게시글 보기 토글 -->
    <v-row justify="end">
      <v-btn
        density="compact"
        variant="plain"
        :href="'#전체보기'"
        @click="requestAllPostList"
        >전체 보기</v-btn
      >
      <div style="color: rgb(190, 190, 190)">|</div>
      <v-btn
        density="compact"
        variant="plain"
        :href="'#내가쓴글'"
        @click="requestMyPostList"
        :disabled="isLogin()"
        >내가 쓴 게시글 보기</v-btn
      >
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
      <template v-slot:item="{ item }">
        <tr @click="goCommunityDetail(item.articleId)">
          <td>{{ item.writerNickname }}</td>
          <td>{{ item.title }}</td>
          <td align="center">{{ item.likeCount }}</td>
          <td align="center">{{ item.commentCount }}</td>
          <td>{{ item.writtenDate }}</td>
        </tr>
      </template>
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
      <template v-slot:no-data> 게시글이 없습니다 </template>
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
