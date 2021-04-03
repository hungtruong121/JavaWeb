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
                        <div class="row row-space pb-4 justify-content-center">
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
                                    <b-tooltip target="choose-avatar">Choose Avatar Team</b-tooltip>
                                </b-overlay>
                            </div>
                        </div>
                        <div class="row row-space" v-show="showTeamUpdate">
                            <div class="col-1"></div>
                            <div class="col-10">
                                <div class="row row-space">
                                    <div class="col-3">
                                        <div class="input-group">
                                            <label class="label">Team ID</label>
                                        </div>
                                    </div>
                                    <div class="col-9">
                                        <CInput
                                            v-model="teamId"
                                            disabled
                                        />
                                    </div>
                                </div>
                                <div class="row row-space">
                                    <div class="col-3">
                                        <div class="input-group">
                                            <label class="label">Team Name *</label>
                                        </div>
                                    </div>
                                    <div class="col-9">
                                        <CInput
                                            v-model="teamName"
                                            placeholder="Enter team name..."
                                            invalid-feedback="Please provide at least 2 characters."
                                            :is-valid="validatorTeamName"
                                            required
                                        />
                                    </div>
                                </div>
                                <div class="row row-space">
                                    <div class="col-3">
                                        <label class="label">Short Name *</label>
                                    </div>
                                    <div class="col-9">
                                        <CInput
                                            v-model="shortName"
                                            placeholder="Enter short name..."
                                            invalid-feedback="Please provide at least 2 characters."
                                            :is-valid="validatorShortName"
                                            required
                                        />
                                    </div>
                                </div>
                                <div class="row row-space pb-3">
                                    <div class="col-3">
                                        <label class="label">Name Sport</label>
                                    </div>
                                    <div class="col-9">
                                        <v-select
                                            :options="sportsName"
                                            v-model="sportNameSelected"
                                            disabled
                                        >
                                        </v-select>
                                    </div>
                                </div>
                                <div class="row row-space pb-3">
                                    <div class="col-3">
                                        <label class="label">Rank *</label>
                                    </div>
                                    <div class="col-9">
                                        <v-select
                                            :options="ranksName"
                                            v-model="rankSelected"
                                        >
                                        </v-select>
                                    </div>
                                </div>
                                <div class="row row-space">
                                    <div class="col-3">
                                        <label class="label">Slogan</label>
                                    </div>
                                    <div class="col-9">
                                        <CTextarea
                                            v-model="sologan"
                                            placeholder="Enter team sologan..."
                                            rows="2"
                                        />
                                    </div>
                                </div>
                                <div class="row row-space">
                                    <div class="col-3">
                                        <label class="label">Team Email *</label>
                                    </div>
                                    <div class="col-9">
                                        <CInput
                                            v-model="teamEmail"
                                            placeholder="Enter team email..."
                                            invalid-feedback="Please enter valid email!"
                                            :is-valid="validatorEmail"
                                            required
                                        />
                                    </div>
                                </div>
                                <div class="row row-space pb-3">
                                    <div class="col-3">
                                        <label class="label">National *</label>
                                    </div>
                                    <div class="col-9">
                                        <v-select
                                            :options="nationals"
                                            v-model="nationalSelected"
                                        >
                                        </v-select>
                                    </div>
                                </div>
                                <div class="row row-space">
                                    <div class="col-3">
                                        <label class="label">Location *</label>
                                    </div>
                                    <div class="col-9">
                                        <CTextarea
                                            v-model="location"
                                            placeholder="Enter team location..."
                                            rows="2"
                                            invalid-feedback="Please enter team location!"
                                            :is-valid="validatorLocation"
                                            required
                                        />
                                    </div>
                                </div>
                                <div class="row row-space">
                                    <div class="col-3">
                                        <label class="label">Description</label>
                                    </div>
                                    <div class="col-9">
                                        <CTextarea
                                            v-model="description"
                                            placeholder="Enter team description..."
                                            rows="3"
                                        />
                                    </div>
                                </div>
                                <div class="row row-space pb-2">
                                    <div class="col-3">
                                        <label class="label">Payment History</label>
                                    </div>
                                    <div class="col-9">
                                        <b-button :to="'/payment/history/' + teamId" variant="primary" class="mb-2">
                                        View history <b-icon icon="credit-card" aria-hidden="true"></b-icon>
                                        </b-button>
                                    </div>
                                </div>
                                <div class="row row-space">
                                    <div class="col-3">
                                        <label class="label">Date Created</label>
                                    </div>
                                    <div class="col-9">
                                        <CInput
                                            v-model="dateCreated"
                                            type="date"
                                            disabled
                                        />
                                    </div>
                                </div>
                                <div class="row row-space">
                                    <div class="col-3">
                                        <label class="label">Date Updated</label>
                                    </div>
                                    <div class="col-9">
                                        <CInput
                                            v-model="dateUpdated"
                                            type="date"
                                            disabled
                                        />
                                    </div>
                                </div>
                                <div class="row row-space">
                                    <div class="col-3">
                                        <label class="label">Team Member</label>
                                    </div>
                                    <div class="col-9">
                                        <CInput
                                            v-model="teamMember"
                                            disabled
                                        />
                                    </div>
                                </div>
                            </div>
                            <div class="col-1"></div>
                        </div>

                        <!-- ****************** -->

                        <div class="row row-space">
                            <div class="col-12">
                                <transition name="fade">
                                    <CCard>
                                        <CCardHeader>
                                            <CRow style="margin-bottom: -25px;">
                                                <div class="col-6">
                                                    <b-form-group
                                                    label-align-sm="right"
                                                    label-size="sm"
                                                    label-for="filterInput"
                                                    >
                                                        <b-input-group size="sm">
                                                        <b-form-input
                                                            v-model="filter"
                                                            type="search"
                                                            id="filterInput"
                                                            placeholder="Type to Search Member"
                                                        ></b-form-input>
                                                        <b-input-group-append>
                                                            <b-button :disabled="!filter" @click="filter = ''">Clear</b-button>
                                                        </b-input-group-append>
                                                        </b-input-group>
                                                    </b-form-group>
                                                </div>
                                                <div class="col-6">
                                                    <div class="card-header-actions">
                                                        <CLink
                                                        class="card-header-action btn-minimize"
                                                        @click="formCollapsed=!formCollapsed"
                                                        >
                                                        <CIcon :name="`cil-chevron-${formCollapsed ? 'bottom' : 'top'}`"/>
                                                        </CLink>
                                                        <CLink href="#" class="card-header-action" v-if="showTeamUpdate" v-on:click="zoomInMember">
                                                            <b-icon icon="arrows-fullscreen"></b-icon>
                                                        </CLink>
                                                        <CLink href="#" class="card-header-action" v-if="!showTeamUpdate" v-on:click="zoomOutMember">
                                                            <b-icon icon="fullscreen-exit"></b-icon>
                                                        </CLink>
                                                    </div>
                                                </div>
                                            </CRow>
                                        </CCardHeader>
                                        <CCollapse :show="formCollapsed">
                                        <CCardBody>
                                            <b-table
                                                responsive
                                                fixed 
                                                outlined
                                                hover
                                                small
                                                :busy="isBusy"
                                                :items="items"
                                                :current-page="currentPage"
                                                :fields="fields"
                                                :per-page="perPage"
                                                :filter="filter"
                                                @filtered="onFiltered"
                                            >
                                                <template v-slot:table-busy>
                                                    <div class="text-center text-danger my-2">
                                                        <b-spinner class="align-middle"></b-spinner>
                                                        <strong>Loading...</strong>
                                                    </div>
                                                </template>

                                                <template v-slot:cell(remove)="row">
                                                    <b-button size="sm" v-on:click="removeMember($event, row.item.userId)" variant="danger" class="mb-2">
                                                        <b-icon icon="trash-fill" aria-hidden="true"></b-icon>
                                                        <b-spinner v-if="isRemoveLoading && indexClicked === row.item.userId" small></b-spinner>
                                                    </b-button>
                                                </template>

                                                <template v-slot:cell(changeRole)="row">
                                                    <b-button v-b-tooltip.hover.v-danger title="Change Role" size="sm" v-on:click="changeRole($event, row.item.userId, row.item.role)" variant="info" class="mb-2">
                                                        <b-icon icon="gear" aria-hidden="true"></b-icon>
                                                        <b-spinner v-if="isChangeRole && indexClicked === row.item.userId" small></b-spinner>
                                                    </b-button>
                                                </template>

                                                <template v-slot:cell(index)="row">
                                                    {{ row.index + 1 }}
                                                </template>

                                                <template v-slot:table-colgroup="scope">
                                                    <col
                                                    v-for="field in scope.fields"
                                                    :key="field.key"
                                                    :style="{ width: field.key === 'remove' ? '40px' :
                                                                    field.key === 'changeRole' ? '40px' : 
                                                                    field.key === 'index' ? '60px' :
                                                                    field.key === 'fullName' ? '150px' : 
                                                                    field.key === 'email' ? '200px' : 
                                                                    field.key === 'dateJoined' ? '100px' : '100px'
                                                            }"
                                                    >
                                                </template>

                                                <template v-slot:cell(role)="row">
                                                    <div v-if="row.item.role == 'TEAM_ADMIN'">
                                                        <b-badge pill variant="primary">Admin</b-badge>
                                                    </div>
                                                    <div v-if="row.item.role == 'TEAM_MEMBER'">
                                                        <b-badge pill variant="success">Member</b-badge>
                                                    </div>
                                                </template>

                                                <template v-slot:cell(userIsActive)="row">
                                                    <div v-if="row.item.userIsActive == 0">
                                                        <b-badge pill variant="danger">Locked</b-badge>
                                                    </div>
                                                    <div v-if="row.item.userIsActive == 1">
                                                        <b-badge pill variant="success">Active</b-badge>
                                                    </div>
                                                    <div v-if="row.item.userIsActive == 2">
                                                        <b-badge pill variant="warning">Non-Active</b-badge>
                                                    </div>
                                                </template>

                                            </b-table>
                                            <b-row>
                                                <b-col sm="5" md="6" class="my-1">
                                                    <b-form-group
                                                        label="Per page"
                                                        label-cols-sm="6"
                                                        label-cols-md="4"
                                                        label-cols-lg="3"
                                                        label-align-sm="right"
                                                        label-size="sm"
                                                        label-for="perPageSelect"
                                                        class="mb-0"
                                                    >
                                                        <b-form-select
                                                            v-model="perPage"
                                                            id="perPageSelect"
                                                            size="sm"
                                                            :options="pageOptions"
                                                        ></b-form-select>
                                                    </b-form-group>
                                                </b-col>
                                                <b-col sm="7" md="6" class="my-1">
                                                <b-pagination
                                                    v-model="currentPage"
                                                    :total-rows="totalRows"
                                                    :per-page="perPage"
                                                    align="fill"
                                                    size="sm"
                                                    class="my-0"
                                                ></b-pagination>
                                                </b-col>
                                            </b-row>
                                        </CCardBody>
                                        </CCollapse>
                                    </CCard>
                                    </transition>
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
                                <button class="btn-action-sport btn-success" v-on:click="zoomOutMember()" type="submit">
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

export default {
    name: "team-details",
    components: {
        "v-select": vSelect
    },
    data() {
        return {
            title: "Team Update",
            msgErrors: "",
            msgSuccess: "",
            dismissCountDown: 0,
            dismissSecs: 4,
            statusSelected: "",
            formCollapsed: true,
            avatar: "",
            avatarId: "",
            isLoadAvatar: false,
            teamId: "",
            teamName: "",
            shortName: "",
            sportsName: ['Football', 'Basketball', 'Swimming'],
            listSport: [],
            sportNameSelected: "",
            ranksName: ['Basic', 'Silver', 'Gold', 'Diamond'],
            listRank: [],
            rankSelected: "",
            sologan: "",
            teamEmail: "",
            nationals: ['Afghanistan', 'Albania', 'Algeria', 'Argentina', 'Australia', 
                'Austria', 'Bangladesh', 'Belgium', 'Bolivia', 'Botswana', 'Brazil', 'Bulgaria',
                'Cambodia', 'Cameroon', 'Canada', 'Chile', 'China', 'Colombia *', 'Costa Rica',
                'Croatia', 'Cuba', 'Czech Republic', 'Denmark', 'Dominican Republic', 'Ecuador', 'Egypt', 'El Salvador',
                'England', 'Estonia', 'Ethiopia', 'Fiji', 'Finland', 'France', 'Germany', 'Ghana', 'Greece',
                'Guatemala', 'Haiti', 'Honduras', 'Hungary', 'Iceland', 'India', 'Indonesia', 'Iran',
                'Iraq', 'Ireland', 'Israel', 'Italy', 'Jamaica', 'Japan', 'Jordan', 'Kenya', 'Kuwait',
                'Laos', 'Latvia', 'Lebanon', 'Libya', 'Lithuania', 'Madagascar', 'Malaysia', 'Mali', 'Malta',
                'Mexico', 'Mongolia', 'Morocco', 'Mozambique', 'Namibia', 'Nepal', 'Netherlands', 'New Zealand',
                'Nicaragua', 'Nigeria', 'Norway', 'Pakistan', 'Panama', 'Paraguay', 'Peru', 'Philippines',
                'Poland', 'Portugal', 'Romania', 'Russia', 'Saudi Arabia', 'Scotland', 'Senegal', 'Serbia', 'Singapore',
                'Slovakia', 'South Africa', 'South Korea', 'Spain', 'Sri Lanka', 'Sudan', 'Sweden', 'Switzerland', 'Syria',
                'Taiwan', 'Tajikistan', 'Thailand', 'Tonga', 'Tunisia', 'Turkey', 'Ukraine', 'United Arab Emirates', '(The) United Kingdom',
                '(The) United States', 'Uruguay', 'Venezuela', 'Vietnam', 'Wales', 'Zambia', 'Zimbabwe'
            ],
            nationalSelected: "",
            location: "",
            description: "",
            dateCreated: "",
            dateUpdated: "",
            teamMember: "",
            isLoading: false,
            isRemoveLoading: false,
            isChangeRole: false,
            indexClicked: -1,
            reg: /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,24}))$/,
            fields: [
                { key: 'changeRole', label: '', class: 'text-center'},
                { key: 'remove', label: '', class: 'text-center'},
                { key: 'index', label: 'No.', class: 'text-center'},
                { key: 'fullName', label: 'Full Name '},
                { key: 'email', label: 'Email'},
                { key: 'position', label: 'Position'},
                { key: 'role', label: 'Role', class: 'text-center'},
                { key: 'userIsActive', label: 'Member Status', class: 'text-center'},
                { key: 'dateJoined', label: 'Date Joined'},
            ],
            items: [],
            currentPage: 1,
            totalRows: 0,
            perPage: 50,
            pageOptions: [50, 100],
            filter: null,
            ilterOn: [],
            isBusy: true,
            showTeamUpdate: true,
            waitTeamDTO: null
        }
    },
    methods: {
        onFiltered(filteredItems) {
            this.totalRows = filteredItems.length
            // this.currentPage = 1
        },
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
        validatorTeamName(val) {
            if (val !== ""){
                return val.trim().length < 2 ? false : val.trim().length > 100 ? false : true;
            }
        },
        validatorShortName(val) {
            if (val !== ""){
                return val.trim().length < 2 ? false : val.trim().length > 5 ? false : true;
            }
        },
        validatorEmail(val){
            if (val !== ""){
                return (val === "") ? false : (this.reg.test(this.teamEmail)) ? true : false;
            }
        },
        validatorLocation(val){
            if (val !== ""){
                return  (val === "") ? false : val.trim().length > 255 ? false : true;
            }
        },
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
                        teamId: this.teamId,
                        uploadId: response.data.data[0].uploadId
                    }
                    // Call update avatar
                    axios.post("/team/updateavatar", requesDTO)
                    .then(res => {
                        this.isLoadAvatar = false;
                        if (res.data.success){
                            this.avatar = URL.createObjectURL(files[0]);
                            this.avatarId = response.data.data[0].uploadId;
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
        zoomInMember(){
            this.showTeamUpdate = false;
            this.perPage = this.items.length;
        },
        zoomOutMember(){
            this.showTeamUpdate = true;
            this.perPage = 50;
        },
        getListMembers(){
            this.waitTeamDTO = setInterval(() => {
                if (this.teamId != "") {
                    clearInterval(this.waitTeamDTO);
                    // Get list members in team
                    axios.get("/team/admin/members?teamId=" + this.teamId)
                    .then(response => {
                        if (response.data.success){
                            var listMembers = response.data.data;
                            listMembers.forEach(e => {
                                var memberDTO = { 
                                    userId: e.userId,
                                    fullName: e.userFullName,
                                    email: e.userMail,
                                    position: e.position,
                                    role: e.teamMemberRole,
                                    userIsActive: e.userIsActive,
                                    dateJoined: this.formatDate(e.memberSince, "/"),
                                    _rowVariant: e.userIsActive == 0 ? 'danger' : '' // User was Locked
                                }

                                this.items.push(Object.assign({}, memberDTO));
                            });
                            this.totalRows = this.items.length;
                            this.isBusy = !this.isBusy;
                        } else { // error
                            this.isBusy = !this.isBusy;
                            this.showAlertError(response.data.message);
                        }
                    })
                    .catch(e => {
                        this.isBusy = !this.isBusy;
                        this.showAlertError(e);
                    });
                }
            }, 500);
            
        },
        removeMember(e, id){
            this.$bvModal.msgBoxConfirm('Are you sure to Kick this member out of team?', {
                title: 'Please Confirm',
                size: 'sm',
                buttonSize: 'sm',
                okVariant: 'danger',
                okTitle: 'YES',
                cancelTitle: 'NO',
                footerClass: 'p-2',
                hideHeaderClose: false,
                centered: true
            }).then(value => {
                if (value === true){
                    this.isRemoveLoading = true;
                    this.indexClicked = id;
                    var indexDelete = -1;
                    this.items.forEach((item, i) => {
                        indexDelete = item.userId === id ? i : indexDelete;
                    });
                    if (indexDelete != -1){
                        // Call api kick member
                        axios.post("/team/admin/kick", {userId: id, teamId: this.teamId})
                        .then(response => {
                            this.isRemoveLoading = false;
                            this.indexClicked = -1;
                            if (response.data.success){
                                // Remove row
                                this.items.splice(indexDelete, 1);
                                this.teamMember = this.teamMember - 1;
                                this.showAlertSuccess("Kick member Successfully!");
                            } else {
                                this.showAlertError(response.data.message);
                            }
                        })
                        .catch(e => {
                            this.isRemoveLoading = false;
                            this.indexClicked = -1;
                            this.showAlertError(e);
                        });
                    } else {
                        this.isRemoveLoading = false;
                        this.indexClicked = -1;
                        this.showAlertError("This Member not exists in team!");
                    }
                }
            })
            .catch(err => {
                // An error occurred
                this.showAlertError(err);
            })
        },
        changeRole(e, id, role) {
            const h = this.$createElement;
            var msg = (role == 'TEAM_ADMIN') ? h('p', { class: ['text-center'] }, [ 'Are you sure to change Role this member to ',
             h('strong', 'Member'), '? ',]) : h('p', { class: ['text-center'] }, [ 'Are you sure to change Role this member to ',
             h('strong', 'Admin'), '? ',]);

            this.$bvModal.msgBoxConfirm(msg, {
                title: 'Please Confirm',
                size: 'sm',
                buttonSize: 'sm',
                okVariant: 'danger',
                okTitle: 'YES',
                cancelTitle: 'NO',
                footerClass: 'p-2',
                hideHeaderClose: false,
                centered: true
            }).then(value => {
                if (value === true){
                    this.isChangeRole = true;
                    this.indexClicked = id;
                    var indexDelete = -1;
                    this.items.forEach((item, i) => {
                        indexDelete = item.userId === id ? i : indexDelete;
                    });
                    if (indexDelete != -1){
                        // Call api change Role
                        var roleChange = role == 'TEAM_ADMIN' ? 'TEAM_MEMBER' : 'TEAM_ADMIN';
                        axios.post("/team/admin/role/change", {teamMemberRole: roleChange, teamId: this.teamId, userId: id})
                        .then(response => {
                            this.isChangeRole = false;
                            this.indexClicked = -1;
                            if (response.data.success){
                                this.items.forEach(el => {
                                    if (el.userId === id) {
                                        el.role = roleChange;
                                    }
                                });
                                var roleToChange = role == 'TEAM_ADMIN' ? 'Member' : 'Admin';
                                this.showAlertSuccess("Change to role " + roleToChange + " Successfully!");
                            } else {
                                this.showAlertError(response.data.message);
                            }
                        })
                        .catch(e => {
                            this.isChangeRole = false;
                            this.indexClicked = -1;
                            this.showAlertError(e);
                        });
                    } else {
                        this.isChangeRole = false;
                        this.indexClicked = -1;
                        this.showAlertError("This Member not exists in team!");
                    }
                }
            })
            .catch(err => {
                // An error occurred
                this.showAlertError(err);
            })
        },
        checkFormUpdate(e) {
            this.zoomOutMember();
            e.preventDefault();

            if (!this.validatorTeamName(this.teamName))
                return this.showAlertError("Team Name is invalid!");

            if (!this.validatorShortName(this.shortName))
                return this.showAlertError("Short name is invalid!");

            if (this.sportNameSelected === "" || this.sportNameSelected === null)
                return this.showAlertError("Please select a Sport!");

            if (this.rankSelected === "" || this.rankSelected === null)
                return this.showAlertError("Please select a Rank!");

            if (this.sologan !== "" && this.sologan.replace(/^\s+/, '').replace(/\s+$/, '').length > 100)
                return this.showAlertError("Please input team sologan no more than 100 characters!");

            if (!this.validatorEmail(this.teamEmail))
                return this.showAlertError("Team email is invalid!");

            if (this.teamEmail !== "" && this.teamEmail.replace(/^\s+/, '').replace(/\s+$/, '').length > 100)
                return this.showAlertError("Please input team email no more than 100 characters!");

            if (this.nationalSelected === "" || this.nationalSelected === null)
                return this.showAlertError("Please select a National!");
            
            if (!this.validatorLocation(this.location))
                return this.showAlertError("Location is invalid!");
                
            if (this.description !== "" && this.description.replace(/^\s+/, '').replace(/\s+$/, '').length > 200)
                return this.showAlertError("Please input team sologan no more than 200 characters!");

            this.isLoading = true;

            axios.post("/team/admin/update", this.teamDTO)
            .then(response => {
                this.isLoading = false;
                var msg = response.data.message;
                if (response.data.success){
                    this.showAlertSuccess(msg);
                    setTimeout(() => {
                        this.$router.push({ path: `/team/listteams` });
                    }, 2000);
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
            var rankName = "";
            this.listRank.forEach(rank => {
                if (e.teamRankId == rank.teamRankId){
                    rankName = rank.teamRankName;
                }   
            });

            this.teamId = e.teamId;
            this.avatarId = e.teamAvatar;
            this.teamName = e.teamName,
            this.shortName = e.teamShortName == null ? "" : e.teamShortName,
            this.sportNameSelected = e.sportName == null ? "" : e.sportName,
            this.rankSelected = rankName,
            this.sologan = e.teamSlogan,
            this.teamEmail = e.teamMail,
            this.nationalSelected = e.teamNational,
            this.location = e.teamAddress,
            this.description = e.teamDescription,
            this.dateCreated = this.formatDate(e.createdDate, "-"),
            this.dateUpdated = this.formatDate(e.updatedDate, "-"),
            this.teamMember = e.totalMember
        },
        formatDate(date, pattern) {
            var d = new Date(date),
                month = '' + (d.getMonth() + 1),
                day = '' + d.getDate(),
                year = d.getFullYear();

            if (month.length < 2) month = '0' + month;
            if (day.length < 2) day = '0' + day;

            return [year, month, day].join(pattern);
        },
    },
    mounted() {
      // Set the initial number of members
      this.totalRows = this.items.length;
      this.getListMembers();
    },
    computed: {
        teamDTO() {
            var sportId = "";
            var rankId = "";
            this.listSport.forEach(sport => {
                if (this.sportNameSelected == sport.sportName){
                    sportId = sport.sportId;
                }   
            });
            this.listRank.forEach(rank => {
                if (this.rankSelected == rank.teamRankName){
                    rankId = rank.teamRankId;
                }   
            });

            return {
                teamId: this.teamId,
                teamAvatar: this.avatarId,
                teamName: this.teamName,
                teamShortName: this.shortName,
                sportName: this.sportNameSelected,
                sportId: sportId, // ko thay doi
                teamMail: this.teamEmail,
                teamNational: this.nationalSelected,
                teamRankId: rankId,
                teamAddress: this.location,
                teamSlogan: this.sologan,
                teamDescription: this.description,
            }
        }
    },
    created() {
        // Get list Sport & list Rank
        axios.get("/team/admin/sportsandranks")
            .then(response => {
                if (response.data.success){
                    this.sportsName = [];
                    this.ranksName = [];
                    var sports = response.data.data.listSport;
                    var ranks = response.data.data.listRank;

                    if (sports && sports != null){
                        sports.forEach(e => {
                            this.listSport.push(Object.assign({}, e));
                            this.sportsName.push(e.sportName);
                        });
                    }
                    if (ranks && ranks != null){
                        ranks.forEach(e => {
                            this.listRank.push(Object.assign({}, e));
                            this.ranksName.push(e.teamRankName);
                        });
                    }

                    var paramId = this.$route.params.id;
                    let teamsDetail = JSON.parse(localStorage.getItem('teamsDetail'));

                    // Get team in items with id = teamId
                    var teamDetail = {};
                    if (teamsDetail && teamsDetail != null) {
                        teamsDetail.forEach((item) => {
                            teamDetail = item.teamId == paramId ? item : teamDetail;
                        });

                        if (teamDetail.teamId && teamDetail.teamId != null){
                            this.convertEntityToDTO(teamDetail);
                            
                            // Load team avatar
                            var baseURL = axios.defaults.baseURL;
                            var teamAvatar = teamDetail.teamAvatar == null ? 1 : teamDetail.teamAvatar;
                            this.avatar = baseURL + '/file/reads?uploadId=' + teamAvatar;
                        } else {
                            // this.isBusy = false;
                            this.showAlertError("TeamID " + paramId + " doesn't exist!");
                        }
                    } else {
                        this.$router.push({ path: `/team/listteams`});
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
            this.teamId = to.params.id;
        },
    }

}
</script>

<style>
    @import "../../assets/styles/main.css";
</style>