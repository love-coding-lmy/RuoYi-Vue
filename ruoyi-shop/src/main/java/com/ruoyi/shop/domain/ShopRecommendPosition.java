package com.ruoyi.shop.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 推荐位对象 shop_recommend_position
 *
 * @author ruoyi
 */
public class ShopRecommendPosition extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 推荐位ID */
    private Long positionId;

    /** 推荐位名称 */
    @Excel(name = "推荐位名称")
    private String positionName;

    /** 推荐位编码 */
    @Excel(name = "推荐位编码")
    private String positionCode;

    /** 推荐位类型（index首页 category分类页 detail详情页） */
    @Excel(name = "推荐位类型")
    private String positionType;

    /** 最大商品数量 */
    @Excel(name = "最大商品数量")
    private Integer maxItems;

    /** 宽度 */
    private Integer width;

    /** 高度 */
    private Integer height;

    /** 模板类型 */
    private String templateType;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public void setPositionId(Long positionId)
    {
        this.positionId = positionId;
    }

    public Long getPositionId()
    {
        return positionId;
    }

    public void setPositionName(String positionName)
    {
        this.positionName = positionName;
    }

    public String getPositionName()
    {
        return positionName;
    }

    public void setPositionCode(String positionCode)
    {
        this.positionCode = positionCode;
    }

    public String getPositionCode()
    {
        return positionCode;
    }

    public void setPositionType(String positionType)
    {
        this.positionType = positionType;
    }

    public String getPositionType()
    {
        return positionType;
    }

    public void setMaxItems(Integer maxItems)
    {
        this.maxItems = maxItems;
    }

    public Integer getMaxItems()
    {
        return maxItems;
    }

    public void setWidth(Integer width)
    {
        this.width = width;
    }

    public Integer getWidth()
    {
        return width;
    }

    public void setHeight(Integer height)
    {
        this.height = height;
    }

    public Integer getHeight()
    {
        return height;
    }

    public void setTemplateType(String templateType)
    {
        this.templateType = templateType;
    }

    public String getTemplateType()
    {
        return templateType;
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
            .append("positionId", getPositionId())
            .append("positionName", getPositionName())
            .append("positionCode", getPositionCode())
            .append("positionType", getPositionType())
            .append("maxItems", getMaxItems())
            .append("width", getWidth())
            .append("height", getHeight())
            .append("templateType", getTemplateType())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
