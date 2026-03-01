package com.ruoyi.shop.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.shop.domain.ShopSnackSeries;
import com.ruoyi.shop.mapper.ShopSnackSeriesMapper;
import com.ruoyi.shop.service.IShopSnackSeriesService;

/**
 * 零食系列Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class ShopSnackSeriesServiceImpl implements IShopSnackSeriesService
{
    @Autowired
    private ShopSnackSeriesMapper seriesMapper;

    /**
     * 查询零食系列
     *
     * @param seriesId 零食系列主键
     * @return 零食系列
     */
    @Override
    public ShopSnackSeries selectSeriesById(Long seriesId)
    {
        return seriesMapper.selectSeriesById(seriesId);
    }

    /**
     * 查询零食系列列表
     *
     * @param series 零食系列
     * @return 零食系列
     */
    @Override
    public List<ShopSnackSeries> selectSeriesList(ShopSnackSeries series)
    {
        return seriesMapper.selectSeriesList(series);
    }

    /**
     * 新增零食系列
     *
     * @param series 零食系列
     * @return 结果
     */
    @Override
    public int insertSeries(ShopSnackSeries series)
    {
        return seriesMapper.insertSeries(series);
    }

    /**
     * 修改零食系列
     *
     * @param series 零食系列
     * @return 结果
     */
    @Override
    public int updateSeries(ShopSnackSeries series)
    {
        return seriesMapper.updateSeries(series);
    }

    /**
     * 批量删除零食系列
     *
     * @param seriesIds 需要删除的零食系列主键
     * @return 结果
     */
    @Override
    public int deleteSeriesByIds(Long[] seriesIds)
    {
        return seriesMapper.deleteSeriesByIds(seriesIds);
    }

    /**
     * 删除零食系列信息
     *
     * @param seriesId 零食系列主键
     * @return 结果
     */
    @Override
    public int deleteSeriesById(Long seriesId)
    {
        return seriesMapper.deleteSeriesById(seriesId);
    }
}
