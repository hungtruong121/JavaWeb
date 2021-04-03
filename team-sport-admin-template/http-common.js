import axios from "axios"

const AXIOS = axios.create({
  // baseURL: "http://localhost:8080/teamsport/api/v1",
  baseURL: "https://teamsport.fibocard.com:8443/api/v1",
  timeout: 10000,
  headers: {
    "Content-type": "application/json",
  }
});

AXIOS.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token');

    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    } else {
      delete AXIOS.defaults.headers.common.Authorization;
    }
    return config;
  },

  error => Promise.reject(error)
);

export default AXIOS;

