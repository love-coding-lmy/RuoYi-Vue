import request from '@/utils/request'

// 查询系统参数列表
export function listConfig(query) {
  return request({
    url: '/shop/config/list',
    method: 'get',
    params: query
  })
}

// 查询系统参数详细
export function getConfig(configId) {
  return request({
    url: '/shop/config/' + configId,
    method: 'get'
  })
}

// 根据键名查询参数值
export function getConfigKey(configKey) {
  return request({
    url: '/shop/config/configKey/' + configKey,
    method: 'get'
  })
}

// 新增系统参数
export function addConfig(data) {
  return request({
    url: '/shop/config',
    method: 'post',
    data: data
  })
}

// 修改系统参数
export function updateConfig(data) {
  return request({
    url: '/shop/config',
    method: 'put',
    data: data
  })
}

// 删除系统参数
export function delConfig(configId) {
  return request({
    url: '/shop/config/' + configId,
    method: 'delete'
  })
}

// 导出系统参数
export function exportConfig(query) {
  return request({
    url: '/shop/config/export',
    method: 'post',
    params: query
  })
}

// 状态切换
export function changeStatus(data) {
  return request({
    url: '/shop/config/changeStatus',
    method: 'put',
    data: data
  })
}

// 刷新参数缓存
export function refreshCache() {
  return request({
    url: '/shop/config/refreshCache',
    method: 'delete'
  })
}
