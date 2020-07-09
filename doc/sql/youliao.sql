/*
Navicat MySQL Data Transfer

Source Server         : 公司开发
Source Server Version : 50639
Source Host           : 172.16.8.187:3307
Source Database       : jinkai

Target Server Type    : MYSQL
Target Server Version : 50639
File Encoding         : 65001

Date: 2020-01-03 22:22:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for base_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `base_dictionary`;
CREATE TABLE `base_dictionary` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `code` varchar(50) NOT NULL COMMENT '字典编码',
  `name` varchar(50) NOT NULL COMMENT '字典名称',
  `type_code` varchar(50) NOT NULL COMMENT '字典类型编码',
  `type_name` varchar(50) NOT NULL COMMENT '字典类型名称',
  `deleted` bit(1) NOT NULL COMMENT '删除状态',
  `status` bit(1) NOT NULL COMMENT '使用状态  0 禁用  1 启用',
  `system_status` bit(1) DEFAULT NULL COMMENT '是否为系统字典 0 否 1是',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '数据创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '数据更新时间',
  `create_by` varchar(36) DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(36) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典表';

-- ----------------------------
-- Table structure for business_log
-- ----------------------------
DROP TABLE IF EXISTS `business_log`;
CREATE TABLE `business_log` (
  `id` varchar(36) NOT NULL COMMENT '主键id',
  `menu` varchar(36) DEFAULT NULL COMMENT '菜单',
  `button` varchar(36) DEFAULT NULL COMMENT '按钮',
  `target_name` varchar(200) DEFAULT NULL COMMENT '目标类',
  `method_name` varchar(200) DEFAULT NULL COMMENT '方法名称',
  `host` varchar(36) DEFAULT NULL COMMENT '操作ip',
  `request_params` varchar(500) DEFAULT NULL COMMENT '请求参数',
  `response_params` text COMMENT '返回参数',
  `result` varchar(12) DEFAULT NULL COMMENT '操作结果 失败 成功',
  `request_time` datetime DEFAULT NULL COMMENT '请求时间',
  `response_time` datetime DEFAULT NULL COMMENT '相应时间',
  `exception` text COMMENT '异常描述',
  `description` varchar(200) DEFAULT NULL COMMENT '操作描述',
  `operation_user` varchar(36) DEFAULT NULL COMMENT '操作人',
  `operation_type` varchar(36) DEFAULT NULL COMMENT '操作类型 ',
  `create_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`),
  KEY `idx_menu` (`menu`) USING BTREE,
  KEY `idx_button` (`button`) USING BTREE,
  KEY `idx_method` (`method_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务日志表';

-- ----------------------------
-- Table structure for fms_pay_order
-- ----------------------------
DROP TABLE IF EXISTS `fms_pay_order`;
CREATE TABLE `fms_pay_order` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_no` varchar(36) NOT NULL COMMENT '订单号',
  `product_name` varchar(36) NOT NULL COMMENT '商品名称',
  `description` varchar(255) NOT NULL COMMENT '商品描述',
  `out_trade_no` varchar(36) NOT NULL COMMENT '商户订单号',
  `transaction_id` varchar(36) DEFAULT NULL COMMENT '流水号-支付订单号',
  `nonce_str` varchar(36) DEFAULT NULL COMMENT '随机字符串',
  `pay_user` varchar(36) DEFAULT NULL COMMENT '缴费人',
  `phone` varchar(36) DEFAULT NULL COMMENT '手机号码',
  `qr_code_url` varchar(255) NOT NULL COMMENT '支付二维码路径',
  `pay_fee` decimal(12,2) NOT NULL COMMENT '缴费金额',
  `pay_type` int(2) NOT NULL COMMENT '缴费方式 1 微信 2 支付宝',
  `pay_status` int(2) NOT NULL COMMENT '支付状态  0 下单 1 支付中 2 支付失败  3 支付成功 ',
  `order_refund_status` bit(1) NOT NULL COMMENT '0 未退款 1 已退款',
  `refund_fee` decimal(12,2) NOT NULL COMMENT '退费金额',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(36) NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(36) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_order_no` (`order_no`) USING BTREE,
  UNIQUE KEY `uniq_out_trade_no` (`out_trade_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='支付订单';

-- ----------------------------
-- Table structure for fms_refund_order
-- ----------------------------
DROP TABLE IF EXISTS `fms_refund_order`;
CREATE TABLE `fms_refund_order` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `pay_id` bigint(11) NOT NULL COMMENT '支付id',
  `out_refund_no` varchar(36) NOT NULL COMMENT '商户退款单号',
  `refund_fee` decimal(12,2) NOT NULL COMMENT '退款金额',
  `refund_status` bit(1) DEFAULT NULL COMMENT '退款状态 0 失败  1 成功',
  `refund_user` varchar(36) NOT NULL COMMENT '退款人',
  `refund_reason` varchar(200) NOT NULL COMMENT '退款事由',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(36) NOT NULL COMMENT '创建人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_out_refund_no` (`out_refund_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='订单退费明细';