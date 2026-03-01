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
import com.ruoyi.shop.domain.ShopProduct;
import com.ruoyi.shop.service.IShopProductService;

/**
 * 商品Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/shop/product")
public class ShopProductController extends BaseController
{
    @Autowired
    private IShopProductService productService;

    /**
     * 查询商品列表
     */
    @PreAuthorize("@ss.hasPermi('shop:product:list')")
    @GetMapping("/list")
    public TableDataInfo list(ShopProduct product)
    {
        startPage();
        List<ShopProduct> list = productService.selectProductList(product);
        return getDataTable(list);
    }

    /**
     * 导出商品列表
     */
    @PreAuthorize("@ss.hasPermi('shop:product:export')")
    @Log(title = "商品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShopProduct product)
    {
        List<ShopProduct> list = productService.selectProductList(product);
        ExcelUtil<ShopProduct> util = new ExcelUtil<ShopProduct>(ShopProduct.class);
        util.exportExcel(response, list, "商品数据");
    }

    /**
     * 获取商品详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:product:query')")
    @GetMapping(value = "/{productId}")
    public AjaxResult getInfo(@PathVariable("productId") Long productId)
    {
        return success(productService.selectProductById(productId));
    }

    /**
     * 新增商品
     */
    @PreAuthorize("@ss.hasPermi('shop:product:add')")
    @Log(title = "商品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody ShopProduct product)
    {
        if (!productService.checkProductCodeUnique(product))
        {
            return error("新增商品'" + product.getProductName() + "'失败，商品编码已存在");
        }
        product.setCreateBy(getUsername());
        return toAjax(productService.insertProduct(product));
    }

    /**
     * 修改商品
     */
    @PreAuthorize("@ss.hasPermi('shop:product:edit')")
    @Log(title = "商品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody ShopProduct product)
    {
        if (!productService.checkProductCodeUnique(product))
        {
            return error("修改商品'" + product.getProductName() + "'失败，商品编码已存在");
        }
        product.setUpdateBy(getUsername());
        return toAjax(productService.updateProduct(product));
    }

    /**
     * 删除商品
     */
    @PreAuthorize("@ss.hasPermi('shop:product:remove')")
    @Log(title = "商品", businessType = BusinessType.DELETE)
    @DeleteMapping("/{productIds}")
    public AjaxResult remove(@PathVariable Long[] productIds)
    {
        return toAjax(productService.deleteProductByIds(productIds));
    }

    /**
     * 商品上架
     */
    @PreAuthorize("@ss.hasPermi('shop:product:edit')")
    @Log(title = "商品上架", businessType = BusinessType.UPDATE)
    @PutMapping("/onSale/{productId}")
    public AjaxResult onSale(@PathVariable Long productId)
    {
        return toAjax(productService.updateProductOnSale(productId));
    }

    /**
     * 商品下架
     */
    @PreAuthorize("@ss.hasPermi('shop:product:edit')")
    @Log(title = "商品下架", businessType = BusinessType.UPDATE)
    @PutMapping("/offSale/{productId}")
    public AjaxResult offSale(@PathVariable Long productId)
    {
        return toAjax(productService.updateProductOffSale(productId));
    }
}
