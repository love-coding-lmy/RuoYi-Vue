package com.ruoyi.shop.service;

import java.util.List;
import com.ruoyi.shop.domain.ShopRecommendItem;

/**
 * 推荐商品Service接口
 *
 * @author ruoyi
 */
public interface IShopRecommendItemService
{
    /**
     * 查询推荐商品
     *
     * @param itemId 推荐商品主键
     * @return 推荐商品
     */
    public ShopRecommendItem selectRecommendItemById(Long itemId);

    /**
     * 查询推荐商品列表
     *
     * @param recommendItem 推荐商品
     * @return 推荐商品集合
     */
    public List<ShopRecommendItem> selectRecommendItemList(ShopRecommendItem recommendItem);

    /**
     * 根据推荐位ID查询推荐商品列表
     *
     * @param positionId 推荐位ID
     * @return 推荐商品集合
     */
    public List<ShopRecommendItem> selectRecommendItemsByPositionId(Long positionId);

    /**
     * 新增推荐商品
     *
     * @param recommendItem 推荐商品
     * @return 结果
     */
    public int insertRecommendItem(ShopRecommendItem recommendItem);

    /**
     * 修改推荐商品
     *
     * @param recommendItem 推荐商品
     * @return 结果
     */
    public int updateRecommendItem(ShopRecommendItem recommendItem);

    /**
     * 批量删除推荐商品
     *
     * @param itemIds 需要删除的推荐商品主键集合
     * @return 结果
     */
    public int deleteRecommendItemByIds(Long[] itemIds);

    /**
     * 删除推荐商品信息
     *
     * @param itemId 推荐商品主键
     * @return 结果
     */
    public int deleteRecommendItemById(Long itemId);
}
