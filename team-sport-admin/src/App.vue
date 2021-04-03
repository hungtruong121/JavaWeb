<template>
  <div id="app" class="container-fluid">
    <div class="site-info">
      <h1>Team Sport</h1>
      <h3>Web Admin</h3>
    </div>
    <nav>
      <!-- <router-link class="btn btn-primary" to="/">List Privacys</router-link> -->
      <router-link class="btn btn-primary" to="/sports">List Sport</router-link>
      <router-link class="btn btn-primary" to="/sport/create">Create Sport</router-link>
      <router-link class="btn btn-primary" to="/login">login</router-link>
      <router-link class="btn btn-primary" to="/changepass">Change Password</router-link>
      <span v-on:click="logout">
        <router-link class="btn btn-primary" to="/logout">Logout</router-link>
      </span>
      <!-- <router-link class="btn btn-primary" to="/add">Add Privacy</router-link> -->
    </nav>
    <br />
    <router-view />
  </div>
</template>
 
<script>
import axios from "@/config";

export default {
  name: "app",
  methods: {
    logout() {
      axios
        .get("/user/logout", {
          headers: {
            "Access-Control-Allow-Origin": "*",
            "Access-Control-Allow-Methods": "GET, PUT, POST, DELETE, OPTIONS",
            "Access-Control-Allow-Headers":
              "Origin, Content-Type, X-Auth-Token",
            "Content-type": "application/json",
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
          useCredentails: true,
        })
        .then((response) => {
          localStorage.removeItem("token");
          localStorage.removeItem("username");
          console.log(response);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>
 
<style>
.site-info {
  color: blue;
  margin-bottom: 20px;
}

.btn-primary {
  margin-right: 5px;
}

.container-fluid {
  text-align: center;
}
</style>
