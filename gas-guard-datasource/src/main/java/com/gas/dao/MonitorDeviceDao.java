package com.gas.dao;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.common.GlobalConstants;
import com.gas.dao.mapper.MonitorDeviceMapper;
import com.gas.dao.mapper.MonitorPointMapper;
import com.gas.dto.MonitorDeviceDto;
import com.gas.entity.MonitorDevice;
import com.gas.entity.MonitorPoint;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class MonitorDeviceDao {

    @Autowired
    private MonitorDeviceMapper deviceMapper;
    @Autowired
    private MonitorPointMapper pointMapper;

    public List<MonitorDevice> getByPointId(Integer id) {
        QueryWrapper<MonitorDevice> wrapper = new QueryWrapper<>();
        wrapper.eq("point_id", id);
        wrapper.eq("enable", true);

        List<MonitorDevice> monitorDevices = deviceMapper.selectList(wrapper);
        return monitorDevices;
    }

    public Page<MonitorDeviceDto> selectPage(MonitorDeviceDto monitorDeviceDto, Integer curr, Integer pageSize) {
        //查询第curr页，每页pageSize条
        Page<MonitorDevice> page = new Page<>(curr,pageSize);

        QueryWrapper<MonitorDevice> wrapper = new QueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(monitorDeviceDto.getPointName()), "point_name", monitorDeviceDto.getPointName());
        wrapper.eq(StrUtil.isNotBlank(monitorDeviceDto.getDeviceName()), "device_name", monitorDeviceDto.getDeviceName());
        wrapper.like(StrUtil.isNotBlank(monitorDeviceDto.getAddress()), "address", monitorDeviceDto.getAddress());
        wrapper.eq(StrUtil.isNotBlank(monitorDeviceDto.getDeviceNo()), "device_no", monitorDeviceDto.getDeviceNo());
        wrapper.eq(StrUtil.isNotBlank(monitorDeviceDto.getDeviceType()), "device_type", monitorDeviceDto.getDeviceType());
        wrapper.eq(StrUtil.isNotBlank(monitorDeviceDto.getDeviceFactory()), "device_factory", monitorDeviceDto.getDeviceFactory());
        wrapper.eq(StrUtil.isNotBlank(monitorDeviceDto.getDeviceModel()), "device_model", monitorDeviceDto.getDeviceModel());
        wrapper.eq(StrUtil.isNotBlank(monitorDeviceDto.getDeviceStatus()), "device_status", monitorDeviceDto.getDeviceStatus());
        wrapper.eq(StrUtil.isNotBlank(monitorDeviceDto.getArchiveStatus()), "archive_status", monitorDeviceDto.getArchiveStatus());
        wrapper.ge(monitorDeviceDto.getCreateTime() != null, "create_time", monitorDeviceDto.getCreateTime());
        wrapper.le(monitorDeviceDto.getEndTime() != null, "create_time", monitorDeviceDto.getEndTime());

        //查询可用数据
        wrapper.eq("enable", 1);
        //不包含已通过数据
        wrapper.ne("archive_status", GlobalConstants.ARCHIVE_PASS_STATUS);
        //查设备
        Page<MonitorDevice> monitorDevicePage = deviceMapper.selectPage(page, wrapper);

        if (CollectionUtils.isEmpty(monitorDevicePage.getRecords())) {
            return new Page<>();
        }

        Page<MonitorDeviceDto> pages = new Page<>();
        List<MonitorDeviceDto> list = new ArrayList<>();
        //查点位
        for (MonitorDevice record : monitorDevicePage.getRecords()) {
            MonitorDeviceDto deviceDto = new MonitorDeviceDto();
            BeanUtils.copyProperties(record, deviceDto);

            QueryWrapper<MonitorPoint> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", deviceDto.getPointId());
            MonitorPoint monitorPoint = pointMapper.selectOne(queryWrapper);

            deviceDto.setGasCompany(monitorPoint.getGasCompany());
            deviceDto.setGasType(monitorPoint.getGasType());
            deviceDto.setUserType(monitorPoint.getUserType());
            deviceDto.setPointType(monitorPoint.getPointType());

            list.add(deviceDto);
        }

        //针对 点位类型，供气企业，燃气种类，用户种类 特殊处理
        List<MonitorDeviceDto> collect = list.stream().filter(e -> StrUtil.isNotBlank(monitorDeviceDto.getGasCompany()) && Objects.equals(monitorDeviceDto.getGasCompany(), e.getGasCompany()))
                .filter(e -> StrUtil.isNotBlank(monitorDeviceDto.getGasType()) && Objects.equals(monitorDeviceDto.getGasType(), e.getGasType()))
                .filter(e -> StrUtil.isNotBlank(monitorDeviceDto.getUserType()) && Objects.equals(monitorDeviceDto.getUserType(), e.getUserType()))
                .filter(e -> StrUtil.isNotBlank(monitorDeviceDto.getPointType()) && Objects.equals(monitorDeviceDto.getPointType(), e.getPointType()))
                .collect(Collectors.toList());

        pages.setRecords(collect);
        pages.setTotal(monitorDevicePage.getTotal());
        pages.setSize(monitorDevicePage.getSize());
        pages.setCurrent(monitorDevicePage.getCurrent());
        pages.setPages(monitorDevicePage.getPages());
        return pages;
    }
}
