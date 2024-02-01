import { ref } from 'vue'
import { defineStore } from 'pinia'
import { getAllPostList, getMyPostList } from '@/api/community'

const useCommunityStore = defineStore('community', () => {
  const allPostList = ref({
    postList: [
      {
        articleId: 5,
        writerNickname: '이경민',
        title: '성격의 장단점',
        likeCount: 4,
        commentCount: 19,
        writtenDate: '1999/4/19'
      },
      {
        articleId: 9,
        writerNickname: '김경민',
        title: '지원 동기',
        likeCount: 4,
        commentCount: 19,
        writtenDate: '1999/4/19'
      },
      {
        articleId: 40,
        writerNickname: '최경민',
        title: '인상깊은 협업 경험',
        likeCount: 4,
        commentCount: 19,
        writtenDate: '1999/4/19'
      }
    ]
  })

  const myPostList = ref({

  })

  const requestPostList = async function () {
    try {
      const response = await getAllPostList()
      return response.data
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
})

export default useCommunityStore