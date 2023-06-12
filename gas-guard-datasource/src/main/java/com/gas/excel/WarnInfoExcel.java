package com.gas.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class WarnInfoExcel {

    /**
     * 预警状态:待确认；待处置
     */
    @ExcelProperty("预警处置状态")
    private String warnStatus;

    /**
     * 点位名称
     */
    @ExcelProperty("点位名称")
    private String pointName;

    /**
     * 预警类型
     */
    @ExcelProperty("预警类型")
    private String type;

    /**
     * 预警级别
     */
    @ExcelProperty("预警级别")
    private String level;

    /**
     * 数据类型:正式数据；调试数据
     */
    @ExcelProperty("数据状态")
    private String dataType;

    /**
     * 创建时间
     */
    @ExcelProperty("数据时间")
    private Date createTime;

    /**
     * 预警时间
     */
    @ExcelProperty("预警时间")
    private Date warnTime;

    /**
     * 设备名称
     */
    @ExcelProperty("设备名称")
    private String deviceName;

    /**
     * 设备编号，唯一
     */
    @ExcelProperty("设备编号")
    private String deviceNo;

    /**
     * 联系人
     */
    @ExcelProperty("联系人")
    private String contract;

    /**
     * 联系电话
     */
    @ExcelProperty("联系电话")
    private String contractMobile;

    /**
     * 详细地址
     */
    @ExcelProperty("详细地址")
    private String address;
}
