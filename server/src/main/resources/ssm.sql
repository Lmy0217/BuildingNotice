/*
 Navicat Premium Data Transfer

 Source Server         : 49.234.178.101_3306
 Source Server Type    : MariaDB
 Source Server Version : 100213
 Source Host           : 49.234.178.101:3306
 Source Schema         : ssm

 Target Server Type    : MariaDB
 Target Server Version : 100213
 File Encoding         : 65001

 Date: 16/07/2019 22:58:07
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
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '结构类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES (1, '砖混结构', NULL, '1）建筑物上部主体结构为砖混结构，砖墙下为砖砌条形基础；\r\n2）房屋主要以砖墙和楼（屋）面板承重，楼（屋）面板为现浇钢筋混凝土板。', '1）基础工作基本正常，地基基础无明显不均匀沉降；\r\n2）上部承重构件工作基本正常，结构整体保持稳定，局部存在的质量缺陷有：', NULL);
INSERT INTO `type` VALUES (2, '砖木结构', '{该房屋无正规设计图纸，无正规施工单位和监理单位，设计和施工资料缺失。}', '{{1）建筑物上部主体结构为砖木结构，木柱下为石柱脚，砖墙下为砖砌条形基础；}\r\n{2）房屋主要以}[(、){砖墙}{木柱}{木屋架}{木檩条}]{承重，楼面为}[{木梁和木楼板}{木梁无楼板}]{，屋面为}[{木檩条挂小青瓦}]{。}}', '{{1）基础工作基本正常，地基基础无明显不均匀沉降；}\r\n{2）上部承重构件工作基本正常，结构整体保持稳定，局部存在的质量缺陷有：}\r\n[(；){[(、){木梁}{木柱}{木屋架}{木檩条}]{存在}[{严重}{轻微}]{腐蚀现象}}\r\n{[(、){木梁}{木柱}{木屋架}{木檩条}]{存在}[{严重}{轻微}]{开裂现象}}\r\n{{柱脚有}[{严重}{轻微}][(、){变形}{倾斜}{破损}]{现象}}\r\n{{屋面板存在}[{严重}{轻微}][{破损}{渗水}]{现象}}]{。}}', '{{对该房屋发现的质量缺陷和问题进行加固处理，加固处理需由有资质的单位实施。}\r\n{    质量缺陷加固处理方法建议：}\r\n[(；){对腐蚀的木质构件进行防腐处理，处理方法可以涂刷木质清漆}\r\n{对出现开裂的木质构件进行加固}\r\n{对问题柱脚进行加固修复}\r\n{[{对破损屋面进行修缮}{对渗水墙面重新进行防水处理}]}]{。}}');
INSERT INTO `type` VALUES (3, '底框结构', NULL, '结构主体结构为底部框架上部砖混结构，基础为柱下独立基础，框架部分以框架梁、柱和楼面板承重，砖混部分以框架梁、承重墙和楼(屋)面板承重，楼(屋)面板为现浇钢筋混凝土板。', '1）基础工作基本正常，地基基础无明显不均匀沉降；\r\n2）上部承重构件工作基本正常，结构整体保持稳定，局部存在的质量缺陷有：', NULL);
INSERT INTO `type` VALUES (4, '框架结构', NULL, '结构主体结构为钢筋混凝土框架结构，框架柱下为钢筋混凝土独立基础，房屋墙体为砖砌非承重填充墙，房屋主要以框架梁、柱和楼(屋)面板承重，楼(屋)面板为现浇钢筋混凝土板。', '1）基础工作基本正常，地基基础无明显不均匀沉降；\r\n2）上部承重构件工作基本正常，结构整体保持稳定，局部存在的质量缺陷有：', NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '盐',
  `token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '令牌',
  `role` int(11) NOT NULL DEFAULT 0 COMMENT '权限',
  `device` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备号',
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (2, 'admin', '0c8b0c323913c23858abd25d518c5646bc0ee5ae856a049665dd89db362a8c7c', 'a456a72721b7c2418072602dd7b97281', '4c433af7df0fbb5d524fe5c79d743e66cfe357c568ec9d90edd518eb89aa52f23b31353633353337313536383133', 10, NULL, 0);
INSERT INTO `user` VALUES (3, 'myluo', '57b2db9ec2d13f97370c69e15afc28e7ea505862d8b3c28835811b7f1dda473c', '83c0f415ade99519294b1c936013db8b', '79fa22988bee4b8dbe7e94e345b68805c36b7cfc4ca0628cdf0bb6bed9765c6f3b31353633353339393235393034', 10, NULL, 0);
INSERT INTO `user` VALUES (4, 'test', 'db095bc25ba72622945b9e697884e313b3d49ee61ee9d8cee3d738015630b0f7', 'cafa959f4b0a579dcd92374ab07ab7b2', NULL, 10, NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
