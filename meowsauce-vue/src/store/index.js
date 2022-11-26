import Vue from 'vue'
import Vuex from  'vuex'

Vue.use(Vuex)
//Vuex需要对state进行管理，则必须控制state的维护入口，因此所有对state的变更请求都必须通过mutations来完成
//项目打开是会判断本地存储中是否有user这个对象存在,如果存在就取出来并获得username,否则则把username设置为空
//当调用login方法时会触发mutations修改state状态
export default new Vuex.Store({
  state: {
    username: window.localStorage.getItem('username') == null ? '' : JSON.parse(window.localStorage.getItem('username' || '[]')),
    adminMenus: []
  },
  mutations: {
    initAdminMenu (state, menus) {
      state.adminMenus = menus
    },
    login (state, data) {
      state.username = data
      window.localStorage.setItem('username', JSON.stringify(data))
    },
    logout (state) {
      // 注意不能用 null 清除，否则将无法判断 user 里具体的内容
      state.username = ''
      window.localStorage.removeItem('username')
      state.adminMenus = []
    }
  },
  actions: {
  }
})
