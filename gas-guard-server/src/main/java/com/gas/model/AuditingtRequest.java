package com.gas.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 审核请求参数
 */
@Data
public class AuditingtRequest implements Serializable {

    /**
     * 对应监测点位id
     */
    @NotNull(message = "对应监测点位id不能为空")
    private Integer pointId;

    /**
     * 对应监测设备id
     */
    @NotNull(message = "对应监测设备id不能为空")
    private Integer deviceId;

    /**
     * 审核状态：通过; 不通过
     */
    @NotBlank(message = "审核状态不能为空")
    private String auditStatus;

    /**
     * 审核反馈
     */
    private String auditFeedback;
}
