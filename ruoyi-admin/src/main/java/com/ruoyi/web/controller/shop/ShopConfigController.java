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
import com.ruoyi.shop.domain.ShopMemberLevel;
import com.ruoyi.shop.domain.ShopPointsRule;
import com.ruoyi.shop.domain.ShopConfig;
import com.ruoyi.shop.service.IShopMemberLevelService;
import com.ruoyi.shop.service.IShopPointsRuleService;
import com.ruoyi.shop.service.IShopConfigService;

/**
 * 商城配置Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/shop/config")
public class ShopConfigController extends BaseController
{
    @Autowired
    private IShopMemberLevelService memberLevelService;

    @Autowired
    private IShopPointsRuleService pointsRuleService;

    @Autowired
    private IShopConfigService configService;

    // ==================== 会员等级相关接口 ====================

    /**
     * 查询会员等级列表
     */
    @PreAuthorize("@ss.hasPermi('shop:config:level:list')")
    @GetMapping("/level/list")
    public TableDataInfo levelList(ShopMemberLevel memberLevel)
    {
        startPage();
        List<ShopMemberLevel> list = memberLevelService.selectMemberLevelList(memberLevel);
        return getDataTable(list);
    }

    /**
     * 导出会员等级列表
     */
    @PreAuthorize("@ss.hasPermi('shop:config:level:export')")
    @Log(title = "会员等级", businessType = BusinessType.EXPORT)
    @PostMapping("/level/export")
    public void levelExport(HttpServletResponse response, ShopMemberLevel memberLevel)
    {
        List<ShopMemberLevel> list = memberLevelService.selectMemberLevelList(memberLevel);
        ExcelUtil<ShopMemberLevel> util = new ExcelUtil<ShopMemberLevel>(ShopMemberLevel.class);
        util.exportExcel(response, list, "会员等级数据");
    }

    /**
     * 获取会员等级详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:config:level:query')")
    @GetMapping("/level/{id}")
    public AjaxResult getLevelInfo(@PathVariable("id") Long levelId)
    {
        return success(memberLevelService.selectMemberLevelById(levelId));
    }

    /**
     * 新增会员等级
     */
    @PreAuthorize("@ss.hasPermi('shop:config:level:add')")
    @Log(title = "会员等级", businessType = BusinessType.INSERT)
    @PostMapping("/level")
    public AjaxResult addLevel(@RequestBody ShopMemberLevel memberLevel)
    {
        return toAjax(memberLevelService.insertMemberLevel(memberLevel));
    }

    /**
     * 修改会员等级
     */
    @PreAuthorize("@ss.hasPermi('shop:config:level:edit')")
    @Log(title = "会员等级", businessType = BusinessType.UPDATE)
    @PutMapping("/level")
    public AjaxResult editLevel(@RequestBody ShopMemberLevel memberLevel)
    {
        return toAjax(memberLevelService.updateMemberLevel(memberLevel));
    }

    /**
     * 删除会员等级
     */
    @PreAuthorize("@ss.hasPermi('shop:config:level:remove')")
    @Log(title = "会员等级", businessType = BusinessType.DELETE)
    @DeleteMapping("/level/{id}")
    public AjaxResult removeLevel(@PathVariable Long[] id)
    {
        return toAjax(memberLevelService.deleteMemberLevelByIds(id));
    }

    /**
     * 会员等级状态切换
     */
    @PreAuthorize("@ss.hasPermi('shop:config:level:edit')")
    @Log(title = "会员等级状态", businessType = BusinessType.UPDATE)
    @PutMapping("/level/changeStatus")
    public AjaxResult changeLevelStatus(@RequestBody ShopMemberLevel memberLevel)
    {
        return toAjax(memberLevelService.updateMemberLevel(memberLevel));
    }

    // ==================== 积分规则相关接口 ====================

    /**
     * 查询积分规则列表
     */
    @PreAuthorize("@ss.hasPermi('shop:config:points:list')")
    @GetMapping("/points/list")
    public TableDataInfo pointsList(ShopPointsRule pointsRule)
    {
        startPage();
        List<ShopPointsRule> list = pointsRuleService.selectPointsRuleList(pointsRule);
        return getDataTable(list);
    }

    /**
     * 导出积分规则列表
     */
    @PreAuthorize("@ss.hasPermi('shop:config:points:export')")
    @Log(title = "积分规则", businessType = BusinessType.EXPORT)
    @PostMapping("/points/export")
    public void pointsExport(HttpServletResponse response, ShopPointsRule pointsRule)
    {
        List<ShopPointsRule> list = pointsRuleService.selectPointsRuleList(pointsRule);
        ExcelUtil<ShopPointsRule> util = new ExcelUtil<ShopPointsRule>(ShopPointsRule.class);
        util.exportExcel(response, list, "积分规则数据");
    }

    /**
     * 获取积分规则详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:config:points:query')")
    @GetMapping("/points/{id}")
    public AjaxResult getPointsInfo(@PathVariable("id") Long ruleId)
    {
        return success(pointsRuleService.selectPointsRuleById(ruleId));
    }

    /**
     * 新增积分规则
     */
    @PreAuthorize("@ss.hasPermi('shop:config:points:add')")
    @Log(title = "积分规则", businessType = BusinessType.INSERT)
    @PostMapping("/points")
    public AjaxResult addPoints(@RequestBody ShopPointsRule pointsRule)
    {
        return toAjax(pointsRuleService.insertPointsRule(pointsRule));
    }

    /**
     * 修改积分规则
     */
    @PreAuthorize("@ss.hasPermi('shop:config:points:edit')")
    @Log(title = "积分规则", businessType = BusinessType.UPDATE)
    @PutMapping("/points")
    public AjaxResult editPoints(@RequestBody ShopPointsRule pointsRule)
    {
        return toAjax(pointsRuleService.updatePointsRule(pointsRule));
    }

    /**
     * 删除积分规则
     */
    @PreAuthorize("@ss.hasPermi('shop:config:points:remove')")
    @Log(title = "积分规则", businessType = BusinessType.DELETE)
    @DeleteMapping("/points/{id}")
    public AjaxResult removePoints(@PathVariable Long[] id)
    {
        return toAjax(pointsRuleService.deletePointsRuleByIds(id));
    }

    /**
     * 积分规则状态切换
     */
    @PreAuthorize("@ss.hasPermi('shop:config:points:edit')")
    @Log(title = "积分规则状态", businessType = BusinessType.UPDATE)
    @PutMapping("/points/changeStatus")
    public AjaxResult changePointsStatus(@RequestBody ShopPointsRule pointsRule)
    {
        return toAjax(pointsRuleService.updatePointsRule(pointsRule));
    }

    // ==================== 系统参数相关接口 ====================

    /**
     * 查询系统参数列表
     */
    @PreAuthorize("@ss.hasPermi('shop:config:system:list')")
    @GetMapping("/system/list")
    public TableDataInfo systemList(ShopConfig config)
    {
        startPage();
        List<ShopConfig> list = configService.selectConfigList(config);
        return getDataTable(list);
    }

    /**
     * 导出系统参数列表
     */
    @PreAuthorize("@ss.hasPermi('shop:config:system:export')")
    @Log(title = "系统参数", businessType = BusinessType.EXPORT)
    @PostMapping("/system/export")
    public void systemExport(HttpServletResponse response, ShopConfig config)
    {
        List<ShopConfig> list = configService.selectConfigList(config);
        ExcelUtil<ShopConfig> util = new ExcelUtil<ShopConfig>(ShopConfig.class);
        util.exportExcel(response, list, "系统参数数据");
    }

    /**
     * 获取系统参数详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:config:system:query')")
    @GetMapping("/system/{id}")
    public AjaxResult getSystemInfo(@PathVariable("id") Long configId)
    {
        return success(configService.selectConfigById(configId));
    }

    /**
     * 新增系统参数
     */
    @PreAuthorize("@ss.hasPermi('shop:config:system:add')")
    @Log(title = "系统参数", businessType = BusinessType.INSERT)
    @PostMapping("/system")
    public AjaxResult addSystem(@RequestBody ShopConfig config)
    {
        return toAjax(configService.insertConfig(config));
    }

    /**
     * 修改系统参数
     */
    @PreAuthorize("@ss.hasPermi('shop:config:system:edit')")
    @Log(title = "系统参数", businessType = BusinessType.UPDATE)
    @PutMapping("/system")
    public AjaxResult editSystem(@RequestBody ShopConfig config)
    {
        return toAjax(configService.updateConfig(config));
    }

    /**
     * 删除系统参数
     */
    @PreAuthorize("@ss.hasPermi('shop:config:system:remove')")
    @Log(title = "系统参数", businessType = BusinessType.DELETE)
    @DeleteMapping("/system/{id}")
    public AjaxResult removeSystem(@PathVariable Long[] id)
    {
        return toAjax(configService.deleteConfigByIds(id));
    }

    /**
     * 刷新系统参数缓存
     */
    @PreAuthorize("@ss.hasPermi('shop:config:system:edit')")
    @Log(title = "刷新系统参数缓存", businessType = BusinessType.OTHER)
    @GetMapping("/system/refreshCache")
    public AjaxResult refreshSystemCache()
    {
        return success();
    }
}
