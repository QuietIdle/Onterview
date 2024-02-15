import { localAxios } from "@/api/index.js"
import axios from 'axios'
import { useUserStore } from "@/stores/user";

const userStore = useUserStore()
const authToken = userStore.accessToken

const api = localAxios()
const api2 = axios.create({
    baseURL: 'https://i10a504.p.ssafy.io/',
    headers: {
        "Authorization": `${authToken}`
    }
});



export const apiMethods = {
    getVideo: function (v_id) {
        api.defaults.headers.common['Authorization'] = `${authToken}`
        return api.get(`/api/video/${v_id}`)
    },
    getVideoAll: function (q_id) {
        api.defaults.headers.common['Authorization'] = `${authToken}`
        return api.get(`/api/my-question/${q_id}`)
    },
    getUserVideoAll: function (category) {
        api.defaults.headers.common['Authorization'] = `${authToken}`
        return api.get(`/api/video?category=${category.toUpperCase()}`)
    },
    getSelfVideoList: function (category, keyword, bookmark) {
        api.defaults.headers.common['Authorization'] = `${authToken}`
        return api.get(`/api/video?category=${category.toUpperCase()}&keyword=${keyword}&bookmark=${bookmark}`)
    },
    deleteVideos: function (v_ids) {
        api.defaults.headers.common['Authorization'] = `${authToken}`
        return api.post('/api/video/delete', v_ids)
    },
    patchVideo: function (v_id, req_body) {
        api.defaults.headers.common['Authorization'] = `${authToken}`
        return api.patch(`/api/video/${v_id}`, req_body)
    },
    saveVideo: function (req_body) {
        api.defaults.headers.common['Authorization'] = `${authToken}`
        return api.post(`/api/video`, req_body)
    },
    getInterviewList: function (roomType, keyword) {
        api.defaults.headers.common['Authorization'] = `${authToken}`
        console.log(`/api/interview-room?roomType=${roomType.toUpperCase()}`)
        return api.get(`/api/interview-room?roomType=${roomType.toUpperCase()}`)
    },
    getInterviewDetail: function (interviewRoomId) {
        api.defaults.headers.common['Authorization'] = `${authToken}`
        return api.get(`/api/interview-room/${interviewRoomId}`)
    },
    deleteInterview: function (payload) {
        api.defaults.headers.common['Authorization'] = `${authToken}`
        return api.post(`/api/interview-room/delete`, payload)
    }
}

export const fileServer = {
    uploadVideo: function (formData) {
        return api2.post(`/api-file/chunk/upload`, formData)
    },
    playVideo: function (filename, st, ed) {
        return api2.get(`/api-file/chunk/stream/${filename}`, {
            responseType: 'arraybuffer',
            headers: {
                Range: `bytes=${st}-${ed}`,
            }
        });
    },
    cancelUpload: function (fileName) {
        return api2.delete(`/api-file/chunk?fileName=${fileName}`)
    },
}
