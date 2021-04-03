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
          :items="itemsSport"
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
              <b-button size="sm" v-on:click="removeSport($event, row.item.id)" variant="danger" class="mb-2">
                  <b-icon icon="trash-fill" aria-hidden="true"></b-icon>
                  <b-spinner v-if="isRemoveLoading && indexClicked === row.item.id" small></b-spinner>
              </b-button>
             </td>
          </template>
          <template #detail="row">
            <td>
              <b-button size="sm" v-on:click="detailSport($event, row.item.id)" variant="primary" class="mb-2">
                  <b-icon icon="info-circle-fill" aria-hidden="true"></b-icon>
                  <b-spinner v-if="isViewDetail && indexClicked === row.item.id" small></b-spinner>
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
    name: "list-sports",
    data() {
      return {
        title: "List Sports",
        fields: [
            { key: 'remove', label: '', sorter: false, filter: false},
            { key: 'detail', label: '', sorter: false, filter: false},
            { key: 'index', label: 'No.', sorter: false, filter: false},
            { key: 'name', label: 'Name Sport ', _style: "min-width:200px" },
            { key: 'positionsLevels', label: 'Positions/Levels', _style: "min-width:200px" },
            { key: 'competition', label: 'Competition', _style: "min-width:200px" },
            { key: 'matchType', label: 'Match Type', _style: "min-width:200px" },
            { key: 'halfSetRound', label: 'Half/Set/Round', _style: "min-width:200px" },
            { key: 'gender', label: 'Gender', _style: "min-width:200px" },
            { key: 'age', label: 'Age (From-To)', _style: "min-width:200px" },
            { key: 'description', label: 'Description', _style: "min-width:200px" },
            { key: 'createdDate', label: 'Date Created', _style: "min-width:200px" },
            { key: 'updatedDate', label: 'Date Updated', _style: "min-width:200px" },
        ],
        itemsSport: [],
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
      this.getListSports();
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
          return [day, month, year].join('/');
      },
      getListSports(){
          axios.get("/sport/sports")
              .then(response => {
                if (response.data.success){
                  this.itemsDetail = response.data.data;
                  response.data.data.forEach(e => {
                    var sportDTO = { 
                        id: e.sportId,
                        name: e.sportName,
                        positionsLevels: e.positionsLevels,
                        competition: e.competition,
                        matchType: e.matchType + ' - ' +  e.matchTypeValue,
                        halfSetRound: e.sportRound.length != 0 ? e.sportRound[0].sportRoundType : "",
                        gender: e.gender,
                        age: e.ageFrom + ' - ' +  e.ageTo,
                        description: e.description.slice(0, 50).concat('...'),
                        createdDate: this.formatDate(e.createdDate),
                        updatedDate: this.formatDate(e.updatedDate)
                    }
                    this.itemsSport.push(Object.assign({}, sportDTO));
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
      removeSport(e, id){
        this.$bvModal.msgBoxConfirm('Are you sure to delete this sport?', {
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
              this.itemsSport.forEach((item, i) => {
                  indexDelete = item.id === id ? i : indexDelete;
              });
              if (indexDelete != -1){
                  // Call api delete
                  axios.get("/sport/delete?sportId=" + id)
                    .then(response => {
                      this.isRemoveLoading = false;
                      this.indexClicked = -1;
                      if (response.data.success){
                        this.itemsSport.splice(indexDelete, 1);
                        this.showAlertSuccess("Remove Sport Successfully!");
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
                  this.showAlertError("This sport is not exist!");
              }
            }
          })
          .catch(err => {
            // An error occurred
            this.showAlertError(err);
        })
      },
      detailSport(e, id) {
        // Redirec to SportDefinition.vue
        this.isViewDetail = true;
        this.indexClicked = id;
        this.redirect = setInterval(() => {
          if (this.itemsDetail.length != 0) {
              this.isViewDetail = false;
              this.indexClicked = -1;
              clearInterval(this.redirect);
              localStorage.setItem('sportsDetail', JSON.stringify(this.itemsDetail));
              this.$router.push({ path: `/sport/detail/${id}` });
          }
        }, 500);
      }
    },
    watch: {
      itemsDetail(){
        localStorage.setItem('sportsDetail', JSON.stringify(this.itemsDetail));
      }
    }
  }
</script>

<style>
    @import "../../assets/styles/main.css";
</style>
