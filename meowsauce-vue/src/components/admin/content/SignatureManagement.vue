<template>
  <el-row style="margin: 18px 0px 0px 18px ">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/admin/dashboard' }">管理中心</el-breadcrumb-item>
      <el-breadcrumb-item>内容管理</el-breadcrumb-item>
      <el-breadcrumb-item>友链管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-table
      :data="signature"
      stripe
      style="width: 100%;margin-top: 20px">
      <el-table-column
        stripe
        prop="realDate"
        label="日期"
        width="300">
      </el-table-column>
      <el-table-column
        prop="signatureContent"
        label="签名"
        width="400">
      </el-table-column>
      <el-table-column
        prop="signatureIcon"
        label="图标"
        width="150">
      </el-table-column>
      <el-table-column
        label="操作"
        width="200">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row)" type="text" size="small">添加签名</el-button>
          <el-button @click="deleteSignature(scope.row)" type="text" size="small">删除签名</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="添加签名" :visible.sync="dialogFormVisible">
      <el-form :model="submitSignature">
        <el-form-item label="签名">
          <el-input v-model="submitSignature.signatureContent" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="添加签名">
          <el-select v-model="submitSignature.signatureIcon" placeholder="请选择签名图标">
            <el-option label="小电脑" value="el-icon-s-platform"></el-option>
            <el-option label="纸飞机" value="el-icon-s-promotion"></el-option>
            <el-option label="电灯泡" value="el-icon-s-opportunity"></el-option>
            <el-option label="头戴耳机" value="el-icon-headset"></el-option>
            <el-option label="购物袋" value="el-icon-s-goods"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveSignature()">确 定</el-button>
      </div>
    </el-dialog>
  </el-row>
</template>
<script>
  export default {
    name: "SignatureManagement",
    data() {
      return {
        signature: [],
        dialogFormVisible: false,
        formLabelWidth: '120px',
        submitSignature: {signatureContent: '',signatureIcon: ''}
      }
    },
    mounted() {
      this.getAllSignature()
    },
    methods: {
      handleClick(row) {
        this.dialogFormVisible = true
      },
      deleteSignature(row){
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$axios.delete("/api/content/signature/delete/" + row.id).then(resp =>{
            if (resp && resp.data.code === 200){
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              this.getAllSignature()
            }else{
              this.$message.error("未知错误")
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
        console.log(row)
      },
      getAllSignature(){
        this.$axios.get("/api/signature/getAll").then(resp =>{
          if (resp && resp.data.code === 200){
            this.signature = resp.data.result
          }
        })
      },
      saveSignature(){
        this.$axios.post("/api/content/signature/save",{
          signatureContent: this.submitSignature.signatureContent,
          signatureIcon: this.submitSignature.signatureIcon
        }).then(resp =>{
          if (resp && resp.data.code === 200){
            this.$message({
              message: '保存成功',
              type: 'success'
            });
            this.submitSignature = {}
            this.getAllSignature()
            this.dialogFormVisible = false
          }else {
            this.$message.error('未知错误');
            this.dialogFormVisible = false
          }
        })
      }
    },
  }
</script>

<style scoped>

</style>
