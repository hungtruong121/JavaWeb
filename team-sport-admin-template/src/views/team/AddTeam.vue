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
                    <form @submit="checkFormCreate" method="post">
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
                                    invalid-feedback="Group names must be between 2 and 100 characters."
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
                                    invalid-feedback="Short names must be between 2 and 5 characters."
                                    :is-valid="validatorShortName"
                                    required
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
                                <label class="label">Sport Name *</label>
                            </div>
                            <div class="col-9">
                                <v-select
                                    :options="sportsName"
                                    v-model="sportNameSelected"
                                >
                                </v-select>
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
                                    <b-spinner v-if="isLoading" small></b-spinner> Add Team
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
    name: "add-team",
    components: {
        "v-select": vSelect
    },
    data() {
        return {
            title: "Add Team",
            msgErrors: "",
            msgSuccess: "",
            dismissCountDown: 0,
            dismissSecs: 4,
            teamName: "",
            shortName: "",
            teamEmail: "",
            sportsName: ['Football', 'Basketball', 'Swimming'],
            listSport: [],
            sportNameSelected: "",
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
            ranksName: ['Basic', 'Silver', 'Gold', 'Diamond'],
            listRank: [],
            rankSelected: "",
            location: "",
            sologan: "",
            description: "",
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
        checkFormCreate(e) {
            e.preventDefault();

            if (!this.validatorTeamName(this.teamName))
                return this.showAlertError("Team Name is invalid!");

            if (!this.validatorShortName(this.shortName))
                return this.showAlertError("Short name is invalid!");

            if (!this.validatorEmail(this.teamEmail))
                return this.showAlertError("Team email is invalid!");

            if (this.teamEmail !== "" && this.teamEmail.replace(/^\s+/, '').replace(/\s+$/, '').length > 100)
                return this.showAlertError("Please input team email no more than 100 characters!");

            if (this.sportNameSelected === "" || this.sportNameSelected === null)
                return this.showAlertError("Please select a Sport!");

            if (this.nationalSelected === "" || this.nationalSelected === null)
                return this.showAlertError("Please select a National!");
            
            if (this.rankSelected === "" || this.rankSelected === null)
                return this.showAlertError("Please select a Rank!");

            if (!this.validatorLocation(this.location))
                return this.showAlertError("Location is invalid!");
                
            if (this.sologan !== "" && this.sologan.replace(/^\s+/, '').replace(/\s+$/, '').length > 100)
                return this.showAlertError("Please input team sologan no more than 100 characters!");

             if (this.description !== "" && this.description.replace(/^\s+/, '').replace(/\s+$/, '').length > 200)
                return this.showAlertError("Please input team sologan no more than 200 characters!");

            this.isLoading = true;

            axios
                .post("/team/admin/update", this.teamDTO)
                .then(response => {
                    this.isLoading = false;
                    var msg = "";
                    if (response.data.success){
                        msg = "Create new Team Successfully!";
                        this.showAlertSuccess(msg);
                        setTimeout(() => {
                            this.$router.push({ path: `/team/listteams` });
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
        teamDTO() {
            var rankId = "";
            var sportId = "";
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
                teamName: this.teamName,
                teamShortName: this.shortName,
                teamMail: this.teamEmail,
                sportName: this.sportNameSelected,
                sportId: sportId,
                teamNational: this.nationalSelected,
                teamRankId: rankId,
                teamAddress: this.location,
                teamSlogan: this.sologan,
                teamDescription: this.description,
            }
        },
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
                } else { // error
                    this.showAlertError(response.data.message);
                }
            })
            .catch(e => {
                this.showAlertError(e);
            });
    },
}
</script>

<style>
    @import "../../assets/styles/main.css";
</style>