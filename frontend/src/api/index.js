import axios from "axios"

//const localURL = 'http://localhost:8080/'
const localURL = 'http://70.12.247.51:8080'; // 재용이 DB IP
//const localURL = 'http://70.12.247.71:8080/'  // 경민

// local vue api axios instance
function localAxios() {
  const instance = axios.create({
    baseURL: localURL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });
  return instance;
}

export { localAxios }