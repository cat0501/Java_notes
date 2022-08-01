
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sh_role
-- ----------------------------
DROP TABLE IF EXISTS `sh_role`;
CREATE TABLE `sh_role` (
  `ID` varchar(36) NOT NULL COMMENT '主键',
  `ROLE_NAME` varchar(36) DEFAULT NULL COMMENT '角色名称',
  `LABEL` varchar(36) DEFAULT NULL COMMENT '角色标识',
  `DESCRIPTION` varchar(200) DEFAULT NULL COMMENT '角色描述',
  `SORT_NO` int(36) DEFAULT NULL COMMENT '排序',
  `ENABLE_FLAG` varchar(18) DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户角色表';

-- ----------------------------
-- Records of sh_role
-- ----------------------------
INSERT INTO `sh_role` VALUES ('497645189184135169', '角色管理', 'MangerRole', '角色管理', '2', 'YES');
INSERT INTO `sh_role` VALUES ('497646927496650783', '用户管理', 'MangerUser', '用户管理', '3', 'YES');
INSERT INTO `sh_role` VALUES ('514636443071586305', '公司管理', 'company', '公司管理', '4', 'YES');
INSERT INTO `sh_role` VALUES ('bd02d248-5dae-11e4-b26b-003067cb64da', '系统管理员', 'SuperAdmin', '系统管理员', '1', 'YES');
