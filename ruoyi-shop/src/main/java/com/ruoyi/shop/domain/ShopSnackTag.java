package com.ruoyi.shop.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 零食标签对象 shop_snack_tag
 *
 * @author ruoyi
 */
public class ShopSnackTag extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 标签ID */
    private Long tagId;

    /** 标签名称 */
    @Excel(name = "标签名称")
    private String tagName;

    /** 标签编码 */
    @Excel(name = "标签编码")
    private String tagCode;

    /** 标签颜色 */
    @Excel(name = "标签颜色")
    private String tagColor;

    /** 标签类型（feature特性 promo促销 status状态 quality品质） */
    @Excel(name = "标签类型")
    private String tagType;

    /** 图标URL */
    private String iconUrl;

    /** 排序号 */
    @Excel(name = "排序号")
    private Integer sortOrder;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public void setTagId(Long tagId)
    {
        this.tagId = tagId;
    }

    public Long getTagId()
    {
        return tagId;
    }

    public void setTagName(String tagName)
    {
        this.tagName = tagName;
    }

    public String getTagName()
    {
        return tagName;
    }

    public void setTagCode(String tagCode)
    {
        this.tagCode = tagCode;
    }

    public String getTagCode()
    {
        return tagCode;
    }

    public void setTagColor(String tagColor)
    {
        this.tagColor = tagColor;
    }

    public String getTagColor()
    {
        return tagColor;
    }

    public void setTagType(String tagType)
    {
        this.tagType = tagType;
    }

    public String getTagType()
    {
        return tagType;
    }

    public void setIconUrl(String iconUrl)
    {
        this.iconUrl = iconUrl;
    }

    public String getIconUrl()
    {
        return iconUrl;
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
            .append("tagId", getTagId())
            .append("tagName", getTagName())
            .append("tagCode", getTagCode())
            .append("tagColor", getTagColor())
            .append("tagType", getTagType())
            .append("iconUrl", getIconUrl())
            .append("sortOrder", getSortOrder())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
