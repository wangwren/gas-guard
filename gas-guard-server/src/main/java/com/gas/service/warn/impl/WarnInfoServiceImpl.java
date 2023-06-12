package com.gas.service.warn.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.common.GlobalConstants;
import com.gas.dao.MonitorDeviceDao;
import com.gas.dao.MonitorPointDao;
import com.gas.dao.WarnInfoDao;
import com.gas.dto.WarnInfoDto;
import com.gas.entity.MonitorDevice;
import com.gas.entity.MonitorPoint;
import com.gas.entity.WarnDealInfo;
import com.gas.entity.WarnInfo;
import com.gas.exception.CommonException;
import com.gas.model.WarnDealInfoRequest;
import com.gas.model.WarnInfoRequest;
import com.gas.service.warn.WarnInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WarnInfoServiceImpl implements WarnInfoService {
    @Autowired
    private WarnInfoDao warnInfoDao;
    @Autowired
    private MonitorPointDao monitorPointDao;
    @Autowired
    private MonitorDeviceDao monitorDeviceDao;
    @Override
    public Page<WarnInfoDto> getWarnFormalInfo(WarnInfoRequest request) {
        WarnInfoDto warnInfoDto = this.getWarnInfoDto(request);

        Page<WarnInfoDto> warnInfoDtoPage = warnInfoDao.selectPage(warnInfoDto, request.getCurr(), request.getPageSize(), "正式数据");
        return warnInfoDtoPage;
    }

    @Override
    public Page<WarnInfoDto> getWarnDebugInfo(WarnInfoRequest request) {
        WarnInfoDto warnInfoDto = this.getWarnInfoDto(request);

        Page<WarnInfoDto> warnInfoDtoPage = warnInfoDao.selectPage(warnInfoDto, request.getCurr(), request.getPageSize(), "调试数据");
        return warnInfoDtoPage;
    }

    @Override
    public WarnInfoDto getWarnInfoDetail(WarnInfoRequest request) {
        return warnInfoDao.selectDtoById(request.getId());
    }

    @Override
    @Transactional
    public void confirmWarnInfo(WarnInfoRequest request) {
        WarnInfo warnInfo = warnInfoDao.selectById(request.getId());
        if (!Objects.equals(warnInfo.getWarnStatus(), GlobalConstants.WAIN_INFO_CONFIRM)) {
            log.warn("预警信息当前状态为 {} ,不允许确认", warnInfo.getWarnStatus());
            throw new CommonException(500, "预警信息当前状态不允许确认");
        }
        warnInfo.setWarnStatus(GlobalConstants.WAIN_INFO_DEAL);

        warnInfoDao.updateWarnInfo(warnInfo);
    }

    @Override
    @Transactional
    public void commitWarnInfo(WarnDealInfoRequest request) {
        WarnDealInfo warnDealInfo = new WarnDealInfo();
        BeanUtils.copyProperties(request, warnDealInfo);

        warnInfoDao.addWarnDealInfo(warnDealInfo);

        //添加成功后，将预警信息改为 已处置
        WarnInfo warnInfo = warnInfoDao.selectById(request.getWarnInfoId());
        warnInfo.setWarnStatus(GlobalConstants.WAIN_INFO_DEAL_OK);
        warnInfoDao.updateWarnInfo(warnInfo);
    }


    private WarnInfoDto getWarnInfoDto(WarnInfoRequest request) {
        WarnInfoDto warnInfoDto = new WarnInfoDto();
        BeanUtils.copyProperties(request, warnInfoDto);

        if (StrUtil.isNotBlank(request.getPointName())) {
            List<MonitorPoint> monitorPoints = monitorPointDao.selectByPointName(request.getPointName());
            if (!CollectionUtils.isEmpty(monitorPoints)) {
                List<Integer> pointIds = monitorPoints.stream().map(MonitorPoint::getId).collect(Collectors.toList());
                warnInfoDto.setPointIds(pointIds);
            }
        }

        if (StrUtil.isNotBlank(request.getDeviceName()) || StrUtil.isNotBlank(request.getDeviceNo())) {
            List<MonitorDevice> monitorDevices = monitorDeviceDao.selectByDeviceNameAndNo(request.getDeviceName(), request.getDeviceNo());
            if (CollectionUtils.isEmpty(monitorDevices)) {
                List<Integer> deviceIds = monitorDevices.stream().map(MonitorDevice::getId).collect(Collectors.toList());
                warnInfoDto.setDeviceIds(deviceIds);
            }
        }
        return warnInfoDto;
    }
}
