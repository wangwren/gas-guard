package com.gas.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysConfigRequest extends BaseRequest implements Serializable {

    private Integer id;

    private String sysName;

    private String sysKey;

    private String sysValue;

    private String sysRemark;
}
