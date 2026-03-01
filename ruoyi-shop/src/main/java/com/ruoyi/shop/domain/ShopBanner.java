package com.ruoyi.shop.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 轮播图对象 shop_banner
 *
 * @author ruoyi
 */
public class ShopBanner extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 轮播图ID */
    private Long bannerId;

    /** 轮播图标题 */
    @Excel(name = "轮播图标题")
    private String bannerTitle;

    /** 轮播图片 */
    private String bannerImage;

    /** 链接地址 */
    @Excel(name = "链接地址")
    private String linkUrl;

    /** 打开方式（_self当前页 _blank新页） */
    @Excel(name = "打开方式", readConverterExp = "_self=当前页,_blank=新页")
    private String target;

    /** 排序号 */
    @Excel(name = "排序号")
    private Integer sortOrder;

    /** 显示状态（0显示 1隐藏） */
    @Excel(name = "显示状态", readConverterExp = "0=显示,1=隐藏")
    private String showStatus;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    public void setBannerId(Long bannerId)
    {
        this.bannerId = bannerId;
    }

    public Long getBannerId()
    {
        return bannerId;
    }

    public void setBannerTitle(String bannerTitle)
    {
        this.bannerTitle = bannerTitle;
    }

    public String getBannerTitle()
    {
        return bannerTitle;
    }

    public void setBannerImage(String bannerImage)
    {
        this.bannerImage = bannerImage;
    }

    public String getBannerImage()
    {
        return bannerImage;
    }

    public void setLinkUrl(String linkUrl)
    {
        this.linkUrl = linkUrl;
    }

    public String getLinkUrl()
    {
        return linkUrl;
    }

    public void setTarget(String target)
    {
        this.target = target;
    }

    public String getTarget()
    {
        return target;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("bannerId", getBannerId())
            .append("bannerTitle", getBannerTitle())
            .append("bannerImage", getBannerImage())
            .append("linkUrl", getLinkUrl())
            .append("target", getTarget())
            .append("sortOrder", getSortOrder())
            .append("showStatus", getShowStatus())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
