<template>
  <div>
    <el-dialog
      title="查看评论信息"
      :visible.sync="dialogFormVisible">
      <el-form v-model="selectCommentMsg" style="text-align: left" ref="dataForm">
        <el-form-item label="头像" label-width="120px">
          <el-avatar :size="50" :src="selectCommentMsg.cover"></el-avatar>
        </el-form-item>
        <el-form-item label="评论内容" label-width="120px" prop="commentContent">
          <label>{{selectCommentMsg.commentContent}}</label>
        </el-form-item>
        <el-form-item label="访客昵称" label-width="120px" prop="nickName">
          <el-input v-model="selectCommentMsg.nickName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="头像地址" label-width="120px" prop="cover">
          <el-input v-model="selectCommentMsg.cover" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="访客邮箱" label-width="120px" prop="email">
          <el-input v-model="selectCommentMsg.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="访客网站" label-width="120px" prop="website">
          <el-input v-model="selectCommentMsg.website" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-row style="margin: 18px 0px 0px 18px ">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/admin/dashboard' }">管理中心</el-breadcrumb-item>
        <el-breadcrumb-item>内容管理</el-breadcrumb-item>
        <el-breadcrumb-item>评论管理</el-breadcrumb-item>
      </el-breadcrumb>
      <el-select v-model="value" filterable placeholder="请选择文章" style="margin-top: 20px">
        <el-option
          v-for="item in options"
          :key="item.id"
          :label="item.articleTitle"
          :value="item.id">
        </el-option>
      </el-select>
      <el-card style="margin: 18px 2%;width: 95%">
        <el-table
          :data="CommentDTO"
          stripe
          :default-sort="{prop: 'cId', order: 'ascending'}"
          style="width: 100%"
          :max-height="tableHeight">
          <el-table-column
            type="selection"
            width="55">
          </el-table-column>
          <el-table-column
            prop="cid"
            label="cId"
            sortable
            width="100">
          </el-table-column>
          <el-table-column
            prop="parentCommentId"
            label="父评论Id"
            show-overflow-tooltip
            fit>
          </el-table-column>
          <el-table-column
            prop="nickName"
            label="用户昵称"
          >
          </el-table-column>
          <el-table-column
            prop="commentDate"
            label="评论日期"
            fit>
          </el-table-column>
          <el-table-column
            label="操作"
            width="120">
            <template slot-scope="scope">
              <el-button
                @click="selectComment(scope.row)"
                type="text"
                size="small">
                查看评论
              </el-button>
              <el-button
                @click="deleteComment(scope.row)"
                type="text"
                size="small">
                移除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin: 20px 0 20px 0;float: left">
          <el-button>取消选择</el-button>
          <el-button>批量删除</el-button>
        </div>
      </el-card>
    </el-row>
  </div>
</template>

<script>
  export default {
    name: "CommentManagement",
    data() {
      return {
        options: [],
        value: '',
        CommentDTO: [],
        selectCommentMsg: [],
        dialogFormVisible: false,
      }
    },
    mounted() {
      this.listAllArticle()
    },
    methods: {
      listAllArticle() {
        var _this = this
        this.$axios.get('/api/admin/getAllArticle').then(resp => {
          if (resp && resp.data.code === 200) {
            _this.options = resp.data.result
            _this.value = resp.data.result[0].articleTitle
            console.log("查找文章")
            _this.listAllCommentByArticleTitle(_this.options[0].id)
          }
        })
      },
      listAllCommentByArticleTitle(articleId) {
        console.log("查找对应评论")
        var _this = this
        this.$axios.get('/api/admin/comment/' + articleId).then(resp => {
          if (resp && resp.data.code === 200) {
            _this.CommentDTO = resp.data.result
          }
        })
      },
      selectComment(Comment){
        this.dialogFormVisible = true
        this.selectCommentMsg = Comment
      },
      deleteComment(comment){
        console.log(comment)
        this.$confirm('此操作将永久删除该评论, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$axios.post('/api/admin/comment/delete',{
            cid:comment.cid
          }).then(resp => {
            if (resp && resp.data.code === 200){
              this.$message({
                message: '成功删除评论',
                type: 'success'
              });
              this.listAllCommentByArticleTitle(comment.articleId)
            }else{
              this.$message.error('未知错误');
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      }
    },
    watch: {
      value: {
        handler(){
          if (typeof this.value == "number") {
            this.listAllCommentByArticleTitle(this.value)
          }
        }
      }
    },
    computed: {
      tableHeight() {
        return window.innerHeight - 320
      }
    },
  }
</script>

<style scoped>

</style>
