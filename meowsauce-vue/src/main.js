import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';
import store from './store'
import 'element-ui/lib/theme-chalk/index.css';
//ElementUi响应式样式
import 'element-ui/lib/theme-chalk/display.css';
// 设置反向代理，前端请求默认发送到 http://localhost:8443/api
import axios from "axios";
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import Bus from "../util/bus";
// 全局注册，之后可在其他组件中通过 this.$axios 发送数据
Vue.prototype.$axios = axios
//带上cookie
axios.defaults.withCredentials = true
axios.defaults.baseURL = 'http://localhost:8443/'
Vue.use(ElementUI)
Vue.use(mavonEditor)
Vue.use(Bus)
Vue.use(store)
//使用钩子函数判断是否拦截
//判断访问的路径是否需要登录,如果需要,判断store里有没有存储user的信息,如果存在,则放行
//,否则跳转到登录页面,并存储访问的页面路径
router.beforeEach((to, from, next) => {
    if (store.state.username && to.path.startsWith('/admin')) {
      initAdminMenu(router, store)
    }
    // 已登录状态下访问 login 页面直接跳转到后台首页
    if (store.state.username && to.path.startsWith('/login')) {
      next({
        path: 'admin/dashboard'
      })
    }
    if (to.meta.requireAuth) {
      if (store.state.username) {
        axios.get('/api/authentication').then(resp => {
          if (resp) next()
        })
      } else {
        next({
          path: 'login',
          query: {redirect: to.fullPath}
        })
      }
    } else {
      next()
    }
  }
)

// 用于执行请求，调用格式化方法并向路由表中添加信息
const initAdminMenu = (router, store) => {
  if (store.state.adminMenus.length > 0) {
    return
  }
  axios.get('/api/menu').then(resp => {
    header: {

    }
    if (resp && resp.status === 200) {
      var fmtRoutes = formatRoutes(resp.data)
      router.addRoutes(fmtRoutes)
      store.commit('initAdminMenu', fmtRoutes)
    }
  })
}
//后台数据处理
const formatRoutes = (routes) => {
  let fmtRoutes = []
  routes.forEach(route => {
    if (route.children) {
      route.children = formatRoutes(route.children)
    }
    let fmtRoute = {
      path: route.path,
      component: resolve => {
        require(['./components/admin/' + route.component + '.vue'], resolve)
      },
      name: route.name,
      nameZh: route.nameZh,
      iconCls: route.iconCls,
      children: route.children
    }
    fmtRoutes.push(fmtRoute)
  })
  return fmtRoutes
}
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
