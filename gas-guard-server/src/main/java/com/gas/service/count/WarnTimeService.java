package com.gas.service.count;

import com.gas.dto.WarnTimeDto;
import com.gas.model.WarnCountRequest;

import java.util.List;

public interface WarnTimeService {
    List<WarnTimeDto> warnTimeCount(WarnCountRequest request);
}
