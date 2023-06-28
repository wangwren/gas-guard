package com.gas.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class MonitorPointRequest extends BaseRequest implements Serializable {

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
     * 省
     */
    private String prov;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 街道
     */
    private String village;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

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

    //监测点位管理用

    /**
     * 无设备
     */
    private Boolean noDevice = false;

    /**
     * 多设备
     */
    private Boolean manyDevice = false;
}
