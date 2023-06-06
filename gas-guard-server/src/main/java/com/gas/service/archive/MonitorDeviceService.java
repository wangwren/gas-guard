package com.gas.service.archive;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.dto.MonitorDeviceDto;
import com.gas.model.MonitorDeviceRequest;

public interface MonitorDeviceService {
    Page<MonitorDeviceDto> getMonitorDevice(MonitorDeviceRequest request);
}
