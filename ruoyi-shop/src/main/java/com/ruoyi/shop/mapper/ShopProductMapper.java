package com.ruoyi.shop.mapper;

import java.util.List;
import com.ruoyi.shop.domain.ShopProduct;

/**
 * 商品Mapper接口
 *
 * @author ruoyi
 */
public interface ShopProductMapper
{
    /**
     * 查询商品
     *
     * @param productId 商品主键
     * @return 商品
     */
    public ShopProduct selectProductById(Long productId);

    /**
     * 查询商品列表
     *
     * @param product 商品
     * @return 商品集合
     */
    public List<ShopProduct> selectProductList(ShopProduct product);

    /**
     * 新增商品
     *
     * @param product 商品
     * @return 结果
     */
    public int insertProduct(ShopProduct product);

    /**
     * 修改商品
     *
     * @param product 商品
     * @return 结果
     */
    public int updateProduct(ShopProduct product);

    /**
     * 删除商品
     *
     * @param productId 商品主键
     * @return 结果
     */
    public int deleteProductById(Long productId);

    /**
     * 批量删除商品
     *
     * @param productIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProductByIds(Long[] productIds);

    /**
     * 商品上架
     *
     * @param productId 商品主键
     * @return 结果
     */
    public int updateProductOnSale(Long productId);

    /**
     * 商品下架
     *
     * @param productId 商品主键
     * @return 结果
     */
    public int updateProductOffSale(Long productId);
}
