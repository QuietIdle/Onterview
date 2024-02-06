import { localAxios, localAxios2 } from '@/api/index'
import { useUserStore } from '@/stores/user'

const api = localAxios()
const userStore = useUserStore()

const getAllPostList = function (order) {
    api.defaults.headers.common["Authorization"] = userStore.accessToken
    return api.get(`/api/community?order=${order}`)
}

const getMyPostList = function (order) {
    api.defaults.headers.common["Authorization"] = userStore.accessToken
    return api.get(`/api/community/my?order=${order}`)
}

const getPostDetail = function (articleId) {
    api.defaults.headers.common["Authorization"] = userStore.accessToken
    return api.get(`/api/community/post/detail/${articleId}`)
}

const getCommentDetail = function (articleId) {
    api.defaults.headers.common["Authorization"] = userStore.accessToken
    return api.get(`/api/community/comment/detail/${articleId}`)
}

const postCreateMyPost = function (payload) {
    api.defaults.headers.common["Authorization"] = userStore.accessToken
    return api.post(`/api/community`, payload)
}

const deleteDeleteMyPost = function (articleId) {
    api.defaults.headers.common["Authorization"] = userStore.accessToken
    return api.delete(`/api/community/${articleId}`)
}

const patchUpdateMyPostContent = function (articleId, payload) {
    api.defaults.headers.common["Authorization"] = userStore.accessToken
    return api.patch(`/api/community/${articleId}`, payload)
}

const postCreateMyComment = function (payload) {
    api.defaults.headers.common["Authorization"] = userStore.accessToken
    return api.post(`/api/community/comment`, payload)
}

const deleteDeleteMyComment = function (commentId) {
    api.defaults.headers.common["Authorization"] = userStore.accessToken
    return api.delete(`/api/community/comment/${commentId}`)
}

const patchLikePost = function (articleId) {
    api.defaults.headers.common["Authorization"] = userStore.accessToken
    return api.patch(`/api/community/like/${articleId}`)
}

// video
const getInterviewVideoList = function (roomType) {
    const api2 = localAxios2()
    api2.defaults.headers.common["Authorization"] = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvbnRlcnZpZXdAZ21haWwuY29tIiwiaWF0IjoxNzA3MjA3NTE0LCJleHAiOjE3MDcyOTM5MTR9.sF8fDBKFJx50xWfi_ihMCufnbFMGqobjXwg6Yq1iqp8'
    return api2.get(`api/interview-room/video?roomType=${roomType}`)
}

const getSelfSpeechVideoList = function () {  
    const api2 = localAxios2()
    api2.defaults.headers.common["Authorization"] = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvbnRlcnZpZXdAZ21haWwuY29tIiwiaWF0IjoxNzA3MjA3NTE0LCJleHAiOjE3MDcyOTM5MTR9.sF8fDBKFJx50xWfi_ihMCufnbFMGqobjXwg6Yq1iqp8'
    return api2.get(`/api/my-question-folder/video`)
}

export {
    getAllPostList,
    getMyPostList,
    getPostDetail,
    getCommentDetail,
    postCreateMyPost,
    deleteDeleteMyPost,
    patchUpdateMyPostContent,
    postCreateMyComment,
    deleteDeleteMyComment,
    patchLikePost,
    getInterviewVideoList,
    getSelfSpeechVideoList,
}