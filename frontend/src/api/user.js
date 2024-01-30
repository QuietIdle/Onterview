import { localAxios } from "@/api/index.js"
import { useUserStore } from "@/stores/user"

const api = localAxios()


const postSignUp = function (payload, success, error) {
    console.log("request post, sign up")
    api.post(`/api/user/signup`, payload).then(success).catch(error)
}

const postLogin = function (payload, success, error) {
    console.log("request post, login")
    api.post(`/api/user/login`, payload).then(success).catch(error)
}

const getIsDuplicatedEmail = function (email, success, error) {
    console.log("request get, is duplicated email")
    api.get(`/api/user/check/email?email=${email}`).then(success).catch(error)
}

const getIsDuplicatedNickname = function (nickname, success, error) {
    console.log("request get, is duplicated nickname")
    api.get(`/api/user/check/nickname?nickname=${nickname}`).then(success).catch(error)
}

const patchUpdateUser = function (payload, success, error) {
    console.log("request patch, update user")
    const userStore = useUserStore()
    api.defaults.headers["Authorization"] = userStore.accessToken
    api.patch(`/api/user`, payload).then(success).catch(error)
}

const patchChangeUserPwd = function (payload, success, error) {
    console.log("request patch, change user password")
    const userStore = useUserStore()
    api.defaults.headers["Authorization"] = userStore.accessToken
    api.patch(`/api/user/password`, payload).then(success).catch(error)
}

const deleteDeleteUser = function (payload, success, error) {
    console.log("request delete, delete user")
    const userStore = useUserStore()
    api.defaults.headers["Authorization"] = userStore.accessToken
    api.delete(`/api/user`, payload).then(success).catch(error)
}

export { postSignUp, postLogin, getIsDuplicatedEmail, getIsDuplicatedNickname, patchUpdateUser, patchChangeUserPwd, deleteDeleteUser }
