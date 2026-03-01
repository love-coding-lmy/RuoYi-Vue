package com.ruoyi.shop.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.shop.mapper.ShopRecommendItemMapper;
import com.ruoyi.shop.domain.ShopRecommendItem;
import com.ruoyi.shop.service.IShopRecommendItemService;

/**
 * 推荐商品Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class ShopRecommendItemServiceImpl implements IShopRecommendItemService
{
    @Autowired
    private ShopRecommendItemMapper recommendItemMapper;

    /**
     * 查询推荐商品
     *
     * @param itemId 推荐商品主键
     * @return 推荐商品
     */
    @Override
    public ShopRecommendItem selectRecommendItemById(Long itemId)
    {
        return recommendItemMapper.selectRecommendItemById(itemId);
    }

    /**
     * 查询推荐商品列表
     *
     * @param recommendItem 推荐商品
     * @return 推荐商品
     */
    @Override
    public List<ShopRecommendItem> selectRecommendItemList(ShopRecommendItem recommendItem)
    {
        return recommendItemMapper.selectRecommendItemList(recommendItem);
    }

    /**
     * 根据推荐位ID查询推荐商品列表
     *
     * @param positionId 推荐位ID
     * @return 推荐商品集合
     */
    @Override
    public List<ShopRecommendItem> selectRecommendItemsByPositionId(Long positionId)
    {
        return recommendItemMapper.selectRecommendItemsByPositionId(positionId);
    }

    /**
     * 新增推荐商品
     *
     * @param recommendItem 推荐商品
     * @return 结果
     */
    @Override
    public int insertRecommendItem(ShopRecommendItem recommendItem)
    {
        return recommendItemMapper.insertRecommendItem(recommendItem);
    }

    /**
     * 修改推荐商品
     *
     * @param recommendItem 推荐商品
     * @return 结果
     */
    @Override
    public int updateRecommendItem(ShopRecommendItem recommendItem)
    {
        return recommendItemMapper.updateRecommendItem(recommendItem);
    }

    /**
     * 批量删除推荐商品
     *
     * @param itemIds 需要删除的推荐商品主键
     * @return 结果
     */
    @Override
    public int deleteRecommendItemByIds(Long[] itemIds)
    {
        return recommendItemMapper.deleteRecommendItemByIds(itemIds);
    }

    /**
     * 删除推荐商品信息
     *
     * @param itemId 推荐商品主键
     * @return 结果
     */
    @Override
    public int deleteRecommendItemById(Long itemId)
    {
        return recommendItemMapper.deleteRecommendItemById(itemId);
    }
}
