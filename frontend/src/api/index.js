import axios from "axios"

// const deploymentURL = 'http://i10a504.p.ssafy.io:8081/'
// const localURL = 'http://localhost:8080/'
const testURL = 'http://70.12.247.35:8081/'  // 은지

// local vue api axios instance
function localAxios() {
  const instance = axios.create({
    baseURL: testURL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });
  return instance;
}

export { localAxios }