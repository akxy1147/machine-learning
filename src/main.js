import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/globle.css'
import * as echarts from 'echarts'
import request from "@/utils/request";
// main.js全局注册
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
// use
Vue.use(mavonEditor)
Vue.prototype.$echarts = echarts
Vue.config.productionTip = false
Vue.use(ElementUI);

// use
Vue.prototype.request=request

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
