package com.gas.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class MonitorDeviceExcel {

    /**
     * 所属点位
     */
    @ExcelProperty("点位名称")
    private String pointName;

    /**
     * 设备编号，唯一
     */
    @ExcelProperty("设备编号")
    private String deviceNo;


    /**
     * 设备名称
     */
    @ExcelProperty("设备名称")
    private String deviceName;

    /**
     * 设备类型
     */
    @ExcelProperty("设备类型")
    private String deviceType;

    /**
     * 设备状态，正常;预警;故障;离线
     */
    @ExcelProperty("设备状态")
    private String deviceStatus;

    /**
     * 档案状态，待提交;待审核;未通过
     */
    @ExcelProperty("档案状态")
    private String archiveStatus;

    /**
     * 设备厂家
     */
    @ExcelProperty("设备厂家")
    private String deviceFactory;

    /**
     * 设备型号
     */
    @ExcelProperty("设备型号")
    private String deviceModel;

    /**
     * 安装位置
     */
    @ExcelProperty("安装位置")
    private String installLocal;

    /**
     * 维保单位
     */
    @ExcelProperty("维保单位")
    private String repairCompany;

    /**
     * 维保联系人
     */
    @ExcelProperty("维保联系人")
    private String repairContract;

    /**
     * 维保电话
     */
    @ExcelProperty("维保电话")
    private String repairMobile;

    /**
     * 详细地址
     */
    @ExcelProperty("详细地址")
    private String address;

    /**
     * 行政区划
     */
    @ExcelProperty("行政区划")
    private String province;

    /**
     * 出厂日期
     */
    @ExcelProperty("出厂日期")
    private Date factoryTime;


    /**
     * 设备联动
     */
    @ExcelProperty("是否有切断阀")
    private Boolean deviceLink;

    /**
     * 备注
     */
    @ExcelProperty("设备备注")
    private String remark;

}
