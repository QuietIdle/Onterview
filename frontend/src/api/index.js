import axios from "axios"

const localURL = 'http://i10a504.p.ssafy.io:8081/';

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