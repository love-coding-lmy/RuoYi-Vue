package com.ruoyi.shop.service;

import java.util.List;
import com.ruoyi.shop.domain.ShopPromotion;

/**
 * 促销活动Service接口
 *
 * @author ruoyi
 */
public interface IShopPromotionService
{
    /**
     * 查询促销活动
     *
     * @param promotionId 促销活动主键
     * @return 促销活动
     */
    public ShopPromotion selectPromotionById(Long promotionId);

    /**
     * 查询促销活动列表
     *
     * @param promotion 促销活动
     * @return 促销活动集合
     */
    public List<ShopPromotion> selectPromotionList(ShopPromotion promotion);

    /**
     * 新增促销活动
     *
     * @param promotion 促销活动
     * @return 结果
     */
    public int insertPromotion(ShopPromotion promotion);

    /**
     * 修改促销活动
     *
     * @param promotion 促销活动
     * @return 结果
     */
    public int updatePromotion(ShopPromotion promotion);

    /**
     * 批量删除促销活动
     *
     * @param promotionIds 需要删除的促销活动主键集合
     * @return 结果
     */
    public int deletePromotionByIds(Long[] promotionIds);

    /**
     * 删除促销活动信息
     *
     * @param promotionId 促销活动主键
     * @return 结果
     */
    public int deletePromotionById(Long promotionId);
}
