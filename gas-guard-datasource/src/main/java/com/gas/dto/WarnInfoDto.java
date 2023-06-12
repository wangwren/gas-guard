package com.gas.dto;

import com.gas.entity.MonitorDevice;
import com.gas.entity.MonitorPoint;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class WarnInfoDto implements Serializable {

    private Integer id;

    /**
     * 点位ids
     */
    private List<Integer> pointIds;

    /**
     * 点位id
     */
    private Integer pointId;

    /**
     * 点位信息
     */
    private MonitorPoint monitorPoint;

    /**
     * 设备ids
     */
    private List<Integer> deviceIds;

    /**
     * 设备id
     */
    private Integer deviceId;

    /**
     * 设备信息
     */
    private MonitorDevice monitorDevice;

    /**
     * 预警类型
     */
    private String type;

    /**
     * 预警级别
     */
    private String level;

    /**
     * 预警时间
     */
    private Date warnTime;

    /**
     * 预警状态:待确认；待处置
     */
    private String warnStatus;

    /**
     * 数据类型:正式数据；调试数据
     */
    private String dataType;

    /**
     * 预警数据
     */
    private String warnData;

    /**
     * 预警内容
     */
    private String warnContent;

    /**
     * 创建数据开始时间
     */
    private Date createTime;

    /**
     * 创建数据结束时间
     */
    private Date endTime;

    /**
     * 预警开始时间
     */
    private Date warnBeginTime;

    /**
     * 预警结束时间
     */
    private Date warnEndTime;
}
