<template>
  <div>
<!--  <el-button @click="getFile" style="margin-top: 10px">-->
<!--    <i class="el-icon-upload"></i>-->
<!--  </el-button>-->
    <input type="file" ref="file"  v-on:change="handleFileUpload($event)">请求
    <input type="file" style="margin-top: 5px;color: blue" v-on:@change="handleFileUpload($event)"></input>
  </div>
</template>

<script>
  export default {
    name: "Test",
    data() {
      return {}
    },
    methods: {
      // 打开文件
      getFile() {
        this.$refs.file.click()
      },
      // 获取文件
      handleFileUpload(event) {
        // 阻止发生默认行为
        event.preventDefault();
        let formData = new FormData()
        let file = this.$refs.file.files[0]
        formData.append('file', file)
        this.postUpload(formData)
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
            alert("上传成功")
          }else {
            alert("上传失败")
          }
        })
      }
    }
  }
</script>

<style scoped>
</style>
