/*
Navicat MySQL Data Transfer

Source Server         : 9a50root
Source Server Version : 50727
Source Host           : 10.167.9.50:3306
Source Database       : storekeeper

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-11-23 15:47:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sk_dict
-- ----------------------------
DROP TABLE IF EXISTS `sk_dict`;
CREATE TABLE `sk_dict` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `KEY1` varchar(1000) DEFAULT NULL COMMENT '定义key1值',
  `VALUE1` varchar(1000) DEFAULT NULL COMMENT '定义value1值',
  `KEY2` varchar(1000) DEFAULT NULL COMMENT '定义key2值',
  `VALUE2` varchar(1000) DEFAULT NULL COMMENT '定义value2值',
  `CATEGORY` varchar(1000) NOT NULL COMMENT '字典分类识别码',
  `START_DT` date NOT NULL COMMENT '开始日期',
  `END_DT` date NOT NULL COMMENT '结束日期',
  `MEMO` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `FLAG` char(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除标志，0：未删除，1：已删除',
  `VERSION` varchar(50) DEFAULT NULL COMMENT '乐观锁',
  `CREATED_BY` varchar(50) DEFAULT NULL COMMENT '创建者',
  `CREATED_DT` date DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` varchar(50) DEFAULT NULL COMMENT '修改者',
  `UPDATED_DT` date DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典表';

-- ----------------------------
-- Table structure for sk_log
-- ----------------------------
DROP TABLE IF EXISTS `sk_log`;
CREATE TABLE `sk_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `DOC_TYPE` varchar(50) NOT NULL COMMENT '文件类型',
  `BIZ_GROUP` varchar(200) DEFAULT NULL COMMENT '业务系统分组编码',
  `BIZ_SUB` varchar(200) NOT NULL COMMENT '业务系统分组子编码',
  `ORIGIN_NAME` varchar(500) NOT NULL COMMENT '文件原名',
  `ENCODE_NAME` varchar(500) NOT NULL COMMENT '加密后文件名',
  `SYS_NAME` varchar(200) NOT NULL COMMENT '来源业务系统名称',
  `FILE_PATH` varchar(500) NOT NULL COMMENT '文件路径',
  `STATUS` varchar(50) NOT NULL COMMENT '上传文件状态',
  `DESCRIPTION` varchar(500) DEFAULT NULL COMMENT '文件描述',
  `MEMO` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `FLAG` char(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除标志，0：未删除，1：已删除',
  `VERSION` varchar(50) DEFAULT NULL COMMENT '乐观锁',
  `CREATED_BY` varchar(50) DEFAULT NULL COMMENT '创建者',
  `CREATED_DT` date DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` varchar(50) DEFAULT NULL COMMENT '修改者',
  `UPDATED_DT` date DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='StoreKeeper日志表';

-- ----------------------------
-- Table structure for sk_upload
-- ----------------------------
DROP TABLE IF EXISTS `sk_upload`;
CREATE TABLE `sk_upload` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(50) NOT NULL COMMENT '原因编码',
  `doc_type` varchar(50) NOT NULL COMMENT '文件类型',
  `origin_name` varchar(500) NOT NULL COMMENT '文件原名',
  `encode_name` varchar(500) NOT NULL COMMENT '加密后文件名',
  `sys_name` varchar(200) NOT NULL COMMENT '来源业务系统名称',
  `file_group` varchar(100) NOT NULL,
  `file_path` varchar(500) NOT NULL COMMENT '文件路径',
  `description` varchar(500) DEFAULT NULL COMMENT '文件描述',
  `memo` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `flag` char(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除标志，0：未删除，1：已删除',
  `version` varchar(50) DEFAULT NULL COMMENT '乐观锁',
  `created_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `created_dt` datetime NOT NULL COMMENT '创建时间',
  `updated_by` varchar(50) DEFAULT NULL COMMENT '修改者',
  `updated_dt` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2427892 DEFAULT CHARSET=utf8 COMMENT='StoreKeeper上传信息表';