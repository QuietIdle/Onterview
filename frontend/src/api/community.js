import { localAxios } from '@/api/index'

const api = localAxios()

const getAllPostList = function () {
    return api.get(`/api/community?order=recent`)
}

const getMyPostList = function () {
    return api.get(`/api/community/my?order=like`)
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