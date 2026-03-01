package com.ruoyi.shop.service;

import java.util.List;
import com.ruoyi.shop.domain.ShopCategory;

/**
 * 商品分类 服务层
 *
 * @author ruoyi
 */
public interface IShopCategoryService
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
     * 批量删除商品分类
     *
     * @param categoryIds 需要删除的商品分类ID
     * @return 结果
     */
    public int deleteCategoryByIds(Long[] categoryIds);

    /**
     * 删除商品分类信息
     *
     * @param categoryId 商品分类ID
     * @return 结果
     */
    public int deleteCategoryById(Long categoryId);

    /**
     * 构建前端所需要树结构
     *
     * @param categories 分类列表
     * @return 树结构列表
     */
    public List<ShopCategory> buildCategoryTree(List<ShopCategory> categories);
}
