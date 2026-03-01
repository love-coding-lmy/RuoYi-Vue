import request from '@/utils/request'

// 查询会员等级列表
export function listMemberLevel(query) {
  return request({
    url: '/shop/memberLevel/list',
    method: 'get',
    params: query
  })
}

// 查询会员等级详细
export function getMemberLevel(levelId) {
  return request({
    url: '/shop/memberLevel/' + levelId,
    method: 'get'
  })
}

// 新增会员等级
export function addMemberLevel(data) {
  return request({
    url: '/shop/memberLevel',
    method: 'post',
    data: data
  })
}

// 修改会员等级
export function updateMemberLevel(data) {
  return request({
    url: '/shop/memberLevel',
    method: 'put',
    data: data
  })
}

// 删除会员等级
export function delMemberLevel(levelId) {
  return request({
    url: '/shop/memberLevel/' + levelId,
    method: 'delete'
  })
}

// 导出会员等级
export function exportMemberLevel(query) {
  return request({
    url: '/shop/memberLevel/export',
    method: 'post',
    params: query
  })
}

// 状态切换
export function changeStatus(data) {
  return request({
    url: '/shop/memberLevel/changeStatus',
    method: 'put',
    data: data
  })
}
