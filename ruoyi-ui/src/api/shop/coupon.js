import request from '@/utils/request'

// 查询优惠券列表
export function listCoupon(query) {
  return request({
    url: '/shop/coupon/list',
    method: 'get',
    params: query
  })
}

// 查询优惠券详细
export function getCoupon(couponId) {
  return request({
    url: '/shop/coupon/' + couponId,
    method: 'get'
  })
}

// 新增优惠券
export function addCoupon(data) {
  return request({
    url: '/shop/coupon',
    method: 'post',
    data: data
  })
}

// 修改优惠券
export function updateCoupon(data) {
  return request({
    url: '/shop/coupon',
    method: 'put',
    data: data
  })
}

// 删除优惠券
export function delCoupon(couponId) {
  return request({
    url: '/shop/coupon/' + couponId,
    method: 'delete'
  })
}

// 导出优惠券
export function exportCoupon(query) {
  return request({
    url: '/shop/coupon/export',
    method: 'post',
    params: query
  })
}

// 状态切换
export function changeStatus(data) {
  return request({
    url: '/shop/coupon/changeStatus',
    method: 'put',
    data: data
  })
}
