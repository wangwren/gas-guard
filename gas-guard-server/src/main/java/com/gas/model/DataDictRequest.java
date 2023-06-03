package com.gas.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class DataDictRequest extends BaseRequest implements Serializable {

    private Integer id;

    private String type;

    private String typeName;

    private String dictKey;

    private String dictValue;

    private String remark;
}
