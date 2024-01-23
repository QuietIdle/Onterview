import { localAxios } from "@/api/index.js"

const api = localAxios();

const getCommonQuestionList = function (success, error) {
    console.log("request get common question list")
    api.get(`/api/common-question`).then(success).catch(error)
}

const getMyQuestionList = function (success, error) {
    console.log("request get my question list")
    api.get(`/api/my-question`).then(success).catch(error)
}

export { getCommonQuestionList, getMyQuestionList }