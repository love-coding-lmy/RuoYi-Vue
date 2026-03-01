import request from '@/utils/request'

// ==================== 会员等级相关接口 ====================

// 查询会员等级列表
export function listLevel(query) {
  return request({
    url: '/shop/config/level/list',
    method: 'get',
    params: query
  })
}

// 查询会员等级详细
export function getLevel(id) {
  return request({
    url: '/shop/config/level/' + id,
    method: 'get'
  })
}

// 新增会员等级
export function addLevel(data) {
  return request({
    url: '/shop/config/level',
    method: 'post',
    data: data
  })
}

// 修改会员等级
export function updateLevel(data) {
  return request({
    url: '/shop/config/level',
    method: 'put',
    data: data
  })
}

// 删除会员等级
export function delLevel(id) {
  return request({
    url: '/shop/config/level/' + id,
    method: 'delete'
  })
}

// 状态切换会员等级
export function changeLevelStatus(data) {
  return request({
    url: '/shop/config/level/changeStatus',
    method: 'put',
    data: data
  })
}

// ==================== 积分规则相关接口 ====================

// 查询积分规则列表
export function listPoints(query) {
  return request({
    url: '/shop/config/points/list',
    method: 'get',
    params: query
  })
}

// 查询积分规则详细
export function getPoints(id) {
  return request({
    url: '/shop/config/points/' + id,
    method: 'get'
  })
}

// 新增积分规则
export function addPoints(data) {
  return request({
    url: '/shop/config/points',
    method: 'post',
    data: data
  })
}

// 修改积分规则
export function updatePoints(data) {
  return request({
    url: '/shop/config/points',
    method: 'put',
    data: data
  })
}

// 删除积分规则
export function delPoints(id) {
  return request({
    url: '/shop/config/points/' + id,
    method: 'delete'
  })
}

// 状态切换积分规则
export function changePointsStatus(data) {
  return request({
    url: '/shop/config/points/changeStatus',
    method: 'put',
    data: data
  })
}

// ==================== 系统参数相关接口 ====================

// 查询系统参数列表
export function listSystem(query) {
  return request({
    url: '/shop/config/system/list',
    method: 'get',
    params: query
  })
}

// 查询系统参数详细
export function getSystem(id) {
  return request({
    url: '/shop/config/system/' + id,
    method: 'get'
  })
}

// 新增系统参数
export function addSystem(data) {
  return request({
    url: '/shop/config/system',
    method: 'post',
    data: data
  })
}

// 修改系统参数
export function updateSystem(data) {
  return request({
    url: '/shop/config/system',
    method: 'put',
    data: data
  })
}

// 删除系统参数
export function delSystem(id) {
  return request({
    url: '/shop/config/system/' + id,
    method: 'delete'
  })
}

// 刷新系统参数缓存
export function refreshCache() {
  return request({
    url: '/shop/config/system/refreshCache',
    method: 'delete'
  })
}
