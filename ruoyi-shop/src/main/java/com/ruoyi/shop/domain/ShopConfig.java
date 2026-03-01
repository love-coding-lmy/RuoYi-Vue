package com.ruoyi.shop.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商城系统参数对象 shop_config
 *
 * @author ruoyi
 */
public class ShopConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 参数ID */
    private Long configId;

    /** 参数名称 */
    @Excel(name = "参数名称")
    private String configName;

    /** 参数键名 */
    @Excel(name = "参数键名")
    private String configKey;

    /** 参数键值 */
    @Excel(name = "参数键值")
    private String configValue;

    /** 参数类型 */
    @Excel(name = "参数类型", readConverterExp = "string=字符串, number=数字, boolean=布尔")
    private String configType;

    /** 分组名称 */
    @Excel(name = "分组名称")
    private String groupName;

    /** 参数描述 */
    @Excel(name = "参数描述")
    private String description;

    /** 是否前端可配置 */
    @Excel(name = "前端可配置", readConverterExp = "0=否, 1=是")
    private Integer isFrontend;

    /** 状态 */
    @Excel(name = "状态", readConverterExp = "0=正常, 1=停用")
    private String status;

    /** 状态描述 */
    private String statusText;

    public void setConfigId(Long configId)
    {
        this.configId = configId;
    }

    public Long getConfigId()
    {
        return configId;
    }

    public void setConfigName(String configName)
    {
        this.configName = configName;
    }

    public String getConfigName()
    {
        return configName;
    }

    public void setConfigKey(String configKey)
    {
        this.configKey = configKey;
    }

    public String getConfigKey()
    {
        return configKey;
    }

    public void setConfigValue(String configValue)
    {
        this.configValue = configValue;
    }

    public String getConfigValue()
    {
        return configValue;
    }

    public void setConfigType(String configType)
    {
        this.configType = configType;
    }

    public String getConfigType()
    {
        return configType;
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public String getGroupName()
    {
        return groupName;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setIsFrontend(Integer isFrontend)
    {
        this.isFrontend = isFrontend;
    }

    public Integer getIsFrontend()
    {
        return isFrontend;
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
            .append("configId", getConfigId())
            .append("configName", getConfigName())
            .append("configKey", getConfigKey())
            .append("configValue", getConfigValue())
            .append("configType", getConfigType())
            .append("groupName", getGroupName())
            .append("description", getDescription())
            .append("isFrontend", getIsFrontend())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
