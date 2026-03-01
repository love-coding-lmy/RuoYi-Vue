package com.ruoyi.web.controller.shop;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.shop.domain.ShopCoupon;
import com.ruoyi.shop.service.IShopCouponService;

/**
 * 优惠券Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/shop/coupon")
public class ShopCouponController extends BaseController
{
    @Autowired
    private IShopCouponService couponService;

    /**
     * 查询优惠券列表
     */
    @PreAuthorize("@ss.hasPermi('shop:coupon:list')")
    @GetMapping("/list")
    public TableDataInfo list(ShopCoupon coupon)
    {
        startPage();
        List<ShopCoupon> list = couponService.selectCouponList(coupon);
        return getDataTable(list);
    }

    /**
     * 导出优惠券列表
     */
    @PreAuthorize("@ss.hasPermi('shop:coupon:export')")
    @Log(title = "优惠券", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShopCoupon coupon)
    {
        List<ShopCoupon> list = couponService.selectCouponList(coupon);
        ExcelUtil<ShopCoupon> util = new ExcelUtil<ShopCoupon>(ShopCoupon.class);
        util.exportExcel(response, list, "优惠券数据");
    }

    /**
     * 获取优惠券详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:coupon:query')")
    @GetMapping(value = "/{couponId}")
    public AjaxResult getInfo(@PathVariable("couponId") Long couponId)
    {
        return success(couponService.selectCouponById(couponId));
    }

    /**
     * 新增优惠券
     */
    @PreAuthorize("@ss.hasPermi('shop:coupon:add')")
    @Log(title = "优惠券", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShopCoupon coupon)
    {
        return toAjax(couponService.insertCoupon(coupon));
    }

    /**
     * 修改优惠券
     */
    @PreAuthorize("@ss.hasPermi('shop:coupon:edit')")
    @Log(title = "优惠券", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShopCoupon coupon)
    {
        return toAjax(couponService.updateCoupon(coupon));
    }

    /**
     * 删除优惠券
     */
    @PreAuthorize("@ss.hasPermi('shop:coupon:remove')")
    @Log(title = "优惠券", businessType = BusinessType.DELETE)
    @DeleteMapping("/{couponIds}")
    public AjaxResult remove(@PathVariable Long[] couponIds)
    {
        return toAjax(couponService.deleteCouponByIds(couponIds));
    }

    /**
     * 状态切换
     */
    @PreAuthorize("@ss.hasPermi('shop:coupon:edit')")
    @Log(title = "优惠券状态", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody ShopCoupon coupon)
    {
        return toAjax(couponService.updateCoupon(coupon));
    }
}
