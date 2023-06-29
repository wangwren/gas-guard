package com.gas.service.param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.dto.WarnTypeDto;
import com.gas.entity.WarnType;
import com.gas.model.WarnTypeRequest;

public interface WarnTypeService {
    Page<WarnTypeDto> getWarnType(WarnTypeRequest request);

    void addOrUpdate(WarnTypeRequest request);

    WarnTypeDto getById(Integer id);

    void delById(Integer id);
}
