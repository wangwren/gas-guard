package com.gas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 离线规则配置
 * @TableName offline_rule
 */
@TableName(value ="offline_rule")
@Data
public class OfflineRule implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 设备厂家
     */
    private String deviceFactory;

    /**
     * 设备类型
     */
    private String deviceModel;

    /**
     * 设备类型
     */
    private String typeName;

    /**
     * 离线超时间隔(秒)
     */
    private Integer offlineTime;

    /**
     * 是否可用
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