-- 零食系列表
DROP TABLE IF EXISTS `shop_snack_series`;
CREATE TABLE `shop_snack_series` (
  `series_id` bigint NOT NULL AUTO_INCREMENT COMMENT '系列ID',
  `series_name` varchar(100) NOT NULL COMMENT '系列名称',
  `series_code` varchar(50) NOT NULL COMMENT '系列编码',
  `series_image` varchar(255) DEFAULT NULL COMMENT '系列封面图',
  `series_desc` varchar(500) DEFAULT NULL COMMENT '系列描述',
  `sort_order` int DEFAULT '0' COMMENT '排序号',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`series_id`),
  UNIQUE KEY `uk_series_code` (`series_code`),
  KEY `idx_status` (`status`),
  KEY `idx_sort_order` (`sort_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='零食系列表';

-- 零食标签表
DROP TABLE IF EXISTS `shop_snack_tag`;
CREATE TABLE `shop_snack_tag` (
  `tag_id` bigint NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  `tag_name` varchar(50) NOT NULL COMMENT '标签名称',
  `tag_code` varchar(50) NOT NULL COMMENT '标签编码',
  `tag_type` varchar(20) NOT NULL COMMENT '标签类型（feature特性 promo促销 status状态 quality品质）',
  `tag_color` varchar(20) DEFAULT NULL COMMENT '标签颜色',
  `icon_url` varchar(255) DEFAULT NULL COMMENT '图标URL',
  `sort_order` int DEFAULT '0' COMMENT '排序号',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`tag_id`),
  UNIQUE KEY `uk_tag_code` (`tag_code`),
  KEY `idx_tag_type` (`tag_type`),
  KEY `idx_status` (`status`),
  KEY `idx_sort_order` (`sort_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='零食标签表';

-- 商品系列关联表
DROP TABLE IF EXISTS `shop_product_series`;
CREATE TABLE `shop_product_series` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `series_id` bigint NOT NULL COMMENT '系列ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_series_id` (`series_id`),
  KEY `idx_product_series` (`product_id`, `series_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品系列关联表';

-- 商品标签关联表
DROP TABLE IF EXISTS `shop_product_tag`;
CREATE TABLE `shop_product_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `tag_id` bigint NOT NULL COMMENT '标签ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_tag_id` (`tag_id`),
  KEY `idx_product_tag` (`product_id`, `tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品标签关联表';

-- 促销活动表
DROP TABLE IF EXISTS `shop_promotion`;
CREATE TABLE `shop_promotion` (
  `promotion_id` bigint NOT NULL AUTO_INCREMENT COMMENT '活动ID',
  `promotion_name` varchar(100) NOT NULL COMMENT '活动名称',
  `promotion_type` varchar(20) NOT NULL COMMENT '活动类型（seckill秒杀 discount满减 bundle捆绑 gift赠品）',
  `promotion_desc` varchar(500) DEFAULT NULL COMMENT '活动描述',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `min_amount` decimal(12,2) DEFAULT '0.00' COMMENT '最低参与金额',
  `discount_type` varchar(20) DEFAULT NULL COMMENT '优惠类型（amount金额 percent百分比）',
  `discount_value` decimal(12,2) DEFAULT '0.00' COMMENT '优惠值',
  `stock` int DEFAULT '0' COMMENT '库存数量',
  `sold_count` int DEFAULT '0' COMMENT '已售数量',
  `limit_per_user` int DEFAULT '0' COMMENT '每人限购数量（0不限购）',
  `priority` int DEFAULT '0' COMMENT '优先级（数字越小优先级越高）',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用 2已结束）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`promotion_id`),
  KEY `idx_promotion_type` (`promotion_type`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_end_time` (`end_time`),
  KEY `idx_status` (`status`),
  KEY `idx_priority` (`priority`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='促销活动表';

-- 促销商品关联表
DROP TABLE IF EXISTS `shop_promotion_product`;
CREATE TABLE `shop_promotion_product` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `promotion_id` bigint NOT NULL COMMENT '活动ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `product_name` varchar(255) DEFAULT NULL COMMENT '商品名称（快照）',
  `original_price` decimal(12,2) DEFAULT '0.00' COMMENT '原价',
  `promotion_price` decimal(12,2) DEFAULT '0.00' COMMENT '促销价',
  `stock` int DEFAULT '0' COMMENT '库存',
  `sort_order` int DEFAULT '0' COMMENT '排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_promotion_id` (`promotion_id`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_promotion_product` (`promotion_id`, `product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='促销商品关联表';

-- 推荐位表
DROP TABLE IF EXISTS `shop_recommend_position`;
CREATE TABLE `shop_recommend_position` (
  `position_id` bigint NOT NULL AUTO_INCREMENT COMMENT '推荐位ID',
  `position_name` varchar(100) NOT NULL COMMENT '推荐位名称',
  `position_code` varchar(50) NOT NULL COMMENT '推荐位编码',
  `position_type` varchar(20) NOT NULL COMMENT '位置类型（index首页 category分类页 detail详情页）',
  `max_items` int NOT NULL DEFAULT '10' COMMENT '最大商品数量',
  `width` int DEFAULT '0' COMMENT '宽度',
  `height` int DEFAULT '0' COMMENT '高度',
  `template_type` varchar(50) DEFAULT NULL COMMENT '模板类型（single swipe grid）',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`position_id`),
  UNIQUE KEY `uk_position_code` (`position_code`),
  KEY `idx_position_type` (`position_type`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='推荐位表';

-- 推荐商品关联表
DROP TABLE IF EXISTS `shop_recommend_item`;
CREATE TABLE `shop_recommend_item` (
  `item_id` bigint NOT NULL AUTO_INCREMENT COMMENT '推荐项ID',
  `position_id` bigint NOT NULL COMMENT '推荐位ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `product_name` varchar(255) DEFAULT NULL COMMENT '商品名称（快照）',
  `product_image` varchar(255) DEFAULT NULL COMMENT '商品图片',
  `sort_order` int DEFAULT '0' COMMENT '排序',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`item_id`),
  KEY `idx_position_id` (`position_id`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_position_product` (`position_id`, `product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='推荐商品关联表';

-- 插入默认零食系列
INSERT INTO `shop_snack_series`
(`series_name`, `series_code`, `series_image`, `series_desc`, `sort_order`, `status`)
VALUES
('坚果系列', 'nuts', '/uploads/snack/nuts.jpg', '各类坚果零食，营养丰富', 1, '0'),
('糖果系列', 'candy', '/uploads/snack/candy.jpg', '各种糖果，甜蜜美味', 2, '0'),
('膨化系列', 'puffed', '/uploads/snack/puffed.jpg', '膨化食品，酥脆可口', 3, '0'),
('饼干系列', 'biscuit', '/uploads/snack/biscuit.jpg', '饼干糕点，香脆美味', 4, '0'),
('进口零食', 'import', '/uploads/snack/import.jpg', '进口零食，品质保证', 5, '0'),
('健康零食', 'healthy', '/uploads/snack/healthy.jpg', '健康低卡，无负担享受', 6, '0');

-- 插入默认零食标签
INSERT INTO `shop_snack_tag`
(`tag_name`, `tag_code`, `tag_type`, `tag_color`, `sort_order`, `status`)
VALUES
('新品', 'new', 'status', '#67C23A', 1, '0'),
('热卖', 'hot', 'status', '#F56C6C', 2, '0'),
('限量', 'limited', 'status', '#E6A23C', 3, '0'),
('健康', 'healthy', 'quality', '#67C23A', 4, '0'),
('低卡', 'lowcal', 'quality', '#909399', 5, '0'),
('无糖', 'sugarfree', 'quality', '#409EFF', 6, '0'),
('进口', 'import', 'quality', '#E6A23C', 7, '0'),
('有机', 'organic', 'quality', '#67C23A', 8, '0'),
('网红', 'viral', 'feature', '#F56C6C', 9, '0'),
('经典', 'classic', 'feature', '#909399', 10, '0');

-- 插入默认推荐位
INSERT INTO `shop_recommend_position`
(`position_name`, `position_code`, `position_type`, `max_items`, `width`, `height`, `template_type`, `status`)
VALUES
('首页轮播', 'index_banner', 'index', 5, 1200, 400, 'swipe', '0'),
('首页推荐1', 'index_recommend1', 'index', 10, 0, 0, 'grid', '0'),
('首页推荐2', 'index_recommend2', 'index', 8, 0, 0, 'grid', '0'),
('分类页推荐', 'category_recommend', 'category', 20, 0, 0, 'grid', '0'),
('详情页推荐', 'detail_recommend', 'detail', 6, 0, 0, 'grid', '0');

-- 插入默认促销活动
INSERT INTO `shop_promotion`
(`promotion_name`, `promotion_type`, `promotion_desc`, `start_time`, `end_time`, `min_amount`, `discount_type`, `discount_value`, `stock`, `limit_per_user`, `priority`, `status`)
VALUES
('限时秒杀', 'seckill', '每日精选零食限时秒杀，手慢无！', '2026-01-01 00:00:00', '2026-12-31 23:59:59', 0.00, 'percent', 80.00, 100, 1, 1, '0'),
('买二送一', 'bundle', '精选零食买二送一，超值优惠！', '2026-01-01 00:00:00', '2026-12-31 23:59:59', 0.00, 'amount', 0.00, 1000, 10, 2, '0'),
('满99减20', 'discount', '零食满99元减20元，多买多惠！', '2026-01-01 00:00:00', '2026-12-31 23:59:59', 99.00, 'amount', 20.00, 0, 0, 3, '0'),
('新手礼包', 'gift', '新用户专享零食礼包，超值体验！', '2026-01-01 00:00:00', '2026-12-31 23:59:59', 0.00, 'amount', 0.00, 500, 1, 4, '0');

-- ==================== 菜单配置SQL ====================
-- 零食模块菜单 - 放在商城配置(parent_id: 2040)下
-- 参考现有菜单结构：积分规则(2041/points)、会员等级(2042/level)、系统参数(2043/system)

-- 1. 零食系列菜单 (path: series, 对应积分规则的points路径模式)
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
('零食系列', 2040, 14, 'series', 'system/shop/config/series/index', 1, 0, 'C', '0', '0', 'shop:series:list', 'tree-table', 'admin', sysdate(), '', NULL, '零食系列菜单');

-- 零食系列按钮权限
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES
('零食系列查询', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '零食系列' AND path = 'series' ORDER BY create_time DESC LIMIT 1) AS tmp), 1, '#', '', 1, 0, 'F', '0', '0', 'shop:series:query', '#', 'admin', sysdate(), ''),
('零食系列新增', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '零食系列' AND path = 'series' ORDER BY create_time DESC LIMIT 1) AS tmp), 2, '#', '', 1, 0, 'F', '0', '0', 'shop:series:add', '#', 'admin', sysdate(), ''),
('零食系列修改', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '零食系列' AND path = 'series' ORDER BY create_time DESC LIMIT 1) AS tmp), 3, '#', '', 1, 0, 'F', '0', '0', 'shop:series:edit', '#', 'admin', sysdate(), ''),
('零食系列删除', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '零食系列' AND path = 'series' ORDER BY create_time DESC LIMIT 1) AS tmp), 4, '#', '', 1, 0, 'F', '0', '0', 'shop:series:remove', '#', 'admin', sysdate(), ''),
('零食系列导出', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '零食系列' AND path = 'series' ORDER BY create_time DESC LIMIT 1) AS tmp), 5, '#', '', 1, 0, 'F', '0', '0', 'shop:series:export', '#', 'admin', sysdate(), '');

-- 2. 零食标签菜单 (path: tag)
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
('零食标签', 2040, 15, 'tag', 'system/shop/config/tag/index', 1, 0, 'C', '0', '0', 'shop:tag:list', 'tag', 'admin', sysdate(), '', NULL, '零食标签菜单');

-- 零食标签按钮权限
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES
('零食标签查询', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '零食标签' AND path = 'tag' ORDER BY create_time DESC LIMIT 1) AS tmp), 1, '#', '', 1, 0, 'F', '0', '0', 'shop:tag:query', '#', 'admin', sysdate(), ''),
('零食标签新增', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '零食标签' AND path = 'tag' ORDER BY create_time DESC LIMIT 1) AS tmp), 2, '#', '', 1, 0, 'F', '0', '0', 'shop:tag:add', '#', 'admin', sysdate(), ''),
('零食标签修改', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '零食标签' AND path = 'tag' ORDER BY create_time DESC LIMIT 1) AS tmp), 3, '#', '', 1, 0, 'F', '0', '0', 'shop:tag:edit', '#', 'admin', sysdate(), ''),
('零食标签删除', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '零食标签' AND path = 'tag' ORDER BY create_time DESC LIMIT 1) AS tmp), 4, '#', '', 1, 0, 'F', '0', '0', 'shop:tag:remove', '#', 'admin', sysdate(), ''),
('零食标签导出', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '零食标签' AND path = 'tag' ORDER BY create_time DESC LIMIT 1) AS tmp), 5, '#', '', 1, 0, 'F', '0', '0', 'shop:tag:export', '#', 'admin', sysdate(), '');

-- 3. 促销活动菜单 (path: promotion)
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
('促销活动', 2040, 16, 'promotion', 'system/shop/config/promotion/index', 1, 0, 'C', '0', '0', 'shop:promotion:list', 'money', 'admin', sysdate(), '', NULL, '促销活动菜单');

-- 促销活动按钮权限
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES
('促销活动查询', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '促销活动' AND path = 'promotion' ORDER BY create_time DESC LIMIT 1) AS tmp), 1, '#', '', 1, 0, 'F', '0', '0', 'shop:promotion:query', '#', 'admin', sysdate(), ''),
('促销活动新增', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '促销活动' AND path = 'promotion' ORDER BY create_time DESC LIMIT 1) AS tmp), 2, '#', '', 1, 0, 'F', '0', '0', 'shop:promotion:add', '#', 'admin', sysdate(), ''),
('促销活动修改', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '促销活动' AND path = 'promotion' ORDER BY create_time DESC LIMIT 1) AS tmp), 3, '#', '', 1, 0, 'F', '0', '0', 'shop:promotion:edit', '#', 'admin', sysdate(), ''),
('促销活动删除', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '促销活动' AND path = 'promotion' ORDER BY create_time DESC LIMIT 1) AS tmp), 4, '#', '', 1, 0, 'F', '0', '0', 'shop:promotion:remove', '#', 'admin', sysdate(), ''),
('促销活动导出', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '促销活动' AND path = 'promotion' ORDER BY create_time DESC LIMIT 1) AS tmp), 5, '#', '', 1, 0, 'F', '0', '0', 'shop:promotion:export', '#', 'admin', sysdate(), '');

-- 4. 推荐位管理菜单 (path: recommend)
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
('推荐位管理', 2040, 17, 'recommend', 'system/shop/config/recommend/index', 1, 0, 'C', '0', '0', 'shop:recommend:list', 'star', 'admin', sysdate(), '', NULL, '推荐位管理菜单');

-- 推荐位管理按钮权限
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES
('推荐位查询', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '推荐位管理' AND path = 'recommend' ORDER BY create_time DESC LIMIT 1) AS tmp), 1, '#', '', 1, 0, 'F', '0', '0', 'shop:recommend:query', '#', 'admin', sysdate(), ''),
('推荐位新增', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '推荐位管理' AND path = 'recommend' ORDER BY create_time DESC LIMIT 1) AS tmp), 2, '#', '', 1, 0, 'F', '0', '0', 'shop:recommend:add', '#', 'admin', sysdate(), ''),
('推荐位修改', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '推荐位管理' AND path = 'recommend' ORDER BY create_time DESC LIMIT 1) AS tmp), 3, '#', '', 1, 0, 'F', '0', '0', 'shop:recommend:edit', '#', 'admin', sysdate(), ''),
('推荐位删除', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '推荐位管理' AND path = 'recommend' ORDER BY create_time DESC LIMIT 1) AS tmp), 4, '#', '', 1, 0, 'F', '0', '0', 'shop:recommend:remove', '#', 'admin', sysdate(), ''),
('推荐位导出', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '推荐位管理' AND path = 'recommend' ORDER BY create_time DESC LIMIT 1) AS tmp), 5, '#', '', 1, 0, 'F', '0', '0', 'shop:recommend:export', '#', 'admin', sysdate(), '');

-- 注意事项：
-- 1. parent_id 设置为2040（商城配置），与积分规则、会员等级、系统参数同级
--    查询命令：SELECT menu_id, menu_name FROM sys_menu WHERE menu_id = 2040;
-- 2. 菜单路径遵循现有模式：series、tag、promotion、recommend
-- 3. 前端组件路径：system/shop/config/{模块}/index
-- 4. 执行SQL后需要在后台 系统管理 -> 菜单管理 中确认菜单是否正确显示
-- 5. 刷新前端页面或重新登录后可见新菜单
-- 6. 需要给角色分配新菜单的权限
