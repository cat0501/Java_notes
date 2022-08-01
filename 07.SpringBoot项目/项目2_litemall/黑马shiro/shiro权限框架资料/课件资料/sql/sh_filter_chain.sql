

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sh_filter_chain
-- ----------------------------
DROP TABLE IF EXISTS `sh_filter_chain`;
CREATE TABLE `sh_filter_chain` (
  `id` varchar(36) CHARACTER SET utf8 NOT NULL COMMENT '主键',
  `url_name` varchar(100) DEFAULT NULL COMMENT '描述',
  `url` varchar(100) DEFAULT NULL COMMENT '路径',
  `filter_name` varchar(100) DEFAULT NULL COMMENT '拦截器名称',
  `sort_no` int(9) DEFAULT NULL COMMENT '排序',
  `enable_flag` varchar(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '是否有效',
  `permissions` varchar(100) DEFAULT NULL,
  `roles` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sh_filter_chain
-- ----------------------------
INSERT INTO `sh_filter_chain` VALUES ('500216072977293313', '静态资源部拦截', '/static/**', 'anon', '1', 'YES', null, null);
INSERT INTO `sh_filter_chain` VALUES ('500253839211929601', '登录地址不拦截', '/login/**', 'anon', '2', 'YES', null, null);
INSERT INTO `sh_filter_chain` VALUES ('500254110587592706', '资源拦截拦截', '/resource/**', 'jwt-perms', '3', 'YES', 'resource:listInitialize', '');
INSERT INTO `sh_filter_chain` VALUES ('500254110587592789', '其他链接是需要登录的', '/**', 'kicked-out,jwt-authc', '5', 'YES', '', '');
INSERT INTO `sh_filter_chain` VALUES ('500937143468728321', '公司业务相关', '/companyface/**', 'jwt-roles', '4', 'YES', '', 'company');
