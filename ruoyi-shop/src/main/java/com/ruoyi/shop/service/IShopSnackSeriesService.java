package com.ruoyi.shop.service;

import java.util.List;
import com.ruoyi.shop.domain.ShopSnackSeries;

/**
 * 零食系列Service接口
 *
 * @author ruoyi
 */
public interface IShopSnackSeriesService
{
    /**
     * 查询零食系列
     *
     * @param seriesId 零食系列主键
     * @return 零食系列
     */
    public ShopSnackSeries selectSeriesById(Long seriesId);

    /**
     * 查询零食系列列表
     *
     * @param series 零食系列
     * @return 零食系列集合
     */
    public List<ShopSnackSeries> selectSeriesList(ShopSnackSeries series);

    /**
     * 新增零食系列
     *
     * @param series 零食系列
     * @return 结果
     */
    public int insertSeries(ShopSnackSeries series);

    /**
     * 修改零食系列
     *
     * @param series 零食系列
     * @return 结果
     */
    public int updateSeries(ShopSnackSeries series);

    /**
     * 批量删除零食系列
     *
     * @param seriesIds 需要删除的零食系列主键集合
     * @return 结果
     */
    public int deleteSeriesByIds(Long[] seriesIds);

    /**
     * 删除零食系列信息
     *
     * @param seriesId 零食系列主键
     * @return 结果
     */
    public int deleteSeriesById(Long seriesId);
}
