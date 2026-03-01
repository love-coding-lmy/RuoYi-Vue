package com.ruoyi.web.controller.shop;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.shop.domain.ShopSnackSeries;
import com.ruoyi.shop.domain.ShopSnackTag;
import com.ruoyi.shop.domain.ShopPromotion;
import com.ruoyi.shop.domain.ShopRecommendPosition;
import com.ruoyi.shop.service.IShopSnackSeriesService;
import com.ruoyi.shop.service.IShopSnackTagService;
import com.ruoyi.shop.service.IShopPromotionService;
import com.ruoyi.shop.service.IShopRecommendPositionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 零食模块Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/shop/snack")
public class ShopSnackController extends BaseController
{
    @Autowired
    private IShopSnackSeriesService seriesService;

    @Autowired
    private IShopSnackTagService tagService;

    @Autowired
    private IShopPromotionService promotionService;

    @Autowired
    private IShopRecommendPositionService positionService;

    // ==================== 零食系列接口 ====================

    /**
     * 查询零食系列列表
     */
    @PreAuthorize("@ss.hasPermi('shop:snack:series:list')")
    @GetMapping("/series/list")
    public TableDataInfo seriesList(ShopSnackSeries series)
    {
        startPage();
        List<ShopSnackSeries> list = seriesService.selectSeriesList(series);
        return getDataTable(list);
    }

    /**
     * 导出零食系列列表
     */
    @PreAuthorize("@ss.hasPermi('shop:snack:series:export')")
    @Log(title = "零食系列", businessType = BusinessType.EXPORT)
    @PostMapping("/series/export")
    public void exportSeries(HttpServletResponse response, ShopSnackSeries series)
    {
        List<ShopSnackSeries> list = seriesService.selectSeriesList(series);
        ExcelUtil<ShopSnackSeries> util = new ExcelUtil<ShopSnackSeries>(ShopSnackSeries.class);
        util.exportExcel(response, list, "零食系列数据");
    }

    /**
     * 获取零食系列详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:snack:series:query')")
    @GetMapping(value = "/series/{seriesId}")
    public AjaxResult getSeries(@PathVariable("seriesId") Long seriesId)
    {
        return success(seriesService.selectSeriesById(seriesId));
    }

    /**
     * 新增零食系列
     */
    @PreAuthorize("@ss.hasPermi('shop:snack:series:add')")
    @Log(title = "零食系列", businessType = BusinessType.INSERT)
    @PostMapping("/series")
    public AjaxResult addSeries(@RequestBody ShopSnackSeries series)
    {
        return toAjax(seriesService.insertSeries(series));
    }

    /**
     * 修改零食系列
     */
    @PreAuthorize("@ss.hasPermi('shop:snack:series:edit')")
    @Log(title = "零食系列", businessType = BusinessType.UPDATE)
    @PutMapping("/series")
    public AjaxResult editSeries(@RequestBody ShopSnackSeries series)
    {
        return toAjax(seriesService.updateSeries(series));
    }

    /**
     * 删除零食系列
     */
    @PreAuthorize("@ss.hasPermi('shop:snack:series:remove')")
    @Log(title = "零食系列", businessType = BusinessType.DELETE)
    @DeleteMapping("/series/{seriesIds}")
    public AjaxResult removeSeries(@PathVariable Long[] seriesIds)
    {
        return toAjax(seriesService.deleteSeriesByIds(seriesIds));
    }

    // ==================== 零食标签接口 ====================

    /**
     * 查询零食标签列表
     */
    @PreAuthorize("@ss.hasPermi('shop:snack:tag:list')")
    @GetMapping("/tag/list")
    public TableDataInfo tagList(ShopSnackTag tag)
    {
        startPage();
        List<ShopSnackTag> list = tagService.selectTagList(tag);
        return getDataTable(list);
    }

    /**
     * 导出零食标签列表
     */
    @PreAuthorize("@ss.hasPermi('shop:snack:tag:export')")
    @Log(title = "零食标签", businessType = BusinessType.EXPORT)
    @PostMapping("/tag/export")
    public void exportTag(HttpServletResponse response, ShopSnackTag tag)
    {
        List<ShopSnackTag> list = tagService.selectTagList(tag);
        ExcelUtil<ShopSnackTag> util = new ExcelUtil<ShopSnackTag>(ShopSnackTag.class);
        util.exportExcel(response, list, "零食标签数据");
    }

    /**
     * 获取零食标签详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:snack:tag:query')")
    @GetMapping(value = "/tag/{tagId}")
    public AjaxResult getTag(@PathVariable("tagId") Long tagId)
    {
        return success(tagService.selectTagById(tagId));
    }

    /**
     * 新增零食标签
     */
    @PreAuthorize("@ss.hasPermi('shop:snack:tag:add')")
    @Log(title = "零食标签", businessType = BusinessType.INSERT)
    @PostMapping("/tag")
    public AjaxResult addTag(@RequestBody ShopSnackTag tag)
    {
        return toAjax(tagService.insertTag(tag));
    }

    /**
     * 修改零食标签
     */
    @PreAuthorize("@ss.hasPermi('shop:snack:tag:edit')")
    @Log(title = "零食标签", businessType = BusinessType.UPDATE)
    @PutMapping("/tag")
    public AjaxResult editTag(@RequestBody ShopSnackTag tag)
    {
        return toAjax(tagService.updateTag(tag));
    }

    /**
     * 删除零食标签
     */
    @PreAuthorize("@ss.hasPermi('shop:snack:tag:remove')")
    @Log(title = "零食标签", businessType = BusinessType.DELETE)
    @DeleteMapping("/tag/{tagIds}")
    public AjaxResult removeTag(@PathVariable Long[] tagIds)
    {
        return toAjax(tagService.deleteTagByIds(tagIds));
    }

    // ==================== 促销活动接口 ====================

    /**
     * 查询促销活动列表
     */
    @PreAuthorize("@ss.hasPermi('shop:snack:promotion:list')")
    @GetMapping("/promotion/list")
    public TableDataInfo promotionList(ShopPromotion promotion)
    {
        startPage();
        List<ShopPromotion> list = promotionService.selectPromotionList(promotion);
        return getDataTable(list);
    }

    /**
     * 导出促销活动列表
     */
    @PreAuthorize("@ss.hasPermi('shop:snack:promotion:export')")
    @Log(title = "促销活动", businessType = BusinessType.EXPORT)
    @PostMapping("/promotion/export")
    public void exportPromotion(HttpServletResponse response, ShopPromotion promotion)
    {
        List<ShopPromotion> list = promotionService.selectPromotionList(promotion);
        ExcelUtil<ShopPromotion> util = new ExcelUtil<ShopPromotion>(ShopPromotion.class);
        util.exportExcel(response, list, "促销活动数据");
    }

    /**
     * 获取促销活动详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:snack:promotion:query')")
    @GetMapping(value = "/promotion/{promotionId}")
    public AjaxResult getPromotion(@PathVariable("promotionId") Long promotionId)
    {
        return success(promotionService.selectPromotionById(promotionId));
    }

    /**
     * 新增促销活动
     */
    @PreAuthorize("@ss.hasPermi('shop:snack:promotion:add')")
    @Log(title = "促销活动", businessType = BusinessType.INSERT)
    @PostMapping("/promotion")
    public AjaxResult addPromotion(@RequestBody ShopPromotion promotion)
    {
        return toAjax(promotionService.insertPromotion(promotion));
    }

    /**
     * 修改促销活动
     */
    @PreAuthorize("@ss.hasPermi('shop:snack:promotion:edit')")
    @Log(title = "促销活动", businessType = BusinessType.UPDATE)
    @PutMapping("/promotion")
    public AjaxResult editPromotion(@RequestBody ShopPromotion promotion)
    {
        return toAjax(promotionService.updatePromotion(promotion));
    }

    /**
     * 删除促销活动
     */
    @PreAuthorize("@ss.hasPermi('shop:snack:promotion:remove')")
    @Log(title = "促销活动", businessType = BusinessType.DELETE)
    @DeleteMapping("/promotion/{promotionIds}")
    public AjaxResult removePromotion(@PathVariable Long[] promotionIds)
    {
        return toAjax(promotionService.deletePromotionByIds(promotionIds));
    }

    // ==================== 推荐位接口 ====================

    /**
     * 查询推荐位列表
     */
    @PreAuthorize("@ss.hasPermi('shop:snack:recommend:list')")
    @GetMapping("/recommend/list")
    public TableDataInfo recommendList(ShopRecommendPosition position)
    {
        startPage();
        List<ShopRecommendPosition> list = positionService.selectPositionList(position);
        return getDataTable(list);
    }

    /**
     * 导出推荐位列表
     */
    @PreAuthorize("@ss.hasPermi('shop:snack:recommend:export')")
    @Log(title = "推荐位", businessType = BusinessType.EXPORT)
    @PostMapping("/recommend/export")
    public void exportRecommend(HttpServletResponse response, ShopRecommendPosition position)
    {
        List<ShopRecommendPosition> list = positionService.selectPositionList(position);
        ExcelUtil<ShopRecommendPosition> util = new ExcelUtil<ShopRecommendPosition>(ShopRecommendPosition.class);
        util.exportExcel(response, list, "推荐位数据");
    }

    /**
     * 获取推荐位详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:snack:recommend:query')")
    @GetMapping(value = "/recommend/{positionId}")
    public AjaxResult getRecommend(@PathVariable("positionId") Long positionId)
    {
        return success(positionService.selectPositionById(positionId));
    }

    /**
     * 新增推荐位
     */
    @PreAuthorize("@ss.hasPermi('shop:snack:recommend:add')")
    @Log(title = "推荐位", businessType = BusinessType.INSERT)
    @PostMapping("/recommend")
    public AjaxResult addRecommend(@RequestBody ShopRecommendPosition position)
    {
        return toAjax(positionService.insertPosition(position));
    }

    /**
     * 修改推荐位
     */
    @PreAuthorize("@ss.hasPermi('shop:snack:recommend:edit')")
    @Log(title = "推荐位", businessType = BusinessType.UPDATE)
    @PutMapping("/recommend")
    public AjaxResult editRecommend(@RequestBody ShopRecommendPosition position)
    {
        return toAjax(positionService.updatePosition(position));
    }

    /**
     * 删除推荐位
     */
    @PreAuthorize("@ss.hasPermi('shop:snack:recommend:remove')")
    @Log(title = "推荐位", businessType = BusinessType.DELETE)
    @DeleteMapping("/recommend/{positionIds}")
    public AjaxResult removeRecommend(@PathVariable Long[] positionIds)
    {
        return toAjax(positionService.deletePositionByIds(positionIds));
    }
}
