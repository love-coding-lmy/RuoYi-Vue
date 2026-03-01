import request from '@/utils/request'

// 查询订单列表
export function listOrder(query) {
  return request({
    url: '/shop/order/list',
    method: 'get',
    params: query
  })
}

// 查询订单详细
export function getOrder(orderId) {
  return request({
    url: '/shop/order/' + orderId,
    method: 'get'
  })
}

// 新增订单
export function addOrder(data) {
  return request({
    url: '/shop/order',
    method: 'post',
    data: data
  })
}

// 修改订单
export function updateOrder(data) {
  return request({
    url: '/shop/order',
    method: 'put',
    data: data
  })
}

// 删除订单
export function delOrder(orderId) {
  return request({
    url: '/shop/order/' + orderId,
    method: 'delete'
  })
}

// 订单发货
export function shipOrder(orderId) {
  return request({
    url: '/shop/order/ship/' + orderId,
    method: 'put'
  })
}

// 订单取消
export function cancelOrder(orderId, cancelReason) {
  return request({
    url: '/shop/order/cancel/' + orderId,
    method: 'put',
    data: cancelReason
  })
}

// 订单完成
export function completeOrder(orderId) {
  return request({
    url: '/shop/order/complete/' + orderId,
    method: 'put'
  })
}

// 导出订单
export function exportOrder(query) {
  return request({
    url: '/shop/order/export',
    method: 'post',
    params: query
  })
}
