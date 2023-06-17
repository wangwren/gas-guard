package com.gas.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class WarnCountExcel {
    /**
     * 点位名称
     */
    @ExcelProperty("点位名称")
    private String pointName;

    /**
     * 设备名称
     */
    @ExcelProperty("设备名称")
    private String deviceName;

    /**
     * 设备编号
     */
    @ExcelProperty("设备编号")
    private String deviceNo;

    /**
     * 总计数，即设备的预警数量
     */
    @ExcelProperty("总计数")
    private Integer countNum;

    /**
     * 预警数，看当前设备状态统计
     */
    @ExcelProperty("预警数")
    private Integer warnNum;

    /**
     * 故障数，看当前设备状态统计
     */
    @ExcelProperty("故障数")
    private Integer failNum;

    /**
     * 离线数，看当前设备状态统计
     */
    @ExcelProperty("离线数")
    private Integer offlineNum;

    /**
     * 当前设备状态
     */
    @ExcelProperty("当前设备状态")
    private String deviceStatus;

    /**
     * 最新告警时间
     */
    @ExcelProperty("最新告警时间")
    private Date warnTime;
}
