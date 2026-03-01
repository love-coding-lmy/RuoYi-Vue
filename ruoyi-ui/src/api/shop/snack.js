import request from '@/utils/request'

// ==================== 零食系列管理 ====================
// 查询零食系列列表
export function listSeries(query) {
  return request({
    url: '/shop/series/list',
    method: 'get',
    params: query
  })
}

// 查询零食系列详细
export function getSeries(seriesId) {
  return request({
    url: '/shop/series/' + seriesId,
    method: 'get'
  })
}

// 新增零食系列
export function addSeries(data) {
  return request({
    url: '/shop/series',
    method: 'post',
    data: data
  })
}

// 修改零食系列
export function updateSeries(data) {
  return request({
    url: '/shop/series',
    method: 'put',
    data: data
  })
}

// 删除零食系列
export function delSeries(seriesId) {
  return request({
    url: '/shop/series/' + seriesId,
    method: 'delete'
  })
}

// 导出零食系列
export function exportSeries(query) {
  return request({
    url: '/shop/series/export',
    method: 'post',
    params: query
  })
}

// ==================== 零食标签管理 ====================
// 查询零食标签列表
export function listTag(query) {
  return request({
    url: '/shop/tag/list',
    method: 'get',
    params: query
  })
}

// 查询零食标签详细
export function getTag(tagId) {
  return request({
    url: '/shop/tag/' + tagId,
    method: 'get'
  })
}

// 新增零食标签
export function addTag(data) {
  return request({
    url: '/shop/tag',
    method: 'post',
    data: data
  })
}

// 修改零食标签
export function updateTag(data) {
  return request({
    url: '/shop/tag',
    method: 'put',
    data: data
  })
}

// 删除零食标签
export function delTag(tagId) {
  return request({
    url: '/shop/tag/' + tagId,
    method: 'delete'
  })
}

// 导出零食标签
export function exportTag(query) {
  return request({
    url: '/shop/tag/export',
    method: 'post',
    params: query
  })
}

// ==================== 促销活动管理 ====================
// 查询促销活动列表
export function listPromotion(query) {
  return request({
    url: '/shop/promotion/list',
    method: 'get',
    params: query
  })
}

// 查询促销活动详细
export function getPromotion(promotionId) {
  return request({
    url: '/shop/promotion/' + promotionId,
    method: 'get'
  })
}

// 新增促销活动
export function addPromotion(data) {
  return request({
    url: '/shop/promotion',
    method: 'post',
    data: data
  })
}

// 修改促销活动
export function updatePromotion(data) {
  return request({
    url: '/shop/promotion',
    method: 'put',
    data: data
  })
}

// 删除促销活动
export function delPromotion(promotionId) {
  return request({
    url: '/shop/promotion/' + promotionId,
    method: 'delete'
  })
}

// 导出促销活动
export function exportPromotion(query) {
  return request({
    url: '/shop/promotion/export',
    method: 'post',
    params: query
  })
}

// ==================== 推荐位管理 ====================
// 查询推荐位列表
export function listRecommend(query) {
  return request({
    url: '/shop/recommend/list',
    method: 'get',
    params: query
  })
}

// 查询推荐位详细
export function getRecommend(positionId) {
  return request({
    url: '/shop/recommend/' + positionId,
    method: 'get'
  })
}

// 新增推荐位
export function addRecommend(data) {
  return request({
    url: '/shop/recommend',
    method: 'post',
    data: data
  })
}

// 修改推荐位
export function updateRecommend(data) {
  return request({
    url: '/shop/recommend',
    method: 'put',
    data: data
  })
}

// 删除推荐位
export function delRecommend(positionId) {
  return request({
    url: '/shop/recommend/' + positionId,
    method: 'delete'
  })
}

// 查询推荐商品列表
export function listRecommendItems(positionId) {
  return request({
    url: '/shop/recommend/items/' + positionId,
    method: 'get'
  })
}

// 添加推荐商品
export function addRecommendItem(data) {
  return request({
    url: '/shop/recommend/item',
    method: 'post',
    data: data
  })
}

// 删除推荐商品
export function delRecommendItem(itemId) {
  return request({
    url: '/shop/recommend/item/' + itemId,
    method: 'delete'
  })
}

// 导出推荐位
export function exportRecommend(query) {
  return request({
    url: '/shop/recommend/export',
    method: 'post',
    params: query
  })
}
