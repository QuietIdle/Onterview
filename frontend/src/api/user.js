import { localAxios } from "@/api/index.js"

const api = localAxios()


const postSignUp = function (payload, success, error) {
    console.log("request post sign up")
    api.post(`/api/signup`, payload).then(success).catch(error)
}

const getIsDuplicatedEmail = function (payload) {
    console.log("request get is duplicated email")
}

const getIsDuplicatedNickname = function (payload) {
    console.log("request get is duplicated nickname")
}

export { postSignUp, getIsDuplicatedEmail, getIsDuplicatedNickname }

// async function userConfirm(param, success, fail) {
//     await api.post(`/login`, param).then(success).catch(fail);
//   }