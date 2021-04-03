<template>
    <div class="list row">
        <div class="col-md-6">
            <h4>Privacys List</h4>
            <ul>
                <li v-for="(privacy, index) in privacys" :key="index">
                    <router-link :to="{
                            name: 'privacy-details',
                            params: { privacy: privacy, privacyId: privacy.privacyId }
                        }">
                            {{privacy.privacyName}}
                    </router-link>
                </li>
            </ul>
        </div>
        <div class="col-md-6">
            <router-view @refreshData="refreshList"></router-view>
        </div>
    </div>
</template>
 
<script>
import http from "../http-common";
 
export default {
  name: "privacys-list",
  data() {
    return {
      privacys: []
    };
  },
  methods: {
    /* eslint-disable no-console */
    retrieveprivacys() {
      http
        .get("/privacys")
        .then(response => {
          this.privacys = response.data; // JSON are parsed automatically.
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },
    refreshList() {
      this.retrieveprivacys();
    }
    /* eslint-enable no-console */
  },
  mounted() {
    this.retrieveprivacys();
  }
};
</script>
 
<style>
.list {
  text-align: left;
  max-width: 450px;
  margin: auto;
}
</style>