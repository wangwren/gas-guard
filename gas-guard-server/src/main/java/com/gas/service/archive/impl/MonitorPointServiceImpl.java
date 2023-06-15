package com.gas.service.archive.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.common.GlobalConstants;
import com.gas.dao.MonitorDeviceDao;
import com.gas.dao.MonitorPointDao;
import com.gas.dto.FormalPointDto;
import com.gas.dto.MonitorPointDto;
import com.gas.entity.MonitorDevice;
import com.gas.entity.MonitorPoint;
import com.gas.entity.Users;
import com.gas.enums.ErrorCodeEnum;
import com.gas.exception.CommonException;
import com.gas.model.MonitorPointRequest;
import com.gas.service.archive.MonitorPointService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class MonitorPointServiceImpl implements MonitorPointService {

    @Autowired
    private MonitorPointDao monitorPointDao;
    @Autowired
    private MonitorDeviceDao monitorDeviceDao;

    /**
     * 查询监测点位建档，不包含已通过数据
     */
    @Override
    public Page<MonitorPoint> getMonitorPoint(MonitorPointRequest request) {
        MonitorPointDto monitorPoint = new MonitorPointDto();
        BeanUtils.copyProperties(request, monitorPoint);

        return monitorPointDao.selectPage(monitorPoint, request.getCurr(), request.getPageSize());
    }

    /**
     * 查询监测点位设备，包含所有状态数据
     * 新增无设备，多设备条件
     */
    @Override
    public Page<MonitorPoint> getPointManage(MonitorPointRequest request) {
        MonitorPointDto monitorPoint = new MonitorPointDto();
        BeanUtils.copyProperties(request, monitorPoint);

        return monitorPointDao.selectPagePointManage(monitorPoint, request.getCurr(), request.getPageSize());
    }

    @Override
    public Page<MonitorPoint> getFormalPoint(MonitorPointRequest request) {
        MonitorPointDto monitorPoint = new MonitorPointDto();
        BeanUtils.copyProperties(request, monitorPoint);

        return monitorPointDao.selectPageFormalPoint(monitorPoint, request.getCurr(), request.getPageSize());
    }

    @Override
    public Page<MonitorPoint> getMonitorPointAll(MonitorPointRequest request) {
        MonitorPointDto monitorPoint = new MonitorPointDto();
        BeanUtils.copyProperties(request, monitorPoint);

        return monitorPointDao.selectPageAll(monitorPoint, request.getCurr(), request.getPageSize());
    }

    @Override
    public FormalPointDto getPointDeviceById(Integer id) {
        FormalPointDto formalPointDto = new FormalPointDto();
        MonitorPoint monitorPoint = this.getById(id);
        List<MonitorDevice> monitorDevices = monitorDeviceDao.getByPointId(monitorPoint.getId());

        formalPointDto.setMonitorPoint(monitorPoint);
        formalPointDto.setMonitorDevices(monitorDevices);
        return formalPointDto;
    }

    @Override
    @Transactional
    public void addOrUpdate(MonitorPointRequest request) {
        MonitorPoint monitorPoint = new MonitorPoint();
        BeanUtils.copyProperties(request, monitorPoint);

        if (Objects.isNull(monitorPoint.getId())) {
//            Subject subject = SecurityUtils.getSubject();
//            Users user = (Users) subject.getPrincipal();
//            monitorPoint.setCreator(user.getUsername());
            //新增
            monitorPointDao.addMonitorPoint(monitorPoint);
            return;
        }

        MonitorPoint oldMonitorPoint = this.getById(monitorPoint.getId());
        //如果是待审核状态，不允许修改
        if (Objects.equals(oldMonitorPoint.getArchiveStatus(), GlobalConstants.ARCHIVE_CHECK_STATUS)) {
            log.warn("修改监测点位建档，当前状态为 {} ,不允许修改", monitorPoint.getArchiveStatus());
            throw new CommonException(500, "待审核状态数据不允许修改");
        }
        //修改
        monitorPointDao.updateMonitorPoint(monitorPoint);
        //点位修改，对应点位下的设备也要进行修改
        this.updatePoint2Device(monitorPoint);
    }

    /**
     * 监测点位管理，任何状态都可修改
     */
    @Override
    @Transactional
    public void addOrUpdateAll(MonitorPointRequest request) {
        MonitorPoint monitorPoint = new MonitorPoint();
        BeanUtils.copyProperties(request, monitorPoint);

        if (Objects.isNull(monitorPoint.getId())) {
//            Subject subject = SecurityUtils.getSubject();
//            Users user = (Users) subject.getPrincipal();
//            monitorPoint.setCreator(user.getUsername());
            //新增
            monitorPointDao.addMonitorPoint(monitorPoint);
            return;
        }

        //修改
        monitorPointDao.updateMonitorPoint(monitorPoint);
        //点位修改，对应点位下的设备也要进行修改
        this.updatePoint2Device(monitorPoint);
    }

    /**
     * 修改对应点位下的设备
     */
    private void updatePoint2Device(MonitorPoint monitorPoint) {
        //点位修改，对应点位下的设备，对应点位名称，组织名称，详细地址，行政区划也要做对应修改
        List<MonitorDevice> monitorDevices = monitorDeviceDao.getByPointId(monitorPoint.getId());
        if (CollectionUtils.isEmpty(monitorDevices)) {
            return;
        }

        for (MonitorDevice monitorDevice : monitorDevices) {
            monitorDevice.setPointName(monitorPoint.getPointName());
            monitorDevice.setOrganName(monitorPoint.getOrganName());
            monitorDevice.setAddress(monitorPoint.getAddress());
            monitorDevice.setProvince(monitorPoint.getProvince());
            monitorDeviceDao.updateMonitorDevice(monitorDevice);
        }
    }

    @Override
    public MonitorPoint getById(Integer id) {
        return monitorPointDao.getById(id);
    }

    @Override
    @Transactional
    public void delById(Integer id) {
        MonitorPoint monitorPoint = this.getById(id);
        //如果是待审核状态，不允许删除
        if (Objects.equals(monitorPoint.getArchiveStatus(), GlobalConstants.ARCHIVE_CHECK_STATUS)) {
            log.warn("删除监测点位建档，当前状态为 {} ,不允许删除", monitorPoint.getArchiveStatus());
            throw new CommonException(500, "待审核状态数据不允许删除");
        }

        //如果点位上绑有设备，不允许删除
        List<MonitorDevice> monitorDevices = monitorDeviceDao.getByPointId(id);
        if (!CollectionUtils.isEmpty(monitorDevices)) {
            log.warn("删除监测点位建档，当前点位绑有设备 ,不允许删除");
            throw new CommonException(500, "当前点位绑有设备 ,不允许删除");
        }

        monitorPointDao.delById(monitorPoint);
    }

    @Override
    @Transactional
    public void delByIdAll(Integer id) {
        MonitorPoint monitorPoint = this.getById(id);

        //如果点位上绑有设备，不允许删除
        List<MonitorDevice> monitorDevices = monitorDeviceDao.getByPointId(id);
        if (!CollectionUtils.isEmpty(monitorDevices)) {
            log.warn("删除监测点位建档，当前点位绑有设备 ,不允许删除");
            throw new CommonException(500, "当前点位绑有设备 ,不允许删除");
        }

        monitorPointDao.delById(monitorPoint);
    }

    @Override
    @Transactional
    public void delBatchIds(List<Integer> ids) {
        List<MonitorPoint> list = monitorPointDao.selectByIds(ids);
        if (CollectionUtils.isEmpty(list)) {
            log.warn("批量删除监测点位建档，查询数据为空");
            throw new CommonException(ErrorCodeEnum.RELATED_RESOURCE_NOT_FOUND);
        }

        //查询设备状态中，是否包含待审核数据，如果包含，不允许删除
        boolean anyMatch = list.stream().anyMatch(e -> Objects.equals(e.getArchiveStatus(), GlobalConstants.ARCHIVE_CHECK_STATUS));
        if (anyMatch) {
            log.warn("批量删除监测点位建档，存在待审核数据集 ,不允许删除, list={}", list);
            throw new CommonException(500, "待审核状态数据不允许删除");
        }

        //如果点位上绑有设备，不允许删除
        List<MonitorDevice> monitorDevices = monitorDeviceDao.getByPointByPointIds(ids);
        if (!CollectionUtils.isEmpty(monitorDevices)) {
            log.warn("删除监测点位建档，当前点位绑有设备 ,不允许删除");
            throw new CommonException(500, "当前点位绑有设备 ,不允许删除");
        }

        monitorPointDao.delBatchIds(list);
    }

    @Override
    @Transactional
    public void delBatchIdsAll(List<Integer> ids) {
        List<MonitorPoint> list = monitorPointDao.selectByIds(ids);
        if (CollectionUtils.isEmpty(list)) {
            log.warn("批量删除监测点位建档，查询数据为空");
            throw new CommonException(ErrorCodeEnum.RELATED_RESOURCE_NOT_FOUND);
        }

        //如果点位上绑有设备，不允许删除
        List<MonitorDevice> monitorDevices = monitorDeviceDao.getByPointByPointIds(ids);
        if (!CollectionUtils.isEmpty(monitorDevices)) {
            log.warn("删除监测点位建档，当前点位绑有设备 ,不允许删除");
            throw new CommonException(500, "当前点位绑有设备 ,不允许删除");
        }

        monitorPointDao.delBatchIds(list);
    }
}
