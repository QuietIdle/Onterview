import { ref } from 'vue'
import { defineStore } from 'pinia'
import { getAllPostList, getMyPostList } from '@/api/community'

const useCommunityStore = defineStore('community', () => {
  const allPostList = ref([])
  const myPostList = ref([])

  const requestPostList = async function () {
    try {
      const response = await getAllPostList()
      allPostList.value = response.data
    } catch (error) {
      alert(`전체 게시글을 조회하지 못했습니다.`)
    }
  }

  const requestMyPostList = async function () {
    try {
      const response = await getMyPostList()
      return response.data
    } catch (error) {
      alert('내가 쓴 게시글을 조회하지 못했습니다.')
    }
  }

  return {
    allPostList,
    myPostList,
    requestPostList,
    requestMyPostList,
  }
}, { persist: true })

export default useCommunityStore