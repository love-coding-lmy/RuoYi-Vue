-- 优惠券表
DROP TABLE IF EXISTS `shop_coupon`;
CREATE TABLE `shop_coupon` (
  `coupon_id` bigint NOT NULL AUTO_INCREMENT COMMENT '优惠券ID',
  `coupon_name` varchar(100) NOT NULL COMMENT '优惠券名称',
  `coupon_type` varchar(20) NOT NULL COMMENT '优惠券类型（reduction满减 discount折扣 gift满赠 shipping运费）',
  `coupon_condition` varchar(500) DEFAULT NULL COMMENT '使用条件描述',
  `min_amount` decimal(12,2) DEFAULT '0.00' COMMENT '最低消费金额',
  `reduce_amount` decimal(12,2) DEFAULT '0.00' COMMENT '减免金额（满减券专用）',
  `discount` decimal(5,2) DEFAULT '0.00' COMMENT '折扣力度（折扣券专用，如8.5折）',
  `gift_product_id` bigint DEFAULT NULL COMMENT '赠品ID（满赠券专用）',
  `free_shipping` tinyint(1) DEFAULT '0' COMMENT '是否免运费（运费券专用）',
  `total_count` int NOT NULL DEFAULT '0' COMMENT '发行总量',
  `receive_count` int DEFAULT '0' COMMENT '已领取数量',
  `use_count` int DEFAULT '0' COMMENT '已使用数量',
  `per_user_limit` int DEFAULT '1' COMMENT '每人限领数量',
  `valid_type` varchar(20) NOT NULL DEFAULT 'time' COMMENT '有效期类型（time按时间 days按天数）',
  `valid_days` int DEFAULT NULL COMMENT '领取后有效天数（按天数类型专用）',
  `start_time` datetime DEFAULT NULL COMMENT '有效期开始时间（按时间类型专用）',
  `end_time` datetime DEFAULT NULL COMMENT '有效期结束时间（按时间类型专用）',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`coupon_id`),
  KEY `idx_coupon_type` (`coupon_type`),
  KEY `idx_status` (`status`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_end_time` (`end_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='优惠券表';

-- 用户优惠券关联表
DROP TABLE IF EXISTS `shop_user_coupon`;
CREATE TABLE `shop_user_coupon` (
  `user_coupon_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户优惠券ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `coupon_id` bigint NOT NULL COMMENT '优惠券ID',
  `coupon_name` varchar(100) NOT NULL COMMENT '优惠券名称',
  `coupon_type` varchar(20) NOT NULL COMMENT '优惠券类型',
  `reduce_amount` decimal(12,2) DEFAULT '0.00' COMMENT '减免金额',
  `discount` decimal(5,2) DEFAULT '0.00' COMMENT '折扣力度',
  `status` varchar(20) NOT NULL DEFAULT 'unused' COMMENT '状态（unused未使用 used已使用 expired已过期）',
  `use_time` datetime DEFAULT NULL COMMENT '使用时间',
  `order_id` bigint DEFAULT NULL COMMENT '使用的订单ID',
  `start_time` datetime DEFAULT NULL COMMENT '有效期开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '有效期结束时间',
  `receive_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '领取时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`user_coupon_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_coupon_id` (`coupon_id`),
  KEY `idx_status` (`status`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户优惠券关联表';

-- 插入测试数据
INSERT INTO `shop_coupon`
(`coupon_name`, `coupon_type`, `coupon_condition`, `min_amount`, `reduce_amount`, `discount`, `total_count`, `per_user_limit`, `valid_type`, `valid_days`, `start_time`, `end_time`, `status`, `remark`)
VALUES
('新用户专享券', 'reduction', '新用户注册专享，满100减20', 100.00, 20.00, 0.00, 1000, 1, 'days', 30, NULL, NULL, '0', '新用户注册后30天内有效'),
('8折优惠券', 'discount', '全场通用8折优惠', 50.00, 0.00, 8.00, 500, 2, 'time', NULL, '2026-01-01 00:00:00', '2026-12-31 23:59:59', '0', '2026年全年有效'),
('免运费券', 'shipping', '订单满99元免运费', 99.00, 0.00, 0.00, 2000, 5, 'days', 15, NULL, NULL, '0', '领取后15天内有效');
