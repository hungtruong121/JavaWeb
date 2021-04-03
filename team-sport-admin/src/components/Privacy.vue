<template>
  <div v-if="this.privacy">
    <h4>Privacy</h4>
    <div>
      <label>Privacy Name: </label> {{this.privacy.privacyName}}
    </div>
    <div>
      <label>Create Date: </label> {{this.privacy.createdDate}}
    </div>
    <div>
      <label>Active: </label> {{this.privacy.isActive}}
    </div>
  
    <span v-if="this.privacy.isActive"
      v-on:click="updateActive(false)"
      class="button is-small btn-primary">Inactive</span>
    <span v-else
      v-on:click="updateActive(true)"
      class="button is-small btn-primary">Active</span>
  
    <span class="button is-small btn-danger" v-on:click="deletePrivacy()">Delete</span>
  </div>
  <div v-else>
    <br/>
    <p>Please click on a privacy...</p>
  </div>
</template>
 
<script>
import http from "../http-common";
 
export default {
  name: "privacy",
  props: ["privacy"],
  methods: {
    /* eslint-disable no-console */
    updateActive(status) {
      var data = {
        privacyId: this.privacy.privacyId,
        privacyName: this.privacy.privacyName,
        createdDate: this.privacy.createdDate,
        isActive: status
      };
 
      http
        .put("/privacy/" + this.privacy.privacyId, data)
        .then(response => {
          this.privacy.isActive = response.data.isActive;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },
    deletePrivacy() {
      http
        .delete("/privacy/" + this.privacy.privacyId)
        .then(response => {
          console.log(response.data);
          this.$emit("refreshData");
          this.$router.push('/');
        })
        .catch(e => {
          console.log(e);
        });
    }
    /* eslint-enable no-console */
  }
};
</script>