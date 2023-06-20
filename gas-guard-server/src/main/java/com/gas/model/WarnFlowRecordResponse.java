package com.gas.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class WarnFlowRecordResponse implements Serializable {

    /**
     * 组织名称
     */
    private String organName;

    /**
     * 行政区划
     */
    private String province;

    /**
     * 设备编码
     */
    private String deviceNo;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 点位名称
     */
    private String pointName;

    /**
     * 地址
     */
    private String address;
}
