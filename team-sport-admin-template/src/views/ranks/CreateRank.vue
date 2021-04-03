<template>
    <div class="page-wrapper bg-gra-02 font-poppins" >
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
        <div class="wrapper wrapper--w960">
            <div class="card card-4">
                <div class="card-body">
                    <h2 class="title">{{ title }}</h2>
                    <form @submit="checkFormCreate" method="post">
                        <div class="row row-space">
                            <div class="col-3">
                                <label class="label">Commons</label> 
                            </div>
                            <div class="col-9"></div>
                        </div>
                        <div class="row row-space">
                            <div class="col-1"></div>
                            <div class="col-2">
                                <label class="label">Rank Name</label>
                            </div>
                            <div class="col-9">
                                <CInput
                                    v-model="teamRankName"
                                    required
                                    ref="rank_name"
                                    placeholder="Enter rank name"
                                    invalid-feedback="Rank Name must have at least 4 characters and a maximum of 50 characters"
                                    :is-valid="validatorteamRankName"
                                >
                                    <template #prepend-content><CIcon name="cil-pencil"/></template>
                                </CInput>
                            </div>
                        </div>
                        <div class="row row-space">
                            <div class="col-1"></div>
                            <div class="col-2">
                                <label class="label">Rank Icon</label>
                            </div>
                            <div class="col-9">
                                <div class="col-1">
                                    <input type="file" v-on:change="onFileChange" hidden ref="inputImage">
                                    <div class="input-group">
                                        <b-img rounded v-bind="mainProps" @click="chooseImage" center  :src="avatar"
                                        :disabled="isLoadAvatar"></b-img>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row row-space">
                            <div class="col-3">
                                <label class="label">Benefits</label> 
                            </div>
                            <div class="col-9"></div>
                        </div>
                        <div class="row row-space">
                            <div class="col-1"></div>
                            <div class="col-2">
                                <label class="label">Storage</label>
                            </div>
                            <div class="col-9">
                                <CInput
                                    v-model="storageCapacity"
                                    required
                                    type="number"
                                    ref="storage_capacity"
                                    placeholder="Enter storage capacity"
                                    invalid-feedback="Storage must have at least 2 characters and a maximum of 4 characters"
                                    :is-valid="validatorStorageCapacity"
                                >
                                    <template #prepend-content><CIcon name="cil-pencil"/></template>
                                </CInput>
                            </div>
                        </div>
                        <div class="row row-space">
                            <div class="col-1"></div>
                            <div class="col-2">
                                <label class="label">Max Members</label>
                            </div>
                            <div class="col-9">
                                <CInput
                                    v-model="teamRankMemberLimit"
                                    required
                                    type="number"
                                    ref="max_member"
                                    placeholder="Enter max member"
                                    invalid-feedback="Max Members must have at least 2 characters and a maximum of 9 characters"
                                    :is-valid="validatorTeamRankMemberLimit"
                                >
                                    <template #prepend-content><CIcon name="cil-pencil"/></template>
                                </CInput>
                            </div>
                        </div>
                        <div class="row row-space">
                            <div class="col-1"></div>
                            <div class="col-2">
                                <label class="label">Description</label>
                            </div>
                            <div class="col-9">
                                <CTextarea
                                    v-model="teamRankDescription"
                                    ref="Description"
                                    placeholder="Enter description"
                                    invalid-feedback="Description must have at maximum of 225 characters"
                                    :is-valid="validatorTeamRankDescription"
                                    rows="5"
                                >
                                    <template #prepend-content><CIcon name="cil-pencil"/></template>
                                </CTextarea>
                            </div>
                        </div>

                        <div class="row row-space">
                            <div class="col-3">
                                <label class="label">Package Price</label> 
                            </div>
                            <div class="col-9"></div>
                        </div>
                        <div class="row row-space">
                            <div class="col-1"></div>
                            <div>
                            <div class="row row-space">
                                <div class="col-3">
                                    <div class="input-group">
                                        <label class="label">Price</label>
                                    </div>
                                </div>
                                <div class="col-2">
                                    <div class="input-group">
                                        <label class="label">Unit</label>
                                    </div>
                                </div>
                                <div class="col-3">
                                    <div class="input-group">
                                        <label class="label">Duration</label>
                                    </div>
                                </div>
                                <div class="col-2"></div>
                            </div>
                            <div class="row row-space m-t-5 pb-3">
                                <div class="col-3">
                                    <div class="input-group">
                                        <CInput
                                            v-model="teamRankPackagePrices.rankPackagePriceValue"
                                            type="number"
                                            ref="price"
                                            placeholder="Enter price"
                                        />
                                    </div>
                                </div>
                                <div class="col-2">
                                    <div class="input-group">
                                        <select v-model="teamRankPackagePrices.rankPackagePriceUnit" class="form-control" name="score-penal">
                                            <option value="USD">USD</option>
                                            <option value="VND">VND</option>
                                            <option value="JPY">JPY</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-3">
                                    <div class="input-group">
                                        <CInput
                                            v-model="teamRankPackagePrices.rankPackagePriceTime"
                                            type="number"
                                            ref="duration"
                                            placeholder="Enter duration"
                                        />
                                    </div>
                                </div>
                                <div class="col-2">
                                    months
                                </div>
                                <div class="col-1">
                                    <div class="qty">
                                        <span v-on:click="addRankPackage" class="plus bg-dark">+</span>
                                    </div>
                                </div>
                                <br>
                                <label class="err-msg ml-3">{{ msgRankPackageErr }}</label>
                            </div>
                            <hr v-if="sectionTeamRankPackage.length !== 0" class="score-panel-dotted" />
                            <div v-for="(item, index) in sectionTeamRankPackage" v-bind:key="index" class="row row-space">
                                <!-- <div class="col-1"></div> -->
                                <div class="col-3">
                                    <div class="input-group">
                                        <CInput
                                            v-model="item.rankPackagePriceValue"
                                            type="number"
                                            ref="price"
                                            placeholder="Enter price"
                                            invalid-feedback="Duration must have at least 2 and max 9 number characters."
                                            :is-valid="validatorRankPackagePriceValue"
                                        />
                                    </div>
                                </div>
                                <div class="col-2">
                                    <div class="input-group">
                                        <select v-model="item.rankPackagePriceUnit" class="form-control">
                                            <option value="USD">USD</option>
                                            <option value="VND">VND</option>
                                            <option value="JPY">JPY</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-3">
                                    <div class="input-group">
                                        <CInput
                                            v-model="item.rankPackagePriceTime"
                                            type="number"
                                            ref="duration"
                                            placeholder="Enter duration"
                                            invalid-feedback="Duration must have at least 1 and max 2 number characters."
                                            :is-valid="validatorRankPackagePriceTime"
                                        >
                                        </CInput>
                                    </div>
                                </div>
                                <div class="col-2">
                                    <div class="input-group">
                                        months
                                    </div>
                                </div>
                                <div class="col-1">
                                    <div class="qty">
                                        <span v-on:click="removePackagePrice($event, index)" class="minus bg-dark">-</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        </div>

                        <div class="row row-space p-t-15">
                            <div class="col-3"></div>
                            <div class="col-2">
                                <button class="btn-action-sport btn-light" @click="$router.go(-1)" type="button">Back</button>
                            </div>

                            <div class="col-2">
                                <button class="btn-action-sport btn-secondary" @click="resetFields" type="button">Reset</button>
                            </div>

                            <div class="col-2">
                                <button class="btn-action-sport btn-success" type="submit">
                                    <b-spinner v-if="isLoading" small></b-spinner> Create
                                </button>
                            </div>
                            <div class="col-3"></div>
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
    name: "rank-detail",
    data () {
        return {
            msgErrors: "",
            msgSuccess: "",
            title: 'Rank Create',
            dismissCountDown: 0,
            dismissSecs: 4,
            teamRankName: "",
            storageCapacity: "",
            teamRankMemberLimit: "",
            teamRankDescription: "",
            avatar: 1,
            avatar: require("@/assets/resources/no_image.jpg"),
            teamRankPackagePrices: { rankPackagePriceValue: "", rankPackagePriceUnit: "", rankPackagePriceTime: ""},
            sectionTeamRankPackage:[],
            mainProps: { width: 50, height: 50, class: 'm1' },
            msgRankPackageErr: "",
            isLoading: false,
            avatarId: "",
            isLoadAvatar: false,
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
        validatorteamRankName(val) {
            if (val != "") {
                return val.trim().length < 4 ? false : val.trim().length > 50 ? false : true;
            }
        },
        validatorStorageCapacity(val) {
            if (val != "") {
                return val.length < 2 ? false : val.length > 4 ? false : true;
            }
        },
        validatorTeamRankMemberLimit(val) {
            if (val != "") {
                return val.length < 2 ? false : val.length > 9 ? false : true;
            }
        },
        validatorTeamRankDescription(val) {
            if (val != "" || val != "undefined"){
                return val.length > 225 ? false : true;
            }
        },
         validatorRankPackagePriceValue(val) {
            if (val == undefined || val == null || val == "") {
                return false;
            }
                return val.length < 2 ? false : val.length > 9 ? false : true;
        },
        validatorRankPackagePriceTime(val) {
            if (val == undefined || val == null || val == "") {
                return false;
                
            }
                return val.length < 1 ? false : val.length > 2 ? false : true;
        },

        checkFormCreate(e) {
            e.preventDefault();

            if (this.teamRankName === "" || this.teamRankName === null)
                return this.showAlertError("Please input Rank Name!");
            if (!this.validatorteamRankName(this.teamRankName))
                return this.showAlertError("Rank Name is invalid!");
            if (this.storageCapacity === "" || this.storageCapacity === null)
                return this.showAlertError("Please input Storage Capacity!");
            if (!this.validatorStorageCapacity(this.storageCapacity))
                return this.showAlertError("Storage Capacity is invalid!");
            if (this.teamRankMemberLimit === "" || this.teamRankMemberLimit === null)
                return this.showAlertError("Please input Max Member!");
            if (!this.validatorTeamRankMemberLimit(this.teamRankMemberLimit))
                return this.showAlertError("Max member is invalid!");
            if (!this.validatorTeamRankDescription(this.teamRankDescription))
                return this.showAlertError("Description is invalid!");
            if (this.sectionTeamRankPackage.length == 0)
                return this.showAlertError("Package Price is required!");

            this.isLoading = true;
            var token = localStorage.getItem('token'); 
            const config = { 
            headers: { Authorization: `Bearer ${token}` } 
            }; 
            axios
                .post("/admin/rank/update", this.teamRankDTO, config)
                .then(response => {
                    this.isLoading = false;
                    var msg = "";
                    if (response.data.success){
                        msg = "Create new Rank Successfully!";
                        this.showAlertSuccess(msg);
                        this.$router.push({ path: `/rank/listranks` });
                    } else {
                        msg = response.data.message;
                        return this.showAlertError(msg);
                    }
                })
                .catch(e => {
                    this.isLoading = false;
                    return this.showAlertError(e);
                });
        },
        chooseImage(){
            this.$refs['inputImage'].click();
        },
        onFileChange(e) {
            var files = e.target.files || e.dataTransfer.files;
            if (!files.length) return;
            this.avatar = URL.createObjectURL(files[0]);

            var formData = new FormData();
            formData.append("files", files[0]);
            var token = localStorage.getItem('token'); 
            axios.post("/file/uploads", formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                    'Authorization': `Bearer ${token}`
                }
            })
            .then(response => {
                if (response.data.success){
                    this.avatarId = response.data.data[0].uploadId;
                } else {
                    this.isLoadAvatar = false;
                    return this.showAlertError(response.data.message);
                }
            })
            .catch(e => {
                this.isLoadAvatar = false;
                return this.showAlertError(e);
            });
        },
        addRankPackage() { 
                if (this.teamRankPackagePrices.rankPackagePriceValue === "" || this.teamRankPackagePrices.rankPackagePriceUnit === "" ||this.teamRankPackagePrices.rankPackagePriceTime === ""){
                    return this.msgRankPackageErr = "The Price, Unit, Duration fields cannot be empty.";
                }
                if (this.teamRankPackagePrices.rankPackagePriceValue.toString().length > 9 || this.teamRankPackagePrices.rankPackagePriceValue.toString().length < 2){
                    return this.msgRankPackageErr = "Price must have at least 2 and a maximum of 9 number characters";
                }
                if (this.teamRankPackagePrices.rankPackagePriceTime.toString().length > 2 || this.teamRankPackagePrices.rankPackagePriceTime.toString().length < 1){
                    return this.msgRankPackageErr = "Duration must have at least 1 and a maximum of 2 number characters";
                }
                // Check add dupplicate
                var isExisted = false;
                if (this.sectionTeamRankPackage.length != 0){
                    this.sectionTeamRankPackage.forEach(e => {
                        if (this.teamRankPackagePrices.rankPackagePriceValue === e.rankPackagePriceValue.toString()
                        && this.teamRankPackagePrices.rankPackagePriceUnit === e.rankPackagePriceUnit
                        && this.teamRankPackagePrices.rankPackagePriceTime === e.rankPackagePriceTime.toString()){
                            return isExisted = true;
                        }
                    });
                }
                if (isExisted){
                    return this.msgRankPackageErr = "Package Price has already existed. Please add a different package Price.";
                } else {
                    this.msgRankPackageErr = "";
                    this.sectionTeamRankPackage.push(Object.assign({}, this.teamRankPackagePrices));
                    this.teamRankPackagePrices.rankPackagePriceValue = "";
                    this.teamRankPackagePrices.rankPackagePriceUnit = "";
                    this.teamRankPackagePrices.rankPackagePriceTime = "";
                }
            },
            removePackagePrice(e, index){
                this.sectionTeamRankPackage.splice(index, 1);
            },
    },
    computed: {
        teamRankDTO() {
            return {
                teamRankId: this.teamRankId,
                teamRankName: this.teamRankName,
                teamRankAvatar: this.avatarId,
                storageCapacity: this.storageCapacity,
                teamRankMemberLimit: this.teamRankMemberLimit,
                teamRankDescription: this.teamRankDescription,
                teamRankPackagePrices: this.sectionTeamRankPackage
            }
        },
    },
}
</script>

<style>
    @import "../../assets/styles/main.css";
</style>