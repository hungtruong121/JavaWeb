<template>
  <div class="changePass">
    <div class="page-wrapper p-t-130 p-b-100 font-poppins">
      <div class="wrapper wrapper--w680">
        <div class="card card-4 p-t-50 a">
          <h2 class="title">Change Password</h2>
          <div class="card-body">
            <b-alert
              :show="dismissCountDown"
              dismissible
              :variant="mesStatus"
              @dismissed="dismissCountDown=0"
              @dismiss-count-down="countDownChanged"
              class="message"
            >{{message}}</b-alert>
            <form @submit="formSubmit">
              <div class="row row-space m-b-15">
                <div class="col-12">
                  <div class="input-group">
                    <input
                      class="input--style-4 inputStype"
                      v-model="userMail"
                      type="email"
                      placeholder="Your email"
                    />
                  </div>
                </div>
              </div>
              <div class="row row-space m-b-15">
                <div class="col-12">
                  <div class="input-group">
                    <input
                      class="input--style-4 inputStype"
                      v-model="oldPassword"
                      type="password"
                      placeholder="Old password"
                    />
                  </div>
                </div>
              </div>
              <div class="row row-space m-b-15">
                <div class="col-12">
                  <div class="input-group">
                    <input
                      class="input--style-4 inputStype"
                      v-model="password"
                      type="password"
                      placeholder="New password"
                    />
                  </div>
                </div>
              </div>
              <div class="row row-space m-b-10">
                <div class="col-12">
                  <div class="input-group">
                    <input
                      class="input--style-4 inputStype"
                      v-model="confirmPassword"
                      type="password"
                      placeholder="Confirm password"
                    />
                  </div>
                </div>
              </div>
              <div class="row row-space p-t-15">
                <div class="col-3"></div>
                <div class="col-6">
                  <b-button block variant="success" type="submit">Save</b-button>
                </div>
                <div class="col-3"></div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "@/config";
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
          console.log(response);
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
          console.log(resp);
        });
    },
  },
};
</script>
<style lang="scss">
.changePass {
  .title {
    margin-bottom: 0px;
  }
  .inputStype {
    line-height: 40px;
  }
  .input--style-4 {
    border-radius: unset;
  }
  .m-b-15 {
    margin-bottom: 15px;
  }
  .p-t-50 {
    padding-top: 50px;
  }
  .card-4 .card-body {
    padding: 42px 65px;
  }
  .btn-savePass {
    line-height: 45px;
  }
}

@import "../../assets/styles/main.css";
</style>

