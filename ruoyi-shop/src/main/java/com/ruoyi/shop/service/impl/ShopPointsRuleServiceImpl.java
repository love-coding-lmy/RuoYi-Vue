package com.ruoyi.shop.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.shop.domain.ShopPointsRule;
import com.ruoyi.shop.mapper.ShopPointsRuleMapper;
import com.ruoyi.shop.service.IShopPointsRuleService;

/**
 * 积分规则Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class ShopPointsRuleServiceImpl implements IShopPointsRuleService
{
    @Autowired
    private ShopPointsRuleMapper pointsRuleMapper;

    /**
     * 查询积分规则
     *
     * @param ruleId 积分规则主键
     * @return 积分规则
     */
    @Override
    public ShopPointsRule selectPointsRuleById(Long ruleId)
    {
        ShopPointsRule pointsRule = pointsRuleMapper.selectPointsRuleById(ruleId);
        if (pointsRule != null)
        {
            // 设置规则类型描述
            pointsRule.setRuleTypeText(getRuleTypeText(pointsRule.getRuleType()));
            // 设置积分类型描述
            pointsRule.setPointsTypeText(getPointsTypeText(pointsRule.getPointsType()));
            // 设置状态描述
            pointsRule.setStatusText(getStatusText(pointsRule.getStatus()));
        }
        return pointsRule;
    }

    /**
     * 查询积分规则列表
     *
     * @param pointsRule 积分规则
     * @return 积分规则
     */
    @Override
    public List<ShopPointsRule> selectPointsRuleList(ShopPointsRule pointsRule)
    {
        List<ShopPointsRule> list = pointsRuleMapper.selectPointsRuleList(pointsRule);
        for (ShopPointsRule item : list)
        {
            // 设置规则类型描述
            item.setRuleTypeText(getRuleTypeText(item.getRuleType()));
            // 设置积分类型描述
            item.setPointsTypeText(getPointsTypeText(item.getPointsType()));
            // 设置状态描述
            item.setStatusText(getStatusText(item.getStatus()));
        }
        return list;
    }

    /**
     * 新增积分规则
     *
     * @param pointsRule 积分规则
     * @return 结果
     */
    @Override
    public int insertPointsRule(ShopPointsRule pointsRule)
    {
        return pointsRuleMapper.insertPointsRule(pointsRule);
    }

    /**
     * 修改积分规则
     *
     * @param pointsRule 积分规则
     * @return 结果
     */
    @Override
    public int updatePointsRule(ShopPointsRule pointsRule)
    {
        return pointsRuleMapper.updatePointsRule(pointsRule);
    }

    /**
     * 批量删除积分规则
     *
     * @param ruleIds 需要删除的积分规则主键
     * @return 结果
     */
    @Override
    public int deletePointsRuleByIds(Long[] ruleIds)
    {
        return pointsRuleMapper.deletePointsRuleByIds(ruleIds);
    }

    /**
     * 删除积分规则信息
     *
     * @param ruleId 积分规则主键
     * @return 结果
     */
    @Override
    public int deletePointsRuleById(Long ruleId)
    {
        return pointsRuleMapper.deletePointsRuleById(ruleId);
    }

    /**
     * 获取规则类型描述
     *
     * @param type 类型代码
     * @return 类型描述
     */
    private String getRuleTypeText(String type)
    {
        switch (type)
        {
            case "consume":
                return "消费行为";
            case "action":
                return "行为活动";
            case "exchange":
                return "兑换";
            case "deduction":
                return "抵扣";
            default:
                return "未知";
        }
    }

    /**
     * 获取积分类型描述
     *
     * @param type 类型代码
     * @return 类型描述
     */
    private String getPointsTypeText(String type)
    {
        switch (type)
        {
            case "earn":
                return "获得";
            case "spend":
                return "消耗";
            default:
                return "未知";
        }
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
