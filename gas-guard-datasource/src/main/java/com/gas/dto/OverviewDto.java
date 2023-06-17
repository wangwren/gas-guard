package com.gas.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OverviewDto implements Serializable {

    /**
     * 居民用户数
     */
    private Integer resident = 0;

    /**
     * 非居民用户数
     */
    private Integer noResident = 0;

    /**
     * 居民设备数
     */
    private Integer residentDevice = 0;

    /**
     * 非居设备数
     */
    private Integer noResidentDevice = 0;

    /**
     * 天然气用户数
     */
    private Integer naturalResident = 0;

    /**
     * 液化气用户数
     */
    private Integer liquefyResident = 0;

    /**
     * 天然气设备数
     */
    private Integer naturalDevice = 0;

    /**
     * 液化气设备数
     */
    private Integer liquefyDevice = 0;

    /**
     * 设备行政区划统计
     */
    private List<DataView> governDeviceDistributions;

    /**
     * 供气企业
     */
    private List<DataView> coTypeDeviceDistributions;

    /**
     * 燃气种类
     */
    private List<DataView> gasTypeDeviceDistributions;

    /**
     * 用户种类
     */
    private List<DataView> userTpyeDeviceDistributions;

    @Data
    public static class DataView {
        private String code;
        private String name;

        private Integer num;
    }
}
