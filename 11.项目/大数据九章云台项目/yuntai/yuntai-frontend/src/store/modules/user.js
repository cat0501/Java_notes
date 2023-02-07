import cloneDeep from 'lodash/cloneDeep'
import { user as userAPI } from '@/api'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'
import { constantRoutes, asyncRoutes, anyRoute } from '@/router/routes'
import router from '@/router'

/**
 * 递归过滤异步路由表，返回符合用户菜单权限的路由表
 * @param asyncRoutes
 * @param routeNames
 */
function filterAsyncRoutes(asyncRoutes, routeNames) {
  // 过滤得到当前用户有权限的路由数组
  const accessedRoutes = asyncRoutes.filter(route => {
    // 遍历的route是否在当前用户的路由权限列表中
    if (routeNames.includes(route.name)) {
      //如果这个路由下面还有下一级的话,就递归调用
      if (route.children && route.children.length) {
        const cRoutes = filterAsyncRoutes(route.children, routeNames)
        //如果过滤一圈后,没有子元素了,这个父级菜单就也不显示了
        if (cRoutes && cRoutes.length > 0) {
          route.children = cRoutes
          return true
        }
        return false
      }
      return true
    }

    return false
  })

  return accessedRoutes
}

const getDefaultState = () => {
  return {
    token: getToken(),
    name: '',
    avatar: '',

    roles: [],
    buttons: [],
    routes: [], // 本用户所有的路由,包括了固定的路由和下面的addRouters
    asyncRoutes: [] // 本用户的角色赋予的新增的动态路由
  }
}

const state = getDefaultState()

const mutations = {
  SET_USER: (state, userInfo) => {
    state.name = userInfo.username; // 用户名
  },

  SET_TOKEN(state, token) {
    state.token = token;
  },

  RESET_USER(state) {
    Object.assign(state, getDefaultState())
  },

  SET_ROUTES: (state, asyncRoutes) => {
    // 保存异步路由
    state.asyncRoutes = asyncRoutes
    // 合并常量路由,异步路由与备选路由, 并保存
    state.routes = constantRoutes.concat(asyncRoutes, anyRoute) //将固定路由和新增路由进行合并, 成为本用户最终的全部路由信息
    // 将当前用户的异步权限路由和备选路由添加到路由器
    router.addRoutes([...asyncRoutes, anyRoute])
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { username, password } = userInfo;
    return new Promise((resolve, reject) => {
      userAPI.login({ username, password })
        .then(result => {
          // 获取token
          const data = result.data;
          commit('SET_TOKEN', data);
          // 将token保存到浏览器的cookies中
          setToken(data);
          resolve();
        }).catch(error => {
          reject(error);
        })
    })
  },

  // get user info
  async getInfo({ commit, state }) {
    const result = await userAPI.getInfo();
    const data = result.data;
    commit('SET_USER', data);

    // 有问题: 切换admin登陆看到的还是上一个用户的菜单列表
    // 原因: 包含所有路由的数组中某个一层路由的children可能被过滤掉了部分
    // commit('SET_ROUTES', filterAsyncRoutes(asyncRoutes, data.routes))

    commit('SET_ROUTES', filterAsyncRoutes(cloneDeep(asyncRoutes), data.routes));
  },

  /*
  重置用户信息
  */
  async resetUser({ commit, state }) {
    // 如果当前是登陆的, 请求退出登陆
    if (state.name) {
      await userAPI.logout()
    }
    // 删除local中保存的token
    removeToken()
    // 重置路由
    resetRouter()
    // 提交重置用户信息的mutation
    commit('RESET_USER')
  },
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
