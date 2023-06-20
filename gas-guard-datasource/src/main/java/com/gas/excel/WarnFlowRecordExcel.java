package com.gas.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class WarnFlowRecordExcel {

    /**
     * 组织名称
     */
    @ExcelProperty("组织名称")
    private String organName;

    /**
     * 行政区划
     */
    @ExcelProperty("行政区划")
    private String province;

    /**
     * 设备编码
     */
    @ExcelProperty("设备编码")
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
     * 点位名称
     */
    @ExcelProperty("点位名称")
    private String pointName;

    /**
     * 地址
     */
    @ExcelProperty("地址")
    private String address;
}
