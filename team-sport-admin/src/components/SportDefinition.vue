<template>
    <div class="page-wrapper bg-gra-02 p-t-130 p-b-100 font-poppins">
        <b-alert
            v-if="msgErrors !== ''"
            :show="dismissCountDown"
            class="position-fixed mt-2 top-right sticky-top"
            dismissible
            variant="danger"
            fade
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged"
        >
            {{ msgErrors }}
        </b-alert>
        <b-alert
            v-if="msgSuccess !== ''"
            :show="dismissCountDown"
            class="position-fixed mt-2 top-right sticky-top"
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
                            <div class="col-4">
                                <div class="input-group">
                                    <label class="label">Name sport *</label>
                                </div>
                            </div>
                            <div class="col-8">
                                <div class="input-group">
                                    <input class="input--style-4" type="text" ref="sport_name" v-model="sportName" placeholder="Enter name sport" />
                                </div>
                            </div>
                        </div>
                        <div class="row row-space">
                            <div class="col-4">
                                <div class="input-group">
                                    <label class="label">Postitions/Levels *</label>
                                </div>
                            </div>
                            <div class="col-8">
                                 <vue-tags-input placeholder="Ex: Goalkeeper,Defending,Central,Striker..."
                                    v-model="tag"
                                    :tags="tags"
                                    :autocomplete-items="filteredItems"
                                    @tags-changed="newTags => tags = newTags"
                                 />
                              <!-- <input class="input--style-4" type="text" placeholder="Ex: Goalkeeper,Defending,Central,Striker..." name="positions_levels" /> -->
                            </div>
                        </div>
                        <div class="row row-space">
                            <div class="col-4">
                                <div class="input-group">
                                    <label class="label">Competition Format *</label>
                                </div>
                            </div>
                            <div class="col-3">
                                <div class="input-group">
                                    <label for="team" class="customcheck">
                                        Team
                                        <input type="checkbox" id="team" value="Team" v-model="checkedCompetition"/>
                                        <span class="checkmark-cb"></span>
                                    </label>
                                </div>
                            </div>
                            <div class="col-3">
                                <div class="input-group">
                                    <label for="single" class="customcheck">
                                        Single
                                        <input type="checkbox" id="single" value="Single" v-model="checkedCompetition" />
                                        <span class="checkmark-cb"></span>
                                    </label>
                                </div>
                            </div>
                            <div class="col-2"></div>
                        </div>
                        <div class="row row-space">
                            <div class="col-3">
                                <div class="input-group">
                                    <label class="label">Match types *</label>
                                </div>
                            </div>
                            <div class="col-3">
                                <div class="input-group">
                                    <label class="label"><b>Fight</b></label>
                                </div>
                            </div>
                            <div class="col-6">
                                <div class="input-group">
                                    <label class="label"><b>Compete</b></label>
                                </div>
                            </div>
                        </div>
                        <div class="row row-space">
                            <div class="col-3"></div>
                            <div class="col-3">
                                <div class="input-group">
                                    <label for="fight" class="radio-container m-r-45">
                                        Score
                                        <input type="radio" id="fight" @change="checkFightScore($event)" v-model="isCheckedFightScore" value="Score" />
                                        <span class="checkmark"></span>
                                    </label>
                                </div>
                            </div>
                            <div class="col-3">
                                <div class="input-group">
                                    <label for="compete" class="radio-container m-r-45">
                                        Distance/Height
                                        <input type="radio" id="compete" @change="checkCompete($event)" v-model="checkedCompete" value="Distance/Height" />
                                        <span class="checkmark"></span>
                                    </label>
                                </div>
                            </div>
                            <div class="col-3">
                                <div class="input-group">
                                    <label for="competeTime" class="radio-container m-r-45">
                                        Time
                                        <input type="radio" id="competeTime" @change="checkCompete($event)" v-model="checkedCompete" value="Time" />
                                        <span class="checkmark"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="row row-space">
                            <div class="col-3"></div>
                            <div class="col-3">
                                <div class="input-group">
                                    <input class="input--style-4" type="text" @keyup="unCheckedFight" v-model="fightOther" placeholder="Other..." />
                                </div>
                            </div>
                            <div class="col-3">
                                <div class="input-group">
                                    <label for="competeScore" class="radio-container m-r-45">
                                        Score
                                        <input type="radio" id="competeScore" @change="checkCompete($event)" v-model="checkedCompete" value="Score" />
                                        <span class="checkmark"></span>
                                    </label>
                                </div>
                            </div>
                            <div class="col-3">
                                <div class="input-group">
                                    <input class="input--style-4" type="text" @keyup="unCheckedCompete" v-model="competeOther" placeholder="Other..." />
                                </div>
                            </div>
                        </div>
                        <div class="row row-space">
                            <div class="col-4">
                                <div class="input-group">
                                    <label class="label">Half/Set/Round</label>
                                </div>
                            </div>
                            <div class="col-4">
                                <div class="input-group">
                                    <select v-model="halfSetRoundSelect" class="form-control" :disabled="listHalfSetRound.length != 0">
                                        <option disabled="disabled" selected="selected">Choose types</option>
                                        <option value="Half">Half</option>
                                        <option value="Set">Set</option>
                                        <option value="Round">Round</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-4"></div>
                        </div>
                        <div class="hafl-set-round-area">
                            <div class="row row-space">
                                <div class="col-3"></div>
                                <div class="col-3">
                                    <div class="input-group">
                                        <label class="label">Name</label>
                                    </div>
                                </div>
                                <div class="col-3">
                                    <div class="input-group">
                                        <label class="label">Time(Minutes)</label>
                                    </div>
                                </div>
                                <div class="col-3"></div>
                            </div>
                            <div class="row row-space m-t-5 pb-3">
                                <div class="col-3"></div>
                                <div class="col-3">
                                    <div class="input-group">
                                        <input class="input--style-4" v-model="halfSetRoundForm.sportRoundName" type="text" placeholder="Name..." />
                                    </div>
                                </div>
                                <div class="col-3">
                                    <div class="input-group">
                                        <input class="input--style-4" v-model="halfSetRoundForm.sportRoundTime" type="number" placeholder="Time..." />
                                    </div>
                                </div>
                                <div class="col-3">
                                    <div class="qty">
                                        <span v-on:click="addHalfSetRound" class="plus bg-dark">+</span>
                                    </div>
                                </div>
                                <br>
                                <label class="err-msg ml-3 m-t-5">{{ msgHalfSetRoundErr }}</label>
                            </div>
                            <hr v-if="sectionHalfSetRound.length != 0" class="dotted" />
                            <div v-for="(item, index) in sectionHalfSetRound" v-bind:key="index" class="row row-space">
                                <div class="col-3"></div>
                                <div class="col-3">
                                    <div class="input-group">
                                        <input class="input--style-4" v-model="item.sportRoundName" type="text" placeholder="Name..." />
                                    </div>
                                </div>
                                <div class="col-3">
                                    <div class="input-group">
                                        <input class="input--style-4" v-model="item.sportRoundTime" type="number" placeholder="Time..." />
                                    </div>
                                </div>
                                <div class="col-3">
                                    <div class="qty">
                                        <span v-on:click="removeHalfSetRound($event, index)" class="minus bg-dark">-</span>
                                    </div>
                                </div>
                            </div>
                            <hr />
                            <div class="row row-space pb-2">
                                <div class="col-3"></div>
                                <div class="col-3">
                                    <button type="button" v-on:click="showExtraTime" class="btn-secondary btn-sport">Extra-time</button>
                                </div>
                                <div class="col-6"></div>
                            </div>
                            <div v-if="this.sectionHalfSetRound.length != 0" v-show="isShowExtraTime">
                                <div class="row row-space">
                                    <div class="col-3"></div>
                                    <div class="col-3">
                                        <div class="input-group">
                                            <label class="label">Name</label>
                                        </div>
                                    </div>
                                    <div class="col-3">
                                        <div class="input-group">
                                            <label class="label">Time(Minutes)</label>
                                        </div>
                                    </div>
                                    <div class="col-3"></div>
                                </div>
                                <div class="row row-space m-t-5 pb-3">
                                    <div class="col-3"></div>
                                    <div class="col-3">
                                        <div class="input-group">
                                            <input class="input--style-4" v-model="halfSetRoundExtraForm.sportRoundName" type="text" placeholder="Name..." />
                                        </div>
                                    </div>
                                    <div class="col-3">
                                        <div class="input-group">
                                            <input class="input--style-4" v-model="halfSetRoundExtraForm.sportRoundTime" type="number" placeholder="Time..." />
                                        </div>
                                    </div>
                                    <div class="col-3">
                                        <div class="qty">
                                            <span v-on:click="addHalfSetRoundExtra" class="plus bg-dark" id="extra-minus">+</span>
                                        </div>
                                    </div>
                                    <br>
                                    <label class="err-msg ml-3 m-t-5">{{ msgHalfSetRoundExtraErr }}</label>
                                </div>
                                <hr v-if="sectionHalfSetRoundExtra.length != 0" class="dotted" />
                                <div v-for="(item, index) in sectionHalfSetRoundExtra" v-bind:key="index" class="row row-space">
                                    <div class="col-3"></div>
                                    <div class="col-3">
                                        <div class="input-group">
                                            <input class="input--style-4" v-model="item.sportRoundName" type="text" placeholder="Name..." />
                                        </div>
                                    </div>
                                    <div class="col-3">
                                        <div class="input-group">
                                            <input class="input--style-4" v-model="item.sportRoundTime" type="number" placeholder="Time..." />
                                        </div>
                                    </div>
                                    <div class="col-3">
                                        <div class="qty">
                                            <span v-on:click="removeHalfSetRoundExtra($event, index)" class="minus bg-dark" id="extra-minus">-</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row row-space">
                            <div class="col-3">
                                <div class="input-group">
                                    <label class="label">Genders *</label>
                                </div>
                            </div>
                            <div class="col-9 pt-1">
                                <div class="input-group-icon">
                                    <label for="male" class="radio-container m-r-45">
                                        Male
                                        <input type="radio" id="male" value="Male" v-model="gender" />
                                        <span class="checkmark"></span>
                                    </label>
                                    <label for="female" class="radio-container m-r-45">
                                        Female
                                        <input type="radio" id="female" value="Female" v-model="gender" />
                                        <span class="checkmark"></span>
                                    </label>
                                    <label for="both" class="radio-container">
                                        Both
                                        <input type="radio" id="both" value="Both" v-model="gender" />
                                        <span class="checkmark"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="row row-space">
                            <div class="col-3">
                                <div class="input-group">
                                    <label class="label">Age *</label>
                                </div>
                            </div>
                            <div class="col-9">
                                <div class="input-group mb-1">
                                    <label class="label m-r-15">From</label>
                                    <input class="input--style-4 col-2 m-r-45" @keyup="ageValidate" v-model.number="ageFrom" type="number"/>
                                    <label class="label m-r-15">To</label>
                                    <input class="input--style-4 col-2 m-r-45" @keyup="ageValidate" v-model.number="ageTo" type="number" />
                                    <label class="err-msg mt-2">{{msgAgeErr}}</label>
                                </div>
                            </div>
                        </div>
                        <div class="row row-space">
                            <div class="col-4">
                                <div class="input-group">
                                    <label class="label">Score/Penal *</label>
                                </div>
                            </div>
                        </div>
                        <div class="score-penal-area">
                            <div class="row row-space">
                                <div class="col-2">
                                    <div class="input-group">
                                        <label class="label">Types</label>
                                    </div>
                                </div>
                                <div class="col-3">
                                    <div class="input-group">
                                        <label class="label">Name</label>
                                    </div>
                                </div>
                                <div class="col-3">
                                    <div class="input-group">
                                        <label class="label">Description</label>
                                    </div>
                                </div>
                                <div class="col-2">
                                    <div class="input-group">
                                        <label class="label">Value</label>
                                    </div>
                                </div>
                                <div class="col-1">
                                    <div class="input-group">
                                        <label class="label">Image</label>
                                    </div>
                                </div>
                                <div class="col-1"></div>
                            </div>
                            <div class="row row-space m-t-5 pb-3">
                                <div class="col-2">
                                    <div class="input-group">
                                        <select v-model="scorePenalForm.sportPointType" class="form-control" name="score-penal">
                                            <!-- <option disabled="disabled" selected="selected">Select types</option> -->
                                            <option value="SCORE">SCORE</option>
                                            <option value="PENAL">PENAL</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-3">
                                    <div class="input-group">
                                        <input class="input--style-4" type="text" v-model="scorePenalForm.sportPointName" />
                                    </div>
                                </div>
                                <div class="col-3">
                                    <div class="input-group">
                                        <textarea class="form-control" v-model="scorePenalForm.sportPointDescription" rows="2"></textarea>
                                    </div>
                                </div>
                                <div class="col-2">
                                    <div class="input-group">
                                        <input class="input--style-4" type="text" v-model="scorePenalForm.sportPointValue" />
                                    </div>
                                </div>
                                <div class="col-1">
                                    <input type="file" v-on:change="onFileChange" hidden ref="inputImage">
                                    <div class="input-group">
                                        <b-img rounded v-bind="mainProps" @click="chooseImage" center :src="scorePenalForm.sportPointAvatar"></b-img>
                                    </div>
                                </div>
                                <div class="col-1">
                                    <div class="qty">
                                        <span v-on:click="addScorePenal" class="plus bg-dark">+</span>
                                    </div>
                                </div>
                                <br>
                                <label class="err-msg ml-3">{{ msgScorePenalErr }}</label>
                            </div>
                            <hr v-if="sectionScorePenals.length !== 0" class="score-panel-dotted" />
                            <div v-for="(item, index) in sectionScorePenals" v-bind:key="index" class="row row-space">
                                <div class="col-2">
                                    <div class="input-group">
                                        <select v-model="item.sportPointType" class="form-control">
                                            <option value="SCORE">SCORE</option>
                                            <option value="PENAL">PENAL</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-3">
                                    <div class="input-group">
                                        <input class="input--style-4" type="text" v-model="item.sportPointName" />
                                    </div>
                                </div>
                                <div class="col-3">
                                    <div class="input-group">
                                        <textarea class="form-control" v-model="item.sportPointDescription" rows="2"></textarea>
                                    </div>
                                </div>
                                <div class="col-2">
                                    <div class="input-group">
                                        <input class="input--style-4" type="text" v-model="item.sportPointValue" />
                                    </div>
                                </div>
                                <div class="col-1">
                                    <!-- <input type="file" v-on:change="onFileChange" hidden ref="inputChangeImage"> -->
                                    <div class="input-group">
                                        <b-img rounded v-bind="mainProps" center :src="item.sportPointAvatar"></b-img>
                                    </div>
                                </div>
                                <div class="col-1">
                                    <div class="qty">
                                        <span v-on:click="removeScorePenal($event, index)" class="minus bg-dark">-</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row row-space">
                            <div class="col-3">
                                <div class="input-group">
                                    <label class="label">Description</label>
                                </div>
                            </div>
                            <div class="col-9">
                                <div class="input-group">
                                    <textarea class="form-control" v-model="ruleDescription" placeholder="Enter sport rules..." name="sport-description-textarea" rows="5"></textarea>
                                </div>
                            </div>
                        </div>
                        <hr class="striped-border" />

                        <div class="attribute-area">
                            <div class="row row-space">
                                <label class="section-atr">The section using for you to add attributes</label>
                            </div>
                            <div class="row row-space">
                                <div class="col-4">
                                    <label class="label">Attributes</label>
                                </div>
                                <div class="col-7">
                                    <label class="label">Attribute description</label>
                                </div>
                                <div class="col-1"></div>
                            </div>
                            <div class="row row-space pb-4">
                                <div class="col-4">
                                    <input class="input--style-4" placeholder="Enter attribute..." type="text" v-model="attributes" />
                                </div>
                                <div class="col-7">
                                    <textarea class="form-control" v-model="attributesDes" rows="2"></textarea>
                                </div>
                                <div class="col-1">
                                    <div class="qty">
                                        <span v-on:click="addAttribute" class="plus bg-dark">+</span>
                                    </div>
                                </div>
                                <br>
                                <label class="err-msg ml-3 mt-2">{{ msgAttributesErr }}</label>
                            </div>
                            <hr v-if="sectionAttributes.length !== 0" class="score-panel-dotted">
                            <div v-for="(item, index) in sectionAttributes" v-bind:key="index" class="row row-space pb-2">
                                <div class="col-4">
                                    <input class="input--style-4" v-model="item.sportAttributeName" placeholder="Enter attribute..." type="text" />
                                </div>
                                <div class="col-7">
                                    <textarea class="form-control" v-model="item.sportAttributeDescription" rows="2"></textarea>
                                </div>
                                <div class="col-1">
                                    <div class="qty">
                                        <span v-on:click="removeAtrribute($event, index)" class="minus bg-dark">-</span>
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
                                <button class="btn-action-sport btn-success" type="submit">Save</button>
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
    import VueTagsInput from "@johmun/vue-tags-input";
    import http from "../http-common";

    export default {
        name: "sport-definition",
        components: {
            VueTagsInput,
        },
        data() {
            return {
               id: this.$route.params.id || null,
               title: "Sport Definition",
               mainProps: { width: 50, height: 50, class: 'm1' },
               no_image: "/img/no_image.be940c4b.jpg",
               msgErrors: "",
               msgSuccess: "",
               dismissCountDown: 0,
               dismissSecs: 4,
               sportName: "",
               isShowExtraTime: false,
               tag: "",
               tags: [],
               autocompleteItems: [
                  {text: "Goalkeeper",},
                  {text: "Defending",},
                  {text: "Central",},
                  {text: "Striker",},
                  {text: "Attrack",},
               ],
               checkedCompetition : [],
               isCheckedFightScore: false,
               fightOther: "",
               checkedCompete: "",
               competeOther: "",
               gender: "",
               ageFrom: null,
               ageTo: null,
               msgAgeErr: "",
               halfSetRoundSelect: "Choose types",
               msgHalfSetRoundErr: "",
               halfSetRoundForm: { sportRoundType: "", sportRoundName: "", sportRoundTime: null},
               msgHalfSetRoundExtraErr: "",
               halfSetRoundExtraForm: { sportRoundType: "", sportRoundName: "", sportRoundTime: null},
               sectionHalfSetRound: [],
               sectionHalfSetRoundExtra: [],
               scorePenalForm: { sportPointType: "", sportPointName: "", sportPointDescription: "", sportPointValue: "", sportPointAvatar: "/img/no_image.be940c4b.jpg"},
               sectionScorePenals: [],
               msgScorePenalErr: "",
               ruleDescription: "",
               sectionAttributes: [],
               attributes: "",
               attributesDes: "",
               msgAttributesErr: ""
            };
        },
        methods: {
            countDownChanged(dismissCountDown) {
                this.dismissCountDown = dismissCountDown
            },
            showAlertError(msg){
                this.msgErrors = msg;
                this.dismissCountDown = this.dismissSecs;
                // window.scrollTo(0,0);
            },
            showAlertSuccess(msg){
                this.msgSuccess = msg;
                this.dismissCountDown = this.dismissSecs;
            },
            showExtraTime() {
                if (this.sectionHalfSetRound.length == 0){
                    return this.msgHalfSetRoundErr = "Please add a Half/Set/Round to continue!";
                }
                return (this.isShowExtraTime = !this.isShowExtraTime);
            },
            ageValidate() {
               if (this.ageFrom < 0 || this.ageFrom > 100 || this.ageTo < 0 || this.ageTo > 100) {
                  return this.msgAgeErr = "Age input is inValid!"
               } else if(this.ageTo !== null && this.ageTo !== "" && this.ageFrom > this.ageTo) {
                  return this.msgAgeErr = "Age to must be greater than or equal to age from!";
               } else {
                   return this.msgAgeErr = "";
               }
            },
            unCheckedFight(){
                this.isCheckedFightScore = false;
                this.checkedCompete = false;
                this.competeOther = "";
            },
            unCheckedCompete(){
                this.checkedCompete = false;
                this.isCheckedFightScore = false;
                this.fightOther = "";
            },
            checkFightScore(){
                if (this.isCheckedFightScore !== ""){
                    this.fightOther = "";
                    this.competeOther = "";
                    this.checkedCompete = false;
                }
            },
            checkCompete(){
                if (this.checkedCompete !== ""){
                    this.competeOther = "";
                    this.isCheckedFightScore = false;
                    this.fightOther = "";
                }
            },
            addAttribute() {
                if (this.attributes === "" || this.attributesDes === ""){
                    return this.msgAttributesErr = "Please input attribute name and desciption to add!";
                }
                // Check add dupplicate
                var isExisted = false;
                if (this.sectionAttributes.length != 0){
                    this.sectionAttributes.forEach(e => {
                        if (this.attributes === e.sportAttributeName && this.attributesDes === e.sportAttributeDescription){
                            return isExisted = true;
                        }
                    });
                }
                if (isExisted){
                    return this.msgAttributesErr = "This Attribute already exists, please input new value!";
                } else {
                    this.msgAttributesErr = "";
                    this.sectionAttributes.push({ sportAttributeName: this.attributes, sportAttributeDescription: this.attributesDes});
                    this.attributes = "";
                    this.attributesDes = "";
                }
            },
            removeAtrribute(e, index){
                this.sectionAttributes.splice(index, 1);
            },
            addScorePenal() {
                if (this.scorePenalForm.sportPointType === "" || this.scorePenalForm.sportPointName === "" ||
                    this.scorePenalForm.sportPointDescription === "" || this.scorePenalForm.sportPointValue === ""){
                    return this.msgScorePenalErr = "Please enter all fields to add!";
                } 
                
                // Check add dupplicate
                var isExisted = false;
                if (this.sectionScorePenals.length != 0){
                    this.sectionScorePenals.forEach(e => {
                        if (this.scorePenalForm.sportPointType === e.sportPointType 
                        && this.scorePenalForm.sportPointName === e.sportPointName
                        && this.scorePenalForm.sportPointType === e.sportPointType
                        && this.scorePenalForm.sportPointValue === e.sportPointValue){
                            return isExisted = true;
                        }
                    });
                }
                if (isExisted){
                    return this.msgScorePenalErr = "This Score/Penal already exists, please input new value!";
                } else {
                    this.msgScorePenalErr = "";
                    this.sectionScorePenals.push(Object.assign({}, this.scorePenalForm));
                    this.scorePenalForm.sportPointType = "";
                    this.scorePenalForm.sportPointName = "";
                    this.scorePenalForm.sportPointDescription = "";
                    this.scorePenalForm.sportPointValue = "";
                    this.scorePenalForm.sportPointAvatar = "/img/no_image.be940c4b.jpg";
                }
            },
            removeScorePenal(e, index){
                this.sectionScorePenals.splice(index, 1);
            },
            addHalfSetRound(){
                if (this.halfSetRoundSelect === "Choose types") {
                    return this.msgHalfSetRoundErr = "Please choose a type of Half/Set/Round to add!";
                }
                if (this.halfSetRoundForm.sportRoundName === "" || this.halfSetRoundForm.sportRoundTime === ""){
                    return this.msgHalfSetRoundErr = "Please input name and time to add!";
                }
                if (this.halfSetRoundForm.sportRoundTime <= 0) {
                    return this.msgHalfSetRoundErr = "Input time invalid, please input again!";
                } 

                // Check add dupplicate to this.sectionHalfSetRound
                var isExisted = false;
                if (this.sectionHalfSetRound.length != 0){
                    this.sectionHalfSetRound.forEach(e => {
                        if (this.halfSetRoundForm.sportRoundName === e.sportRoundName 
                        && this.halfSetRoundForm.sportRoundTime === e.sportRoundTime){
                            return isExisted = true;
                        }
                    });
                }
                if (isExisted){
                    return this.msgHalfSetRoundErr = "Data input already exists, please input new value!";
                } else {
                    this.halfSetRoundForm.sportRoundType = this.halfSetRoundSelect.toUpperCase();
                    this.msgHalfSetRoundErr = "";
                    this.sectionHalfSetRound.push(Object.assign({}, this.halfSetRoundForm));
                    this.halfSetRoundForm.sportRoundType = "";
                    this.halfSetRoundForm.sportRoundName = "";
                    this.halfSetRoundForm.sportRoundTime = "";
                }
            },
            removeHalfSetRound(e, index){
                this.sectionHalfSetRound.splice(index, 1);
            },
            addHalfSetRoundExtra(){
                if (this.halfSetRoundExtraForm.sportRoundName === "" || this.halfSetRoundExtraForm.sportRoundTime === ""){
                    return this.msgHalfSetRoundExtraErr = "Please input name and time to add!";
                }
                if (this.halfSetRoundExtraForm.sportRoundTime <= 0) {
                    return this.msgHalfSetRoundExtraErr = "Input time invalid, please input again!";
                } 

                // Check add dupplicate to this.sectionHalfSetRoundExtra
                var isExisted = false;
                if (this.sectionHalfSetRoundExtra.length != 0){
                    this.sectionHalfSetRoundExtra.forEach(e => {
                        if (this.halfSetRoundExtraForm.sportRoundName === e.sportRoundName && this.halfSetRoundExtraForm.sportRoundTime === e.sportRoundTime){
                            return isExisted = true;
                        }
                    });
                }
                if (isExisted){
                    return this.msgHalfSetRoundExtraErr = "This extra-time already exists, please input new value!";
                } else {
                    this.halfSetRoundExtraForm.sportRoundType = "EXTRA_TIME";
                    this.msgHalfSetRoundExtraErr = "";
                    this.sectionHalfSetRoundExtra.push(Object.assign({}, this.halfSetRoundExtraForm));
                    this.halfSetRoundExtraForm.sportRoundType = "";
                    this.halfSetRoundExtraForm.sportRoundName = "";
                    this.halfSetRoundExtraForm.sportRoundTime = "";
                }
            },
            removeHalfSetRoundExtra(e, index){
                this.sectionHalfSetRoundExtra.splice(index, 1);
            },
            checkFormCreate(e){
                e.preventDefault();

                // Check input Sport Name
                if (this.sportName === "" || this.sportName === null){
                    this.$refs.sport_name.focus();
                    return this.showAlertError("Please input Sport Name!");
                }

                // Check input positionsLevels
                if (this.sportDTO.positionsLevels === "")
                    return this.showAlertError("Postitions/Levels is required!");
                
                // Check Competition Format
                if (this.sportDTO.competition === "")
                    return this.showAlertError("Please choose Competition Format!");
                
                // Check Match Types
                if (this.sportDTO.matchTypeValue === "")
                    return this.showAlertError("Match Types is required!");

                // Check choose Gender
                if (this.gender === "" || this.gender === null)
                    return this.showAlertError("Please choose a Gender!");

                // Validate age input
                if (this.ageFrom === null || this.ageFrom === "" || this.ageTo === null || this.ageTo === "") 
                    return this.showAlertError("Please input age from and age to!");
                if (this.msgAgeErr !== "")  return this.showAlertError(this.msgAgeErr);
                
                if (this.sectionScorePenals.length == 0)
                    return this.showAlertError("Please add a Score/Penal!");

                http
                    .post("/sport/update", this.sportDTO)
                    .then(response => {
                        console.log(response.data);
                        var msg = "";

                        if (response.data.success){
                            msg = this.sportDTO.sportId === null ? 
                                "Create new Sport Successfully!" : "Update Sport Successfully!";
                            this.showAlertSuccess(msg);
                            this.$router.push({ path: `/sports` });
                        } else {
                            msg = response.data.message;
                            return this.showAlertError(msg);
                        }
                    })
                    .catch(e => {
                        console.log(e);
                    });
            },
            resetFields() {
                Object.assign(this.$data, this.$options.data.call(this));
            },
            capitalizeFirstLetter(string) {
            return string.charAt(0).toUpperCase() + string.slice(1);
            },
            convertEntityToDTO(e){
                let positions = e.positionsLevels.split(",");
                this.sportName = e.sportName;
                positions.forEach(el => {
                    this.tags.push(Object.assign({}, {
                        "text": el,
                        "tiClasses": ["ti-valid"]
                    }));
                });
                this.checkedCompetition = e.competition.split(",");
                let value = e.matchTypeValue
                if (e.matchType === "Compete") {
                    if ( value !== "Score" && value !== "Distance/Height" && value !== "Time") {
                        this.competeOther = value;
                    } else {
                        this.checkedCompete = value;
                    }
                } else {
                    if (value !== "Score") {
                        this.fightOther = value;
                    } else {
                        this.isCheckedFightScore = value;
                    }
                }
                
                this.gender = e.gender;
                this.ageFrom = e.ageFrom;
                this.ageTo = e.ageTo;
                this.ruleDescription = e.description;
                e.sportAttribute.forEach(el => {
                    this.sectionAttributes.push(Object.assign({}, {
                            "sportAttributeName": el.sportAttributeName,
                            "sportAttributeDescription": el.sportAttributeDescription
                        }
                    ));
                });
                e.sportScorePenal.forEach(el => {
                    this.sectionScorePenals.push(Object.assign({}, {
                            "sportPointType": el.sportPointType,
                            "sportPointName": el.sportPointName,
                            "sportPointDescription": el.sportPointDescription,
                            "sportPointValue": el.sportPointValue
                        }
                    ));
                });

                let roundType = "";
                e.sportRound.forEach(el => {
                    let objRound = {
                        "sportRoundType": el.sportRoundType,
                        "sportRoundName": el.sportRoundName,
                        "sportRoundTime": el.sportRoundTime
                    };
                    if (el.sportRoundType !== "EXTRA_TIME"){
                        roundType = this.capitalizeFirstLetter(el.sportRoundType.toLowerCase());
                        this.sectionHalfSetRound.push(Object.assign({}, objRound));
                    } else {
                        this.isShowExtraTime = true;
                        this.sectionHalfSetRoundExtra.push(Object.assign({}, objRound));
                    }
                });
                this.halfSetRoundSelect = roundType;
            },
            chooseImage(){
                this.$refs['inputImage'].click();
            },
            onFileChange(e) {
                var files = e.target.files || e.dataTransfer.files;
                if (!files.length)
                    return;
                // this.createImage(files[0]);
                this.scorePenalForm.sportPointAvatar = URL.createObjectURL(files[0]);
            },
        },
        computed: {
            filteredItems() {
               return this.autocompleteItems.filter(i => {
               return i.text.toLowerCase().indexOf(this.tag.toLowerCase()) !== -1;
               });
            },
            fightMatchType() {
                if (this.fightOther !== "" && this.isCheckedFightScore === false){
                    return this.fightOther;
                } else {
                    return (this.isCheckedFightScore === false) ? "" : this.isCheckedFightScore;
                }
            },
            competeMathType(){
                return (this.competeOther !== "") ? this.competeOther : this.checkedCompete;
            },
            listHalfSetRound(){
                return this.sectionHalfSetRound.concat(this.sectionHalfSetRoundExtra);
            },
            sportDTO() {
                var postitionsLevels = [];
                this.tags.forEach(e => {
                    postitionsLevels.push(e.text);
                });
                return {
                    sportId: this.id,
                    sportName: this.sportName,
                    positionsLevels: postitionsLevels.join(),
                    competition: this.checkedCompetition.join(),
                    matchType: (this.fightMatchType === "") ? "Compete" : "Fight",
                    matchTypeValue: (this.fightMatchType === "") ? this.competeMathType : this.fightMatchType,
                    sportRound: this.listHalfSetRound, // Array
                    gender: this.gender,
                    ageFrom: this.ageFrom,
                    ageTo: this.ageTo,
                    sportScorePenal: this.sectionScorePenals, // Array
                    description: this.ruleDescription,
                    sportAttribute: this.sectionAttributes, // Array
                }
            },
        },
        created() {
            if (this.id !== null) {
                this.resetFields();
                this.title = "Update " + this.title;
                let sportDetail = JSON.parse(localStorage.getItem('sportDetail'));
                this.convertEntityToDTO(sportDetail);
            }
        },
        watch: {
            competeMathType(){
                if (this.competeMathType !== "")
                    this.fightMatchType == "";
            },
            $route (to){
                console.log("to", to);
                if (to.path === "/sport/create"){
                    this.resetFields();
                } else { //detail
                    this.id = to.params.id;
                }
            },
            id(){
                if (this.id !== null){
                    this.resetFields();
                    this.title = "Update Sport Definition";
                    // Get sport for id
                    let sportDetail = JSON.parse(localStorage.getItem('sportDetail'));
                    console.log("sportDetail", sportDetail);
                    this.convertEntityToDTO(sportDetail);
                }
            }
        } 
    };
</script>

<style>
    @import "../assets/styles/main.css";
</style>

