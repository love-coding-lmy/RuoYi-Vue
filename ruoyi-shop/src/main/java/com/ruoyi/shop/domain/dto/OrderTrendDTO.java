package com.ruoyi.shop.domain.dto;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单趋势查询DTO
 *
 * @author ruoyi
 */
public class OrderTrendDTO extends BaseEntity
{
    /** 开始时间 */
    private String startTime;

    /** 结束时间 */
    private String endTime;

    /** 统计类型（day/week/month） */
    private String type;

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
