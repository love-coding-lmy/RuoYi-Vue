package com.ruoyi.shop.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 零食系列对象 shop_snack_series
 *
 * @author ruoyi
 */
public class ShopSnackSeries extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 系列ID */
    private Long seriesId;

    /** 系列名称 */
    @Excel(name = "系列名称")
    private String seriesName;

    /** 系列编码 */
    @Excel(name = "系列编码")
    private String seriesCode;

    /** 系列封面图 */
    @Excel(name = "系列封面")
    private String seriesImage;

    /** 系列描述 */
    @Excel(name = "系列描述")
    private String seriesDesc;

    /** 排序号 */
    @Excel(name = "排序号")
    private Integer sortOrder;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public void setSeriesId(Long seriesId)
    {
        this.seriesId = seriesId;
    }

    public Long getSeriesId()
    {
        return seriesId;
    }

    public void setSeriesName(String seriesName)
    {
        this.seriesName = seriesName;
    }

    public String getSeriesName()
    {
        return seriesName;
    }

    public void setSeriesCode(String seriesCode)
    {
        this.seriesCode = seriesCode;
    }

    public String getSeriesCode()
    {
        return seriesCode;
    }

    public void setSeriesImage(String seriesImage)
    {
        this.seriesImage = seriesImage;
    }

    public String getSeriesImage()
    {
        return seriesImage;
    }

    public void setSeriesDesc(String seriesDesc)
    {
        this.seriesDesc = seriesDesc;
    }

    public String getSeriesDesc()
    {
        return seriesDesc;
    }

    public void setSortOrder(Integer sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    public Integer getSortOrder()
    {
        return sortOrder;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("seriesId", getSeriesId())
            .append("seriesName", getSeriesName())
            .append("seriesCode", getSeriesCode())
            .append("seriesImage", getSeriesImage())
            .append("seriesDesc", getSeriesDesc())
            .append("sortOrder", getSortOrder())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
