package com.ruoyi.shop.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 积分规则对象 shop_points_rule
 *
 * @author ruoyi
 */
public class ShopPointsRule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 规则ID */
    private Long ruleId;

    /** 规则名称 */
    @Excel(name = "规则名称")
    private String ruleName;

    /** 规则类型 */
    @Excel(name = "规则类型", readConverterExp = "consume=消费行为, action=行为活动, exchange=兑换, deduction=抵扣")
    private String ruleType;

    /** 规则编码 */
    @Excel(name = "规则编码")
    private String ruleCode;

    /** 积分类型 */
    @Excel(name = "积分类型", readConverterExp = "earn=获得, spend=消耗")
    private String pointsType;

    /** 积分值 */
    @Excel(name = "积分值")
    private Integer pointsValue;

    /** 规则条件 */
    private String ruleCondition;

    /** 每日最大积分获取限制 */
    @Excel(name = "每日最大积分")
    private Integer maxPointsPerDay;

    /** 状态 */
    @Excel(name = "状态", readConverterExp = "0=正常, 1=停用")
    private String status;

    /** 规则类型描述 */
    private String ruleTypeText;

    /** 积分类型描述 */
    private String pointsTypeText;

    /** 状态描述 */
    private String statusText;

    public void setRuleId(Long ruleId)
    {
        this.ruleId = ruleId;
    }

    public Long getRuleId()
    {
        return ruleId;
    }

    public void setRuleName(String ruleName)
    {
        this.ruleName = ruleName;
    }

    public String getRuleName()
    {
        return ruleName;
    }

    public void setRuleType(String ruleType)
    {
        this.ruleType = ruleType;
    }

    public String getRuleType()
    {
        return ruleType;
    }

    public void setRuleCode(String ruleCode)
    {
        this.ruleCode = ruleCode;
    }

    public String getRuleCode()
    {
        return ruleCode;
    }

    public void setPointsType(String pointsType)
    {
        this.pointsType = pointsType;
    }

    public String getPointsType()
    {
        return pointsType;
    }

    public void setPointsValue(Integer pointsValue)
    {
        this.pointsValue = pointsValue;
    }

    public Integer getPointsValue()
    {
        return pointsValue;
    }

    public void setRuleCondition(String ruleCondition)
    {
        this.ruleCondition = ruleCondition;
    }

    public String getRuleCondition()
    {
        return ruleCondition;
    }

    public void setMaxPointsPerDay(Integer maxPointsPerDay)
    {
        this.maxPointsPerDay = maxPointsPerDay;
    }

    public Integer getMaxPointsPerDay()
    {
        return maxPointsPerDay;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public String getRuleTypeText()
    {
        return ruleTypeText;
    }

    public void setRuleTypeText(String ruleTypeText)
    {
        this.ruleTypeText = ruleTypeText;
    }

    public String getPointsTypeText()
    {
        return pointsTypeText;
    }

    public void setPointsTypeText(String pointsTypeText)
    {
        this.pointsTypeText = pointsTypeText;
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
            .append("ruleId", getRuleId())
            .append("ruleName", getRuleName())
            .append("ruleType", getRuleType())
            .append("ruleCode", getRuleCode())
            .append("pointsType", getPointsType())
            .append("pointsValue", getPointsValue())
            .append("ruleCondition", getRuleCondition())
            .append("maxPointsPerDay", getMaxPointsPerDay())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
