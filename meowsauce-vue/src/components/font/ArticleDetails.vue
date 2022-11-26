<template>
  <el-row>
    <el-col :xs="0" :lg="24">
      <div class="articles-area">
        <el-card>
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>正文</el-breadcrumb-item>
          </el-breadcrumb>
          <el-divider></el-divider>
          <div>
            <div class="markdown-body" style="width: 926px !important;">
              <div style="width: 886px" v-html="article.articleContentHtml"></div>
            </div>
          </div>
          <el-divider></el-divider>
          <div>🌌
            <el-tag effect="dark" size="small" v-for="(item,index) in labels" :key="index" style="margin-left: 5px">
              {{item}}
            </el-tag>
          </div>
        </el-card>
        <comment :articleId="article.id"></comment>
      </div>
    </el-col>
    <el-col :xs="24" :lg="0">
      <div class="articles-areaByMobile">
        <el-card>
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>正文</el-breadcrumb-item>
          </el-breadcrumb>
          <el-divider></el-divider>
          <div>
            <div class="markdown-body" style="width: 380px !important;">
              <div style="width: 340px" v-html="article.articleContentHtml"></div>
            </div>
          </div>
          <el-divider></el-divider>
          <div>🌌
            <el-tag effect="dark" size="small" v-for="(item,index) in labels" :key="index" style="margin-left: 5px">
              {{item}}
            </el-tag>
          </div>
        </el-card>
        <comment :articleId="article.id" style="width: 320px"></comment>
      </div>
    </el-col>
  </el-row>
</template>

<script>
  import Comment from "./Comment";
  import Bus from "../../../util/bus";

  export default {
    name: 'ArticleDetails',
    data() {
      return {
        article: [],
        labels: [],
        articleId: 0
      }
    },
    components: {Comment},
    mounted() {
      this.loadArticle()
    },
    methods: {
      loadArticle() {
        var _this = this
        this.$axios.get('/api/article/' + this.$route.query.id).then(resp => {
          if (resp && resp.data.code === 200) {
            _this.article = resp.data.result
          }
        })
      },
      loadLabels(articleId) {
        this.$axios.get('/api/getLabel/' + articleId).then(resp => {
          if (resp && resp.data.code === 200) {
            this.labels = resp.data.result
          }
        })
      },
      readingNumberAdd(articleId) {
        this.$axios.get('/api/ReadingNumAdd/' + articleId).then(resp => {
          if (resp && resp.data.code === 200) {
            console.log("阅读量+1")
          }
        })
      }
    },
    watch: {
      article: {
        handler() {
          this.articleId = this.article.id
          Bus.$emit('articleId', this.article.id)
          this.readingNumberAdd(this.articleId)
        }
      },
      articleId: {
        handler() {
          this.loadLabels(this.articleId)
        }
      }
    },
    destroyed() {
      Bus.$off('articleId', this.article.id);
    }
  }
</script>

<style scoped>
  @import "../../styles/markdown.css";

  .articles-area {
    width: 926px;
  }
  .articles-areaByMobile{
    margin-left: 2px;
    width: 370px;
  }

  /deep/ .el-tag--small {
    /*padding: 0;*/
    border-radius: 10px;
  }
</style>
