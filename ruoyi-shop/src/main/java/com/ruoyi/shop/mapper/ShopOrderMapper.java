package com.ruoyi.shop.mapper;

import java.util.List;
import java.util.Map;
import com.ruoyi.shop.domain.ShopOrder;
import com.ruoyi.shop.domain.ShopOrderItem;

/**
 * 订单Mapper接口
 *
 * @author ruoyi
 */
public interface ShopOrderMapper
{
    /**
     * 查询订单
     *
     * @param orderId 订单主键
     * @return 订单
     */
    public ShopOrder selectOrderById(Long orderId);

    /**
     * 查询订单列表
     *
     * @param order 订单
     * @return 订单集合
     */
    public List<ShopOrder> selectOrderList(ShopOrder order);

    /**
     * 查询订单明细
     *
     * @param orderId 订单ID
     * @return 订单明细集合
     */
    public List<ShopOrderItem> selectOrderItemsByOrderId(Long orderId);

    /**
     * 新增订单
     *
     * @param order 订单
     * @return 结果
     */
    public int insertOrder(ShopOrder order);

    /**
     * 修改订单
     *
     * @param order 订单
     * @return 结果
     */
    public int updateOrder(ShopOrder order);

    /**
     * 删除订单
     *
     * @param orderId 订单主键
     * @return 结果
     */
    public int deleteOrderById(Long orderId);

    /**
     * 批量删除订单
     *
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderByIds(Long[] orderIds);

    /**
     * 订单发货
     *
     * @param order 订单
     * @return 结果
     */
    public int updateOrderShip(ShopOrder order);

    /**
     * 订单取消
     *
     * @param order 订单
     * @return 结果
     */
    public int updateOrderCancel(ShopOrder order);

    /**
     * 订单完成
     *
     * @param orderId 订单ID
     * @return 结果
     */
    public int updateOrderComplete(Long orderId);

    /**
     * 新增订单明细
     *
     * @param orderItem 订单明细
     * @return 结果
     */
    public int insertOrderItem(ShopOrderItem orderItem);

    /**
     * 批量新增订单明细
     *
     * @param orderItems 订单明细集合
     * @return 结果
     */
    public int batchInsertOrderItems(List<ShopOrderItem> orderItems);

    /**
     * 查询订单统计概览
     *
     * @return 统计数据
     */
    public Map<String, Object> selectOrderOverview();

    /**
     * 查询订单趋势数据
     *
     * @param params 查询参数
     * @return 趋势数据列表
     */
    public List<Map<String, Object>> selectOrderTrend(Map<String, Object> params);

    /**
     * 按状态统计订单数量
     *
     * @return 状态统计数据
     */
    public List<Map<String, Object>> selectOrderStatusStatistics();

    /**
     * 查询热销商品TOP N
     *
     * @param limit 查询数量
     * @return 热销商品列表
     */
    public List<Map<String, Object>> selectTopSellingProducts(Integer limit);
}
