package com.ruoyi.shop.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.shop.domain.ShopMemberLevel;
import com.ruoyi.shop.mapper.ShopMemberLevelMapper;
import com.ruoyi.shop.service.IShopMemberLevelService;

/**
 * 会员等级Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class ShopMemberLevelServiceImpl implements IShopMemberLevelService
{
    @Autowired
    private ShopMemberLevelMapper memberLevelMapper;

    /**
     * 查询会员等级
     *
     * @param levelId 会员等级主键
     * @return 会员等级
     */
    @Override
    public ShopMemberLevel selectMemberLevelById(Long levelId)
    {
        ShopMemberLevel memberLevel = memberLevelMapper.selectMemberLevelById(levelId);
        if (memberLevel != null)
        {
            // 设置状态描述
            memberLevel.setStatusText(getStatusText(memberLevel.getStatus()));
        }
        return memberLevel;
    }

    /**
     * 查询会员等级列表
     *
     * @param memberLevel 会员等级
     * @return 会员等级
     */
    @Override
    public List<ShopMemberLevel> selectMemberLevelList(ShopMemberLevel memberLevel)
    {
        List<ShopMemberLevel> list = memberLevelMapper.selectMemberLevelList(memberLevel);
        for (ShopMemberLevel item : list)
        {
            // 设置状态描述
            item.setStatusText(getStatusText(item.getStatus()));
        }
        return list;
    }

    /**
     * 新增会员等级
     *
     * @param memberLevel 会员等级
     * @return 结果
     */
    @Override
    public int insertMemberLevel(ShopMemberLevel memberLevel)
    {
        return memberLevelMapper.insertMemberLevel(memberLevel);
    }

    /**
     * 修改会员等级
     *
     * @param memberLevel 会员等级
     * @return 结果
     */
    @Override
    public int updateMemberLevel(ShopMemberLevel memberLevel)
    {
        return memberLevelMapper.updateMemberLevel(memberLevel);
    }

    /**
     * 批量删除会员等级
     *
     * @param levelIds 需要删除的会员等级主键
     * @return 结果
     */
    @Override
    public int deleteMemberLevelByIds(Long[] levelIds)
    {
        return memberLevelMapper.deleteMemberLevelByIds(levelIds);
    }

    /**
     * 删除会员等级信息
     *
     * @param levelId 会员等级主键
     * @return 结果
     */
    @Override
    public int deleteMemberLevelById(Long levelId)
    {
        return memberLevelMapper.deleteMemberLevelById(levelId);
    }

    /**
     * 获取状态描述
     *
     * @param status 状态代码
     * @return 状态描述
     */
    private String getStatusText(String status)
    {
        switch (status)
        {
            case "0":
                return "正常";
            case "1":
                return "停用";
            default:
                return "未知";
        }
    }
}
