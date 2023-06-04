package com.gas.service.param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.entity.ShieldConfig;
import com.gas.model.ShieldConfigRequest;

public interface ShieldConfigService {
    Page<ShieldConfig> getShieldConfig(ShieldConfigRequest request);

    void add(ShieldConfigRequest request);

    ShieldConfig getById(Integer id);

    void updateById(Integer id);
}
