-- ----------------------------
-- Table structure for sms_coupon
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon`;
CREATE TABLE `sms_coupon`  (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT,
                               `type` int(1) NULL DEFAULT NULL COMMENT '优惠卷类型；0->全场赠券；1->会员赠券；2->购物赠券；3->注册赠券',
                               `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `platform` int(1) NULL DEFAULT NULL COMMENT '使用平台：0->全部；1->移动；2->PC',
                               `count` int(11) NULL DEFAULT NULL COMMENT '数量',
                               `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '金额',
                               `per_limit` int(11) NULL DEFAULT NULL COMMENT '每人限领张数',
                               `min_point` decimal(10, 2) NULL DEFAULT NULL COMMENT '使用门槛；0表示无门槛',
                               `start_time` datetime(0) NULL DEFAULT NULL,
                               `end_time` datetime(0) NULL DEFAULT NULL,
                               `use_type` int(1) NULL DEFAULT NULL COMMENT '使用类型：0->全场通用；1->指定分类；2->指定商品',
                               `note` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
                               `publish_count` int(11) NULL DEFAULT NULL COMMENT '发行数量',
                               `use_count` int(11) NULL DEFAULT NULL COMMENT '已使用数量',
                               `receive_count` int(11) NULL DEFAULT NULL COMMENT '领取数量',
                               `enable_time` datetime(0) NULL DEFAULT NULL COMMENT '可以领取的日期',
                               `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '优惠码',
                               `member_level` int(1) NULL DEFAULT NULL COMMENT '可领取的会员类型：0->无限时',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '优惠卷表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sms_coupon
-- ----------------------------
INSERT INTO `sms_coupon` VALUES (2, 0, '全品类通用券', 0, 92, 10.00, 1, 100.00, '2018-08-27 16:40:47', '2018-11-23 16:40:47', 0, '满100减10', 100, 0, 8, '2018-08-27 16:40:47', NULL, NULL);
INSERT INTO `sms_coupon` VALUES (3, 0, '小米手机专用券', 0, 92, 50.00, 1, 1000.00, '2018-08-27 16:40:47', '2018-11-16 16:40:47', 2, '小米手机专用优惠券', 100, 0, 8, '2018-08-27 16:40:47', NULL, NULL);
INSERT INTO `sms_coupon` VALUES (4, 0, '手机品类专用券', 0, 92, 300.00, 1, 2000.00, '2018-08-27 16:40:47', '2018-09-15 16:40:47', 1, '手机分类专用优惠券', 100, 0, 8, '2018-08-27 16:40:47', NULL, NULL);
INSERT INTO `sms_coupon` VALUES (7, 0, 'T恤分类专用优惠券', 0, 93, 50.00, 1, 500.00, '2018-08-27 16:40:47', '2018-08-15 16:40:47', 1, '满500减50', 100, 0, 7, '2018-08-27 16:40:47', NULL, NULL);
INSERT INTO `sms_coupon` VALUES (8, 0, '新优惠券', 0, 100, 100.00, 1, 1000.00, '2018-11-08 00:00:00', '2018-11-27 00:00:00', 0, '测试', 100, 0, 1, NULL, NULL, NULL);
INSERT INTO `sms_coupon` VALUES (9, 0, '全品类通用券', 0, 100, 5.00, 1, 100.00, '2018-11-08 00:00:00', '2018-11-10 00:00:00', 0, NULL, 100, 0, 1, NULL, NULL, NULL);
INSERT INTO `sms_coupon` VALUES (10, 0, '全品类通用券', 0, 100, 15.00, 1, 200.00, '2018-11-08 00:00:00', '2018-11-10 00:00:00', 0, NULL, 100, 0, 1, NULL, NULL, NULL);
INSERT INTO `sms_coupon` VALUES (11, 0, '全品类通用券', 0, 1000, 50.00, 1, 1000.00, '2018-11-08 00:00:00', '2018-11-10 00:00:00', 0, NULL, 1000, 0, 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon` VALUES (12, 0, '移动端全品类通用券', 1, 1, 10.00, 1, 100.00, '2018-11-08 00:00:00', '2018-11-10 00:00:00', 0, NULL, 100, 0, 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon` VALUES (22, 0, '华为手机专用', 0, 999, 999.00, 1, 2000.00, '2020-03-24 16:00:00', '2020-12-30 16:00:00', 2, NULL, 1000, 0, 1, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sms_coupon_history
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon_history`;
CREATE TABLE `sms_coupon_history`  (
                                       `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                       `coupon_id` bigint(20) NULL DEFAULT NULL,
                                       `member_id` bigint(20) NULL DEFAULT NULL,
                                       `coupon_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                       `member_nickname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '领取人昵称',
                                       `get_type` int(1) NULL DEFAULT NULL COMMENT '获取类型：0->后台赠送；1->主动获取',
                                       `create_time` datetime(0) NULL DEFAULT NULL,
                                       `use_status` int(1) NULL DEFAULT NULL COMMENT '使用状态：0->未使用；1->已使用；2->已过期',
                                       `use_time` datetime(0) NULL DEFAULT NULL COMMENT '使用时间',
                                       `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单编号',
                                       `order_sn` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单号码',
                                       PRIMARY KEY (`id`) USING BTREE,
                                       INDEX `idx_member_id`(`member_id`) USING BTREE,
                                       INDEX `idx_coupon_id`(`coupon_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '优惠券使用、领取历史表' ROW_FORMAT = DYNAMIC;


-- ----------------------------
-- Table structure for sms_coupon_product_category_relation
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon_product_category_relation`;
CREATE TABLE `sms_coupon_product_category_relation`  (
                                                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                                         `coupon_id` bigint(20) NULL DEFAULT NULL,
                                                         `product_category_id` bigint(20) NULL DEFAULT NULL,
                                                         `product_category_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品分类名称',
                                                         `parent_category_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父分类名称',
                                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '优惠券和产品分类关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sms_coupon_product_category_relation
-- ----------------------------

-- ----------------------------
-- Table structure for sms_coupon_product_relation
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon_product_relation`;
CREATE TABLE `sms_coupon_product_relation`  (
                                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                                `coupon_id` bigint(20) NULL DEFAULT NULL,
                                                `product_id` bigint(20) NULL DEFAULT NULL,
                                                `product_name` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
                                                `product_sn` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品编码',
                                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '优惠券和产品的关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sms_coupon_product_relation
-- ----------------------------
INSERT INTO `sms_coupon_product_relation` VALUES (13, 22, 38, '华为nova6se 手机 绮境森林 全网通（8G+128G)', '');

-- ----------------------------
-- Table structure for sms_flash_promotion
-- ----------------------------
DROP TABLE IF EXISTS `sms_flash_promotion`;
CREATE TABLE `sms_flash_promotion`  (
                                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                        `title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动名称',
                                        `start_date` date NULL DEFAULT NULL COMMENT '开始日期',
                                        `end_date` date NULL DEFAULT NULL COMMENT '结束日期',
                                        `status` int(1) NULL DEFAULT NULL COMMENT '上下线状态,1上线、0下线',
                                        `create_time` datetime(0) NULL DEFAULT NULL COMMENT '秒杀时间段名称',
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '限时购表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sms_flash_promotion
-- ----------------------------
INSERT INTO `sms_flash_promotion` VALUES (3, '秒杀专场', '2020-02-15', '2021-06-10', 1, '2018-11-16 11:11:31');
INSERT INTO `sms_flash_promotion` VALUES (7, '春季家电家具疯狂秒杀6', '2021-11-04', '2021-11-25', 0, '2018-11-16 11:12:35');

-- ----------------------------
-- Table structure for sms_flash_promotion_log
-- ----------------------------
DROP TABLE IF EXISTS `sms_flash_promotion_log`;
CREATE TABLE `sms_flash_promotion_log`  (
                                            `id` int(11) NOT NULL AUTO_INCREMENT,
                                            `member_id` int(11) NULL DEFAULT NULL,
                                            `product_id` bigint(20) NULL DEFAULT NULL,
                                            `member_phone` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                            `product_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                            `subscribe_time` datetime(0) NULL DEFAULT NULL COMMENT '会员订阅时间',
                                            `send_time` datetime(0) NULL DEFAULT NULL,
                                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '限时购通知记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sms_flash_promotion_log
-- ----------------------------

-- ----------------------------
-- Table structure for sms_flash_promotion_product_relation
-- ----------------------------
DROP TABLE IF EXISTS `sms_flash_promotion_product_relation`;
CREATE TABLE `sms_flash_promotion_product_relation`  (
                                                         `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
                                                         `flash_promotion_id` bigint(20) NULL DEFAULT NULL COMMENT '秒杀活动ID->关联sms_flash_promotion表',
                                                         `flash_promotion_session_id` bigint(20) NULL DEFAULT NULL COMMENT '当前日期活动场次编号',
                                                         `product_id` bigint(20) NULL DEFAULT NULL COMMENT '产品ID',
                                                         `flash_promotion_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '限时购价格',
                                                         `flash_promotion_count` int(11) NULL DEFAULT NULL COMMENT '限时购数量',
                                                         `flash_promotion_limit` int(11) NULL DEFAULT NULL COMMENT '每人限购数量',
                                                         `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
                                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品限时购与商品关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sms_flash_promotion_product_relation
-- ----------------------------
INSERT INTO `sms_flash_promotion_product_relation` VALUES (26, 3, 2, 32, 60.00, 10, 1, NULL);
INSERT INTO `sms_flash_promotion_product_relation` VALUES (40, 3, 5, 29, 4999.00, 10, 1, 100);
INSERT INTO `sms_flash_promotion_product_relation` VALUES (41, 3, 1, 42, 499.00, 100, 1, 1);
INSERT INTO `sms_flash_promotion_product_relation` VALUES (42, 3, 3, 26, 3700.00, 123, 1, NULL);
INSERT INTO `sms_flash_promotion_product_relation` VALUES (43, 3, 4, 26, 3699.00, 89, 11, 1);

-- ----------------------------
-- Table structure for sms_flash_promotion_session
-- ----------------------------
DROP TABLE IF EXISTS `sms_flash_promotion_session`;
CREATE TABLE `sms_flash_promotion_session`  (
                                                `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
                                                `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '场次名称',
                                                `start_time` time(0) NULL DEFAULT NULL COMMENT '每日开始时间',
                                                `end_time` time(0) NULL DEFAULT NULL COMMENT '每日结束时间',
                                                `status` int(1) NULL DEFAULT NULL COMMENT '启用状态：0->不启用；1->启用',
                                                `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '限时购场次表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sms_flash_promotion_session
-- ----------------------------
INSERT INTO `sms_flash_promotion_session` VALUES (1, '16:00场', '08:00:00', '10:00:33', 1, '2018-11-16 13:22:17');
INSERT INTO `sms_flash_promotion_session` VALUES (2, '18:00场', '10:00:00', '12:00:00', 1, '2018-11-16 13:22:34');
INSERT INTO `sms_flash_promotion_session` VALUES (3, '20:00场', '12:00:00', '14:00:00', 1, '2018-11-16 13:22:37');
INSERT INTO `sms_flash_promotion_session` VALUES (4, '10:00场', '02:00:00', '04:00:00', 1, '2018-11-16 13:22:41');
INSERT INTO `sms_flash_promotion_session` VALUES (5, '13:00场', '05:00:00', '07:00:00', 1, '2018-11-16 13:22:45');
INSERT INTO `sms_flash_promotion_session` VALUES (6, '18:00', '18:00:00', '20:00:00', 0, '2018-11-16 13:21:34');
INSERT INTO `sms_flash_promotion_session` VALUES (7, '20:00', '20:00:33', '21:00:33', 0, '2018-11-16 13:22:55');

