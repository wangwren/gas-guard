package com.gas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 屏蔽策略配置
 * @TableName shield_config
 */
@TableName(value ="shield_config")
@Data
public class ShieldConfig implements Serializable {
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
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 设备屏蔽时长(天)
     */
    private Integer shieldDay;

    /**
     * 开始屏蔽时间
     */
    private Date shieldTime;

    /**
     * 策略状态，屏蔽中；已撤销；已结束
     */
    private String status;

    /**
     * 配置说明
     */
    private String remark;

    /**
     * 是否可用  true 可用 1  false 不可用 其他
     */
    private Integer enable;

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