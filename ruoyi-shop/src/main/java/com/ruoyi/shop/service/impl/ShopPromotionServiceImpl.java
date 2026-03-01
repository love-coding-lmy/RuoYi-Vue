package com.ruoyi.shop.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.shop.domain.ShopPromotion;
import com.ruoyi.shop.mapper.ShopPromotionMapper;
import com.ruoyi.shop.service.IShopPromotionService;

/**
 * 促销活动Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class ShopPromotionServiceImpl implements IShopPromotionService
{
    @Autowired
    private ShopPromotionMapper promotionMapper;

    /**
     * 查询促销活动
     *
     * @param promotionId 促销活动主键
     * @return 促销活动
     */
    @Override
    public ShopPromotion selectPromotionById(Long promotionId)
    {
        return promotionMapper.selectPromotionById(promotionId);
    }

    /**
     * 查询促销活动列表
     *
     * @param promotion 促销活动
     * @return 促销活动
     */
    @Override
    public List<ShopPromotion> selectPromotionList(ShopPromotion promotion)
    {
        return promotionMapper.selectPromotionList(promotion);
    }

    /**
     * 新增促销活动
     *
     * @param promotion 促销活动
     * @return 结果
     */
    @Override
    public int insertPromotion(ShopPromotion promotion)
    {
        return promotionMapper.insertPromotion(promotion);
    }

    /**
     * 修改促销活动
     *
     * @param promotion 促销活动
     * @return 结果
     */
    @Override
    public int updatePromotion(ShopPromotion promotion)
    {
        return promotionMapper.updatePromotion(promotion);
    }

    /**
     * 批量删除促销活动
     *
     * @param promotionIds 需要删除的促销活动主键
     * @return 结果
     */
    @Override
    public int deletePromotionByIds(Long[] promotionIds)
    {
        return promotionMapper.deletePromotionByIds(promotionIds);
    }

    /**
     * 删除促销活动信息
     *
     * @param promotionId 促销活动主键
     * @return 结果
     */
    @Override
    public int deletePromotionById(Long promotionId)
    {
        return promotionMapper.deletePromotionById(promotionId);
    }
}
