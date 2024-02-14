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

api.defaults.headers.common['Authorization'] = `${authToken}`

export const apiMethods = {
    getVideo: function (v_id) {
        //console.log('request get video')
        return api.get(`/api/video/${v_id}`)
    },
    getVideoAll: function (q_id) {
        //console.log('request get all videos')
        return api.get(`/api/my-question/${q_id}`)
    },
    getUserVideoAll: function (category) {
        return api.get(`/api/video?category=${category.toUpperCase()}`)
    },
    deleteVideos: function (v_ids) {
        api.defaults.headers.common['Authorization'] = `${authToken}`
        return api.post('/api/video/delete', v_ids)
    },
    patchVideo: function (v_id, req_body) {
        return api.patch(`/api/video/${v_id}`, req_body)
    },
    saveVideo: function (req_body) {
        return api.post(`/api/video`, req_body)
    },
    getInterviewList: function (roomType) {
        console.log(roomType)
        return api.get(`/api/interview-room?roomType=${roomType.toUpperCase()}`)
    },
    getInterviewDetail: function (interviewRoomId) {
        return api.get(`/api/interview-room/${interviewRoomId}`)
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
