<template>
  <div class="submitform">
    <div v-if="!submitted">
        <div class="form-group">
          <label for="name">Privacy Name</label>
          <input type="text" class="form-control" id="privacyName" required v-model="privacy.privacyName" name="privacyName">
        </div>
    
        <div class="form-group">
          <label for="createdDate">Create Date</label>
          <input type="datetime-local" class="form-control" id="privacyName" required v-model="privacy.createdDate" name="createdDate">
        </div>
    
        <button v-on:click="savePrivacy" class="btn btn-success">Submit</button>
    </div>
    
    <div v-else>
      <h4>You submitted successfully!</h4>
      <button class="btn btn-success" v-on:click="newPrivacy">Add</button>
    </div>
  </div>
</template>
 
<script>
import http from "../http-common";
 
export default {
  name: "add-privacy",
  data() {
    return {
      privacy: {
        privacyId: 0,
        privacyName: "",
        createdDate: new Date(),
        isActive: false
      },
      submitted: false
    };
  },
  methods: {
    /* eslint-disable no-console */
    savePrivacy() {
      var data = {
        privacyName: this.privacy.privacyName,
        createdDate: this.privacy.createdDate
      };
 
      http
        .post("/privacy", data)
        .then(response => {
          this.privacy.privacyId = response.data.privacyId;
          console.log(response.data);
          this.submitted = true;
        })
        .catch(e => {
          console.log(e);
        });
      
    },
    newPrivacy() {
      this.submitted = false;
      this.privacy = {};
    }
    /* eslint-enable no-console */
  }
};
</script>
 
<style>
.submitform {
  max-width: 300px;
  margin: auto;
}
</style>