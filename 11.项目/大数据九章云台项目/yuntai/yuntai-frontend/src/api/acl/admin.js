import request from '@/utils/request';

const api_name = '/admin/user';

export default {
  getPageList() {
    return request({
      url: `${api_name}/getAllUsers`,
      method: 'get'
    })
  },
  addUser(username, password) {
    return request({
      url: `${api_name}/addUser`,
      method: 'post',
      data: { username, password }
    });
  },
  getById(id) {
    return request({
      url: `${api_name}/getUser/${id}`,
      method: 'get'
    })
  },
  save(role) {
    return request({
      url: `${api_name}/save`,
      method: 'post',
      data: role
    })
  },
  removeById(id) {
    return request({
      url: `${api_name}/deleteUser/${id}`,
      method: 'delete'
    })
  },
  getRoles(adminId) {
    return request({
      url: `${api_name}/toAssign/${adminId}`,
      method: 'get'
    })
  },
  getRoleByAdminId(adminId) {
    return request({
      url: `admin/user/getRoleIdByUserId/${adminId}`,
      method: 'get'
    });
  },
  getAllRoles() {
    return request({
      url: `/admin/role/getAllRoles`,
      method: 'get'
    });
  },
  assignRoles(adminId, roleId) {
    return request({
      url: `${api_name}/assignRoleToUser`,
      method: 'post',
      data: {
        userId: adminId,
        roleId: roleId
      }
    })
  }
}
