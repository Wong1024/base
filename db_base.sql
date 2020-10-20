/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : db_base

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 20/10/2020 15:13:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_depart
-- ----------------------------
DROP TABLE IF EXISTS `sys_depart`;
CREATE TABLE `sys_depart`  (
  `id` bigint(20) UNSIGNED NOT NULL COMMENT '部门id',
  `parent_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '上级部门id（为0时没有上级部门）',
  `depart_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '部门名称',
  `delete_flag` tinyint(1) NULL DEFAULT 0,
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_depart
-- ----------------------------
INSERT INTO `sys_depart` VALUES (1, 0, '部门1', 0, '2020-10-09 09:24:26', '2020-10-09 17:09:13');
INSERT INTO `sys_depart` VALUES (764088233103847424, 0, '部门5', 0, '2020-10-09 11:33:50', '2020-10-09 11:33:50');
INSERT INTO `sys_depart` VALUES (764088263177007104, 1, '测试1', 0, '2020-10-09 11:33:58', '2020-10-09 17:09:16');
INSERT INTO `sys_depart` VALUES (764088313244413952, 764088233103847424, '测试2', 0, '2020-10-09 11:34:09', '2020-10-12 15:42:06');
INSERT INTO `sys_depart` VALUES (765237936889655296, 1, 'test', 0, '2020-10-12 15:42:21', '2020-10-12 15:42:21');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(10) UNSIGNED NOT NULL,
  `parent_id` bigint(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '父菜单id',
  `menu_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  `type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0-菜单组，1-功能菜单；菜单组下没有可访问功能菜单时不显示',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单功能描述',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单路径',
  `extra_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '额外赋予权限的路径（部分功能需要切换页面）',
  `menu_icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '在菜单中显示的图标（对非菜单功能无效）',
  `new_window` tinyint(1) NULL DEFAULT 0 COMMENT '是否弹出新窗口',
  `sort` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '排序顺序',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单表\r\n\r\n\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (765977613380743168, 0, '首页', 1, '首页', '/', '', 'el-icon-s-home', 0, 1, '2020-10-14 16:41:34', '2020-10-19 11:37:14');
INSERT INTO `sys_menu` VALUES (765984830624493568, 0, '系统管理', 0, '系统管理', '', '', '', 0, 2, '2020-10-14 17:10:14', '2020-10-14 17:10:14');
INSERT INTO `sys_menu` VALUES (766221166786768896, 765984830624493568, '用户管理', 1, '用户管理', '/system/userAdmin', '', '', 0, 1, '2020-10-15 08:49:21', '2020-10-19 09:09:46');
INSERT INTO `sys_menu` VALUES (766221273754103808, 765984830624493568, '角色管理', 1, '角色管理', '/system/roleAdmin', '', '', 0, 2, '2020-10-15 08:49:47', '2020-10-19 09:09:50');
INSERT INTO `sys_menu` VALUES (766222423907758080, 765984830624493568, '部门管理', 1, '部门管理', '/system/departAdmin', '', '', 0, 3, '2020-10-15 08:54:21', '2020-10-15 08:54:21');
INSERT INTO `sys_menu` VALUES (766222519563055104, 765984830624493568, '菜单管理', 1, '菜单管理', '/system/menuAdmin', '', '', 0, 4, '2020-10-15 08:54:44', '2020-10-15 08:54:44');
INSERT INTO `sys_menu` VALUES (766222608113201152, 765984830624493568, '权限管理', 1, '权限管理', '/system/permissionAdmin', '', '', 0, 5, '2020-10-15 08:55:05', '2020-10-15 08:55:05');

-- ----------------------------
-- Table structure for sys_menu_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_permission`;
CREATE TABLE `sys_menu_permission`  (
  `id` bigint(20) UNSIGNED NOT NULL,
  `menu_id` bigint(20) UNSIGNED NOT NULL,
  `permission_id` bigint(20) UNSIGNED NOT NULL,
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_menu_permission`(`menu_id`, `permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu_permission
-- ----------------------------
INSERT INTO `sys_menu_permission` VALUES (767687437898346496, 766221166786768896, 766707869339217920, '2020-10-19 09:55:48', '2020-10-19 09:55:48');
INSERT INTO `sys_menu_permission` VALUES (767687437898346498, 766221166786768896, 766708447419166720, '2020-10-19 09:55:48', '2020-10-19 09:55:48');
INSERT INTO `sys_menu_permission` VALUES (768036510874595328, 766221166786768896, 766708083810758656, '2020-10-20 09:02:53', '2020-10-20 09:02:53');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` bigint(20) UNSIGNED NOT NULL,
  `parent_id` bigint(20) UNSIGNED NOT NULL DEFAULT 0,
  `type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0-权限组，1-权限',
  `permission_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '接口权限名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '接口路径',
  `method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'GET，POST，PUT，DELETE等',
  `sort` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '排序顺序',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (766706878212599808, 0, 0, '系统管理', '', '', 0, '2020-10-16 16:59:24', '2020-10-16 16:59:24');
INSERT INTO `sys_permission` VALUES (766707869339217920, 766707914570592256, 1, '新增用户', '/users', 'POST', 1, '2020-10-16 17:03:20', '2020-10-16 17:03:38');
INSERT INTO `sys_permission` VALUES (766707914570592256, 766706878212599808, 0, '用户', '', '', 1, '2020-10-16 17:03:31', '2020-10-16 17:03:31');
INSERT INTO `sys_permission` VALUES (766708083810758656, 766707914570592256, 1, '修改用户', '/users/*', 'PUT', 2, '2020-10-16 17:04:11', '2020-10-16 17:04:11');
INSERT INTO `sys_permission` VALUES (766708447419166720, 766707914570592256, 1, '删除用户', '/users', 'DELETE', 3, '2020-10-16 17:05:38', '2020-10-16 17:05:38');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) UNSIGNED NOT NULL,
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (760526706111279104, '系统管理员', '2020-09-29 15:41:36', '2020-09-29 15:41:36');
INSERT INTO `sys_role` VALUES (764130373242580992, 'test', '2020-10-09 14:21:17', '2020-10-09 14:21:17');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(20) UNSIGNED NOT NULL,
  `role_id` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `menu_id` bigint(20) UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_role_menu`(`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (766320197764509696, 760526706111279104, 765977613380743168);
INSERT INTO `sys_role_menu` VALUES (766700441268776960, 760526706111279104, 766221166786768896);
INSERT INTO `sys_role_menu` VALUES (766320197768704000, 760526706111279104, 766221273754103808);
INSERT INTO `sys_role_menu` VALUES (766320256111472640, 760526706111279104, 766222423907758080);
INSERT INTO `sys_role_menu` VALUES (766320305604259840, 760526706111279104, 766222519563055104);
INSERT INTO `sys_role_menu` VALUES (766679112847060992, 760526706111279104, 766222608113201152);
INSERT INTO `sys_role_menu` VALUES (767685023854096384, 764130373242580992, 765977613380743168);
INSERT INTO `sys_role_menu` VALUES (767685023854096386, 764130373242580992, 766221273754103808);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) UNSIGNED NOT NULL,
  `depart_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '部门',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号名',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户姓名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `state` tinyint(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '状态，0-可用，1-禁用',
  `delete_at` bigint(20) NOT NULL DEFAULT 0 COMMENT '删除标志位，0-未删除，非0时为删除时间的毫秒数',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username`, `delete_at`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (760145763072602112, NULL, '123', '1231', '$2a$10$kH./WXazyYTXJAXcT/RyGuokzeXsfWUw2By363WXHgxSMtjDv26Nu', 0, 1, '2020-09-28 14:27:52', '2020-09-29 12:02:51');
INSERT INTO `sys_user` VALUES (760155385514815488, 1, 'admin', '管理员', '$2a$10$umH/JwuCE2ByK2E3q02aUevVzIQHGZiVj3ep9y/Yc4o5lhDuljsUy', 0, 0, '2020-09-28 15:06:07', '2020-10-19 17:09:19');
INSERT INTO `sys_user` VALUES (760446239257260032, 764088263177007104, 'test', '测试', '$2a$10$bzi6Ea5XW17K.IGm/yw54e9.Pg88EmahTt1RCFyXpcI0jNzSAse.q', 0, 0, '2020-09-29 10:21:51', '2020-10-12 16:18:30');
INSERT INTO `sys_user` VALUES (760455186362003456, 0, 'ttt', 'rew', '$2a$10$q7Nk3k/wjPZaRvG2xSedhuQV2t3.jh7p6dVxoVCHYSnSzo79nGFCi', 0, 0, '2020-09-29 10:57:25', '2020-10-20 09:36:45');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) UNSIGNED NOT NULL,
  `user_id` bigint(20) UNSIGNED NOT NULL,
  `role_id` bigint(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_role`(`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户与角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (766301865506107392, 760155385514815488, 760526706111279104);

SET FOREIGN_KEY_CHECKS = 1;
