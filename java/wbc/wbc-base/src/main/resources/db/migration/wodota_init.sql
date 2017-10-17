/*
Navicat MySQL Data Transfer

Source Server         : mycat0
Source Server Version : 50508
Source Host           : 192.168.11.210:8066
Source Database       : wodota

Target Server Type    : MYSQL
Target Server Version : 50508
File Encoding         : 65001

Date: 2016-11-16 12:13:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` varchar(32) NOT NULL COMMENT '唯一标识',
  `value` varchar(100) DEFAULT NULL,
  `label` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `sort` decimal(10,0) DEFAULT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  `label_class` varchar(100) DEFAULT 'label label-sm label-success arrowed arrowed-righ' COMMENT '文字列表样式',
  `create_by` varchar(32) DEFAULT NULL,
  `create_date` bigint(20) DEFAULT NULL,
  `update_by` varchar(32) DEFAULT NULL,
  `update_date` bigint(20) DEFAULT NULL,
  `remarks` varchar(100) DEFAULT NULL,
  `del_flag` varchar(4) DEFAULT NULL COMMENT '0:正常 1：删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标准代码字典';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('004609812f174904a7c03e4d4867d362', '1', '评论', 'message_business_type', '用户消息业务类型', '1', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1474426628508', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('05d03df4bfca4173aa1b6c76a4f99ea7', '2', '待审核', 'VIDEO_EXAMINE_STATE', '视频审核状态', '5', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1473044147408', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('08d8a5b8aad74825a1c6445365dc0376', '2', '视频', 'business_type', '内容分类', '2', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1471416052101', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('0da279ca051b4169a45b2a2a69688c13', '3', '申请失败', 'cash_apply_state', '提现申请状态', '3', null, 'label label-sm label-warning arrowed arrowed-righ', '4', '1473240531562', '4', '1473244396037', null, '0');
INSERT INTO `sys_dict` VALUES ('119a4c2c5856412ebff1be60bdbda86a', '1', '编辑推荐', 'video_attributes', '视频属性', '1', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1473042905276', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('18fc37505f214df398bd044bbd637a60', '2', '银币', 'currency_type', '货币类型', '2', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1471518351472', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('2c2d4eb787d24e10a8bec86f3f3a971f', '4', '敏感政治倾向', 'sw_content_type', '敏感词内容所属类型', '4', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1471330744801', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('30bbb9e9ba304a828ce9b4623d54103d', '6', '个人主页', 'seo_config_type', 'SEO类型', '6', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1477377100131', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('31f3e5149a6c47959c3b94791814cb24', '1', '不文明语', 'sw_content_type', '敏感词内容所属类型', '1', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1471330572103', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('4970317bd84a44aea425def5fdba2d68', '5', '视频详情', 'seo_config_type', 'SEO类型', '5', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1477377084708', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('4d23de103f784ee2887f90a9eef62c03', '2', '已处理', 'cash_apply_state', '提现申请状态', '2', null, 'label label-sm label-yellow arrowed arrowed-righ', '4', '1473240502602', '4', '1473244414574', null, '0');
INSERT INTO `sys_dict` VALUES ('536bfdc5c18a462cb201bd88ae673549', '1', '力量', 'type_hero', '英雄类型', '1', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1471255575999', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('55e1193f8d904896a972aae615475549', '1', '是', 'yes_no', '是/否', '1', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1471341998984', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('57b6755fa3fa4505934f027421dfb2dd', '2', '今日最佳', 'video_attributes', '视频属性', '2', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1473042943780', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('599ee49909624564ba295447faaf3585', '0', '普通', 'video_attributes', '视频属性', '3', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1473042968826', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('5b196fb6f47b49a78b86fa15f957c984', '0', '正常', 'is_delete', '是否删除', '1', null, 'label label-sm label-success arrowed arrowed-righ', '4', null, '4', '1471256865060', null, '0');
INSERT INTO `sys_dict` VALUES ('61bb27ea832c467a92e6469d57357072', '1', '全局', 'sw_use_type', '敏感词作用域类型', '1', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1471330529219', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('644092ca439249e793b9a19db2ecd847', '1', '通用', 'seo_config_type', 'SEO类型', '1', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1477297905771', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('6d4674a5a9ca4731a2e80c7792b22771', '2', '暴力倾向', 'sw_content_type', '敏感词内容所属类型', '2', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1471330593927', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('8abf5f33c313495fa06b397b6a39d764', '1', '用户', 'business_type', '内容分类', '1', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1471416032528', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('8c1b6d0aafa0428993c3b36ee6d0fa79', '1', '已提交', 'cash_apply_state', '提现申请状态', '1', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1473240488635', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('9716694c24f44d83abbec6c59856b095', '2', '敏捷', 'type_hero', '英雄类型', '2', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1471255593337', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('9cd1c8a4f5474b5bb9dec08362036670', '1', '审核通过', 'VIDEO_EXAMINE_STATE', '视频审核状态', '4', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1473044122351', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('9dd3b9f6fe1a402ba9c2cf0e18171c51', '2', '首页', 'seo_config_type', 'SEO类型', '2', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1477297927567', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('9ef0758c57e541dfb5826b417c133b95', '0', '否', 'yes_no', '是/否', '2', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1471342020397', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('aac0655f7ffb44c8aca49ddcc2ef29d4', '4', '发现', 'seo_config_type', 'SEO类型', '4', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1477377069471', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('af04e47e4b9d44e58dce785823a38339', '5', '邪教欺骗', 'sw_content_type', '敏感词内容所属类型', '5', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1471330677092', '4', '1471330794438', null, '0');
INSERT INTO `sys_dict` VALUES ('b801e1c31c6b4958b89ea9242b918863', '1', '评论', 'laudlog_business_type', '点赞业务类型', '1', null, 'label label-sm label-yellow arrowed arrowed-righ', '4', '1473132243248', '4', '1473132443506', null, '0');
INSERT INTO `sys_dict` VALUES ('bf66ed45f4ba4ae8acbbe618fe86b9ba', '1', 'G币', 'currency_type', '货币类型', '1', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1471518339963', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('c315d92b2d264d3ba1352fb7b98f5680', '4', '关注', 'message_business_type', '用户消息业务类型', '4', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1474426675359', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('c38e706b810545de9d59e158e89d26a9', '3', '主页', 'seo_config_type', 'SEO类型', '3', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1477377053078', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('c8806c6115954d328fe8fdb7fef13b7d', '1', '已删除', 'is_delete', '是否删除', '2', null, 'label label-sm label-warning arrowed arrowed-righ', '4', null, '4', '1471681541054', null, '0');
INSERT INTO `sys_dict` VALUES ('c99a5d65a1ef4b0893e8436e78d1cd71', '3', '转码', 'VIDEO_EXAMINE_STATE', '视频审核状态', '5', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1473044169207', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('c9dd0913bb93449ca4baa96466a7a4b3', '2', '管理员手动', 'gold_rule_log_type', 'G币发放分类', '2', null, 'label label-sm label-yellow arrowed arrowed-righ', '4', '1473327515735', '4', '1473327530009', null, '0');
INSERT INTO `sys_dict` VALUES ('dc5d1f3ca9884a388fdbd5f1ad0ce298', '0', '审核不通过', 'VIDEO_EXAMINE_STATE', '视频审核状态', '5', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1473044186601', '4', '1473069265997', null, '0');
INSERT INTO `sys_dict` VALUES ('e1259b4861e941fb83cd0e07c5d0815a', '3', '智力', 'type_hero', '英雄类型', '3', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1471255604956', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('e3934d8be12b49408ff97289f19ba4e0', '2', '视频', 'laudlog_business_type', '点赞业务类型', '2', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1473132253866', '4', '1473132452539', null, '0');
INSERT INTO `sys_dict` VALUES ('e777e25ab86146ba8cdb1f6220161161', '1', '系统', 'gold_rule_log_type', 'G币发放分类', '1', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1473327500199', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('f8259c547dbc45a3ab4be9488d4bb6a3', '3', '不健康色彩', 'sw_content_type', '敏感词内容所属类型', '3', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1471330653321', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('fbbc2b823c6e47358d1154fb084fff97', '2', '点赞', 'message_business_type', '用户消息业务类型', '2', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1474426645202', null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('fdcd46d714b941529e8c78ae6a90960e', '3', '打赏', 'message_business_type', '用户消息业务类型', '3', null, 'label label-sm label-success arrowed arrowed-righ', '4', '1474426658825', null, null, null, '0');

-- ----------------------------
-- Table structure for tb_login_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_login_info`;
CREATE TABLE `tb_login_info` (
  `l_id` varchar(64) NOT NULL,
  `u_id` varchar(32) DEFAULT NULL,
  `u_account_name` varchar(255) DEFAULT NULL,
  `l_ip` varchar(255) DEFAULT NULL,
  `l_login_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`l_id`),
  KEY `IN_LOGININFO_UID` (`u_id`),
  KEY `IN_LOGININFO_LTIME` (`l_login_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_login_info
-- ----------------------------

-- ----------------------------
-- Table structure for tb_log_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_log_info`;
CREATE TABLE `tb_log_info` (
  `l_id` varchar(64) NOT NULL,
  `u_id` varchar(32) NOT NULL,
  `l_account_name` varchar(100) DEFAULT NULL,
  `l_operation` varchar(255) DEFAULT NULL COMMENT '用户所做的操作',
  `l_content` varchar(1000) DEFAULT NULL COMMENT '日志内容',
  `l_create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建日期',
  PRIMARY KEY (`l_id`),
  KEY `IN_LOGINFO_UID` (`u_id`),
  KEY `IN_LOGINFO_CTIME` (`l_create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_log_info
-- ----------------------------

-- ----------------------------
-- Table structure for tb_resource
-- ----------------------------
DROP TABLE IF EXISTS `tb_resource`;
CREATE TABLE `tb_resource` (
  `s_id` varchar(32) NOT NULL COMMENT '资源id',
  `s_parent_id` varchar(32) DEFAULT NULL COMMENT '资源父id',
  `s_name` varchar(100) NOT NULL COMMENT '资源名称',
  `s_source_key` varchar(100) NOT NULL COMMENT '资源唯一标识',
  `s_type` int(11) NOT NULL COMMENT '资源类型,0:目录;1:菜单;2:按钮',
  `s_source_url` varchar(500) DEFAULT NULL COMMENT '资源url',
  `s_level` int(11) DEFAULT NULL COMMENT '层级',
  `s_icon` varchar(100) DEFAULT '' COMMENT '图标',
  `s_is_hide` int(11) DEFAULT '0' COMMENT '是否隐藏',
  `s_description` varchar(100) DEFAULT NULL COMMENT '描述',
  `s_create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `s_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`s_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源表';

-- ----------------------------
-- Records of tb_resource
-- ----------------------------
INSERT INTO `tb_resource` VALUES ('1', null, '控制台', 'desktop', '0', '/welcome.jsp', '1', 'fa fa-tachometer', '0', '控制台', '2016-01-12 17:08:55', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('2', null, '系统基础管理', 'system', '0', '', '1', 'fa fa-list', '0', '系统基础管理', '2016-01-05 12:11:12', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('3', '2', '用户管理', 'system:user', '0', '/user/listUI.html', '2', '', '0', '用户管理', '2016-01-08 12:37:10', '2016-06-30 20:53:36');
INSERT INTO `tb_resource` VALUES ('4', '2', '角色管理', 'system:role', '0', '/role/listUI.html', '2', '', '0', '角色管理', '2016-01-11 22:51:07', '2016-06-30 20:53:38');
INSERT INTO `tb_resource` VALUES ('5', '2', '资源管理', 'system:resource', '0', '/resource/listUI.html', '2', '', '0', '资源管理', '2016-01-11 22:51:55', '2016-06-30 20:53:40');
INSERT INTO `tb_resource` VALUES ('6', null, '系统监控管理', 'monitor', '0', '', '1', 'fa fa-pencil-square-o', '0', '系统监控管理', '2016-01-05 12:11:12', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('7', '6', 'Sirona监控', 'monitor:sirona', '0', '/sirona', '2', '', '0', 'Sirona监控', '2016-01-05 12:11:12', '2016-06-30 20:53:41');
INSERT INTO `tb_resource` VALUES ('8', '6', 'Druid监控', 'monitor:druid', '0', '/druid', '2', '', '0', 'Druid监控', '2016-01-11 22:45:27', '2016-06-30 20:53:44');
INSERT INTO `tb_resource` VALUES ('9', null, '日志信息管理', 'logininfo', '0', '', '1', 'fa fa-tag', '0', '日志信息管理', '2016-01-11 22:46:39', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('10', '9', '用户登录信息', 'logininfo:userLogin', '0', '/loginInfo/listUI.html', '2', '', '0', '用户登录信息', '2016-01-11 22:47:41', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('11', '3', '添加', 'user:add', '1', '/user/add.html', '3', '', '0', '添加用户', '2016-01-22 00:18:40', '2016-06-30 20:54:42');
INSERT INTO `tb_resource` VALUES ('12', '3', '编辑', 'user:edit', '1', '/user/edit.html', '3', '', '0', '编辑用户', '2016-01-22 00:18:40', '2016-06-30 20:54:43');
INSERT INTO `tb_resource` VALUES ('13', '3', '删除', 'user:deleteBatch', '1', '/user/deleteBatch.html', null, null, '0', '删除用户', '2016-02-05 15:26:32', '2016-06-30 20:54:44');
INSERT INTO `tb_resource` VALUES ('14', '3', '重置密码', 'user:resetPassword', '1', '/user/resetPassword.html', null, null, '0', '重置密码', '2016-02-27 23:55:13', '2016-06-30 20:54:45');
INSERT INTO `tb_resource` VALUES ('15', '4', '添加', 'role:add', '1', '/role/add.html', null, null, '0', '添加', '2016-02-27 23:56:52', '2016-06-30 20:54:46');
INSERT INTO `tb_resource` VALUES ('16', '4', '编辑', 'role:edit', '1', '/role/edit.html', null, null, '0', '编辑', '2016-02-27 23:57:35', '2016-06-30 20:54:47');
INSERT INTO `tb_resource` VALUES ('17', '4', '删除', 'role:deleteBatch', '1', '/role/deleteBatch.html ', null, null, '0', '删除', '2016-02-27 23:58:02', '2016-06-30 20:54:48');
INSERT INTO `tb_resource` VALUES ('18', '4', '分配权限', 'role:permission', '1', '/role/permission.html', null, null, '0', '分配权限', '2016-02-27 23:59:20', '2016-06-30 20:54:48');
INSERT INTO `tb_resource` VALUES ('19', '5', '添加', 'resource:add', '1', '/resource/add.html', null, null, '0', '添加', '2016-02-28 00:01:15', '2016-06-30 20:54:49');
INSERT INTO `tb_resource` VALUES ('20', '5', '编辑', 'resource:edit', '1', '/resource/edit.html', null, null, '0', '编辑', '2016-02-28 00:02:01', '2016-06-30 20:54:50');
INSERT INTO `tb_resource` VALUES ('21', '5', '删除', 'resource:deleteBatch', '1', '/resource/deleteBatch.html', null, null, '0', '删除', '2016-02-28 00:03:03', '2016-06-30 20:54:51');
INSERT INTO `tb_resource` VALUES ('22', '9', '用户操作信息', 'loginfo:userOperation', '0', '/logInfo/listUI.html', null, 'fa  fa-sticky-note-o', '0', '用户操作信息', '2016-03-08 22:00:36', '2016-07-01 16:14:54');
INSERT INTO `tb_resource` VALUES ('24', null, '一级菜单', 'menu', '0', '', null, 'fa  fa-asterisk', '0', '一级菜单', '2016-06-30 19:51:57', '2016-06-30 19:51:57');
INSERT INTO `tb_resource` VALUES ('25', '24', '二级菜单', 'menu:menu1', '0', '', null, '', '0', '二级菜单', '2016-06-30 19:54:01', '2016-06-30 21:03:49');
INSERT INTO `tb_resource` VALUES ('26', '25', '三级菜单', 'menu:menu1:menu2', '0', '', null, 'fa  fa-cloud-download', '0', '三级菜单', '2016-06-30 19:54:29', '2016-07-01 10:26:48');
INSERT INTO `tb_resource` VALUES ('27', '26', '四级菜单', 'menu:menu1:menu2:menu3', '0', '/logInfo/listUI.html', null, 'fa  fa-line-chart', '0', '四级菜单', '2016-06-30 19:55:53', '2016-07-01 11:17:38');
INSERT INTO `tb_resource` VALUES ('28', '25', '三级菜单1', 'menu:menu1:menu21', '0', '/logInfo/listUI.html', null, 'fa  fa-balance-scale', '0', '三级菜单1', '2016-06-30 20:16:44', '2016-07-01 16:37:53');
INSERT INTO `tb_resource` VALUES ('29', '24', '二级菜单1', 'menu:menu11', '0', '/logInfo/listUI.html', null, 'fa  fa-comment', '0', '二级菜单1', '2016-07-01 15:41:44', '2016-07-01 15:42:05');
INSERT INTO `tb_resource` VALUES ('30', null, '计划任务管理', 'schedule', '0', '', null, 'fa  fa-list-ol', '0', '计划任务管理', '2016-07-17 01:09:27', '2016-07-20 16:01:08');
INSERT INTO `tb_resource` VALUES ('32', '38', '添加job', 'schedule:addJob', '1', '/scheduleJob/addJobUI.html', null, null, '0', '添加任务', '2016-07-17 01:15:38', '2016-07-26 17:33:06');
INSERT INTO `tb_resource` VALUES ('33', '40', '编辑trigger', 'schedule:editTrigger', '1', '/scheduleJob/editTriggerUI.html', null, null, '0', '编辑任务', '2016-07-17 01:17:19', '2016-07-26 18:11:19');
INSERT INTO `tb_resource` VALUES ('34', '38', '删除job', 'schedule:deleteJob', '1', '/scheduleJob/deleteJob.html', null, null, '0', '删除任务', '2016-07-17 01:19:24', '2016-07-26 17:32:05');
INSERT INTO `tb_resource` VALUES ('35', '38', '执行job', 'schedule:executeJob', '1', '/scheduleJob/executeJob.html', null, null, '0', '立即执行一次', '2016-07-17 01:22:01', '2016-07-26 17:33:26');
INSERT INTO `tb_resource` VALUES ('36', '39', '暂停job', 'schedule:pauseJob', '1', '/scheduleJob/pauseJob.html', null, null, '0', '暂停任务', '2016-07-17 01:24:13', '2016-07-26 17:32:20');
INSERT INTO `tb_resource` VALUES ('37', '39', '恢复job', 'schedule:resumeJob', '1', '/scheduleJob/resumeJob.html', null, null, '0', '恢复任务', '2016-07-17 01:25:23', '2016-07-26 17:32:26');
INSERT INTO `tb_resource` VALUES ('38', '30', '计划中任务', 'schedule:planScheduleList', '0', '/scheduleJob/planningJobListUI.html', null, 'fa  fa-hourglass', '0', '计划中任务', '2016-07-19 18:51:54', '2016-07-26 18:02:14');
INSERT INTO `tb_resource` VALUES ('39', '30', '运行中任务', 'schedule:runScheduleList', '0', '/scheduleJob/runningJobListUI.html', null, 'fa  fa-hourglass-2', '0', '运行中任务', '2016-07-19 18:53:45', '2016-07-26 18:02:20');
INSERT INTO `tb_resource` VALUES ('40', '38', '触发器列表', 'schedule:triggerList', '1', '/scheduleJob/jobTriggerListUI.html', null, '', '0', '查看触发器列表', '2016-07-21 21:19:57', '2016-07-26 18:03:03');
INSERT INTO `tb_resource` VALUES ('41', '40', '添加trigger', 'schedule:addTrigger', '1', '/scheduleJob/addTriggerUI.html', null, null, '0', '给当前job添加trigger', '2016-07-26 19:31:07', '2016-07-26 19:34:11');
INSERT INTO `tb_resource` VALUES ('42', '38', '暂停job', 'schedule:pauseJob', '1', '/scheduleJob/pauseJob.html', null, '', '0', '暂停job', '2016-07-26 19:32:15', '2016-07-26 19:32:15');
INSERT INTO `tb_resource` VALUES ('43', '38', '恢复job', 'schedule:resumeJob', '1', '/scheduleJob/resumeJob.html', null, '', '0', '恢复job', '2016-07-26 19:32:52', '2016-07-26 19:32:52');
INSERT INTO `tb_resource` VALUES ('44', '40', '编辑trigger', 'schedule:editTrigger', '1', '/scheduleJob/editTriggerUI.html', null, '', '0', '编辑trigger', '2016-07-26 19:34:44', '2016-07-26 19:34:44');
INSERT INTO `tb_resource` VALUES ('45', '40', '删除trigger', 'schedule:deleteTrigger', '1', '/scheduleJob/deleteTrigger.html', null, null, '0', '删除trigger', '2016-07-26 19:35:23', '2016-07-26 19:36:50');
INSERT INTO `tb_resource` VALUES ('46', '40', '暂停trigger', 'schedule:pauseTrigger', '1', '/scheduleJob/pasueTrigger.html', null, '', '0', '暂停trigger', '2016-07-26 19:36:37', '2016-07-26 19:36:37');
INSERT INTO `tb_resource` VALUES ('47', '40', '恢复trigger', 'schedule:resumeTrigger', '1', '/scheduleJob/resumeTrigger.html', null, '', '0', '恢复trigger', '2016-07-26 19:37:22', '2016-07-26 19:37:22');

-- ----------------------------
-- Table structure for tb_resources_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_resources_role`;
CREATE TABLE `tb_resources_role` (
  `id` varchar(32) NOT NULL,
  `s_id` varchar(32) NOT NULL COMMENT '资源id',
  `r_id` varchar(32) NOT NULL COMMENT '角色id',
  `t_create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限映射表';

-- ----------------------------
-- Records of tb_resources_role
-- ----------------------------
INSERT INTO `tb_resources_role` VALUES ('1', '3', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('2', '2', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('3', '4', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('4', '5', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('5', '6', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('6', '9', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('7', '26', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('8', '25', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('9', '24', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('10', '38', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('11', '30', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('12', '40', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('13', '39', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('14', '1', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('15', '11', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('16', '12', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('17', '13', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('18', '14', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('19', '15', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('20', '16', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('21', '17', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('22', '18', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('23', '19', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('24', '20', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('25', '21', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('26', '7', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('27', '8', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('28', '10', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('29', '22', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('30', '27', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('31', '28', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('32', '29', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('33', '35', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('34', '41', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('35', '44', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('36', '45', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('37', '46', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('38', '47', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('39', '42', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('40', '43', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('41', '36', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('42', '37', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('43', '32', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('44', '34', '1', '2016-07-26 19:38:29');
INSERT INTO `tb_resources_role` VALUES ('45', '33', '1', '2016-07-26 19:38:29');

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `r_id` varchar(32) NOT NULL COMMENT '角色id',
  `role_type` int(4) DEFAULT NULL COMMENT '角色类型，0：网站官方组  1：普通用户组',
  `r_name` varchar(50) NOT NULL COMMENT '角色名称',
  `r_key` varchar(50) NOT NULL COMMENT '角色key',
  `r_status` int(11) DEFAULT '0' COMMENT '角色状态,0：正常；1：删除',
  `r_description` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `r_create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `r_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`r_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES ('1', '0', '超级管理员', 'administrator', '0', '超级管理员', '2016-01-05 12:07:42', '2016-10-20 05:59:24');

-- ----------------------------
-- Table structure for tb_role_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_user`;
CREATE TABLE `tb_role_user` (
  `id` varchar(32) NOT NULL,
  `r_id` varchar(32) NOT NULL COMMENT '角色id',
  `u_id` varchar(32) NOT NULL COMMENT '用户id',
  `t_create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `IN_ROLEUSER_UID` (`u_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色映射表';

-- ----------------------------
-- Records of tb_role_user
-- ----------------------------
INSERT INTO `tb_role_user` VALUES ('1', '1', '1', null);

-- ----------------------------
-- Table structure for tb_schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `tb_schedule_job`;
CREATE TABLE `tb_schedule_job` (
  `j_id` int(11) NOT NULL AUTO_INCREMENT,
  `j_job_name` varchar(255) DEFAULT NULL,
  `j_job_group` varchar(255) DEFAULT NULL,
  `j_job_status` varchar(20) DEFAULT NULL COMMENT 'NONE:未知\r\nNORMAL:正常无任务\r\nPAUSED:暂停\r\nCOMPLETE:完成\r\nERROR:异常\r\nBLOCKED:等待运行,阻塞\r\nDELETE:删除\r\n',
  `j_cron_expression` varchar(255) DEFAULT NULL,
  `j_job_class_name` varchar(255) DEFAULT NULL,
  `j_job_desc` varchar(255) DEFAULT NULL,
  `j_job_start` datetime DEFAULT NULL,
  `j_job_end` datetime DEFAULT NULL,
  `j_job_create_time` datetime DEFAULT NULL,
  `j_job_update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`j_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_schedule_job
-- ----------------------------

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `u_id` varchar(32) NOT NULL COMMENT '用户id',
  `u_password` varchar(100) NOT NULL COMMENT '用户密码',
  `u_locked` int(11) NOT NULL DEFAULT '0' COMMENT '是否锁定',
  `u_credentials_salt` varchar(500) NOT NULL COMMENT '加密盐',
  `NICK_NAME` varchar(32) NOT NULL COMMENT '用户昵称',
  `Mobile` varchar(20) NOT NULL COMMENT '手机号码',
  `BACKGROUND_IMG` varchar(256) DEFAULT NULL COMMENT '背景图',
  `photo` varchar(256) DEFAULT NULL COMMENT '头像',
  `email` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `sign` varchar(128) DEFAULT NULL COMMENT '签名',
  `steam_key` varchar(64) DEFAULT NULL,
  `qq_key` varchar(64) DEFAULT NULL,
  `wechat_key` varchar(64) DEFAULT NULL,
  `weibo_key` varchar(64) DEFAULT NULL,
  `steam_nick` varchar(50) DEFAULT NULL COMMENT 'steam 用户昵称',
  `qq_nick` varchar(50) DEFAULT NULL COMMENT 'qq 用户昵称',
  `wechat_nick` varchar(50) DEFAULT NULL COMMENT '微信 用户昵称',
  `weibo_nick` varchar(50) DEFAULT NULL COMMENT '微博 用户昵称',
  `user_url` varchar(32) DEFAULT NULL COMMENT '用户自定义个人主页地址后缀',
  `u_creator_name` varchar(100) DEFAULT NULL COMMENT '创建者',
  `u_create_time` bigint(20) unsigned NOT NULL COMMENT '创建时间',
  `u_updator_name` varchar(100) DEFAULT NULL COMMENT '更新人',
  `u_update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `is_auth` varchar(2) NOT NULL DEFAULT '0' COMMENT '是否认证 0未认证  1：已认证',
  `auth_desc` varchar(512) DEFAULT NULL COMMENT '认证简介',
  `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除，0未删除，1已删除，默认值0',
  `alipay_account` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`u_id`),
  UNIQUE KEY `IN_USER_MOBILE` (`Mobile`) USING BTREE,
  UNIQUE KEY `IN_USER_NICK_NAME` (`NICK_NAME`) USING BTREE,
  UNIQUE KEY `IN_USER_EMAIL` (`email`),
  KEY `IN_USER_CTIME` (`u_create_time`),
  KEY `IN_USER_UTIME` (`u_update_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户账户表';

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', '0vu2YiJ2ZGOQ8yUzpdzFFA==', '0', '151a714eccce230688285b2397a91e37', 'superadmin', '15361427685', null, '/resources/wodota/images/denglu_n.png', 'admin@webside.com', '什么游戏好玩呢', '', '', '', '', '', '', '', '', '', 'admin@webside.com', '1471231126132', 'superadmin', '1479206080637', '1', '', '0', null);

-- ----------------------------
-- Table structure for tb_user_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_info`;
CREATE TABLE `tb_user_info` (
  `u_id` varchar(32) NOT NULL COMMENT '用户id',
  `u_sex` int(11) DEFAULT NULL COMMENT '性别',
  `u_birthday` date DEFAULT NULL COMMENT '出生日期',
  `u_telephone` varchar(20) DEFAULT NULL COMMENT '电话',
  `u_email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `u_address` varchar(200) DEFAULT NULL COMMENT '住址',
  `u_create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`u_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户扩展信息表';

-- ----------------------------
-- Table structure for t_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `ID` varchar(32) NOT NULL COMMENT '唯一标识',
  `USER_ID` varchar(32) NOT NULL COMMENT '用户ID',
  `CONTENT` varchar(300) NOT NULL COMMENT '评论内容',
  `VIDEO_ID` varchar(32) NOT NULL COMMENT '文章视频ID',
  `CREATE_TIME` bigint(20) NOT NULL COMMENT '评论时间',
  `LAUD_NUM` int(11) DEFAULT '0' COMMENT '点赞量',
  `IS_DELETED` int(11) NOT NULL DEFAULT '0' COMMENT '默认0     1：已删除   0：未删除',
  `UPDATE_TIME` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `UPDATE_OPERATOR_ID` varchar(32) DEFAULT NULL COMMENT '更新人',
  `UPDATE_OPERATOR_NICK_NAME` varchar(32) DEFAULT NULL COMMENT '更新人昵称',
  PRIMARY KEY (`ID`,`CREATE_TIME`),
  KEY `IN_COMMENT_UID` (`USER_ID`),
  KEY `IN_COMMENT_VID` (`VIDEO_ID`),
  KEY `IN_COMMENT_CTIME` (`CREATE_TIME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论'
/*!50100 PARTITION BY RANGE (`CREATE_TIME`)
(PARTITION P_201609 VALUES LESS THAN (1475251200000) ENGINE = InnoDB,
 PARTITION P_201610 VALUES LESS THAN (1477929600000) ENGINE = InnoDB,
 PARTITION P_201611 VALUES LESS THAN (1480521600000) ENGINE = InnoDB,
 PARTITION P_201612 VALUES LESS THAN (1483200000000) ENGINE = InnoDB,
 PARTITION P_201701 VALUES LESS THAN (1485878400000) ENGINE = InnoDB,
 PARTITION P_201702 VALUES LESS THAN (1488297600000) ENGINE = InnoDB,
 PARTITION P_201703 VALUES LESS THAN (1490976000000) ENGINE = InnoDB,
 PARTITION P_201704 VALUES LESS THAN (1493568000000) ENGINE = InnoDB,
 PARTITION P_201705 VALUES LESS THAN (1496246400000) ENGINE = InnoDB,
 PARTITION P_201706 VALUES LESS THAN (1498838400000) ENGINE = InnoDB,
 PARTITION P_201707 VALUES LESS THAN (1501516800000) ENGINE = InnoDB,
 PARTITION P_201708 VALUES LESS THAN (1504195200000) ENGINE = InnoDB,
 PARTITION P_201709 VALUES LESS THAN (1506787200000) ENGINE = InnoDB,
 PARTITION P_201710 VALUES LESS THAN (1509465600000) ENGINE = InnoDB,
 PARTITION P_201711 VALUES LESS THAN (1512057600000) ENGINE = InnoDB,
 PARTITION P_201712 VALUES LESS THAN (1514736000000) ENGINE = InnoDB,
 PARTITION P_201801 VALUES LESS THAN (1517414400000) ENGINE = InnoDB,
 PARTITION P_201802 VALUES LESS THAN (1519833600000) ENGINE = InnoDB,
 PARTITION P_201803 VALUES LESS THAN (1522512000000) ENGINE = InnoDB,
 PARTITION P_201804 VALUES LESS THAN (1525104000000) ENGINE = InnoDB,
 PARTITION P_201805 VALUES LESS THAN (1527782400000) ENGINE = InnoDB,
 PARTITION P_201806 VALUES LESS THAN (1530374400000) ENGINE = InnoDB,
 PARTITION P_201807 VALUES LESS THAN (1533052800000) ENGINE = InnoDB,
 PARTITION P_201808 VALUES LESS THAN (1535731200000) ENGINE = InnoDB,
 PARTITION P_201809 VALUES LESS THAN (1538323200000) ENGINE = InnoDB,
 PARTITION P_201810 VALUES LESS THAN (1541001600000) ENGINE = InnoDB,
 PARTITION P_201811 VALUES LESS THAN (1543593600000) ENGINE = InnoDB,
 PARTITION P_201812 VALUES LESS THAN (1546272000000) ENGINE = InnoDB,
 PARTITION P_MAX VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;

-- ----------------------------
-- Table structure for t_seo_config
-- ----------------------------
DROP TABLE IF EXISTS `t_seo_config`;
CREATE TABLE `t_seo_config` (
  `ID` varchar(32) NOT NULL COMMENT '唯一标识',
  `keywords` varchar(512) DEFAULT NULL COMMENT 'key',
  `description` varchar(512) DEFAULT NULL COMMENT 'value',
  `title` varchar(512) DEFAULT NULL COMMENT 'title',
  `type` int(11) DEFAULT NULL COMMENT '类别: 1通用 2首页 3主页 4发现 5视频详情 6个人主页',
  `CREATE_TIME` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='SEO配置';

-- ----------------------------
-- Records of t_seo_config
-- ----------------------------
INSERT INTO `t_seo_config` VALUES ('05d656403581456186125db433f13f89', 'WoDota', 'WoDota', ' | WoDota', '4', '1477541143569', '1477541159234');
INSERT INTO `t_seo_config` VALUES ('34d535ce18e7430f9acfd7f7b18fcb92', 'Wodota,Wodota短视频,DOTA2游戏视频,守望先锋OW游戏视频,LOL游戏视频,主播秀,游戏录像,视频软件,屏幕录像', 'Wodota,Wodota短视频,DOTA2游戏视频,守望先锋OW游戏视频,LOL游戏视频,主播秀,游戏录像,视频软件,屏幕录像', ' | WoDota', '5', '1477541105506', '1477551285460');
INSERT INTO `t_seo_config` VALUES ('59b205d17d3c43e29e224ba60c90560c', 'WoDota', 'WoDota', ' | WoDota', '6', '1477541111958', null);
INSERT INTO `t_seo_config` VALUES ('656f44a794bd4a3d976c94644c0d2876', 'WoDota', 'WoDota', ' | WoDota', '2', '1477541136595', '1477541166178');
INSERT INTO `t_seo_config` VALUES ('c002373729904beebe74bfb2a53b3c5e', 'WoDota', 'WoDota', ' | WoDota', '3', '1477541149894', '1477541163067');
INSERT INTO `t_seo_config` VALUES ('f730a127e3c541b29155d58928369fcf', 'WoDota', 'WoDota', ' | WoDota', '1', '1477541116548', '1477985787212');

-- ----------------------------
-- Table structure for t_sms_log
-- ----------------------------
DROP TABLE IF EXISTS `t_sms_log`;
CREATE TABLE `t_sms_log` (
  `ID` varchar(32) NOT NULL COMMENT '唯一标识',
  `mobile` varchar(32) NOT NULL,
  `content` varchar(500) NOT NULL,
  `result` varchar(200) DEFAULT NULL,
  `send_time` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `IN_SMSLOG_MOBILE` (`mobile`),
  KEY `IN_SMSLOG_STIME` (`send_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信日志';

-- ----------------------------
-- Table structure for t_user_increment
-- ----------------------------
DROP TABLE IF EXISTS `t_user_increment`;
CREATE TABLE `t_user_increment` (
  `ID` varchar(32) NOT NULL COMMENT '唯一标识',
  `USER_ID` varchar(32) NOT NULL COMMENT '用户ID',
  `ATTENTION_NUM` int(11) DEFAULT NULL COMMENT '关注量',
  `FANS_NUM` int(11) DEFAULT '0' COMMENT '粉丝量',
  `ACTION_NUM` int(11) DEFAULT '0' COMMENT '动态量',
  `LEVEL` int(11) DEFAULT '0' COMMENT '等级',
  `INTEGRAL` int(11) DEFAULT '0' COMMENT '积分',
  `COMMENT_UNREAD` int(11) DEFAULT '0' COMMENT '回复未读',
  `LAUD_UNREAD` int(11) DEFAULT '0' COMMENT '点赞未读',
  `REWARD_UNREAD` int(11) DEFAULT '0' COMMENT '打赏未读',
  `RELATION_UNREAD` int(11) DEFAULT '0' COMMENT '关注未读',
  `NOTICE_UNREAD` int(11) DEFAULT '0' COMMENT '系统通知未读',
  `CREATE_TIME` bigint(20) NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `CREATE_OPERATOR_ID` varchar(32) DEFAULT NULL COMMENT '创建人',
  `UPDATE_OPERATOR_ID` varchar(32) DEFAULT NULL COMMENT '更新人',
  `IS_DELETED` int(11) NOT NULL DEFAULT '0' COMMENT '用户增量是否删除，用户删除时，该记录也相应删除，默认值0    0：未删除   1：已删除',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UN_INCREMENT_UID` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户增量表';

-- ----------------------------
-- Records of t_user_increment
-- ----------------------------
INSERT INTO `t_user_increment` VALUES ('1', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '1471231126132', '1479206164440', '4', '4', '0');

-- ----------------------------
-- Table structure for t_sys_config
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_config`;
CREATE TABLE `t_sys_config` (
  `ID` varchar(32) NOT NULL COMMENT '唯一标识',
  `config_key` varchar(100) DEFAULT NULL COMMENT '配置键',
  `config_value` varchar(500) DEFAULT NULL COMMENT '配置值',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_operator_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `update_operator_id` varchar(32) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配置表';

-- ----------------------------
-- Records of t_sys_config
-- ----------------------------
INSERT INTO `t_sys_config` VALUES ('1', 'sms_register', '{\"smsTemplateCode\":\"SMS_13023438\",\"appKey\":\"23436816\",\"secret\":\"9f00fe3dd94869d8a46bdef2c0286974\",\"smsSign\":\"BOCAI网\"}', null, null, null, null);
INSERT INTO `t_sys_config` VALUES ('2', 'gold_job_status', 'true', null, '4', '1478255003778', null);
INSERT INTO `t_sys_config` VALUES ('3', 'mobile_authentication', '{\"smsTemplateCode\":\"SMS_13023442\",\"appKey\":\"23436816\",\"secret\":\"9f00fe3dd94869d8a46bdef2c0286974\",\"smsSign\":\"BOCAI网\"}', null, null, null, null);
INSERT INTO `t_sys_config` VALUES ('4', 'modify_password', '{\"smsTemplateCode\":\"SMS_13023436\",\"appKey\":\"23436816\",\"secret\":\"9f00fe3dd94869d8a46bdef2c0286974\",\"smsSign\":\"BOCAI网\"}', null, null, null, null);

