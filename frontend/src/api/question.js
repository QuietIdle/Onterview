import { localAxios } from "@/api/index.js"

const api = localAxios();

const getCommonQuestionList = function (success, error) {
    console.log("request get common question list")
    api.get(`/api/common-question`).then(success).catch(error)
}

const getMyQuestionList = function () {
    console.log("get myQuestionList")
    return api.get(`/api/my-question`)
}

const postMyQuestionFolder = function (payload) {
    console.log("post myQuestionFolder")
    return api.post(`/api/my-question-folder`, payload)
}

const postMyQuestion = function (payload) {
    console.log("post myQuestion")
    return api.post(`/api/my-question`, payload)
}

export { getCommonQuestionList, getMyQuestionList, postMyQuestionFolder, postMyQuestion }