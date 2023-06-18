package com.gas.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WarnTimeDto implements Serializable {

    /**
     * 预警时间
     */
    private Date warnTime;
}
