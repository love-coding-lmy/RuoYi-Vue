package com.ruoyi.shop.domain.vo;

import java.math.BigDecimal;

/**
 * 订单统计概览VO
 *
 * @author ruoyi
 */
public class OrderStatisticsVO
{
    /** 订单总数 */
    private Integer totalOrders;

    /** 订单总金额 */
    private BigDecimal totalAmount;

    /** 今日订单数 */
    private Integer todayOrders;

    /** 今日销售额 */
    private BigDecimal todayAmount;

    /** 待付款订单数 */
    private Integer pendingOrders;

    /** 待发货订单数 */
    private Integer paidOrders;

    /** 已发货订单数 */
    private Integer shippedOrders;

    /** 已完成订单数 */
    private Integer completedOrders;

    /** 已取消订单数 */
    private Integer cancelledOrders;

    public Integer getTotalOrders()
    {
        return totalOrders;
    }

    public void setTotalOrders(Integer totalOrders)
    {
        this.totalOrders = totalOrders;
    }

    public BigDecimal getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public Integer getTodayOrders()
    {
        return todayOrders;
    }

    public void setTodayOrders(Integer todayOrders)
    {
        this.todayOrders = todayOrders;
    }

    public BigDecimal getTodayAmount()
    {
        return todayAmount;
    }

    public void setTodayAmount(BigDecimal todayAmount)
    {
        this.todayAmount = todayAmount;
    }

    public Integer getPendingOrders()
    {
        return pendingOrders;
    }

    public void setPendingOrders(Integer pendingOrders)
    {
        this.pendingOrders = pendingOrders;
    }

    public Integer getPaidOrders()
    {
        return paidOrders;
    }

    public void setPaidOrders(Integer paidOrders)
    {
        this.paidOrders = paidOrders;
    }

    public Integer getShippedOrders()
    {
        return shippedOrders;
    }

    public void setShippedOrders(Integer shippedOrders)
    {
        this.shippedOrders = shippedOrders;
    }

    public Integer getCompletedOrders()
    {
        return completedOrders;
    }

    public void setCompletedOrders(Integer completedOrders)
    {
        this.completedOrders = completedOrders;
    }

    public Integer getCancelledOrders()
    {
        return cancelledOrders;
    }

    public void setCancelledOrders(Integer cancelledOrders)
    {
        this.cancelledOrders = cancelledOrders;
    }
}
