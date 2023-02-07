<template>
  <div class="app-container">
    <!-- 工具条 -->
    <el-card class="operate-container" shadow="never">
      <i class="el-icon-tickets" style="margin-top: 5px" />
      <span style="margin-top: 5px">用户列表</span>
      <el-button class="btn-add" size="mini" @click="addAdmin">添加用户</el-button>
    </el-card>

    <table class="tableCss">
      <tr style="text-align: center;">
        <th>ID</th>
        <th>用户名</th>
        <th>角色</th>
        <th>操作</th>
      </tr>
      <tr v-for="item in list" style="text-align: center;">
        <td>{{ item.id }}</td>
        <td>{{ item.username }}</td>
        <td>{{ item.roleName }}</td>
        <td>
          <button @click="removeDataById(item.id)">删除</button>
          <button @click="showAssignRole(item.id, item.username)">分配角色</button>
        </td>
      </tr>
    </table>

    <el-dialog title="添加/修改" :visible.sync="dialogVisible" width="40%">
      <el-form ref="dataForm" label-width="150px" size="small" style="padding-right: 40px;">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="adminUsername" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="adminPassword" type="password" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" size="small" @click="addUser">确定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="分配角色" :visible.sync="dialogRoleVisible">
      <el-form label-width="80px">
        <el-form-item label="用户名">
          <el-input disabled :value="adminUsername" />
        </el-form-item>
        <el-form-item label="角色列表">
          <el-select v-model="roleName">
            <el-option v-for="item in allRoles" :key="item.roleId" :value="item.roleName"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button type="primary" size="small" @click="assignRole">保存</el-button>
        <el-button size="small" @click="dialogRoleVisible = false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import api from '@/api/acl/admin';

const defaultForm = {
  id: '',
  username: '',
  password: '',
  name: '',
  phone: '',
  wareId: ''
}
export default {
  data() {
    return {
      listLoading: true, // 数据是否正在加载
      list: null, // banner列表
      dialogVisible: false,
      admin: defaultForm,
      saveBtnDisabled: false,
      dialogRoleVisible: false,
      userRoleIds: [], // 用户的角色ID的列表
      isIndeterminate: false, // 是否是不确定的
      checkAll: false, // 是否全选

      // 修改添加用户数据所用到的内部状态
      adminId: '',
      adminUsername: '',
      adminPassword: '',

      // 分配角色用到的内部状态
      roleId: -1,
      roleName: '',
      allRoles: []
    }
  },
  // 生命周期函数：内存准备完毕，页面尚未渲染
  created() {
    this.fetchData();
  },
  methods: {
    // 加载banner列表数据
    fetchData() {
      api.getPageList().then(
        response => {
          this.list = response.data;
          // 数据加载并绑定成功
          this.listLoading = false;
        }
      )
    },
    // 根据id删除数据
    removeDataById(id) {
      // debugger
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => { // promise
        // 点击确定，远程调用ajax
        return api.removeById(id)
      }).then((response) => {
        this.fetchData(this.page)
        if (response.code) {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    addAdmin() {
      this.dialogVisible = true;
      this.adminId = '';
      this.adminUsername = '';
      this.adminPassword = '';
    },
    edit(id) {
      this.dialogVisible = true;
      this.fetchDataById(id);
    },
    fetchDataById(id) {
      api.getById(id).then(response => {
        const result = response.data;
        this.adminId = result.id;
        this.adminUsername = result.username;
        this.adminPhoneNumber = result.phoneNumber;
        this.adminPassword = result.password;
      })
    },
    saveOrUpdate() {
      if (this.adminId === '') {
        this.saveData();
      } else {
        this.updateData();
      }
    },
    addUser() {
      api.addUser(this.adminUsername, this.adminPassword).then(response => {
        this.$message({
          type: 'success',
          message: response.message
        });
        this.dialogVisible = false;
        this.fetchData(this.page);
      })
    },
    // 新增
    saveData() {
      api.save({ adminUsername: this.adminUsername, adminPassword: this.adminPassword}).then(response => {
        if (response.code) {
          this.$message({
            type: 'success',
            message: response.message
          })
          this.dialogVisible = false
          this.fetchData(this.page)
        }
      })
    },
    // 根据id更新记录
    updateData() {
      api.updateById(this.admin).then(response => {
        if (response.code) {
          this.$message({
            type: 'success',
            message: response.message
          })
          this.dialogVisible = false
          this.fetchData(this.page)
        }
      })
    },
    showAssignRole(admin_id, admin_username) {
      this.dialogRoleVisible = true;
      this.adminUsername = admin_username;
      this.adminId = admin_id;
      this.getRoles(admin_id);
    },
    getRoles(admin_id) {
      api.getAllRoles().then(response => {
        this.allRoles = response.data;
        api.getRoleByAdminId(admin_id).then(response => {
          this.roleId = response.data;
          for (let i = 0; i < this.allRoles.length; i++) {
            if (this.allRoles[i].id === this.roleId) {
              this.roleName = this.allRoles[i].roleName;
              break;
            }
          }
        });
      });
    },
    assignRole() {
      for (let i = 0; i < this.allRoles.length; i++) {
        if (this.allRoles[i].roleName === this.roleName) {
          this.roleId = this.allRoles[i].id;
          break;
        }
      }
      api.assignRoles(this.adminId, this.roleId).then(response => {
        this.$message.success(response.message || '分配角色成功');
        this.dialogRoleVisible = false;
        this.fetchData(this.page);
      })
    }
  }
}
</script>

<style scoped>
.tableCss {
  width: 100%;
  margin-top: 10px;
  border: 1px solid #ebeef5;
}

table>tr>th {
  padding: 10px;
}

table>tr>td {
  padding: 10px;
}

button {
  margin: 10px;
}
</style>