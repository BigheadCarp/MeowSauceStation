<template>
  <div>
  <!--xs是移动端的占位,lg是PC端的占位,gutter是占位的间隔,-->
  <el-row :gutter="0" justify="start">
    <el-menu class="webTitle" mode="horizontal" router :default-active="'/index'">
      <el-col :xs="4" :lg="0" style="margin-left: 15px;margin-top: 15px">
        <i class="el-icon-more" @click="drawer = true" style="color: white"></i>
      </el-col>
      <el-col :xs="16" :lg="4">
        <router-link :to="{path:'/index'}">
        <el-menu-item  class="title" style="text-decoration: none">MeowSauce'S Blog</el-menu-item>
        </router-link>
      </el-col>
      <el-col :xs="0" :lg="2">
        <el-submenu index="2" class="classify">
          <template style="color: white !important;font-size: 16px" slot="title">📋分类</template>
          <el-menu-item v-for="(item,i) in navContext" :key="i" @click="findArticleByType(item.name)" class="classify">
            {{item.contextItem}}
          </el-menu-item>
        </el-submenu>
      </el-col>
      <el-col :xs="0" :lg="2" class="interval">
        <el-menu-item  style="color: white!important;font-size: 16px" @click="SkipPage(navList[0].name)">{{navList[0].navItem}}</el-menu-item>
      </el-col>
      <el-col :xs="0" :lg="2" class="interval">
        <el-menu-item  style="color: white!important;font-size: 16px" @click="SkipPage(navList[1].name)">{{navList[1].navItem}}</el-menu-item>
      </el-col>
      <el-col :xs="0" :lg="2" class="interval">
        <el-menu-item  style="color: white!important;font-size: 16px" @click="SkipPage(navList[2].name)">{{navList[2].navItem}}</el-menu-item>
      </el-col>
      <el-col :xs="0" :lg="2" class="interval">
        <el-menu-item style="color: white!important;font-size: 16px" @click="SkipPage(navList[3].name)">{{navList[3].navItem}}</el-menu-item>
      </el-col>
      <el-col :xs="1" :lg="1">
<!--        <i class="el-icon-search" :@click="dialogFormVisible = true"></i>-->
        <i class="el-icon-search" @click="dialogFormVisible = true"></i>
      </el-col>
    </el-menu>
  </el-row>
<!--    搜索框-->
  <el-dialog :visible.sync="dialogFormVisible" width="350px" center>
    <div style="margin-top: -30px;font-size: 25px;font-weight: bold">搜&nbsp&nbsp索</div>
    <el-input v-model="content" placeholder="请输入内容" style="width: 65%;border: none;border-radius: 30px">
    </el-input>
    <el-button  @click="sendArticle(content)">点击搜索</el-button>
  </el-dialog>
<!--菜单栏-->
    <el-drawer
      :with-header="false"
      :visible.sync="drawer"
      :direction="direction"
      size="80%">
      <el-row>
        <el-col :xs="5">
          <el-avatar src="../../../static/image/headPhoto.png"
                     :size="60" shape="square" class="avatar">
          </el-avatar>
        </el-col>
        <el-col :xs="6">
          <span class="avatarName">MeowSauce</span>
        </el-col>
      </el-row>
      <el-row>
        <span class="signature">每个人都是星辰</span>
      </el-row>
      <el-row>
        <hr style="margin-top: 40px;width: 90%;background-color: gray">
      </el-row>
      <el-col :xs="24">
        <el-menu
          class="el-menu-vertical-demo"
          text-color="black"
          router
          active-text-color="#ffd04b"
          style="text-align: left">
          <el-submenu index="1">
            <template slot="title">📋分类</template>
            <el-menu-item v-for="(item,i) in navContext" :key="i" @click="findArticleByType(item.name)">
              {{item.contextItem}}
            </el-menu-item>
          </el-submenu>
          <el-menu-item v-for="(item,i) in navList" :key="i" @click="SkipPage(item.name)">
            {{item.navItem}}
          </el-menu-item>
        </el-menu>
      </el-col>
    </el-drawer>
  </div>
</template>

<script>
  export default {
    name: 'NavMenu',
    data() {
      return {
        navList: [
          {name: '归档', navItem: '📖归档'},
          {name: '碎碎念', navItem: '🎮碎碎念'},
          {name: '朋友们', navItem: '👬🏼朋友们'},
          {name: '关于我', navItem: '👋🏼关于我'}
        ],
        navContext: [
          {name: '生活向', contextItem: '🍜生活向'},
          {name: '技术向', contextItem: '🚀技术向'},
          {name: '非原创', contextItem: '📌非原创'}
        ],
        //搜索框
        dialogFormVisible: false,
        content: '',
        //菜单展开
        drawer: false,
        direction: 'ltr',
        isPage: true
      };
    },
    methods: {
      sendArticle(content){
        if (content.length > 0){
          let articleUrl = this.$router.resolve(
            {
              path: '../../font/search',
              query: {
                articleTitle: content
              }
            }
          )
          window.open(articleUrl.href, "_self")
          this.dialogFormVisible=false
        }else {
          this.$message({
            message: '搜索内容不能为空',
            type: 'warning'
          });
        }

      },
      findArticleByType(type){
        let articleUrl = this.$router.resolve(
          {
            path: '../../font/type',
            query: {
              type: type
            }
          }
        )
        window.open(articleUrl.href,"_self")
      },
      SkipPage(name){
        if (name == '归档'){
          let articleUrl = this.$router.resolve(
            {
              path: '../../font/pigeonhole',
            })
          window.open(articleUrl.href,"_self")
        }else if (name == '碎碎念'){
          let articleUrl = this.$router.resolve(
            {
            path: '../../font/signature',
          })
          window.open(articleUrl.href,"_self")
        }else if (name == '朋友们'){
          let articleUrl = this.$router.resolve(
            {
              path: '../../font/friend'
            })
          window.open(articleUrl.href,"_self")
        }else if (name == '关于我'){
          let articleUrl = this.$router.resolve(
            {
              path: '../../font/about'
            })
          window.open(articleUrl.href,"_self")
        }

      },
      BackHomePage(){
        console.log("返回首页")
        this.$emit('BackHomePage',this.isPage)
        this.isPage = !this.isPage
      },
    },
  }
</script>
<style>

</style>
<style scoped>
  /deep/ .el-menu.el-menu--horizontal{
    border-bottom: none;
}
/*覆盖ElementUI原本样式 分类字样*/
  /deep/ .el-submenu__title{
    color: white;
  }
  a {
    text-decoration: none;
  }

  span {
    pointer-events: none;
  }

  /*导航栏整体样式*/
  .webTitle {
    font-weight: bold;
    background-color:rgba(220,38,38,0.2) !important;
    border-color: #1118271a;
  }

  /*取消导航栏点击后的hover样式*/
  .webTitle :hover {
    background: none !important;
  }

  /*标题样式*/
  .title {
    color: white !important;
    font-size: 20px !important;
    border: none !important;
    /*border-bottom: none;*/
  }

  /*搜索框样式*/
  .el-icon-search {
    color: white;
    position: absolute;
    font-size: 25px;
    margin-top: 15px;
    right: 1%;
  }

  /*分类下拉框样式*/
  .classify {
    color: black !important;
    font-size: 15px;
    font-weight: bold;
    text-align: center;
  }

  /*两个标签之间的间隔*/
  .interval {
    margin-left: -20px;
  }

  /*搜索文字样式*/
  .search-title-text{
    margin-top: -30px !important;
    margin-bottom: 10px !important;
    font-size: 25px !important;
    font-weight: bold !important;
  }
  .search-title{
    width:100%;
    border: none !important;
    border-radius: 30px !important;
  }
  /*头像样式*/
  .avatar{
    margin-top: 30px;
    margin-left: 20px;
  }
  /*名字样式*/
  .avatarName{
    position: absolute;
    margin-top: 60px;
    margin-left: 20px;
    color: gray;
  }
  /*签名样式*/
  .signature{
    position: absolute;
    margin-top: 10px;
    left: 6%;
    color: gray;
    font-size: 15px;
  }
</style>

