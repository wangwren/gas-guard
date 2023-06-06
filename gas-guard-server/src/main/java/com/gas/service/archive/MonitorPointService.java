package com.gas.service.archive;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.entity.MonitorPoint;
import com.gas.model.MonitorPointRequest;

import java.util.List;

public interface MonitorPointService {
    Page<MonitorPoint> getMonitorPoint(MonitorPointRequest request);

    void addOrUpdate(MonitorPointRequest request);

    MonitorPoint getById(Integer id);

    void delById(Integer id);

    void delBatchIds(List<Integer> ids);

    Page<MonitorPoint> getMonitorPointAll(MonitorPointRequest request);
}
