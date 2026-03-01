package com.ruoyi.shop.service;

import java.util.List;
import com.ruoyi.shop.domain.ShopCoupon;

/**
 * 优惠券Service接口
 *
 * @author ruoyi
 */
public interface IShopCouponService
{
    /**
     * 查询优惠券
     *
     * @param couponId 优惠券主键
     * @return 优惠券
     */
    public ShopCoupon selectCouponById(Long couponId);

    /**
     * 查询优惠券列表
     *
     * @param coupon 优惠券
     * @return 优惠券集合
     */
    public List<ShopCoupon> selectCouponList(ShopCoupon coupon);

    /**
     * 新增优惠券
     *
     * @param coupon 优惠券
     * @return 结果
     */
    public int insertCoupon(ShopCoupon coupon);

    /**
     * 修改优惠券
     *
     * @param coupon 优惠券
     * @return 结果
     */
    public int updateCoupon(ShopCoupon coupon);

    /**
     * 批量删除优惠券
     *
     * @param couponIds 需要删除的优惠券主键集合
     * @return 结果
     */
    public int deleteCouponByIds(Long[] couponIds);

    /**
     * 删除优惠券信息
     *
     * @param couponId 优惠券主键
     * @return 结果
     */
    public int deleteCouponById(Long couponId);
}
