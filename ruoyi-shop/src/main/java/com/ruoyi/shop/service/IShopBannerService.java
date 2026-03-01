package com.ruoyi.shop.service;

import java.util.List;
import com.ruoyi.shop.domain.ShopBanner;

/**
 * 轮播图Service接口
 *
 * @author ruoyi
 */
public interface IShopBannerService
{
    /**
     * 查询轮播图
     *
     * @param bannerId 轮播图主键
     * @return 轮播图
     */
    public ShopBanner selectBannerById(Long bannerId);

    /**
     * 查询轮播图列表
     *
     * @param banner 轮播图
     * @return 轮播图集合
     */
    public List<ShopBanner> selectBannerList(ShopBanner banner);

    /**
     * 新增轮播图
     *
     * @param banner 轮播图
     * @return 结果
     */
    public int insertBanner(ShopBanner banner);

    /**
     * 修改轮播图
     *
     * @param banner 轮播图
     * @return 结果
     */
    public int updateBanner(ShopBanner banner);

    /**
     * 批量删除轮播图
     *
     * @param bannerIds 需要删除的轮播图主键集合
     * @return 结果
     */
    public int deleteBannerByIds(Long[] bannerIds);

    /**
     * 删除轮播图信息
     *
     * @param bannerId 轮播图主键
     * @return 结果
     */
    public int deleteBannerById(Long bannerId);
}
