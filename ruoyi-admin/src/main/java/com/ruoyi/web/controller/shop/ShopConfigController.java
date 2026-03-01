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
import com.ruoyi.shop.domain.ShopConfig;
import com.ruoyi.shop.service.IShopConfigService;

/**
 * 商城系统参数Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/shop/config")
public class ShopConfigController extends BaseController
{
    @Autowired
    private IShopConfigService configService;

    /**
     * 查询商城系统参数列表
     */
    @PreAuthorize("@ss.hasPermi('shop:config:list')")
    @GetMapping("/list")
    public TableDataInfo list(ShopConfig config)
    {
        startPage();
        List<ShopConfig> list = configService.selectConfigList(config);
        return getDataTable(list);
    }

    /**
     * 导出商城系统参数列表
     */
    @PreAuthorize("@ss.hasPermi('shop:config:export')")
    @Log(title = "商城系统参数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShopConfig config)
    {
        List<ShopConfig> list = configService.selectConfigList(config);
        ExcelUtil<ShopConfig> util = new ExcelUtil<ShopConfig>(ShopConfig.class);
        util.exportExcel(response, list, "商城系统参数数据");
    }

    /**
     * 获取商城系统参数详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:config:query')")
    @GetMapping(value = "/{configId}")
    public AjaxResult getInfo(@PathVariable("configId") Long configId)
    {
        return success(configService.selectConfigById(configId));
    }

    /**
     * 新增商城系统参数
     */
    @PreAuthorize("@ss.hasPermi('shop:config:add')")
    @Log(title = "商城系统参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShopConfig config)
    {
        return toAjax(configService.insertConfig(config));
    }

    /**
     * 修改商城系统参数
     */
    @PreAuthorize("@ss.hasPermi('shop:config:edit')")
    @Log(title = "商城系统参数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShopConfig config)
    {
        return toAjax(configService.updateConfig(config));
    }

    /**
     * 删除商城系统参数
     */
    @PreAuthorize("@ss.hasPermi('shop:config:remove')")
    @Log(title = "商城系统参数", businessType = BusinessType.DELETE)
    @DeleteMapping("/{configIds}")
    public AjaxResult remove(@PathVariable Long[] configIds)
    {
        return toAjax(configService.deleteConfigByIds(configIds));
    }

    /**
     * 状态切换
     */
    @PreAuthorize("@ss.hasPermi('shop:config:edit')")
    @Log(title = "商城系统参数状态", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody ShopConfig config)
    {
        return toAjax(configService.updateConfig(config));
    }
}
