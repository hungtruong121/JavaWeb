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
                    <b-button size="sm" v-on:click="blockUser($event, row.item.userId, row.item.status)" variant="danger" class="mb-2">
                        <b-icon icon="lock-fill"  v-if="row.item.status != 'Locked'" aria-hidden="true"></b-icon>
                        <b-icon icon="unlock-fill" v-if="row.item.status == 'Locked'" aria-hidden="true"></b-icon>
                        <b-spinner v-if="isBlockLoading && indexClicked === row.item.userId" small></b-spinner>
                    </b-button>
                </td>
            </template>
            <template #detail="row">
                <td>
                    <b-button size="sm" v-on:click="detailUser($event, row.item.userId)" variant="primary" class="mb-2">
                        <b-icon icon="info-circle-fill" aria-hidden="true"></b-icon>
                        <b-spinner v-if="isViewDetail && indexClicked === row.item.userId" small></b-spinner>
                    </b-button>
                </td>
            </template>
            <template #joined="row">
                <td class="py-2">
                    <b-link v-for="(team, index) in row.item.joined" v-bind:key="index" :href="'/#/team/detail/' + team.teamId">{{ team.teamName }} </b-link>
                </td>
            </template>
            <template #status="row">
                <td class="py-2">
                    <div v-if="row.item.status == 'Locked'">
                        <b-badge pill variant="danger">Locked</b-badge>
                    </div>
                    <div v-if="row.item.status == 'Active'">
                        <b-badge pill variant="success">Active</b-badge>
                    </div>
                    <div v-if="row.item.status == 'Non-Active'">
                        <b-badge pill variant="warning">Non-Active</b-badge>
                    </div>
                </td>
            </template>
            <template #index="item">
                <td>
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
    name: "list-users",
    data() {
        return {
            title: "List Users",
            fields: [
                { key: 'remove', class: 'remove-class', label: ''},
                { key: 'detail', class: 'detail-class', label: ''},
                { key: 'index', label: 'No.', sortable: true, class: 'text-center'},
                { key: 'fullName', label: 'Full Name ', _style: "min-width:200px" },
                { key: 'email', label: 'Email', class: 'email-class', sortable: true},
                { key: 'gender', label: 'Gender', sortable: true, class: 'text-center'},
                { key: 'birthDate', label: 'Date of Birth', _style: "min-width:150px" },
                { key: 'nationality', label: 'Nationality', sortable: true},
                { key: 'phone', label: 'Phone', sortable: true, class: 'text-center' },
                { key: 'address', label: 'Address', _style: "min-width:250px" },
                { key: 'weight', label: 'Weight (KG)', sortable: true, class: 'text-center'},
                { key: 'height', label: 'Height (CM)', sortable: true, class: 'text-center'},
                { key: 'preferredHand', label: 'Preferred Hand', sortable: true},
                { key: 'preferredFoot', label: 'Preferred Foot', sortable: true},
                { key: 'joined', label: 'Teams Join', _style: "min-width:250px" },
                { key: 'status', label: 'Status', sortable: true, class: 'text-center'},
                { key: 'dateCreated', label: 'Date Created', tdClass: 'date-class', sortable: true},
                { key: 'dateUpdated', label: 'Date Updated', tdClass: 'date-class', sortable: true},
            ],
            teamsJoined: [],
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
            redirect: null,
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
        getListUsers(){
            this.items = [];
            var listUsers = JSON.parse(localStorage.getItem('listUsers'));
            if (listUsers && listUsers != null){
                setTimeout(() => {
                    if (this.items.length == 0) {
                        listUsers.forEach(user => {
                            this.items.push(Object.assign({}, user));
                        });
                        this.totalRows = this.items.length;
                        this.isBusy = false;
                    }
                }, 2000);
            }
            axios.get("/user/admin/all")
                .then(response => {
                    if (response.data.success){
                        this.items = [];
                        this.isBusy = true;
                        this.itemsDetail = response.data.data;
                        response.data.data.forEach(e => {
                            var gender = "";
                            switch (e.userGender) {
                                 case "0": gender = "Other"
                                    break;
                                case "1": gender = "Male"
                                    break;
                                case "2": gender = "Female"
                                    break;
                                default:
                                    gender = "Other"
                                    break;
                            };
                            var teams = [];
                            e.teamsJoined.forEach(el => {
                                teams.push(Object.assign({}, {teamId: el.teamId, teamName: el.teamName}));
                            });
                            var userDTO = { 
                                userId: e.userId,
                                fullName: e.userFullName,
                                email: e.userMail,
                                gender: gender,
                                birthDate: e.userBirthDay == null ? "" : e.userBirthDay,
                                nationality: e.userNational == null ? "" : e.userNational,
                                phone: e.userPhone == null ? "" : e.userPhone,
                                address: e.userAddress == null ? "" : e.userAddress,
                                weight: e.userWeight == null ? "" : e.userWeight,
                                height: e.userHeight == null ? "" : e.userHeight,
                                preferredHand: e.userPreferredHand == null ? "" : e.userPreferredHand,
                                preferredFoot: e.userPreferredFoot == null ? "" : e.userPreferredFoot,
                                joined: teams,
                                status: e.status == 0 ? 'Locked' : e.status == 1 ? 'Active' : 'Non-Active',
                                dateCreated: this.formatDate(e.createdDate),
                                dateUpdated: this.formatDate(e.updatedDate)
                            }
                            if (userDTO.status == 'Locked'){
                                userDTO._classes = [ userDTO._classes, "table-danger" ]
                            }
                            if (userDTO.status == 'Non-Active'){
                                userDTO._classes = [ userDTO._classes, "table-warning" ]
                            }
                            this.items.push(Object.assign({}, userDTO));
                        });
                        this.totalRows = this.items.length;
                        localStorage.setItem('listUsers', JSON.stringify(this.items));
                        this.isBusy = false;
                    } else { // error
                        this.showAlertError(response.data.message);
                    }
                })
                .catch(e => {
                    this.showAlertError(e);
                });
        },
        blockUser(e, id, status){
            const h = this.$createElement;
            var msg = (status == 'Locked') ? h('p', { class: ['text-center'] }, [ 'Are you sure to ',
             h('strong', 'UnBlock'), ' this user?',]) : h('p', { class: ['text-center'] }, [ 'Are you sure to ',
             h('strong', 'Block'), ' this user?',]);

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
                        indexDelete = item.userId === id ? i : indexDelete;
                    });
                    if (indexDelete != -1){
                        // Call api delete
                        axios.get("/user/admin/block?userId=" + id)
                        .then(response => {
                            this.isBlockLoading = false;
                            this.indexClicked = -1;
                            if (response.data.success){
                                // Change row hightlight
                                this.items.forEach(el => {
                                    if (el.userId === id) {
                                        el.status = status == 'Locked' ? 'Active' : 'Locked';
                                        el._classes = status == 'Locked' ? '' : [ el._classes, "table-danger" ];
                                    }
                                });
                                this.itemsDetail.forEach(el => {
                                    if (el.userId === id) {
                                        el.status = status == 'Locked' ? 1 : 0;
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
        detailUser(e, id) {
            // Redirect to UserDetails.vue
            this.isViewDetail = true;
            this.indexClicked = id;
            this.redirect = setInterval(() => {
                if (this.itemsDetail.length != 0) {
                    this.isViewDetail = false;
                    this.indexClicked = -1;
                    clearInterval(this.redirect);
                    localStorage.setItem('usersDetail', JSON.stringify(this.itemsDetail));
                    this.$router.push({ path: `/user/detail/${id}` });
                }
            }, 500);
        }
    },
    mounted() {
      this.getListUsers();
    },
    computed: {
 
    },
    watch: {
       itemsDetail(){
           localStorage.setItem('usersDetail', JSON.stringify(this.itemsDetail));
       }
    }
}
</script>

<style>
    @import "../../assets/styles/main.css";
</style>
