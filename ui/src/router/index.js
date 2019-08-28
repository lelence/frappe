import Vue from 'vue';
import Router from 'vue-router';

import routes from './routes';

Vue.use(Router);

const router = new Router({
  mode: 'history',
  routes
});

// router.beforeEach((to, from, next) => {

//   if (to.path === '/')
//     next({
//       path: '/login'
//     });
//   else
//     next();

// });

export default router;