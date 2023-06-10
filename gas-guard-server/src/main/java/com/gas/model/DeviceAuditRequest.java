package com.gas.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeviceAuditRequest extends BaseRequest implements Serializable {

    /**
     * 要修改的点位信息
     */
    private MonitorPointRequest pointRequest;

    /**
     * 要修改的设备信息
     */
    private MonitorDeviceRequest deviceRequest;
}
