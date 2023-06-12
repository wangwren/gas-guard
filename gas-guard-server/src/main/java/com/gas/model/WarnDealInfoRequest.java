package com.gas.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class WarnDealInfoRequest implements Serializable {


    /**
     * 预警信息id
     */
    @NotNull(message = "预警信息id不能为空")
    private Integer warnInfoId;

    /**
     * 设备id
     */
    @NotNull(message = "对应设备id不能为空")
    private Integer deviceId;

    /**
     * 设备状态:待自动恢复；已自动恢复
     */
    @NotBlank(message = "设备状态不能为空")
    private String deviceStatus;

    /**
     * 屏蔽策略:启用；不启用
     */
    @NotBlank(message = "屏蔽策略不能为空")
    private String blockPolicy;

    /**
     * 屏蔽时长(天)
     */
    private Integer blockTime;

    /**
     * 处置记录
     */
    @NotBlank(message = "处置记录不能为空")
    private String remark;
}
