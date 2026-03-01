package com.ruoyi.shop.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.shop.domain.ShopSnackTag;
import com.ruoyi.shop.mapper.ShopSnackTagMapper;
import com.ruoyi.shop.service.IShopSnackTagService;

/**
 * 零食标签Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class ShopSnackTagServiceImpl implements IShopSnackTagService
{
    @Autowired
    private ShopSnackTagMapper tagMapper;

    /**
     * 查询零食标签
     *
     * @param tagId 零食标签主键
     * @return 零食标签
     */
    @Override
    public ShopSnackTag selectTagById(Long tagId)
    {
        return tagMapper.selectTagById(tagId);
    }

    /**
     * 查询零食标签列表
     *
     * @param tag 零食标签
     * @return 零食标签
     */
    @Override
    public List<ShopSnackTag> selectTagList(ShopSnackTag tag)
    {
        return tagMapper.selectTagList(tag);
    }

    /**
     * 新增零食标签
     *
     * @param tag 零食标签
     * @return 结果
     */
    @Override
    public int insertTag(ShopSnackTag tag)
    {
        return tagMapper.insertTag(tag);
    }

    /**
     * 修改零食标签
     *
     * @param tag 零食标签
     * @return 结果
     */
    @Override
    public int updateTag(ShopSnackTag tag)
    {
        return tagMapper.updateTag(tag);
    }

    /**
     * 批量删除零食标签
     *
     * @param tagIds 需要删除的零食标签主键
     * @return 结果
     */
    @Override
    public int deleteTagByIds(Long[] tagIds)
    {
        return tagMapper.deleteTagByIds(tagIds);
    }

    /**
     * 删除零食标签信息
     *
     * @param tagId 零食标签主键
     * @return 结果
     */
    @Override
    public int deleteTagById(Long tagId)
    {
        return tagMapper.deleteTagById(tagId);
    }
}
