import Vue from 'vue'
import App from './App.vue'
import router from './router';
import ElementUI from 'element-ui';
import 'normalize.css/normalize.css'
import 'element-ui/lib/theme-chalk/index.css';
import '@/styles/index.scss';
import '@/icons';

// https://juejin.im/post/59097cd7a22b9d0065fb61d2
// https://github.com/varHarrie/varharrie.github.io/issues/10
Vue.config.productionTip = false;
Vue.use(ElementUI);
// https://github.com/vuejs/vue-docs-zh-cn/blob/master/vue-cli-plugin-babel/README.md
new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
