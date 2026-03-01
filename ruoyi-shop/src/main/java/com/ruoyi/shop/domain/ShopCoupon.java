package com.ruoyi.shop.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 优惠券对象 shop_coupon
 *
 * @author ruoyi
 */
public class ShopCoupon extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 优惠券ID */
    private Long couponId;

    /** 优惠券名称 */
    @Excel(name = "优惠券名称")
    private String couponName;

    /** 优惠券类型（reduction满减 discount折扣 gift满赠 shipping运费） */
    @Excel(name = "优惠券类型", readConverterExp = "reduction=满减券, discount=折扣券, gift=满赠券, shipping=运费券")
    private String couponType;

    /** 使用条件描述 */
    @Excel(name = "使用条件")
    private String couponCondition;

    /** 最低消费金额 */
    @Excel(name = "最低消费金额")
    private BigDecimal minAmount;

    /** 减免金额（满减券专用） */
    @Excel(name = "减免金额")
    private BigDecimal reduceAmount;

    /** 折扣力度（折扣券专用，如8.5折） */
    @Excel(name = "折扣力度")
    private BigDecimal discount;

    /** 赠品ID（满赠券专用） */
    private Long giftProductId;

    /** 是否免运费（运费券专用） */
    @Excel(name = "是否免运费", readConverterExp = "0=否, 1=是")
    private Integer freeShipping;

    /** 发行总量 */
    @Excel(name = "发行总量")
    private Integer totalCount;

    /** 已领取数量 */
    @Excel(name = "已领取数量")
    private Integer receiveCount;

    /** 已使用数量 */
    @Excel(name = "已使用数量")
    private Integer useCount;

    /** 每人限领数量 */
    @Excel(name = "每人限领数量")
    private Integer perUserLimit;

    /** 有效期类型（time按时间 days按天数） */
    @Excel(name = "有效期类型", readConverterExp = "time=按时间, days=按天数")
    private String validType;

    /** 领取后有效天数（按天数类型专用） */
    @Excel(name = "有效天数")
    private Integer validDays;

    /** 有效期开始时间（按时间类型专用） */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 有效期结束时间（按时间类型专用） */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常, 1=停用")
    private String status;

    /** 优惠券类型描述（非数据库字段） */
    private String couponTypeText;

    /** 有效期类型描述（非数据库字段） */
    private String validTypeText;

    /** 状态描述（非数据库字段） */
    private String statusText;

    /** 剩余数量（非数据库字段） */
    private Integer remainCount;

    public void setCouponId(Long couponId)
    {
        this.couponId = couponId;
    }

    public Long getCouponId()
    {
        return couponId;
    }

    public void setCouponName(String couponName)
    {
        this.couponName = couponName;
    }

    public String getCouponName()
    {
        return couponName;
    }

    public void setCouponType(String couponType)
    {
        this.couponType = couponType;
    }

    public String getCouponType()
    {
        return couponType;
    }

    public void setCouponCondition(String couponCondition)
    {
        this.couponCondition = couponCondition;
    }

    public String getCouponCondition()
    {
        return couponCondition;
    }

    public void setMinAmount(BigDecimal minAmount)
    {
        this.minAmount = minAmount;
    }

    public BigDecimal getMinAmount()
    {
        return minAmount;
    }

    public void setReduceAmount(BigDecimal reduceAmount)
    {
        this.reduceAmount = reduceAmount;
    }

    public BigDecimal getReduceAmount()
    {
        return reduceAmount;
    }

    public void setDiscount(BigDecimal discount)
    {
        this.discount = discount;
    }

    public BigDecimal getDiscount()
    {
        return discount;
    }

    public void setGiftProductId(Long giftProductId)
    {
        this.giftProductId = giftProductId;
    }

    public Long getGiftProductId()
    {
        return giftProductId;
    }

    public void setFreeShipping(Integer freeShipping)
    {
        this.freeShipping = freeShipping;
    }

    public Integer getFreeShipping()
    {
        return freeShipping;
    }

    public void setTotalCount(Integer totalCount)
    {
        this.totalCount = totalCount;
    }

    public Integer getTotalCount()
    {
        return totalCount;
    }

    public void setReceiveCount(Integer receiveCount)
    {
        this.receiveCount = receiveCount;
    }

    public Integer getReceiveCount()
    {
        return receiveCount;
    }

    public void setUseCount(Integer useCount)
    {
        this.useCount = useCount;
    }

    public Integer getUseCount()
    {
        return useCount;
    }

    public void setPerUserLimit(Integer perUserLimit)
    {
        this.perUserLimit = perUserLimit;
    }

    public Integer getPerUserLimit()
    {
        return perUserLimit;
    }

    public void setValidType(String validType)
    {
        this.validType = validType;
    }

    public String getValidType()
    {
        return validType;
    }

    public void setValidDays(Integer validDays)
    {
        this.validDays = validDays;
    }

    public Integer getValidDays()
    {
        return validDays;
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

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public String getCouponTypeText()
    {
        return couponTypeText;
    }

    public void setCouponTypeText(String couponTypeText)
    {
        this.couponTypeText = couponTypeText;
    }

    public String getValidTypeText()
    {
        return validTypeText;
    }

    public void setValidTypeText(String validTypeText)
    {
        this.validTypeText = validTypeText;
    }

    public String getStatusText()
    {
        return statusText;
    }

    public void setStatusText(String statusText)
    {
        this.statusText = statusText;
    }

    public Integer getRemainCount()
    {
        return remainCount;
    }

    public void setRemainCount(Integer remainCount)
    {
        this.remainCount = remainCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("couponId", getCouponId())
            .append("couponName", getCouponName())
            .append("couponType", getCouponType())
            .append("couponCondition", getCouponCondition())
            .append("minAmount", getMinAmount())
            .append("reduceAmount", getReduceAmount())
            .append("discount", getDiscount())
            .append("giftProductId", getGiftProductId())
            .append("freeShipping", getFreeShipping())
            .append("totalCount", getTotalCount())
            .append("receiveCount", getReceiveCount())
            .append("useCount", getUseCount())
            .append("perUserLimit", getPerUserLimit())
            .append("validType", getValidType())
            .append("validDays", getValidDays())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
