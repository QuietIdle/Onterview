import { localAxios } from "@/api/index.js"
import axios from 'axios'

const api = localAxios()
const api2 = axios.create({
    baseURL: 'http://70.12.247.60:8080',
});

export const apiMethods = {
    getVideo: function (v_id) {
        //console.log('request get video')
        return api.get(`/api/video/${v_id}`)
    },
    getVideoAll: function (q_id) {
        console.log('request get all videos')
        return api.get(`/api/my-question/${q_id}`)
    },
}

export const fileServer = {
    uploadVideo: function (idx, flag, formData) {
        return api2.post(`/api/chunk/upload?&chunkNumber=${idx}&endOfChunk=${flag}`, formData)
    },
    playVideo: function (filename) {
        return api2.get(`/api/chunk/stream/${filename}`, {
            responseType: 'arraybuffer'
        });
    },
}
