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
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged"
        >
            {{ msgSuccess }}
        </b-alert>
      <CCardBody>
        <h1>Payment History</h1>
        <br />
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
            <template #index="item">
                <td>
                    {{ item.index + 1 }}
                </td>
            </template>
          <template #duration="{item}">
            <td class="py-2">{{ item.duration + " months" }}</td>
          </template>
          <template #transactionID="{item}">
            <td class="py-2">
              <CInput v-model="item.transactionID" :disabled="item.paymentMethod != 'Transfer'"></CInput>
            </td>
          </template>
          <template #status="{item}">
            <td class="py-2">
              <v-select :options="statusOption" v-model="item.status"></v-select>
            </td>
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
  name: "list-payment",
  components: {
    "v-select": vSelect,
  },
  data() {
    return {
      fields: [
        { key: 'index', label: 'No.',  _style: "width:1%",sortable: true, class: 'text-center'},
        { key: "invoiceID", _style: "min-width:200px" },
        { key: "promotionCode", _style: "min-width:200px" },
        { key: "teamName", _style: "min-width:200px" },
        { key: "serviceName", _style: "min-width:200px" },
        { key: "duration", _style: "min-width:200px" },
        { key: "paymentMethod", _style: "min-width:200px" },
        { key: "amount", _style: "min-width:200px" },
        { key: "paymentDay", _style: "min-width:200px" },
        { key: "transactionID", _style: "min-width:200px" },
        { key: "status", _style: "min-width:250px" },
        { key: "errorCode", _style: "min-width:200px" },
        {
          key: "update-Payment",
          label: "",
          _style: "width:1%",
          sorter: false,
          filter: false,
        },
      ],
      items: [],
      statusOption: ["FAILURE", "WAITING CONFIRM", "SUCCESSFUL"],
      itemsDetail: [],
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
  },
  mounted() {
    this.getListPayment();
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
    getListPayment() {
      axios
        .get("/admin/paymenthistory/list")
        .then((response) => {
          if (response.data.success) {
            this.itemsDetail = response.data.data;
            response.data.data.forEach((e) => {
              var paymentDTO = {
                paymentHistoryId: e.paymentHistoryId,
                invoiceID: e.invoiceId,
                promotionCode: e.promotionCode,
                teamId: e.teamId,
                teamName: e.teamName,
                serviceName: e.serviceName,
                duration: e.duration,
                from: "",
                to: "",
                paymentMethod: e.paymentMethod,
                amount: e.amount,
                paymentDay: this.formatDate(e.createdDate),
                transactionID: e.transactionId,
                status: e.statusName,
                errorCode: e.errorCode,
              };
              this.items.push(Object.assign({}, paymentDTO));
            });
            this.isBusy = !this.isBusy;
          } else {
            this.showAlertError(response.data.message);
          }
        })
        .catch((e) => {
          this.showAlertError(e);
        });
    },
    updatePayment(e, paymentHistoryId) {
      var payment = {};
      var index = -1;
      this.items.forEach((el, i) => {
        if (el.paymentHistoryId == paymentHistoryId) {
          index = i;
          payment = el;
        }
      });
      if (index != -1) {
        if (payment.status == null)
          return this.showAlertError("Payment Status can't be null!");
        var bodyRequest = {
          paymentHistoryId: payment.paymentHistoryId,
          invoiceId: payment.invoiceID,
          promotionCode: payment.promotionCode,
          teamId: payment.teamId,
          serviceName: payment.serviceName,
          duration: payment.duration,
          paymentMethod: payment.paymentMethod,
          amount: payment.amount,
          transactionId: payment.transactionID,
          statusId:
            payment.status == "FAILURE"
              ? 11
              : payment.status == "SUCCESSFUL"
              ? 10
              : 12,
          errorCode: payment.errorCode,
        };
        axios
          .post("/admin/paymenthistory/update", bodyRequest)
          .then((response) => {
            if (response.data.success) {
              this.showAlertSuccess(response.data.message);
            } else {
              // error
              this.showAlertError(response.data.message);
            }
          })
          .catch((e) => {
            this.showAlertError(e);
          });
      } else {
        this.showAlertError("This payment not exist!");
      }
    },
  },
};
</script>
<style>
    @import "../../assets/styles/main.css";
</style>