<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
import Bus from "../util/bus";
export default {
  name: 'App',
  //全局中定义的方法名
  methods:{
    passMsg(){
      //注意这里的写法
      Bus.$emit('msg','要传入的数数据')//自定义事件都用emit
    }
  },
//子组件利用勾子函数监听事件
  mounted(){
    Bus.$on('msg',(val)=>{

    })
  }
}
</script>

<style>
body{
  margin: 0;
  padding: 0;
}
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

.el-menu--collapse .el-menu .el-submenu, .el-menu--popup{
  min-width: 120px!important;
}
</style>
