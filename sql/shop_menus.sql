-- 商城模块菜单SQL
-- 注意：需要根据实际的商城管理菜单ID调整 parent_id

-- 查询商城管理菜单ID
-- SELECT menu_id, menu_name FROM sys_menu WHERE menu_name LIKE '%商城%' OR menu_id = 2070;

-- 1. 会员等级菜单
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
('会员等级', 2070, 10, 'memberLevel', 'system/shop/memberLevel/index', 1, 0, 'C', '0', '0', 'shop:memberLevel:list', 'peoples', 'admin', sysdate(), '', NULL, '会员等级菜单');

-- 获取刚插入的会员等级菜单ID用于按钮权限
-- SELECT @member_level_id := LAST_INSERT_ID();

-- 会员等级按钮权限
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES
('会员等级查询', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '会员等级' AND path = 'memberLevel' ORDER BY create_time DESC LIMIT 1) AS tmp), 1, '#', '', 1, 0, 'F', '0', '0', 'shop:memberLevel:query', '#', 'admin', sysdate(), ''),
('会员等级新增', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '会员等级' AND path = 'memberLevel' ORDER BY create_time DESC LIMIT 1) AS tmp), 2, '#', '', 1, 0, 'F', '0', '0', 'shop:memberLevel:add', '#', 'admin', sysdate(), ''),
('会员等级修改', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '会员等级' AND path = 'memberLevel' ORDER BY create_time DESC LIMIT 1) AS tmp), 3, '#', '', 1, 0, 'F', '0', '0', 'shop:memberLevel:edit', '#', 'admin', sysdate(), ''),
('会员等级删除', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '会员等级' AND path = 'memberLevel' ORDER BY create_time DESC LIMIT 1) AS tmp), 4, '#', '', 1, 0, 'F', '0', '0', 'shop:memberLevel:remove', '#', 'admin', sysdate(), ''),
('会员等级导出', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '会员等级' AND path = 'memberLevel' ORDER BY create_time DESC LIMIT 1) AS tmp), 5, '#', '', 1, 0, 'F', '0', '0', 'shop:memberLevel:export', '#', 'admin', sysdate(), '');

-- 2. 积分规则菜单
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
('积分规则', 2070, 11, 'pointsRule', 'system/shop/pointsRule/index', 1, 0, 'C', '0', '0', 'shop:pointsRule:list', 'guide', 'admin', sysdate(), '', NULL, '积分规则菜单');

-- 积分规则按钮权限
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES
('积分规则查询', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '积分规则' AND path = 'pointsRule' ORDER BY create_time DESC LIMIT 1) AS tmp), 1, '#', '', 1, 0, 'F', '0', '0', 'shop:pointsRule:query', '#', 'admin', sysdate(), ''),
('积分规则新增', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '积分规则' AND path = 'pointsRule' ORDER BY create_time DESC LIMIT 1) AS tmp), 2, '#', '', 1, 0, 'F', '0', '0', 'shop:pointsRule:add', '#', 'admin', sysdate(), ''),
('积分规则修改', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '积分规则' AND path = 'pointsRule' ORDER BY create_time DESC LIMIT 1) AS tmp), 3, '#', '', 1, 0, 'F', '0', '0', 'shop:pointsRule:edit', '#', 'admin', sysdate(), ''),
('积分规则删除', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '积分规则' AND path = 'pointsRule' ORDER BY create_time DESC LIMIT 1) AS tmp), 4, '#', '', 1, 0, 'F', '0', '0', 'shop:pointsRule:remove', '#', 'admin', sysdate(), ''),
('积分规则导出', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '积分规则' AND path = 'pointsRule' ORDER BY create_time DESC LIMIT 1) AS tmp), 5, '#', '', 1, 0, 'F', '0', '0', 'shop:pointsRule:export', '#', 'admin', sysdate(), '');

-- 3. 系统参数菜单
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
('系统参数', 2070, 12, 'config', 'system/shop/config/index', 1, 0, 'C', '0', '0', 'shop:config:list', 'edit', 'admin', sysdate(), '', NULL, '系统参数菜单');

-- 系统参数按钮权限
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES
('系统参数查询', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '系统参数' AND path = 'config' ORDER BY create_time DESC LIMIT 1) AS tmp), 1, '#', '', 1, 0, 'F', '0', '0', 'shop:config:query', '#', 'admin', sysdate(), ''),
('系统参数新增', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '系统参数' AND path = 'config' ORDER BY create_time DESC LIMIT 1) AS tmp), 2, '#', '', 1, 0, 'F', '0', '0', 'shop:config:add', '#', 'admin', sysdate(), ''),
('系统参数修改', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '系统参数' AND path = 'config' ORDER BY create_time DESC LIMIT 1) AS tmp), 3, '#', '', 1, 0, 'F', '0', '0', 'shop:config:edit', '#', 'admin', sysdate(), ''),
('系统参数删除', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '系统参数' AND path = 'config' ORDER BY create_time DESC LIMIT 1) AS tmp), 4, '#', '', 1, 0, 'F', '0', '0', 'shop:config:remove', '#', 'admin', sysdate(), ''),
('系统参数导出', (SELECT menu_id FROM (SELECT menu_id FROM sys_menu WHERE menu_name = '系统参数' AND path = 'config' ORDER BY create_time DESC LIMIT 1) AS tmp), 5, '#', '', 1, 0, 'F', '0', '0', 'shop:config:export', '#', 'admin', sysdate(), '');

-- 注意事项：
-- 1. parent_id 默认设置为2070，请根据实际商城管理的菜单ID进行调整
--    查询命令：SELECT menu_id, menu_name FROM sys_menu WHERE menu_name LIKE '%商城%';
-- 2. 执行SQL后需要在后台 系统管理 -> 菜单管理 中确认菜单是否正确显示
-- 3. 刷新前端页面或重新登录后可见新菜单
-- 4. 需要给角色分配新菜单的权限
