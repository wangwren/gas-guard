package com.gas.dto;

import com.gas.entity.MonitorDevice;
import com.gas.entity.MonitorPoint;
import lombok.Data;

import java.io.Serializable;

@Data
public class DeviceAuditDto implements Serializable {

    /**
     * 设备信息
     */
    private MonitorDevice monitorDevice;

    /**
     * 点位信息
     */
    private MonitorPoint monitorPoint;
}
