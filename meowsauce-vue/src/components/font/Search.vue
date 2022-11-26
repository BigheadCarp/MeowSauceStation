<template>
  <el-row>
    <el-col :xs="0" :lg="24" v-for="(item, index) in articles" :key="item.id">
      <div class="outCard">
        <span :class="computedClass(index)" @click="viewArticle(articles[index].id)">
          <!--判断循环到了哪个区域-->
          <el-image v-if="computedClass(index) === 'innerImg'" class="imgSize" :src="articles[index].articleCover">
          </el-image>
          <div v-if="computedClass(index) === 'innerText'">
            <el-image class="bgImg" :src="articles[index].articleCover"></el-image>
            <el-col class="articlesLeftLabel">⏰{{articles[index].articleDate}}</el-col>
            <el-col class="articlesRightLabel">
              📖{{computedReadingNum(index)}}&nbsp📑{{computedArticleType(articles[index].articleType)}}
            </el-col>
            <el-col class="articlesTitle">
              <h2>{{articles[index].articleTitle}}</h2>
            </el-col>
            <p class="articlesDetail">{{articles[index].articleAbstract}}</p>
          </div>
        </span>
        <span :class="computedClass(index+1)" @click="viewArticle(articles[index].id)">
          <el-image v-if="computedClass(index+1) === 'innerImg'" class="imgSize" :src="articles[index].articleCover">
          </el-image>
          <el-row v-if="computedClass(index+1) === 'innerText'">
            <el-image class="bgImg" :src="articles[index].articleCover"></el-image>
            <el-col class="articlesLeftLabel">⏰{{articles[index].articleDate}}</el-col>
            <el-col class="articlesRightLabel">
              📖{{computedReadingNum(index)}}&nbsp📑{{computedArticleType(articles[index].articleType)}}
            </el-col>
            <el-col class="articlesTitle">
              <h2>{{articles[index].articleTitle}}</h2>
            </el-col>
            <p class="articlesDetail">{{articles[index].articleAbstract}}</p>
          </el-row>
        </span>
      </div>
    </el-col>
    <el-col :xs="0" :lg="24" style="text-align: center">
      <el-pagination
        background
        layout="total, prev, pager, next, jumper"
        @current-change="handleCurrentChange"
        :page-size="pageSize"
        :total="total">
      </el-pagination>
    </el-col>
    <el-col :xs="24" :lg="0" v-for="(item,index) in articles" :key="item.id + '-only'">

      <div class="outCardByMobile">
        <span class="innerTextByMobile" @click="viewArticle(articles[index].id)">

          <el-image class="bgImgByMobile" :src="articles[index].articleCover   "></el-image>
          <el-col class="articlesLeftLabelByMobile">⏰{{articles[index].articleDate}}</el-col>
          <el-col class="articlesRightLabelByMobile">
            📖{{computedReadingNum(index)}}&nbsp📑{{computedArticleType(articles[index].articleType)}}
          </el-col>
          <el-col class="articlesTitleByMobile">
            <h3>{{articles[index].articleTitle}}</h3>
          </el-col>
          <p class="articlesDetailByMobile">{{articles[index].articleAbstract}}</p>
        </span>
      </div>
    </el-col>
  </el-row>
</template>

<script>
  export default {
    name: "Search",
    data() {
      return {
        fatherClass: 'innerImg',
        articles: [],
        pageSize: 4,
        total: 0,
        msg: [
          {searchTitle: '',searchTotal: 0}
        ]
      };
    },
    mounted() {
      this.loadArticles()
    },
    methods: {
      loadArticles() {
        this.$axios.get('/api/search/' + this.pageSize + '/1/' + this.$route.query.articleTitle).then(resp => {
          if (resp && resp.data.code === 200) {
            this.articles = resp.data.result.records
            this.total = resp.data.result.total
            this.msg[0].searchTitle = this.$route.query.articleTitle
            this.msg[0].searchTotal = this.total
          }
          this.$emit('searchArticle',this.msg)
        })
      },
      handleCurrentChange(page) {
        var _this = this
        if (this.articleType.length > 0) {
          this.$axios.get('/api/search' + this.pageSize + '/' + page + '/' + this.$route.query.articleTitle).then(resp => {
            _this.articles = resp.data.result.records
            _this.articles = resp.data.result.total
          })
        }
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
      computedArticleType() {
        return function (type) {
          if (type === '生活向') {
            return '🍜' + type
          } else if (type === '技术向') {
            return '🚀' + type
          } else {
            return '📌' + type
          }
        }
      },
      computedClass() {
        return function (index) {
          if (index % 2 == 0) {
            return 'innerImg'
          } else {
            return 'innerText'
          }
        }
      },
      computedReadingNum() {
        return function (index) {
          let num = this.articles[index].articleView
          if (num >= 1000) {
            let resultNum = num / 1000
            let result = resultNum + 'k'
            return result
          }
          return num
        }
      },
    }
  }
</script>

<style scoped>
  /*卡片整体样式*/
  .outCard {
    margin-top: 10px;
    margin-left: -5px;
    margin-bottom: 15px;
    width: 925px;
    height: 255px;
    /*border-radius: 40px;*/
    border-radius: 14px;
    border: 1px solid #EBEEF5;
    /*background-color: #FFF;*/
    overflow: hidden;
    /*color: #303133;*/
    -webkit-transition: .3s;
    transition: .3s;
  }

  .outCardByMobile {
    margin-top: 10px;
    margin-left: 18px;
    margin-bottom: 15px;
    width: 340px;
    height: 160px;
    border-radius: 14px;
    border: 1px solid #EBEEF5;
    overflow: hidden;
    transition: .3s;
  }

  /*图片div样式*/
  .innerImg {
    float: left;
    width: 300px;
    height: 100%;
    overflow: hidden;
  }

  /*文字部分样式*/
  .innerText {
    float: left;
    width: 625px;
    height: 100%;
  }

  .innerTextByMobile {
    float: left;
    width: 340px;
    height: 100%;
  }

  /*el image样式*/
  .imgSize {
    width: 100%;
    height: 100%;
  }

  /*文字部分左边时间标签的样式*/
  .articlesLeftLabel {
    margin-left: 20px;
    margin-top: 50px;
    /*z-index: 10;*/
    position: absolute;
    color: white;
  }

  .articlesLeftLabelByMobile {
    font-size: 14px;
    left: 10%;
    width: 50%;
    margin-top: 15px;
    position: absolute;
    color: white;
  }

  /*文字部分右边时间标签的样式*/
  .articlesRightLabel {
    text-align: right;
    margin-top: -180px;
    margin-left: -40px;
    /*margin-top: 50px;*/
    /*z-index: 10;*/
    /*position: absolute;*/
    color: white;
  }

  .articlesRightLabelByMobile {
    font-size: 14px;
    text-align: right;
    margin-top: 15px;
    width: 85%;
    z-index: 10;
    position: absolute;
    color: white;
  }

  /*文字部分标题的样式*/
  .articlesTitle {
    margin-top: 80px;
    margin-left: 20px;
    position: absolute;
    color: white;
  }

  .articlesTitleByMobile {
    margin-top: 30px;
    margin-left: 20px;
    width: 50%;
    position: absolute;
    color: white;
  }

  /*文字部分内容的样式*/
  .articlesDetail {
    margin-top: 150px;
    margin-left: 20px;
    position: absolute;
    color: white;
    width: 600px;
    text-indent: 32px;
    line-height: 30px;
  }

  .articlesDetailByMobile {
    margin-top: 90px;
    margin-left: 15px;
    position: absolute;
    color: white;
    /*width: 600px;*/
    text-indent: 32px;
    line-height: 25px;
    font-size: 13px;
  }

  .bgImg {
    filter: blur(2rem);
    margin-top: 40px;
    width: 625px;
    height: 180px;
    z-index: -50 !important;
    float: left !important;
    display: inline !important;
    overflow: hidden;
  }

  .bgImgByMobile {
    /*filter: blur(2rem);*/
    /*margin-top: 40px;*/
    /*width: 340px;*/
    /*height: 160px;*/
    width: 100%;
    height: 100%;
    z-index: -50 !important;
    float: left !important;
    display: inline !important;
    overflow: hidden;
  }

  .imgSize:hover {
    animation: move .5s linear forwards;
  }

  @keyframes move {
    0% {
      transform: scale(1);
    }

    100% {
      transform: scale(1.1);
    }
  }
</style>
