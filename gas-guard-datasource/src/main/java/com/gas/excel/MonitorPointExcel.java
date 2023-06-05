package com.gas.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class MonitorPointExcel {

    /**
     * 点位名称
     */
    @ExcelProperty("点位名称")
    private String pointName;

    /**
     * 燃气种类
     */
    @ExcelProperty("燃气种类")
    private String gasType;

    /**
     * 点位类型
     */
    @ExcelProperty("点位类型")
    private String pointType;

    /**
     * 点位状态，正常;离线
     */
    @ExcelProperty("点位类型")
    private String pointStatus;

    /**
     * 档案状态，待提交;待审核;未通过
     */
    @ExcelProperty("点位类型")
    private String archiveStatus;

    /**
     * 组织名称
     */
    @ExcelProperty("所属组织")
    private String organName;

    /**
     * 详细地址
     */
    @ExcelProperty("详细地址")
    private String address;

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
     * 供气企业
     */
    @ExcelProperty("供气企业")
    private String gasCompany;

    /**
     * 用户种类
     */
    @ExcelProperty("用户种类")
    private String userType;

    /**
     * 创建时间
     */
    @ExcelProperty("建档时间")
    private Date createTime;
}
