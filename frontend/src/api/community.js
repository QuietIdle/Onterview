import { localAxios } from '@/api/index'

const api = localAxios()

const getAllPostList = () => {
    return api.get(`/api/community?order=recent`)
}

const getMyPostList = () => {
    return api.get(`/api/community/my?order=like`)
}

const getPostDetail = (articleId) => {
    return api.get(`/api/community/post/detail/${articleId}`)
}

// const getCommentDetail = () => {}

export {
    getAllPostList,
    getMyPostList,
    getPostDetail,
}