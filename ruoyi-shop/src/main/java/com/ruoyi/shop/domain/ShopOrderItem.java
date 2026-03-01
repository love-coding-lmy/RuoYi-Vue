package com.ruoyi.shop.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单明细对象 shop_order_item
 *
 * @author ruoyi
 */
public class ShopOrderItem
{
    private static final long serialVersionUID = 1L;

    /** 明细ID */
    private Long itemId;

    /** 订单ID */
    private Long orderId;

    /** 商品ID */
    private Long productId;

    /** 商品名称 */
    private String productName;

    /** 商品图片 */
    private String productImage;

    /** 商品单价 */
    private BigDecimal productPrice;

    /** 购买数量 */
    private Integer quantity;

    /** 小计金额 */
    private BigDecimal subtotal;

    /** 创建时间 */
    private Date createTime;

    public void setItemId(Long itemId)
    {
        this.itemId = itemId;
    }

    public Long getItemId()
    {
        return itemId;
    }

    public void setOrderId(Long orderId)
    {
        this.orderId = orderId;
    }

    public Long getOrderId()
    {
        return orderId;
    }

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

    public void setProductImage(String productImage)
    {
        this.productImage = productImage;
    }

    public String getProductImage()
    {
        return productImage;
    }

    public void setProductPrice(BigDecimal productPrice)
    {
        this.productPrice = productPrice;
    }

    public BigDecimal getProductPrice()
    {
        return productPrice;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }

    public Integer getQuantity()
    {
        return quantity;
    }

    public void setSubtotal(BigDecimal subtotal)
    {
        this.subtotal = subtotal;
    }

    public BigDecimal getSubtotal()
    {
        return subtotal;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
}
