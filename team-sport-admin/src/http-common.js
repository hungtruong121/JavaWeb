import axios from "axios"

export default axios.create({
  // baseURL: "http://localhost:8080/teamsport/api/v1",
  baseURL: "https://livecart.nhaqueviet.com:8443/api/v1",
  headers: {
    "Content-type": "application/json",
  }
});

