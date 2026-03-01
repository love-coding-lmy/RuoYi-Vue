package com.ruoyi.shop.service;

import java.util.List;
import com.ruoyi.shop.domain.ShopSnackTag;

/**
 * 零食标签Service接口
 *
 * @author ruoyi
 */
public interface IShopSnackTagService
{
    /**
     * 查询零食标签
     *
     * @param tagId 零食标签主键
     * @return 零食标签
     */
    public ShopSnackTag selectTagById(Long tagId);

    /**
     * 查询零食标签列表
     *
     * @param tag 零食标签
     * @return 零食标签集合
     */
    public List<ShopSnackTag> selectTagList(ShopSnackTag tag);

    /**
     * 新增零食标签
     *
     * @param tag 零食标签
     * @return 结果
     */
    public int insertTag(ShopSnackTag tag);

    /**
     * 修改零食标签
     *
     * @param tag 零食标签
     * @return 结果
     */
    public int updateTag(ShopSnackTag tag);

    /**
     * 批量删除零食标签
     *
     * @param tagIds 需要删除的零食标签主键集合
     * @return 结果
     */
    public int deleteTagByIds(Long[] tagIds);

    /**
     * 删除零食标签信息
     *
     * @param tagId 零食标签主键
     * @return 结果
     */
    public int deleteTagById(Long tagId);
}
