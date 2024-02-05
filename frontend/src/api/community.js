import { localAxios } from '@/api/index'
import { useUserStore } from '@/stores/user'

const api = localAxios()
const userStore = useUserStore()
api.defaults.headers.common["Authorization"] = userStore.accessToken

const getAllPostList = function () {
    return api.get(`/api/community?order=recent`)
}

const getMyPostList = function () {
    return api.get(`/api/community/my?order=recent`)
}

const getPostDetail = function (articleId) {
    return api.get(`/api/community/post/detail/${articleId}`)
}

// const getCommentDetail = function () {}

const deleteDeleteMyPost = function (articleId) {
    return api.delete(`/api/community/${articleId}`)
}

const patchUpdateMyPostContent = function (articleId, payload) {
    return api.patch(`/api/community/${articleId}`, payload)
}

export {
    getAllPostList,
    getMyPostList,
    getPostDetail,
    deleteDeleteMyPost,
    patchUpdateMyPostContent
}