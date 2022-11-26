<template>
  <el-card class="admin-header">
    <a href="/index">
      <img src="https://meowsauce.oss-cn-hangzhou.aliyuncs.com/blogImg%20/mainPhoto/headPhoto/head.png" alt="" height="200px" width="165px" style="float: left;margin-top: -80px;margin-left: 0px">
    </a>
    <span style="font-size: 32px;font-weight: bold;position:absolute;left: 200px">喵酱的小站</span>
    <i class="el-icon-switch-button" v-on:click="logout" style="font-size: 40px;float: right"></i>
  </el-card>
</template>

<script>
  import {createRouter} from '../../router'

  export default {
    name: 'Header',
    methods: {
      logout () {
        var _this = this
        this.$axios.get('/api/logout').then(resp => {
          if (resp && resp.data.code === 200) {
            _this.$store.commit('logout')
            _this.$router.replace('/index')
            // 清空路由，防止路由重复加载
            const newRouter = createRouter()
            _this.$router.matcher = newRouter.matcher
          }
        }).catch(failResponse => {})
      }
    }
  }
</script>

<style scoped>
  .admin-header {
    height: 80px;
    opacity: 0.85;
    line-height: 40px;
    /*min-width: 900px;*/
    width: 1000px;
  }
  .el-icon-switch-button {
    cursor: pointer;
    outline:0;
  }
</style>
