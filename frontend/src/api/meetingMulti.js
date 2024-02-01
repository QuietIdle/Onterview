import { localAxios } from "@/api/index.js"
import { useUserStore } from "@/stores/user";

const userStore = useUserStore()
const authToken = userStore.accessToken
const api = localAxios();

api.defaults.headers.common['Authorization'] = `${authToken}`

export const multiApi = {
  getMatch: function () {
    return api.get(`/api/meeting/matching`)
  },
}