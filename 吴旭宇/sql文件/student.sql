/*
Navicat MySQL Data Transfer

Source Server         : KrisWu
Source Server Version : 50550
Source Host           : localhost:3306
Source Database       : class

Target Server Type    : MYSQL
Target Server Version : 50550
File Encoding         : 65001

Date: 2018-10-11 20:39:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `dr` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('135050', 'JR', '男', 'E10ADC3949BA59ABBE56E057F20F883E', '0');
INSERT INTO `student` VALUES ('135051', '球哥', '男', 'E10ADC3949BA59ABBE56E057F20F883E', '0');
INSERT INTO `student` VALUES ('135052', '裤子吗', '男', 'E10ADC3949BA59ABBE56E057F20F883E', '1');
INSERT INTO `student` VALUES ('135053', '詹姆斯', '男', 'E10ADC3949BA59ABBE56E057F20F883E', '0');
INSERT INTO `student` VALUES ('135054', '安东尼', '男', 'E10ADC3949BA59ABBE56E057F20F883E', '0');
INSERT INTO `student` VALUES ('135055', '保罗', '男', 'E10ADC3949BA59ABBE56E057F20F883E', '0');
INSERT INTO `student` VALUES ('135056', '德罗赞', '男', 'E10ADC3949BA59ABBE56E057F20F883E', '0');
INSERT INTO `student` VALUES ('135057', '獭兔', '男', 'E10ADC3949BA59ABBE56E057F20F883E', '0');
INSERT INTO `student` VALUES ('135058', '西蒙斯', '男', 'E10ADC3949BA59ABBE56E057F20F883E', '0');
INSERT INTO `student` VALUES ('135059', '英格拉姆', '男', 'E10ADC3949BA59ABBE56E057F20F883E', '0');
INSERT INTO `student` VALUES ('135060', '韦德', '男', 'E10ADC3949BA59ABBE56E057F20F883E', '0');
