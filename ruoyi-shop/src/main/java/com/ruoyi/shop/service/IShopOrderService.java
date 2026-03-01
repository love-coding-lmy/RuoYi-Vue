package com.ruoyi.shop.service;

import java.util.List;
import com.ruoyi.shop.domain.ShopOrder;
import com.ruoyi.shop.domain.ShopOrderItem;

/**
 * 订单Service接口
 *
 * @author ruoyi
 */
public interface IShopOrderService
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
     * 批量删除订单
     *
     * @param orderIds 需要删除的订单主键集合
     * @return 结果
     */
    public int deleteOrderByIds(Long[] orderIds);

    /**
     * 删除订单信息
     *
     * @param orderId 订单主键
     * @return 结果
     */
    public int deleteOrderById(Long orderId);

    /**
     * 订单发货
     *
     * @param orderId 订单ID
     * @return 结果
     */
    public int updateOrderShip(Long orderId);

    /**
     * 订单取消
     *
     * @param orderId 订单ID
     * @param cancelReason 取消原因
     * @return 结果
     */
    public int updateOrderCancel(Long orderId, String cancelReason);

    /**
     * 订单完成
     *
     * @param orderId 订单ID
     * @return 结果
     */
    public int updateOrderComplete(Long orderId);

    /**
     * 查询订单明细
     *
     * @param orderId 订单ID
     * @return 订单明细集合
     */
    public List<ShopOrderItem> selectOrderItemsByOrderId(Long orderId);
}
