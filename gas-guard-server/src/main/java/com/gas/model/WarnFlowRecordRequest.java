package com.gas.model;

import lombok.Data;

import java.util.Date;

@Data
public class WarnFlowRecordRequest extends BaseRequest{

    /**
     * 点位名称
     */
    private String pointName;

    /**
     * 组织名称
     */
    private String organName;

    /**
     * 设备编码
     */
    private String deviceNo;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 预警类型
     */
    private String type;

    /**
     * 预警级别
     */
    private String level;

    /**
     * 预警状态
     */
    private String warnStatus;

    /**
     * 预警开始时间
     */
    private Date warnBeginTime;

    /**
     * 预警结束时间
     */
    private Date warnEndTime;
}
