<template>
  <div class="c-app flex-row align-items-center">
    <CContainer>
      <CRow class="justify-content-center">
        <CCol md="8">
          <CCardGroup>
            <CCard class="p-8">
              <CCardBody>
                <CForm @submit="formSubmit">
                  <h1>Log In </h1>
                  <p class="text-muted">Log in your admin account </p>
                  <CInput
                    id="user_name"
                    label="Username"
                    placeholder="Enter your email"
                    v-model="username"
                    type="email"
                    autocomplete="email"
                  >
                    <template #prepend-content><CIcon name="cil-user"/></template>
                  </CInput>
                  <CInput
                    label="Password"
                    id="password"
                    placeholder="Enter your password"
                    v-model="password"
                    type="password"
                    autocomplete="curent-password"
                  >
                    <template #prepend-content><CIcon name="cil-lock-locked"/></template>
                  </CInput>
                  <CRow v-if="errorMsg !== ''">
                    <CCol col="12">
                      <CAlert
                          :show.sync="dismissCountDown"
                          color="danger"
                          fade
                        >
                          {{ errorMsg }}
                    </CAlert>
                      <!-- <b-alert
                        :show="dismissCountDown"
                        dismissible
                        variant="danger"
                        @dismissed="dismissCountDown=0"
                        @dismiss-count-down="countDownChanged"
                        class="errormsg"
                      > {{errorMsg}}
                      </b-alert> -->
                    </CCol>
                  </CRow>
                  <CRow>
                    <CCol col="6" class="text-left">
                      <CButton color="primary" type="submit" class="px-4">Login</CButton>
                    </CCol>
                    <CCol col="6" class="text-right">
                      <CButton color="link" class="px-0">Forgot password?</CButton>
                      <CButton color="link" class="d-lg-none">Register now!</CButton>
                    </CCol>
                  </CRow>
                </CForm>
              </CCardBody>
            </CCard>
            <CCard
              color="primary"
              text-color= "white"
              class="text-center py-5 d-md-down-none"
              body-wrapper>
              <CCardBody>
                <h2>Team Sport</h2>
                <p>Management App for Sport team</p>
              </CCardBody>
            </CCard>
          </CCardGroup>
        </CCol>
      </CRow>
    </CContainer>
  </div>
</template>

<script>
import axios from "../../../http-common";

export default {
  name: "Login",
  data() {
    return {
      username: "",
      password: "",
      errorMsg: "",
      msg: "",
      dismissSecs: 3,
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
      // Check input email & password
      if (this.username == "" || this.password == "") {
        this.msg = "Please enter all the fields.";
        this.showAlert();
        return;
      } 

      axios
        .post("/login", requestObject)
        .then((resp) => {
          if (resp.data.success) {
            if(resp.data.data.role == "2"){
              localStorage.setItem('token',resp.data.data.token);
              localStorage.setItem('username', resp.data.data.username);
              localStorage.setItem('userId', resp.data.data.userId);
              localStorage.setItem('role', resp.data.data.role);
              this.$router.push({ path: `/dashboard` });
            }else{
              this.msg = "You are not an admin!";
              this.showAlert();
            }
          } else {
            this.msg = "Username or Password is incorrect, please try again.";
            this.showAlert();
          }
        })
        .catch(function (err) {
          this.msg = err;
          this.showAlert();
        });
    },
  },
};
</script>
