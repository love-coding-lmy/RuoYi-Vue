package com.ruoyi.shop.mapper;

import java.util.List;
import com.ruoyi.shop.domain.ShopMemberLevel;

/**
 * 会员等级Mapper接口
 *
 * @author ruoyi
 */
public interface ShopMemberLevelMapper
{
    /**
     * 查询会员等级
     *
     * @param levelId 会员等级主键
     * @return 会员等级
     */
    public ShopMemberLevel selectMemberLevelById(Long levelId);

    /**
     * 查询会员等级列表
     *
     * @param memberLevel 会员等级
     * @return 会员等级集合
     */
    public List<ShopMemberLevel> selectMemberLevelList(ShopMemberLevel memberLevel);

    /**
     * 新增会员等级
     *
     * @param memberLevel 会员等级
     * @return 结果
     */
    public int insertMemberLevel(ShopMemberLevel memberLevel);

    /**
     * 修改会员等级
     *
     * @param memberLevel 会员等级
     * @return 结果
     */
    public int updateMemberLevel(ShopMemberLevel memberLevel);

    /**
     * 删除会员等级
     *
     * @param levelId 会员等级主键
     * @return 结果
     */
    public int deleteMemberLevelById(Long levelId);

    /**
     * 批量删除会员等级
     *
     * @param levelIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMemberLevelByIds(Long[] levelIds);
}
