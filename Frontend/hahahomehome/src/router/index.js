import { createRouter, createWebHistory } from 'vue-router'
import MainView from '../views/MainView.vue'
import UserView from '../views/UserView.vue'
import ApartView from '../views/ApartView.vue'

import UserLogin from '../components/user/UserLogin.vue'
import UserSignUp from '../components/user/UserSignUp.vue'
import UserMyPage from '../components/user/UserMyPage.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'main',
      component: MainView,
    },
    {
      path: '/user',
      name: 'user',
      component: UserView,
      children: [
        {
          path: 'login',
          name: 'login',
          component: UserLogin,
        },
        {
          path: 'signup',
          name: 'signup',
          component: UserSignUp,
        },
        {
          path: 'mypage',
          name: 'mypage',
          component: UserMyPage,
        }
      ]
    },
    {
      path:'/apart/:code?',
      name:'apart',
      component: ApartView,
    },
    {
      path: '/apart/detail',
      name: 'apartdetail',
      component: () => import('../views/ApartDetailView.vue')
    },
    {
      path:'/calculate',
      name:'calculate',
      component:()=>import('../views/CalculatorPopUpView.vue')
    }
    
  ]
})

export default router
