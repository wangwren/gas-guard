package com.gas.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MonitorPointDto implements Serializable {

    private Integer id;

    /**
     * 点位名称
     */
    private String pointName;

    /**
     * 监测类型
     */
    private String monitorType;

    /**
     * 点位类型
     */
    private String pointType;

    /**
     * 点位状态，正常;离线
     */
    private String pointStatus;

    /**
     * 档案状态，待提交;待审核;未通过
     */
    private String archiveStatus;

    /**
     * 组织名称
     */
    private String organName;

    /**
     * 联系人
     */
    private String contract;

    /**
     * 联系电话
     */
    private String contractMobile;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 行政区划
     */
    private String province;

    /**
     * 供气企业
     */
    private String gasCompany;

    /**
     * 燃气种类
     */
    private String gasType;

    /**
     * 用户种类
     */
    private String userType;

    /**
     * 所属行业
     */
    private String industry;

    /**
     * 所属物业
     */
    private String property;

    /**
     * 物业电话
     */
    private String propertyMobile;

    /**
     * 配送站点
     */
    private String deliverySite;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 结束时间
     */
    private Date endTime;
}
