package com.gas.dto;

import com.gas.entity.MonitorDevice;
import com.gas.entity.MonitorPoint;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FormalPointDto implements Serializable {

    private MonitorPoint monitorPoint;

    private List<MonitorDevice> monitorDevices;
}
