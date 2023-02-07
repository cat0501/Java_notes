const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name,

  roles: state => state.user.roles, // 用户的角色列表
  routes: state => state.user.routes, // 用户的所有路由列表
  buttons: state => state.user.buttons, // 用户的按钮权限列表
};

export default getters;
