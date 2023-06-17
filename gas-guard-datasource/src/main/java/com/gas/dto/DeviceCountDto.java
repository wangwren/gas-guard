package com.gas.dto;

import com.gas.entity.MonitorDevice;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DeviceCountDto implements Serializable {

    /**
     * 设备列表
     */
    private List<MonitorDevice> monitorDevices;

    /**
     * 总计建档数量
     */
    private Integer createDeviceCount = 0;

    /**
     * 总计审核通过数量
     */
    private Integer passDeviceCount = 0;

    /**
     * 总计未审核数量
     */
    private Integer noPassDeviceCount = 0;
}
