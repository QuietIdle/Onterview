import { localAxios } from "@/api/index.js"

const api = localAxios();

const getCommonQuestionList = function () {
    return api.get(`/api/common-question-folder`)
}

const getMyQuestionList = function () {
    return api.get(`/api/my-question-folder?memberId=${1}`) // 추후 member_id 삭제 후 user token 추가
}

const postCreateMyQuestionFolder = function (payload) {
    return api.post(`/api/my-question-folder`, payload)
}

const postCreateMyQuestion = function (payload) {
    return api.post(`/api/my-question`, payload)
}

const deleteDeleteMyQuestionFolder = function (myQuestionFolderId) {
    return api.delete(`/api/my-question-folder/${myQuestionFolderId}`)
}

const deleteDeleteMyQuestion = function (myQuestionId) {
    return api.delete(`/api/my-question/${myQuestionId}`)
}

const patchUpdateMyQuestionFolder = function (myQuestionFolderId, payload) {
    return api.patch(`/api/my-question-folder/${myQuestionFolderId}`, payload) // myQuestionFolder
}

const patchUpdateMyQuestion = function (myQuestionId, payload) {
    return api.patch(`/api/my-question/${myQuestionId}`, payload) // myQuestion
}

export {
    getCommonQuestionList,
    getMyQuestionList,
    postCreateMyQuestionFolder,
    postCreateMyQuestion,
    deleteDeleteMyQuestionFolder,
    deleteDeleteMyQuestion,
    patchUpdateMyQuestionFolder,
    patchUpdateMyQuestion
}