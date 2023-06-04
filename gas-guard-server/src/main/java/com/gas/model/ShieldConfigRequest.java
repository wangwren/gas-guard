package com.gas.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ShieldConfigRequest extends BaseRequest implements Serializable {

    private Integer id;

    private String pointName;

    private String deviceName;

    private String deviceNo;

    private Integer shieldDay;

    private String status;

    private String remark;
}
