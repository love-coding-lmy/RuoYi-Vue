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

    /** 推荐位类型（1首页推荐 2分类推荐 3详情推荐） */
    @Excel(name = "推荐位类型", readConverterExp = "1=首页推荐,2=分类推荐,3=详情推荐")
    private String positionType;

    /** 推荐商品数量 */
    @Excel(name = "推荐数量")
    private Integer productCount;

    /** 推荐位描述 */
    @Excel(name = "推荐位描述")
    private String positionDesc;

    /** 显示状态（0显示 1隐藏） */
    @Excel(name = "显示状态", readConverterExp = "0=显示,1=隐藏")
    private String showStatus;

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

    public void setProductCount(Integer productCount)
    {
        this.productCount = productCount;
    }

    public Integer getProductCount()
    {
        return productCount;
    }

    public void setPositionDesc(String positionDesc)
    {
        this.positionDesc = positionDesc;
    }

    public String getPositionDesc()
    {
        return positionDesc;
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
            .append("positionId", getPositionId())
            .append("positionName", getPositionName())
            .append("positionCode", getPositionCode())
            .append("positionType", getPositionType())
            .append("productCount", getProductCount())
            .append("positionDesc", getPositionDesc())
            .append("showStatus", getShowStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
