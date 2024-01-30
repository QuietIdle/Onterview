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
    getUserVideoAll: function () {
        return api.get(`/api/video/all`)
    },
    deleteVideos: function (v_ids) {
        return api.post('/api/video/delete', v_ids)
    },
    patchVideo: function (v_id, req_body) {
        return api.patch(`/api/video/${v_id}`, req_body)
    },
    saveVideo: function (req_body) {
        return api.post(`/api/video`, req_body)
    }
}

export const fileServer = {
    uploadVideo: function (formData) {
        return api2.post(`/api/chunk/upload`, formData)
    },
    playVideo: function (filename, st, ed) {
        return api2.get(`/api/chunk/stream/${filename}`, {
            responseType: 'arraybuffer',
            headers: {
                Range: `bytes=${st}-${ed}`,
            }
        });
    },
    cancelUpload: function (username, fileName) {
        return api2.delete(`/api/chunk?username=${username}&fileName=${fileName}`)
    },
}
