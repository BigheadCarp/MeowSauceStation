<template>
  <div>
    <el-row style="margin: 18px 0px 0px 18px ">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/admin/dashboard'}">管理中心</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/content/book'}">内容管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/content/article'}">文章管理</el-breadcrumb-item>
        <el-breadcrumb-item>编辑器</el-breadcrumb-item>
      </el-breadcrumb>
    </el-row>
    <el-row>
      <el-input
        v-model="article.articleTitle"
        style="margin: 10px 0px;font-size: 18px;"
        placeholder="请输入标题"></el-input>
    </el-row>
    <el-row style="height: calc(100vh - 140px);">
      <mavon-editor
        v-model="article.articleContentMd"
        style="height: 100%;"
        ref=md
        @save="saveArticles"
        fontSize="16px"
      >
        <button type="button" class="op-icon el-icon-document" :title="'摘要/封面'" slot="left-toolbar-after"
                @click="dialogVisible = true"></button>
        <button type="button" class="op-icon el-icon-postcard" :title="'标签'" slot="left-toolbar-after"
                @click="labelDialogVisible = true"></button>
      </mavon-editor>
      <el-dialog
        :visible.sync="dialogVisible"
        width="30%">
        <el-divider content-position="left">文章分类</el-divider>
        <el-select v-model="article.articleType" placeholder="文章分类">
          <el-option
            v-for="item in type"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <el-divider content-position="left">摘要</el-divider>
        <el-input
          type="textarea"
          v-model="article.articleAbstract"
          rows="6"
          maxlength="255"
          show-word-limit></el-input>
        <el-divider content-position="left">封面</el-divider>
        <div style="margin-top: 20px">
          <el-input v-model="article.articleCover" autocomplete="off" placeholder="图片 URL"></el-input>
<!--          <img-upload @onUpload="uploadImg" :before-upload="onBeforeImgUpload" ref="imgUpload" style="margin-top: 5px"></img-upload>-->
          <input type="file" ref="file"  v-on:change="handleFileUpload($event)" style="margin-top: 10px">
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
        </span>
      </el-dialog>
      <el-dialog
        :visible.sync="labelDialogVisible"
        width="30%">
        <el-divider content-position="left">标签</el-divider>
        <el-tag
          :key="index"
          v-for="(tag,index) in label"
          closable
          :disable-transitions="false"
          @close="handleClose(tag)">
          {{tag}}
        </el-tag>
        <el-input
          class="input-new-tag"
          v-if="inputVisible"
          v-model="inputValue"
          ref="saveTagInput"
          size="small"
          @keyup.enter.native="handleInputConfirm"
          @blur="handleInputConfirm"
        >
        </el-input>
        <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
        <span slot="footer" class="dialog-footer">
          <el-button @click="labelDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="labelDialogVisible = false">确 定</el-button>
        </span>
      </el-dialog>
    </el-row>
  </div>
</template>

<script>
  import ImgUpload from './ImgUpload'

  export default {
    name: 'Editor',
    components: {ImgUpload},
    data() {
      return {
        article: {},
        dialogVisible: false,
        labelDialogVisible: false,
        label: [],
        type: [{
          value: '生活向',
          label: '生活向',
        }, {
          value: '技术向',
          label: '技术向',
        }, {
          value: '非原创',
          label: '非原创'
        }],
        value: '',
        inputVisible: false,
        inputValue: '',
      }
    },
    mounted() {
      if (this.$route.params.article) {
        this.article = this.$route.params.article
        this.getAllLabelByArticleId(this.article.id)
      }
    },
    methods: {
      saveArticles(value, render) {
        // value 是 md，render 是 html
        this.$confirm('是否保存并发布文章?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            this.$axios
              .post('/api/admin/content/article', {
                id: this.article.id,
                articleTitle: this.article.articleTitle,
                articleContentMd: value,
                articleContentHtml: render,
                articleAbstract: this.article.articleAbstract,
                articleCover: this.article.articleCover,
                articleDate: this.article.articleDate,
                articleType: this.article.articleType
              }).then(resp => {
              if (resp && resp.data.code === 200) {
                this.saveLabels(this.article.id)
                this.$message({
                  type: 'info',
                  message: '已保存成功'
                })
                this.$router.push('/admin')
              }
            })
          }
        ).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消发布'
          })
        })
      },
      handleClose(tag) {
        this.label.splice(this.label.indexOf(tag), 1);
      },

      showInput() {
        this.inputVisible = true;
        this.$nextTick(_ => {
          this.$refs.saveTagInput.$refs.input.focus();
        });
      },

      handleInputConfirm() {
        let inputValue = this.inputValue;
        if (inputValue) {
          this.label.push(inputValue);
        }
        this.inputVisible = false;
        this.inputValue = '';
      },
      getAllLabelByArticleId(articleId) {
        this.$axios.get("/api/getLabel/" + articleId).then(resp => {
          if (resp && resp.data.code === 200) {
            this.label = resp.data.result
          }
        })
      },
      saveLabels(articleId){
        this.$axios.post('/api/admin/content/label',{
          id: articleId,
          label: this.label
        }).then(resp =>{
          if (resp && resp.data.code === 200){
            console.log("标签保存成功")
          }
        })
      },
      // 获取文件
      handleFileUpload(event) {
        console.log("处理图片")
        // 阻止发生默认行为
        event.preventDefault();
        this.$confirm('此操作后将图片将上传至云盘, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '已发送请求!'
          });
          let formData = new FormData()
          let file = this.$refs.file.files[0]
          formData.append('file', file)
          this.postUpload(formData)
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '请求已取消'
          });
        });
      },
      postUpload(file){
        this.$axios({
          url: 'uploadFile',
          method: 'post',
          data:  file,
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }).then(resp =>{
          if (resp && resp.data.code === 200){
            this.article.articleCover = resp.data.result
            alert("上传成功")
          }else {
            alert("上传失败：" + resp.data.message)
          }
        })
      }
    }
  }
</script>
<style scoped>
  /deep/ .el-tag {
    margin-right: 10px;
  }

  .el-tag + .el-tag {
    margin-right: 10px;
  }

  .button-new-tag {
    margin-top: 10px;
    margin-left: 0px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }

  .input-new-tag {
    margin-top: 10px;
    width: 90px;
    vertical-align: bottom;
  }
</style>
