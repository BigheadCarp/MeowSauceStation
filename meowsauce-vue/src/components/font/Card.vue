<template>
  <el-card class="box-card" style="border-radius: 20px;text-align: center;">
    <div slot="header" style="margin-top: 60px">
      <el-avatar :src="headImg"
                 :size="60">
      </el-avatar>
      <div style="margin-top: 20px;font-weight: bolder;font-size: 25px">MeowSauce</div>
      <div style="margin-top: 20px">每个人都是星辰</div>
    </div>
    <div>
      <el-row style="font-weight: bold">
        <el-col :lg="8">
          <span>文章</span>
        </el-col>
        <el-col :lg="8">
          <span>评论</span>
        </el-col>
        <el-col :lg="8">
          <span>标签</span>
        </el-col>
      </el-row>
    </div>
    <div style="margin-top: 10px">
      <el-row>
        <el-col :lg="8">
          <span>{{totalDTO.articleTotal}}</span>
        </el-col>
        <el-col :lg="8">
          <span>{{totalDTO.commentTotal}}</span>
        </el-col>
        <el-col :lg="8">
          <span>{{totalDTO.labelTotal}}</span>
        </el-col>
      </el-row>
    </div>
  </el-card>
</template>

<script>
  export default {
    name: "Card",
    data() {
      return {
        totalDTO: [],
        headImg: 'https://meowsauce.oss-cn-hangzhou.aliyuncs.com/blogImg%20/mainPhoto/mmexportd6db14817968735c37a3037dfbd7e362_16363315.png',
      };
    },
    mounted() {
      this.getTotal()
    },
    methods: {
      getTotal(){
        this.$axios.get('/api/pigeonhole/getTotal').then(resp => {
          if (resp && resp.data.code === 200){
            this.totalDTO = resp.data.result
          }
        })
      },
    }
  }
</script>

<style scoped>
  /*卡片大小*/
  .box-card {
    width: 280px;
  }

  span {
  }
</style>
