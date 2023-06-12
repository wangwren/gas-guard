package com.gas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 预警处置信息
 * @TableName warn_deal_info
 */
@TableName(value ="warn_deal_info")
@Data
public class WarnDealInfo implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 预警信息id
     */
    private Integer warnInfoId;

    /**
     * 设备id
     */
    private Integer deviceId;

    /**
     * 设备状态:待自动恢复；已自动恢复
     */
    private String deviceStatus;

    /**
     * 屏蔽策略:启用；不启用
     */
    private String blockPolicy;

    /**
     * 屏蔽时长(天)
     */
    private Integer blockTime;

    /**
     * 处置记录
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