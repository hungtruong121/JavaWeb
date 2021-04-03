import Vue from "vue";
import Router from "vue-router";
// import PrivacysList from "./components/PrivacysList.vue";
import AddPrivacy from "./components/AddPrivacy.vue";
// import Privacy from "./components/Privacy.vue";
import Login from "@/components/LoginComp/Login"
import ChangePass from "@/components/LoginComp/ChangePass"
// import PrivacysList from "./components/PrivacysList.vue";
// import AddPrivacy from "./components/AddPrivacy.vue";
// import Privacy from "./components/Privacy.vue";
import SportDefinition from './components/SportDefinition.vue'
import ListSport from './components/ListSport.vue'
 
Vue.use(Router);
 
export default new Router({
  mode: "history",
  routes: [
    {
      path: "/sports",
      name: "list-sport",
      alias: "/sport",
      component: ListSport,
    },
    {
      path: "/add",
      name: "add",
      component: AddPrivacy
    },
    {
      path: "/login",
      name: "login",
      component: Login
    },
    {
      path: "/changepass",
      name: "changepass",
      component: ChangePass
    },
    {
      path: "/sport/create",
      name: "add-sport",
      component: SportDefinition
    },
    {
      path: "/sport/detail/:id",
      name: "sport-detail",
      component: SportDefinition
    },
    // {
    //   path: "/",
    //   name: "privacys",
    //   alias: "/privacy",
    //   component: PrivacysList,
    //   children: [
    //     {
    //       path: "/privacy/:privacyId",
    //       name: "privacy-details",
    //       component: Privacy,
    //       props: true
    //     }
    //   ]
    // },
    // {
    //   path: "/add",
    //   name: "add",
    //   component: AddPrivacy
    // }
  ]
});
