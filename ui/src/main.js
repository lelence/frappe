import Vue from 'vue'
import App from './App.vue'
import router from './router';
import ElementUI from 'element-ui';
import 'normalize.css/normalize.css'
import 'element-ui/lib/theme-chalk/index.css';
import '@/styles/index.scss';
import '@/icons';


Vue.config.productionTip = false;
Vue.use(ElementUI);

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
