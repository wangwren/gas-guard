package com.gas.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class WarnTypeRequest extends BaseRequest implements Serializable {

    private Integer id;

    private String type;

    private String bigType;

    private String level;

    private String status;

    private String whenab;

    private String[] notifi;

    private String content;
}
