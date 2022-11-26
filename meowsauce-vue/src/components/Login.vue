<template>
  <body id="paper">
  <el-form :model="loginForm" :rules="rules" class="login-container" label-position="left"
           label-width="0px" v-loading="loading">
    <h3 class="login_title">系统登录</h3>
    <el-form-item prop="username">
      <el-input type="text" v-model="loginForm.username"
                auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input type="password" v-model="loginForm.password"
                auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
    <el-checkbox class="login_remember" v-model="checked"
                 label-position="left"><span style="color: #505458">记住密码</span></el-checkbox>
    <el-form-item style="width: 100%;margin-left: 40px">
      <el-button type="primary" style="width: 40%;background: #505458;border: none" v-on:click="login">登录</el-button>
      <router-link to="register">
        <el-button type="primary" style="width: 40%;background: #505458;border: none">注册</el-button>
      </router-link>
    </el-form-item>
  </el-form>
  </body>
</template>

<script>
  export default {
    name: "Login",
    data() {
      return {
        rules: {
          username: [{required: true, message: '用户名不能为空', trigger: 'blur'}],
          password: [{required: true, message: '密码不能为空', trigger: 'blur'}]
        },
        checked: true,
        loginForm: {
          username: '',
          password: ''
        },
        loading: false
      }
    },
    methods: {
      login() {
        var _this = this
        console.log(this.$store.state)
        this.$axios
          .post('/api/login', {
            userName: this.loginForm.username,
            userPassword: this.loginForm.password
          })
          .then(successResponse => {
            if (successResponse.data.code === 200) {
              var data = successResponse.data.result
              //触发store/index.js中mutations的login方法,将loginForm赋值给state中的user,并存储在LocalStorage中
              _this.$store.commit('login',data)
              var path = this.$route.query.redirect
              this.$router.replace({path: path === '/' || path === undefined ? '/admin/dashboard' : path})
            }else {
              this.$alert(successResponse.data.message, '提示', {
                confirmButtonText: '确定'
              })
            }
          })
          .catch(failResponse => {
          })
      }
    }
  }
</script>

<style scoped>
  #paper {
    background: url("https://meowsauce.oss-cn-hangzhou.aliyuncs.com/blogImg%20/2022-11-17/1536ffb3ee5a4cbea437852f47ea23ac-.jpg") ;
    background-position: center;
    height: 100%;
    width: 100%;
    background-size: cover;
    position: fixed;
  }
  body{
    margin: 0;
  }
  .login-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 90px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: gray;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }
  .login_title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }
  .login_remember {
    margin: 0px 0px 35px 0px;
    text-align: left;
  }
</style>
