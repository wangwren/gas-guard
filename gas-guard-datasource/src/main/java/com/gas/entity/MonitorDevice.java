package com.gas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 监测设备建档
 * @TableName monitor_device
 */
@TableName(value ="monitor_device")
@Data
public class MonitorDevice implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
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
     * 创建人
     */
    private String creator;

    /**
     * 是否可用  true 可用 1  false 不可用 其他
     */
    private Boolean enable;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}