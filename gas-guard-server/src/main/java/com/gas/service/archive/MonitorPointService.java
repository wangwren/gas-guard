package com.gas.service.archive;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.dto.FormalPointDto;
import com.gas.entity.MonitorPoint;
import com.gas.model.MonitorPointRequest;

import java.util.List;

public interface MonitorPointService {
    Page<MonitorPoint> getMonitorPoint(MonitorPointRequest request);

    Page<MonitorPoint> getPointManage(MonitorPointRequest request);

    Page<MonitorPoint> getFormalPoint(MonitorPointRequest request);

    void addOrUpdate(MonitorPointRequest request);

    void addOrUpdateAll(MonitorPointRequest request);

    MonitorPoint getById(Integer id);

    void delById(Integer id);

    void delByIdAll(Integer id);

    void delBatchIds(List<Integer> ids);

    void delBatchIdsAll(List<Integer> ids);

    Page<MonitorPoint> getMonitorPointAll(MonitorPointRequest request);

    FormalPointDto getPointDeviceById(Integer id);
}
