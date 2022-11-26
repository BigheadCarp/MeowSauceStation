<template>
  <el-row>
<!--    PC端-->
    <el-col :xs="0" :lg="24" style="width: 925px;margin-left: -3px">
      <div class="block">
        <el-carousel trigger="click" height="260px" arrow="always" :interval='4500' style="border-radius: 10px">
          <el-carousel-item v-for="(item,index) in article" :key="index">
            <el-image :src="item.articleCover" style="width: 100%;height: 100%;float: left" @click="viewArticle(item.id)">
            </el-image>
            <h3 class="h3ByPC" style="position:absolute;">{{item.articleTitle}}</h3>
            <h6 class="h6ByPc" style="position: absolute">
              📑{{item.type}}&nbsp📖{{item.readingNum}}&nbsp⏰{{item.articleDate}}&nbsp💬{{item.commentNum}}
            </h6>
          </el-carousel-item>
        </el-carousel>
      </div>
    </el-col>
    <!--移动端-->
    <el-col :xs="24" :lg="0"  style="width: 340px;margin-left: -5px">
      <div class="block">
        <el-carousel trigger="click" height="160px" arrow="always" :interval='4500' style="border-radius: 10px">
          <el-carousel-item v-for="(item,index) in article" :key="index">
            <el-image :src="item.articleCover" style="width: 100%;height: 100%;float: left" @click="viewArticle(item.id)">
            </el-image>
            <div>
            <h3 class="h3ByMobile" style="position:absolute;">{{item.articleTitle}}</h3>

            <h6 class="h6ByMobile" style="position: absolute">
              📑{{item.type}}&nbsp📖{{item.readingNum}}&nbsp⏰{{item.articleDate}}&nbsp💬{{item.commentNum}}
            </h6>
            </div>
          </el-carousel-item>
        </el-carousel>
      </div>
    </el-col>
  </el-row>
</template>

<script>
  export default {
    name: "Carousel",
    data() {
      return {
        article:[]
      }
    },
    mounted() {
      this.getCarouselMsg()
    },
    methods: {
      getCarouselMsg(){
        this.$axios.get("/api/getCarouselMsg").then(resp =>{
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
    },
    computed: {
    }
  }
</script>

<style scoped>
  .h3ByPC{
    color: white;
    font-size: 26px;
    margin-top: 80px;
    margin-left: 360px;
  }

  .h6ByPc {
    color: white;
    font-size: 14px;
    font-weight: normal;
    margin-top: 150px;
    margin-left: 310px;
  }

  .h3ByMobile{
    color: white;
    text-align: center;
    margin-top: 60px;
    width: 100%;
  }

  .h6ByMobile{
    color: white;
    text-align: center;
    font-weight: normal;
    margin-top: 100px;
    width: 100%;
  }
</style>
