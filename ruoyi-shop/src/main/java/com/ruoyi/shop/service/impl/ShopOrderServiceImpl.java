package com.ruoyi.shop.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.shop.domain.ShopOrder;
import com.ruoyi.shop.domain.ShopOrderItem;
import com.ruoyi.shop.mapper.ShopOrderMapper;
import com.ruoyi.shop.service.IShopOrderService;

/**
 * 订单Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class ShopOrderServiceImpl implements IShopOrderService
{
    @Autowired
    private ShopOrderMapper orderMapper;

    /**
     * 查询订单
     *
     * @param orderId 订单主键
     * @return 订单
     */
    @Override
    public ShopOrder selectOrderById(Long orderId)
    {
        ShopOrder order = orderMapper.selectOrderById(orderId);
        if (order != null)
        {
            // 设置完整收货地址
            String fullAddress = order.getReceiverProvince();
            if (order.getReceiverCity() != null)
            {
                fullAddress += order.getReceiverCity();
            }
            if (order.getReceiverDistrict() != null)
            {
                fullAddress += order.getReceiverDistrict();
            }
            fullAddress += order.getReceiverAddress();
            order.setFullAddress(fullAddress);

            // 设置订单状态描述
            String statusText = getOrderStatusText(order.getOrderStatus());
            order.setOrderStatusText(statusText);

            // 查询订单明细
            List<ShopOrderItem> orderItems = orderMapper.selectOrderItemsByOrderId(orderId);
            order.setOrderItems(orderItems);
        }
        return order;
    }

    /**
     * 查询订单列表
     *
     * @param order 订单
     * @return 订单
     */
    @Override
    public List<ShopOrder> selectOrderList(ShopOrder order)
    {
        List<ShopOrder> list = orderMapper.selectOrderList(order);
        for (ShopOrder item : list)
        {
            // 设置订单状态描述
            item.setOrderStatusText(getOrderStatusText(item.getOrderStatus()));
        }
        return list;
    }

    /**
     * 新增订单
     *
     * @param order 订单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertOrder(ShopOrder order)
    {
        int rows = orderMapper.insertOrder(order);
        // 插入订单明细
        if (order.getOrderItems() != null && !order.getOrderItems().isEmpty())
        {
            for (ShopOrderItem item : order.getOrderItems())
            {
                item.setOrderId(order.getOrderId());
                orderMapper.insertOrderItem(item);
            }
        }
        return rows;
    }

    /**
     * 修改订单
     *
     * @param order 订单
     * @return 结果
     */
    @Override
    public int updateOrder(ShopOrder order)
    {
        return orderMapper.updateOrder(order);
    }

    /**
     * 批量删除订单
     *
     * @param orderIds 需要删除的订单主键
     * @return 结果
     */
    @Override
    public int deleteOrderByIds(Long[] orderIds)
    {
        return orderMapper.deleteOrderByIds(orderIds);
    }

    /**
     * 删除订单信息
     *
     * @param orderId 订单主键
     * @return 结果
     */
    @Override
    public int deleteOrderById(Long orderId)
    {
        return orderMapper.deleteOrderById(orderId);
    }

    /**
     * 订单发货
     *
     * @param orderId 订单ID
     * @return 结果
     */
    @Override
    public int updateOrderShip(Long orderId)
    {
        return orderMapper.updateOrderShip(new ShopOrder() {{ setOrderId(orderId); }});
    }

    /**
     * 订单取消
     *
     * @param orderId 订单ID
     * @param cancelReason 取消原因
     * @return 结果
     */
    @Override
    public int updateOrderCancel(Long orderId, String cancelReason)
    {
        ShopOrder order = new ShopOrder();
        order.setOrderId(orderId);
        order.setCancelReason(cancelReason);
        return orderMapper.updateOrderCancel(order);
    }

    /**
     * 订单完成
     *
     * @param orderId 订单ID
     * @return 结果
     */
    @Override
    public int updateOrderComplete(Long orderId)
    {
        return orderMapper.updateOrderComplete(orderId);
    }

    /**
     * 查询订单明细
     *
     * @param orderId 订单ID
     * @return 订单明细集合
     */
    @Override
    public List<ShopOrderItem> selectOrderItemsByOrderId(Long orderId)
    {
        return orderMapper.selectOrderItemsByOrderId(orderId);
    }

    /**
     * 获取订单状态描述
     *
     * @param status 状态码
     * @return 状态描述
     */
    private String getOrderStatusText(String status)
    {
        switch (status)
        {
            case "pending":
                return "待付款";
            case "paid":
                return "已付款";
            case "shipped":
                return "已发货";
            case "completed":
                return "已完成";
            case "cancelled":
                return "已取消";
            default:
                return "未知";
        }
    }

    /**
     * 获取订单统计概览
     *
     * @return 统计概览数据
     */
    @Override
    public Map<String, Object> getOrderOverview()
    {
        return orderMapper.selectOrderOverview();
    }

    /**
     * 获取订单趋势数据
     *
     * @param params 查询参数（包含startTime、endTime、type）
     * @return 趋势数据列表
     */
    @Override
    public List<Map<String, Object>> getOrderTrend(Map<String, Object> params)
    {
        // 根据类型设置日期格式
        String type = (String) params.get("type");
        String dateFormat;
        if ("week".equals(type))
        {
            dateFormat = "%Y-%u";
        }
        else if ("month".equals(type))
        {
            dateFormat = "%Y-%m";
        }
        else
        {
            dateFormat = "%Y-%m-%d";
        }
        params.put("dateFormat", dateFormat);

        return orderMapper.selectOrderTrend(params);
    }

    /**
     * 获取订单状态统计
     *
     * @return 状态统计数据列表
     */
    @Override
    public List<Map<String, Object>> getOrderStatusStatistics()
    {
        List<Map<String, Object>> statusList = orderMapper.selectOrderStatusStatistics();

        // 计算总数
        int totalCount = 0;
        for (Map<String, Object> status : statusList)
        {
            totalCount += ((Number) status.get("count")).intValue();
        }

        // 计算百分比
        if (totalCount > 0)
        {
            for (Map<String, Object> status : statusList)
            {
                int count = ((Number) status.get("count")).intValue();
                double percentage = (count * 100.0) / totalCount;
                status.put("percentage", Math.round(percentage * 100.0) / 100.0);
            }
        }

        return statusList;
    }

    /**
     * 获取热销商品排行
     *
     * @param limit 查询数量
     * @return 热销商品列表
     */
    @Override
    public List<Map<String, Object>> getTopSellingProducts(Integer limit)
    {
        List<Map<String, Object>> products = orderMapper.selectTopSellingProducts(limit);

        // 设置排名
        for (int i = 0; i < products.size(); i++)
        {
            products.get(i).put("rank", i + 1);
        }

        return products;
    }
}
