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
import com.ruoyi.shop.service.IShopMemberLevelService;

/**
 * 会员等级Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/shop/memberLevel")
public class ShopMemberLevelController extends BaseController
{
    @Autowired
    private IShopMemberLevelService memberLevelService;

    /**
     * 查询会员等级列表
     */
    @PreAuthorize("@ss.hasPermi('shop:memberLevel:list')")
    @GetMapping("/list")
    public TableDataInfo list(ShopMemberLevel memberLevel)
    {
        startPage();
        List<ShopMemberLevel> list = memberLevelService.selectMemberLevelList(memberLevel);
        return getDataTable(list);
    }

    /**
     * 导出会员等级列表
     */
    @PreAuthorize("@ss.hasPermi('shop:memberLevel:export')")
    @Log(title = "会员等级", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShopMemberLevel memberLevel)
    {
        List<ShopMemberLevel> list = memberLevelService.selectMemberLevelList(memberLevel);
        ExcelUtil<ShopMemberLevel> util = new ExcelUtil<ShopMemberLevel>(ShopMemberLevel.class);
        util.exportExcel(response, list, "会员等级数据");
    }

    /**
     * 获取会员等级详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:memberLevel:query')")
    @GetMapping(value = "/{levelId}")
    public AjaxResult getInfo(@PathVariable("levelId") Long levelId)
    {
        return success(memberLevelService.selectMemberLevelById(levelId));
    }

    /**
     * 新增会员等级
     */
    @PreAuthorize("@ss.hasPermi('shop:memberLevel:add')")
    @Log(title = "会员等级", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShopMemberLevel memberLevel)
    {
        return toAjax(memberLevelService.insertMemberLevel(memberLevel));
    }

    /**
     * 修改会员等级
     */
    @PreAuthorize("@ss.hasPermi('shop:memberLevel:edit')")
    @Log(title = "会员等级", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShopMemberLevel memberLevel)
    {
        return toAjax(memberLevelService.updateMemberLevel(memberLevel));
    }

    /**
     * 删除会员等级
     */
    @PreAuthorize("@ss.hasPermi('shop:memberLevel:remove')")
    @Log(title = "会员等级", businessType = BusinessType.DELETE)
    @DeleteMapping("/{levelIds}")
    public AjaxResult remove(@PathVariable Long[] levelIds)
    {
        return toAjax(memberLevelService.deleteMemberLevelByIds(levelIds));
    }

    /**
     * 状态切换
     */
    @PreAuthorize("@ss.hasPermi('shop:memberLevel:edit')")
    @Log(title = "会员等级状态", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody ShopMemberLevel memberLevel)
    {
        return toAjax(memberLevelService.updateMemberLevel(memberLevel));
    }
}
