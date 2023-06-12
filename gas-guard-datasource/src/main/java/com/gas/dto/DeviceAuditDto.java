package com.gas.dto;

import com.gas.entity.MonitorDevice;
import com.gas.entity.MonitorPoint;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class DeviceAuditDto implements Serializable {

    /**
     * 设备信息
     */
    private MonitorDevice monitorDevice;

    /**
     * 点位信息
     */
    private MonitorPoint monitorPoint;

    private List<DeviceWarnInfo> deviceWarnInfo;

    @Data
    public static class DeviceWarnInfo {
        /**
         * 预警类型
         */
        private String type;

        /**
         * 预警状态
         */
        private String warnStatus;

        /**
         * 预警时间
         */
        private Date warnTime;

        /**
         * 设备名称
         */
        private String deviceName;

        /**
         * 预警内容
         */
        private String warnContent;
    }
}
