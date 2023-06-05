package com.gas.service.archive.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.common.GlobalConstants;
import com.gas.dao.MonitorPointDao;
import com.gas.dto.MonitorPointDto;
import com.gas.entity.MonitorPoint;
import com.gas.enums.ErrorCodeEnum;
import com.gas.exception.CommonException;
import com.gas.model.MonitorPointRequest;
import com.gas.service.archive.MonitorPointService;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public Page<MonitorPoint> getMonitorPoint(MonitorPointRequest request) {
        MonitorPointDto monitorPoint = new MonitorPointDto();
        BeanUtils.copyProperties(request, monitorPoint);

        return monitorPointDao.selectPage(monitorPoint, request.getCurr(), request.getPageSize());
    }

    @Override
    @Transactional
    public void addOrUpdate(MonitorPointRequest request) {
        MonitorPoint monitorPoint = new MonitorPoint();
        BeanUtils.copyProperties(request, monitorPoint);

        if (Objects.isNull(monitorPoint.getId())) {
            //新增
            monitorPointDao.addMonitorPoint(monitorPoint);
            return;
        }

        //如果是待审核状态，不允许修改
        if (Objects.equals(monitorPoint.getArchiveStatus(), GlobalConstants.ARCHIVE_CHECK_STATUS)) {
            log.warn("修改监测点位建档，当前状态为 {} ,不允许修改", monitorPoint.getArchiveStatus());
            throw new CommonException(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }
        //修改
        monitorPointDao.updateMonitorPoint(monitorPoint);
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
            throw new CommonException(ErrorCodeEnum.INVALID_PARAM_VALUE);
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
            throw new CommonException(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        monitorPointDao.delBatchIds(ids);
    }
}
