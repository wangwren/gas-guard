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

@Component
public class MonitorDeviceDao {

    @Autowired
    private MonitorDeviceMapper deviceMapper;
    @Autowired
    private MonitorPointMapper pointMapper;

    /**
     * 根据点位 id 查询设备
     */
    public List<MonitorDevice> getByPointId(Integer id) {
        QueryWrapper<MonitorDevice> wrapper = new QueryWrapper<>();
        wrapper.eq("point_id", id);
        wrapper.eq("enable", true);

        List<MonitorDevice> monitorDevices = deviceMapper.selectList(wrapper);
        return monitorDevices;
    }

    public List<MonitorDevice> getByPointByPointIds(List<Integer> ids) {
        QueryWrapper<MonitorDevice> wrapper = new QueryWrapper<>();
        wrapper.in("point_id", ids);
        wrapper.eq("enable", true);

        List<MonitorDevice> monitorDevices = deviceMapper.selectList(wrapper);
        return monitorDevices;
    }

    public Page<MonitorDeviceDto> selectPage(MonitorDeviceDto monitorDeviceDto, Integer curr, Integer pageSize) {
        //查询第curr页，每页pageSize条
        Page<MonitorDevice> page = new Page<>(curr,pageSize);

        QueryWrapper<MonitorDevice> wrapper = getDeviceQueryWrapper(monitorDeviceDto);
        //不包含已通过数据
        wrapper.ne("archive_status", GlobalConstants.ARCHIVE_PASS_STATUS);
        wrapper.orderByDesc("create_time");
        //查设备
        Page<MonitorDevice> monitorDevicePage = deviceMapper.selectPage(page, wrapper);

        if (CollectionUtils.isEmpty(monitorDevicePage.getRecords())) {
            return new Page<>();
        }

        //查点位,整合最终数据
        Page<MonitorDeviceDto> pages = getMonitorDeviceDtoPage(monitorDeviceDto, monitorDevicePage ,false, false);
        return pages;
    }

    public Page<MonitorDeviceDto> selectPageDeviceManage(MonitorDeviceDto monitorDeviceDto, Integer curr, Integer pageSize) {
        //查询第curr页，每页pageSize条
        Page<MonitorDevice> page = new Page<>(curr,pageSize);

        QueryWrapper<MonitorDevice> wrapper = getDeviceQueryWrapper(monitorDeviceDto);
        //查设备
        Page<MonitorDevice> monitorDevicePage = deviceMapper.selectPage(page, wrapper);

        if (CollectionUtils.isEmpty(monitorDevicePage.getRecords())) {
            return new Page<>();
        }

        //查点位
        Page<MonitorDeviceDto> pages = getMonitorDeviceDtoPage(monitorDeviceDto, monitorDevicePage, false, false);
        return pages;
    }

    public Page<MonitorDeviceDto> selectPageDeviceAudit(MonitorDeviceDto monitorDeviceDto, Integer curr, Integer pageSize) {
        //查询第curr页，每页pageSize条
        Page<MonitorDevice> page = new Page<>(curr,pageSize);

        QueryWrapper<MonitorDevice> wrapper = getDeviceQueryWrapper(monitorDeviceDto);
        //只查询待审核数据
        wrapper.eq("archive_status", GlobalConstants.ARCHIVE_CHECK_STATUS);
        wrapper.orderByDesc("create_time");
        //查设备
        Page<MonitorDevice> monitorDevicePage = deviceMapper.selectPage(page, wrapper);

        if (CollectionUtils.isEmpty(monitorDevicePage.getRecords())) {
            return new Page<>();
        }

        //查点位,整合最终数据
        Page<MonitorDeviceDto> pages = getMonitorDeviceDtoPage(monitorDeviceDto, monitorDevicePage, false, false);
        return pages;
    }

    public Page<MonitorDeviceDto> selectPageNaturalAudit(MonitorDeviceDto monitorDeviceDto, Integer curr, Integer pageSize) {
        //查询第curr页，每页pageSize条
        Page<MonitorDevice> page = new Page<>(curr,pageSize);

        QueryWrapper<MonitorDevice> wrapper = getDeviceQueryWrapper(monitorDeviceDto);
        //只查询待审核数据
        wrapper.eq("archive_status", GlobalConstants.ARCHIVE_CHECK_STATUS);
        wrapper.orderByDesc("create_time");
        //查设备
        Page<MonitorDevice> monitorDevicePage = deviceMapper.selectPage(page, wrapper);

        if (CollectionUtils.isEmpty(monitorDevicePage.getRecords())) {
            return new Page<>();
        }

        //查点位,整合最终数据
        Page<MonitorDeviceDto> pages = getMonitorDeviceDtoPage(monitorDeviceDto, monitorDevicePage, true, false);
        return pages;
    }

    public Page<MonitorDeviceDto> selectPageLiquefyAudit(MonitorDeviceDto monitorDeviceDto, Integer curr, Integer pageSize) {
        //查询第curr页，每页pageSize条
        Page<MonitorDevice> page = new Page<>(curr,pageSize);

        QueryWrapper<MonitorDevice> wrapper = getDeviceQueryWrapper(monitorDeviceDto);
        //只查询待审核数据
        wrapper.eq("archive_status", GlobalConstants.ARCHIVE_CHECK_STATUS);
        wrapper.orderByDesc("create_time");
        //查设备
        Page<MonitorDevice> monitorDevicePage = deviceMapper.selectPage(page, wrapper);

        if (CollectionUtils.isEmpty(monitorDevicePage.getRecords())) {
            return new Page<>();
        }

        //查点位,整合最终数据
        Page<MonitorDeviceDto> pages = getMonitorDeviceDtoPage(monitorDeviceDto, monitorDevicePage, false, true);
        return pages;
    }

    private static QueryWrapper<MonitorDevice> getDeviceQueryWrapper(MonitorDeviceDto monitorDeviceDto) {
        QueryWrapper<MonitorDevice> wrapper = new QueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(monitorDeviceDto.getPointName()), "point_name", monitorDeviceDto.getPointName());
        wrapper.like(StrUtil.isNotBlank(monitorDeviceDto.getDeviceName()), "device_name", monitorDeviceDto.getDeviceName());
        wrapper.like(StrUtil.isNotBlank(monitorDeviceDto.getAddress()), "address", monitorDeviceDto.getAddress());
        wrapper.eq(StrUtil.isNotBlank(monitorDeviceDto.getDeviceNo()), "device_no", monitorDeviceDto.getDeviceNo());
        wrapper.eq(StrUtil.isNotBlank(monitorDeviceDto.getDeviceType()), "device_type", monitorDeviceDto.getDeviceType());
        wrapper.eq(StrUtil.isNotBlank(monitorDeviceDto.getDeviceFactory()), "device_factory", monitorDeviceDto.getDeviceFactory());
        wrapper.eq(StrUtil.isNotBlank(monitorDeviceDto.getDeviceModel()), "device_model", monitorDeviceDto.getDeviceModel());
        wrapper.eq(StrUtil.isNotBlank(monitorDeviceDto.getDeviceStatus()), "device_status", monitorDeviceDto.getDeviceStatus());
        wrapper.eq(StrUtil.isNotBlank(monitorDeviceDto.getArchiveStatus()), "archive_status", monitorDeviceDto.getArchiveStatus());
        wrapper.ge(monitorDeviceDto.getCreateTime() != null, "create_time", monitorDeviceDto.getCreateTime());
        wrapper.le(monitorDeviceDto.getEndTime() != null, "create_time", monitorDeviceDto.getEndTime());
        wrapper.orderByDesc("create_time");

        //查询可用数据
        wrapper.eq("enable", 1);
        return wrapper;
    }

    /**
     * 查询出的设备，再去查对应点位信息
     * @param natural  是否天然气
     * @param liquefy  是否液化气
     */
    private Page<MonitorDeviceDto> getMonitorDeviceDtoPage(MonitorDeviceDto monitorDeviceDto, Page<MonitorDevice> monitorDevicePage
            , Boolean natural, Boolean liquefy) {
        Page<MonitorDeviceDto> pages = new Page<>();
        List<MonitorDeviceDto> list = new ArrayList<>();
        //查点位
        for (MonitorDevice record : monitorDevicePage.getRecords()) {
            MonitorDeviceDto deviceDto = new MonitorDeviceDto();
            BeanUtils.copyProperties(record, deviceDto);

            QueryWrapper<MonitorPoint> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", deviceDto.getPointId());
            //针对 点位类型，供气企业，燃气种类，用户种类 特殊处理
            queryWrapper.eq(StrUtil.isNotBlank(monitorDeviceDto.getPointType()), "point_type", monitorDeviceDto.getPointType());
            queryWrapper.eq(StrUtil.isNotBlank(monitorDeviceDto.getGasCompany()), "gas_company", monitorDeviceDto.getGasCompany());
            queryWrapper.eq(StrUtil.isNotBlank(monitorDeviceDto.getGasType()), "gas_type", monitorDeviceDto.getGasType());
            queryWrapper.eq(StrUtil.isNotBlank(monitorDeviceDto.getUserType()), "user_type", monitorDeviceDto.getUserType());
            queryWrapper.orderByDesc("create_time");
            if (natural) {
                queryWrapper.eq("gas_type", "天然气");
            }

            if (liquefy) {
                queryWrapper.eq("gas_type", "液化石油气");
            }
            MonitorPoint monitorPoint = pointMapper.selectOne(queryWrapper);
            if (!Objects.isNull(monitorPoint)) {
                deviceDto.setGasCompany(monitorPoint.getGasCompany());
                deviceDto.setGasType(monitorPoint.getGasType());
                deviceDto.setUserType(monitorPoint.getUserType());
                deviceDto.setPointType(monitorPoint.getPointType());

                list.add(deviceDto);
            }
        }

        pages.setRecords(list);
        pages.setTotal(list.size());
        pages.setSize(list.size());
        pages.setCurrent(monitorDevicePage.getCurrent());
        pages.setPages(monitorDevicePage.getPages());
        return pages;
    }

    /**
     * 根据设备编号查询设备
     */
    public MonitorDevice selectMonitorDeviceByNo(String deviceNo) {
        QueryWrapper<MonitorDevice> wrapper = new QueryWrapper<>();
        wrapper.eq("device_no", deviceNo);
        return deviceMapper.selectOne(wrapper);
    }

    public void addMonitorDevice(MonitorDevice monitorDevice) {
        deviceMapper.insert(monitorDevice);
    }

    public void updateMonitorDevice(MonitorDevice monitorDevice) {
        deviceMapper.updateById(monitorDevice);
    }

    public MonitorDevice getById(Integer id) {
        return deviceMapper.selectById(id);
    }

    public void delById(MonitorDevice monitorDevice) {
        monitorDevice.setEnable(false);
        deviceMapper.updateById(monitorDevice);
    }

    public void delBatchIds(List<MonitorDevice> monitorDevices) {
        for (MonitorDevice monitorDevice : monitorDevices) {
            monitorDevice.setEnable(false);
            deviceMapper.updateById(monitorDevice);
        }
    }

    public List<MonitorDevice> selectByIds(List<Integer> ids) {
        List<MonitorDevice> monitorDevices = deviceMapper.selectBatchIds(ids);
        if (CollectionUtils.isEmpty(monitorDevices)) {
            return null;
        }
        return monitorDevices;
    }

    public List<MonitorDevice> selectByDeviceNameAndNo(String deviceName, String deviceNo) {
        QueryWrapper<MonitorDevice> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(deviceName), "device_name", deviceName);
        wrapper.eq(StrUtil.isNotBlank(deviceNo), "device_no", deviceNo);
        List<MonitorDevice> monitorDevices = deviceMapper.selectList(wrapper);
        return monitorDevices;
    }

    public List<MonitorDevice> getDeviceList() {
        List<MonitorDevice> monitorDevices = deviceMapper.selectList(null);
        return monitorDevices;
    }

    public List<MonitorDevice> selectByCond(MonitorDeviceDto deviceDto) {
        QueryWrapper<MonitorDevice> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(deviceDto.getDeviceType()), "device_type", deviceDto.getDeviceType());
        wrapper.eq(StrUtil.isNotBlank(deviceDto.getDeviceFactory()), "device_factory", deviceDto.getDeviceFactory());
        wrapper.eq(StrUtil.isNotBlank(deviceDto.getDeviceModel()), "device_model", deviceDto.getDeviceModel());
        wrapper.ge(deviceDto.getCreateTime() != null, "create_time", deviceDto.getCreateTime());
        wrapper.le(deviceDto.getEndTime() != null, "create_time", deviceDto.getEndTime());

        List<MonitorDevice> monitorDevices = deviceMapper.selectList(wrapper);
        return monitorDevices;
    }

    public List<MonitorDevice> selectByIdsCond(List<Integer> deviceIds, MonitorDeviceDto deviceDto) {
        QueryWrapper<MonitorDevice> wrapper = new QueryWrapper<>();
        wrapper.in("id", deviceIds);
        wrapper.eq(StrUtil.isNotBlank(deviceDto.getDeviceType()), "device_type", deviceDto.getDeviceType());
        wrapper.eq(StrUtil.isNotBlank(deviceDto.getArchiveStatus()), "archive_status", deviceDto.getArchiveStatus());
        wrapper.eq(StrUtil.isNotBlank(deviceDto.getDeviceStatus()), "device_status", deviceDto.getDeviceStatus());

        wrapper.eq(StrUtil.isNotBlank(deviceDto.getPointName()), "point_name", deviceDto.getPointName());
        wrapper.eq(StrUtil.isNotBlank(deviceDto.getOrganName()), "organ_name", deviceDto.getOrganName());
        wrapper.eq(StrUtil.isNotBlank(deviceDto.getDeviceNo()), "device_no", deviceDto.getDeviceNo());
        wrapper.eq(StrUtil.isNotBlank(deviceDto.getDeviceName()), "device_name", deviceDto.getDeviceName());

        List<MonitorDevice> monitorDevices = deviceMapper.selectList(wrapper);

        return monitorDevices;
    }
}
