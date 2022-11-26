<template>
  <div>
    <el-dialog
      title="修改用户信息"
      :visible.sync="dialogFormVisible">
      <el-form v-model="selectedUser" style="text-align: left" ref="dataForm">
        <el-form-item label="用户名" label-width="120px" prop="username">
          <label>{{selectedUser.userName}}</label>
        </el-form-item>
        <el-form-item label="真实姓名" label-width="120px" prop="name">
          <el-input v-model="selectedUser.userNickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号" label-width="120px" prop="phone">
          <el-input v-model="selectedUser.userTelephoneNumber" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" label-width="120px" prop="email">
          <el-input v-model="selectedUser.userEmail" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" label-width="120px" prop="password">
          <el-button type="warning" @click="resetPassword(selectedUser.userName)">重置密码</el-button>
        </el-form-item>
        <el-form-item label="角色分配" label-width="120px" prop="roles">
          <el-checkbox-group v-model="selectedRolesIds">
            <el-checkbox v-for="(role,i) in roles" :key="i" :label="role.id">{{role.nameZh}}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="onSubmit(selectedUser)">确 定</el-button>
      </div>
    </el-dialog>
    <el-row style="margin: 18px 0px 0px 18px ">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/admin/dashboard' }">管理中心</el-breadcrumb-item>
        <el-breadcrumb-item>用户管理</el-breadcrumb-item>
        <el-breadcrumb-item>用户信息</el-breadcrumb-item>
      </el-breadcrumb>
    </el-row>
    <bulk-registration @onSubmit="listUsers()"></bulk-registration>
    <el-card style="margin: 18px 2%;width: 95%">
      <el-table
        :data="users"
        stripe
        :default-sort="{prop: 'id', order: 'ascending'}"
        style="width: 100%"
        :max-height="tableHeight">
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column
          prop="userId"
          label="userId"
          sortable
          width="100">
        </el-table-column>
        <el-table-column
          prop="userName"
          label="用户名"
          fit>
        </el-table-column>
        <el-table-column
          prop="userNickname"
          label="真实姓名"
          fit>
        </el-table-column>
        <el-table-column
          prop="userTelephoneNumber"
          label="手机号"
          fit>
        </el-table-column>
        <el-table-column
          prop="userEmail"
          label="邮箱"
          show-overflow-tooltip
          fit>
        </el-table-column>
        <el-table-column
          label="状态"
          sortable
          width="100">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.enabled"
              active-color="#13ce66"
              inactive-color="#ff4949"
              @change="(value) => commitStatusChange(value, scope.row)">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          width="120">
          <template slot-scope="scope">
            <el-button
              @click="editUser(scope.row)"
              type="text"
              size="small">
              编辑
            </el-button>
            <el-button
              @click="deleteUser(scope.row)"
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
  </div>
</template>

<script>
  import BulkRegistration from './BulkRegistration'
  export default {
    name: 'UserProfile',
    components: {BulkRegistration},
    data () {
      return {
        users: [],
        roles: [],
        dialogFormVisible: false,
        selectedUser: [],
        selectedRolesIds: []
      }
    },
    mounted () {
      this.listUsers()
      this.listRoles()
    },
    computed: {
      tableHeight () {
        return window.innerHeight - 320
      }
    },
    methods: {
      listUsers () {
        var _this = this
        this.$axios.get('/api/admin/user').then(resp => {
          if (resp && resp.data.code === 200) {
            _this.users = resp.data.result
          }
        })
      },
      listRoles () {
        var _this = this
        this.$axios.get('/api/admin/role').then(resp => {
          if (resp && resp.data.code === 200) {
            _this.roles = resp.data.result
          }
        })
      },
      commitStatusChange (value, user) {
        console.log(user)
        if (user.userName !== 'admin') {
          this.$axios.put('/admin/user/status', {
            enabled: value,
            userId: user.userId
          }).then(resp => {
            if (resp && resp.data.code === 200) {
              if (value) {
                this.$message('用户 [' + user.userName + '] 已启用')
              } else {
                this.$message('用户 [' + user.userName + '] 已禁用')
              }
            }
          })
        } else {
          user.enabled = true
          this.$alert('不能禁用管理员账户')
        }
      },
      onSubmit (user) {
        let _this = this
        // 根据视图绑定的角色 id 向后端传送角色信息
        let roles = []
        for (let i = 0; i < _this.selectedRolesIds.length; i++) {
          for (let j = 0; j < _this.roles.length; j++) {
            if (_this.selectedRolesIds[i] === _this.roles[j].id) {
              roles.push(_this.roles[j])
            }
          }
        }
        this.$axios.put('/admin/user', {
          userName: user.userName,
          userNickname: user.userNickname,
          userTelephoneNumber: user.userTelephoneNumber,
          userEmail: user.userEmail,
          roles: roles
        }).then(resp => {
          if (resp && resp.data.code === 200) {
            this.$alert('用户信息修改成功')
            this.dialogFormVisible = false
            // 修改角色后重新请求用户信息，实现视图更新
            this.listUsers()
          } else {
            this.$alert(resp.data.message)
          }
        })
      },
      editUser (user) {
        this.dialogFormVisible = true
        this.selectedUser = user
        let roleIds = []
        for (let i = 0; i < user.roles.length; i++) {
          roleIds.push(user.roles[i].id)
        }
        this.selectedRolesIds = roleIds
      },
      deleteUser (user){
        this.$axios.post('/admin/delete', {
          userId: user.userId,
        }).then(resp => {
          if (resp && resp.data.code === 200) {
            this.$alert(resp.data.result)
            // 删除用户后重新请求用户信息，实现视图更新
            this.listUsers()
          } else {
            this.$alert(resp.data.message)
          }
        })
      },
      resetPassword (username) {
        this.$axios.put('/admin/user/password', {
          userName: username
        }).then(resp => {
          if (resp && resp.data.code === 200) {
            this.$alert('密码已重置为 123')
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
