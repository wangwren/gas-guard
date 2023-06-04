package com.gas.service.param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.entity.OfflineRule;
import com.gas.model.OfflineRuleRequest;

public interface OfflineRuleService {
    Page<OfflineRule> getOfflineRule(OfflineRuleRequest request);

    void addOrUpdate(OfflineRuleRequest request);

    OfflineRule getById(Integer id);

    void delById(Integer id);
}
