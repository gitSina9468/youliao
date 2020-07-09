/*
Navicat MySQL Data Transfer

Source Server         : 公司开发
Source Server Version : 50639
Source Host           : 172.16.8.187:3307
Source Database       : youliao-security

Target Server Type    : MYSQL
Target Server Version : 50639
File Encoding         : 65001

Date: 2020-02-29 13:13:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `pid` int(11) NOT NULL COMMENT '父部门id',
  `dept_name` varchar(30) NOT NULL DEFAULT '' COMMENT '部门名称',
  `sort` int(4) DEFAULT NULL COMMENT '显示顺序',
  `enabled` bit(1) NOT NULL COMMENT '部门状态:1正常,0停用',
  `create_by` varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_dept_name` (`dept_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', '0', '海马集团', '0', '', '系统创建', '2020-02-11 18:57:38', '这是一个备注');
INSERT INTO `sys_dept` VALUES ('2', '1', '技术部', '1', '', 'admin', '2020-02-11 19:06:27', '技术部');
INSERT INTO `sys_dept` VALUES ('3', '1', '测试部', '2', '', 'admin', '2020-02-11 19:06:47', '测试部');
INSERT INTO `sys_dept` VALUES ('5', '0', '通威集团', '1', '', 'admin', '2020-02-12 14:41:49', null);
INSERT INTO `sys_dept` VALUES ('15', '5', '销售部', '12', '', 'admin', '2020-02-23 14:26:04', '这是一个销售部门');

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `code` varchar(64) NOT NULL COMMENT '岗位编码',
  `job` varchar(64) NOT NULL COMMENT '岗位名称',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门id',
  `enabled` bit(1) NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_job` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='岗位信息表';

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job` VALUES ('1', 'CTO', '技术总监', '1', '', '系统创建', '2020-02-11 18:52:49', '这是技术总监');
INSERT INTO `sys_job` VALUES ('2', 'develop', '开发实习生', null, '', 'admin', '2020-04-06 17:49:36', '大学刚毕业的');
INSERT INTO `sys_job` VALUES ('3', 'test', '测试人员', null, '', 'admin', '2020-04-07 20:21:29', '产品测试人员');

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `login_name` varchar(50) DEFAULT '' COMMENT '登录账号',
  `ip` varchar(50) DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) DEFAULT NULL,
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `status` int(1) DEFAULT '0' COMMENT '登录状态 0成功 1失败',
  `msg` varchar(255) DEFAULT '' COMMENT '提示消息',
  `login_time` datetime NOT NULL COMMENT '访问时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=165 DEFAULT CHARSET=utf8 COMMENT='系统访问记录';

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu` varchar(36) NOT NULL COMMENT '菜单名称',
  `is_frame` bit(1) NOT NULL COMMENT '是否外链',
  `component` varchar(100) DEFAULT NULL COMMENT '组件',
  `component_name` varchar(100) DEFAULT NULL COMMENT '组件名称',
  `path` varchar(100) DEFAULT '' COMMENT '请求地址',
  `redirect` varchar(100) DEFAULT NULL COMMENT '重定向',
  `pid` int(11) NOT NULL COMMENT '父级id',
  `type` int(1) NOT NULL COMMENT '类型:1目录,2菜单,3按钮',
  `visible` bit(1) NOT NULL COMMENT '菜单状态:显示,隐藏',
  `permission` varchar(100) DEFAULT '' COMMENT '权限标识',
  `cache` bit(1) DEFAULT NULL COMMENT '缓存',
  `icon` varchar(100) DEFAULT '' COMMENT '菜单图标',
  `sort` int(4) NOT NULL COMMENT '排序',
  `create_by` varchar(32) NOT NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(64) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_menu` (`menu`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8 COMMENT='菜单权限表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('2', '仪表盘', '\0', 'RouteView', 'dashboard', '/dashboard', '/dashboard/workplace', '0', '1', '', '', null, 'dashboard', '11', 'admin', '2020-01-26 19:17:04', '');
INSERT INTO `sys_menu` VALUES ('3', '工作台', '\0', 'dashboard/Workplace', 'Workplace', '/dashboard/workplace', null, '2', '2', '', '', null, '', '101', 'admin', '2020-01-26 19:20:28', '');
INSERT INTO `sys_menu` VALUES ('4', '分析页', '\0', 'dashboard/Analysis', 'Analysis', '/dashboard/analysis', null, '2', '2', '', '', null, '', '102', 'admin', '2020-01-26 19:21:27', '');
INSERT INTO `sys_menu` VALUES ('6', '测试功能', '\0', 'dashboard/TestWork', 'TestWork', '/dashboard/test-work', null, '2', '2', '', '', null, '', '104', 'admin', '2020-01-26 19:23:29', '');
INSERT INTO `sys_menu` VALUES ('7', '表单页', '\0', 'PageView', 'form', '/form', '/form/base-form', '0', '1', '', '', null, 'form', '12', 'admin', '2020-01-26 19:24:39', '');
INSERT INTO `sys_menu` VALUES ('8', '基础表单', '\0', 'form/BasicForm', 'BaseForm', '/form/base-form', null, '7', '2', '', '', null, '', '201', 'admin', '2020-01-26 19:25:51', '');
INSERT INTO `sys_menu` VALUES ('9', '分步表单', '\0', 'form/stepForm/StepForm', 'StepForm', '/form/step-form', null, '7', '2', '', '', null, '', '202', 'admin', '2020-01-26 19:26:46', '');
INSERT INTO `sys_menu` VALUES ('10', '高级表单', '\0', 'form/advancedForm/AdvancedForm', 'AdvanceForm', '/form/advanced-form', null, '7', '2', '', '', null, '', '203', 'admin', '2020-01-26 19:28:14', '');
INSERT INTO `sys_menu` VALUES ('11', '列表页', '\0', 'PageView', 'list', '/list', '/list/table-list', '0', '1', '', '', null, 'table', '13', 'admin', '2020-01-26 19:39:26', '');
INSERT INTO `sys_menu` VALUES ('12', '查询表格', '\0', 'list/TableList', 'TableListWrapper', '/list/table-list/:pageNo([1-9]\\d*)?', null, '11', '2', '', '', null, '', '301', 'admin', '2020-01-26 19:40:48', '');
INSERT INTO `sys_menu` VALUES ('13', '标准列表', '\0', 'list/StandardList', 'BasicList', '/list/basic-list', null, '11', '2', '', '', null, '', '302', 'admin', '2020-01-26 19:41:44', '');
INSERT INTO `sys_menu` VALUES ('14', '卡片表格', '\0', 'list/CardList', 'CardList', '/list/card', null, '11', '2', '', '', null, '', '303', 'admin', '2020-01-26 19:42:19', '');
INSERT INTO `sys_menu` VALUES ('15', '搜索表格', '\0', 'list/search/SearchLayout', 'SearchList', '/list/search', '/list/search/article', '11', '2', '', '', null, '', '304', 'admin', '2020-01-26 19:43:05', '');
INSERT INTO `sys_menu` VALUES ('16', '搜索列表（文章）', '\0', 'list/search/Article', 'SearchArticles', '/list/search/article', null, '15', '2', '', '', null, '', '3401', 'admin', '2020-01-26 19:44:33', '');
INSERT INTO `sys_menu` VALUES ('17', '搜索列表（项目）', '\0', 'list/search/Projects', 'SearchProjects', '/list/search/project', null, '15', '2', '', '', null, '', '3402', 'admin', '2020-01-26 19:45:28', '');
INSERT INTO `sys_menu` VALUES ('18', '搜索列表（应用）', '\0', 'list/search/Applications', 'SearchApplications', '/list/search/application', null, '15', '2', '', '', null, '', '3403', 'admin', '2020-01-26 19:49:22', '');
INSERT INTO `sys_menu` VALUES ('19', '详情页', '\0', 'RouteView', 'profile', '/profile', '/profile/basic', '0', '1', '', '', null, 'profile', '14', 'admin', '2020-01-26 19:54:54', '');
INSERT INTO `sys_menu` VALUES ('20', '基础详情页', '\0', 'profile/basic/Index', 'ProfileBasic', '/profile/basic', null, '19', '2', '', '', null, '', '401', 'admin', '2020-01-26 19:56:31', '');
INSERT INTO `sys_menu` VALUES ('21', '高级详情页', '\0', 'profile/advanced/Advanced', 'ProfileAdvanced', '/profile/advanced', null, '19', '2', '', '', null, '', '402', 'admin', '2020-01-26 20:03:38', '');
INSERT INTO `sys_menu` VALUES ('22', '结果页', '\0', 'PageView', 'result', '/result', '/result/success', '0', '1', '', '', null, 'check-circle-o', '15', 'admin', '2020-01-26 20:05:29', '');
INSERT INTO `sys_menu` VALUES ('23', '成功', '\0', 'result/Success', 'ResultSuccess', '/result/success', null, '22', '2', '', '', null, '', '501', 'admin', '2020-01-26 20:06:20', '');
INSERT INTO `sys_menu` VALUES ('24', '失败', '\0', 'result/Error', 'ResultFail', '/result/fail', null, '22', '2', '', '', null, '', '502', 'admin', '2020-01-26 20:06:56', '');
INSERT INTO `sys_menu` VALUES ('25', '异常页', '\0', 'RouteView', 'RouteView', '/exception', '/exception/403', '0', '1', '', '', null, 'warning', '16', 'admin', '2020-01-26 20:07:46', '');
INSERT INTO `sys_menu` VALUES ('26', '403', '\0', 'exception/403', 'Exception403', '/exception/403', null, '25', '2', '', '', null, '', '601', 'admin', '2020-01-26 20:09:02', '');
INSERT INTO `sys_menu` VALUES ('27', '404', '\0', 'exception/404', 'Exception404', '/exception/404', null, '25', '2', '', '', null, '', '602', 'admin', '2020-01-26 20:09:48', '');
INSERT INTO `sys_menu` VALUES ('28', '500', '\0', 'exception/500', 'Exception500', '/exception/500', null, '25', '2', '', '', null, '', '603', 'admin', '2020-01-26 20:10:14', '');
INSERT INTO `sys_menu` VALUES ('29', '个人页', '\0', 'RouteView', 'account', '/account', '/account/center', '0', '1', '', '', null, 'user', '17', 'admin', '2020-01-26 20:11:30', '');
INSERT INTO `sys_menu` VALUES ('30', '个人中心', '\0', 'account/center/Index', 'center', '/account/center', null, '29', '2', '', '', null, '', '701', 'admin', '2020-01-26 20:12:31', '');
INSERT INTO `sys_menu` VALUES ('31', '个人设置', '\0', 'account/settings/Index', 'settings', '/account/settings', '/account/settings/base', '29', '2', '', '', null, '', '702', 'admin', '2020-01-26 20:13:33', '');
INSERT INTO `sys_menu` VALUES ('32', '基本设置', '\0', 'account/settings/BaseSetting', 'BaseSettings', '/account/settings/base', null, '31', '2', '', '', null, '', '7201', 'admin', '2020-01-26 20:16:14', '');
INSERT INTO `sys_menu` VALUES ('33', '安全设置', '\0', 'account/settings/Security', 'SecuritySettings', '/account/settings/security', null, '31', '2', '', '', null, '', '7202', 'admin', '2020-01-26 20:16:55', '');
INSERT INTO `sys_menu` VALUES ('34', '个性化设置', '\0', 'account/settings/Custom', 'CustomSettings', '/account/settings/custom', null, '31', '2', '', '', null, '', '7203', 'admin', '2020-01-26 20:17:51', '');
INSERT INTO `sys_menu` VALUES ('35', '账户绑定', '\0', 'account/settings/Binding', 'BindingSettings', '/account/settings/binding', null, '31', '2', '', '', null, '', '7204', 'admin', '2020-01-26 20:18:22', '');
INSERT INTO `sys_menu` VALUES ('36', '新消息通知', '\0', 'account/settings/Notification', 'NotificationSettings', '/account/settings/notification', null, '31', '2', '', '', null, '', '7205', 'admin', '2020-01-26 20:18:59', '');
INSERT INTO `sys_menu` VALUES ('37', '其他组件', '\0', 'PageView', 'otherPage', '/other', '/other/icon-selector', '0', '1', '', '', null, 'slack', '18', 'admin', '2020-01-26 20:20:56', '');
INSERT INTO `sys_menu` VALUES ('38', 'IconSelector', '\0', 'other/IconSelectorView', 'TestIconSelect', '/other/icon-selector', null, '37', '2', '', '', null, '', '801', 'admin', '2020-01-26 20:22:04', '');
INSERT INTO `sys_menu` VALUES ('39', '业务布局', '\0', 'RouteView', 'other', '/other/list', '/other/list/tree-list', '37', '2', '', '', null, '', '802', 'admin', '2020-01-26 20:23:26', '');
INSERT INTO `sys_menu` VALUES ('40', '树目录表格', '\0', 'other/TreeList', 'TreeList', '/other/list/tree-list', null, '39', '2', '', '', null, '', '8201', 'admion', '2020-01-26 20:24:20', '');
INSERT INTO `sys_menu` VALUES ('41', '内联编辑表格', '\0', 'other/TableInnerEditList', 'EditList', '/other/list/edit-table', null, '39', '2', '', '', null, '', '8202', 'admin', '2020-01-26 20:24:58', '');
INSERT INTO `sys_menu` VALUES ('44', '网页链接', '', 'RouteView', 'iframe', '/iframe', null, '0', '1', '', null, '\0', 'branches', '12', 'admin', '2020-02-22 13:39:03', null);
INSERT INTO `sys_menu` VALUES ('45', 'swagger文档', '', 'IframePageView', 'swagger', 'http://132.232.43.102:8080/api/swagger-ui.html', null, '44', '2', '', null, '\0', null, '1', 'admin', '2020-02-22 13:41:32', null);
INSERT INTO `sys_menu` VALUES ('48', '系统大纲', '\0', 'dashboard/Guide', 'Guide', '/dashboard/guide', null, '2', '2', '\0', null, '', null, '102', 'admin', '2020-02-26 21:42:08', null);
INSERT INTO `sys_menu` VALUES ('49', '系统管理', '\0', 'RouteView', 'system', '/sys', '/sys/menu', '0', '1', '', null, '', 'setting', '11', 'admin', '2020-02-27 11:28:52', null);
INSERT INTO `sys_menu` VALUES ('50', '用户管理', '\0', 'system/UserList', 'user', '/sys/user', null, '49', '2', '', null, '', null, '1', 'admin', '2020-02-27 11:31:23', null);
INSERT INTO `sys_menu` VALUES ('51', '角色管理', '\0', 'system/RoleList', 'role', '/sys/role', null, '49', '2', '', null, '', null, '2', 'admin', '2020-02-27 11:32:01', null);
INSERT INTO `sys_menu` VALUES ('52', '菜单管理', '\0', 'system/PermissionList', 'menu', '/sys/menu', null, '49', '2', '', null, '', null, '3', 'admin', '2020-02-27 11:33:52', null);
INSERT INTO `sys_menu` VALUES ('53', '部门管理', '\0', 'system/DepartList', 'dept', '/sys/dept', null, '49', '2', '', null, '', null, '4', 'admin', '2020-02-27 11:34:42', null);
INSERT INTO `sys_menu` VALUES ('54', '部门用户', '\0', 'system/DepartUserList', 'userDept', '/sys/userDept', null, '49', '2', '', null, '', null, '5', 'admin', '2020-02-27 11:36:35', null);
INSERT INTO `sys_menu` VALUES ('55', '职位管理', '\0', 'system/JobList', 'job', '/sys/job', null, '49', '2', '', null, '', null, '6', 'admin', '2020-02-27 11:37:47', null);
INSERT INTO `sys_menu` VALUES ('58', '数据库监控', '', 'IframePageView', 'druid', 'http://132.232.43.102:8080/api/druid/login.html', null, '44', '2', '', null, '\0', null, '2', 'admin', '2020-02-27 11:42:34', null);
INSERT INTO `sys_menu` VALUES ('59', 'bootstrap-ui文档', '', 'IframePageView', 'swagger-bootstrap-ui', 'http://132.232.43.102:8080/api/doc.html', null, '44', '2', '', null, '\0', null, '3', 'admin', '2020-02-27 11:43:35', null);
INSERT INTO `sys_menu` VALUES ('60', '叫号大厅', '', 'IframePageView', 'LED', 'http://132.232.43.102:8080/api/screen/LED', null, '44', '2', '', null, '\0', null, '4', 'admin', '2020-02-27 11:44:33', null);
INSERT INTO `sys_menu` VALUES ('61', '进程日志', '', 'IframePageView', 'log', 'http://132.232.43.102:8080/api/log/view', null, '44', '2', '', null, '\0', null, '5', 'admin', '2020-02-27 11:45:12', null);
INSERT INTO `sys_menu` VALUES ('62', '系统监控', '\0', 'RouteView', 'monitor', '/monitor', '/monitor/disk', '0', '1', '', null, '\0', 'dashboard', '13', 'admin', '2020-02-27 11:46:11', null);
INSERT INTO `sys_menu` VALUES ('63', 'Redis监控', '\0', 'youliao/monitor/Redis', 'redis', '/monitor/redis', null, '62', '2', '', null, '\0', null, '6', 'admin', '2020-02-27 11:47:39', null);
INSERT INTO `sys_menu` VALUES ('64', '系统日志', '\0', 'system/LogList', 'logList', '/sys/log', null, '49', '2', '', null, '', null, '7', 'admin', '2020-03-13 11:32:42', null);
INSERT INTO `sys_menu` VALUES ('65', '常见案列', '\0', 'RouteView', 'youliao', '/youliao', '/youliao/printDemo', '0', '1', '', null, '', 'qrcode', '12', 'admin', '2020-03-15 17:01:39', null);
INSERT INTO `sys_menu` VALUES ('66', 'OSS存储', '\0', 'youliao/oss/OSSFileList', 'oss', '/youliao/oss', null, '65', '2', '', null, '', null, '1', 'admin', '2020-03-15 17:03:06', null);
INSERT INTO `sys_menu` VALUES ('67', '支付列表', '\0', 'youliao/pay/PayList', 'pay', '/youliao/pay', null, '65', '2', '', null, '', null, '2', 'admin', '2020-03-15 17:55:44', null);
INSERT INTO `sys_menu` VALUES ('68', '磁盘信息', '\0', 'youliao/monitor/Disk', 'disk', '/monitor/disk', null, '62', '2', '', null, '', null, '2', 'guest', '2020-04-01 21:23:51', null);
INSERT INTO `sys_menu` VALUES ('69', 'JVM信息', '\0', 'youliao/monitor/JvmInfo', 'jvmInfo', '/monitor/jvmInfo', null, '62', '2', '', null, '', null, '3', 'guest', '2020-04-01 21:24:41', null);
INSERT INTO `sys_menu` VALUES ('70', '服务器信息', '\0', 'youliao/monitor/Server', 'server', '/monitor/server', null, '62', '2', '', null, '', null, '4', 'guest', '2020-04-01 21:25:16', null);
INSERT INTO `sys_menu` VALUES ('71', 'Tomcat信息', '\0', 'youliao/monitor/TomcatInfo', 'tomcat', '/monitor/tomcatInfo', null, '62', '2', '', null, '', null, '5', 'guest', '2020-04-01 21:26:02', null);
INSERT INTO `sys_menu` VALUES ('72', '新增角色', '\0', null, null, null, null, '51', '3', '', 'role:add', '', null, '1', 'admin', '2020-04-06 17:21:15', null);
INSERT INTO `sys_menu` VALUES ('73', '编辑角色', '\0', null, null, null, null, '51', '3', '', 'role:update', '', null, '2', 'admin', '2020-04-06 17:21:47', null);
INSERT INTO `sys_menu` VALUES ('74', '删除角色', '\0', null, null, null, null, '51', '3', '', 'role:delete', '', null, '3', 'admin', '2020-04-06 17:22:25', null);
INSERT INTO `sys_menu` VALUES ('75', '批量删除角色', '\0', null, null, '', null, '51', '3', '', 'role:batch;delete', '', null, '4', 'admin', '2020-04-06 17:23:25', null);
INSERT INTO `sys_menu` VALUES ('76', '角色授权', '\0', null, null, null, null, '51', '3', '', 'role:auth', '', null, '5', 'admin', '2020-04-06 17:23:48', null);
INSERT INTO `sys_menu` VALUES ('77', '新增用户', '\0', null, null, null, null, '50', '3', '', 'user:add', '', null, '1', 'admin', '2020-04-06 17:24:55', null);
INSERT INTO `sys_menu` VALUES ('78', '编辑用户', '\0', null, null, null, null, '50', '3', '', 'user:update', '', null, '2', 'admin', '2020-04-06 17:25:07', null);
INSERT INTO `sys_menu` VALUES ('79', '修改密码', '\0', null, null, null, null, '50', '3', '', 'user:password:update', '', null, '3', 'admin', '2020-04-06 17:37:38', null);
INSERT INTO `sys_menu` VALUES ('80', '冻结用户', '\0', null, null, null, null, '50', '3', '', 'user:batch:frozen', '', null, '3', 'admin', '2020-04-06 17:38:05', null);
INSERT INTO `sys_menu` VALUES ('81', '删除用户', '\0', null, null, null, null, '50', '3', '', 'user:delete', '', null, '4', 'admin', '2020-04-06 17:38:23', null);
INSERT INTO `sys_menu` VALUES ('82', '批量删除用户', '\0', null, null, null, null, '50', '3', '', 'user:batch:delete', '', null, '5', 'admin', '2020-04-06 17:39:12', null);
INSERT INTO `sys_menu` VALUES ('83', '新增职位', '\0', null, null, null, null, '55', '3', '', 'job:add', '', null, '1', 'admin', '2020-04-06 17:39:58', null);
INSERT INTO `sys_menu` VALUES ('84', '编辑职位', '\0', null, null, null, null, '55', '3', '', 'job:update', '', null, '2', 'admin', '2020-04-06 17:40:19', null);
INSERT INTO `sys_menu` VALUES ('85', '删除职位', '\0', null, null, null, null, '55', '3', '', 'job:delete', '', null, '3', 'admin', '2020-04-06 17:40:42', null);
INSERT INTO `sys_menu` VALUES ('86', '批量删除职位', '\0', null, null, null, null, '55', '3', '', 'job:batch;delete', '', null, '4', 'admin', '2020-04-06 17:41:10', null);
INSERT INTO `sys_menu` VALUES ('87', '打印测试', '\0', 'youliao/PrintDemo', 'printDemo', '/youliao/printDemo', null, '65', '2', '', null, '', null, '3', 'admin', '2020-04-06 18:09:04', null);
INSERT INTO `sys_menu` VALUES ('88', '登录日志', '\0', 'system/LoginLog', 'loginLog', '/login/log', null, '62', '2', '', null, '', null, '1', 'admin', '2020-04-06 21:06:46', null);
INSERT INTO `sys_menu` VALUES ('89', '新增部门', '\0', null, null, null, null, '53', '3', '', 'dept:add', '', null, '1', 'admin', '2020-04-06 21:27:17', null);
INSERT INTO `sys_menu` VALUES ('90', '用户绑定部门', '\0', null, null, null, null, '54', '3', '', 'user:dept:add', '', null, '1', 'admin', '2020-04-07 14:49:50', null);
INSERT INTO `sys_menu` VALUES ('91', '用户部门解除关系', '\0', null, null, null, null, '54', '3', '', 'user:dept:delete', '', null, '2', 'admin', '2020-04-07 14:50:27', null);
INSERT INTO `sys_menu` VALUES ('92', '编辑部门', '\0', null, null, null, null, '53', '3', '', 'dept:update', '', null, '2', 'admin', '2020-04-07 14:56:20', null);
INSERT INTO `sys_menu` VALUES ('93', '删除部门', '\0', null, null, null, null, '53', '3', '', 'dept:delete', '', null, '3', 'admin', '2020-04-07 14:56:40', null);
INSERT INTO `sys_menu` VALUES ('94', '批量删除部门', '\0', null, null, null, null, '53', '3', '', 'dept:batch:delete', '', null, '4', 'admin', '2020-04-07 14:57:39', null);
INSERT INTO `sys_menu` VALUES ('95', '上传OSS文件', '\0', null, null, null, null, '66', '3', '', 'oss:upload', '', null, '1', 'admin', '2020-04-07 15:00:20', null);
INSERT INTO `sys_menu` VALUES ('96', '删除OSS文件', '\0', null, null, null, null, '66', '3', '', 'oss:delete', '', null, '3', 'admin', '2020-04-07 15:00:48', null);
INSERT INTO `sys_menu` VALUES ('97', '下载OSS文件', '\0', null, null, null, null, '66', '3', '', 'oss:downLoad', '', null, '2', 'admin', '2020-04-07 15:01:12', null);
INSERT INTO `sys_menu` VALUES ('98', '商品下单', '\0', null, null, null, null, '67', '3', '', 'order:unified', '', null, '1', 'admin', '2020-04-07 15:01:54', null);
INSERT INTO `sys_menu` VALUES ('99', '订单退款', '\0', null, null, null, null, '67', '3', '', 'order:refund', '', null, '2', 'admin', '2020-04-07 15:02:20', null);
INSERT INTO `sys_menu` VALUES ('100', '删除订单', '\0', null, null, null, null, '67', '3', '', 'order:delete', '', null, '3', 'admin', '2020-04-07 15:02:40', null);
INSERT INTO `sys_menu` VALUES ('101', 'pdf小票', '\0', 'youliao/PdfBill', 'bill', '/youliao/bill', null, '65', '2', '', null, '', null, '4', 'admin', '2020-04-09 15:31:21', null);
INSERT INTO `sys_menu` VALUES ('102', 'Redis缓存', '\0', 'youliao/redis/RedisCache', 'redisCache', '/youliao/redisCache', null, '65', '2', '', null, '', null, '5', 'admin', '2020-04-09 15:34:32', null);

-- ----------------------------
-- Table structure for sys_oss_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss_file`;
CREATE TABLE `sys_oss_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(36) DEFAULT NULL COMMENT '文件名称',
  `file_url` varchar(255) DEFAULT NULL COMMENT '地址',
  `file_size` decimal(10,2) DEFAULT NULL COMMENT '文件大小 kb',
  `type` varchar(36) DEFAULT NULL COMMENT '文件类型 excel ppt pdf txt ...',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(36) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='oss文件管理';

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_code` varchar(32) NOT NULL COMMENT '角色编码',
  `role_name` varchar(32) NOT NULL COMMENT '角色名称',
  `permission` varchar(100) DEFAULT NULL COMMENT '角色权限字符串',
  `level` int(2) DEFAULT NULL COMMENT '角色级别',
  `data_scope` varchar(100) DEFAULT NULL COMMENT '数据权限',
  `create_by` varchar(32) NOT NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(64) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_role_code` (`role_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='角色信息表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'admin', '管理员角色', 'all', '1', null, '系统创建', '2020-02-07 14:36:37', '这是管理员角色');
INSERT INTO `sys_role` VALUES ('6', 'test', '测试角色', null, null, null, 'admin', '2020-02-17 21:40:10', '测试角色');
INSERT INTO `sys_role` VALUES ('7', '1', 'test', null, null, null, 'guest', '2020-04-06 17:52:57', '1');

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `dept_id` int(11) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`role_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和部门关联表';

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1', '2');
INSERT INTO `sys_role_menu` VALUES ('1', '3');
INSERT INTO `sys_role_menu` VALUES ('1', '4');
INSERT INTO `sys_role_menu` VALUES ('1', '6');
INSERT INTO `sys_role_menu` VALUES ('1', '7');
INSERT INTO `sys_role_menu` VALUES ('1', '8');
INSERT INTO `sys_role_menu` VALUES ('1', '9');
INSERT INTO `sys_role_menu` VALUES ('1', '10');
INSERT INTO `sys_role_menu` VALUES ('1', '11');
INSERT INTO `sys_role_menu` VALUES ('1', '12');
INSERT INTO `sys_role_menu` VALUES ('1', '13');
INSERT INTO `sys_role_menu` VALUES ('1', '14');
INSERT INTO `sys_role_menu` VALUES ('1', '15');
INSERT INTO `sys_role_menu` VALUES ('1', '16');
INSERT INTO `sys_role_menu` VALUES ('1', '17');
INSERT INTO `sys_role_menu` VALUES ('1', '18');
INSERT INTO `sys_role_menu` VALUES ('1', '19');
INSERT INTO `sys_role_menu` VALUES ('1', '20');
INSERT INTO `sys_role_menu` VALUES ('1', '21');
INSERT INTO `sys_role_menu` VALUES ('1', '22');
INSERT INTO `sys_role_menu` VALUES ('1', '23');
INSERT INTO `sys_role_menu` VALUES ('1', '24');
INSERT INTO `sys_role_menu` VALUES ('1', '25');
INSERT INTO `sys_role_menu` VALUES ('1', '26');
INSERT INTO `sys_role_menu` VALUES ('1', '27');
INSERT INTO `sys_role_menu` VALUES ('1', '28');
INSERT INTO `sys_role_menu` VALUES ('1', '29');
INSERT INTO `sys_role_menu` VALUES ('1', '30');
INSERT INTO `sys_role_menu` VALUES ('1', '31');
INSERT INTO `sys_role_menu` VALUES ('1', '32');
INSERT INTO `sys_role_menu` VALUES ('1', '33');
INSERT INTO `sys_role_menu` VALUES ('1', '34');
INSERT INTO `sys_role_menu` VALUES ('1', '35');
INSERT INTO `sys_role_menu` VALUES ('1', '36');
INSERT INTO `sys_role_menu` VALUES ('1', '37');
INSERT INTO `sys_role_menu` VALUES ('1', '38');
INSERT INTO `sys_role_menu` VALUES ('1', '39');
INSERT INTO `sys_role_menu` VALUES ('1', '40');
INSERT INTO `sys_role_menu` VALUES ('1', '41');
INSERT INTO `sys_role_menu` VALUES ('1', '44');
INSERT INTO `sys_role_menu` VALUES ('1', '45');
INSERT INTO `sys_role_menu` VALUES ('1', '48');
INSERT INTO `sys_role_menu` VALUES ('1', '49');
INSERT INTO `sys_role_menu` VALUES ('1', '50');
INSERT INTO `sys_role_menu` VALUES ('1', '51');
INSERT INTO `sys_role_menu` VALUES ('1', '52');
INSERT INTO `sys_role_menu` VALUES ('1', '53');
INSERT INTO `sys_role_menu` VALUES ('1', '54');
INSERT INTO `sys_role_menu` VALUES ('1', '55');
INSERT INTO `sys_role_menu` VALUES ('1', '58');
INSERT INTO `sys_role_menu` VALUES ('1', '59');
INSERT INTO `sys_role_menu` VALUES ('1', '60');
INSERT INTO `sys_role_menu` VALUES ('1', '61');
INSERT INTO `sys_role_menu` VALUES ('1', '62');
INSERT INTO `sys_role_menu` VALUES ('1', '63');
INSERT INTO `sys_role_menu` VALUES ('1', '64');
INSERT INTO `sys_role_menu` VALUES ('1', '65');
INSERT INTO `sys_role_menu` VALUES ('1', '66');
INSERT INTO `sys_role_menu` VALUES ('1', '67');
INSERT INTO `sys_role_menu` VALUES ('1', '68');
INSERT INTO `sys_role_menu` VALUES ('1', '69');
INSERT INTO `sys_role_menu` VALUES ('1', '70');
INSERT INTO `sys_role_menu` VALUES ('1', '71');
INSERT INTO `sys_role_menu` VALUES ('1', '72');
INSERT INTO `sys_role_menu` VALUES ('1', '73');
INSERT INTO `sys_role_menu` VALUES ('1', '74');
INSERT INTO `sys_role_menu` VALUES ('1', '75');
INSERT INTO `sys_role_menu` VALUES ('1', '76');
INSERT INTO `sys_role_menu` VALUES ('1', '77');
INSERT INTO `sys_role_menu` VALUES ('1', '78');
INSERT INTO `sys_role_menu` VALUES ('1', '79');
INSERT INTO `sys_role_menu` VALUES ('1', '80');
INSERT INTO `sys_role_menu` VALUES ('1', '81');
INSERT INTO `sys_role_menu` VALUES ('1', '82');
INSERT INTO `sys_role_menu` VALUES ('1', '83');
INSERT INTO `sys_role_menu` VALUES ('1', '84');
INSERT INTO `sys_role_menu` VALUES ('1', '85');
INSERT INTO `sys_role_menu` VALUES ('1', '86');
INSERT INTO `sys_role_menu` VALUES ('1', '87');
INSERT INTO `sys_role_menu` VALUES ('1', '88');
INSERT INTO `sys_role_menu` VALUES ('1', '89');
INSERT INTO `sys_role_menu` VALUES ('1', '90');
INSERT INTO `sys_role_menu` VALUES ('1', '91');
INSERT INTO `sys_role_menu` VALUES ('1', '92');
INSERT INTO `sys_role_menu` VALUES ('1', '93');
INSERT INTO `sys_role_menu` VALUES ('1', '94');
INSERT INTO `sys_role_menu` VALUES ('1', '95');
INSERT INTO `sys_role_menu` VALUES ('1', '96');
INSERT INTO `sys_role_menu` VALUES ('1', '97');
INSERT INTO `sys_role_menu` VALUES ('1', '98');
INSERT INTO `sys_role_menu` VALUES ('1', '99');
INSERT INTO `sys_role_menu` VALUES ('1', '100');
INSERT INTO `sys_role_menu` VALUES ('1', '101');
INSERT INTO `sys_role_menu` VALUES ('1', '102');
INSERT INTO `sys_role_menu` VALUES ('6', '2');
INSERT INTO `sys_role_menu` VALUES ('6', '3');
INSERT INTO `sys_role_menu` VALUES ('6', '4');
INSERT INTO `sys_role_menu` VALUES ('6', '6');
INSERT INTO `sys_role_menu` VALUES ('6', '22');
INSERT INTO `sys_role_menu` VALUES ('6', '23');
INSERT INTO `sys_role_menu` VALUES ('6', '24');
INSERT INTO `sys_role_menu` VALUES ('6', '25');
INSERT INTO `sys_role_menu` VALUES ('6', '26');
INSERT INTO `sys_role_menu` VALUES ('6', '27');
INSERT INTO `sys_role_menu` VALUES ('6', '28');
INSERT INTO `sys_role_menu` VALUES ('6', '44');
INSERT INTO `sys_role_menu` VALUES ('6', '45');
INSERT INTO `sys_role_menu` VALUES ('6', '48');
INSERT INTO `sys_role_menu` VALUES ('6', '49');
INSERT INTO `sys_role_menu` VALUES ('6', '50');
INSERT INTO `sys_role_menu` VALUES ('6', '51');
INSERT INTO `sys_role_menu` VALUES ('6', '52');
INSERT INTO `sys_role_menu` VALUES ('6', '55');
INSERT INTO `sys_role_menu` VALUES ('6', '58');
INSERT INTO `sys_role_menu` VALUES ('6', '60');
INSERT INTO `sys_role_menu` VALUES ('6', '62');
INSERT INTO `sys_role_menu` VALUES ('6', '63');
INSERT INTO `sys_role_menu` VALUES ('6', '64');
INSERT INTO `sys_role_menu` VALUES ('6', '65');
INSERT INTO `sys_role_menu` VALUES ('6', '66');
INSERT INTO `sys_role_menu` VALUES ('6', '67');
INSERT INTO `sys_role_menu` VALUES ('6', '68');
INSERT INTO `sys_role_menu` VALUES ('6', '70');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(32) NOT NULL DEFAULT '' COMMENT '用户名称',
  `nick_name` varchar(32) DEFAULT NULL COMMENT '昵称',
  `email` varchar(100) DEFAULT '' COMMENT '用户邮箱',
  `phone` varchar(20) NOT NULL DEFAULT '' COMMENT '手机号码',
  `birthday` varchar(20) DEFAULT NULL COMMENT '生日',
  `sex` char(1) DEFAULT NULL COMMENT '性别 男 女',
  `avatar` varchar(100) DEFAULT NULL COMMENT '用户头像',
  `password` varchar(100) NOT NULL DEFAULT '' COMMENT '密码',
  `enabled` bit(1) NOT NULL COMMENT '帐号状态:1正常,0禁用',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门id',
  `job_id` int(11) DEFAULT NULL COMMENT '岗位id',
  `create_by` varchar(32) NOT NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `login_ip` varchar(32) DEFAULT '' COMMENT '最后登录ip',
  `login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `remark` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_user_name` (`user_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '超级管理员', '470472264@qq.com', '18202838208', '2020-02-07', '男', '2020-04-06/a625d188-2bb7-4d7e-ad01-afd67505c596.jpg', '$2a$10$doq0XR1.fKVkldSgiu9TXuNeQaNXS1oIZnXGPKorQIHTS7TPAdbOK', '', '1', '1', '系统创建', '2020-02-07 10:37:46', '127.0.0.1', '2020-02-07 10:37:54', '这是超级管理员拥有最大权限');
INSERT INTO `sys_user` VALUES ('2', '强仔', 'qwe', '470472264@qq.com', '18202838208', '2020-02-17', '男', '2020-04-04/9694a509-cad0-4b20-a01b-8bf21451c97c.jpg', '$2a$10$iGpzhFk6KhZ.YERWnYaRLuSzVZL14W6V26PcCqqQ/OHVNQaG38SCK', '', '5', '1', 'admin', '2020-02-17 21:25:20', null, null, null);
INSERT INTO `sys_user` VALUES ('4', '王麻子', '123123123', '12372264@qq.com', '18302838208', '2020-02-23', '男', '2020-04-04/fb89a546-913f-44a8-8956-bd325eae98ec.jpg', '$2a$10$ezylMhHWrGH.vPz0sldmjO6g7kVRM50OJUMBR6gczshsNxeO27ARW', '', '5', '2', 'admin', '2020-02-23 11:36:00', null, null, null);
INSERT INTO `sys_user` VALUES ('9', 'guest', 'guest', 'guest666@qq.com', '18888888888', '2020-03-30', '男', '2020-04-04/1799a2c9-f055-438f-9446-c01eb058236a.jpg', '$2a$10$c/dczJE/ysHXO/FhIQ8mFuAMCuZsR8Ks9NknsT2qMKey50GOXbzBS', '', null, '3', 'admin', '2020-03-30 17:39:07', null, null, null);

-- ----------------------------
-- Table structure for sys_user_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_job`;
CREATE TABLE `sys_user_job` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `job_id` int(11) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`,`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与岗位关联表';

-- ----------------------------
-- Records of sys_user_job
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户和角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');
INSERT INTO `sys_user_role` VALUES ('1', '6');
INSERT INTO `sys_user_role` VALUES ('2', '6');
INSERT INTO `sys_user_role` VALUES ('4', '6');
INSERT INTO `sys_user_role` VALUES ('8', '6');
INSERT INTO `sys_user_role` VALUES ('9', '6');
