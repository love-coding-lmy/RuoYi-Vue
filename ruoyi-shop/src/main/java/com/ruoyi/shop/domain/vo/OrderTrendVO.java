package com.ruoyi.shop.domain.vo;

import java.math.BigDecimal;

/**
 * 订单趋势VO
 *
 * @author ruoyi
 */
public class OrderTrendVO
{
    /** 日期 */
    private String date;

    /** 订单数量 */
    private Integer orderCount;

    /** 订单金额 */
    private BigDecimal orderAmount;

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public Integer getOrderCount()
    {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount)
    {
        this.orderCount = orderCount;
    }

    public BigDecimal getOrderAmount()
    {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount)
    {
        this.orderAmount = orderAmount;
    }
}
