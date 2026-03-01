package com.ruoyi.shop.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商品对象 shop_product
 *
 * @author ruoyi
 */
public class ShopProduct extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 商品ID */
    private Long productId;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String productName;

    /** 商品编码 */
    @Excel(name = "商品编码")
    private String productCode;

    /** 商品副标题 */
    @Excel(name = "商品副标题")
    private String productSubTitle;

    /** 分类ID */
    @Excel(name = "分类ID")
    private Long categoryId;

    /** 品牌ID */
    private Long brandId;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 销售价 */
    @Excel(name = "销售价")
    private BigDecimal price;

    /** 原价 */
    @Excel(name = "原价")
    private BigDecimal originalPrice;

    /** 会员价 */
    @Excel(name = "会员价")
    private BigDecimal memberPrice;

    /** 成本价 */
    @Excel(name = "成本价")
    private BigDecimal costPrice;

    /** 库存数量 */
    @Excel(name = "库存数量")
    private Integer stock;

    /** 库存预警值 */
    @Excel(name = "库存预警值")
    private Integer lowStock;

    /** 销量 */
    @Excel(name = "销量")
    private Integer sales;

    /** 商品重量（克） */
    @Excel(name = "商品重量")
    private BigDecimal weight;

    /** 主图 */
    private String mainImage;

    /** 商品图片（JSON数组） */
    private String images;

    /** 商品详情（HTML） */
    private String detail;

    /** 关键词 */
    private String keywords;

    /** 是否热销（0否 1是） */
    @Excel(name = "是否热销", readConverterExp = "0=否,1=是")
    private String isHot;

    /** 是否新品（0否 1是） */
    @Excel(name = "是否新品", readConverterExp = "0=否,1=是")
    private String isNew;

    /** 是否推荐（0否 1是） */
    @Excel(name = "是否推荐", readConverterExp = "0=否,1=是")
    private String isRecommend;

    /** 是否上架（0下架 1上架） */
    @Excel(name = "是否上架", readConverterExp = "0=下架,1=上架")
    private String isOnSale;

    /** 排序号 */
    @Excel(name = "排序号")
    private Integer sortOrder;

    /** 分类名称（非数据库字段） */
    @Excel(name = "分类名称")
    private String categoryName;

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public Long getProductId()
    {
        return productId;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }

    public String getProductCode()
    {
        return productCode;
    }

    public void setProductSubTitle(String productSubTitle)
    {
        this.productSubTitle = productSubTitle;
    }

    public String getProductSubTitle()
    {
        return productSubTitle;
    }

    public void setCategoryId(Long categoryId)
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId()
    {
        return categoryId;
    }

    public void setBrandId(Long brandId)
    {
        this.brandId = brandId;
    }

    public Long getBrandId()
    {
        return brandId;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setOriginalPrice(BigDecimal originalPrice)
    {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getOriginalPrice()
    {
        return originalPrice;
    }

    public void setMemberPrice(BigDecimal memberPrice)
    {
        this.memberPrice = memberPrice;
    }

    public BigDecimal getMemberPrice()
    {
        return memberPrice;
    }

    public void setCostPrice(BigDecimal costPrice)
    {
        this.costPrice = costPrice;
    }

    public BigDecimal getCostPrice()
    {
        return costPrice;
    }

    public void setStock(Integer stock)
    {
        this.stock = stock;
    }

    public Integer getStock()
    {
        return stock;
    }

    public void setLowStock(Integer lowStock)
    {
        this.lowStock = lowStock;
    }

    public Integer getLowStock()
    {
        return lowStock;
    }

    public void setSales(Integer sales)
    {
        this.sales = sales;
    }

    public Integer getSales()
    {
        return sales;
    }

    public void setWeight(BigDecimal weight)
    {
        this.weight = weight;
    }

    public BigDecimal getWeight()
    {
        return weight;
    }

    public void setMainImage(String mainImage)
    {
        this.mainImage = mainImage;
    }

    public String getMainImage()
    {
        return mainImage;
    }

    public void setImages(String images)
    {
        this.images = images;
    }

    public String getImages()
    {
        return images;
    }

    public void setDetail(String detail)
    {
        this.detail = detail;
    }

    public String getDetail()
    {
        return detail;
    }

    public void setKeywords(String keywords)
    {
        this.keywords = keywords;
    }

    public String getKeywords()
    {
        return keywords;
    }

    public void setIsHot(String isHot)
    {
        this.isHot = isHot;
    }

    public String getIsHot()
    {
        return isHot;
    }

    public void setIsNew(String isNew)
    {
        this.isNew = isNew;
    }

    public String getIsNew()
    {
        return isNew;
    }

    public void setIsRecommend(String isRecommend)
    {
        this.isRecommend = isRecommend;
    }

    public String getIsRecommend()
    {
        return isRecommend;
    }

    public void setIsOnSale(String isOnSale)
    {
        this.isOnSale = isOnSale;
    }

    public String getIsOnSale()
    {
        return isOnSale;
    }

    public void setSortOrder(Integer sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    public Integer getSortOrder()
    {
        return sortOrder;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "ShopProduct{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productSubTitle='" + productSubTitle + '\'' +
                ", categoryId=" + categoryId +
                ", brandId=" + brandId +
                ", unit='" + unit + '\'' +
                ", price=" + price +
                ", originalPrice=" + originalPrice +
                ", memberPrice=" + memberPrice +
                ", costPrice=" + costPrice +
                ", stock=" + stock +
                ", lowStock=" + lowStock +
                ", sales=" + sales +
                ", weight=" + weight +
                ", mainImage='" + mainImage + '\'' +
                ", images='" + images + '\'' +
                ", detail='" + detail + '\'' +
                ", keywords='" + keywords + '\'' +
                ", isHot='" + isHot + '\'' +
                ", isNew='" + isNew + '\'' +
                ", isRecommend='" + isRecommend + '\'' +
                ", isOnSale='" + isOnSale + '\'' +
                ", sortOrder=" + sortOrder +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
