package com.ruoyi.shop.domain.vo;

/**
 * 订单状态统计VO
 *
 * @author ruoyi
 */
public class OrderStatusStatisticsVO
{
    /** 订单状态 */
    private String status;

    /** 订单状态名称 */
    private String statusName;

    /** 订单数量 */
    private Integer count;

    /** 占比（百分比） */
    private Double percentage;

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatusName()
    {
        return statusName;
    }

    public void setStatusName(String statusName)
    {
        this.statusName = statusName;
    }

    public Integer getCount()
    {
        return count;
    }

    public void setCount(Integer count)
    {
        this.count = count;
    }

    public Double getPercentage()
    {
        return percentage;
    }

    public void setPercentage(Double percentage)
    {
        this.percentage = percentage;
    }
}
