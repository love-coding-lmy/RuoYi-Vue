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

    /** 活动类型（seckill秒杀 discount满减 bundle捆绑 gift赠品） */
    @Excel(name = "活动类型")
    private String promotionType;

    /** 促销描述 */
    @Excel(name = "促销描述")
    private String promotionDesc;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 最低参与金额 */
    @Excel(name = "最低金额")
    private BigDecimal minAmount;

    /** 优惠类型（amount金额 percent百分比） */
    @Excel(name = "优惠类型")
    private String discountType;

    /** 优惠值 */
    @Excel(name = "优惠值")
    private BigDecimal discountValue;

    /** 库存数量 */
    @Excel(name = "库存数量")
    private Integer stock;

    /** 已售数量 */
    @Excel(name = "已售数量")
    private Integer soldCount;

    /** 每人限购数量（0不限购） */
    @Excel(name = "每人限购")
    private Integer limitPerUser;

    /** 优先级（数字越小优先级越高） */
    @Excel(name = "优先级")
    private Integer priority;

    /** 状态（0正常 1停用 2已结束） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用,2=已结束")
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

    public void setPromotionDesc(String promotionDesc)
    {
        this.promotionDesc = promotionDesc;
    }

    public String getPromotionDesc()
    {
        return promotionDesc;
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

    public void setMinAmount(BigDecimal minAmount)
    {
        this.minAmount = minAmount;
    }

    public BigDecimal getMinAmount()
    {
        return minAmount;
    }

    public void setDiscountType(String discountType)
    {
        this.discountType = discountType;
    }

    public String getDiscountType()
    {
        return discountType;
    }

    public void setDiscountValue(BigDecimal discountValue)
    {
        this.discountValue = discountValue;
    }

    public BigDecimal getDiscountValue()
    {
        return discountValue;
    }

    public void setStock(Integer stock)
    {
        this.stock = stock;
    }

    public Integer getStock()
    {
        return stock;
    }

    public void setSoldCount(Integer soldCount)
    {
        this.soldCount = soldCount;
    }

    public Integer getSoldCount()
    {
        return soldCount;
    }

    public void setLimitPerUser(Integer limitPerUser)
    {
        this.limitPerUser = limitPerUser;
    }

    public Integer getLimitPerUser()
    {
        return limitPerUser;
    }

    public void setPriority(Integer priority)
    {
        this.priority = priority;
    }

    public Integer getPriority()
    {
        return priority;
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
            .append("promotionDesc", getPromotionDesc())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("minAmount", getMinAmount())
            .append("discountType", getDiscountType())
            .append("discountValue", getDiscountValue())
            .append("stock", getStock())
            .append("soldCount", getSoldCount())
            .append("limitPerUser", getLimitPerUser())
            .append("priority", getPriority())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
