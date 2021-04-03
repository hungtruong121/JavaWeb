import '@babel/polyfill'
import 'mutationobserver-shim'
import 'core-js/stable'
import Vue from 'vue'
import './plugins/bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import { BootstrapVueIcons } from 'bootstrap-vue'
import 'bootstrap-vue/dist/bootstrap-vue-icons.min.css'
import App from './App'
import router from './router'
import CoreuiVue from '@coreui/vue'
import { iconsSet as icons } from './assets/icons/icons.js'
import store from './store'


Vue.config.performance = true
Vue.use(CoreuiVue)
Vue.use(BootstrapVueIcons)
Vue.prototype.$log = console.log.bind(console)

new Vue({
  el: '#app',
  router,
  store,
  icons,
  template: '<App/>',
  components: {
    App
  }
})
