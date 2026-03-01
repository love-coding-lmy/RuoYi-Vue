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

    /** 系列图标 */
    private String seriesIcon;

    /** 系列描述 */
    @Excel(name = "系列描述")
    private String seriesDesc;

    /** 排序号 */
    @Excel(name = "排序号")
    private Integer sortOrder;

    /** 显示状态（0显示 1隐藏） */
    @Excel(name = "显示状态", readConverterExp = "0=显示,1=隐藏")
    private String showStatus;

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

    public void setSeriesIcon(String seriesIcon)
    {
        this.seriesIcon = seriesIcon;
    }

    public String getSeriesIcon()
    {
        return seriesIcon;
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

    public void setShowStatus(String showStatus)
    {
        this.showStatus = showStatus;
    }

    public String getShowStatus()
    {
        return showStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("seriesId", getSeriesId())
            .append("seriesName", getSeriesName())
            .append("seriesIcon", getSeriesIcon())
            .append("seriesDesc", getSeriesDesc())
            .append("sortOrder", getSortOrder())
            .append("showStatus", getShowStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
