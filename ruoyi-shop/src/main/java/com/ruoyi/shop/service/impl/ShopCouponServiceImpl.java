package com.ruoyi.shop.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.shop.domain.ShopCoupon;
import com.ruoyi.shop.mapper.ShopCouponMapper;
import com.ruoyi.shop.service.IShopCouponService;

/**
 * 优惠券Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class ShopCouponServiceImpl implements IShopCouponService
{
    @Autowired
    private ShopCouponMapper couponMapper;

    /**
     * 查询优惠券
     *
     * @param couponId 优惠券主键
     * @return 优惠券
     */
    @Override
    public ShopCoupon selectCouponById(Long couponId)
    {
        ShopCoupon coupon = couponMapper.selectCouponById(couponId);
        if (coupon != null)
        {
            // 设置优惠券类型描述
            coupon.setCouponTypeText(getCouponTypeText(coupon.getCouponType()));
            // 设置有效期类型描述
            coupon.setValidTypeText(getValidTypeText(coupon.getValidType()));
            // 设置状态描述
            coupon.setStatusText(getStatusText(coupon.getStatus()));
            // 计算剩余数量
            int remainCount = coupon.getTotalCount() - coupon.getReceiveCount();
            coupon.setRemainCount(remainCount);
        }
        return coupon;
    }

    /**
     * 查询优惠券列表
     *
     * @param coupon 优惠券
     * @return 优惠券
     */
    @Override
    public List<ShopCoupon> selectCouponList(ShopCoupon coupon)
    {
        List<ShopCoupon> list = couponMapper.selectCouponList(coupon);
        for (ShopCoupon item : list)
        {
            // 设置优惠券类型描述
            item.setCouponTypeText(getCouponTypeText(item.getCouponType()));
            // 设置有效期类型描述
            item.setValidTypeText(getValidTypeText(item.getValidType()));
            // 设置状态描述
            item.setStatusText(getStatusText(item.getStatus()));
            // 计算剩余数量
            int remainCount = item.getTotalCount() - item.getReceiveCount();
            item.setRemainCount(remainCount);
        }
        return list;
    }

    /**
     * 新增优惠券
     *
     * @param coupon 优惠券
     * @return 结果
     */
    @Override
    public int insertCoupon(ShopCoupon coupon)
    {
        return couponMapper.insertCoupon(coupon);
    }

    /**
     * 修改优惠券
     *
     * @param coupon 优惠券
     * @return 结果
     */
    @Override
    public int updateCoupon(ShopCoupon coupon)
    {
        return couponMapper.updateCoupon(coupon);
    }

    /**
     * 批量删除优惠券
     *
     * @param couponIds 需要删除的优惠券主键
     * @return 结果
     */
    @Override
    public int deleteCouponByIds(Long[] couponIds)
    {
        return couponMapper.deleteCouponByIds(couponIds);
    }

    /**
     * 删除优惠券信息
     *
     * @param couponId 优惠券主键
     * @return 结果
     */
    @Override
    public int deleteCouponById(Long couponId)
    {
        return couponMapper.deleteCouponById(couponId);
    }

    /**
     * 获取优惠券类型描述
     *
     * @param type 类型代码
     * @return 类型描述
     */
    private String getCouponTypeText(String type)
    {
        switch (type)
        {
            case "reduction":
                return "满减券";
            case "discount":
                return "折扣券";
            case "gift":
                return "满赠券";
            case "shipping":
                return "运费券";
            default:
                return "未知";
        }
    }

    /**
     * 获取有效期类型描述
     *
     * @param type 类型代码
     * @return 类型描述
     */
    private String getValidTypeText(String type)
    {
        switch (type)
        {
            case "time":
                return "按时间";
            case "days":
                return "按天数";
            default:
                return "未知";
        }
    }

    /**
     * 获取状态描述
     *
     * @param status 状态代码
     * @return 状态描述
     */
    private String getStatusText(String status)
    {
        switch (status)
        {
            case "0":
                return "正常";
            case "1":
                return "停用";
            default:
                return "未知";
        }
    }
}
