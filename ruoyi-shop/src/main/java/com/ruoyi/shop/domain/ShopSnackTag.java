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

    /** 标签颜色 */
    @Excel(name = "标签颜色")
    private String tagColor;

    /** 标签类型（1新品 2热销 3推荐 4限时） */
    @Excel(name = "标签类型", readConverterExp = "1=新品,2=热销,3=推荐,4=限时")
    private String tagType;

    /** 排序号 */
    @Excel(name = "排序号")
    private Integer sortOrder;

    /** 显示状态（0显示 1隐藏） */
    @Excel(name = "显示状态", readConverterExp = "0=显示,1=隐藏")
    private String showStatus;

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
            .append("tagId", getTagId())
            .append("tagName", getTagName())
            .append("tagColor", getTagColor())
            .append("tagType", getTagType())
            .append("sortOrder", getSortOrder())
            .append("showStatus", getShowStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
