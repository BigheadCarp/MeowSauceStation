<template>
  <body id="paper">
  <el-form :model="loginForm" :rules="rules" class="login-container" label-position="left"
           label-width="0px" v-loading="loading">
    <h3 class="login_title">用户注册</h3>
    <el-form-item prop="username">
      <el-input type="text" v-model="loginForm.username"
                auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input type="password" v-model="loginForm.password"
                auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
    <!--    <el-form-item>-->
    <!--      <el-input type="text" v-model="loginForm.name"-->
    <!--                auto-complete="off" placeholder="真实姓名"></el-input>-->
    <!--    </el-form-item>-->
    <el-form-item>
      <el-input type="text" v-model="loginForm.phone"
                auto-complete="off" placeholder="电话号码"></el-input>
    </el-form-item>
    <!--    <el-form-item>-->
    <!--      <el-input type="text" v-model="loginForm.email"-->
    <!--                auto-complete="off" placeholder="E-Mail"></el-input>-->
    <!--    </el-form-item>-->
    <el-form-item prop="verificationCode">
      <span>
        <el-input type="text" v-model="loginForm.verificationCode"
                  auto-complete="off" placeholder="请输入验证码" style="width: 180px;margin-left: 0px">
      </el-input>
        <el-button type="info" round @click="changeMsg" style="margin-left: 30px"
                   :disabled="disable">{{msg}}</el-button>
      </span>
    </el-form-item>
    <el-form-item style="width: 100%">
      <el-button type="primary" style="width: 40%;background: #505458;border: none" v-on:click="register">注册</el-button>
    </el-form-item>
  </el-form>
  </body>
</template>
<script>
  export default {
    data () {
      return {
        rules: {
          username: [{required: true, message: '用户名不能为空', trigger: 'blur'}],
          password: [{required: true, message: '密码不能为空', trigger: 'blur'}],
          verificationCode: [{required: true, message: '验证码不能为空', trigger: 'blur'}]
        },
        checked: true,
        loginForm: {
          username: '',
          password: '',
          name: '访客',
          phone: '',
          email: '',
          verificationCode: ''
        },
        loading: false,
        disable: false,
        msg: '发送验证码',
        time: 60
      }
    },
    methods: {
      register () {
        var _this = this
        this.$axios
          .post('/api/register', {
            userName: this.loginForm.username,
            userPassword: this.loginForm.password,
            userNickname: this.loginForm.name,
            userTelephoneNumber: this.loginForm.phone,
            userEmail: this.loginForm.phone + '@163com',
            verificationCode: this.loginForm.verificationCode
          })
          .then(resp => {
            if (resp.data.code === 200) {
              this.$alert('注册成功', '提示', {
                confirmButtonText: '确定'
              })
              _this.$router.replace('/login')
            } else {
              this.$alert(resp.data.message, '提示', {
                confirmButtonText: '确定'
              })
            }
          })
          .catch(failResponse => {
          })
      },
      changeMsg () {
        var reg = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9]|17[0|1|2|3|5|6|7|8|9])\d{8}$/
        if (this.loginForm.phone.length === 11) {
          if (reg.test(this.loginForm.phone)) {
            this.sendMsg()
            this.disable = true
            this.msg = this.time + 's后获取'
            var timer = setInterval(() => {
              this.time--
              this.time = this.time >= 0 ? this.time : 0
              this.msg = this.time + 's后获取'
              if (this.time === 0) {
                this.msg = '发送验证码'
                this.disable = false
                clearInterval(timer)
                this.time = 60
              }
            }, 1000)
          } else {
            this.$message({
              message: '手机号码输入不正确',
              type: 'warning'
            })
          }
        } else {
          this.$message({
            message: '请输入11位手机号码',
            type: 'warning'
          })
        }
      },
      sendMsg () {
        this.$axios
          .post('/api/sendMsg', {
            userTelephoneNumber: this.loginForm.phone
          })
          .then(resp => {
            if (resp.data.code === 200) {
              this.$message({
                message: '已发送验证码',
                type: 'success'
              })
            } else {
              this.$message.error('短信功能异常,请联系管理员')
            }
          })
      }
    }
  }
</script>
<style>
  #paper {
    background: url("https://meowsauce.oss-cn-hangzhou.aliyuncs.com/blogImg%20/2022-11-17/1536ffb3ee5a4cbea437852f47ea23ac-.jpg") ;
    background-position: center;
    height: 800px;
    width: 100%;
    background-size: cover;
    position: fixed;
    margin: 0;
  }

  body {
    margin: -5px 0px;
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
