import request from '@/utils/request'

// 查询积分规则列表
export function listPointsRule(query) {
  return request({
    url: '/shop/pointsRule/list',
    method: 'get',
    params: query
  })
}

// 查询积分规则详细
export function getPointsRule(ruleId) {
  return request({
    url: '/shop/pointsRule/' + ruleId,
    method: 'get'
  })
}

// 新增积分规则
export function addPointsRule(data) {
  return request({
    url: '/shop/pointsRule',
    method: 'post',
    data: data
  })
}

// 修改积分规则
export function updatePointsRule(data) {
  return request({
    url: '/shop/pointsRule',
    method: 'put',
    data: data
  })
}

// 删除积分规则
export function delPointsRule(ruleId) {
  return request({
    url: '/shop/pointsRule/' + ruleId,
    method: 'delete'
  })
}

// 导出积分规则
export function exportPointsRule(query) {
  return request({
    url: '/shop/pointsRule/export',
    method: 'post',
    params: query
  })
}

// 状态切换
export function changeStatus(data) {
  return request({
    url: '/shop/pointsRule/changeStatus',
    method: 'put',
    data: data
  })
}
