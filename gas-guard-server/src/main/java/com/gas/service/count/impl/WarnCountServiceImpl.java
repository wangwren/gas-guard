package com.gas.service.count.impl;

import com.gas.common.GlobalConstants;
import com.gas.dao.MonitorDeviceDao;
import com.gas.dao.WarnInfoDao;
import com.gas.dto.MonitorDeviceDto;
import com.gas.dto.WarnCountDto;
import com.gas.dto.WarnInfoDto;
import com.gas.entity.MonitorDevice;
import com.gas.entity.WarnInfo;
import com.gas.model.WarnCountRequest;
import com.gas.service.count.WarnCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WarnCountServiceImpl implements WarnCountService {

    @Autowired
    private WarnInfoDao warnInfoDao;
    @Autowired
    private MonitorDeviceDao deviceDao;

    @Override
    public List<WarnCountDto> warnCount(WarnCountRequest request) {
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
        List<WarnCountDto> list = new ArrayList<>();
        for (MonitorDevice device : monitorDevices) {
            WarnCountDto countDto = new WarnCountDto();
            List<WarnInfo> warnInfos = warnInfoDao.selectByDeviceId(device.getId());

            countDto.setPointName(device.getPointName());
            countDto.setDeviceName(device.getDeviceName());
            countDto.setDeviceNo(device.getDeviceNo());
            countDto.setDeviceStatus(device.getDeviceStatus());
            countDto.setCountNum(warnInfos.size());
            countDto.setWarnTime(warnInfos.get(0).getWarnTime());
            countDto.setFailNum(Objects.equals(device.getDeviceStatus(), "故障") ? 1 : 0);
            countDto.setOfflineNum(Objects.equals(device.getDeviceStatus(), "离线") ? 1 : 0);
            countDto.setWarnNum(Objects.equals(device.getDeviceStatus(), "预警") ? 1 : 0);
            //这块很恶心
//            if (warnInfos.size() > 1) {
//
//            } else {
//
//            }

            list.add(countDto);
        }
        return list;
    }
}
