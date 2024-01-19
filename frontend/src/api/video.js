import { localAxios } from "@/api/index.js"

const api = localAxios()

export const apiMethods = {
    getVideo: function (v_id, success, error) {
        console.log('request get video')
        api.get(`/api/video/${v_id}`).then(success).catch(error)
    },
    getVideoAll: function (q_id, success, error) {
        console.log('request get all videos')
        api.get(`/api/video/all/${q_id}`).then(success).catch(error)
    },
}
