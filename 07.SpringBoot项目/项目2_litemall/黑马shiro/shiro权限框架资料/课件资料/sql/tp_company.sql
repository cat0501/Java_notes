

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tp_company
-- ----------------------------
DROP TABLE IF EXISTS `tp_company`;
CREATE TABLE `tp_company` (
  `ID` varchar(36) CHARACTER SET utf8 NOT NULL,
  `ENABLE_FLAG` varchar(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '是否有效',
  `company_name` varchar(50) DEFAULT NULL COMMENT '企业名称',
  `address` varchar(50) DEFAULT NULL COMMENT '公司地址',
  `company_no` varchar(50) DEFAULT NULL COMMENT '企业编码',
  `boss` varchar(50) DEFAULT NULL COMMENT '法人',
  `registered_fund` varchar(50) DEFAULT NULL COMMENT '注册资金',
  `registered_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '注册时间',
  `insurance_number` int(10) DEFAULT NULL COMMENT '在保人数',
  `state` varchar(10) DEFAULT NULL COMMENT '状态 0：正常 1：拉黑',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tp_company
-- ----------------------------
INSERT INTO `tp_company` VALUES ('1', 'YES', '张三公司', '1', '101010', '张三', '10000', '2017-01-06 14:41:00', '1', '1');
