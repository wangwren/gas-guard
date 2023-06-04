package com.gas.service.param.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.dao.OfflineRuleDao;
import com.gas.entity.OfflineRule;
import com.gas.model.OfflineRuleRequest;
import com.gas.service.param.OfflineRuleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class OfflineRuleServiceImpl implements OfflineRuleService {

    @Autowired
    private OfflineRuleDao offlineRuleDao;

    @Override
    public Page<OfflineRule> getOfflineRule(OfflineRuleRequest request) {

        OfflineRule offlineRule = new OfflineRule();
        BeanUtils.copyProperties(request, offlineRule);

        Page<OfflineRule> offlineRulePage = offlineRuleDao.selectPage(offlineRule, request.getCurr(), request.getPageSize());

        return offlineRulePage;
    }

    @Override
    @Transactional
    public void addOrUpdate(OfflineRuleRequest request) {
        OfflineRule offlineRule = new OfflineRule();
        BeanUtils.copyProperties(request, offlineRule);

        if (Objects.isNull(offlineRule.getId())) {
            //新增
            offlineRuleDao.addDataDict(offlineRule);
        } else {
            //修改
            offlineRuleDao.updateDataDict(offlineRule);
        }
    }

    @Override
    public OfflineRule getById(Integer id) {
        return offlineRuleDao.getById(id);
    }

    @Override
    @Transactional
    public void delById(Integer id) {
        offlineRuleDao.delDataDict(id);
    }
}
