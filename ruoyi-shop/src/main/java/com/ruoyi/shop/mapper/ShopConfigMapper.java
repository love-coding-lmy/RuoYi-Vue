package com.ruoyi.shop.mapper;

import java.util.List;
import com.ruoyi.shop.domain.ShopConfig;

/**
 * 商城系统参数Mapper接口
 *
 * @author ruoyi
 */
public interface ShopConfigMapper
{
    /**
     * 查询商城系统参数
     *
     * @param configId 商城系统参数主键
     * @return 商城系统参数
     */
    public ShopConfig selectConfigById(Long configId);

    /**
     * 查询商城系统参数列表
     *
     * @param config 商城系统参数
     * @return 商城系统参数集合
     */
    public List<ShopConfig> selectConfigList(ShopConfig config);

    /**
     * 新增商城系统参数
     *
     * @param config 商城系统参数
     * @return 结果
     */
    public int insertConfig(ShopConfig config);

    /**
     * 修改商城系统参数
     *
     * @param config 商城系统参数
     * @return 结果
     */
    public int updateConfig(ShopConfig config);

    /**
     * 删除商城系统参数
     *
     * @param configId 商城系统参数主键
     * @return 结果
     */
    public int deleteConfigById(Long configId);

    /**
     * 批量删除商城系统参数
     *
     * @param configIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteConfigByIds(Long[] configIds);
}
