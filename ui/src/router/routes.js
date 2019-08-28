import { loadView } from '../utils/components'; 

export default [{
  path: '/',
  redirect: '/login'
}, {
  name: 'Login',
  path: '/login',
  component: loadView('login')
}, {
  name: 'Home',
  path: '/home',
  component: loadView('home')
}]