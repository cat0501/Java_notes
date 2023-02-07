/*
角色管理相关的API请求函数
*/
import request from '@/utils/request'

const api_name = '/admin/role'

export default {

  /*
  获取角色分页列表(带搜索)
  */
  getPageList() {
    return request({
      url: `${api_name}/getAllRoles`,
      method: 'get'
    })
  },

  /*
  获取某个角色
  */
  getById(id) {
    return request({
      url: `${api_name}/get/${id}`,
      method: 'get'
    })
  },

  /*
  保存一个新角色
  */
  save(roleName) {
    return request({
      url: `admin/role/addRole`,
      method: 'post',
      params: roleName
    })
  },
  /*
  获取一个角色的所有权限列表
  */
  getAssign(roleId) {
    return request({
      url: `${api_name}/toAssign/${roleId}`,
      method: 'get'
    })
  },

  /*
  删除某个角色
  */
  removeById(roleId) {
    return request({
      url: `/admin/role/deleteRole/${roleId}`,
      method: 'delete'
    })
  },

  assignPermissionsToRole(body) {
    return request({
      url: `${api_name}/assignPermissionsToRole`,
      method: 'post',
      data: body
    })
  },

  /*
  批量删除多个角色
  */
  removeRoles(ids) {
    return request({
      url: `${api_name}/batchRemove`,
      method: 'delete',
      data: ids
    })
  }
}
