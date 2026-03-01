package com.ruoyi.shop.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 促销活动对象 shop_promotion
 *
 * @author ruoyi
 */
public class ShopPromotion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 促销ID */
    private Long promotionId;

    /** 促销名称 */
    @Excel(name = "促销名称")
    private String promotionName;

    /** 促销类型（1折扣 2满减 3买赠） */
    @Excel(name = "促销类型", readConverterExp = "1=折扣,2=满减,3=买赠")
    private String promotionType;

    /** 促销额度/折扣值 */
    @Excel(name = "促销额度")
    private BigDecimal discountValue;

    /** 满足条件金额 */
    @Excel(name = "满足金额")
    private BigDecimal conditionAmount;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 促销描述 */
    @Excel(name = "促销描述")
    private String promotionDesc;

    /** 促销状态（0未开始 1进行中 2已结束） */
    @Excel(name = "促销状态", readConverterExp = "0=未开始,1=进行中,2=已结束")
    private String status;

    public void setPromotionId(Long promotionId)
    {
        this.promotionId = promotionId;
    }

    public Long getPromotionId()
    {
        return promotionId;
    }

    public void setPromotionName(String promotionName)
    {
        this.promotionName = promotionName;
    }

    public String getPromotionName()
    {
        return promotionName;
    }

    public void setPromotionType(String promotionType)
    {
        this.promotionType = promotionType;
    }

    public String getPromotionType()
    {
        return promotionType;
    }

    public void setDiscountValue(BigDecimal discountValue)
    {
        this.discountValue = discountValue;
    }

    public BigDecimal getDiscountValue()
    {
        return discountValue;
    }

    public void setConditionAmount(BigDecimal conditionAmount)
    {
        this.conditionAmount = conditionAmount;
    }

    public BigDecimal getConditionAmount()
    {
        return conditionAmount;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setPromotionDesc(String promotionDesc)
    {
        this.promotionDesc = promotionDesc;
    }

    public String getPromotionDesc()
    {
        return promotionDesc;
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
            .append("promotionId", getPromotionId())
            .append("promotionName", getPromotionName())
            .append("promotionType", getPromotionType())
            .append("discountValue", getDiscountValue())
            .append("conditionAmount", getConditionAmount())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("promotionDesc", getPromotionDesc())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
