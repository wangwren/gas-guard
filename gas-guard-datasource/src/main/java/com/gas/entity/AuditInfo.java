package com.gas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 审核信息表
 * @TableName audit_info
 */
@TableName(value ="audit_info")
@Data
public class AuditInfo implements Serializable {
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
     * 对应监测设备id
     */
    private Integer deviceId;

    /**
     * 审核状态：通过; 不通过
     */
    private String auditStatus;

    /**
     * 审核反馈
     */
    private String auditFeedback;

    /**
     * 创建人
     */
    private String creator;

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