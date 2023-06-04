package com.gas.service.param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.entity.SysConfig;
import com.gas.model.SysConfigRequest;

import java.util.List;

public interface SysConfigService {
    Page<SysConfig> getSysConfig(SysConfigRequest request);

    List<SysConfig> getByCond(SysConfigRequest request);

    void addOrUpdate(SysConfigRequest request);

    SysConfig getById(Integer id);

    void delById(Integer id);
}
