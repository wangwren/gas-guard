package com.gas.service.archive;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.dto.MonitorDeviceDto;
import com.gas.entity.MonitorDevice;
import com.gas.model.MonitorDeviceRequest;

import java.util.List;

public interface MonitorDeviceService {
    Page<MonitorDeviceDto> getMonitorDevice(MonitorDeviceRequest request);

    Page<MonitorDeviceDto> getDeviceManage(MonitorDeviceRequest request);

    void addOrUpdate(MonitorDeviceRequest request);

    void addOrUpdateAll(MonitorDeviceRequest request);

    MonitorDevice getById(Integer id);

    void delById(Integer id);

    void delByIdAll(Integer id);

    void delBatchIdsAll(List<Integer> ids);

    void delBatchIds(List<Integer> ids);

    void commit(Integer id);

    void commitBatchIds(List<Integer> ids);

    void resumeBatchIds(List<Integer> ids);
}
