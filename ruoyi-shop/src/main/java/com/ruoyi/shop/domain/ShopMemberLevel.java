package com.ruoyi.shop.domain;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 会员等级对象 shop_member_level
 *
 * @author ruoyi
 */
public class ShopMemberLevel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 等级ID */
    private Long levelId;

    /** 等级名称 */
    @Excel(name = "等级名称")
    private String levelName;

    /** 等级编码 */
    @Excel(name = "等级编码")
    private String levelCode;

    /** 所需最低积分 */
    @Excel(name = "所需最低积分")
    private Integer minPoints;

    /** 等级积分上限 */
    @Excel(name = "等级积分上限")
    private Integer maxPoints;

    /** 折扣力度 */
    @Excel(name = "折扣力度")
    private BigDecimal discount;

    /** 是否免运费 */
    @Excel(name = "是否免运费", readConverterExp = "0=不免费, 1=免运费")
    private Integer freeShipping;

    /** 免运费最低金额 */
    @Excel(name = "免运费最低金额")
    private BigDecimal freeShippingAmount;

    /** 优先级 */
    @Excel(name = "优先级")
    private Integer priority;

    /** 等级图标URL */
    private String icon;

    /** 背景颜色 */
    private String backgroundColor;

    /** 等级权益描述 */
    private String benefits;

    /** 状态 */
    @Excel(name = "状态", readConverterExp = "0=正常, 1=停用")
    private String status;

    /** 状态描述 */
    private String statusText;

    public void setLevelId(Long levelId)
    {
        this.levelId = levelId;
    }

    public Long getLevelId()
    {
        return levelId;
    }

    public void setLevelName(String levelName)
    {
        this.levelName = levelName;
    }

    public String getLevelName()
    {
        return levelName;
    }

    public void setLevelCode(String levelCode)
    {
        this.levelCode = levelCode;
    }

    public String getLevelCode()
    {
        return levelCode;
    }

    public void setMinPoints(Integer minPoints)
    {
        this.minPoints = minPoints;
    }

    public Integer getMinPoints()
    {
        return minPoints;
    }

    public void setMaxPoints(Integer maxPoints)
    {
        this.maxPoints = maxPoints;
    }

    public Integer getMaxPoints()
    {
        return maxPoints;
    }

    public void setDiscount(BigDecimal discount)
    {
        this.discount = discount;
    }

    public BigDecimal getDiscount()
    {
        return discount;
    }

    public void setFreeShipping(Integer freeShipping)
    {
        this.freeShipping = freeShipping;
    }

    public Integer getFreeShipping()
    {
        return freeShipping;
    }

    public void setFreeShippingAmount(BigDecimal freeShippingAmount)
    {
        this.freeShippingAmount = freeShippingAmount;
    }

    public BigDecimal getFreeShippingAmount()
    {
        return freeShippingAmount;
    }

    public void setPriority(Integer priority)
    {
        this.priority = priority;
    }

    public Integer getPriority()
    {
        return priority;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setBackgroundColor(String backgroundColor)
    {
        this.backgroundColor = backgroundColor;
    }

    public String getBackgroundColor()
    {
        return backgroundColor;
    }

    public void setBenefits(String benefits)
    {
        this.benefits = benefits;
    }

    public String getBenefits()
    {
        return benefits;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public String getStatusText()
    {
        return statusText;
    }

    public void setStatusText(String statusText)
    {
        this.statusText = statusText;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("levelId", getLevelId())
            .append("levelName", getLevelName())
            .append("levelCode", getLevelCode())
            .append("minPoints", getMinPoints())
            .append("maxPoints", getMaxPoints())
            .append("discount", getDiscount())
            .append("freeShipping", getFreeShipping())
            .append("freeShippingAmount", getFreeShippingAmount())
            .append("priority", getPriority())
            .append("icon", getIcon())
            .append("backgroundColor", getBackgroundColor())
            .append("benefits", getBenefits())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
