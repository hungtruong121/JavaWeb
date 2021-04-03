import axios from "axios"
// import Login from './components/LoginComp/Login.vue'
let TOKEN = localStorage.getItem('token');
export default axios.create({
  baseURL: "http://localhost:8080/teamsport/api/v1",
  headers: {
    "Content-type": "application/json",
    "Authorization": 'Bearer '+TOKEN
  }
});

