/*
Navicat MySQL Data Transfer

Source Server         : KrisWu
Source Server Version : 50550
Source Host           : localhost:3306
Source Database       : class

Target Server Type    : MYSQL
Target Server Version : 50550
File Encoding         : 65001

Date: 2018-10-11 20:38:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `teaname` varchar(255) DEFAULT NULL,
  `point` int(11) DEFAULT NULL,
  `dr` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('1', '篮球', '9', '我', '2', '0');
INSERT INTO `course` VALUES ('2', '足球', '10', '你', '2', '0');
INSERT INTO `course` VALUES ('3', '划澡', '10', '河里游', '1', '0');
INSERT INTO `course` VALUES ('4', '说唱', '10', '你和她', '2', '0');
INSERT INTO `course` VALUES ('5', '电影', '10', '他和你', '2', '1');
INSERT INTO `course` VALUES ('6', '动漫', '0', '她和他', '2', '0');
INSERT INTO `course` VALUES ('7', '计算机', '10', '阿周', '2', '0');
