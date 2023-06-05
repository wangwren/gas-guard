package com.gas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 监测点位建档
 * @TableName monitor_point
 */
@TableName(value ="monitor_point")
@Data
public class MonitorPoint implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
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