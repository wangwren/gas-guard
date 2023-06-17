package com.gas.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DeviceCountRequest implements Serializable {

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 设备厂家
     */
    private String deviceFactory;

    /**
     * 设备型号
     */
    private String deviceModel;

    /**
     * 统计维度: 天，月
     * 默认按天
     */
    private String dimension = "天";

    /**
     * 建档开始时间
     */
    private Date createTime;

    /**
     * 建档结束时间
     */
    private Date endTime;
}
