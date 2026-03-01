package com.ruoyi.shop.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.shop.domain.ShopConfig;
import com.ruoyi.shop.mapper.ShopConfigMapper;
import com.ruoyi.shop.service.IShopConfigService;

/**
 * 商城系统参数Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class ShopConfigServiceImpl implements IShopConfigService
{
    @Autowired
    private ShopConfigMapper configMapper;

    /**
     * 查询商城系统参数
     *
     * @param configId 商城系统参数主键
     * @return 商城系统参数
     */
    @Override
    public ShopConfig selectConfigById(Long configId)
    {
        ShopConfig config = configMapper.selectConfigById(configId);
        if (config != null)
        {
            // 设置状态描述
            config.setStatusText(getStatusText(config.getStatus()));
        }
        return config;
    }

    /**
     * 查询商城系统参数列表
     *
     * @param config 商城系统参数
     * @return 商城系统参数
     */
    @Override
    public List<ShopConfig> selectConfigList(ShopConfig config)
    {
        List<ShopConfig> list = configMapper.selectConfigList(config);
        for (ShopConfig item : list)
        {
            // 设置状态描述
            item.setStatusText(getStatusText(item.getStatus()));
        }
        return list;
    }

    /**
     * 新增商城系统参数
     *
     * @param config 商城系统参数
     * @return 结果
     */
    @Override
    public int insertConfig(ShopConfig config)
    {
        return configMapper.insertConfig(config);
    }

    /**
     * 修改商城系统参数
     *
     * @param config 商城系统参数
     * @return 结果
     */
    @Override
    public int updateConfig(ShopConfig config)
    {
        return configMapper.updateConfig(config);
    }

    /**
     * 批量删除商城系统参数
     *
     * @param configIds 需要删除的商城系统参数主键
     * @return 结果
     */
    @Override
    public int deleteConfigByIds(Long[] configIds)
    {
        return configMapper.deleteConfigByIds(configIds);
    }

    /**
     * 删除商城系统参数信息
     *
     * @param configId 商城系统参数主键
     * @return 结果
     */
    @Override
    public int deleteConfigById(Long configId)
    {
        return configMapper.deleteConfigById(configId);
    }

    /**
     * 获取状态描述
     *
     * @param status 状态代码
     * @return 状态描述
     */
    private String getStatusText(String status)
    {
        switch (status)
        {
            case "0":
                return "正常";
            case "1":
                return "停用";
            default:
                return "未知";
        }
    }
}
