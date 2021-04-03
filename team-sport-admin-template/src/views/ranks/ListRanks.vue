<template>
    <div>
    <CCard>
      <CCardBody>
        <h1 class="title">{{ title }}</h1>
        <br />
        <b-alert
            v-if="msgErrors !== ''"
            :show="dismissCountDown"
            class="position-fixed top-right sticky-top"
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
            class="position-fixed top-right sticky-top"
            dismissible
            variant="success"
            fade
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged"
        >
            {{ msgSuccess }}
        </b-alert>
      <CDataTable
          :items="items"
          :fields="fields"
          column-filter
          table-filter
          items-per-page-select
          :items-per-page="5"
          hover
          sorter
          pagination
        >
            <template v-slot:table-busy>
                <div class="text-center text-danger my-2">
                    <b-spinner class="align-middle"></b-spinner>
                    <strong>Loading...</strong>
                </div>
            </template>
            <template #remove="row">
                <td>
                  <b-button size="sm" v-on:click="removeRank($event, row.item.rankId)" variant="danger" class="mb-2">
                    <b-icon icon="trash-fill" aria-hidden="true"></b-icon>
                    <b-spinner v-if="isRemoveLoading && indexClicked === row.item.rankId" small></b-spinner>
                  </b-button>
                </td>
            </template>
            <template #detail="row">
                <td>
                  <b-button size="sm" v-on:click="detailRank($event, row.item.rankId)" variant="primary" class="mb-2">
                    <b-icon icon="info-circle-fill" aria-hidden="true"></b-icon>
                    <b-spinner v-if="isViewDetail && indexClicked === row.item.rankId" small></b-spinner>
                  </b-button>
                </td>
            </template>
            <template #index="item">
                <td class="py-2">
                    {{ item.index + 1 }}
                </td>
            </template>
        </CDataTable>
      </CCardBody>
    </CCard>
  </div>
</template>

<script>
  import axios from "../../../http-common";

  export default {
    name: "list-ranks",
    data() {
      return {
        title: "List Ranks",
        fields: [
                { key: 'remove', class: 'remove-class', label: ''},
                { key: 'detail', class: 'detail-class', label: ''},
                { key: 'index', label: 'No.', class: 'text-center'},
                { key: 'rankId', label: 'Rank ID ', class: 'rankId-class text-center', sortable: true},
                { key: 'rankName', label: 'Rank Name', class: 'rankName-class text-center', sortable: true},
                { key: 'storage', label: 'Storage', sortable: true, class: 'text-center'},
                { key: 'maxMember', label: 'Max Member', sortable: true, class: 'text-center'},
                { key: 'description', label: 'Description', tdClass: 'description-class', sortable: true, class: 'text-center'},
                { key: 'createdDate', label: 'Date Created', tdClass: 'date-class', sortable: true, class: 'text-center'},
                { key: 'updatedDate', label: 'Date Updated', tdClass: 'date-class', sortable: true, class: 'text-center'},
        ],
        items: [],
        itemsDetail: [], 
        isRemoveLoading: false,
        isViewDetail: false,
        indexClicked: -1,
        isBusy: true,
        msgErrors: "",
        msgSuccess: "",
        dismissCountDown: 0,
        dismissSecs: 4,
        redirect: null
      }
    },
    computed: {
     
    },
    mounted() {
      this.getListRanks();
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
      formatDate(date) {
          var d = new Date(date),
              month = '' + (d.getMonth() + 1),
              day = '' + d.getDate(),
              year = d.getFullYear();
          if (month.length < 2) month = '0' + month;  
          if (day.length < 2) day = '0' + day;
          return [year, month, day].join('/');
      },
      getListRanks(){
          axios.get("/admin/rank/list")
              .then(response => {
                if (response.data.success){
                  this.itemsDetail = response.data.data;
                  response.data.data.forEach(e => {
                    var teamRankDTO = { 
                        rankId: e.teamRankId,
                        rankName: e.teamRankName,
                        storage: e.storageCapacity,
                        maxMember: e.teamRankMemberLimit,
                        description: e.teamRankDescription == null ? "" : e.teamRankDescription.slice(0, 50).concat('...'),
                        createdDate: this.formatDate(e.createdDate),
                        updatedDate: this.formatDate(e.updatedDate)
                    }
                    this.items.push(Object.assign({}, teamRankDTO)); // set in here for using to fetch data into screen
                  });
                  this.isBusy = !this.isBusy;
                } else { // error
                  this.showAlertError(response.data.message);
                } 
              })
              .catch(e => {
                this.showAlertError(e);
              });
      },
      removeRank(e, rankId){
        this.$bvModal.msgBoxConfirm('Are you sure to delete this rank?', {
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
              this.indexClicked = rankId;
              var indexDelete = -1;
              this.items.forEach((item, i) => {
                  indexDelete = item.rankId === rankId ? i : indexDelete;
              });
              if (indexDelete != -1){          
                  // Call api delete
                  var token = localStorage.getItem('token'); 
                  const config = { 
                  headers: { Authorization: `Bearer ${token}` } 
                  }; 
                  var requestBody = {
                    "teamRankId": rankId
                  }
                  axios.post("/admin/rank/delete", requestBody, config)
                  .then(response => {
                      this.isRemoveLoading = false;
                      this.indexClicked = -1;
                      if (response.data.success){
                        this.items.splice(indexDelete, 1);
                        this.showAlertSuccess("Remove Rank Successfully!");
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
                  this.showAlertError("This Rank is not exist!");
              }
            }
          })
          .catch(err => {
            // An error occurred
            this.showAlertError(err);
        })
      },
      detailRank(e, rankId) {
        this.isViewDetail = true;
        this.indexClicked = rankId;
        this.redirect = setInterval(() => {
          if (this.itemsDetail.length != 0) {
              this.isViewDetail = false;
              this.indexClicked = -1;
              clearInterval(this.redirect);
              localStorage.setItem('ranksDetail', JSON.stringify(this.itemsDetail));
              this.$router.push({ path: `/rank/detail/${rankId}` });
          }
        }, 500);
      }
    },
    watch: {
     itemsDetail(){
        localStorage.setItem('ranksDetail', JSON.stringify(this.itemsDetail));
     }
    }
  }
</script>

<style>
    @import "../../assets/styles/main.css";
</style>
