import { localAxios } from "@/api/index.js"

const api = localAxios()


const postSignUp = function (payload, success, error) {
    console.log("request post sign up")
    api.post(`/api/user/signup`, payload).then(success).catch(error)
}

const postLogin = function (payload, success, error) {
    console.log("request post login")
    api.post(`/api/user/login`, payload).then(success).catch(error)
}

const getIsDuplicatedEmail = function (email, success, error) {
    console.log("request get is duplicated email")
    api.get(`/api/user/check?email=${email}`).then(success).catch(error)
}

const getIsDuplicatedNickname = function (nickname, success, error) {
    console.log("request get is duplicated nickname")
    api.get(`/api/user/check?nickname=${nickname}`).then(success).catch(error)
}

export { postSignUp, postLogin, getIsDuplicatedEmail, getIsDuplicatedNickname }

// async function userConfirm(param, success, fail) {
//     await api.post(`/login`, param).then(success).catch(fail);
//   }