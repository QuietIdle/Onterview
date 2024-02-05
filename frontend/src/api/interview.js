import { localAxios } from "@/api/index.js"
import { useUserStore } from "@/stores/user"

const api = localAxios()


const postInterviewQuestions = function (payload, success, error) {
    console.log("request post, interview questions")
    api.post(`/api/interview-room`, payload).then(success).catch(error)
}

export {
  postInterviewQuestions,
}