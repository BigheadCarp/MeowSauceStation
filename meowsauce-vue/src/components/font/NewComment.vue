<template>
  <el-card class="box-card">
    <div slot="header">
      <span>💬&nbsp&nbsp最新回复</span>
    </div>
    <div v-for="(item,index) in remarks" :key="index" class="text item">
      <el-row>
        <!--头像-->
        <el-col :span="4">
          <el-avatar shape="circle" :src="item.cover" :size="30"></el-avatar>
        </el-col>
        <el-col :span="9" style="font-size: 12px;margin-top: 6px">{{item.nickName}}</el-col>
        <el-col :span="11" style="margin-top: 5px;text-align: right;font-size: 12px">{{item.commentDate}}</el-col>
      </el-row>
        <div class="content" @click="viewArticle(item.articleId)">
          {{item.commentContent.length > 30 ? item.commentContent.slice(0,20):item.commentContent}}
        </div>
    </div>
  </el-card>
</template>

<script>
  export default {
    name: "NewComment",
    data() {
      return {
        remarks: [
        ]
      }
    },
    mounted() {
      this.getNewComment()
    },
    methods: {
      getNewComment(){
        var _this = this
        this.$axios.get('/api/comment/newComment').then(resp =>{
          if (resp && resp.data.code === 200){
            _this.remarks = resp.data.result
          }
        })
      },
      viewArticle(id) {
        if (id == -1){
          this.$router.push('/font/about')
        }else{
          let articleUrl = this.$router.resolve(
            {
              path: '../../font/article',
              query: {
                id: id
              }
            }
          )
          window.open(articleUrl.href, "_self")
        }
      },
    },

  }
</script>

<style scoped>
  .text {
    font-size: 16px;
  }

  .item {
    margin-bottom: 18px;
  }

  .box-card {
    width: 280px;
    border-radius: 10px;
  }

  .content {
    padding-left: 5px;
    margin-top: 5px;
    border: gray solid;
    border-radius: 10px;
    color: #6b7280;
  }
</style>
