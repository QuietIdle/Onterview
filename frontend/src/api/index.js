import axios from "axios"

const deploymentURL = 'https://i10a504.p.ssafy.io/'
// const localURL = 'http://localhost:8080/'
//const testURL = 'http://70.12.247.51:8081/'

// local vue api axios instance
function localAxios() {
  const instance = axios.create({
    baseURL: deploymentURL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });
  return instance;
}

export { localAxios }