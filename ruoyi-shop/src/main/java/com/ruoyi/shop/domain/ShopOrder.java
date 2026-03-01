package com.ruoyi.shop.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ruoyi.common.annotation.Excel;

/**
 * 订单对象 shop_order
 *
 * @author ruoyi
 */
public class ShopOrder
{
    private static final long serialVersionUID = 1L;

    /** 请求参数 */
    private Map<String, Object> params;

    /** 订单ID */
    private Long orderId;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderNo;

    /** 用户ID */
    private Long userId;

    /** 用户名 */
    @Excel(name = "用户名")
    private String username;

    /** 订单状态 */
    @Excel(name = "订单状态", readConverterExp = "pending=待付款,paid=已付款,shipped=已发货,completed=已完成,cancelled=已取消")
    private String orderStatus;

    /** 商品数量 */
    @Excel(name = "商品数量")
    private Integer productCount;

    /** 商品总额 */
    @Excel(name = "商品总额")
    private BigDecimal productAmount;

    /** 运费 */
    @Excel(name = "运费")
    private BigDecimal freightAmount;

    /** 优惠金额 */
    @Excel(name = "优惠金额")
    private BigDecimal discountAmount;

    /** 积分抵扣金额 */
    @Excel(name = "积分抵扣")
    private BigDecimal pointsAmount;

    /** 应付金额 */
    @Excel(name = "应付金额")
    private BigDecimal payAmount;

    /** 使用积分 */
    @Excel(name = "使用积分")
    private Integer usePoints;

    /** 获得积分 */
    @Excel(name = "获得积分")
    private Integer earnPoints;

    /** 支付方式 */
    @Excel(name = "支付方式", readConverterExp = "wechat=微信,alipay=支付宝,balance=余额")
    private String payType;

    /** 支付时间 */
    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    /** 发货时间 */
    @Excel(name = "发货时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date shipTime;

    /** 收货时间 */
    @Excel(name = "收货时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date receiveTime;

    /** 完成时间 */
    @Excel(name = "完成时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    /** 取消时间 */
    @Excel(name = "取消时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date cancelTime;

    /** 收货人姓名 */
    @Excel(name = "收货人")
    private String receiverName;

    /** 收货人电话 */
    @Excel(name = "联系电话")
    private String receiverPhone;

    /** 收货省份 */
    private String receiverProvince;

    /** 收货城市 */
    private String receiverCity;

    /** 收货区县 */
    private String receiverDistrict;

    /** 详细地址 */
    private String receiverAddress;

    /** 订单备注 */
    @Excel(name = "订单备注")
    private String orderRemark;

    /** 取消原因 */
    private String cancelReason;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /** 订单明细列表 */
    private List<ShopOrderItem> orderItems;

    /** 完整收货地址（非数据库字段） */
    @Excel(name = "收货地址")
    private String fullAddress;

    /** 订单状态描述（非数据库字段） */
    @Excel(name = "订单状态")
    private String orderStatusText;

    public void setOrderId(Long orderId)
    {
        this.orderId = orderId;
    }

    public Long getOrderId()
    {
        return orderId;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }

    public void setOrderStatus(String orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus()
    {
        return orderStatus;
    }

    public void setProductCount(Integer productCount)
    {
        this.productCount = productCount;
    }

    public Integer getProductCount()
    {
        return productCount;
    }

    public void setProductAmount(BigDecimal productAmount)
    {
        this.productAmount = productAmount;
    }

    public BigDecimal getProductAmount()
    {
        return productAmount;
    }

    public void setFreightAmount(BigDecimal freightAmount)
    {
        this.freightAmount = freightAmount;
    }

    public BigDecimal getFreightAmount()
    {
        return freightAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount)
    {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getDiscountAmount()
    {
        return discountAmount;
    }

    public void setPointsAmount(BigDecimal pointsAmount)
    {
        this.pointsAmount = pointsAmount;
    }

    public BigDecimal getPointsAmount()
    {
        return pointsAmount;
    }

    public void setPayAmount(BigDecimal payAmount)
    {
        this.payAmount = payAmount;
    }

    public BigDecimal getPayAmount()
    {
        return payAmount;
    }

    public void setUsePoints(Integer usePoints)
    {
        this.usePoints = usePoints;
    }

    public Integer getUsePoints()
    {
        return usePoints;
    }

    public void setEarnPoints(Integer earnPoints)
    {
        this.earnPoints = earnPoints;
    }

    public Integer getEarnPoints()
    {
        return earnPoints;
    }

    public void setPayType(String payType)
    {
        this.payType = payType;
    }

    public String getPayType()
    {
        return payType;
    }

    public void setPayTime(Date payTime)
    {
        this.payTime = payTime;
    }

    public Date getPayTime()
    {
        return payTime;
    }

    public void setShipTime(Date shipTime)
    {
        this.shipTime = shipTime;
    }

    public Date getShipTime()
    {
        return shipTime;
    }

    public void setReceiveTime(Date receiveTime)
    {
        this.receiveTime = receiveTime;
    }

    public Date getReceiveTime()
    {
        return receiveTime;
    }

    public void setFinishTime(Date finishTime)
    {
        this.finishTime = finishTime;
    }

    public Date getFinishTime()
    {
        return finishTime;
    }

    public void setCancelTime(Date cancelTime)
    {
        this.cancelTime = cancelTime;
    }

    public Date getCancelTime()
    {
        return cancelTime;
    }

    public void setReceiverName(String receiverName)
    {
        this.receiverName = receiverName;
    }

    public String getReceiverName()
    {
        return receiverName;
    }

    public void setReceiverPhone(String receiverPhone)
    {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverPhone()
    {
        return receiverPhone;
    }

    public void setReceiverProvince(String receiverProvince)
    {
        this.receiverProvince = receiverProvince;
    }

    public String getReceiverProvince()
    {
        return receiverProvince;
    }

    public void setReceiverCity(String receiverCity)
    {
        this.receiverCity = receiverCity;
    }

    public String getReceiverCity()
    {
        return receiverCity;
    }

    public void setReceiverDistrict(String receiverDistrict)
    {
        this.receiverDistrict = receiverDistrict;
    }

    public String getReceiverDistrict()
    {
        return receiverDistrict;
    }

    public void setReceiverAddress(String receiverAddress)
    {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverAddress()
    {
        return receiverAddress;
    }

    public void setOrderRemark(String orderRemark)
    {
        this.orderRemark = orderRemark;
    }

    public String getOrderRemark()
    {
        return orderRemark;
    }

    public void setCancelReason(String cancelReason)
    {
        this.cancelReason = cancelReason;
    }

    public String getCancelReason()
    {
        return cancelReason;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public List<ShopOrderItem> getOrderItems()
    {
        return orderItems;
    }

    public void setOrderItems(List<ShopOrderItem> orderItems)
    {
        this.orderItems = orderItems;
    }

    public String getFullAddress()
    {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress)
    {
        this.fullAddress = fullAddress;
    }

    public String getOrderStatusText()
    {
        return orderStatusText;
    }

    public void setOrderStatusText(String orderStatusText)
    {
        this.orderStatusText = orderStatusText;
    }

    public Map<String, Object> getParams()
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        return params;
    }

    public void setParams(Map<String, Object> params)
    {
        this.params = params;
    }
}
