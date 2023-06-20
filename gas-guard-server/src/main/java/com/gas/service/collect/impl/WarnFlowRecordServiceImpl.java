package com.gas.service.collect.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.dao.MonitorDeviceDao;
import com.gas.dao.WarnInfoDao;
import com.gas.dto.DataFlowRecordDto;
import com.gas.dto.MonitorDeviceDto;
import com.gas.dto.WarnFlowRecordDto;
import com.gas.dto.WarnInfoDto;
import com.gas.entity.MonitorDevice;
import com.gas.model.WarnFlowRecordRequest;
import com.gas.model.WarnFlowRecordResponse;
import com.gas.service.collect.WarnFlowRecordService;
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
public class WarnFlowRecordServiceImpl implements WarnFlowRecordService {

    @Autowired
    private WarnInfoDao warnInfoDao;
    @Autowired
    private MonitorDeviceDao deviceDao;

    @Override
    public Page<WarnFlowRecordResponse> getPage(WarnFlowRecordRequest request) {

        WarnFlowRecordDto dto = new WarnFlowRecordDto();
        BeanUtils.copyProperties(request, dto);

        WarnInfoDto warnInfoDto = new WarnInfoDto();
        BeanUtils.copyProperties(dto, warnInfoDto);
        Page<WarnInfoDto> warnInfoDtoPage = warnInfoDao.selectPageAll(warnInfoDto, request.getCurr(), request.getPageSize());

        if (CollectionUtils.isEmpty(warnInfoDtoPage.getRecords())) {
            return new Page<>(request.getCurr(),request.getPageSize());
        }

        List<Integer> deviceIds = warnInfoDtoPage.getRecords().stream().map(WarnInfoDto::getDeviceId).collect(Collectors.toList());
        MonitorDeviceDto deviceDto = new MonitorDeviceDto();
        BeanUtils.copyProperties(dto, deviceDto);
        List<MonitorDevice> monitorDevices = deviceDao.selectByIdsCond(deviceIds, deviceDto);

        List<WarnFlowRecordResponse> list = new ArrayList<>();
        for (MonitorDevice device : monitorDevices) {
            WarnFlowRecordResponse response = new WarnFlowRecordResponse();
            response.setPointName(device.getPointName());
            response.setOrganName(device.getOrganName());
            response.setProvince(device.getProvince());
            response.setAddress(device.getAddress());

            response.setDeviceNo(device.getDeviceNo());
            response.setDeviceName(device.getDeviceName());
            response.setDeviceType(device.getDeviceType());

            list.add(response);
        }

        Page<WarnFlowRecordResponse> page = new Page<>(request.getCurr(), request.getPageSize());
        page.setRecords(list);
        page.setTotal(list.size());
        page.setSize(list.size());
        return page;
    }
}
