<template>
  <el-row>
    <el-col :xs="0" :lg="24">
      <div class="CardStyle">
        <el-form :inline="true" :model="CommentDTO">
          <el-avatar :src="CommentDTO.cover"></el-avatar>
          <el-form-item style="margin-left: 5px">
            <el-input @blur="getVisitorMessage" v-model="CommentDTO.qq" placeholder="填写QQ获取邮箱与昵称"
                      style="width: 380px"></el-input>
          </el-form-item>
          <el-form-item style="margin-left: 5px">
            <el-input v-model="CommentDTO.nickName" placeholder="昵称" style="width: 400px;"></el-input>
          </el-form-item>
          <br>
          <el-form-item>
            <el-input v-model="CommentDTO.email" placeholder="邮箱" style="width: 430px"></el-input>
          </el-form-item>
          <el-form-item style="margin-left: 5px">
            <el-input v-model="CommentDTO.website" placeholder="http(s)://主页" style="width: 400px"></el-input>
          </el-form-item>
          <br>
          <el-form-item label="">
            <el-input type="textarea" v-model="CommentDTO.commentContent" style="width: 850px;" :rows="4"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit" style="margin-left: 750px">提交评论</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-col>
    <el-col :xs="24" :lg="0">
      <div class="CardStyleByMobile">
        <el-form :inline="true" :model="CommentDTO">
          <el-avatar :src="CommentDTO.cover"></el-avatar>
          <el-form-item>
            <el-input @blur="getVisitorMessage" v-model="CommentDTO.qq" placeholder="填写QQ获取邮箱与昵称"
                      style="width: 330px"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="CommentDTO.nickName" placeholder="昵称" style="width: 330px;"></el-input>
          </el-form-item>
          <br>
          <el-form-item>
            <el-input v-model="CommentDTO.email" placeholder="邮箱" style="width: 330px"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="CommentDTO.website" placeholder="http(s)://主页" style="width: 330px"></el-input>
          </el-form-item>
          <br>
          <el-form-item label="">
            <el-input type="textarea" v-model="CommentDTO.commentContent" style="width: 330px;" :rows="4"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit" style="margin-left: 230px">提交评论</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-col>
  </el-row>
</template>

<script>
  export default {
    name: "CommentForm",
    data() {
      return {
        CommentDTO: {
          qq: '',
          parentCommentId: 0,
          commentContent: '',
          nickName: '',
          cover: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
          email: '',
          website: '',
          user: '',
          region: ''
        },
        isSubmit: true
      }
    },
    props: [
      'realParentCommentId',
      'articleId'
    ],
    methods: {
      getVisitorMessage() {
        var _this = this
        this.$axios.get('https://api.usuuu.com/qq/' + this.CommentDTO.qq, {withCredentials: false}).then(resp => {
          if (resp && resp.data.code === 200) {
            _this.CommentDTO.cover = resp.data.data.avatar
            _this.CommentDTO.nickName = resp.data.data.name
            _this.CommentDTO.email = resp.data.data.qq + '@qq.com'
          }
        })
      },
      onSubmit() {
        this.$axios.post('/api/comment/save', {
          articleId: this.articleId,
          commentContent: this.CommentDTO.commentContent,
          parentCommentId: this.realParentCommentId,
          nickName: this.CommentDTO.nickName,
          cover: this.CommentDTO.cover,
          email: this.CommentDTO.email,
          website: this.CommentDTO.website
        }).then(resp => {
          if (resp && resp.data.code === 200) {
            this.$message({
              message: '评论成功',
              type: 'success'
            });
            this.$emit('submit', this.isSubmit)
          }
        })
        this.clearForm()
        this.CommentDTO.cover = 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'
      },
      clearForm() {
        this.CommentDTO = {}
      }
    },
    computed: {}
  }
</script>

<style scoped>
  .CardStyle {
    width: 926px;
    height: 300px;
    margin-top: 10px;
    /*border-radius: 10px;*/
  }
  .CardStyleByMobile{
    width: 380px;
    height: 300px;
    margin-top: 10px;
  }

  /deep/ .el-card__body {
    padding: 0;
    margin: 0;
  }
</style>
