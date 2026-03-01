-- 订单统计菜单SQL
-- 菜单 SQL
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
('订单统计', 2070, 5, 'statistics', 'system/shop/statistics/index', 1, 0, 'C', '0', '0', 'shop:order:query', 'chart', 'admin', sysdate(), '', NULL, '订单统计菜单');

-- 获取刚插入的菜单ID（假设父菜单ID为2070，请根据实际情况调整）
-- 查询命令：SELECT menu_id FROM sys_menu WHERE menu_name = '订单管理' AND parent_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '商城管理');

-- 注意事项：
-- 1. parent_id 需要根据实际商城管理的菜单ID进行调整
-- 2. 执行SQL后需要在后台系统管理->菜单管理中确认菜单是否正确显示
-- 3. 刷新前端页面或重新登录后可见新菜单
-- 4. 权限标识 shop:order:query 使用现有的订单查询权限
