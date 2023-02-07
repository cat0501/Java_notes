<template>
  <div>
    <!-- 工具条 -->
    <el-card class="operate-container" shadow="never">
      <i class="el-icon-tickets" style="margin-top: 5px"></i>
      <span style="margin-top: 5px">数据列表</span>
      <el-button class="btn-add" size="mini" @click="addRole">添加新角色</el-button>
    </el-card>

    <table class="tableCss">
      <tr style="text-align: center;">
        <th>ID</th>
        <th>角色名称</th>
        <th>操作</th>
      </tr>
      <tr v-for="item in roles" style="text-align: center;">
        <td>{{ item.id }}</td>
        <td>{{ item.roleName }}</td>
        <td>
          <button @click="removeRole(item.id, item.roleName)">删除角色</button>
          <button @click="showAssignRoleDialog(item.id, item.roleName)">分配权限</button>
        </td>
      </tr>
    </table>

    <el-dialog title="添加/修改" :visible.sync="dialogVisible" width="40%">
      <el-input disabled :value="roleName" />
      <el-tree ref="tree" style="margin: 20px 0" :data="allPermissions" node-key="id" show-checkbox default-expand-all
        :props="defaultProps" />
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" size="small" @click="assignRoles()">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'RoleList',
  data() {
    return {
      dialogVisible: false,
      allPermissions: [],
      roleId: -1,
      roleName: '',
      defaultProps: {
        children: 'children',
        label: 'permissionName'
      },
      listLoading: true, // 数据是否正在加载
      roles: [], // 角色列表
      selectedRoles: [] // 所有选中的角色列表
    }
  },
  mounted() {
    this.getRoles()
  },
  methods: {
    /*
    添加角色
    */
    addRole() {
      // 显示添加界面
      this.$prompt('请输入新名称', '添加角色', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({ value }) => {
        this.$API.role.save({ roleName: value }).then(result => {
          this.$message.success(result.message || '添加角色成功')
          this.getRoles()
        })
      }).catch(() => {
        this.$message.warning('取消添加')
      })
    },
    showAssignRoleDialog(roleId, roleName) {
      this.roleId = roleId;
      this.roleName = roleName;
      this.dialogVisible = true;
      this.$API.permission.toAssign(roleId).then(result => {
        const allPermissions = result.data
        this.allPermissions = allPermissions
        const checkedIds = this.getCheckedIds(allPermissions)
        console.log('getPermissions() checkedIds', checkedIds)
        this.$refs.tree.setCheckedKeys(checkedIds)
      });
    },

    /*
      得到所有选中的id列表
      */
    getCheckedIds(auths, initArr = []) {
      return auths.reduce((pre, item) => {
        console.log(item.select);
        if (item.select && item.level === 3) {
          pre.push(item.id)
        } else if (item.children) {
          this.getCheckedIds(item.children, initArr)
        }
        return pre
      }, initArr)
    },

    /*
    异步获取角色分页列表
    */
    getRoles(page = 1) {
      this.page = page
      this.listLoading = true
      this.$API.role.getPageList().then(
        result => {
          this.roles = result.data;
        }
      ).finally(() => {
        this.listLoading = false;
      })
    },
    /*
    删除指定的角色
    */
    removeRole(roleId, roleName) {
      this.$confirm(`确定删除 '${roleName}' 吗?`, '提示', {
        type: 'warning'
      }).then(async () => {
        const result = await this.$API.role.removeById(roleId);
        this.getRoles(this.roles.length === 1 ? this.page - 1 : this.page);
        this.$message.success(result.message || '删除成功!');
      }).catch(() => {
        this.$message.info('已取消删除');
      })
    },
    assignRoles() {
      var ids = this.$refs.tree.getCheckedKeys().join(',');
      this.$API.role.assignPermissionsToRole({ roleId: this.roleId, permissionIds: ids }).then(
        result => { this.$message.success('分配成功!'); this.dialogVisible = false; }
      );
    }
  }
}
</script>

<style scoped>
.edit-input {
  padding-right: 100px;
}

.cancel-btn {
  position: absolute;
  right: 15px;
  top: 10px;
}

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
