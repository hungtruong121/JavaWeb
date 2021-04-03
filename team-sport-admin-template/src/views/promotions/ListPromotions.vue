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
      >{{ msgErrors }}</CAlert>
      <b-alert
        v-if="msgSuccess !== ''"
        :show="dismissCountDown"
        class="position-fixed top-right sticky-top"
        dismissible
        variant="success"
        fade
        @dismissed="dismissCountDown=0"
        @dismiss-count-down="countDownChanged"
      >{{ msgSuccess }}</b-alert>
      <CCardBody>
        <h1>List Promotion</h1>
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
          <template #duration="{item}">
            <td class="py-2">{{ item.duration + " months" }}</td>
          </template>
          <template #details-promotion="{item}">
            <td class="py-2">
              <CButton color="primary" variant="outline" square size="sm">
                Details
              </CButton>
            </td>
          </template>
          <template v-slot:cell(teamObjects)="row">
            <b-link
              v-for="(team, index) in row.item.teamObjects"
              v-bind:key="index"
              :href="'/#/team/detail/' + team.teamId"
            >{{ team.teamName }}</b-link>
          </template>
          <template v-slot:cell(promotionObjects)="row">
            <b-link
              v-for="(team, index) in row.item.promotionObjects"
              v-bind:key="index"
              :href="'/#/team/detail/' + team.teamRankId"
            >{{ team.teamRankName }}</b-link>
          </template>
          <template #update-Payment="{item}">
            <td class="py-2">
              <CButton
                color="primary"
                variant="outline"
                square
                size="sm"
                v-on:click="updatePayment($event, item.paymentHistoryId)"
              >Save</CButton>
            </td>
          </template>
        </CDataTable>
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
  name: "list-promotion",
  components: {
    "v-select": vSelect,
  },
  data() {
    return {
     fields: [
        {
          key: "details-promotion",
          label: "",
          _style: "width:1%",
          sorter: false,
          filter: false,
        },
        {
          key: "del-promotion",
          label: "",
          _style: "width:1%",
          sorter: false,
          filter: false,
        },
        { key: "index", _style: "min-width:1%" },
        { key: "promotionCode", _style: "min-width:200px" },
        { key: "status", _style: "min-width:200px" },
        { key: "title", _style: "min-width:200px" },
        { key: "description", _style: "min-width:200px" },
        { key: "promotionObjects", _style: "min-width:200px" },
        { key: "teamObjects", _style: "min-width:200px" },
        { key: "discount", _style: "min-width:200px" },
        { key: "inscreaseDuration", _style: "min-width:200px" },
        { key: "dateBegin", _style: "min-width:200px" },
        { key: "dateEnd", _style: "min-width:200px" },
        { key: "dateCreated", _style: "min-width:200px" },
        { key: "dateUpdate", _style: "min-width:200px" },
      ],
      items: [],
      itemsDetail: [],
      totalRows: 1,
      currentPage: 1,
      sortBy: "",
      sortDesc: false,
      sortDirection: "asc",
      filter: null,
      filterOn: [],
      infoModal: {
        id: "info-modal",
        title: "",
        content: "",
      },
      isRemoveLoading: false,
      isViewDetail: false,
      indexClicked: -1,
      isBusy: true,
      msgErrors: "",
      msgSuccess: "",
      dismissCountDown: 0,
      dismissSecs: 4,
      redirect: null,
    };
  },
  computed: {
    sortOptions() {
       //Create an options list from our fields
      return this.fields
        .filter((f) => f.sortable)
        .map((f) => {
          return { text: f.label, value: f.key };
        });
    },
  },
  mounted() {
    // Set the initial number of items
    this.totalRows = this.items.length;
    this.getListPromotion();
  },
  methods: {
    resetInfoModal() {
      this.infoModal.title = "";
      this.infoModal.content = "";
    },
    onFiltered(filteredItems) {
      // Trigger pagination to update the number of buttons/pages due to filtering
      this.totalRows = filteredItems.length;
      this.currentPage = 1;
    },
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
     getListPromotion() {
        this.items = [];
             var listPromotion = JSON.parse(localStorage.getItem('listPromotion'));
             if (listPromotion && listPromotion != null)
             {
                 setTimeout(() => 
                 {
                     if (this.items.length == 0) 
                     {
                         listPromotion.forEach(promotion => 
                         {
                             this.items.push(Object.assign({}, promotion));
                         });
                         this.totalRows = this.items.length;
                         this.isBusy = false;
                     }
                 }, 2000);
             }
        var token = localStorage.getItem("token");
        const config = {
          headers: { Authorization: `Bearer ${token}` },
        };
       axios
         .get("/promotion/list", config)
         .then(response => {
           if (response.data.success)
           {
             this.items=[];
             this.itemsDetail = response.data.data;
             console.log("response.data.data", response.data.data);
             response.data.data.forEach((e) =>
             {
               var teams =[];
               e.teamObjects.forEach(el => {
               teams.push(Object.assign({}, {teamId: el.teamId, teamName: el.teamName}));
               });
               var promotionObj =[];
               e.teamObjects.forEach(el => {
               teams.push(Object.assign({}, {teamRankId: el.teamRankId, teamRankName: el.teamRankName}));
               });
               var promotionDTO = {
                 promotionId: e.promotionId,
                 listTeamApplyPromo: e.listTeamApplyPromo,
                 promotionCode: e.promotionCode,
                 title: e.promotionTitle,
                 description: e.promotionDescription,
                 status: e.promotionObjects == 0 ? "Inactive" : "Active",
                 isUnlimitedTeam: e.isUnlimitedTeam,
                 promotionObjects: promotionObj,
                 teamObjects: teams,
                 discount: e.promotionValue + e.promotionType,
                 inscreaseDuration: e.increaseDuration,
                 dateBegin: this.formatDate(e.dateBeginDate),
                 dateEnd: this.formatDate(e.dateEndDate),
                 isActive: e.isActive,
                 dateCreated: this.formatDate(e.dateCreated),
                 dateUpdate: this.formatDate(e.updatedDate)
               };
               this.items.push(Object.assign({}, promotionDTO));
              });
             this.isBusy = !this.isBusy;
           } 
           else {  error
             this.showAlertError(response.data.message);
           }
         })
         .catch((e) => {
           this.showAlertError(e);
         });
     }
  }
}
</script>