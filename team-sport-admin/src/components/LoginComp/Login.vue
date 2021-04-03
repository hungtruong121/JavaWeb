<template>
  <div class="loginForm">
    <div class="page-wrapper p-t-130 p-b-100 font-poppins">
      <div class="wrapper wrapper--w680">
        <div class="card card-4 p-t-50 a">
          <h2 class="title">ADMINISTRATOR</h2>
          <div class="card-body">
            <b-alert
              :show="dismissCountDown"
              dismissible
              variant="danger"
              @dismissed="dismissCountDown=0"
              @dismiss-count-down="countDownChanged"
              class="errormsg"
            >{{errorMsg}}</b-alert>
            <form @submit="formSubmit">
              <div class="row row-space m-b-20">
                <div class="col-12">
                  <div class="input-group">
                    <input
                      class="input--style-4 inputStype"
                      v-model="username"
                      type="email"
                      placeholder="Enter your email"
                    />
                  </div>
                </div>
              </div>
              <div class="row row-space m-b-15">
                <div class="col-12">
                  <div class="input-group-pass">
                    <input
                      class="input--style-4 inputStype"
                      v-model="password"
                      type="password"
                      placeholder="Enter your password"
                    />
                  </div>
                </div>
              </div>
              <!-- <div class="row row-space">
                <div class="col-12">
                  <div class="input-group">
                    <b-form-checkbox
                      id="checkbox-1"
                      v-model="remember"
                      name="checkbox-1"
                      value="accepted"
                      unchecked-value="not_accepted"
                    >Remember password?</b-form-checkbox>
                  </div>
                </div>
              </div> -->
              <div class="row row-space p-t-15">
                <div class="col-3"></div>
                <div class="col-6">
                  <b-button block variant="success" type="submit">Login</b-button>
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
import axios from "@/http-common";
export default {
  name: "login",
  data() {
    return {
      username: "",
      password: "",
      errorMsg: "",
      msg: "",
      dismissSecs: 5,
      dismissCountDown: 0,
      token: "",
    };
  },
  methods: {
    countDownChanged(dismissCountDown) {
      this.dismissCountDown = dismissCountDown;
    },
    showAlert() {
      this.errorMsg = this.msg;
      this.dismissCountDown = this.dismissSecs;
    },
    formSubmit(e) {
      e.preventDefault();
      let requestObject = {
        username: this.username,
        password: this.password,
      };
      axios
        .post("/login", requestObject)
        .then((resp) => {
          let response = resp.data;
          if (response != null) {
            if (response.success == false) {
              if (this.username == "" || this.password == "") {
                this.msg = "Please enter all the fields.";
                this.showAlert();
              } else {
                this.msg =
                  "Username or Password is incorrect, please try again.";
                this.showAlert();
              }
            }
            if ((response.success == true) & (response.data != null)) {
              localStorage.setItem('token',response.data.token)
              localStorage.setItem('username', response.data.username)
              this.$router.push({name: 'changepass'})
              console.log(this.token);
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
.loginForm {
  .title {
    margin-bottom: 0px;
  }
  .inputStype {
    line-height: 40px;
  }
  .input--style-4 {
    border-radius: unset;
  }
  .m-b-20 {
    margin-bottom: 20px;
  }
  .m-b-15,
  .input-group-pass {
    margin-bottom: 15px;
  }
  .p-t-50 {
    padding-top: 50px;
  }
  .card-4 .card-body {
    padding: 42px 65px;
  }
}

@import "../../assets/styles/main.css";
</style>