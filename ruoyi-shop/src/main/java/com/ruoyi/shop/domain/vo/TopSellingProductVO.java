package com.ruoyi.shop.domain.vo;

import java.math.BigDecimal;

/**
 * 热销商品VO
 *
 * @author ruoyi
 */
public class TopSellingProductVO
{
    /** 商品ID */
    private Long productId;

    /** 商品名称 */
    private String productName;

    /** 商品图片 */
    private String productImage;

    /** 销售数量 */
    private Integer salesCount;

    /** 销售金额 */
    private BigDecimal salesAmount;

    /** 排名 */
    private Integer rank;

    public Long getProductId()
    {
        return productId;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getProductImage()
    {
        return productImage;
    }

    public void setProductImage(String productImage)
    {
        this.productImage = productImage;
    }

    public Integer getSalesCount()
    {
        return salesCount;
    }

    public void setSalesCount(Integer salesCount)
    {
        this.salesCount = salesCount;
    }

    public BigDecimal getSalesAmount()
    {
        return salesAmount;
    }

    public void setSalesAmount(BigDecimal salesAmount)
    {
        this.salesAmount = salesAmount;
    }

    public Integer getRank()
    {
        return rank;
    }

    public void setRank(Integer rank)
    {
        this.rank = rank;
    }
}
