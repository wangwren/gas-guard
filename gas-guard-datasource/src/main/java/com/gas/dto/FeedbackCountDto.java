package com.gas.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FeedbackCountDto implements Serializable {

    /**
     * 预警类型，即处置反馈类型
     */
    private String feedback;

    /**
     * 告警数量
     */
    private Integer num;

    public List<FactoryCount> factoryCounts;

    @Data
    public static class FactoryCount {
        /**
         * 厂家名称
         */
        private String factory;

        /**
         * 厂家数量
         */
        private Integer factoryNum;
    }
}
