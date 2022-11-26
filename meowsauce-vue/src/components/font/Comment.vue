<template>
  <el-row>
<!--    PC端开始-->
    <el-col :xs="0" :lg="24">
      <el-card class="commentSize">
        <CommentForm v-if="parentCommentId === 0" :realParentCommentId="0" :articleId="articleId"
                     @submit="getSubmitMsg"/>
        <span>💬 评论区</span>
        <div v-for="(item,index) in commentDTOs" :key="item.cid">
          <div>
            <div style="display: inline-block;margin-top: 20px">
              <el-avatar :size="50" :src="item.cover"></el-avatar>
            </div>
            <div style="display: inline-block">
              <div style="display: inline-block">
                <span>{{item.nickName}}&nbsp;&nbsp;&nbsp;{{item.commentDate}}</span>
                <span class="replayPosition" v-if="parentCommentId === item.cid">
                  <a href="#" @click="setReply(item.cid)">取消</a>
                </span>
                <span class="replayPosition" v-if="parentCommentId != item.cid">
                  <a href="#" @click="setReply(item.cid)">回复</a>
                </span>
              </div>
              <div class="commentBorder">
                <span class="commentContent">&emsp;{{item.commentContent}}</span>
              </div>
            </div>
          </div>
          <CommentForm v-if="parentCommentId === item.cid" :realParentCommentId="item.cid" :articleId="articleId"
                       @submit="getSubmitMsg"/>
          <div v-for="(children) in item.children" :key="children.cid">
            <div>
              <div style="display: inline-block;margin-top: 20px;margin-left: 20px">
                <el-avatar :size="40" :src="children.cover"></el-avatar>
              </div>
              <div style="display: inline-block;margin-top: 20px">
                <div style="display: inline-block">
                  <span>{{children.nickName}}&nbsp;&nbsp;&nbsp;{{children.commentDate}}</span>
                  <span class="replayPosition" v-if="parentCommentId === children.cid">
                    <a href="#" @click="setChildrenReply(children.cid)">取消</a>
                  </span>
                  <span class="replayPosition" v-if="parentCommentId != children.cid">
                    <a href="#" @click="setChildrenReply(children.cid)">回复</a>
                  </span>
                </div>
                <div class="ChildrenCommentBorder">
                  <span class="commentContent">&emsp;{{children.commentContent}}</span>
                </div>
              </div>
            </div>
            <CommentForm v-if="parentCommentId===children.cid" :realParentCommentId="item.cid" :articleId="articleId"
                         style="margin-left: 20px" @submit="getSubmitMsg">
            </CommentForm>
          </div>
        </div>
      </el-card>
    </el-col>
    <!--PC端结束-->
    <!--移动端开始-->
    <el-col :xs="24" :lg="0">
      <el-card class="commentSizeByMobile">
        <CommentForm v-if="parentCommentId === 0" :realParentCommentId="0" :articleId="articleId"
                     @submit="getSubmitMsg"/>
        <div style="margin-top: 150px">
          <span>💬 评论区</span>
        <div v-for="(item,index) in commentDTOs" :key="item.cid">
          <div>
            <div style="display: inline-block;margin-top: 20px">
              <el-avatar :size="40" :src="item.cover"></el-avatar>
              <span>{{item.nickName}}</span>
              <br>
              <span>{{item.commentDate}}</span>
              <span class="replayPosition" v-if="parentCommentId === item.cid">
                <a href="#" @click="setReply(item.cid)">取消</a>
              </span>
              <span class="replayPosition" v-if="parentCommentId != item.cid">
                <a href="#" @click="setReply(item.cid)">回复</a>
              </span>
            </div>
            <div style="display: inline-block">
              <div class="commentBorderByMobile">
                <span class="commentContent">&emsp;{{item.commentContent}}</span>
              </div>
            </div>
          </div>
          <CommentForm v-if="parentCommentId === item.cid" :realParentCommentId="item.cid" :articleId="articleId"
                       @submit="getSubmitMsg" style="margin-bottom: 150px"/>
          <div v-for="(children) in item.children" :key="children.cid">
            <div>
              <div style="display: inline-block;margin-top: 20px;margin-left: 20px">
                <el-avatar :size="30" :src="children.cover"></el-avatar>
                <span>{{children.nickName}}</span>
                <br>
                <span>{{children.commentDate}}</span>
                <span class="replayPosition" v-if="parentCommentId === children.cid">
                  <a href="#" @click="setChildrenReply(children.cid)">取消</a>
                </span>
                <span class="replayPosition" v-if="parentCommentId != children.cid">
                  <a href="#" @click="setChildrenReply(children.cid)">回复</a>
                </span>
              </div>
              <div style="display: inline-block;">
                <div class="ChildrenCommentBorderByMobile">
                  <span class="commentContent">&emsp;{{children.commentContent}}</span>
                </div>
              </div>
            </div>
            <CommentForm v-if="parentCommentId===children.cid" :realParentCommentId="item.cid" :articleId="articleId"
                         style="margin-left: 20px;margin-bottom: 150px" @submit="getSubmitMsg">
            </CommentForm>
          </div>
        </div>
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
  import CommentForm from "./CommentForm";

  export default {
    name: "Comment",
    components: {CommentForm},
    data() {
      return {
        commentDTOs: [],
        parentCommentId: 0,
      }
    },
    props: ['articleId'],
    // mounted () {
    //   console.log("Comment组件中的articleId" + this.articleId)
    //   this.loadCommentDTOs(this.articleId)
    // },
    watch: {
      articleId: {
        handler() {
          if (this.articleId != undefined) {
            this.loadCommentDTOs(this.articleId)
          }
        }
      }
    },
    methods: {
      //设置回复的父亲id
      setReply(id) {
        if (this.parentCommentId === 0) {
          this.parentCommentId = id
        } else {
          this.parentCommentId = 0
        }
      },
      //设置回复的父亲id
      setChildrenReply(id) {
        if (this.parentCommentId === 0) {
          this.parentCommentId = id
        } else {
          this.parentCommentId = 0
        }
      },
      //接受子组件信息
      getSubmitMsg() {
        this.parentCommentId = 0
        this.loadCommentDTOs(this.articleId)
      },
      loadCommentDTOs(articleId) {
        var _this = this
        this.$axios.get('/api/comment/' + articleId).then(resp => {
          if (resp && resp.data.code === 200) {
            _this.commentDTOs = resp.data.result
          }
        })
      },
    },
    computed: {}
  }
</script>

<style scoped>
  .commentSize {
    margin-top: 20px;
    width: 926px;
  }
  .commentSizeByMobile{
    margin-top: 20px;
    width: 370px;
  }

  /*评论框*/
  .commentContent {
    background-color: #F5F7FA;
    border-color: #E4E7ED;
    cursor: unset;
  }

  .ChildrenCommentBorder {
    height: 50px;
    width: 790px;
    margin-top: 10px;
    background-color: #F5F7FA;
    border: solid #E4E7ED;
    border-radius: 10px;
  }
  .ChildrenCommentBorderByMobile{
    height: 50px;
    width: 310px;
    margin-top: 10px;
    margin-left: 20px;
    background-color: #F5F7FA;
    border: solid #E4E7ED;
    border-radius: 10px;
  }

  .commentBorder {
    height: 50px;
    width: 800px;
    margin-top: 10px;
    background-color: #F5F7FA;
    border: solid #E4E7ED;
    border-radius: 10px;
  }
  .commentBorderByMobile{
    height: 50px;
    width: 330px;
    margin-top: 10px;
    background-color: #F5F7FA;
    border: solid #E4E7ED;
    border-radius: 10px;
  }
  .replayPosition {
    position: absolute;
    left: 89%;
  }

  /deep/ .el-card__body .el-main {
    padding: 0;
    margin: 0;
  }

  a {
    text-decoration: none;
  }
</style>
