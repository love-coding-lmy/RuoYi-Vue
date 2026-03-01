package com.ruoyi.shop.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.shop.domain.ShopProduct;
import com.ruoyi.shop.mapper.ShopProductMapper;
import com.ruoyi.shop.service.IShopProductService;

/**
 * 商品Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class ShopProductServiceImpl implements IShopProductService
{
    @Autowired
    private ShopProductMapper productMapper;

    /**
     * 查询商品
     *
     * @param productId 商品主键
     * @return 商品
     */
    @Override
    public ShopProduct selectProductById(Long productId)
    {
        return productMapper.selectProductById(productId);
    }

    /**
     * 查询商品列表
     *
     * @param product 商品
     * @return 商品
     */
    @Override
    public List<ShopProduct> selectProductList(ShopProduct product)
    {
        return productMapper.selectProductList(product);
    }

    /**
     * 新增商品
     *
     * @param product 商品
     * @return 结果
     */
    @Override
    public int insertProduct(ShopProduct product)
    {
        return productMapper.insertProduct(product);
    }

    /**
     * 修改商品
     *
     * @param product 商品
     * @return 结果
     */
    @Override
    public int updateProduct(ShopProduct product)
    {
        return productMapper.updateProduct(product);
    }

    /**
     * 批量删除商品
     *
     * @param productIds 需要删除的商品主键
     * @return 结果
     */
    @Override
    public int deleteProductByIds(Long[] productIds)
    {
        return productMapper.deleteProductByIds(productIds);
    }

    /**
     * 删除商品信息
     *
     * @param productId 商品主键
     * @return 结果
     */
    @Override
    public int deleteProductById(Long productId)
    {
        return productMapper.deleteProductById(productId);
    }

    /**
     * 商品上架
     *
     * @param productId 商品主键
     * @return 结果
     */
    @Override
    public int updateProductOnSale(Long productId)
    {
        return productMapper.updateProductOnSale(productId);
    }

    /**
     * 商品下架
     *
     * @param productId 商品主键
     * @return 结果
     */
    @Override
    public int updateProductOffSale(Long productId)
    {
        return productMapper.updateProductOffSale(productId);
    }

    /**
     * 校验商品编码是否唯一
     *
     * @param product 商品信息
     * @return 结果
     */
    @Override
    public boolean checkProductCodeUnique(ShopProduct product)
    {
        Long productId = StringUtils.isNull(product.getProductId()) ? -1L : product.getProductId();
        ShopProduct info = productMapper.selectProductById(productId);
        if (StringUtils.isNotNull(info) && info.getProductId().longValue() != productId.longValue())
        {
            return false;
        }
        return true;
    }
}
