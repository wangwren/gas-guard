package com.gas.model;

import lombok.Data;

/**
 * 通用请求参数
 */
@Data
public class BaseRequest {

    /**
     * 当前页数，默认第一页
     */
    private Integer curr = 1;

    /**
     * 一页条数，默认20条
     */
    private Integer pageSize = 20;
}
