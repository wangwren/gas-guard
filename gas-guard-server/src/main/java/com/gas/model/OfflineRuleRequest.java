package com.gas.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class OfflineRuleRequest extends BaseRequest implements Serializable {

    private Integer id;

    private String deviceFactory;

    private String deviceModel;

    private String typeName;

    private Integer offlineTime;
}
