import request from '@/utils/request'

/*
登陆
*/
export function login({ username, password }) {
  return request({
    url: '/admin/index/login',
    method: 'post',
    data: { username, password }
  })
}

/*
获取用户信息(根据token)
*/
export function getInfo() {
  return request({
    url: '/admin/index/userInfo',
    method: 'get'
  })
}

/*
登出
*/
export function logout() {
  return request({
    url: '/admin/index/logout',
    method: 'post'
  })
}
