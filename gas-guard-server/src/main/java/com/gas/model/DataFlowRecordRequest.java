package com.gas.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DataFlowRecordRequest extends BaseRequest implements Serializable {

    /**
     * 组织名称
     */
    private String organName;

    /**
     * 点位名称
     */
    private String pointName;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 采集开始时间
     */
    private Date collectBeginTime;

    /**
     * 采集结束时间
     */
    private Date collectEndTime;
}
