/*
 Navicat Premium Data Transfer

 Source Server         : 49.234.178.101_3306
 Source Server Type    : MariaDB
 Source Server Version : 100225
 Source Host           : 49.234.178.101:3306
 Source Schema         : ssm

 Target Server Type    : MariaDB
 Target Server Version : 100225
 File Encoding         : 65001

 Date: 14/08/2019 20:37:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for archimg
-- ----------------------------
DROP TABLE IF EXISTS `archimg`;
CREATE TABLE `archimg`  (
  `archid` int(11) NOT NULL COMMENT '报告id',
  `imgid` int(11) NOT NULL COMMENT '图片id',
  INDEX `archid`(`archid`) USING BTREE,
  INDEX `imgid`(`imgid`) USING BTREE,
  CONSTRAINT `archimg_ibfk_1` FOREIGN KEY (`archid`) REFERENCES `archive` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `archimg_ibfk_2` FOREIGN KEY (`imgid`) REFERENCES `image` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '报告_图片' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for archive
-- ----------------------------
DROP TABLE IF EXISTS `archive`;
CREATE TABLE `archive`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `unit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '委托单位（人）',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `material` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '提供资料情况',
  `addr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '房屋地址',
  `hold` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '户主名称',
  `holdid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `attr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '房屋属性',
  `layer` int(4) NULL DEFAULT NULL COMMENT '层数',
  `createyear` timestamp(0) NULL DEFAULT NULL COMMENT '建造时间',
  `typeid` int(11) NOT NULL COMMENT '结构类型id',
  `body1` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '相关资料',
  `body2` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主体结构概况',
  `body3` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结构现状描述',
  `rankid` int(11) NULL DEFAULT NULL COMMENT '危险性等级id',
  `rankratio` double(25, 20) NULL DEFAULT NULL COMMENT '危险构件比例',
  `advise` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '建议',
  `identitytime` timestamp(0) NOT NULL DEFAULT current_timestamp() ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '鉴定时间',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `userid` int(11) NOT NULL COMMENT '用户id',
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `typeid`(`typeid`) USING BTREE,
  INDEX `rankid`(`rankid`) USING BTREE,
  INDEX `userid`(`userid`) USING BTREE,
  CONSTRAINT `archive_ibfk_1` FOREIGN KEY (`typeid`) REFERENCES `type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `archive_ibfk_2` FOREIGN KEY (`rankid`) REFERENCES `rank` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `archive_ibfk_3` FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '报告' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for damage
-- ----------------------------
DROP TABLE IF EXISTS `damage`;
CREATE TABLE `damage`  (
  `archid` int(11) NOT NULL COMMENT '报告id',
  `a111` int(255) UNSIGNED NULL DEFAULT 0 COMMENT '共有中柱',
  `a112` int(255) UNSIGNED NULL DEFAULT 0 COMMENT '危险中柱',
  `a121` int(255) UNSIGNED NULL DEFAULT 0 COMMENT '共有边柱',
  `a122` int(255) UNSIGNED NULL DEFAULT 0 COMMENT '危险边柱',
  `a131` int(255) UNSIGNED NULL DEFAULT 0 COMMENT '共有角柱',
  `a132` int(255) UNSIGNED NULL DEFAULT 0 COMMENT '危险角柱',
  `a211` int(255) UNSIGNED NULL DEFAULT 0 COMMENT '共有屋架',
  `a212` int(255) UNSIGNED NULL DEFAULT 0 COMMENT '危险屋架',
  `a221` int(255) UNSIGNED NULL DEFAULT 0 COMMENT '共有中梁',
  `a222` int(255) UNSIGNED NULL DEFAULT 0 COMMENT '危险中梁',
  `a311` int(255) UNSIGNED NULL DEFAULT 0 COMMENT '共有边梁',
  `a312` int(255) UNSIGNED NULL DEFAULT 0 COMMENT '危险边梁',
  `a411` int(255) UNSIGNED NULL DEFAULT 0 COMMENT '共有称重墙体',
  `a412` int(255) UNSIGNED NULL DEFAULT 0 COMMENT '危险称重墙体',
  `a511` int(255) UNSIGNED NULL DEFAULT 0 COMMENT '共有楼板构件',
  `a512` int(255) UNSIGNED NULL DEFAULT 0 COMMENT '危险楼板构件',
  `a611` int(255) UNSIGNED NULL DEFAULT 0 COMMENT '共有围护构件',
  `a612` int(255) UNSIGNED NULL DEFAULT 0 COMMENT '危险围护构件',
  PRIMARY KEY (`archid`) USING BTREE,
  CONSTRAINT `damage_ibfk_1` FOREIGN KEY (`archid`) REFERENCES `archive` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '受损情况' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for image
-- ----------------------------
DROP TABLE IF EXISTS `image`;
CREATE TABLE `image`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '路径',
  `depict` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '图片' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for invite
-- ----------------------------
DROP TABLE IF EXISTS `invite`;
CREATE TABLE `invite`  (
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邀请码',
  `createid` int(11) NOT NULL COMMENT '生成用户 Id',
  `inviteid` int(11) NULL DEFAULT NULL COMMENT '被邀请用户 Id',
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '状态',
  PRIMARY KEY (`code`) USING BTREE,
  INDEX `createid`(`createid`) USING BTREE,
  INDEX `inviteid`(`inviteid`) USING BTREE,
  CONSTRAINT `invite_ibfk_1` FOREIGN KEY (`createid`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `invite_ibfk_2` FOREIGN KEY (`inviteid`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '邀请码' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rank
-- ----------------------------
DROP TABLE IF EXISTS `rank`;
CREATE TABLE `rank`  (
  `id` int(11) NOT NULL COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '危险性等级',
  `depict` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '比例区间描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '危险性等级' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rank
-- ----------------------------
INSERT INTO `rank` VALUES (2, 'B', '大于0但未超过比例5%');
INSERT INTO `rank` VALUES (3, 'C', '大于5%但未超过比例25%');
INSERT INTO `rank` VALUES (4, 'D', '大于比例25%');

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结构类型名',
  `body1` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '相关资料',
  `body2` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主体结构概况',
  `body3` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结构现状描述',
  `advise` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '建议',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '结构类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES (1, '砖木结构', '该房屋无正规设计图纸，无正规施工单位和监理单位，设计和施工资料缺失。', '1）建筑物上部主体结构为砖木结构，木柱下为石柱脚，砖墙下为砖砌条形基础；\n    2）房屋主要以[(、){砖墙}{木柱}{木屋架}{木檩条}]承重，楼面为[{木梁和木楼板}{木梁无楼板}]，屋面为木檩条挂小青瓦。', '[{1）基础工作基本正常，地基基础无明显不均匀沉降；}{1）基础工作不正常，地基基础轻微沉降；}{1）基础工作不正常，地基基础严重沉降；}]\n    2）上部承重构件工作基本正常，结构整体保持稳定，局部存在的质量缺陷有：\n        [(；\n        ){$[(、){木梁}{木柱}{木屋架}{木檩条}]存在[{轻微}{严重}]腐蚀现象}{$[(、){木梁}{木柱}{木屋架}{木檩条}]存在[{轻微}{严重}]开裂现象}{$柱脚有[{轻微}{严重}][(、){变形}{倾斜}{破损}]现象}{$屋面板存在[{轻微}{严重}][(、){破损}{渗水}{变形}]现象}{$墙体存在[{轻微}{严重}][(、){开裂}{倾斜}{破损}]现象}{$纵横墙无可靠拉接}]。', '[(；\n    ){$对腐蚀的木质构件进行防腐处理，处理方法可以涂刷木质清漆}{$对出现开裂的木质构件进行加固}{$对问题柱脚进行加固修复}{$[(、){对破损屋面视严重程度进行更换或加固}{对渗水墙面重新进行防水处理}{对变形屋面视严重程度进行更换或加固}]}{$[(、){对开裂砖墙原粉刷铲除，在墙体两侧铺设钢丝网片后用高标号水泥砂浆重新粉刷}{对倾斜墙面进行加固修复}{对破损砖墙原粉刷铲除，在墙体两侧铺设钢丝网片后用高标号水泥砂浆重新粉刷}]}{$对无可靠拉接的纵横墙节点进行加固}]。');
INSERT INTO `type` VALUES (2, '砖混结构', '该房屋无正规设计图纸，无正规施工单位和监理单位，设计和施工资料缺失。', '1）建筑物上部主体结构为砖混结构，砖墙下为砖砌条形基础；\n    2）房屋主要以砖墙和楼面板承重，楼面板为现浇钢筋混凝土板。', '[{1）基础工作基本正常，地基基础无明显不均匀沉降；}{1）基础工作不正常，地基基础轻微沉降；}{1）基础工作不正常，地基基础严重沉降；}]\n    2）上部承重构件工作基本正常，结构整体保持稳定，局部存在的质量缺陷有：\n        [(；\n        ){$墙体存在[{轻微}{严重}]腐蚀现象}{$[{墙体}{楼面板}]存在[{轻微}{严重}]渗水现象}{$混凝土构件存在[{轻微}{严重}]露筋并锈蚀现象}{$纵横墙无可靠拉接}]。', '[(；\n    ){$将原粉刷铲除在两侧铺设钢丝网片后用水泥砂浆重新粉刷}{$对[(、){墙体}{楼面板}]重新进行防水处理}{$对钢筋进行除锈后用高标号水泥砂浆粉刷}{$对无可靠拉接的纵横墙节点进行加固}]。');
INSERT INTO `type` VALUES (3, '底框结构', '该房屋无正规设计图纸，无正规施工单位和监理单位，设计和施工资料缺失。', '1）结构主体结构为底部框架上部砖混结构，基础为柱下独立基础；\n    2）框架部分以[(、){框架梁}{柱}{楼面板}]承重，砖混部分以[(、){框架梁}{承重墙}{楼面板}]承重，楼面板为现浇钢筋混凝土板。', '[{1）基础工作基本正常，地基基础无明显不均匀沉降；}{1）基础工作不正常，地基基础轻微沉降；}{1）基础工作不正常，地基基础严重沉降；}]\n    2）上部承重构件工作基本正常，结构整体保持稳定，局部存在的质量缺陷有：\n        [(；\n        ){$[(、){框架部分填充墙}{砖混部分承重墙}]存在[{轻微}{严重}]开裂现象}{$[(、){墙体}{楼面板}]存在[{轻微}{严重}]渗水现象}{$混凝土构件存在[{轻微}{严重}]露筋并锈蚀现象}{$混凝土构件存在开裂现象}]。', '[(；\n    ){$[(、){将原粉刷铲除后用水泥砂浆重新粉刷}{将原粉刷铲除在两侧铺设钢丝网片后用水泥砂浆重新粉刷}]}{$对[(、){墙体}{楼面板}]重新进行防水处理}{$对钢筋进行除锈后用高标号水泥砂浆粉刷}{$清理干净用环氧树脂对裂缝进行封闭后在构件表面粘贴碳纤维布进行加固}]。');
INSERT INTO `type` VALUES (4, '框架结构', '该房屋无正规设计图纸，无正规施工单位和监理单位，设计和施工资料缺失。', '1）结构主体结构为钢筋混凝土框架结构，框架柱下为钢筋混凝土独立基础；\n    2）房屋墙体为砖砌非承重填充墙，房屋主要以[(、){框架梁}{柱}{楼面板}]承重，楼面板为现浇钢筋混凝土板。', '[{1）基础工作基本正常，地基基础无明显不均匀沉降；}{1）基础工作不正常，地基基础轻微沉降；}{1）基础工作不正常，地基基础严重沉降；}]\n    2）上部承重构件工作基本正常，结构整体保持稳定，局部存在的质量缺陷有：\n        [(；\n        ){$墙体存在[{轻微}{严重}]开裂现象}{$[(、){墙体}{屋面板}]存在[{轻微}{严重}]渗水现象}{$混凝土构件存在[{轻微}{严重}]露筋并锈蚀现象}{$混凝土构件存在开裂现象}]。', '[(；\n    ){$将原粉刷铲除后用水泥砂浆重新粉刷}{$对[(、){墙体}{楼面板}]重新进行防水处理}{$对钢筋进行除锈后用高标号水泥砂浆粉刷}{$清理干净用环氧树脂对裂缝进行封闭后在构件表面粘贴碳纤维布进行加固}]。');
INSERT INTO `type` VALUES (5, '木结构', '该房屋无正规设计图纸，无正规施工单位和监理单位，设计和施工资料缺失。', '1）建筑物上部主体结构为砖木结构，木柱下为石柱脚[{，房屋四面均为木墙}]；\n    2）房屋主要以[(、){木柱}{木屋架}{木檩条}]承重，楼面为[{木梁和木楼板}{木梁无楼板}]，屋面为木檩条挂小青瓦。', '[{1）基础工作基本正常，地基基础无明显不均匀沉降；}{1）基础工作不正常，地基基础轻微沉降；}{1）基础工作不正常，地基基础严重沉降；}]\n    2）上部承重构件工作基本正常，结构整体保持稳定，局部存在的质量缺陷有：\n        [(；\n        ){$[(、){木梁}{木柱}{木屋架}{木檩条}]存在[{轻微}{严重}]腐蚀现象}{$[(、){木梁}{木柱}{木屋架}{木檩条}]存在[{轻微}{严重}]开裂现象}{$柱脚有[{轻微}{严重}][(、){变形}{倾斜}{破损}]现象}{$屋面板存在[{轻微}{严重}][(、){破损}{渗水}{变形}]现象}]。', '[(；\n    ){$对腐蚀的木质构件进行防腐处理，处理方法可以涂刷木质清漆}{$对出现开裂的木质构件进行加固}{$对问题柱脚进行加固修复}{$[(、){对破损屋面视严重程度进行更换或加固}{对渗水墙面重新进行防水处理}{对变形屋面视严重程度进行更换或加固}]}]。');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `pwdstatus` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码状态',
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '盐',
  `token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '令牌',
  `role` int(11) NOT NULL DEFAULT 0 COMMENT '权限',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮件地址',
  `device` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备号',
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (2, 'admin', 'e2657513976719c2b57e7836e0c74b348d30635f8561d6c962b935c0cc1396b8', NULL, '5c9a4009958750c1c1999b293b2310a2', 'e1615ada31f3867cc4c956014d7e7decaf10c2494b32333be00fabf951ebf64d3b31353634383032323038363739', 10, NULL, NULL, 0);
INSERT INTO `user` VALUES (3, 'myluo', 'ac461aeb630e16cc54decee122c9113ff0c779d257e6d39f87c5ffa5d1fe0c4e', NULL, '932445cb165ae89febe09b085962d852', 'b061e7cf4d876742f27fce47510ccc5c0378e6e19ae4e32df46df4cdd03e6a263b31353636303435343137363331', 10, 'lmy0217@126.com', NULL, 0);
INSERT INTO `user` VALUES (4, 'test', 'f1e68b18c93390b23051087bdc922d37f192c361b2cc474e33290cc364315939', NULL, 'f1c99abd6ac9991a0994bf2d93a10d79', '03b5c8bea3c6283116c37eee71e386a6cde31031aa866e65e3377dbb4b5e91aa3b31353635393535323037333236', 10, 'rorwey@163.com', NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
