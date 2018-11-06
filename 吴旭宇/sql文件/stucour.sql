/*
Navicat MySQL Data Transfer

Source Server         : KrisWu
Source Server Version : 50550
Source Host           : localhost:3306
Source Database       : class

Target Server Type    : MYSQL
Target Server Version : 50550
File Encoding         : 65001

Date: 2018-10-11 20:38:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for stucour
-- ----------------------------
DROP TABLE IF EXISTS `stucour`;
CREATE TABLE `stucour` (
  `id` varchar(255) NOT NULL,
  `sid` int(11) DEFAULT NULL,
  `cid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stucour
-- ----------------------------
INSERT INTO `stucour` VALUES ('9eb22449-4db8-4d7b-8e4e-9b8fc7bf6c5f', '135052', '6');
INSERT INTO `stucour` VALUES ('b1eae8fb-cf06-4dae-a0d6-f7db1cec6a40', '135060', '1');
