import { localAxios } from "@/api/index.js"

const api = localAxios()

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
