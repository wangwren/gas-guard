package com.gas.service.archive.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.dao.MonitorDeviceDao;
import com.gas.dto.MonitorDeviceDto;
import com.gas.model.MonitorDeviceRequest;
import com.gas.service.archive.MonitorDeviceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitorDeviceServiceImpl implements MonitorDeviceService {

    @Autowired
    private MonitorDeviceDao monitorDeviceDao;

    @Override
    public Page<MonitorDeviceDto> getMonitorDevice(MonitorDeviceRequest request) {
        MonitorDeviceDto monitorDeviceDto = new MonitorDeviceDto();
        BeanUtils.copyProperties(request, monitorDeviceDto);

        return monitorDeviceDao.selectPage(monitorDeviceDto, request.getCurr(), request.getPageSize());
    }
}
