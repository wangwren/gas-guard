package com.gas.service.count;

import com.gas.dto.DeviceCountDto;
import com.gas.model.DeviceCountRequest;

public interface DeviceCountService {
    DeviceCountDto getDeviceCount(DeviceCountRequest request);
}
