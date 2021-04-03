import App from '@/App.vue'
import '@babel/polyfill'
import 'mutationobserver-shim'
import Vue from 'vue'
import './plugins/bootstrap-vue'
// import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import { BootstrapVueIcons } from 'bootstrap-vue'
import 'bootstrap-vue/dist/bootstrap-vue-icons.min.css'
import router from './router'
// import './permission'

Vue.use(BootstrapVueIcons)
Vue.config.productionTip = false

new Vue({
  router, // inject the router to make whole app router-aware
  render: h => h(App),
}).$mount('#app')
