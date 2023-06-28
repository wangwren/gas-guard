package com.gas.service.warn;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.dto.WarnInfoDto;
import com.gas.model.WarnDealInfoRequest;
import com.gas.model.WarnInfoRequest;

public interface WarnInfoService {
    Page<WarnInfoDto> getWarnFormalInfo(WarnInfoRequest request);

    Page<WarnInfoDto> getWarnDebugInfo(WarnInfoRequest request);

    WarnInfoDto getWarnInfoDetail(WarnInfoRequest request);

    void confirmWarnInfo(WarnInfoRequest request);

    void commitWarnInfo(WarnDealInfoRequest request);

    Page<WarnInfoDto> getPage(WarnInfoRequest request);

    void addOrUpdate(WarnInfoRequest request);
}
