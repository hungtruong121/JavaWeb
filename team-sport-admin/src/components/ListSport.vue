<template>
  <b-container fluid>
    <!-- <b-row> -->
        <h2 class="title">List Sport</h2>
    <!-- </b-row> -->
    <!-- User Interface controls -->
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
    <b-row>
      <b-col lg="6" class="my-1">
        <b-form-group
          label="Sort"
          label-cols-sm="3"
          label-align-sm="right"
          label-size="sm"
          label-for="sortBySelect"
          class="mb-0"
        >
          <b-input-group size="sm">
            <b-form-select v-model="sortBy" id="sortBySelect" :options="sortOptions" class="w-75">
              <template v-slot:first>
                <option value="">-- none --</option>
              </template>
            </b-form-select>
            <b-form-select v-model="sortDesc" size="sm" :disabled="!sortBy" class="w-25">
              <option :value="false">Asc</option>
              <option :value="true">Desc</option>
            </b-form-select>
          </b-input-group>
        </b-form-group>
      </b-col>

      <b-col lg="6" class="my-1">
        <b-form-group
          label="Initial sort"
          label-cols-sm="3"
          label-align-sm="right"
          label-size="sm"
          label-for="initialSortSelect"
          class="mb-0"
        >
          <b-form-select
            v-model="sortDirection"
            id="initialSortSelect"
            size="sm"
            :options="['asc', 'desc', 'last']"
          ></b-form-select>
        </b-form-group>
      </b-col>

      <b-col lg="6" class="my-1">
        <b-form-group
          label="Filter"
          label-cols-sm="3"
          label-align-sm="right"
          label-size="sm"
          label-for="filterInput"
          class="mb-0"
        >
          <b-input-group size="sm">
            <b-form-input
              v-model="filter"
              type="search"
              id="filterInput"
              placeholder="Type to Search"
            ></b-form-input>
            <b-input-group-append>
              <b-button :disabled="!filter" @click="filter = ''">Clear</b-button>
            </b-input-group-append>
          </b-input-group>
        </b-form-group>
      </b-col>

      <b-col lg="6" class="my-1">
        <b-form-group
          label="Filter On"
          label-cols-sm="3"
          label-align-sm="right"
          label-size="sm"
          description="Leave all unchecked to filter on all data"
          class="mb-0">
          <b-form-checkbox-group v-model="filterOn" class="mt-1">
            <b-form-checkbox value="name">Name</b-form-checkbox>
            <b-form-checkbox value="positionsLevels">Positions</b-form-checkbox>
            <b-form-checkbox value="competition">Competition</b-form-checkbox>
            <b-form-checkbox value="matchType">MatchType</b-form-checkbox>
            <b-form-checkbox value="halfSetRound">HalfSetRound</b-form-checkbox>
            <b-form-checkbox value="age">Age</b-form-checkbox>
            <b-form-checkbox value="gender">Gender</b-form-checkbox>
            <b-form-checkbox value="description">Description</b-form-checkbox>
            <b-form-checkbox value="createdDate">CreatedDate</b-form-checkbox>
            <b-form-checkbox value="updatedDate">UpdatedDate</b-form-checkbox>
          </b-form-checkbox-group>
          <div class="input-group-sm">
            <b-button v-on:click="filterOn = []">Clear all filter</b-button>
          </div>
            
        </b-form-group>
        
      </b-col>
    </b-row>

    <!-- Main table element -->
    <b-table
      responsive
      outlined
      :busy="isBusy"
      :items="items"
      :fields="fields"
      :current-page="currentPage"
      :per-page="perPage"
      :filter="filter"
      :filterIncludedFields="filterOn"
      :sort-by.sync="sortBy"
      :sort-desc.sync="sortDesc"
      :sort-direction="sortDirection"
      @filtered="onFiltered"
    >
      <template v-slot:table-busy>
        <div class="text-center text-danger my-2">
          <b-spinner class="align-middle"></b-spinner>
          <strong>Loading...</strong>
        </div>
      </template>

      <template v-slot:cell(remove)="row">
         <b-button size="sm" v-on:click="removeSport($event, row.item.id)" variant="danger" class="mb-2">
            <b-icon icon="trash-fill" aria-hidden="true"></b-icon>
        </b-button>
      </template>

      <template v-slot:cell(detail)="row">
         <b-button size="sm" v-on:click="detailSport($event, row.item.id)" variant="primary" class="mb-2">
            <b-icon icon="info-circle-fill" aria-hidden="true"></b-icon>
        </b-button>
      </template>

<!--       
      <template v-slot:table-colgroup="scope">
        <col
          v-for="field in scope.fields"
          :key="field.key"
          :style="{ width: field.key === 'description' ? '400px' : 'auto' }"
        >
      </template> -->

      <!-- <template v-slot:cell(index)="row">
        {{ row.index + 1 }}
      </template>


      <template v-slot:cell(name)="row">
          {{ row.item.sportName }}
      </template>

      <template v-slot:cell(matchType)="row">
          {{ row.item.matchType }}: {{row.item.matchTypeValue}}
      </template>

       <template v-slot:cell(halfSetRound)="row">
          {{ row.item.sportRound[0] }}
      </template>

      <template v-slot:cell(age)="row">
          {{row.item.ageFrom}} - {{row.item.ageTo}}
      </template>

      <template v-slot:cell(actions)="row">
        <b-button size="sm" @click="info(row.item, row.index, $event.target)" class="mr-1">
          Info modal
        </b-button>
        <b-button size="sm" @click="row.toggleDetails">
          {{ row.detailsShowing ? 'Hide' : 'Show' }} Details
        </b-button>
      </template>

      <template v-slot:row-details="row">
        <b-card>
          <ul>
            <li v-for="(value, key) in row.item" :key="key">{{ key }}: {{ value }}</li>
          </ul>
        </b-card>
      </template> -->
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
    <!-- Info modal -->
    <b-modal :id="infoModal.id" :title="infoModal.title" ok-only @hide="resetInfoModal">
      <pre>{{ infoModal.content }}</pre>
    </b-modal>
  </b-container>
</template>

<script>
  import http from "../http-common";

  export default {
    data() {
      return {
        fields: [
            { key: 'remove', class: 'remove-class', label: ''},
            { key: 'detail', class: 'detail-class', label: ''},
            { key: 'index', label: 'No.', sortable: true, class: 'text-center'},
            { key: 'name', label: 'Name Sport ', class: 'name-class', sortable: true},
            { key: 'positionsLevels', label: 'Positions/Levels', class: 'position-class', sortable: true},
            { key: 'competition', label: 'Competition', sortable: true},
            { key: 'matchType', label: 'Match Type', sortable: true},
            { key: 'halfSetRound', label: 'Half/Set/Round', sortable: true},
            { key: 'gender', label: 'Gender', sortable: true},
            { key: 'age', label: 'Age', sortable: true, class: 'text-center' },
            { key: 'description', label: 'Description', class: 'description-class', sortable: true},
            { key: 'createdDate', label: 'Date Created', sortable: true},
            { key: 'updatedDate', label: 'Date Updated', sortable: true},
        ],
        items: [],
        itemsDetail: [], 
        totalRows: 1,
        currentPage: 1,
        perPage: 5,
        pageOptions: [5, 10, 15],
        sortBy: '',
        sortDesc: false,
        sortDirection: 'asc',
        filter: null,
        filterOn: [],
        infoModal: {
          id: 'info-modal',
          title: '',
          content: ''
        },
        isBusy: true,
        msgErrors: "",
        msgSuccess: "",
        dismissCountDown: 0,
        dismissSecs: 4,
      }
    },
    computed: {
      sortOptions() {
        // Create an options list from our fields
        return this.fields
          .filter(f => f.sortable)
          .map(f => {
            return { text: f.label, value: f.key }
          })
      }
    },
    mounted() {
      // Set the initial number of items
      this.totalRows = this.items.length;
      this.getListSports();
    },
    methods: {
      info(item, index, button) {
        this.infoModal.title = `Row index: ${index}`
        this.infoModal.content = JSON.stringify(item, null, 2)
        this.$root.$emit('bv::show::modal', this.infoModal.id, button)
      },
      resetInfoModal() {
        this.infoModal.title = ''
        this.infoModal.content = ''
      },
      onFiltered(filteredItems) {
        // Trigger pagination to update the number of buttons/pages due to filtering
        this.totalRows = filteredItems.length
        this.currentPage = 1
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
      getListSports(){
          http.get("/sport/sports")
              .then(response => {
                console.log(response.data.data);
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
                        description: e.description,
                        createdDate: e.createdDate,
                        updatedDate: e.updatedDate
                    }
                    this.items.push(Object.assign({}, sportDTO));
                  });
                  this.isBusy = !this.isBusy;
                } else { // error
                  this.showAlertError(response.data.message);
                }
              })
              .catch(e => {
                console.log(e);
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
                console.log("Remove sport id:", id);
                var indexDelete = -1;
                this.items.forEach((item, i) => {
                    indexDelete = item.id === id ? i : indexDelete;
                });
                if (indexDelete != -1){
                    this.items.splice(indexDelete, 1);
                    // Call api delete
                    http.get("/sport/delete?sportId=" + id)
                      .then(response => {
                        if (response.data.success){
                          this.showAlertSuccess("Remove Sport Successfully!");
                        } else {
                          this.showAlertError(response.data.message);
                        }
                        console.log(response.data.data);
                        // this.$emit("refreshData");
                    })
                    .catch(e => {
                      console.log(e);
                      this.showAlertError(e);
                    });

                }
            }
          })
          .catch(err => {
            // An error occurred
            console.log("Have an error when remove sport", err);
        })
      },
      detailSport(e, id) {
        // Get sport in items with id = id
        var sportDetail = {};
        this.itemsDetail.forEach((item) => {
            sportDetail = item.sportId === id ? item : sportDetail;
        });
        // Redirec to SportDefinition.vue
        console.log("View detail sport:", sportDetail);
        this.$router.push({ path: `/sport/detail/${id}` });
      }
    }
  }
</script>

<style>
    /* h2 {
        font-size: 24px;
        color: #525252;
        font-weight: bold;
        text-transform: uppercase;
        margin-bottom: 40px;
        margin-top: 20px;
        text-align: center;
    } */
    .description-class { max-width: 500px; }
    /* .no-class { max-width: 60px; }
    .remove-class { max-width: 50px; }
    .detail-class { max-width: 50px; }
    .name-class { width: 100px; max-width: 130px;}
    .position-class { width: 150px; max-width: 200px;} */
</style>
