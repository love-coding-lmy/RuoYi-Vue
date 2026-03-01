package com.ruoyi.web.controller.shop;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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
import com.ruoyi.shop.domain.ShopCategory;
import com.ruoyi.shop.service.IShopCategoryService;

/**
 * 商品分类Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/shop/category")
public class ShopCategoryController extends BaseController
{
    @Autowired
    private IShopCategoryService categoryService;

    /**
     * 查询商品分类列表
     */
    @PreAuthorize("@ss.hasPermi('shop:category:list')")
    @GetMapping("/list")
    public TableDataInfo list(ShopCategory category)
    {
        List<ShopCategory> list = categoryService.selectCategoryList(category);
        return getDataTable(list);
    }

    /**
     * 查询商品分类树列表
     */
    @PreAuthorize("@ss.hasPermi('shop:category:list')")
    @GetMapping("/tree")
    public AjaxResult tree(ShopCategory category)
    {
        List<ShopCategory> list = categoryService.selectCategoryList(category);
        return success(categoryService.buildCategoryTree(list));
    }

    /**
     * 导出商品分类列表
     */
    @PreAuthorize("@ss.hasPermi('shop:category:export')")
    @Log(title = "商品分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShopCategory category)
    {
        List<ShopCategory> list = categoryService.selectCategoryList(category);
        ExcelUtil<ShopCategory> util = new ExcelUtil<ShopCategory>(ShopCategory.class);
        util.exportExcel(response, list, "商品分类数据");
    }

    /**
     * 获取商品分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:category:query')")
    @GetMapping(value = "/{categoryId}")
    public AjaxResult getInfo(@PathVariable("categoryId") Long categoryId)
    {
        return success(categoryService.selectCategoryById(categoryId));
    }

    /**
     * 新增商品分类
     */
    @PreAuthorize("@ss.hasPermi('shop:category:add')")
    @Log(title = "商品分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody ShopCategory category)
    {
        return toAjax(categoryService.insertCategory(category));
    }

    /**
     * 修改商品分类
     */
    @PreAuthorize("@ss.hasPermi('shop:category:edit')")
    @Log(title = "商品分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody ShopCategory category)
    {
        return toAjax(categoryService.updateCategory(category));
    }

    /**
     * 删除商品分类
     */
    @PreAuthorize("@ss.hasPermi('shop:category:remove')")
    @Log(title = "商品分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{categoryIds}")
    public AjaxResult remove(@PathVariable Long[] categoryIds)
    {
        return toAjax(categoryService.deleteCategoryByIds(categoryIds));
    }
}
