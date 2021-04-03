<template>
    <div class="page-wrapper bg-gra-02 font-poppins">
        <CAlert
              v-if="msgErrors !== ''"
              :show.sync="dismissCountDown"
              class="position-fixed top-right sticky-top"
              closeButton
              color="danger"
              fade
            >
              {{ msgErrors }}
        </CAlert>
        <b-alert
            v-if="msgSuccess !== ''"
            :show="dismissCountDown"
            class="position-fixed top-right sticky-top"
            dismissible
            variant="success"
            fade
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged"
        >
            {{ msgSuccess }}
        </b-alert>
        <div class="wrapper">
            <div class="card card-4">
                <div class="card-body">
                    <h1>{{ title }}</h1>
                    <p class="text-muted"> Create a new user</p>
                    <form @submit="checkFormCreate" method="post">
                                <CInput
                                label="Full name"
                                    v-model="fullName"
                                    required
                                    ref="full_name"
                                    placeholder="Enter fullname..."
                                    invalid-feedback="Please provide at least 2 characters."
                                    :is-valid="validatorFullName"
                                >
                                    <template #prepend-content><CIcon name="cil-pencil"/></template>
                                </CInput>

                                <CInput
                                label="Email"
                                    v-model="email"
                                    placeholder="Enter your email..."
                                    invalid-feedback="Enter valid email"
                                    :is-valid="validatorEmail"
                                >
                                    <template #prepend-content><CIcon name="cil-envelope-open"/></template>
                                </CInput>
                                <CInput
                                label="Phone number"
                                    v-model="phone"
                                    type="number"
                                    placeholder="Enter your phone number..."
                                    invalid-feedback="Please provide less than 15 characters."
                                    :is-valid="validatorPhone"
                                >
                                    <template #prepend-content><CIcon name="cil-grid"/></template>
                                </CInput>
                                <CInput
                                label="Password"
                                    v-model="password"
                                    type="password"
                                    placeholder="Enter password..."
                                    invalid-feedback="Please provide at least 8 characters."
                                    :is-valid="validatorPassword"
                                >
                                    <template #prepend-content><CIcon name="cil-shield-alt"/></template>
                                </CInput>
                                <CInput
                                label="Confirm password"
                                    v-model="repassword"
                                    type="password"
                                    placeholder="Enter password again..."
                                    invalid-feedback="Please provide at least 8 characters."
                                    :is-valid="validatorPassword"
                                >
                                    <template #prepend-content><CIcon name="cil-shield-alt"/></template>
                                </CInput>
                        <div class="row row-space p-t-15">
                            <div class="col"></div>
                            <div class="col">
                                <button class="btn-action-sport btn-light" @click="$router.go(-1)" type="button">Back</button>
                            </div>
                            <div class="col">
                                <button class="btn-action-sport btn-info" @click="resetFields" type="button">Reset</button>
                            </div>
                            <div class="col">
                                <button class="btn-action-sport btn-success" type="submit">
                                    <b-spinner v-if="isLoading" small></b-spinner> Create
                                </button>
                            </div>
                            <div class="col"></div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "../../../http-common";

export default {
    name: "user-create",
    data() {
        return {
            title: "Create User",
            msgErrors: "",
            msgSuccess: "",
            dismissCountDown: 0,
            dismissSecs: 4,
            fullName: "",
            userName: "",
            password: "",
            repassword: "",
            phone: "",
            email: "",
            isLoading: false,
            reg: /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,24}))$/
        }
    },
    methods: {
        countDownChanged(dismissCountDown) {
            this.dismissCountDown = dismissCountDown
        },
        showAlertError(msg){
            this.msgErrors = msg;
            this.dismissCountDown = this.dismissSecs;
        },
        showAlertSuccess(msg){
            this.msgSuccess = msg;
            this.dismissCountDown = this.dismissSecs;
        },
        resetFields() {
            Object.assign(this.$data, this.$options.data.call(this));
        },
        validatorFullName(val) {
            if (val !== ""){
                return val.trim().length < 2 ? false : val.trim().length > 50 ? false : true;
            }
        },
        // validatorUserName(val) {
        //     return val.trim().length < 3 ? false : val.trim().length > 20 ? false : true;
        // },
        validatorPassword(val){
            if (val !== ""){
                return val ? val.trim().length >= 8 : false;
            }
        },
        validatorPhone(val){
            if (val !== ""){
                return val < 0 ? false : val.trim().length > 15 ? false : true;
            }
        },
        validatorEmail(val){
            if (val !== ""){
                return (val === "") ? false : (this.reg.test(this.email)) ? true : false;
            }
        },
        checkFormCreate(e) {
            e.preventDefault();

            if (this.fullName === "" || this.fullName === null)
                return this.showAlertError("Please input Full Name!");
            if (!this.validatorFullName(this.fullName))
                return this.showAlertError("Full Name is invalid!");
            if (!this.validatorEmail(this.email))
                return this.showAlertError("Email is invalid!");
             if (!this.validatorPhone(this.phone))
                return this.showAlertError("Phone number is invalid!");
            // if (this.userName === "" || this.userName === null){
            //     return this.showAlertError("Please input User Name!");
            // }
            // if (!this.validatorUserName(this.userName)){
            //     return this.showAlertError("User Name is invalid!");
            // }
            if (!this.validatorPassword(this.password))
                return this.showAlertError("Password is invalid!");
            if (this.password !== this.repassword)
                return this.showAlertError("Confirm Password is not correct!");

            this.isLoading = true;

            axios
                .post("/user/admin/create", this.userDTO)
                .then(response => {
                    this.isLoading = false;
                    if (response.data.success){
                        msg = "Create new User Successfully!";
                        this.showAlertSuccess(msg);
                        setTimeout(() => {
                            this.$router.push({ path: `/user/listusers` });
                        }, 2000);
                    } else {
                        msg = response.data.message;
                        return this.showAlertError(msg);
                    }
                })
                .catch(e => {
                    this.isLoading = false;
                    return this.showAlertError(e);
                });
        }
    },
    computed: {
        userDTO() {
            return {
                userFullName: this.fullName,
                userMail: this.email,
                password: this.password,
                userPhone: this.phone
            }
        },
    },

}
</script>

<style>
    .wrapper{
        width: 700px;
    }
    .card-body{
        width: 700;
    }
    h1{
    font-size: 2.1875rem;
    margin-bottom: 0.5rem;
    font-weight: 550;
    line-height: 1.2;
    }
    p{
        margin-bottom: 30px;
    }
    button{
        border-radius: 5px;
    }
    @import "../../assets/styles/main.css";
</style>