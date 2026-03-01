import request from '@/utils/request'

/**
 * 查询订单统计概览
 */
export function getOverview() {
  return request({
    url: '/shop/order/statistics/overview',
    method: 'get'
  })
}

/**
 * 查询订单趋势数据
 */
export function getTrend(query) {
  return request({
    url: '/shop/order/statistics/trend',
    method: 'get',
    params: query
  })
}

/**
 * 查询订单状态统计
 */
export function getStatusStatistics() {
  return request({
    url: '/shop/order/statistics/status',
    method: 'get'
  })
}

/**
 * 查询热销商品排行
 */
export function getTopSellingProducts(query) {
  return request({
    url: '/shop/order/statistics/topProducts',
    method: 'get',
    params: query
  })
}
