<template>
  <el-container>
    <el-header style="padding: 0">
      <nav-menu></nav-menu>
    </el-header>
<!--    主图开始-->
    <!--PC端主图开始-->
    <el-col :xs="0" :lg="24" style="text-align: center;">
      <el-main style="padding: 0;margin-top: -70px;z-index: -1;position: relative;">
        <div style="height: 480px;width: 100%">
          <el-image :src='headPhoto' :fit="fit" style="height: 99%;width: 100%;float: left"></el-image>
          <el-col :xs="0" :lg="1">
            <div style="position: absolute;margin-top: -300px;text-align: center;width: 100%">
              <h1 style="font-size: 35px;color: white;">{{title[0].head}}</h1>
              <h3 style="margin-top: -10px;color: white;">{{title[0].subhead}}</h3>
            </div>
          </el-col>
        </div>
      </el-main>
    </el-col>
    <!--PC端主图结束-->
    <!--移动端主图开始-->
    <el-col :xs="24" :lg="0" style="text-align: center;">
      <el-main style="padding: 0;margin-top: -70px;z-index: -1;position: relative;">
        <div style="height: 330px;width: 100%">
          <el-image :src='headPhoto' :fit="fit" style="height: 99%;width: 100%;float: left"></el-image>
          <el-col :xs="6" :lg="0">
            <div style="position: absolute;margin-top: -200px;text-align: center;width: 100%">
              <div>
              <h1 style="font-size: 25px;color: white;">{{title[0].head}}</h1>
              </div>
              <h3 style="margin-top: -10px;color: white;font-size: 14px">{{title[0].subhead}}</h3>

            </div>
          </el-col>
        </div>
      </el-main>
    </el-col>
    <!--移动端主图结束-->
    <!--主图结束-->
    <!--主内容区开始-->
    <el-container style="margin-top: 1.75rem">
      <!--左端卡片模块最新评论模块,移动端不显示-->
      <el-col :xs="0" :lg="7">
        <el-aside width="450px">
          <card style="margin-left: 80px"></card>
          <new-comment style="margin-left: 80px;margin-top: 20px"></new-comment>
          <HavingLook style="margin-left: 80px;margin-top: 20px"></HavingLook>
        </el-aside>
      </el-col>
      <!--左端卡片模块结束-->
      <!--主内容轮播图开始-->
      <el-col>
     <router-view @searchArticle="handleArticles" @changeArticleType="handleArticleType" @BackHomePage="BackHomePage"
     @Page="handlePage"></router-view>
      </el-col>
    </el-container>
    <!--主内容区结束-->
    <el-container>
      <el-footer><Foot></Foot></el-footer>
      <el-backtop>
        <img alt="" src="../../../static/image/ico-to-top.png" style="width: 40px;height: 40px;">
      </el-backtop>
    </el-container>
  </el-container>
</template>

<script>
  import NavMenu from "../common/NavMenu";
  import Card from "./Card";
  import NewComment from "./NewComment";
  import HavingLook from "./HavingLook";
  import Foot from "../common/Foot";
  import Bus from "../../../util/bus";

  export default {
    name: "home",
    components: {NavMenu, Card, NewComment, HavingLook, Foot},
    data() {
      return {
        headPhoto: '../../../static/image/mainPhoto/mainPhoto1',
        fit: 'cover',
        title: [
          {head: 'MeowSauce\'s Blog', subhead: '每个人都是星辰'}
        ],
      }
    },
    mounted: function () {
      // 用$on事件来接收参数
      Bus.$on('articleId', (data) => {
        console.log(data)
        this.handleArticleTitle(data)
      })
    },
    methods: {
      handleArticles(data) {
        console.log("home页面接受搜索消息: " + data)
        this.title[0].head = '搜索到' + data[0].searchTotal + '篇与' + data[0].searchTitle + '相关的结果'
        this.title[0].subhead = ''
      },
      handleArticleType(data) {
        console.log("home页面接受到分类信息:" + data)
        this.$axios.get('/api/type/' + data).then(resp => {
          if (resp && resp.data.code === 200) {
            this.title[0].head = this.computedType(data) + "·共" + resp.data.result + "篇"
            this.title[0].subhead = ''
          }
        })
      },
      BackHomePage(data) {
        console.log("home页面接受到返回首页的信息" + data)
        this.title[0].head = 'MeowSauce\'s Blog'
        this.title[0].subhead = '每个人都是星辰'
      },
      computedType(type) {
        if (type === '生活向') {
          return '🍜' + type
        } else if (type === '技术向') {
          return '🚀' + type
        } else {
          return '📌' + type
        }
      },
      handlePage(date) {
        console.log(date)
        if (date == '归档') {
          this.title[0].head = '📖归档'
          this.title[0].subhead = ''
        } else if (date == '碎碎念') {
          this.title[0].head = '🎮碎碎念'
          this.title[0].subhead = ''
        } else if (date == '朋友们') {
          this.title[0].head = '👬🏼朋友们'
          this.title[0].subhead = ''
        } else if (date == '关于我') {
          this.title[0].head = '👋🏼关于我'
          this.title[0].subhead = ''
        }
      },
      handleArticleTitle(data) {
        console.log("主页捕获到文章展示组件传来的消息" + data)
        this.$axios.get('/api/pageTitle/' + data).then(resp => {
          if (resp && resp.data.code === 200) {
            this.title[0].head = resp.data.result.articleTitle
            this.title[0].subhead = '📑' + this.computedType(resp.data.result.type)
              + '📖' + resp.data.result.readingNum + '⏰' + resp.data.result.articleDate
              + '📄' + resp.data.result.commentNum
          }
        })
      },
    },
      computed: {},
      /*刷新随机图片*/
      created() {
        let imgs = ['https://meowsauce.oss-cn-hangzhou.aliyuncs.com/blogImg%20/mainPhoto/%E5%BE%AE%E4%BF%A1%E5%85%AC%E4%BC%97%E5%8F%B78%E9%9F%B3%E7%9B%92%E5%AD%90%20%288%29.jpg',
          'https://meowsauce.oss-cn-hangzhou.aliyuncs.com/blogImg%20/mainPhoto/3K%E5%A3%81%E7%BA%B8%20%28750%29.jpg',
          'https://meowsauce.oss-cn-hangzhou.aliyuncs.com/blogImg%20/mainPhoto/%E5%BE%AE%E4%BF%A1%E5%85%AC%E4%BC%97%E5%8F%B78%E9%9F%B3%E7%9B%92%E5%AD%90%20%2810%29.jpg',
          'https://meowsauce.oss-cn-hangzhou.aliyuncs.com/blogImg%20/mainPhoto/%E5%A3%81%E7%BA%B8%20%2827%29.png',
          'https://meowsauce.oss-cn-hangzhou.aliyuncs.com/blogImg%20/mainPhoto/4K%E5%A3%81%E7%BA%B8%20%2883%29.png'
        ]
        let imgName = imgs[Math.floor(Math.random() * 5)]//进行计算随机
        console.log('-----------------------------------')
        this.headPhoto = imgName
      },
    }
</script>

<style scoped>
  body > .el-container {
    margin-bottom: 40px;
  }

  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }

  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }
</style>
