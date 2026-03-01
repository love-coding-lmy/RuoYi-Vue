package com.ruoyi.shop.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.shop.domain.ShopRecommendPosition;
import com.ruoyi.shop.mapper.ShopRecommendPositionMapper;
import com.ruoyi.shop.service.IShopRecommendPositionService;

/**
 * 推荐位Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class ShopRecommendPositionServiceImpl implements IShopRecommendPositionService
{
    @Autowired
    private ShopRecommendPositionMapper positionMapper;

    /**
     * 查询推荐位
     *
     * @param positionId 推荐位主键
     * @return 推荐位
     */
    @Override
    public ShopRecommendPosition selectPositionById(Long positionId)
    {
        return positionMapper.selectPositionById(positionId);
    }

    /**
     * 查询推荐位列表
     *
     * @param position 推荐位
     * @return 推荐位
     */
    @Override
    public List<ShopRecommendPosition> selectPositionList(ShopRecommendPosition position)
    {
        return positionMapper.selectPositionList(position);
    }

    /**
     * 新增推荐位
     *
     * @param position 推荐位
     * @return 结果
     */
    @Override
    public int insertPosition(ShopRecommendPosition position)
    {
        return positionMapper.insertPosition(position);
    }

    /**
     * 修改推荐位
     *
     * @param position 推荐位
     * @return 结果
     */
    @Override
    public int updatePosition(ShopRecommendPosition position)
    {
        return positionMapper.updatePosition(position);
    }

    /**
     * 批量删除推荐位
     *
     * @param positionIds 需要删除的推荐位主键
     * @return 结果
     */
    @Override
    public int deletePositionByIds(Long[] positionIds)
    {
        return positionMapper.deletePositionByIds(positionIds);
    }

    /**
     * 删除推荐位信息
     *
     * @param positionId 推荐位主键
     * @return 结果
     */
    @Override
    public int deletePositionById(Long positionId)
    {
        return positionMapper.deletePositionById(positionId);
    }
}
