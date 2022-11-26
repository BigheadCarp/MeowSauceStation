<template>
  <el-main style="margin-top: -20px">
    <carousel style="margin-bottom: 15px"></carousel>
    <mainContent @articleId="handleArticleTitle"></mainContent>
  </el-main>
</template>

<script>
  import MainContent from "./MainContent";
  import Carousel from "./Carousel";

  export default {
    name: "AppIndex",
    components: {MainContent,Carousel},
    data() {
      return {
      }
    },
    props: ["content","isPage"],
    computed: {},
    mounted() {
      // let script = document.createElement('script');
      // script.type = 'text/javascript';
      // script.src = '';
      // document.body.appendChild(script);
      this.$emit('BackHomePage',true)
    },
    methods: {
      handleArticleTitle(data){
        console.log("AppIndex组件接收信息" + data)
      }
    }
  }
</script>

<style scoped>
</style>
