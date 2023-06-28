package com.gas.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MonitorDeviceDto implements Serializable {

    private Integer id;

    /**
     * 对应监测点位id
     */
    private Integer pointId;

    /**
     * 所属点位
     */
    private String pointName;

    /**
     * 所属组织
     */
    private String organName;

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
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备编号，唯一
     */
    private String deviceNo;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 安装位置
     */
    private String installLocal;

    /**
     * 设备厂家
     */
    private String deviceFactory;

    /**
     * 设备型号
     */
    private String deviceModel;

    /**
     * 出厂日期
     */
    private Date factoryTime;

    /**
     * 传感器寿命(年)
     */
    private Integer sensorLife;

    /**
     * 设备联动
     */
    private Integer deviceLink;

    /**
     * 维保单位
     */
    private String repairCompany;

    /**
     * 维保联系人
     */
    private String repairContract;

    /**
     * 维保电话
     */
    private String repairMobile;

    /**
     * 设备状态，正常;预警;故障;离线
     */
    private String deviceStatus;

    /**
     * 档案状态，待提交;待审核;未通过
     */
    private String archiveStatus;

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

    //以下是点位数据

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
     * 点位类型
     */
    private String pointType;
}
