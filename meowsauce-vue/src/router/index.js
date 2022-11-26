import Vue from 'vue'
import Router from 'vue-router'
// import HelloWorld from '@/components/HelloWorld'
import Login from "../components/Login";
import AppIndex from "../components/font/AppIndex";
import Home from "../components/font/Home";
import Test from "../components/Test";
import contrast from "../components/contrast";
import Register from "../components/Register";
import AdminIndex from "../components/admin/AdminIndex";
import Index from "../components/admin/dashboard/admin/Index"
import AdminMenu from "../components/admin/AdminMenu";
import Error404 from "../components/pages/Error404";

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/home',
      name: 'Home',
      component: Home,
      //home页面并不需要访问
      redirect: '/index',
      children: [
        {
          path: '/index',
          name: 'AppIndex',
          component: AppIndex,
        },
        {
          path: '/font/article',
          name: 'Article',
          component: () => import('../components/font/ArticleDetails')
        },{
          path: '/font/type',
          name: 'Type',
          component: () => import('../components/font/Type')
        },{
          path: '/font/search',
          name: 'Search',
          component: () => import('../components/font/Search')
        },{
          path: '/font/pigeonhole',
          name: 'Pigeonhole',
          component: () => import('../components/font/Pigeonhole')
        },{
          path: '/font/signature',
          name: 'Signature',
          component: () => import('../components/font/Signature')
        },{
          path: '/font/friend',
          name: 'Friend',
          component: () => import('../components/font/Friend')
        },{
          path: '/font/about',
          name: 'About',
          component: () => import('../components/font/About')
        }
      ]
    },
    {
      path: '/admin/content/editor',
      name: 'Editor',
      component: () => import('../components/admin/content/ArticleEditor'),
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/',
      name: 'Default',
      component: Home,
      redirect: '/home'
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/test',
      name: 'Test',
      component: Test,
      children: [
        {
          path: '/contrast',
          name: 'contrast',
          component: contrast
        }
      ],
    },
    {
      path: '*',
      name: 'Error404',
      component: Error404
    },
    {
      path: '/admin',
      name: 'Admin',
      component: AdminIndex,
      redirect: '/admin/dashboard',
      meta: {
        requireAuth: true
      },
      children: [
        {
          path: '/admin/dashboard',
          name: 'Dashboard',
          component: Index,
          meta: {
            requireAuth: true
          }
        }
      ]
    },
    {
      path: '*',
      component: () => import('../components/pages/Error404')
    }
  ]
})

//用于创建默认路由
export const createRouter = routes => new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Default',
      redirect: '/home',
      component: Home
    },
    {
      //home页面并不需要被访问,只是作为其他组件的父组件
      path: '/home',
      name: 'Home',
      component: Home,
      redirect: '/index',
      children: [
        {
          path: '/index',
          name: 'AppIndex',
          component: () => import('../components/font/AppIndex')
        },
        {
          path: '/font/article',
          name: 'Article',
          component: () => import('../components/font/ArticleDetails')
        },
      ]
    },
    {
      path: '/admin/content/editor',
      name: 'Editor',
      component: () => import('../components/admin/content/ArticleEditor'),
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('../components/Login')
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('../components/Register')
    },
    {
      path: '/admin',
      name: 'Admin',
      component: () => import('../components/admin/AdminIndex'),
      meta: {
        requireAuth: true
      },
      children: [
        {
          path: '/admin/dashboard',
          name: 'Dashboard',
          component: () => import('../components/admin/dashboard/admin/Index'),
          meta: {
            requireAuth: true
          }
        }
      ]
    },
    {
      path: '*',
      component: () => import('../components/pages/Error404')
    }
  ]
})
