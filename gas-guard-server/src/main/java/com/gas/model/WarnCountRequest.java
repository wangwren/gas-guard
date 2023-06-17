package com.gas.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WarnCountRequest implements Serializable {

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 档案状态
     */
    private String archiveStatus;

    /**
     * 告警类型
     */
    private String type;

    /**
     * 设备状态
     */
    private String deviceStatus;

    /**
     * 预警开始时间
     */
    private Date warnBeginTime;

    /**
     * 预警结束时间
     */
    private Date warnEndTime;

}
