

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sh_resource
-- ----------------------------
DROP TABLE IF EXISTS `sh_resource`;
CREATE TABLE `sh_resource` (
  `ID` varchar(36) NOT NULL COMMENT '主键',
  `ENABLE_FLAG` varchar(18) DEFAULT NULL COMMENT '是否有效',
  `PARENT_ID` varchar(36) DEFAULT NULL COMMENT '父Id',
  `RESOURCE_NAME` varchar(36) DEFAULT NULL COMMENT '资源名称',
  `REQUEST_PATH` varchar(200) DEFAULT NULL COMMENT '资源路径',
  `LABEL` varchar(200) DEFAULT NULL COMMENT '资源标识',
  `ICON` varchar(20) DEFAULT NULL COMMENT '图标',
  `IS_LEAF` varchar(18) DEFAULT NULL COMMENT '是否叶子节点',
  `RESOURCE_TYPE` varchar(36) DEFAULT NULL COMMENT '资源类型',
  `SORT_NO` int(11) DEFAULT NULL COMMENT '排序',
  `DESCRIPTION` varchar(200) DEFAULT NULL COMMENT '描述',
  `SYSTEM_CODE` varchar(36) DEFAULT NULL COMMENT '系统归属',
  `IS_SYSTEM_ROOT` varchar(18) DEFAULT NULL COMMENT '是否根节点',
  `SERVICE_NAME` varchar(100) DEFAULT NULL COMMENT '服务接口全限定路径',
  `METHOD_NAME` varchar(100) DEFAULT NULL COMMENT '方法名',
  `METHOD_PARAM` varchar(100) DEFAULT NULL COMMENT '传入参数全限定类型',
  `DUBBO_VERSION` varchar(50) DEFAULT NULL COMMENT 'DUBBO版本号',
  `LOADBALANCE` varchar(100) DEFAULT NULL COMMENT '接口轮训算法',
  `TIMEOUT` int(11) DEFAULT NULL COMMENT '接口超时时间',
  `RETRIES` int(11) DEFAULT NULL COMMENT '重试次数',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='资源表';

-- ----------------------------
-- Records of sh_resource
-- ----------------------------
INSERT INTO `sh_resource` VALUES ('325d06ee-eba5-4216-af0c-284762a67c86', 'YES', 'bd02d248-5dae-21e4-b26b-003068cb64da', '用户', 'user/listInitialize', 'user:listInitialize', 'icon-nav', 'NO', 'menu', '3', '用户管理', 'shiro-mgt', 'NO', null, null, null, null, null, null, null);
INSERT INTO `sh_resource` VALUES ('4e3f1766-26c5-4cd2-8054-d210137426f2', 'YES', 'bd02d248-5dae-21e4-b26b-003068cb64da', '角色', 'role/listInitialize', 'role:listInitialize', 'icon-nav', 'NO', 'menu', '2', '平台角色管理列表', 'shiro-mgt', 'NO', null, null, null, null, null, null, null);
INSERT INTO `sh_resource` VALUES ('500212870718464001', 'YES', 'bd02d248-5dae-21e4-b26b-003068cb64da', '过滤器链', '/filterChain/listInitialize', 'filterChain:listInitialize', '4', 'NO', 'menu', '4', '过滤器链', 'shiro-mgt', 'NO', null, null, null, null, null, null, null);
INSERT INTO `sh_resource` VALUES ('500607335832526849', 'YES', 'aaaaaaaa-bbbb-21e4-b26b-003068cb64da', 'gateway项目', 'gateway项目', 'gateway项目', '0', 'NO', 'menu', '2', 'gateway项目', 'gateway-handler', 'YES', null, null, null, null, null, null, null);
INSERT INTO `sh_resource` VALUES ('500916701206978561', 'YES', '500607335832526849', '公司分页列表', '/companyface/findCompanyList', 'companyface:findCompanyList', '1', 'YES', 'dubbo', '1', '查询公司分页列表', 'gateway-handler', 'NO', 'com.itheima.shiro.faced.CompanyFace', 'findCompanyList', 'com.itheima.shiro.request.CompanyPagReq', '1.0.0', 'random', '3', '6000');
INSERT INTO `sh_resource` VALUES ('aaaaaaaa-bbbb-21e4-b26b-003068cb64da', 'YES', '-1', '资源权限', '#', '#', 'icon-nav', 'NO', 'menu', '1', null, 'root-directory', 'YES', null, null, null, null, null, null, null);
INSERT INTO `sh_resource` VALUES ('bd02d248-5dae-11e4-b26b-003068cb64da', 'YES', 'edwed348-5dae-11e4-b2tb-003068cb63da', '系统设置', '#', 'resource:all', '1', 'NO', 'menu', '1', '', 'shiro-mgt', 'NO', null, null, null, null, null, null, null);
INSERT INTO `sh_resource` VALUES ('bd02d248-5dae-21e4-b26b-003068cb64da', 'YES', 'bd02d248-5dae-11e4-b26b-003068cb64da', '权限管理', '#', 'resource:manage', '1', 'NO', 'menu', '1', '', 'shiro-mgt', 'NO', null, null, null, null, null, null, null);
INSERT INTO `sh_resource` VALUES ('bd02z248-5dae-21e4-b26b-003068cb64da', 'YES', 'bd02d248-5dae-21e4-b26b-003068cb64da', '资源', 'resource/listInitialize', 'resource:listInitialize', 'icon-nav', 'NO', 'menu', '1', '', 'shiro-mgt', 'NO', null, null, null, null, null, null, null);
INSERT INTO `sh_resource` VALUES ('edwed348-5dae-11e4-b2tb-003068cb63da', 'YES', 'aaaaaaaa-bbbb-21e4-b26b-003068cb64da', 'shiro-mgt', 'platform项目', 'shiro-mgt项目', 'icon-nav', 'NO', 'menu', '1', '统一管理平台', 'shiro-mgt', 'YES', '', '', '', '', '', null, null);
