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
                    <b-button id="block-team" size="sm" v-on:click="blockTeam($event, row.item.teamId, row.item.status)" variant="danger" class="mb-2">
                        <b-icon icon="lock-fill" v-if="row.item.status == 'Active'" aria-hidden="true"></b-icon>
                        <b-icon icon="unlock-fill" v-if="row.item.status == 'Blocked'" aria-hidden="true"></b-icon>
                        <b-spinner v-if="isBlockLoading && indexClicked === row.item.teamId" small></b-spinner>
                    </b-button>
                    <b-tooltip v-show="row.item.status == 'Active'" target="block-team">Block Team</b-tooltip>
                </td>
            </template>
            <template #detail="row">
                <td>
                    <b-button id="team-detail" size="sm" v-on:click="detailTeam($event, row.item.teamId)" variant="primary" class="mb-2">
                        <b-icon icon="info-circle-fill" aria-hidden="true"></b-icon>
                        <b-spinner v-if="isViewDetail && indexClicked === row.item.teamId" small></b-spinner>
                    </b-button>
                    <b-tooltip target="team-detail">Team Detail</b-tooltip>
                </td>
            </template>
            <template #paymentHistory="row">
                <td>
                    <b-button id="payment-history" size="sm" :to="'/payment/payment/' + row.item.teamId" variant="primary" class="mb-2">
                        <b-icon icon="credit-card" aria-hidden="true"></b-icon>
                    </b-button>
                    <b-tooltip target="payment-history">View payment history</b-tooltip>
                </td>
            </template>
            <template #index="item">
                <td>
                    {{ item.index + 1 }}
                </td>
            </template>
            <template #status="row">
                <td class="py-2">
                    <div v-if="row.item.status == 'Blocked'">
                        <b-badge pill variant="danger">Blocked</b-badge>
                    </div>
                    <div v-if="row.item.status == 'Active'">
                        <b-badge pill variant="success">Active</b-badge>
                    </div>
                </td>
            </template>
             <template #rank="row">
                <td class="py-2">
                    <div v-if="row.item.rank == 1">
                        <b-badge pill variant="primary">Basic</b-badge>
                    </div>
                    <div v-if="row.item.rank == 2">
                        <b-badge pill variant="light">Silver</b-badge>
                    </div>
                    <div v-if="row.item.rank == 3">
                        <b-badge pill variant="warning">Gold</b-badge>
                    </div>
                    <div v-if="row.item.rank == 4">
                        <b-badge pill variant="success">Diamond</b-badge>
                    </div>
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
    name: "list-teams",
    data() {
        return {
            title: "List Teams",
            fields: [
                { key: 'remove', class: 'text-center', label: ''},
                { key: 'detail', class: 'text-center', label: ''},
                { key: 'paymentHistory', class: 'text-center', label: ''},
                { key: 'index', label: 'No.', sortable: true, class: 'text-center'},
                { key: 'teamId', label: 'Team ID', sortable: true, class: 'text-center'},
                { key: 'teamName', label: 'Team Name', _style: "min-width:200px" },
                { key: 'shortName', label: 'Short Name', sortable: true},
                { key: 'sportName', label: 'Sport Name', _style: "min-width:120px" },
                { key: 'sologan', label: 'Sologan', _style: "min-width:200px" },
                { key: 'teamEmail', label: 'Team Email', sortable: true},
                { key: 'national', label: 'National', sortable: true},
                { key: 'location', label: 'Location', _style: "min-width:200px" },
                { key: 'members', label: 'Members', sortable: true, class: 'text-center'},
                { key: 'description', label: 'Description', _style: "min-width:220px" },
                { key: 'rank', label: 'Rank', sortable: true, class: 'text-center'},
                { key: 'status', label: 'Status', sortable: true, class: 'text-center'},
                { key: 'dateCreated', label: 'Date Created', sortable: true},
                { key: 'dateUpdated', label: 'Date Updated', sortable: true},
            ],
            items: [],
            itemsDetail: [],
            isBlockLoading: false,
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
        getListTeams(){
            this.items = [];
            var listTeams = JSON.parse(localStorage.getItem('listTeams'));
            if (listTeams && listTeams != null){
                setTimeout(() => {
                    if (this.items.length == 0) {
                        listTeams.forEach(team => {
                            this.items.push(Object.assign({}, team));
                        });
                        this.totalRows = this.items.length;
                        this.isBusy = false;
                    }
                }, 2000);
            }
            axios.get("/team/admin/all")
                .then(response => {
                    if (response.data.success){
                        this.items = [];
                        this.isBusy = true;
                        this.itemsDetail = response.data.data;
                        response.data.data.forEach(e => {
                            var teamDTO = {
                                teamId: e.teamId,
                                status: e.isActive == 0 ? 'Blocked' : 'Active',
                                teamName: e.teamName,
                                shortName: e.teamShortName,
                                sportName: e.sportName,
                                sologan: e.teamSlogan,
                                teamEmail: e.teamMail,
                                national: e.teamNational,
                                location: e.teamAddress,
                                members: e.totalMember,
                                description: e.teamDescription.slice(0, 50).concat('...'),
                                rank: e.teamRankId,
                                dateCreated: this.formatDate(e.createdDate),
                                dateUpdated: this.formatDate(e.updatedDate)
                            }
                            teamDTO._classes = teamDTO.status == 'Active' ? '' : [
                                teamDTO._classes, "table-danger"
                            ]
                            this.items.push(Object.assign({}, teamDTO));
                        });
                        this.totalRows = this.items.length;
                        localStorage.setItem('listTeams', JSON.stringify(this.items));
                        this.isBusy = false;
                    } else { // error
                        this.showAlertError(response.data.message);
                    }
                })
                .catch(e => {
                    this.showAlertError(e);
                });
        },
        blockTeam(e, id, status){
            const h = this.$createElement;
            var msg = (status == 'Blocked') ? h('p', { class: ['text-center'] }, [ 'Are you sure to ',
             h('strong', 'UnBlock'), ' this team?',]) : h('p', { class: ['text-center'] }, [ 'Are you sure to ',
             h('strong', 'Block'), ' this team?',]);

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
                    this.isBlockLoading = true;
                    this.indexClicked = id;
                    var indexDelete = -1;
                    this.items.forEach((item, i) => {
                        indexDelete = item.teamId === id ? i : indexDelete;
                    });
                    if (indexDelete != -1){
                        // Call api block
                        axios.get("/team/admin/block?teamId=" + id)
                        .then(response => {
                            this.isBlockLoading = false;
                            this.indexClicked = -1;
                            if (response.data.success){
                                // Change row hightlight
                                this.items.forEach(el => {
                                    if (el.teamId === id) {
                                        el.status = status == 'Blocked' ? 'Active' : 'Blocked';
                                        // el._rowVariant = status == 'Blocked' ? '' : 'danger';
                                        el._classes = status == 'Blocked' ? '' : [
                                            el._classes, "table-danger"
                                        ]
                                    }
                                });
                                this.itemsDetail.forEach(el => {
                                    if (el.teamId === id) {
                                        el.isActive = status == 'Blocked' ? 1 : 0;
                                    }
                                });

                                this.showAlertSuccess(response.data.message);
                            } else {
                                this.showAlertError(response.data.message);
                            }
                        })
                        .catch(e => {
                            this.isBlockLoading = false;
                            this.indexClicked = -1;
                            this.showAlertError(e);
                        });
                    }
                }
            })
            .catch(err => {
                // An error occurred
                this.showAlertError(err);
            })
        },
        detailTeam(e, id) {
            // Redirect to TeamUpdate.vue
            this.isViewDetail = true;
            this.indexClicked = id;
            this.redirect = setInterval(() => {
                if (this.itemsDetail.length != 0) {
                    this.isViewDetail = false;
                    this.indexClicked = -1;
                    clearInterval(this.redirect);
                    localStorage.setItem('teamsDetail', JSON.stringify(this.itemsDetail));
                    this.$router.push({ path: `/team/detail/${id}` });
                }
            }, 500);
        }
    },
    mounted() {
      this.getListTeams();
    },
    computed: {
    },
    watch: {
       itemsDetail(){
           localStorage.setItem('teamsDetail', JSON.stringify(this.itemsDetail));
       }
    }
}
</script>

<style>
    @import "../../assets/styles/main.css";
</style>