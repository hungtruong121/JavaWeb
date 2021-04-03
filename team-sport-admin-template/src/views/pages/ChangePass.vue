<template>
  <div class="d-flex align-items-center min-vh-100">
    <CContainer fluid>
      <CRow class="justify-content-center">
        <CCol md="4">
          <CCard class="mx-4 mb-0">
            <CCardBody class="p-4">
             <b-alert
              :show="dismissCountDown"
              dismissible
              :variant="mesStatus"
              @dismissed="dismissCountDown=0"
              @dismiss-count-down="countDownChanged"
              class="message"
            >{{message}}</b-alert>

              <CForm @submit="formSubmit" >
                <h1>Change password</h1>
                <p class="text-muted">Change your password</p>
                <CInput
                      label="Email"
                      v-model="userMail"
                      type="email"
                      placeholder="Your email"
                  autocomplete="email"
                  prepend="@"
                />
                <CInput
                label="Old Password"
                v-model="oldPassword"  
                  placeholder="Old password"
                  type="password"
                  autocomplete="new-password"
                >
                  <template #prepend-content><CIcon name="cil-lock-locked"/></template>
                </CInput>
                <CInput   
                label="New password"
                v-model="password"
                  placeholder="Password"
                  type="password"
                  autocomplete="new-password"
                >
                  <template #prepend-content><CIcon name="cil-lock-locked"/></template>
                </CInput>
                <CInput
                label="Confirm password"
                v-model="confirmPassword"
                  placeholder="Repeat password"
                  type="password"
                  autocomplete="new-password"
                  class="mb-4"
                >
                  <template #prepend-content><CIcon name="cil-lock-locked"/></template>
                </CInput>
                <b-button block variant="success" type="submit">Save</b-button>
              </CForm>
            </CCardBody>
          </CCard>
        </CCol>
      </CRow>
    </CContainer>
  </div>
</template>

<script>
import axios from "../../../http-common";

export default {
  name: "changePass",
  data() {
    return {
      userMail: "",
      oldPassword: "",
      password: "",
      confirmPassword: "",
      dismissSecs: 5,
      dismissCountDown: 0,
      currentPassword: "",
      message: "",
      msg: "",
      mesStatus: "danger"
    };
  },
  methods: {
    countDownChanged(dismissCountDown) {
      this.dismissCountDown = dismissCountDown;
    },
    showAlert() {
      this.message = this.msg;
      this.dismissCountDown = this.dismissSecs;
    },
    formSubmit(e) {
      e.preventDefault();
      let requestObject = {
        userMail: this.userMail,
        oldPassword: this.oldPassword,
        password: this.password,
      };
      axios
        .post("/user/changepass", requestObject, {
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
        .then((resp) => {
          let response = resp.data;
          let username = localStorage.getItem('username');
          if (response != null) {
            if (
              this.userMail == "" &&
              this.password == "" &&
              this.oldPassword == "" &&
              this.confirmPassword == ""
            ) {
              this.msg = "Please enter all the fields.";
              this.showAlert();
            } else if (
              this.userMail != username
            ) {
              this.msg = "Email or Password incorrect, please check again.";
              this.showAlert();
            } else if (
              this.confirmPassword != this.password
            ) {
              this.msg = "Password and Confirm password does not match.";
              this.showAlert();
            } else {
              this.mesStatus ="success"
              this.msg = "Your password has been changed successfully!";
              this.showAlert();
            }

          }
        })
        .catch(function (resp) {
        });
    },
  },
};
</script>

