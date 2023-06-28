package com.gas.service.archive.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.common.GlobalConstants;
import com.gas.dao.MonitorDeviceDao;
import com.gas.dao.MonitorPointDao;
import com.gas.dao.WarnInfoDao;
import com.gas.dto.DeviceAuditDto;
import com.gas.dto.MonitorDeviceDto;
import com.gas.entity.MonitorDevice;
import com.gas.entity.MonitorPoint;
import com.gas.entity.Users;
import com.gas.entity.WarnInfo;
import com.gas.enums.ErrorCodeEnum;
import com.gas.exception.CommonException;
import com.gas.model.MonitorDeviceRequest;
import com.gas.service.archive.MonitorDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MonitorDeviceServiceImpl implements MonitorDeviceService {

    @Autowired
    private MonitorDeviceDao monitorDeviceDao;
    @Autowired
    private MonitorPointDao monitorPointDao;
    @Autowired
    private WarnInfoDao warnInfoDao;

    @Override
    public Page<MonitorDeviceDto> getMonitorDevice(MonitorDeviceRequest request) {
        MonitorDeviceDto monitorDeviceDto = new MonitorDeviceDto();
        BeanUtils.copyProperties(request, monitorDeviceDto);

        return monitorDeviceDao.selectPage(monitorDeviceDto, request.getCurr(), request.getPageSize());
    }

    @Override
    public Page<MonitorDeviceDto> getDeviceManage(MonitorDeviceRequest request) {
        MonitorDeviceDto monitorDeviceDto = new MonitorDeviceDto();
        BeanUtils.copyProperties(request, monitorDeviceDto);

        return monitorDeviceDao.selectPageDeviceManage(monitorDeviceDto, request.getCurr(), request.getPageSize());
    }

    @Override
    public Page<MonitorDeviceDto> getDeviceAudit(MonitorDeviceRequest request) {
        MonitorDeviceDto monitorDeviceDto = new MonitorDeviceDto();
        BeanUtils.copyProperties(request, monitorDeviceDto);

        return monitorDeviceDao.selectPageDeviceAudit(monitorDeviceDto, request.getCurr(), request.getPageSize());
    }

    @Override
    public Page<MonitorDeviceDto> getNaturalAudit(MonitorDeviceRequest request) {
        MonitorDeviceDto monitorDeviceDto = new MonitorDeviceDto();
        BeanUtils.copyProperties(request, monitorDeviceDto);

        return monitorDeviceDao.selectPageNaturalAudit(monitorDeviceDto, request.getCurr(), request.getPageSize());
    }

    @Override
    public Page<MonitorDeviceDto> getLiquefyAudit(MonitorDeviceRequest request) {
        MonitorDeviceDto monitorDeviceDto = new MonitorDeviceDto();
        BeanUtils.copyProperties(request, monitorDeviceDto);

        return monitorDeviceDao.selectPageLiquefyAudit(monitorDeviceDto, request.getCurr(), request.getPageSize());
    }

    @Override
    public List<MonitorDevice> getDeviceList() {
        return monitorDeviceDao.getDeviceList();
    }

    @Override
    @Transactional
    public void addOrUpdate(MonitorDeviceRequest request) {
        MonitorDevice monitorDevice = new MonitorDevice();
        BeanUtils.copyProperties(request, monitorDevice);

        if (Objects.isNull(monitorDevice.getId())) {
            //查询设备编号是否存在
            MonitorDevice device = monitorDeviceDao.selectMonitorDeviceByNo(monitorDevice.getDeviceNo());
            if (!Objects.isNull(device)) {
                log.warn("新增监测设备建档，设备号 {} 已存在，不允许新增", monitorDevice.getDeviceNo());
                throw new CommonException(500, "设备号已存在，不允许新增操作");
            }
            MonitorPoint monitorPoint = monitorPointDao.getById(monitorDevice.getPointId());
            monitorDevice.setProv(monitorPoint.getProv());
            monitorDevice.setCity(monitorPoint.getCity());
            monitorDevice.setArea(monitorPoint.getArea());
            monitorDevice.setVillage(monitorPoint.getVillage());

//            Subject subject = SecurityUtils.getSubject();
//            Users user = (Users) subject.getPrincipal();
            //monitorDevice.setCreator(user.getUsername());
            //新增
            monitorDeviceDao.addMonitorDevice(monitorDevice);
            return;
        }

        MonitorDevice oldMonitorDevice = this.getById(monitorDevice.getId());
        //如果是待审核状态，不允许修改
        if (Objects.equals(oldMonitorDevice.getArchiveStatus(), GlobalConstants.ARCHIVE_CHECK_STATUS)) {
            log.warn("修改监测设备建档，当前状态为 {} ,不允许修改", monitorDevice.getArchiveStatus());
            throw new CommonException(500, "待审核状态数据不允许修改");
        }
        //修改
        monitorDeviceDao.updateMonitorDevice(monitorDevice);
    }

    @Override
    @Transactional
    public void addOrUpdateAll(MonitorDeviceRequest request) {
        MonitorDevice monitorDevice = new MonitorDevice();
        BeanUtils.copyProperties(request, monitorDevice);

        if (Objects.isNull(monitorDevice.getId())) {
            //查询设备编号是否存在
            MonitorDevice device = monitorDeviceDao.selectMonitorDeviceByNo(monitorDevice.getDeviceNo());
            if (!Objects.isNull(device)) {
                log.warn("新增监测设备建档，设备号 {} 已存在，不允许新增", monitorDevice.getDeviceNo());
                throw new CommonException(500, "设备号已存在，不允许新增操作");
            }

//            Subject subject = SecurityUtils.getSubject();
//            Users user = (Users) subject.getPrincipal();
//            monitorDevice.setCreator(user.getUsername());
            //新增
            monitorDeviceDao.addMonitorDevice(monitorDevice);
            return;
        }

        //修改
        monitorDeviceDao.updateMonitorDevice(monitorDevice);
    }

    @Override
    public MonitorDevice getById(Integer id) {
        return monitorDeviceDao.getById(id);
    }

    @Override
    @Transactional
    public void delById(Integer id) {
        MonitorDevice monitorDevice = this.getById(id);
        //如果是待审核状态，不允许删除
        if (Objects.equals(monitorDevice.getArchiveStatus(), GlobalConstants.ARCHIVE_CHECK_STATUS)) {
            log.warn("删除监测设备建档，当前状态为 {} ,不允许删除", monitorDevice.getArchiveStatus());
            throw new CommonException(500, "待审核状态数据不允许删除");
        }

        monitorDeviceDao.delById(monitorDevice);
    }

    @Override
    @Transactional
    public void delByIdAll(Integer id) {
        MonitorDevice monitorDevice = this.getById(id);
        monitorDeviceDao.delById(monitorDevice);
    }

    @Override
    @Transactional
    public void delBatchIdsAll(List<Integer> ids) {
        List<MonitorDevice> list = monitorDeviceDao.selectByIds(ids);
        if (CollectionUtils.isEmpty(list)) {
            log.warn("批量删除监测设备建档，查询数据为空");
            throw new CommonException(ErrorCodeEnum.RELATED_RESOURCE_NOT_FOUND);
        }

        monitorDeviceDao.delBatchIds(list);
    }

    @Override
    @Transactional
    public void delBatchIds(List<Integer> ids) {
        List<MonitorDevice> list = monitorDeviceDao.selectByIds(ids);
        if (CollectionUtils.isEmpty(list)) {
            log.warn("批量删除监测设备建档，查询数据为空");
            throw new CommonException(ErrorCodeEnum.RELATED_RESOURCE_NOT_FOUND);
        }

        //查询设备状态中，是否包含待审核数据，如果包含，不允许删除
        boolean anyMatch = list.stream().anyMatch(e -> Objects.equals(e.getArchiveStatus(), GlobalConstants.ARCHIVE_CHECK_STATUS));
        if (anyMatch) {
            log.warn("批量删除监测设备建档，存在待审核数据集 ,不允许删除, list={}", list);
            throw new CommonException(500, "待审核状态数据不允许删除");
        }

        monitorDeviceDao.delBatchIds(list);
    }

    @Override
    @Transactional
    public void commit(Integer id) {
        MonitorDevice monitorDevice = this.getById(id);
        if (monitorDevice == null) {
            log.warn("提交设备不存在id={}", id);
            throw new CommonException(ErrorCodeEnum.RESOURCE_NOT_FOUND);
        }

        //如果当前状态为待审核 或 已通过，不允许提交
        if (Objects.equals(monitorDevice.getArchiveStatus(), GlobalConstants.ARCHIVE_CHECK_STATUS)
                || Objects.equals(monitorDevice.getArchiveStatus(), GlobalConstants.ARCHIVE_PASS_STATUS)) {
            log.warn("提交监测设备建档，当前状态为 {} ,不允许提交", monitorDevice.getArchiveStatus());
            throw new CommonException(500, "当前数据状态不允许提交");
        }

        //查询设备对应点位
        MonitorPoint monitorPoint = monitorPointDao.getById(monitorDevice.getPointId());

        //修改设备为待审核
        monitorDevice.setArchiveStatus("待审核");
        monitorDeviceDao.updateMonitorDevice(monitorDevice);

        //修改点位为待审核
        monitorPoint.setArchiveStatus("待审核");
        monitorPointDao.updateMonitorPoint(monitorPoint);
    }

    @Override
    @Transactional
    public void commitBatchIds(List<Integer> ids) {
        List<MonitorDevice> monitorDevices = monitorDeviceDao.selectByIds(ids);
        if (CollectionUtils.isEmpty(monitorDevices)) {
            log.warn("提交设备不存在ids={}", ids);
            throw new CommonException(ErrorCodeEnum.RESOURCE_NOT_FOUND);
        }

        //如果当前状态为待审核 或 已通过，不允许提交
        boolean anyMatch = monitorDevices.stream().anyMatch(
                e -> (Objects.equals(e.getArchiveStatus(), GlobalConstants.ARCHIVE_CHECK_STATUS)
                    || Objects.equals(e.getArchiveStatus(), GlobalConstants.ARCHIVE_PASS_STATUS))
        );
        if (anyMatch) {
            log.warn("提交监测设备建档，存在非待提交数据 ,不允许提交");
            throw new CommonException(500, "非待提交状态数据不允许提交");
        }

        //查询设备对应点位
        List<Integer> pointIds = monitorDevices.stream().map(MonitorDevice::getPointId).collect(Collectors.toList());
        List<MonitorPoint> monitorPoints = monitorPointDao.selectByIds(pointIds);

        //修改设备为待审核
        for (MonitorDevice monitorDevice : monitorDevices) {
            monitorDevice.setArchiveStatus("待审核");
            monitorDeviceDao.updateMonitorDevice(monitorDevice);
        }

        //修改点位为待审核
        for (MonitorPoint monitorPoint : monitorPoints) {
            monitorPoint.setArchiveStatus("待审核");
            monitorPointDao.updateMonitorPoint(monitorPoint);
        }
    }

    @Override
    @Transactional
    public void resumeBatchIds(List<Integer> ids) {
        List<MonitorDevice> monitorDevices = monitorDeviceDao.selectByIds(ids);
        if (CollectionUtils.isEmpty(monitorDevices)) {
            log.warn("恢复设备不存在ids={}", ids);
            throw new CommonException(ErrorCodeEnum.RESOURCE_NOT_FOUND);
        }



        //查询设备对应点位
        List<Integer> pointIds = monitorDevices.stream().map(MonitorDevice::getPointId).collect(Collectors.toList());
        List<MonitorPoint> monitorPoints = monitorPointDao.selectByIds(pointIds);

        //修改设备状态为正常
        for (MonitorDevice monitorDevice : monitorDevices) {
            monitorDevice.setDeviceStatus("正常");
            monitorDeviceDao.updateMonitorDevice(monitorDevice);
        }

        //修改点位状态为正常
        for (MonitorPoint monitorPoint : monitorPoints) {
            monitorPoint.setPointStatus("正常");
            monitorPointDao.updateMonitorPoint(monitorPoint);
        }
    }

    @Override
    public DeviceAuditDto doAudit(MonitorDeviceRequest request) {
        DeviceAuditDto deviceAuditDto = new DeviceAuditDto();

        //根据id查询设备信息
        MonitorDevice monitorDevice = monitorDeviceDao.getById(request.getId());
        if (Objects.isNull(monitorDevice)) {
            log.warn("设备信息不存在 id = {}", request.getId());
            throw new CommonException(ErrorCodeEnum.RESOURCE_NOT_FOUND);
        }

        //根据设备对应的点位id，查询点位信息
        MonitorPoint monitorPoint = monitorPointDao.getById(monitorDevice.getPointId());

        //根据设备id，查询预警信息
        List<WarnInfo> warnInfos = warnInfoDao.selectByDeviceId(monitorDevice.getId());
        if (!CollectionUtils.isEmpty(warnInfos)) {
            List<DeviceAuditDto.DeviceWarnInfo> list = new ArrayList<>();
            for (WarnInfo warnInfo : warnInfos) {
                DeviceAuditDto.DeviceWarnInfo deviceWarnInfo = new DeviceAuditDto.DeviceWarnInfo();
                BeanUtils.copyProperties(warnInfo, deviceWarnInfo);
                deviceWarnInfo.setDeviceName(monitorDevice.getDeviceName());

                list.add(deviceWarnInfo);
            }
            deviceAuditDto.setDeviceWarnInfo(list);
        }
        deviceAuditDto.setMonitorDevice(monitorDevice);
        deviceAuditDto.setMonitorPoint(monitorPoint);
        return deviceAuditDto;
    }
}
