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

export { getCommonQuestionList, getMyQuestionList }