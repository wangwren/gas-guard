package com.gas.service.count.impl;

import com.gas.common.GlobalConstants;
import com.gas.dao.MonitorDeviceDao;
import com.gas.dto.DeviceCountDto;
import com.gas.dto.MonitorDeviceDto;
import com.gas.entity.MonitorDevice;
import com.gas.model.DeviceCountRequest;
import com.gas.service.count.DeviceCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DeviceCountServiceImpl implements DeviceCountService {

    @Autowired
    private MonitorDeviceDao deviceDao;
    @Override
    public DeviceCountDto getDeviceCount(DeviceCountRequest request) {

        MonitorDeviceDto deviceDto = new MonitorDeviceDto();
        BeanUtils.copyProperties(request, deviceDto);
        List<MonitorDevice> devices = deviceDao.selectByCond(deviceDto);
        if (CollectionUtils.isEmpty(devices)) {
            return null;
        }

        DeviceCountDto countDto = new DeviceCountDto();
        countDto.setMonitorDevices(devices);

        countDto.setCreateDeviceCount(devices.size());
        //已通过审核数量
        List<MonitorDevice> passList = devices.stream()
                .filter(e -> e.getArchiveStatus().equals(GlobalConstants.ARCHIVE_PASS_STATUS))
                .collect(Collectors.toList());
        countDto.setPassDeviceCount(CollectionUtils.isEmpty(passList) ? 0 : passList.size());

        //未审核数量
        List<MonitorDevice> noPassList = devices.stream()
                .filter(e -> !e.getArchiveStatus().equals(GlobalConstants.ARCHIVE_PASS_STATUS))
                .collect(Collectors.toList());
        countDto.setNoPassDeviceCount(CollectionUtils.isEmpty(noPassList)? 0 : noPassList.size());

        return countDto;
    }
}
