package com.gas.service.archive.impl;

import com.gas.dao.AuditInfoDao;
import com.gas.dao.MonitorDeviceDao;
import com.gas.dao.MonitorPointDao;
import com.gas.entity.AuditInfo;
import com.gas.entity.MonitorDevice;
import com.gas.entity.MonitorPoint;
import com.gas.entity.Users;
import com.gas.model.AuditingtRequest;
import com.gas.model.BatchIdsRequest;
import com.gas.model.NoPassBatchIdsRequest;
import com.gas.service.archive.DeviceAuditService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeviceAuditServiceImpl implements DeviceAuditService {

    @Autowired
    private AuditInfoDao auditInfoDao;
    @Autowired
    private MonitorPointDao pointDao;
    @Autowired
    private MonitorDeviceDao deviceDao;

    @Override
    @Transactional
    public void auditing(AuditingtRequest request) {

        AuditInfo auditInfo = new AuditInfo();
        BeanUtils.copyProperties(request, auditInfo);

        Subject subject = SecurityUtils.getSubject();
        Users user = (Users) subject.getPrincipal();

        auditInfo.setCreator(user.getUsername());
        auditInfoDao.add(auditInfo);

        MonitorPoint monitorPoint = pointDao.getById(request.getPointId());
        MonitorDevice monitorDevice = deviceDao.getById(request.getDeviceId());
        //审核通过，设备、点位 状态都已通过
        monitorPoint.setPointStatus("已通过");
        monitorDevice.setDeviceStatus("已通过");

        //审核不通过，设备、点位 状态都 未通过
        monitorPoint.setPointStatus("未通过");
        monitorDevice.setDeviceStatus("未通过");

        pointDao.updateMonitorPoint(monitorPoint);
        deviceDao.updateMonitorDevice(monitorDevice);
    }

    @Override
    @Transactional
    public void passBatchIds(BatchIdsRequest request) {
        Subject subject = SecurityUtils.getSubject();
        Users user = (Users) subject.getPrincipal();

        for (Integer id : request.getIds()) {
            AuditInfo auditInfo = new AuditInfo();
            MonitorDevice monitorDevice = deviceDao.getById(id);
            MonitorPoint monitorPoint = pointDao.getById(monitorDevice.getPointId());

            auditInfo.setDeviceId(monitorDevice.getId());
            auditInfo.setPointId(monitorPoint.getId());
            auditInfo.setAuditStatus("通过");
            auditInfo.setCreator(user.getUsername());

            auditInfoDao.add(auditInfo);

            //审核通过，设备、点位 状态都已通过
            monitorPoint.setPointStatus("已通过");
            monitorDevice.setDeviceStatus("已通过");

            pointDao.updateMonitorPoint(monitorPoint);
            deviceDao.updateMonitorDevice(monitorDevice);
        }
    }

    @Override
    @Transactional
    public void noPassBatchIds(NoPassBatchIdsRequest request) {
        Subject subject = SecurityUtils.getSubject();
        Users user = (Users) subject.getPrincipal();

        for (Integer id : request.getIds()) {
            AuditInfo auditInfo = new AuditInfo();
            MonitorDevice monitorDevice = deviceDao.getById(id);
            MonitorPoint monitorPoint = pointDao.getById(monitorDevice.getPointId());

            auditInfo.setDeviceId(monitorDevice.getId());
            auditInfo.setPointId(monitorPoint.getId());
            auditInfo.setAuditStatus("不通过");
            auditInfo.setAuditFeedback(request.getAuditFeedback());
            auditInfo.setCreator(user.getUsername());

            auditInfoDao.add(auditInfo);

            //审核通过，设备、点位 状态都已通过
            monitorPoint.setPointStatus("未通过");
            monitorDevice.setDeviceStatus("未通过");

            pointDao.updateMonitorPoint(monitorPoint);
            deviceDao.updateMonitorDevice(monitorDevice);
        }
    }
}
