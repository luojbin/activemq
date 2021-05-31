/*
Navicat MySQL Data Transfer

Source Server         : 阿里云-ROOT
Source Server Version : 50722
Source Host           : 47.106.82.153:3306
Source Database       : student

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-07-11 12:44:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for CANCEL
-- ----------------------------
DROP TABLE IF EXISTS `CANCEL`;
CREATE TABLE `CANCEL` (
  `id` varchar(32) NOT NULL,
  `customer` varchar(50) DEFAULT NULL,
  `input` varchar(50) DEFAULT NULL,
  `output` varchar(50) DEFAULT NULL,
  `remark` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for DEMAND
-- ----------------------------
DROP TABLE IF EXISTS `DEMAND`;
CREATE TABLE `DEMAND` (
  `id` varchar(32) NOT NULL,
  `customer` varchar(50) DEFAULT NULL,
  `input` varchar(50) DEFAULT NULL,
  `output` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='需求表';

-- ----------------------------
-- Table structure for EXAMINE
-- ----------------------------
DROP TABLE IF EXISTS `EXAMINE`;
CREATE TABLE `EXAMINE` (
  `id` varchar(32) NOT NULL,
  `demand_id` varchar(32) DEFAULT NULL,
  `program_id` varchar(32) DEFAULT NULL,
  `test_date` varchar(50) DEFAULT NULL,
  `test_result` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for PROGRAM
-- ----------------------------
DROP TABLE IF EXISTS `PROGRAM`;
CREATE TABLE `PROGRAM` (
  `id` varchar(32) NOT NULL,
  `demand_id` varchar(32) DEFAULT NULL,
  `input` varchar(50) DEFAULT NULL,
  `output` varchar(50) DEFAULT NULL,
  `step1` varchar(50) DEFAULT NULL,
  `step2` varchar(50) DEFAULT NULL,
  `step3` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='程序表';

-- ----------------------------
-- Table structure for STUDENT
-- ----------------------------
DROP TABLE IF EXISTS `STUDENT`;
CREATE TABLE `STUDENT` (
  `id` varchar(32) NOT NULL,
  `name` varchar(32) DEFAULT NULL COMMENT 'hghghghg',
  `age` tinyint(4) DEFAULT NULL COMMENT 'gdfgdfgdgd',
  `sex` tinyint(4) DEFAULT NULL COMMENT 'fghdghdfgdfg',
  `ttets2022` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
