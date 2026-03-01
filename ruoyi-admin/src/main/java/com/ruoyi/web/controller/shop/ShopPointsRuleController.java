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
import com.ruoyi.shop.domain.ShopPointsRule;
import com.ruoyi.shop.service.IShopPointsRuleService;

/**
 * 积分规则Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/shop/pointsRule")
public class ShopPointsRuleController extends BaseController
{
    @Autowired
    private IShopPointsRuleService pointsRuleService;

    /**
     * 查询积分规则列表
     */
    @PreAuthorize("@ss.hasPermi('shop:pointsRule:list')")
    @GetMapping("/list")
    public TableDataInfo list(ShopPointsRule pointsRule)
    {
        startPage();
        List<ShopPointsRule> list = pointsRuleService.selectPointsRuleList(pointsRule);
        return getDataTable(list);
    }

    /**
     * 导出积分规则列表
     */
    @PreAuthorize("@ss.hasPermi('shop:pointsRule:export')")
    @Log(title = "积分规则", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShopPointsRule pointsRule)
    {
        List<ShopPointsRule> list = pointsRuleService.selectPointsRuleList(pointsRule);
        ExcelUtil<ShopPointsRule> util = new ExcelUtil<ShopPointsRule>(ShopPointsRule.class);
        util.exportExcel(response, list, "积分规则数据");
    }

    /**
     * 获取积分规则详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:pointsRule:query')")
    @GetMapping(value = "/{ruleId}")
    public AjaxResult getInfo(@PathVariable("ruleId") Long ruleId)
    {
        return success(pointsRuleService.selectPointsRuleById(ruleId));
    }

    /**
     * 新增积分规则
     */
    @PreAuthorize("@ss.hasPermi('shop:pointsRule:add')")
    @Log(title = "积分规则", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShopPointsRule pointsRule)
    {
        return toAjax(pointsRuleService.insertPointsRule(pointsRule));
    }

    /**
     * 修改积分规则
     */
    @PreAuthorize("@ss.hasPermi('shop:pointsRule:edit')")
    @Log(title = "积分规则", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShopPointsRule pointsRule)
    {
        return toAjax(pointsRuleService.updatePointsRule(pointsRule));
    }

    /**
     * 删除积分规则
     */
    @PreAuthorize("@ss.hasPermi('shop:pointsRule:remove')")
    @Log(title = "积分规则", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ruleIds}")
    public AjaxResult remove(@PathVariable Long[] ruleIds)
    {
        return toAjax(pointsRuleService.deletePointsRuleByIds(ruleIds));
    }

    /**
     * 状态切换
     */
    @PreAuthorize("@ss.hasPermi('shop:pointsRule:edit')")
    @Log(title = "积分规则状态", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody ShopPointsRule pointsRule)
    {
        return toAjax(pointsRuleService.updatePointsRule(pointsRule));
    }
}
