

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sh_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sh_user_role`;
CREATE TABLE `sh_user_role` (
  `ID` varchar(36) NOT NULL,
  `ENABLE_FLAG` varchar(18) DEFAULT NULL,
  `USER_ID` varchar(36) DEFAULT NULL,
  `ROLE_ID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户角色表';

-- ----------------------------
-- Records of sh_user_role
-- ----------------------------
INSERT INTO `sh_user_role` VALUES ('506626370713985026', 'YES', '473982847909142529', '497646927496650783');
INSERT INTO `sh_user_role` VALUES ('506626409347719171', 'YES', '483127545785524225', '497645189184135169');
INSERT INTO `sh_user_role` VALUES ('514636495403917317', 'YES', 'bd02d148-5dae-11e4-b26b-003067cb64da', 'bd02d248-5dae-11e4-b26b-003067cb64da');
INSERT INTO `sh_user_role` VALUES ('514636495416500230', 'YES', 'bd02d148-5dae-11e4-b26b-003067cb64da', '514636443071586305');
