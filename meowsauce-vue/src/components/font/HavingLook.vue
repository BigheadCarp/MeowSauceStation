<template>
  <el-card class="box-card">
    <div slot="header">
      <span>πΈ &nbsp&nbsp;ιδΎΏηη</span>
    </div>
    <div v-for="item in article" :key="item.id" class="text item">
      <a @click="viewArticle(item.id)">
        <div class="smallBox">
          <el-image :src="item.articleCover" class="theImg">
          </el-image>
          <h5 class="bgTime">{{item.articleDate}}</h5>
          <h5 class="bgTitle">{{item.articleTitle}}</h5>
        </div>
      </a>
    </div>
  </el-card>
</template>

<script>
  export default {
    name: "HavingLook",
    data() {
      return {
        article:[]
      }
    },
    mounted() {
      this.getArticleHavingLooking()
    },
    methods: {
      getArticleHavingLooking(){
        this.$axios.get('/api/HavingLooking').then(resp => {
          if (resp && resp.data.code === 200){
            this.article = resp.data.result
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
    }
  }
</script>

<style scoped>
  .text {
    font-size: 16px;
    border-radius: 14px;
  }
  .item {
    margin-bottom: 18px;
  }
  .box-card {
    width: 285px;
    border-radius: 10px;
  }
  /*θ£εΎηηε°ηε­*/
  .smallBox{
    width: 250px;
    height: 123px;
  }
  /*θζ―εΎη*/
  .theImg{
    width: 100%;
    height: 100%;
    float: left;
    border-radius: 10px;
  }
  /*θζ―εΎδΈ­ηζΆι΄*/
  .bgTime{
    position: absolute;
    color: white;
    margin-top: 80px;
    margin-left: 10px;
    font-weight: normal;
  }
  .bgTitle{
    position: absolute;
    color: white;
    margin-top:100px;
    margin-left: 10px;
    font-weight: normal;
  }
  a{
    text-decoration: none;
  }
</style>
