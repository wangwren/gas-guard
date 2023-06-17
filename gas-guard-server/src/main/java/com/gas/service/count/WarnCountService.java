package com.gas.service.count;

import com.gas.dto.WarnCountDto;
import com.gas.model.WarnCountRequest;

import java.util.List;

public interface WarnCountService {
    List<WarnCountDto> warnCount(WarnCountRequest request);
}
