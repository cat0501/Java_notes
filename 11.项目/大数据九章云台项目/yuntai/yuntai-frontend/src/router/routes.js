/* Layout */
import Layout from '@/layout';

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/*
常量路由
需要被静态注册, 不需要进行用户权限的检查
*/
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  }
]

/*
所有需要权限的异步路由
某个登陆用户可能需要从中过滤出其中一部分动态注册
router.addRoutes(routes)
*/
export const asyncRoutes = [
  /* 权限管理 */
  {
    name: 'ADMIN',
    path: '/acl',
    component: Layout,
    redirect: '/acl/admin/list',
    meta: {
      title: '权限管理',
      icon: 'el-icon-lock'
    },
    children: [
      {
        name: 'ADMIN-USER',
        path: 'admin/list',
        component: () => import('@/views/acl/admin/list'),
        meta: {
          title: '用户管理'
        }
      },
      {
        name: 'ADMIN-ROLE',
        path: 'role/list',
        component: () => import('@/views/acl/role/list'),
        meta: {
          title: '角色管理'
        }
      }
    ]
  },
  {
    path: '/statistics',
    name: 'STATISTICS',
    component: Layout,
    redirect: 'noredirect',
    meta: { title: '统计管理', icon: 'table' },
    alwaysShow: true,
    children: [
      {
        path: 'real',
        name: 'STATISTICS-REALTIME',
        component: () => import('@/views/statistics/real/realTime'),
        meta: { title: '实时统计' }
      },
      {
        path: 'visit',
        name: 'STATISTICS-VISIT',
        component: () => import('@/views/statistics/visit/visit'),
        meta: { title: '访问流量统计' }
      }
    ]
  },

  {
    path: '/report',
    name: 'REPORT',
    component: Layout,
    redirect: 'noredirect',
    meta: { title: '报表管理', icon: 'table' },
    alwaysShow: true,
    children: [
      {
        path: 'report/customClickhouseQuery',
        name: 'REPORT-CLICKHOUSE',
        component: () => import('@/views/report/customClickhouseQuery'),
        meta: { title: 'ClickHouse报表管理' }
      },
      {
        path: 'report/customMySQLQuery',
        name: 'REPORT-MYSQL',
        component: () => import('@/views/report/customMySQLQuery'),
        meta: { title: 'MySQL报表管理' }
      }
    ]
  },
  {
    path: '/schedule',
    name: 'SCHEDULER',
    component: Layout,
    redirect: 'schedule',
    meta: { title: '任务调度', icon: 'table' },
    alwaysShow: true,
    children: [
      {
        path: 'schedule/scheduleList',
        name: 'SCHEDULER-DETAIL',
        component: () => import('@/views/schedule/scheduleList'),
        meta: { title: '调度详情' }
      }
    ]
  },
  {
    path: '/govern',
    name: "GOVERNMENT",
    component: Layout,
    redirect: 'govern',
    meta: { title: '数据治理', icon: 'table' },
    alwaysShow: true,
    children: [
      {
        path: 'govern/governList',
        name: 'GOVERNMENT-HIVE-META',
        component: () => import('@/views/govern/governList'),
        meta: { title: 'HIVE表元数据质量' }
      },
      {
        path: 'govern/mysqlDataMonitor',
        name: 'GOVERNMENT-MYSQL',
        component: () => import('@/views/govern/mysqlDataMonitor'),
        meta: { title: 'MySQL表数据质量监控' }
      },
      {
        path: 'govern/hiveTableLineage',
        name: 'GOVERNMENT-HIVE-LINEAGE',
        component: () => import('@/views/govern/hiveTableLineage'),
        meta: { title: 'Hive表级血缘关系' }
      }
    ]
  }
]

/*
必须在最后被动态注册
*/
export const anyRoute = { path: '*', redirect: '/404', hidden: true }
