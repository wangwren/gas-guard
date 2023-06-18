package com.gas.service.count.impl;

import com.gas.dao.MonitorDeviceDao;
import com.gas.dao.WarnInfoDao;
import com.gas.dto.MonitorDeviceDto;
import com.gas.dto.WarnInfoDto;
import com.gas.dto.WarnTimeDto;
import com.gas.entity.MonitorDevice;
import com.gas.entity.WarnInfo;
import com.gas.model.WarnCountRequest;
import com.gas.service.count.WarnTimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WarnTimeServiceImpl implements WarnTimeService {

    @Autowired
    private WarnInfoDao warnInfoDao;
    @Autowired
    private MonitorDeviceDao deviceDao;

    @Override
    public List<WarnTimeDto> warnTimeCount(WarnCountRequest request) {
        WarnInfoDto warnInfoDto = new WarnInfoDto();
        BeanUtils.copyProperties(request, warnInfoDto);
        List<WarnInfoDto> infoDtoList = warnInfoDao.selectList(warnInfoDto);
        if (CollectionUtils.isEmpty(infoDtoList)) {
            return null;
        }
        List<Integer> deviceIds = infoDtoList.stream().map(WarnInfoDto::getDeviceId).collect(Collectors.toList());
        MonitorDeviceDto deviceDto = new MonitorDeviceDto();
        BeanUtils.copyProperties(request, deviceDto);

        List<MonitorDevice> monitorDevices = deviceDao.selectByIdsCond(deviceIds,deviceDto);
        List<WarnTimeDto> list = new ArrayList<>();
        for (MonitorDevice device : monitorDevices) {
            List<WarnInfo> warnInfos = warnInfoDao.selectByDeviceId(device.getId());
            for (WarnInfo warnInfo : warnInfos) {
                WarnTimeDto timeDto = new WarnTimeDto();
                timeDto.setWarnTime(warnInfo.getWarnTime());
                list.add(timeDto);
            }
        }
        return list;
    }
}
