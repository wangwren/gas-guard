package com.gas.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WarnCountDto implements Serializable {

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
     * 总计数，即设备的预警数量
     */
    private Integer countNum;

    /**
     * 预警数，看当前设备状态统计
     */
    private Integer warnNum;

    /**
     * 故障数，看当前设备状态统计
     */
    private Integer failNum;

    /**
     * 离线数，看当前设备状态统计
     */
    private Integer offlineNum;

    /**
     * 当前设备状态
     */
    private String deviceStatus;

    /**
     * 最新告警时间
     */
    private Date warnTime;
}
