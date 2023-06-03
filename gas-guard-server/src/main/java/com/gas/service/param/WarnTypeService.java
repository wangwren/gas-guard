package com.gas.service.param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.entity.WarnType;
import com.gas.model.WarnTypeRequest;

public interface WarnTypeService {
    Page<WarnType> getWarnType(WarnTypeRequest request);

    void addOrUpdate(WarnTypeRequest request);

    WarnType getById(Integer id);

    void delById(Integer id);
}
