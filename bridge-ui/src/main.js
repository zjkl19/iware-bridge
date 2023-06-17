import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios';
import * as echarts from 'echarts';
import Utils from './utils/utils';
import { message } from '@/utils/resetMessage';
import animated from 'animate.css';

import '@/assets/css/index.css';
import '@/assets/css/elStyle.scss';
import '@/assets/css/public.scss';
import '@/assets/iconfont/iconfont.css';

import AmapVueConfig from '@amap/amap-vue/lib/config';
AmapVueConfig.key = '0ab19c0466870f9dcf216fee401b4b04';

Vue.use(ElementUI);
Vue.use(animated);

Vue.config.productionTip = false;
Vue.prototype.$echarts = echarts;
Vue.prototype.$http = axios;
Vue.prototype.$utils = Utils;
Vue.prototype.$message = message;
Vue.prototype.$basePath = '/bridge/static/';

new Vue({
  router,
  store,
  render: (h) => h(App)
}).$mount('#app');
