package com.gas.dao;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.dao.mapper.OfflineRuleMapper;
import com.gas.entity.OfflineRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OfflineRuleDao {

    @Autowired
    private OfflineRuleMapper mapper;

    public Page<OfflineRule> selectPage(OfflineRule offlineRule, Integer curr, Integer pageSize) {
        //查询第curr页，每页pageSize条
        Page<OfflineRule> page = new Page<>(curr,pageSize);

        QueryWrapper<OfflineRule> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(offlineRule.getTypeName()), "type_name", offlineRule.getTypeName());

        //查询可用数据
        wrapper.eq("enable", 1);


        Page<OfflineRule> offlineRulePage = mapper.selectPage(page, wrapper);
        return offlineRulePage;
    }

    public void addDataDict(OfflineRule offlineRule) {
        mapper.insert(offlineRule);
    }

    public void updateDataDict(OfflineRule offlineRule) {
        mapper.updateById(offlineRule);
    }

    public OfflineRule getById(Integer id) {
        return mapper.selectById(id);
    }

    public void delDataDict(Integer id) {
        OfflineRule offlineRule = this.getById(id);
        offlineRule.setEnable(false);
        mapper.updateById(offlineRule);
    }
}
