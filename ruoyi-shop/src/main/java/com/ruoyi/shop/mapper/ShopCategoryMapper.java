package com.ruoyi.shop.mapper;

import java.util.List;
import com.ruoyi.shop.domain.ShopCategory;

/**
 * 商品分类 数据层
 *
 * @author ruoyi
 */
public interface ShopCategoryMapper
{
    /**
     * 查询商品分类信息
     *
     * @param categoryId 商品分类ID
     * @return 商品分类信息
     */
    public ShopCategory selectCategoryById(Long categoryId);

    /**
     * 查询商品分类列表
     *
     * @param category 商品分类信息
     * @return 商品分类集合
     */
    public List<ShopCategory> selectCategoryList(ShopCategory category);

    /**
     * 新增商品分类
     *
     * @param category 商品分类信息
     * @return 结果
     */
    public int insertCategory(ShopCategory category);

    /**
     * 修改商品分类
     *
     * @param category 商品分类信息
     * @return 结果
     */
    public int updateCategory(ShopCategory category);

    /**
     * 删除商品分类
     *
     * @param categoryId 商品分类ID
     * @return 结果
     */
    public int deleteCategoryById(Long categoryId);

    /**
     * 批量删除商品分类
     *
     * @param categoryIds 需要删除的商品分类ID
     * @return 结果
     */
    public int deleteCategoryByIds(Long[] categoryIds);

    /**
     * 查询子分类数量
     *
     * @param parentId 父分类ID
     * @return 结果
     */
    public int selectChildrenCountByParentId(Long parentId);

    /**
     * 查询分类下的商品数量
     *
     * @param categoryId 分类ID
     * @return 结果
     */
    public int selectProductCountByCategoryId(Long categoryId);
}
