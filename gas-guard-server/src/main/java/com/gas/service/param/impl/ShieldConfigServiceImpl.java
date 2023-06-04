package com.gas.service.param.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.dao.ShieldConfigDao;
import com.gas.entity.ShieldConfig;
import com.gas.model.ShieldConfigRequest;
import com.gas.service.param.ShieldConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShieldConfigServiceImpl implements ShieldConfigService {

    @Autowired
    private ShieldConfigDao shieldConfigDao;

    @Override
    public Page<ShieldConfig> getShieldConfig(ShieldConfigRequest request) {
        ShieldConfig shieldConfig = new ShieldConfig();
        BeanUtils.copyProperties(request, shieldConfig);

        Page<ShieldConfig> offlineRulePage = shieldConfigDao.selectPage(shieldConfig, request.getCurr(), request.getPageSize());

        return offlineRulePage;
    }

    @Override
    @Transactional
    public void add(ShieldConfigRequest request) {
        ShieldConfig shieldConfig = new ShieldConfig();
        BeanUtils.copyProperties(request, shieldConfig);

        shieldConfigDao.addShieldConfig(shieldConfig);
    }

    @Override
    public ShieldConfig getById(Integer id) {
        return shieldConfigDao.getById(id);
    }

    @Override
    @Transactional
    public void updateById(Integer id) {
        ShieldConfig shieldConfig = this.getById(id);
        shieldConfig.setStatus("已撤销");

        shieldConfigDao.updateShieldConfig(shieldConfig);
    }
}
