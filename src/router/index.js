import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/Manage.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Manage',
    component:()=>import('../views/Manage.vue'),
    redirect:"home/",
    children:[
      { path: 'user', name: 'User', component:()=>import('../views/User')},
      {path: 'home', name: 'Home', component:()=>import('../views/Home')},
      {path: 'problem', name: 'Problem', component:()=>import('../views/Problem')},
      {path: 'award', name: 'Award', component:()=>import('../views/Award')},
      {path: 'teacher', name: 'Teacher', component:()=>import('../views/Teacher')},
      {path: 'team', name: 'Team', component:()=>import('../views/Team')},
      {path: 'formwork', name: 'Formwork', component:()=>import('../views/Formwork')},
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
