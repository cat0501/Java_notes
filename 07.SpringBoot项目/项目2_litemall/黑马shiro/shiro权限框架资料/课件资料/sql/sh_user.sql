

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sh_user
-- ----------------------------
DROP TABLE IF EXISTS `sh_user`;
CREATE TABLE `sh_user` (
  `ID` varchar(36) NOT NULL COMMENT '主键',
  `LOGIN_NAME` varchar(36) DEFAULT NULL COMMENT '登录名称',
  `REAL_NAME` varchar(36) DEFAULT NULL COMMENT '真实姓名',
  `NICK_NAME` varchar(36) DEFAULT NULL COMMENT '昵称',
  `PASS_WORD` varchar(150) DEFAULT NULL COMMENT '密码',
  `SALT` varchar(36) DEFAULT NULL COMMENT '加密因子',
  `SEX` int(11) DEFAULT NULL COMMENT '性别',
  `ZIPCODE` varchar(36) DEFAULT NULL COMMENT '邮箱',
  `ADDRESS` varchar(36) DEFAULT NULL COMMENT '地址',
  `TEL` varchar(36) DEFAULT NULL COMMENT '固定电话',
  `MOBIL` varchar(36) DEFAULT NULL COMMENT '电话',
  `EMAIL` varchar(36) DEFAULT NULL COMMENT '邮箱',
  `DUTIES` varchar(36) DEFAULT NULL COMMENT '职务',
  `SORT_NO` int(11) DEFAULT NULL COMMENT '排序',
  `ENABLE_FLAG` varchar(18) DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户表';

-- ----------------------------
-- Records of sh_user
-- ----------------------------
INSERT INTO `sh_user` VALUES ('473982847909142529', 'jay', '小耗子', '', 'cdce3b73444b6664da6178035ac930f2d6ce2fe1', '0bfb3baa6dca9bcf', '1', '', '', '', '15156403000', '5894@qq.com', '', '1', 'YES');
INSERT INTO `sh_user` VALUES ('483127545785524225', 'roleAdmin', '角色管理员', '', 'cdce3b73444b6664da6178035ac930f2d6ce2fe1', '0bfb3baa6dca9bcf', '1', '', '', '', '18121313333', '1515641@qq.com', '', '3', 'YES');
INSERT INTO `sh_user` VALUES ('bd02d148-5dae-11e4-b26b-003067cb64da', 'admin', '管理员', '', 'cdce3b73444b6664da6178035ac930f2d6ce2fe1', '0bfb3baa6dca9bcf', '1', '237000', '安徽', '', '15156400000', '50000@qq.com', '', '1', 'YES');
