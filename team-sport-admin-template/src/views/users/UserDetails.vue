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
        <div class="wrapper wrapper--w960">
            <div class="card card-4">
                <div class="card-body">
                    <h2 class="title">{{ title }}</h2>
                    <form @submit="checkFormUpdate" method="post">
                        <div class="row row-space">
                            <div class="col-7">
                                <div class="personal-info">
                                    <div class="row row-space">
                                        <label class="section-title">Personal Info</label>
                                    </div>
                                    <div class="row row-space">
                                        <div class="col-3">
                                            <div class="input-group">
                                                <label class="label">Status</label>
                                            </div>
                                        </div>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <select v-model="statusSelected" disabled class="form-control">
                                                    <option value="Active">Active</option>
                                                    <option value="Non-Active">Non-Active</option>
                                                    <option value="Locked">Locked</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row row-space">
                                        <div class="col-3">
                                            <label class="label">Email</label>
                                        </div>
                                        <div class="col-9">
                                            <CInput
                                                v-model="email"
                                                disabled
                                            />
                                        </div>
                                    </div>
                                    <div class="row row-space">
                                        <div class="col-3">
                                            <label class="label">Full Name</label>
                                        </div>
                                        <div class="col-9">
                                            <CInput
                                                v-model="fullName"
                                                placeholder="Enter fullname..."
                                                invalid-feedback="Please provide at least 2 characters."
                                                :is-valid="validatorFullName"
                                            />
                                        </div>
                                    </div>
                                    <!-- <div class="row row-space">
                                        <div class="col-3">
                                            <label class="label">UserName</label>
                                        </div>
                                        <div class="col-9">
                                            <CInput
                                                v-model="userName"
                                                disabled
                                                placeholder="Enter username..."
                                                invalid-feedback="Please provide at least 3 characters."
                                                :is-valid="validatorUserName"
                                            />
                                        </div>
                                    </div> -->
                                    <div class="row row-space pb-3">
                                        <div class="col-3">
                                            <label class="label">Genders</label>
                                        </div>
                                        <div class="col-9 pt-1">
                                            <div class="input-group-icon">
                                                <label for="male" class="radio-container m-r-15">
                                                    Male
                                                    <input type="radio" id="male" value="Male" v-model="gender" />
                                                    <span class="checkmark"></span>
                                                </label>
                                                <label for="female" class="radio-container m-r-15">
                                                    Female
                                                    <input type="radio" id="female" value="Female" v-model="gender" />
                                                    <span class="checkmark"></span>
                                                </label>
                                                <label for="other" class="radio-container">
                                                    Other
                                                    <input type="radio" id="other" value="Other" v-model="gender" />
                                                    <span class="checkmark"></span>
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row row-space">
                                        <div class="col-3">
                                            <label class="label">BirthDate</label>
                                        </div>
                                        <div class="col-9">
                                            <CInput
                                                v-model="birthDate"
                                                type="date"
                                            />
                                        </div>
                                    </div>
                                    <div class="row row-space pb-3">
                                        <div class="col-3">
                                            <label class="label">Nationality</label>
                                        </div>
                                        <div class="col-9">
                                            <v-select
                                                :options="nationalities"
                                                v-model="nationality"
                                            >
                                            </v-select>
                                        </div>
                                    </div>
                                    <div class="row row-space">
                                        <div class="col-3">
                                            <label class="label">Phone</label>
                                        </div>
                                        <div class="col-9">
                                            <CInput
                                                v-model="phone"
                                                type="number"
                                                placeholder="Enter phone number..."
                                                invalid-feedback="Please provide less than 15 characters."
                                                :is-valid="validatorPhone"
                                            />
                                        </div>
                                    </div>
                                    <div class="row row-space">
                                        <div class="col-3">
                                            <label class="label">Address</label>
                                        </div>
                                        <div class="col-9">
                                            <CTextarea
                                                v-model="address"
                                                placeholder="Enter address..."
                                                rows="2"
                                            />
                                        </div>
                                    </div>
                                    <div class="row row-space">
                                        <div class="col-3">
                                            <label class="label">Height</label>
                                        </div>
                                        <div class="col-9">
                                            <CInput
                                                v-model="height"
                                                type="number"
                                                placeholder="Enter height..."
                                                append="CM"
                                            />
                                        </div>
                                    </div>
                                    <div class="row row-space">
                                        <div class="col-3">
                                            <label class="label">Weight</label>
                                        </div>
                                        <div class="col-9">
                                            <CInput
                                                v-model="weight"
                                                type="number"
                                                placeholder="Enter weight..."
                                                append="KG"
                                            />
                                        </div>
                                    </div>
                                    <div class="row row-space">
                                        <div class="col-3">
                                            <div class="input-group">
                                                <label class="label">Preferred Hand</label>
                                            </div>
                                        </div>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <select v-model="preferredHandSelected" class="form-control">
                                                    <option value="Left">Left</option>
                                                    <option value="Right">Right</option>
                                                    <option value="Both">Both</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row row-space">
                                        <div class="col-3">
                                            <div class="input-group">
                                                <label class="label">Preferred Foot</label>
                                            </div>
                                        </div>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <select v-model="preferredFootSelected" class="form-control">
                                                    <option value="Left">Left</option>
                                                    <option value="Right">Right</option>
                                                    <option value="Both">Both</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>




                                </div>
                            </div>
                            <div class="col-5">
                                <div class="row row-space pb-4 justify-content-center">
                                    <!-- <div class="col-2"></div>
                                    <div class="col-8 "> -->
                                        <input type="file" v-on:change="onFileChange" hidden ref="inputAvatar">
                                        <div style="font-size: 3rem">
                                            <b-overlay
                                                :show="isLoadAvatar"
                                                rounded
                                                opacity="0.6"
                                                spinner-small
                                                spinner-variant="primary"
                                                class="d-inline-block"
                                                >
                                                <b-avatar
                                                    id="choose-avatar"
                                                    button
                                                    badge-variant="info" 
                                                    style="width: 120px; height: 120px;"
                                                    @click="chooseAvatar"
                                                    center
                                                    :src="avatar"
                                                    :disabled="isLoadAvatar"
                                                >
                                                    <template v-slot:badge><b-icon icon="star-fill"></b-icon></template>
                                                </b-avatar>
                                                <b-tooltip target="choose-avatar">Choose Avatar</b-tooltip>
                                                
                                            </b-overlay>
                                            <!-- <b-img rounded v-bind="mainProps" @click="chooseAvatar" center :src="avatar"></b-img> -->
                                        </div>
                                    <!-- </div>
                                    <div class="col-2"></div> -->
                                </div>

                                <div class="row row-space">
                                    <div class="col-12">
                                        <div class="personal-info">
                                            <div class="row row-space">
                                                <label class="section-title">Team Joined</label>
                                            </div>
                                            <div class="row row-space">
                                                <CCardBody v-if="teams.length == 0">
                                                    <h6><mark>Haven't joined any team!</mark> <CBadge color="info">Info</CBadge></h6>
                                                </CCardBody>
                                                <b-table :items="teams" :fields="fields" v-show="teams.length != 0" :busy="isBusy" class="mt-3">
                                                    <template v-slot:table-busy>
                                                        <div class="text-center text-danger my-2">
                                                            <b-spinner class="align-middle"></b-spinner>
                                                            <strong>Loading...</strong>
                                                        </div>
                                                    </template>
                                                    <template v-slot:cell(team)="row">
                                                        <b-link :href="'/#/team/detail/' + row.item.id">{{ row.item.team }} </b-link>
                                                    </template>
                                                </b-table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                
                            </div>
                        </div>
                        <!-- ****************** -->
                        <div class="row row-space">
                            <div class="col-12">
                                <div class="personal-info">
                                    <div class="row row-space">
                                        <label class="section-title">Sporting Achievements</label>
                                    </div>
                                    <div class="row row-space">
                                        <div class="col-3">
                                            <label class="label">Name Sport</label>
                                        </div>
                                        <div class="col-4">
                                            <label class="label">Achievement</label>
                                        </div>
                                        <div class="col-4">
                                            <label class="label">Time/Season</label>
                                        </div>
                                        <div class="col-1"></div>
                                    </div>
                                    <div class="row row-space pb-4">
                                        <div class="col-3">
                                            <v-select
                                                :options="sportsName"
                                                v-model="sportNameSelected"
                                            >
                                            </v-select>
                                        </div>
                                        <div class="col-4">
                                            <CInput
                                                v-model="achievement"
                                                placeholder="Enter achievement..."
                                            />
                                        </div>
                                        <div class="col-4">
                                            <CInput
                                                v-model="timeSeason"
                                                placeholder="Enter time/season..."
                                            />
                                        </div>
                                        <div class="col-1">
                                            <div class="qty">
                                                <span v-on:click="addSportAchievement" class="plus bg-dark">+</span>
                                            </div>
                                        </div>
                                        <br>
                                        <label class="err-msg ml-3 mt-2">{{ msgSportAchievementErr }}</label>
                                    </div>
                                    <hr v-if="sectionSportAchievements.length !== 0" class="score-panel-dotted">
                                    <div v-for="(item, index) in sectionSportAchievements" v-bind:key="index" class="row row-space pb-2">
                                        <div class="col-3">
                                            <v-select
                                                :options="sportsName"
                                                v-model="item.userAchievementSport"
                                            >
                                            </v-select>
                                        </div>
                                        <div class="col-4">
                                            <CInput
                                                v-model="item.userAchievementName"
                                                placeholder="Enter achievement..."
                                            />
                                        </div>
                                        <div class="col-4">
                                            <CInput
                                                v-model="item.userAchievementTime"
                                                placeholder="Enter time/season..."
                                            />
                                        </div>
                                        <div class="col-1">
                                            <div class="qty">
                                                <span v-on:click="removeSportAchievement($event, index)" class="minus bg-dark">-</span>
                                            </div>
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
                                <button class="btn-action-sport btn-success" type="submit">
                                    <b-spinner v-if="isLoading" small></b-spinner> Update
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
import vSelect from "vue-select";
import "vue-select/dist/vue-select.css";
import moment from 'moment';

export default {
    name: "user-details",
    components: {
        "v-select": vSelect
    },
    data() {
        return {
            title: "User Details",
            msgErrors: "",
            msgSuccess: "",
            dismissCountDown: 0,
            dismissSecs: 4,
            statusSelected: "",
            userId: "",
            fullName: "",
            // userName: "nhannv5",
            gender: "",
            birthDate: "", //yyyy-mm-dd
            nationalities: ['Afghan', 'Albanian', 'Algerian', 'Argentinian', 'Australian', 
                'Austrian', 'Bangladeshi', 'Belgian', 'Bolivian', 'Batswana', 'Brazilian', 'Bulgarian',
                'Cambodian', 'Cameroonian', 'Canadian', 'Chilean', 'Chinese', 'Colombian', 'Costa Rican',
                'Croatian', 'Cuban', 'Czech', 'Danish', 'Dominican', 'Ecuadorian', 'Egyptian', 'Salvadorian',
                'English', 'Estonian', 'Ethiopian', 'Fijian', 'Finnish', 'French', 'German', 'Ghanaian', 'Greek',
                'Guatemalan', 'Haitian', 'Honduran', 'Hungarian', 'Icelandic', 'Indian', 'Indonesian', 'Iranian',
                'Iraqi', 'Irish', 'Israeli', 'Italian', 'Jamaican', 'Japanese', 'Jordanian', 'Kenyan', 'Kuwaiti',
                'Lao', 'Latvian', 'Lebanese', 'Libyan', 'Lithuanian', 'Malagasy', 'Malaysian', 'Malian', 'Maltese',
                'Mexican', 'Mongolian', 'Moroccan', 'Mozambican', 'Namibian', 'Nepalese', 'Dutch', 'New Zealand',
                'Nicaraguan', 'Nigerian', 'Norwegian', 'Pakistani', 'Panamanian', 'Paraguayan', 'Peruvian', 'Philippine',
                'Polish', 'Portuguese', 'Romanian', 'Russian', 'Saudi', 'Scottish', 'Senegalese', 'Serbian', 'Singaporean',
                'Slovak', 'South African', 'Korean', 'Spanish', 'Sri Lankan', 'Sudanese', 'Swedish', 'Swiss', 'Syrian',
                'Taiwanese', 'Tajikistani', 'Thai', 'Tongan', 'Tunisian', 'Turkish', 'Ukrainian', 'Emirati', 'British',
                'American **', 'Uruguayan', 'Venezuelan', 'Vietnamese', 'Welsh', 'Zambian', 'Zimbabwean'
            ],
            nationality: "",
            phone: "",
            email: "",
            reg: /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,24}))$/,
            address: "",
            height: "",
            weight: "",
            preferredHandSelected: "",
            preferredFootSelected: "",
            sportsName: ['Football', 'Basketball', 'Swimming'],
            listSport: [],
            sportNameSelected: "",
            achievement: "",
            timeSeason: "",
            msgSportAchievementErr: "",
            sectionSportAchievements: [],
            avatar: "",
            avatarId: "",
            isLoadAvatar: false,
            isBusy: false,
            teams: [
                { team: 'Dickerson', role: 'MacDonald'},
                { team: 'Dickerson', role: 'MacDonald'},
                { team: 'Dickerson', role: 'MacDonald'},
                { team: 'Dickerson', role: 'MacDonald'},
                { team: 'Dickerson', role: 'MacDonald'},
            ],
            fields: [
                { key: 'team', label: 'Team'},
                { key: 'role', label: 'Role'},
            ],
            isLoading: false
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
        validatorFullName(val) {
            if (val !== ""){
                return val.trim().length < 2 ? false : val.trim().length > 50 ? false : true;
            }
        },
        validatorPhone(val){
            if (val !== ""){
                return val < 0 ? false : val.trim().length > 15 ? false : true;
            }
        },
        // validatorEmail(val){
        //      return (val === "") ? false : (this.reg.test(this.email)) ? true : false;
        // },
        chooseAvatar(){
            this.$refs['inputAvatar'].click();
        },
        onFileChange(e) {
            this.isLoadAvatar = true;

            var files = e.target.files || e.dataTransfer.files;
            if (!files.length)
                return;

            // Call api upload
            var formData = new FormData();
            formData.append("files", files[0]);
            axios.post("/file/uploads", formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            }).then(response => {
                if (response.data.success){
                    var requesDTO = {
                        userId: this.userId,
                        uploadId: response.data.data[0].uploadId
                    }
                    // Call update avatar
                    axios.post("/user/update/avatar", requesDTO)
                    .then(res => {
                        this.isLoadAvatar = false;
                        if (res.data.success){
                            this.avatar = URL.createObjectURL(files[0]);
                            this.avatarId = response.data.data[0].uploadId;
                            //update to local storage 
                            this.updateImageForUserLocalStorage(this.userId, this.avatarId);
                            this.showAlertSuccess("Upload avatar Successfully!");
                        } else {
                            var msg = res.data.message != null ? res.data.message : "Invalid request!";
                            return this.showAlertError(msg);
                        }
                    })
                    .catch(e => {
                        this.isLoadAvatar = false;
                        return this.showAlertError(e.message);
                    });
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
        removeSportAchievement(e, index){
            this.sectionSportAchievements.splice(index, 1);
        },
        addSportAchievement() {
            if (this.sportNameSelected === "" || this.achievement === "" || this.timeSeason === ""){
                return this.msgSportAchievementErr = "Please input all of the field to add!";
            }
            // Check add dupplicate
            var isExisted = false;
            if (this.sectionSportAchievements.length != 0){
                this.sectionSportAchievements.forEach(e => {
                    if (this.sportNameSelected === e.userAchievementSport && 
                        this.achievement === e.userAchievementName && 
                        this.timeSeason === e.userAchievementTime){
                        return isExisted = true;
                    }
                });
            }
            if (isExisted){
                return this.msgSportAchievementErr = "This Achievement already exists, please input new value!";
            } else {
                this.msgSportAchievementErr = "";
                this.sectionSportAchievements.push({ 
                    userAchievementSport: this.sportNameSelected, 
                    userAchievementName: this.achievement,
                    userAchievementTime: this.timeSeason
                });
                this.sportNameSelected = "";
                this.achievement = "";
                this.timeSeason = "";
            }
        },
        checkFormUpdate(e) {
            e.preventDefault();

            if (this.statusSelected === "" || this.statusSelected === null)
                return this.showAlertError("Please choose a Status!");
            if (this.fullName === "" || this.fullName === null)
                return this.showAlertError("Please input Full Name!");
            if (!this.validatorFullName(this.fullName))
                return this.showAlertError("Full Name is invalid!");
            // if (this.userName === "" || this.userName === null){
            //     return this.showAlertError("Please input User Name!");
            // }
            // if (!this.validatorUserName(this.userName)){
            //     return this.showAlertError("User Name is invalid!");
            // }
            if (this.gender === "" || this.gender === null) 
                return this.showAlertError("Gender is required!");
            if (this.birthDate === "" || this.birthDate === null) 
                return this.showAlertError("BirthDate is required!");
            if (this.nationality === "" || this.nationality === null) 
                return this.showAlertError("Nationality is required!");
            if (!this.validatorPhone(this.phone))
                return this.showAlertError("Phone number is invalid!");
            // if (!this.validatorEmail(this.email)){
            //     return this.showAlertError("Email is invalid!");
            // }
            if (this.height !== "" && this.height.toString().length != 3) 
                return this.showAlertError("Height is invalid!");
           
            if (this.weight !== "" && (this.weight.toString().length < 2 || this.weight.toString().length > 3))
                return this.showAlertError("Weight is invalid!");

            this.isLoading = true;
            axios.post("/user/admin/update", this.userDTO)
            .then(response => {
                this.isLoading = false;
                var msg = response.data.message;
                if (response.data.success){
                    this.showAlertSuccess(msg);
                    this.$router.push({ path: `/user/listusers` });
                } else {
                    return this.showAlertError(msg);
                }
            })
            .catch(e => {
                this.isLoading = false;
                return this.showAlertError(e);
            });
        },
        convertEntityToDTO(e){
            var gender = e.userGender !== null ? e.userGender : "0";
            var preferredHand = e.userPreferredHand !== null ? e.userPreferredHand : "";
            var preferredFoot = e.userPreferredFoot !== null ? e.userPreferredFoot : "";

            this.userId = e.userId;
            this.avatarId = e.userAvatar;
            this.statusSelected = e.status == 0 ? 'Locked' : e.status == 1 ? 'Active' : 'Non-Active';
            this.fullName = e.userFullName == null ? "" : e.userFullName;
            this.gender =  gender == "0" ? 'Other' : gender == "1" ? 'Male' : 'Female';
            this.birthDate =  e.userBirthDay == null ? "" : moment(e.userBirthDay,'DD/MM/YYYY').format("YYYY-MM-DD"); //yyyy-mm-dd
            this.nationality = e.userNational == null ? "" : e.userNational;
            this.phone = e.userPhone == null ? "" : e.userPhone;
            this.email = e.userMail == null ? "" : e.userMail;
            this.address = e.userAddress == null ? "" : e.userAddress;
            this.height = e.userHeight == null ? "" : e.userHeight;
            this.weight = e.userWeight == null ? "" : e.userWeight;
            this.preferredHandSelected = preferredHand.toLowerCase() == 'right' ? 'Right' : preferredHand.toLowerCase() == 'left' ? 'Left' : 'Both';
            this.preferredFootSelected = preferredFoot.toLowerCase() == 'right' ? 'Right' : preferredFoot.toLowerCase() == 'left' ? 'Left' : 'Both';

            var teamsJoined = e.teamsJoined != null ? e.teamsJoined : [];
            this.teams = [];
            teamsJoined.forEach(el => {
                this.teams.push(Object.assign({}, { id: el.teamId, team: el.teamName, role: el.teamMemberRole}));
            });
            
            if (e.userAchievements && e.userAchievements != null) {
                e.userAchievements.forEach(el => {
                    this.sectionSportAchievements.push(Object.assign({}, {
                            "userAchievementSport": el.userAchievementSport,
                            "userAchievementName": el.userAchievementName,
                            "userAchievementTime": el.userAchievementTime
                        }
                    ));
                });
            }
            this.isBusy = false;
        },
        updateImageForUserLocalStorage(userId, uploadId){
            let listUser = JSON.parse(localStorage.getItem('usersDetail'));
            if (listUser && listUser != null) {
                listUser.forEach((item) => {
                    item.userId == userId ? item.userAvatar=uploadId : '';
                });  
                localStorage.setItem('listUsers', JSON.stringify(listUser));
            }
        }
    },
    computed: {
        
        listSportAchievements(){
            var listAchievements = [];
            this.sectionSportAchievements.forEach(el => {
                listAchievements.push(Object.assign({}, {
                        "userAchievementSport": el.userAchievementSport,
                        "userAchievementName": el.userAchievementName,
                        "userAchievementTime": el.userAchievementTime
                    }
                ));
            });

            return listAchievements;
        },
        userDTO() {
            return {
                userId: this.userId,
                userRoleId: 1,
                userFullName: this.fullName,
                userMail: this.email,
                userPhone: this.phone,
                userAvatar: this.avatarId,
                userGender: this.gender == "Other" ? '0' : this.gender == "Male" ? '1' : '2',
                userNational: this.nationality,
                userAddress: this.address,
                userWeight: this.weight,
                userHeight: this.height,
                userPreferredHand: this.preferredHandSelected.toLowerCase(),
                userPreferredFoot: this.preferredFootSelected.toLowerCase(),
                status: this.statusSelected == 'Locked' ? 0 : this.statusSelected == 'Active' ? 1 : 2,
                userBirthDay: this.birthDate != null?moment(this.birthDate,'YYYY-MM-DD').format('DD/MM/YYYY'):"",
                userAchievements: this.listSportAchievements //Array
            }
        }
    },
    created() {
        axios.get("/sport/all")
            .then(response => {
                if (response.data.success){
                    this.sportsName = [];
                    var sports = response.data.data;
                    if (sports && sports != null){
                        sports.forEach(e => {
                            this.listSport.push(Object.assign({}, e));
                            this.sportsName.push(e.sportName);
                        });
                    }
                    // this.resetFields();
                    this.isBusy = true;

                    var paramId = this.$route.params.id;
                    let usersDetail = JSON.parse(localStorage.getItem('usersDetail'));
                
                    // Get user in items with id = id
                    var userDetail = {};
                    if (usersDetail && usersDetail != null) {
                        usersDetail.forEach((item) => {
                            userDetail = item.userId == paramId ? item : userDetail;
                        });
                        if (userDetail.userId && userDetail.userId != null){
                           
                            this.convertEntityToDTO(userDetail);
                            
                            // Load avatar
                            var baseURL = axios.defaults.baseURL;
                            var userAvatar = userDetail.userAvatar;
                            this.avatar = baseURL + '/file/reads?uploadId=' + userAvatar;
                        } else {
                            this.isBusy = false;
                            this.showAlertError("UserId " + paramId + " doesn't exist!");
                        }
                    } else {
                        this.isBusy = false;
                        this.$router.push({ path: `/user/listusers`});
                    }
                } else { // error
                    this.showAlertError(response.data.message);
                }
            })
            .catch(e => {
                this.showAlertError(e);
            });
    },
    watch: {
        $route(to, from) {
            this.userId = to.params.id;
        },
    }

}
</script>

<style>
    @import "../../assets/styles/main.css";

.personal-info {
    border: 2px solid rgb(66, 185, 66);
    padding: 15px;
    margin-bottom: 20px;
}

.section-title {
    margin-top: -5px;
    margin-left: 10px;
    font-size: 15px;
    font-weight: bold;
    color: #2c6ed5;
}

</style>
