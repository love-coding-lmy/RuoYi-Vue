package com.ruoyi.shop.service;

import java.util.List;
import com.ruoyi.shop.domain.ShopPointsRule;

/**
 * 积分规则Service接口
 *
 * @author ruoyi
 */
public interface IShopPointsRuleService
{
    /**
     * 查询积分规则
     *
     * @param ruleId 积分规则主键
     * @return 积分规则
     */
    public ShopPointsRule selectPointsRuleById(Long ruleId);

    /**
     * 查询积分规则列表
     *
     * @param pointsRule 积分规则
     * @return 积分规则集合
     */
    public List<ShopPointsRule> selectPointsRuleList(ShopPointsRule pointsRule);

    /**
     * 新增积分规则
     *
     * @param pointsRule 积分规则
     * @return 结果
     */
    public int insertPointsRule(ShopPointsRule pointsRule);

    /**
     * 修改积分规则
     *
     * @param pointsRule 积分规则
     * @return 结果
     */
    public int updatePointsRule(ShopPointsRule pointsRule);

    /**
     * 批量删除积分规则
     *
     * @param ruleIds 需要删除的积分规则主键集合
     * @return 结果
     */
    public int deletePointsRuleByIds(Long[] ruleIds);

    /**
     * 删除积分规则信息
     *
     * @param ruleId 积分规则主键
     * @return 结果
     */
    public int deletePointsRuleById(Long ruleId);
}
