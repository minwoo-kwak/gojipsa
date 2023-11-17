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
      path: '/apart/detail/:apartcode',
      name: 'apartdetail',
      component: () => import('../views/ApartDetailView.vue'),
    },
    {
        path: '/apart/detail/checklist',
        name: 'checklist',
      component: () => import('../views/CheckListPopUpView.vue'),
        beforeEnter: (to, from, next) => {
          console.log("sessionStorage.getItem('userStore') == ", sessionStorage.getItem('userStore'))
          if (sessionStorage.getItem('userStore') === null || sessionStorage.getItem('userStore')['Authorization'] === null) {
          next({
            path: '/user/login',
            // 다시 넘어올 수 있도록 경로를 넘겨준다.
            query: { redirect: to.fullPath }, 
          });
        } else {
          next();
        }
        }
    },
    {
      path:'/calculate',
      name:'calculate',
      component:()=>import('../views/CalculatorPopUpView.vue')
    }
    
  ]
})

export default router
