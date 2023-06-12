package com.gas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 预警信息
 * @TableName warn_info
 */
@TableName(value ="warn_info")
@Data
public class WarnInfo implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 点位id
     */
    private Integer pointId;

    /**
     * 设备id
     */
    private Integer deviceId;

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