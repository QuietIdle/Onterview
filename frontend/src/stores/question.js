import { ref } from 'vue'
import { defineStore } from 'pinia'
import { getCommonQuestionList, getMyQuestionList } from "@/api/question"

export const useQuestionStore = defineStore('question', () => {
  const commonQuestionList = ref([
    {
      common_question_folder_id: 5,
      folder: '인성 질문',
      question: [{ common_question_id: 500, question: '성격의 장단점' }]
    },
    {
      common_question_folder_id: 6,
      folder: '기술 질문',
      question: [
        { common_question_id: 600, question: 'OOP 정의' },
        { common_question_id: 601, question: '클래스와 객체의 차이점' },
      ]
    }
  ]) 
  
  const myQuestionList = ref([])

  const requestCommonQuestionList = function () {
    const success = function (response) {
      if (response.status === 201) {
        return
      }
    }

    const error = function () {
      alert(`빈출 면접 문항 목록을 불러오지 못했습니다. `)
      return
    }
    
    getCommonQuestionList(success, error)
  }
  
  const requestMyQuestionList = async function () {
    try {
      const response = await getMyQuestionList()
      myQuestionList.value = response.data
      console.log('response myQuestionList', response)
    } catch (error) {
      alert(`나의 면접 문항 목록을 불러오지 못했습니다. `)
      console.log('error myQuestionList', error)
    }
  }
  
  return {
    commonQuestionList,
    myQuestionList,
    requestCommonQuestionList,
    requestMyQuestionList,
  }
})