package com.ruoyi.shop.service;

import java.util.List;
import com.ruoyi.shop.domain.ShopRecommendPosition;

/**
 * 推荐位Service接口
 *
 * @author ruoyi
 */
public interface IShopRecommendPositionService
{
    /**
     * 查询推荐位
     *
     * @param positionId 推荐位主键
     * @return 推荐位
     */
    public ShopRecommendPosition selectPositionById(Long positionId);

    /**
     * 查询推荐位列表
     *
     * @param position 推荐位
     * @return 推荐位集合
     */
    public List<ShopRecommendPosition> selectPositionList(ShopRecommendPosition position);

    /**
     * 新增推荐位
     *
     * @param position 推荐位
     * @return 结果
     */
    public int insertPosition(ShopRecommendPosition position);

    /**
     * 修改推荐位
     *
     * @param position 推荐位
     * @return 结果
     */
    public int updatePosition(ShopRecommendPosition position);

    /**
     * 批量删除推荐位
     *
     * @param positionIds 需要删除的推荐位主键集合
     * @return 结果
     */
    public int deletePositionByIds(Long[] positionIds);

    /**
     * 删除推荐位信息
     *
     * @param positionId 推荐位主键
     * @return 结果
     */
    public int deletePositionById(Long positionId);
}
