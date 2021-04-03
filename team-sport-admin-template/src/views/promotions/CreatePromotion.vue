<template>
  <div>
    <CCard>
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
        @dismissed="dismissCountDown = 0"
        @dismiss-count-down="countDownChanged"
      >
        {{ msgSuccess }}
      </b-alert>
      <CCardBody>
        <h1>Create Promotion</h1>
        <br />
        <CForm @submit="checkFormCreate" method="post">
          <div class="col-4">
            <h3>Promotion Commons</h3>
            <br />
            <CInput
              @input="upper($event)"
              label="Promotion code"
              onkeyup="this.value = this.value.toUpperCase();"
              placeholder="Enter promotion code"
              v-model="promotionCode"
            ></CInput>
            <CButton v-on:click="rndStr()" color="primary">Ramdom code</CButton>
            <br />
            <br />
            <CInput
              label="Title"
              placeholder="Enter promotion title"
              v-model="promotionTitle"
            ></CInput>
            <CTextarea
              label="Description"
              placeholder="Enter description"
              v-model="description"
            ></CTextarea>
            <label> Status</label>
            <v-select :options="statusOption" v-model="statusSelected">
            </v-select>
            <br />
            <b-form-group label="Promotion Objects">
              <b-form-checkbox-group
                id="select-rank"
                v-model="rankSelected"
                :options="optionsRank"
                name="rank"
              ></b-form-checkbox-group>
            </b-form-group>
          </div>
          <hr />
          <h3>Team Objects</h3>
          <br />
          <CDataTable
            :items="items"
            :fields="fields"
            column-filter
            table-filter
            items-per-page-select
            :items-per-page="50"
            hover
            sorter
            pagination
          >
            <template #index="item">{{ item.index + 1 }}</template>
            <template #selectedTeam>
              <td class="py-2">
                <CInputCheckbox></CInputCheckbox>
              </td>
            </template>
          </CDataTable>
          <br />
          <b-form-checkbox v-model="unlimited" value="1" unchecked-value="0">
            Unlimited team
          </b-form-checkbox>

          <hr />
          <div class="col-4">
            <h3>Promotion type</h3>
            <br />
            <label>Discount </label>
            <b-input-group inline>
              <b-input
                placeholder="Enter percentage or amount"
                v-model="amount"
              ></b-input>
              <v-select :options="optionsDiscountType" v-model="discountType">
              </v-select>
            </b-input-group>
            <br />
            <CInput
              label="Increase Duration"
              placeholder=" Enter increase duration day"
              v-model="duration"
            ></CInput>
          </div>
          <hr />
          <div class="col-4">
            <h3>Promotion period</h3>
            <br />
            <label>Date time start</label>
            <b-input-group class="mb-3">
              <b-form-input
                v-model="timeStart"
                type="text"
                placeholder="hh:mm:ss"
              ></b-form-input>
              <b-form-input
                id="example-input"
                v-model="dayStart"
                type="text"
                placeholder="yyyy:mm:dd"
                autocomplete="off"
              ></b-form-input>
              <b-input-group-append>
                <b-form-timepicker
                  v-model="timeStart"
                  button-only
                  right
                  show-seconds
                ></b-form-timepicker>
              </b-input-group-append>

              <b-input-group-append>
                <b-form-datepicker
                  v-model="dayStart"
                  button-only
                  right
                ></b-form-datepicker>
              </b-input-group-append>
            </b-input-group>
            <br />
            <label>Date time end</label>
            <b-input-group class="mb-3">
              <b-form-input
                v-model="timeEnd"
                type="text"
                placeholder="hh:mm:ss"
              ></b-form-input>
              <b-form-input
                id="example-input"
                v-model="dayEnd"
                type="text"
                placeholder="yyyy:mm:dd"
                autocomplete="off"
              ></b-form-input>
              <b-input-group-append>
                <b-form-timepicker
                  v-model="timeEnd"
                  button-only
                  right
                  show-seconds
                ></b-form-timepicker>
              </b-input-group-append>
              <b-input-group-append>
                <b-form-datepicker
                  v-model="dayEnd"
                  button-only
                  right
                ></b-form-datepicker>
              </b-input-group-append>
            </b-input-group>
          </div>
          <hr />
          <div class="col-4">
            <b-button variant="success" type="submit">
              <b-spinner v-if="isLoading" small></b-spinner> Add promotion
            </b-button>
           
          </div>
        </CForm>
      </CCardBody>
    </CCard>
  </div>
</template>

<script>
import axios from "../../../http-common";
import vSelect from "vue-select";
import "vue-select/dist/vue-select.css";

export default {
  success: true,
  errorCode: null,
  message: null,
  components: {
    "v-select": vSelect,
  },
  data() {
    return {
      duration:"",
      isLoading: false,
      unlimited: "",
      dayEnd: "",
      timeEnd: "",
      dayStart: "",
      timeStart: "",
      optionsDiscountType: ["%", "Amout"],
      promotionCode: "",
      promotionTitle: "",
      description: "",
      statusSelected: "",
      amount: "",
      discountType: "",
      dateBegin: "",
      dateEnd: "",
      rankSelected: [], // Must be an array reference!
      optionsRank: [
        { text: "Silver", value: "silver" },
        { text: "Gold", value: "gold" },
        { text: "Diamond", value: "diamond" },
      ],
      fields: [
        {
          key: "selectedTeam",
          label: "",
          _style: "width:1%",
          sorter: false,
          filter: false,
        },
        { key: "index", _style: "min-width:1%" },
        { key: "teamId", _style: "min-width:100px" },
        { key: "teamName", _style: "min-width:200px" },
        { key: "shortName", _style: "min-width:200px" },
        { key: "shortName", _style: "min-width:200px" },
        { key: "sportName", _style: "min-width:200px" },
        { key: "sologan", _style: "min-width:200px" },
        { key: "teamEmail", _style: "min-width:200px" },
        { key: "national", _style: "min-width:200px" },
        { key: "location", _style: "min-width:200px" },
        { key: "members", _style: "min-width:200px" },
        { key: "description", _style: "min-width:200px" },
        { key: "rank", _style: "min-width:200px" },
        { key: "status", _style: "min-width:200px" },
        { key: "dateCreated", _style: "min-width:200px" },
        { key: "dateUpdated", _style: "min-width:200px" },
      ],
      items: [],
      statusOption: ["Active", "Inactive"],
      itemsDetail: [],
      indexClicked: -1,
      isBusy: true,
      msgErrors: "",
      msgSuccess: "",
      dismissCountDown: 0,
      dismissSecs: 4,
      redirect: null,
    };
  },

  mounted() {
    this.getListTeam();
  },
  computed: {
        promotionDTO() {
          var dateTimeStart= this.dayStart + " " + this.timeStart;
          var dateTimeEnd= this.dayEnd + " " + this.timeEnd;
          var promotionStatus="";
          if(this.statusSelected=="Active")
            promotionStatus="1";
          else promotionStatus="0";
            return {
                listTeamApplyPromo: this.rankSelected,
                promotionCode: this.promotionCode,
                promotionTitle: this.promotionTitle,
                promotionDescription: this.description,
                promotionStatus: promotionStatus,
                isUnlimitedTeam: this.unlimited,
                promotionType:this.promotionType,
                promotionValue: this.promotionType,
                increaseDuration: this.duration,
                beginDate: dateTimeStart,
                endDate: dateTimeEnd,
            }
        },
  },
  methods: {
    countDownChanged(dismissCountDown) {
      this.dismissCountDown = dismissCountDown;
    },
    showAlertError(msg) {
      this.msgErrors = msg;
      this.dismissCountDown = this.dismissSecs;
    },
    showAlertSuccess(msg) {
      this.msgSuccess = msg;
      this.dismissCountDown = this.dismissSecs;
    },
    formatDate(date) {
      var d = new Date(date),
        month = "" + (d.getMonth() + 1),
        day = "" + d.getDate(),
        year = d.getFullYear();
      if (month.length < 2) month = "0" + month;
      if (day.length < 2) day = "0" + day;
      return [day, month, year].join("/");
    },
    rndStr() {
      let text = " ";
      let chars = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
      for (let i = 0; i < 10; i++) {
        text += chars.charAt(Math.floor(Math.random() * chars.length));
      }
      this.promotionCode = text;
    },
    getListTeam() {
      axios
        .get("/team/admin/all")
        .then((response) => {
          if (response.data.success) {
            this.itemsDetail = response.data.data;
            console.log("response.data.data", response.data.data);
            response.data.data.forEach((e) => {
              var paymentDTO = {
                teamId: e.teamId,
                status: e.isActive == 0 ? "Blocked" : "Active",
                // userId: e.userId,
                teamName: e.teamName,
                shortName: e.teamShortName,
                sportName: e.sportName,
                sologan: e.teamSlogan,
                teamEmail: e.teamMail,
                national: e.teamNational,
                location: e.teamAddress,
                members: e.totalMember,
                description: e.teamDescription.slice(0, 50).concat("..."),
                rank:
                  e.teamRankId == 1
                    ? "Basic"
                    : e.teamRankId == 2
                    ? "Silver"
                    : e.teamRankId == 3
                    ? "Gold"
                    : "Diamond",
                dateCreated: this.formatDate(e.createdDate),
                dateUpdated: this.formatDate(e.updatedDate),
              };
              this.items.push(Object.assign({}, paymentDTO));
            });
            this.isBusy = !this.isBusy;
          } else {
            // error
            this.showAlertError(response.data.message);
          }
        })
        .catch((e) => {
          this.showAlertError(e);
        });
    },
    checkFormCreate(e) {
            e.preventDefault();
            if (this.promotionCode === "" || this.promotionCode === null)
                return this.showAlertError("Please enter Promotion Code!");

            if (this.promotionTitle === "" || this.promotionTitle === null)
                return this.showAlertError("Please enter Promotion Title!");
            
            if (this.promotionStatus === "" || this.promotionStatus === null)
                return this.showAlertError("Please select Promotion status!");

            if (this.promotionDescription === "" || this.promotionDescription === null)
                return this.showAlertError("Please enter Promotion description!");

            if (this.promotionType === "" || this.promotionType === null)
                return this.showAlertError("Please select Promotion type!");   

            if (this.promotionValue === "" || this.promotionValue === null)
                return this.showAlertError("Please enter Promotion value");    
                
            if (this.duration === "" || this.duration === null)
                return this.showAlertError("Please enter duration"); 
            
            if (this.dayStart === "" || this.dayStart === null)
                return this.showAlertError("Please select day start"); 
            
             if (this.dayEnd === "" || this.dayEnd === null)
                return this.showAlertError("Please select day end"); 

             if (this.timeStart === "" || this.timeStartStart === null)
                return this.showAlertError("Please select time start");

             if (this.timeEnd === "" || this.timeEnd === null)
                return this.showAlertError("Please select time end!"); 

            this.isLoading = true;

            axios
                .post("/promotion/update", this.promotionDTO)
                .then(response => {
                    this.isLoading = false;
                    var msg = "";
                    if (response.data.success){
                        msg = "Create new Promotion Successfully!";
                        this.showAlertSuccess(msg);
                        setTimeout(() => {
                            this.$router.push({ path: `promotions/listpromotions` });
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
};
</script>
<style>
    @import "../../assets/styles/main.css";
</style>