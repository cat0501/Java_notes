import request from '@/utils/request';

/*
权限管理相关的API请求函数
*/
const api_name = '/admin/acl/permission'

export default {
  /*
  获取权限(菜单/功能)列表
  */
  getPermissionList() {
    return request({
      url: `${api_name}`,
      method: 'get'
    })
  },
  /*
  查看某个角色的权限列表
  */
  toAssign(roleId) {
    return request({
      url: `/admin/role/permissions/${roleId}`,
      method: 'get'
    })
  },
  /*
  给某个角色授权
  */
  doAssign(roleId, permissionId) {
    return request({
      url: `${api_name}/doAssign`,
      method: "post",
      params: { roleId, permissionId }
    })
  }
}
