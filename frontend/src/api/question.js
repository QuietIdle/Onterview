import { localAxios } from "@/api/index.js"
import { useUserStore } from "@/stores/user"

const api = localAxios()
const userStore = useUserStore()
api.defaults.headers.common["Authorization"] = userStore.accessToken

const getCommonQuestionList = function () {
    return api.get(`/api/common-question-folder`)
}

const getMyQuestionList = function () {
    return api.get(`/api/my-question-folder`) 
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

const patchUpdateMyQuestionAnswer = function (myQuestionId, payload) {
    return api.patch(`/api/my-question/answer/${myQuestionId}`, payload) // myQuestionAnswer
}

const patchMoveMyQuestion = function (payload) {
    return api.patch(`/api/my-question/move`, payload)
}

export {
    getCommonQuestionList,
    getMyQuestionList,
    postCreateMyQuestionFolder,
    postCreateMyQuestion,
    deleteDeleteMyQuestionFolder,
    deleteDeleteMyQuestion,
    patchUpdateMyQuestionFolder,
    patchUpdateMyQuestion,
    patchUpdateMyQuestionAnswer,
    patchMoveMyQuestion,
}