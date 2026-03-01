package com.ruoyi.shop.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.shop.domain.ShopCategory;
import com.ruoyi.shop.mapper.ShopCategoryMapper;
import com.ruoyi.shop.service.IShopCategoryService;

/**
 * 商品分类 服务层实现
 *
 * @author ruoyi
 */
@Service
public class ShopCategoryServiceImpl implements IShopCategoryService
{
    @Autowired
    private ShopCategoryMapper categoryMapper;

    /**
     * 查询商品分类信息
     *
     * @param categoryId 商品分类ID
     * @return 商品分类信息
     */
    @Override
    public ShopCategory selectCategoryById(Long categoryId)
    {
        return categoryMapper.selectCategoryById(categoryId);
    }

    /**
     * 查询商品分类列表
     *
     * @param category 商品分类信息
     * @return 商品分类集合
     */
    @Override
    public List<ShopCategory> selectCategoryList(ShopCategory category)
    {
        return categoryMapper.selectCategoryList(category);
    }

    /**
     * 新增商品分类
     *
     * @param category 商品分类信息
     * @return 结果
     */
    @Override
    public int insertCategory(ShopCategory category)
    {
        ShopCategory info = categoryMapper.selectCategoryById(category.getParentId());
        // 如果父节点不为正常状态,则不允许新增子节点
        if (!StringUtils.isNull(info) && "1".equals(info.getStatus()))
        {
            throw new RuntimeException("父分类已停用,不允许新增");
        }
        category.setAncestors(info == null ? "0" : info.getAncestors() + "," + category.getParentId());
        return categoryMapper.insertCategory(category);
    }

    /**
     * 修改商品分类
     *
     * @param category 商品分类信息
     * @return 结果
     */
    @Override
    public int updateCategory(ShopCategory category)
    {
        ShopCategory info = categoryMapper.selectCategoryById(category.getParentId());
        ShopCategory oldCategory = categoryMapper.selectCategoryById(category.getCategoryId());
        if (StringUtils.isNotNull(info) && StringUtils.isNotNull(oldCategory))
        {
            String newAncestors = info.getAncestors() + "," + info.getCategoryId();
            String oldAncestors = oldCategory.getAncestors();
            category.setAncestors(newAncestors);
            updateCategoryChildren(category.getCategoryId(), newAncestors, oldAncestors);
        }
        int result = categoryMapper.updateCategory(category);
        if (StringUtils.isNotNull(category.getStatus()) && "0".equals(category.getStatus()))
        {
            // 如果该分类是启用状态，则启用该分类的所有父级分类
            updateParentCategoryStatus(category);
        }
        return result;
    }

    /**
     * 修改子元素关系
     *
     * @param categoryId 被修改的分类ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateCategoryChildren(Long categoryId, String newAncestors, String oldAncestors)
    {
        List<ShopCategory> children = categoryMapper.selectCategoryList(new ShopCategory());
        for (ShopCategory child : children)
        {
            if (child.getAncestors() != null && child.getAncestors().contains(oldAncestors))
            {
                child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
                categoryMapper.updateCategory(child);
            }
        }
    }

    /**
     * 修改该分类的父级分类状态
     *
     * @param category 当前分类
     */
    private void updateParentCategoryStatus(ShopCategory category)
    {
        String ancestors = category.getAncestors();
        Long[] categoryIds = Convert.toLongArray(ancestors);
        if (categoryIds.length > 0)
        {
            for (Long categoryId : categoryIds)
            {
                ShopCategory parentCategory = categoryMapper.selectCategoryById(categoryId);
                if (parentCategory != null && "1".equals(parentCategory.getStatus()))
                {
                    parentCategory.setStatus("0");
                    categoryMapper.updateCategory(parentCategory);
                }
            }
        }
    }

    /**
     * 批量删除商品分类
     *
     * @param categoryIds 需要删除的商品分类ID
     * @return 结果
     */
    @Override
    public int deleteCategoryByIds(Long[] categoryIds)
    {
        for (Long categoryId : categoryIds)
        {
            ShopCategory category = selectCategoryById(categoryId);
            // 检查是否有子分类
            int childrenCount = categoryMapper.selectChildrenCountByParentId(categoryId);
            if (childrenCount > 0)
            {
                throw new RuntimeException("该分类下存在子分类,不允许删除");
            }
            // 检查是否有商品
            int productCount = categoryMapper.selectProductCountByCategoryId(categoryId);
            if (productCount > 0)
            {
                throw new RuntimeException("该分类下存在商品,不允许删除");
            }
        }
        return categoryMapper.deleteCategoryByIds(categoryIds);
    }

    /**
     * 删除商品分类信息
     *
     * @param categoryId 商品分类ID
     * @return 结果
     */
    @Override
    public int deleteCategoryById(Long categoryId)
    {
        return categoryMapper.deleteCategoryById(categoryId);
    }

    /**
     * 构建前端所需要树结构
     *
     * @param categories 分类列表
     * @return 树结构列表
     */
    @Override
    public List<ShopCategory> buildCategoryTree(List<ShopCategory> categories)
    {
        List<ShopCategory> returnList = new ArrayList<ShopCategory>();
        List<Long> tempList = categories.stream().map(ShopCategory::getCategoryId).collect(Collectors.toList());
        for (ShopCategory category : categories)
        {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(category.getParentId()))
            {
                recursionFn(categories, category);
                returnList.add(category);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = categories;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<ShopCategory> list, ShopCategory t)
    {
        // 得到子节点列表
        List<ShopCategory> childList = getChildList(list, t);
        t.setChildren(childList);
        for (ShopCategory tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<ShopCategory> getChildList(List<ShopCategory> list, ShopCategory t)
    {
        List<ShopCategory> tlist = new ArrayList<ShopCategory>();
        Iterator<ShopCategory> it = list.iterator();
        while (it.hasNext())
        {
            ShopCategory n = (ShopCategory) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().equals(t.getCategoryId()))
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<ShopCategory> list, ShopCategory t)
    {
        return getChildList(list, t).size() > 0;
    }
}
