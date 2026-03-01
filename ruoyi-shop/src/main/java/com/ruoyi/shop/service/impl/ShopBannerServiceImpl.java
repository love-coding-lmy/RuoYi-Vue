package com.ruoyi.shop.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.shop.domain.ShopBanner;
import com.ruoyi.shop.mapper.ShopBannerMapper;
import com.ruoyi.shop.service.IShopBannerService;

/**
 * 轮播图Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class ShopBannerServiceImpl implements IShopBannerService
{
    @Autowired
    private ShopBannerMapper bannerMapper;

    /**
     * 查询轮播图
     *
     * @param bannerId 轮播图主键
     * @return 轮播图
     */
    @Override
    public ShopBanner selectBannerById(Long bannerId)
    {
        return bannerMapper.selectBannerById(bannerId);
    }

    /**
     * 查询轮播图列表
     *
     * @param banner 轮播图
     * @return 轮播图
     */
    @Override
    public List<ShopBanner> selectBannerList(ShopBanner banner)
    {
        return bannerMapper.selectBannerList(banner);
    }

    /**
     * 新增轮播图
     *
     * @param banner 轮播图
     * @return 结果
     */
    @Override
    public int insertBanner(ShopBanner banner)
    {
        return bannerMapper.insertBanner(banner);
    }

    /**
     * 修改轮播图
     *
     * @param banner 轮播图
     * @return 结果
     */
    @Override
    public int updateBanner(ShopBanner banner)
    {
        return bannerMapper.updateBanner(banner);
    }

    /**
     * 批量删除轮播图
     *
     * @param bannerIds 需要删除的轮播图主键
     * @return 结果
     */
    @Override
    public int deleteBannerByIds(Long[] bannerIds)
    {
        return bannerMapper.deleteBannerByIds(bannerIds);
    }

    /**
     * 删除轮播图信息
     *
     * @param bannerId 轮播图主键
     * @return 结果
     */
    @Override
    public int deleteBannerById(Long bannerId)
    {
        return bannerMapper.deleteBannerById(bannerId);
    }
}
