<template>
  <el-row style="margin: 18px 0px 0px 18px ">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/admin/dashboard' }">管理中心</el-breadcrumb-item>
      <el-breadcrumb-item>内容管理</el-breadcrumb-item>
      <el-breadcrumb-item>友链管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-button type="success" round style="margin-top: 20px" @click="dialogFormVisible = true">添加友链</el-button>
    <el-dialog title="添加友链" :visible.sync="dialogFormVisible">
      <el-form :model="addFriend">
        <el-form-item label="友链名称" :label-width="formLabelWidth">
          <el-input v-model="addFriend.friendName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="友链图标" :label-width="formLabelWidth">
          <el-avatar :size="50" :src="addFriend.friendIcon"></el-avatar>
          <el-input v-model="addFriend.friendIcon"></el-input>
        </el-form-item>
        <el-form-item label="友链地址" :label-width="formLabelWidth">
          <el-input v-model="addFriend.friendAddress"></el-input>
        </el-form-item>
        <el-form-item label="友链介绍" :label-width="formLabelWidth">
          <el-input v-model="addFriend.friendIntroduce"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="toAddFriend">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="修改友链" :visible.sync="UpdateDialogFormVisible">
      <el-form :model="addFriend">
        <el-form-item label="友链名称" :label-width="formLabelWidth">
          <el-input v-model="updateFriend.friendName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="友链图标" :label-width="formLabelWidth">
          <el-avatar :size="50" :src="updateFriend.friendIcon"></el-avatar>
          <el-input v-model="updateFriend.friendIcon"></el-input>
        </el-form-item>
        <el-form-item label="友链地址" :label-width="formLabelWidth">
          <el-input v-model="updateFriend.friendAddress"></el-input>
        </el-form-item>
        <el-form-item label="友链介绍" :label-width="formLabelWidth">
          <el-input v-model="updateFriend.friendIntroduce"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="UpdateDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="toUpdateFriend">确 定</el-button>
      </div>
    </el-dialog>
    <el-table
      :data="friends"
      style="width: 100%;margin-top: 20px">
      <el-table-column
        label="友链名称"
        width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.friendName }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="友链图标"
        width="180">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>图标地址: {{ scope.row.friendIcon }}</p>
            <div slot="reference" class="name-wrapper">
              <el-avatar :size="50" :src="scope.row.friendIcon"></el-avatar>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        label="友链介绍"
        width="180">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.friendIntroduce }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="友链地址"
        width="200">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.friendAddress }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)">编辑
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-row>
</template>

<script>
  export default {
    name: "FriendManagement",
    data() {
      return {
        friends: [],
        dialogFormVisible: false,
        UpdateDialogFormVisible: false,
        addFriend: {
          friendName: '',
          friendAddress: '',
          friendIcon: '',
          friendIntroduce: '',
        },
        updateFriend: {},
        formLabelWidth: '100px'
      }
    },
    mounted() {
      this.LoadFriend()
    },
    methods: {
      toAddFriend() {
        this.$axios.post('/api/admin/content/friend/saveOrUpdate', {
          friendName: this.addFriend.friendName,
          friendAddress: this.addFriend.friendAddress,
          friendIcon: this.addFriend.friendIcon,
          friendIntroduce: this.addFriend.friendIntroduce
        }).then(resp => {
          if (resp && resp.data.code === 200) {
            this.$message({
              type: 'success',
              message: '添加成功'
            })
            this.LoadFriend()
            this.dialogFormVisible = false
          } else {
            this.$message({
              type: 'error',
              message: '未知错误'
            })
            this.dialogFormVisible = false
          }
        })
      },
      toUpdateFriend() {
        this.$axios.post('/api/admin/content/friend/saveOrUpdate', {
          id: this.updateFriend.id,
          friendName: this.updateFriend.friendName,
          friendAddress: this.updateFriend.friendAddress,
          friendIcon: this.updateFriend.friendIcon,
          friendIntroduce: this.updateFriend.friendIntroduce
        }).then(resp => {
          if (resp && resp.data.code === 200) {
            this.$message({
              type: 'success',
              message: '修改成功'
            })
            this.LoadFriend()
            this.UpdateDialogFormVisible = false
          } else {
            this.$message({
              type: 'error',
              message: '未知错误'
            })
            this.UpdateDialogFormVisible = false
          }
        })
      },
      handleEdit(index, row) {
        this.UpdateDialogFormVisible = true
        this.updateFriend = row
        console.log(index, row);
      },
      handleDelete(index, row) {
        this.$confirm('此操作将删除该友链, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$axios.delete("/api/admin/content/friend/deleteFriend/" + row.id).then(resp => {
            if (resp.data.code === 200) {
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              this.LoadFriend()
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
        console.log(index, row);
      },
      LoadFriend() {
        this.$axios.get("/api/friends/getAllFriend").then(resp => {
          if (resp && resp.data.code === 200) {
            this.friends = resp.data.result
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
