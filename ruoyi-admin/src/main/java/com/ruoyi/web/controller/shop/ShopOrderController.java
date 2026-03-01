package com.ruoyi.web.controller.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.shop.domain.ShopOrder;
import com.ruoyi.shop.service.IShopOrderService;

/**
 * 订单Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/shop/order")
public class ShopOrderController extends BaseController
{
    @Autowired
    private IShopOrderService orderService;

    /**
     * 查询订单列表
     */
    @PreAuthorize("@ss.hasPermi('shop:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(ShopOrder order)
    {
        startPage();
        List<ShopOrder> list = orderService.selectOrderList(order);
        return getDataTable(list);
    }

    /**
     * 导出订单列表
     */
    @PreAuthorize("@ss.hasPermi('shop:order:export')")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShopOrder order)
    {
        List<ShopOrder> list = orderService.selectOrderList(order);
        ExcelUtil<ShopOrder> util = new ExcelUtil<ShopOrder>(ShopOrder.class);
        util.exportExcel(response, list, "订单数据");
    }

    /**
     * 获取订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:order:query')")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId)
    {
        return success(orderService.selectOrderById(orderId));
    }

    /**
     * 新增订单
     */
    @PreAuthorize("@ss.hasPermi('shop:order:add')")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShopOrder order)
    {
        return toAjax(orderService.insertOrder(order));
    }

    /**
     * 修改订单
     */
    @PreAuthorize("@ss.hasPermi('shop:order:edit')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShopOrder order)
    {
        return toAjax(orderService.updateOrder(order));
    }

    /**
     * 删除订单
     */
    @PreAuthorize("@ss.hasPermi('shop:order:remove')")
    @Log(title = "订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable Long[] orderIds)
    {
        return toAjax(orderService.deleteOrderByIds(orderIds));
    }

    /**
     * 订单发货
     */
    @PreAuthorize("@ss.hasPermi('shop:order:edit')")
    @Log(title = "订单发货", businessType = BusinessType.UPDATE)
    @PutMapping("/ship/{orderId}")
    public AjaxResult ship(@PathVariable Long orderId)
    {
        return toAjax(orderService.updateOrderShip(orderId));
    }

    /**
     * 订单取消
     */
    @PreAuthorize("@ss.hasPermi('shop:order:edit')")
    @Log(title = "订单取消", businessType = BusinessType.UPDATE)
    @PutMapping("/cancel/{orderId}")
    public AjaxResult cancel(@PathVariable Long orderId, @RequestBody String cancelReason)
    {
        return toAjax(orderService.updateOrderCancel(orderId, cancelReason));
    }

    /**
     * 订单完成
     */
    @PreAuthorize("@ss.hasPermi('shop:order:edit')")
    @Log(title = "订单完成", businessType = BusinessType.UPDATE)
    @PutMapping("/complete/{orderId}")
    public AjaxResult complete(@PathVariable Long orderId)
    {
        return toAjax(orderService.updateOrderComplete(orderId));
    }

    /**
     * 获取订单统计概览
     */
    @PreAuthorize("@ss.hasPermi('shop:order:query')")
    @GetMapping("/statistics/overview")
    public AjaxResult getOverview()
    {
        return success(orderService.getOrderOverview());
    }

    /**
     * 获取订单趋势数据
     */
    @PreAuthorize("@ss.hasPermi('shop:order:query')")
    @GetMapping("/statistics/trend")
    public AjaxResult getTrend(@RequestParam(required = false) String startTime,
                               @RequestParam(required = false) String endTime,
                               @RequestParam(required = false, defaultValue = "day") String type)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("type", type);
        return success(orderService.getOrderTrend(params));
    }

    /**
     * 获取订单状态统计
     */
    @PreAuthorize("@ss.hasPermi('shop:order:query')")
    @GetMapping("/statistics/status")
    public AjaxResult getStatusStatistics()
    {
        return success(orderService.getOrderStatusStatistics());
    }

    /**
     * 获取热销商品排行
     */
    @PreAuthorize("@ss.hasPermi('shop:order:query')")
    @GetMapping("/statistics/topProducts")
    public AjaxResult getTopSellingProducts(@RequestParam(required = false, defaultValue = "10") Integer limit)
    {
        return success(orderService.getTopSellingProducts(limit));
    }
}
