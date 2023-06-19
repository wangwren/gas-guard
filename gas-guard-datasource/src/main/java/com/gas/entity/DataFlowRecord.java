package com.gas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 数据流水记录
 * @TableName data_flow_record
 */
@TableName(value ="data_flow_record")
@Data
public class DataFlowRecord implements Serializable {
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
     * 对应设备id
     */
    private Integer deviceId;

    /**
     * 所属点位
     */
    private String pointName;

    /**
     * 所属组织
     */
    private String organName;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 设备状态，正常;预警;故障;离线
     */
    private String deviceStatus;

    /**
     * 上报详情
     */
    private String detail;

    /**
     * 是否可用  true 可用 1  false 不可用 其他
     */
    private Boolean enable;

    /**
     * 采集时间
     */
    private Date collectTime;

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