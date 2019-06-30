/*
 Navicat Premium Data Transfer

 Source Server         : agekt.com_3306
 Source Server Type    : MariaDB
 Source Server Version : 100213
 Source Host           : agekt.com:3306
 Source Schema         : ssm

 Target Server Type    : MariaDB
 Target Server Version : 100213
 File Encoding         : 65001

 Date: 30/06/2019 21:53:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for advise
-- ----------------------------
DROP TABLE IF EXISTS `advise`;
CREATE TABLE `advise`  (
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '质量缺陷',
  `desc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '方法建议',
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of advise
-- ----------------------------
INSERT INTO `advise` VALUES ('type1_21', '将原粉刷铲除在两侧铺设钢丝网片后用水泥砂浆重新粉刷');
INSERT INTO `advise` VALUES ('type1_22', '重新进行防水处理');
INSERT INTO `advise` VALUES ('type1_23', '对钢筋进行除锈后用高标号水泥砂浆粉刷');
INSERT INTO `advise` VALUES ('type2_31_B', '对腐蚀的木质构件进行防腐处理，处理方法可以涂刷木质清漆');
INSERT INTO `advise` VALUES ('type2_31_C', '对腐蚀的木质构件进行补强后做防腐处理，防腐处理可以涂刷木质清漆');
INSERT INTO `advise` VALUES ('type2_32_B', '对出现开裂的木质构件进行加固');
INSERT INTO `advise` VALUES ('type2_32_C', '对出现开裂的木质构件视严重程度进行更换或加固');
INSERT INTO `advise` VALUES ('type2_33_B', '对问题柱脚进行加固修复');
INSERT INTO `advise` VALUES ('type2_33_C', '对问题柱脚视严重程度进行更换或加固修复');
INSERT INTO `advise` VALUES ('type2_34_B', '对破损屋面进行修缮/对渗水墙面重新进行防水处理');
INSERT INTO `advise` VALUES ('type2_34_C', '对破损屋面视严重程度进行更换或修缮/对渗水屋面重新进行防水处理');
INSERT INTO `advise` VALUES ('type3_21', '将原粉刷铲除后用水泥砂浆重新粉刷/将原粉刷铲除在两侧铺设钢丝网片后用水泥砂浆重新粉刷');
INSERT INTO `advise` VALUES ('type3_22', '重新进行防水处理');
INSERT INTO `advise` VALUES ('type3_23', '对钢筋进行除锈后用高标号水泥砂浆粉刷');
INSERT INTO `advise` VALUES ('type3_24', '清理干净用环氧树脂对裂缝进行封闭后在构件表面粘贴碳纤维布进行加固');
INSERT INTO `advise` VALUES ('type4_21', '将原粉刷铲除后用水泥砂浆重新粉刷');
INSERT INTO `advise` VALUES ('type4_22', '重新进行防水处理');
INSERT INTO `advise` VALUES ('type4_23', '对钢筋进行除锈后用高标号水泥砂浆粉刷');
INSERT INTO `advise` VALUES ('type4_24', '清理干净用环氧树脂对裂缝进行封闭后在构件表面粘贴碳纤维布进行加固');

-- ----------------------------
-- Table structure for archimg
-- ----------------------------
DROP TABLE IF EXISTS `archimg`;
CREATE TABLE `archimg`  (
  `archid` int(11) NOT NULL COMMENT '报告id',
  `imgid` int(11) NOT NULL COMMENT '图片id',
  PRIMARY KEY (`archid`, `imgid`) USING BTREE,
  INDEX `imgid`(`imgid`) USING BTREE,
  CONSTRAINT `archimg_ibfk_1` FOREIGN KEY (`archid`) REFERENCES `archive` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `archimg_ibfk_2` FOREIGN KEY (`imgid`) REFERENCES `image` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '报告_图片' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for archive
-- ----------------------------
DROP TABLE IF EXISTS `archive`;
CREATE TABLE `archive`  (
  `id` int(11) NOT NULL COMMENT 'id',
  `unit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '委托单位（人）',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `material` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '提供资料情况',
  `addr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '房屋地址',
  `hold` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '户主名称',
  `holdid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '身份证号',
  `attr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '房屋属性',
  `layer` int(4) NOT NULL COMMENT '层数',
  `create` year NULL DEFAULT NULL COMMENT '建造时间',
  `typeid` int(11) NOT NULL COMMENT '结构类型id',
  `identitytime` timestamp(0) NOT NULL DEFAULT current_timestamp() ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '鉴定时间',
  `rankid` int(11) NOT NULL COMMENT '危险性等级id',
  `rankratio` double(2, 2) NOT NULL COMMENT '危险构件比例',
  `userid` int(11) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `typeid`(`typeid`) USING BTREE,
  INDEX `rankid`(`rankid`) USING BTREE,
  INDEX `userid`(`userid`) USING BTREE,
  CONSTRAINT `archive_ibfk_1` FOREIGN KEY (`typeid`) REFERENCES `type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `archive_ibfk_2` FOREIGN KEY (`rankid`) REFERENCES `rank` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `archive_ibfk_3` FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '报告' ROW_FORMAT = Dynamic;

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
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '图片' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rank
-- ----------------------------
DROP TABLE IF EXISTS `rank`;
CREATE TABLE `rank`  (
  `id` int(11) NOT NULL COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '危险性等级',
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '比例区间描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '危险性等级' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rank
-- ----------------------------
INSERT INTO `rank` VALUES (2, 'B', '大于0但未超过比例5%');
INSERT INTO `rank` VALUES (3, 'C', '大于5%但未超过比例25%');

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`  (
  `id` int(11) NOT NULL COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结构类型名',
  `body2` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主体结构概况',
  `body3` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结构现状描述',
  `tabel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '对应表名',
  PRIMARY KEY (`id`, `tabel`) USING BTREE,
  INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '结构类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES (1, '砖混结构', '1）建筑物上部主体结构为砖混结构，砖墙下为砖砌条形基础；\r\n2）房屋主要以砖墙和楼（屋）面板承重，楼（屋）面板为现浇钢筋混凝土板。', '1）基础工作基本正常，地基基础无明显不均匀沉降；\r\n2）上部承重构件工作基本正常，结构整体保持稳定，局部存在的质量缺陷有：', 'type1');
INSERT INTO `type` VALUES (2, '砖木结构', '建筑物上部主体结构为砖木结构，木柱下为石柱脚，砖墙下为砖砌条形基础；\r\n房屋主要以{}承重，楼面为{}，屋面为{}。', '基础和上部结构工作基本正常，基础无明显不均匀沉降，结构存在的质量缺陷有：', 'type2');
INSERT INTO `type` VALUES (3, '底框结构', '结构主体结构为底部框架上部砖混结构，基础为柱下独立基础，框架部分以框架梁、柱和楼面板承重，砖混部分以框架梁、承重墙和楼(屋)面板承重，楼(屋)面板为现浇钢筋混凝土板。', '1）基础工作基本正常，地基基础无明显不均匀沉降；\r\n2）上部承重构件工作基本正常，结构整体保持稳定，局部存在的质量缺陷有：', 'type3');
INSERT INTO `type` VALUES (4, '框架结构', '结构主体结构为钢筋混凝土框架结构，框架柱下为钢筋混凝土独立基础，房屋墙体为砖砌非承重填充墙，房屋主要以框架梁、柱和楼(屋)面板承重，楼(屋)面板为现浇钢筋混凝土板。', '1）基础工作基本正常，地基基础无明显不均匀沉降；\r\n2）上部承重构件工作基本正常，结构整体保持稳定，局部存在的质量缺陷有：', 'type4');

-- ----------------------------
-- Table structure for type1
-- ----------------------------
DROP TABLE IF EXISTS `type1`;
CREATE TABLE `type1`  (
  `archid` int(11) NOT NULL COMMENT '报告id',
  `a1` int(255) NULL DEFAULT NULL COMMENT '不均匀沉降（ab）\r\na：无（0）、存在（1）\r\nb：轻微（1）、明显（2）',
  `a21` int(255) NULL DEFAULT NULL COMMENT '开裂（ab）\r\na（墙体）：无（0）、有（1）\r\nb：轻微（1）、严重（2）',
  `a22` int(255) NULL DEFAULT NULL COMMENT '渗水（abc）\r\na（墙体）：无（0）、有（1）\r\nb（屋面板）：无（0）、有（1）\r\nc：轻微（1）、严重（2）',
  `a23` int(255) NULL DEFAULT NULL COMMENT '露筋并锈蚀（ab）\r\na（混凝土构件）：无（0）、有（1）\r\nb：轻微（1）、严重（2）',
  PRIMARY KEY (`archid`) USING BTREE,
  CONSTRAINT `type1_ibfk_1` FOREIGN KEY (`archid`) REFERENCES `archive` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '砖混结构' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for type2
-- ----------------------------
DROP TABLE IF EXISTS `type2`;
CREATE TABLE `type2`  (
  `archid` int(11) NOT NULL COMMENT '报告id',
  `a221` int(255) NULL DEFAULT NULL COMMENT '承重（abcd）\r\na（砖墙）：否（0）、是（1）\r\nb（木柱）：否（0）、是（1）\r\nc（木屋架）：否（0）、是（1）\r\nd（木檩条）：否（0）、是（1）',
  `a222` int(255) NULL DEFAULT NULL COMMENT '楼面（ab）\r\na（木梁）：无（0）、有（1）\r\nb（木楼板）：无（0）、有（1）',
  `a223` int(255) NULL DEFAULT NULL COMMENT '屋面（a）\r\na（木檩条挂小青瓦）：无（0）、有（1）',
  `a31` int(255) NULL DEFAULT NULL COMMENT '腐蚀（abcde）\r\na（木梁）：无（0）、有（1）\r\nb（木柱）：无（0）、有（1）\r\nc（木屋架）：无（0）、有（1）\r\nd（木檩条：无（0）、有（1）\r\ne：轻微（1）、严重（2）',
  `a32` int(255) NULL DEFAULT NULL COMMENT '开裂（abcde）\r\na（木梁）：无（0）、有（1）\r\nb（木柱）：无（0）、有（1）\r\nc（木屋架）：无（0）、有（1）\r\nd（木檩条：无（0）、有（1）\r\ne：轻微（1）、严重（2）',
  `a33` int(255) NULL DEFAULT NULL COMMENT '柱脚（abcd）\r\na：轻微（1）、严重（2）\r\nb（变形）：无（0）、有（1）\r\nc（倾斜）：无（0）、有（1）\r\nd（破损）：无（0）、有（1）',
  `a34` int(255) NULL DEFAULT NULL COMMENT '屋面板（abc）\r\na：轻微（1）、严重（2）\r\nb（破损）：无（0）、有（1）\r\nc（渗水）：无（0）、有（1）',
  PRIMARY KEY (`archid`) USING BTREE,
  CONSTRAINT `type2_ibfk_1` FOREIGN KEY (`archid`) REFERENCES `archive` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '砖木结构' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for type3
-- ----------------------------
DROP TABLE IF EXISTS `type3`;
CREATE TABLE `type3`  (
  `archid` int(11) NOT NULL COMMENT '报告id',
  `a21` int(255) NULL DEFAULT NULL COMMENT '开裂（abc）\r\na（框架部分填充墙）：无（0）、有（1）\r\nb（砖混部分承重墙）：无（0）、有（1）\r\nc：轻微（1）、严重（2）',
  `a22` int(255) NULL DEFAULT NULL COMMENT '渗水（abc）\r\na（墙体）：无（0）、有（1）\r\nb（屋面板）：无（0）、有（1）\r\nc：轻微（1）、严重（2）',
  `a23` int(255) NULL DEFAULT NULL COMMENT '露筋并锈蚀（ab）\r\na（混凝土构件）：无（0）、有（1）\r\nb：轻微（1）、严重（2）',
  `a24` int(255) NULL DEFAULT NULL COMMENT '混凝土构件开裂（a）\r\na（混凝土构件）：无（0）、有（1）',
  PRIMARY KEY (`archid`) USING BTREE,
  CONSTRAINT `type3_ibfk_1` FOREIGN KEY (`archid`) REFERENCES `archive` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '底框结构' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for type4
-- ----------------------------
DROP TABLE IF EXISTS `type4`;
CREATE TABLE `type4`  (
  `archid` int(11) NOT NULL COMMENT '报告id',
  `a21` int(255) NULL DEFAULT NULL COMMENT '开裂（ab）\r\na（墙体）：无（0）、有（1）\r\nb：轻微（1）、严重（2）',
  `a22` int(255) NULL DEFAULT NULL COMMENT '渗水（abc）\r\na（墙体）：无（0）、有（1）\r\nb（屋面板）：无（0）、有（1）\r\nc：轻微（1）、严重（2）',
  `a23` int(255) NULL DEFAULT NULL COMMENT '露筋并锈蚀（ab）\r\na（混凝土构件）：无（0）、有（1）\r\nb：轻微（1）、严重（2）',
  `a24` int(255) NULL DEFAULT NULL COMMENT '混凝土构件开裂（a）\r\na（混凝土构件）：无（0）、有（1）',
  PRIMARY KEY (`archid`) USING BTREE,
  CONSTRAINT `type4_ibfk_1` FOREIGN KEY (`archid`) REFERENCES `archive` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '框架结构' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '令牌',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'admin', NULL);

SET FOREIGN_KEY_CHECKS = 1;
