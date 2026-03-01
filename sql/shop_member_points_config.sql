-- 会员等级表
DROP TABLE IF EXISTS `shop_member_level`;
CREATE TABLE `shop_member_level` (
  `level_id` bigint NOT NULL AUTO_INCREMENT COMMENT '等级ID',
  `level_name` varchar(50) NOT NULL COMMENT '等级名称',
  `level_code` varchar(20) NOT NULL COMMENT '等级编码',
  `min_points` int NOT NULL DEFAULT '0' COMMENT '所需最低积分',
  `max_points` int DEFAULT NULL COMMENT '等级积分上限（NULL表示无上限）',
  `discount` decimal(5,2) DEFAULT '100.00' COMMENT '折扣力度（如98表示98折）',
  `free_shipping` tinyint(1) DEFAULT '0' COMMENT '是否免运费（0不免费 1免运费）',
  `free_shipping_amount` decimal(12,2) DEFAULT '0.00' COMMENT '免运费最低金额',
  `priority` int DEFAULT '0' COMMENT '优先级（数字越小优先级越高）',
  `icon` varchar(255) DEFAULT NULL COMMENT '等级图标URL',
  `background_color` varchar(20) DEFAULT NULL COMMENT '背景颜色',
  `benefits` varchar(1000) DEFAULT NULL COMMENT '等级权益描述（JSON格式）',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`level_id`),
  UNIQUE KEY `uk_level_code` (`level_code`),
  KEY `idx_min_points` (`min_points`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='会员等级表';

-- 积分规则表
DROP TABLE IF EXISTS `shop_points_rule`;
CREATE TABLE `shop_points_rule` (
  `rule_id` bigint NOT NULL AUTO_INCREMENT COMMENT '规则ID',
  `rule_name` varchar(100) NOT NULL COMMENT '规则名称',
  `rule_type` varchar(20) NOT NULL COMMENT '规则类型（consume消费行为 action行为活动 exchange兑换 deduction抵扣）',
  `rule_code` varchar(50) NOT NULL COMMENT '规则编码',
  `points_type` varchar(20) DEFAULT NULL COMMENT '积分类型（earn获得 spend消费）',
  `points_value` int NOT NULL DEFAULT '0' COMMENT '积分值',
  `rule_condition` varchar(500) DEFAULT NULL COMMENT '规则条件（JSON格式）',
  `max_points_per_day` int DEFAULT NULL COMMENT '每日最大积分获取限制',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`rule_id`),
  UNIQUE KEY `uk_rule_code` (`rule_code`),
  KEY `idx_rule_type` (`rule_type`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='积分规则表';

-- 商城系统参数配置表
DROP TABLE IF EXISTS `shop_config`;
CREATE TABLE `shop_config` (
  `config_id` bigint NOT NULL AUTO_INCREMENT COMMENT '参数ID',
  `config_name` varchar(100) NOT NULL COMMENT '参数名称',
  `config_key` varchar(100) NOT NULL COMMENT '参数键名',
  `config_value` varchar(500) DEFAULT NULL COMMENT '参数键值',
  `config_type` varchar(20) DEFAULT 'string' COMMENT '参数类型（string字符串 number数字 boolean布尔）',
  `group_name` varchar(50) DEFAULT NULL COMMENT '分组名称（points积分 member会员 order订单 payment支付 other其他）',
  `description` varchar(500) DEFAULT NULL COMMENT '参数描述',
  `is_frontend` tinyint(1) DEFAULT '0' COMMENT '是否前端可配置（0否 1是）',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`config_id`),
  UNIQUE KEY `uk_config_key` (`config_key`),
  KEY `idx_group_name` (`group_name`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商城系统参数配置表';

-- 用户积分明细表
DROP TABLE IF EXISTS `shop_user_points`;
CREATE TABLE `shop_user_points` (
  `points_id` bigint NOT NULL AUTO_INCREMENT COMMENT '积分ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `points_type` varchar(20) NOT NULL COMMENT '积分类型（earn获得 spend消耗 refund退还）',
  `points_value` int NOT NULL COMMENT '积分变化值（正数为获得，负数为消耗）',
  `balance` int NOT NULL COMMENT '变化后余额',
  `source_type` varchar(50) DEFAULT NULL COMMENT '来源类型（order订单 sign签到 activity活动 register注册 exchange兑换 refund退款）',
  `source_id` bigint DEFAULT NULL COMMENT '来源ID（如订单ID）',
  `source_desc` varchar(255) DEFAULT NULL COMMENT '来源描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`points_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_points_type` (`points_type`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户积分明细表';

-- 插入默认会员等级
INSERT INTO `shop_member_level`
(`level_name`, `level_code`, `min_points`, `max_points`, `discount`, `free_shipping`, `free_shipping_amount`, `priority`, `benefits`, `status`)
VALUES
('普通会员', 'normal', 0, 999, 100.00, 0, 0.00, 1, '基础会员权益', '0'),
('铜牌会员', 'bronze', 1000, 4999, 98.00, 0, 99.00, 2, '全场98折，满99元免运费', '0'),
('银牌会员', 'silver', 5000, 19999, 95.00, 0, 88.00, 3, '全场95折，满88元免运费，生日礼券', '0'),
('金牌会员', 'gold', 20000, 49999, 90.00, 1, 0.00, 4, '全场90折，免运费，专属客服，生日礼券', '0'),
('钻石会员', 'diamond', 50000, NULL, 85.00, 1, 0.00, 5, '全场85折，免运费，专属客服，生日礼券，优先发货', '0');

-- 插入默认积分规则
INSERT INTO `shop_points_rule`
(`rule_name`, `rule_type`, `rule_code`, `points_type`, `points_value`, `rule_condition`, `max_points_per_day`, `status`, `remark`)
VALUES
('购物获得积分', 'consume', 'order_earn', 'earn', 1, '{"ratio": 1, "amountPerPoint": 1}', NULL, '0', '消费1元获得1积分'),
('每日签到积分', 'action', 'daily_sign', 'earn', 5, NULL, 5, '0', '每日签到获得5积分'),
('完善资料积分', 'action', 'complete_profile', 'earn', 10, NULL, NULL, '0', '首次完善资料获得10积分'),
('绑定手机积分', 'action', 'bind_phone', 'earn', 20, NULL, NULL, '0', '首次绑定手机获得20积分'),
('邀请好友积分', 'action', 'invite_friend', 'earn', 50, NULL, NULL, '0', '每邀请一个好友注册获得50积分'),
('评价订单积分', 'action', 'review_order', 'earn', 10, NULL, 50, '0', '评价订单获得10积分'),
('积分兑换抵扣', 'deduction', 'points_deduction', 'spend', -100, '{"ratio": 100, "pointsPerYuan": 100}', NULL, '0', '100积分抵扣1元'),
('积分兑换优惠券', 'exchange', 'exchange_coupon', 'spend', -500, NULL, NULL, '0', '500积分兑换优惠券');

-- 插入默认系统参数
INSERT INTO `shop_config`
(`config_name`, `config_key`, `config_value`, `config_type`, `group_name`, `description`, `is_frontend`, `status`)
VALUES
('消费积分比例', 'points.consume.ratio', '1', 'number', 'points', '消费1元获得的积分数', 0, '0'),
('积分抵扣比例', 'points.deduction.ratio', '100', 'number', 'points', '100积分抵扣的金额（元）', 0, '0'),
('积分抵扣上限比例', 'points.deduction.maxRatio', '50', 'number', 'points', '订单金额最多可用积分抵扣的比例（%）', 0, '0'),
('每日签到积分', 'points.daily.sign', '5', 'number', 'points', '每日签到获得的积分数', 0, '0'),
('新用户注册积分', 'points.register.reward', '100', 'number', 'points', '新用户注册赠送积分数', 0, '0'),
('会员自动升级', 'member.autoUpgrade', 'true', 'boolean', 'member', '是否根据积分自动升级会员等级', 0, '0'),
('默认运费', 'order.default.freight', '10.00', 'number', 'order', '默认运费（元）', 0, '0'),
('免运费金额', 'order.freeShipping.amount', '99.00', 'number', 'order', '订单满多少元免运费', 1, '0'),
('订单完成时间限制', 'order.auto.complete.days', '15', 'number', 'order', '发货后多少天自动确认收货', 0, '0'),
('订单可取消时间', 'order.cancel.hours', '24', 'number', 'order', '订单发货后多少小时内可取消', 0, '0'),
('库存预警数量', 'product.stock.warning', '10', 'number', 'other', '商品库存预警数量', 0, '0');
