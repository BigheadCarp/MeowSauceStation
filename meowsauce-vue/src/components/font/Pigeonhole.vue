<template>
  <el-row>
    <el-col :xs="0" :lg="24">
      <el-card class="box-card">
        <h1 style="margin-left: 35px">🚴🏻‍♀️总览</h1>️
        <div>
          <el-card class="ArticleTitle">
            <span>
              <p class="markSize">🛹️</p>
            </span>
            <h1 class="numSize">{{totalDTO.articleTotal}}</h1>
            <h3 class="textSize">文章总计</h3>
          </el-card>
          <el-card class="labelTitle">
            <span>
              <p class="markSize">⚖️</p>
            </span>
            <h1 class="numSize">{{totalDTO.labelTotal}}</h1>
            <h3 class="textSize">标签总计</h3>
          </el-card>
          <el-card class="commentTitle">
            <span>
              <p class="markSize">🗽</p>
            </span>
            <h1 class="numSize">{{totalDTO.commentTotal}}</h1>
            <h3 class="textSize">评论总计</h3>
          </el-card>
        </div>
        <h1 style="margin-left: 35px;margin-top: 40px">🏄🏻️文章列表</h1>
        <el-collapse accordion>
          <div v-for="(item,index) in articles" :key="index">
            <el-collapse-item class="collapseSize">
              <template slot="title">
                <p class="articleListTitleSize">{{item.year}}年 📜{{item.articlesByYear.length}}篇
                  🎨{{computedArticleTotal(item.articlesByYear)}}字
                </p>
              </template>
              <div class="block">
                <el-timeline style="font-size: 20px;font-weight: bold" v-for="(data,index) in item.month" :key="index">
                  <el-timeline-item
                    v-for="(value,key) in data"
                    :key="key"
                    placement="top"
                    :timestamp="key+'月'">
                    <div v-for="(v,i) in value" :key="i" style="color: blue;cursor: pointer">
                      <h5 @click="viewArticle(v.id)">{{computedArticleDate(v.articleDate)}}&nbsp;{{v.articleTitle}}</h5>
                    </div>
                  </el-timeline-item>
                </el-timeline>
              </div>
            </el-collapse-item>
          </div>
        </el-collapse>

        <h1 style="margin-left: 35px;margin-top: 40px">🏊🏻‍♀️ 更新信息</h1>
        <el-timeline v-for="(value,index) in pigeonhole" :key="index">
          <el-timeline-item :timestamp="value.pigeonholeDate" placement="top">
            <h4>{{value.pigeonholeContent}}</h4>
          </el-timeline-item>
        </el-timeline>
      </el-card>
    </el-col>
<!--    移动端-->
    <el-col :xs="24" :lg="0">
      <el-card class="box-cardByMobile">
        <h1 style="margin-left: 35px">🚴🏻‍♀️总览</h1>️
        <div style="margin-left: 5px">
          <el-card class="ArticleTitle">
            <span>
              <p class="markSize">🛹️</p>
            </span>
            <h1 class="numSize">{{totalDTO.articleTotal}}</h1>
            <h3 class="textSize">文章总计</h3>
          </el-card>
          <el-card class="labelTitle">
            <span>
              <p class="markSize">⚖️</p>
            </span>
            <h1 class="numSize">{{totalDTO.labelTotal}}</h1>
            <h3 class="textSize">标签总计</h3>
          </el-card>
          <el-card class="commentTitle">
            <span>
              <p class="markSize">🗽</p>
            </span>
            <h1 class="numSize">{{totalDTO.commentTotal}}</h1>
            <h3 class="textSize">评论总计</h3>
          </el-card>
        </div>
        <h1 style="margin-left: 35px;margin-top: 40px">🏄🏻️文章列表</h1>
        <el-collapse accordion style="width: 330px">
          <div v-for="(item,index) in articles" :key="index">
            <el-collapse-item class="collapseSize">
              <template slot="title">
                <p class="articleListTitleSize">{{item.year}}年 📜{{item.articlesByYear.length}}篇
                  🎨{{computedArticleTotal(item.articlesByYear)}}字
                </p>
              </template>
              <div class="block">
                <el-timeline style="font-size: 20px;font-weight: bold" v-for="(data,index) in item.month" :key="index">
                  <el-timeline-item
                    v-for="(value,key) in data"
                    :key="key"
                    placement="top"
                    :timestamp="key+'月'">
                    <div v-for="(v,i) in value" :key="i" style="color: blue;cursor: pointer">
                      <h5 @click="viewArticle(v.id)">{{computedArticleDate(v.articleDate)}}&nbsp;{{v.articleTitle}}</h5>
                    </div>
                  </el-timeline-item>
                </el-timeline>
              </div>
            </el-collapse-item>
          </div>
        </el-collapse>

        <h1 style="margin-left: 35px;margin-top: 40px">🏊🏻‍♀️ 更新信息</h1>
        <div style="margin-right: 11px">
        <el-timeline v-for="(value,index) in pigeonhole" :key="index">
          <el-timeline-item :timestamp="value.pigeonholeDate" placement="top">
            <h4>{{value.pigeonholeContent}}</h4>
          </el-timeline-item>
        </el-timeline>
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
  export default {
    name: "Pigeonhole",
    data() {
      return {
        articles: [],
        reverse: true,
        totalDTO: {},
        pigeonhole: []
      }
    },
    mounted() {
      this.getArticleByTime()
      this.getTotal()
      this.getAllUpdateMsg()
      this.$emit('Page', '归档')
    },
    methods: {
      getArticleByTime() {
        var _this = this
        this.$axios.get('/api/pigeonhole/getArticleByTime').then(resp => {
          if (resp && resp.data.code === 200) {
            _this.articles = resp.data.result
          }
        })
      },
      getTotal() {
        this.$axios.get('/api/pigeonhole/getTotal').then(resp => {
          if (resp && resp.data.code === 200) {
            this.totalDTO = resp.data.result
          }
        })
      },
      getAllUpdateMsg() {
        this.$axios.get('/api/pigeonhole/getAllUpdateMsg').then(resp => {
          if (resp && resp.data.code === 200) {
            this.pigeonhole = resp.data.result
          }
        })
      },
      viewArticle(id) {
        let articleUrl = this.$router.resolve(
          {
            path: '../../font/article',
            query: {
              id: id
            }
          }
        )
        window.open(articleUrl.href, "_self")
      },
    },
    computed: {
      computedArticleTotal() {
        return function (article) {
          let num = 0
          let i;
          for (i = 0; i < article.length; i++) {
            num += article[i].articleContentMd.length
          }
          num = num > 1000 ? num % 1000 + 'k' : num
          return num
        }
      },
      computedArticleDate() {
        return function (date) {
          let timeDate = date.slice(5)
          return timeDate
        }
      }
    },
  }
</script>

<style scoped>
  /deep/ .el-timeline-item {
    padding: 0;
  }

  /deep/ .el-timeline-item__timestamp {
    font-size: 16px;
    color: black;
  }

  /*页面大小*/
  .box-card {
    /*margin-top: 20px;*/
    padding-left: 10px;
    width: 926px;
  }
  .box-cardByMobile{
    margin-left: 19px;
    padding-left: 10px;
    width: 324px;
  }

  /*文章框大小*/
  .ArticleTitle {
    color: #0d6efd;
    background-color: rgba(13, 110, 253, .1) !important;
    border-radius: 0.25rem;
    border: #0d6efd solid 2px;
    width: 260px;
    margin-left: 20px;
    height: 100px;
    display: inline-block;
  }

  /*标签框大小*/
  .labelTitle {
    color: #fd7e14;
    background-color: rgba(253, 126, 20, .1) !important;
    border-radius: 0.25rem;
    border: #fd7e14 solid 2px;
    width: 260px;
    margin-left: 20px;
    height: 100px;
    display: inline-block;
  }

  /*评论框大小*/
  .commentTitle {
    color: #20c997;
    background-color: rgba(32, 201, 151, .1) !important;
    border-radius: 0.25rem;
    border: #20c997 solid 2px;
    width: 260px;
    margin-left: 20px;
    height: 100px;
    display: inline-block;
  }

  /*标志大小*/
  .markSize {
    font-size: 50px;
    margin-left: 20px;
    margin-top: 14px
  }

  /*数字大小*/
  .numSize {
    margin-top: -112px;
    float: right;
    margin-right: 50px
  }

  /*文字大小*/
  .textSize {
    font-weight: normal;
    margin-top: -73px;
    float: right;
    margin-right: 13px
  }

  /*文章列表标题字大小*/
  .articleListTitleSize {
    margin-left: 20px;
    font-weight: 700 !important;
    font-size: 1.25rem !important;
  }

  /*折叠面板大小*/
  .collapseSize {
    margin-left: 10px;
    border: #CCCCCC 1px solid;
    border-radius: 5px;
    margin-top: 20px;
    width: 88%;

  }

  /*清楚el-card的内边距*/
  /deep/ .el-card__body, .el-main {
    padding: 0;
  }
</style>
