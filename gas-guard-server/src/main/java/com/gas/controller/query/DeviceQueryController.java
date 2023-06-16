package com.gas.controller.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.common.ResponseInfo;
import com.gas.dto.DeviceAuditDto;
import com.gas.dto.MonitorDeviceDto;
import com.gas.enums.ErrorCodeEnum;
import com.gas.excel.MonitorDeviceExcel;
import com.gas.excel.MonitorPointExcel;
import com.gas.model.MonitorDeviceRequest;
import com.gas.service.archive.MonitorDeviceService;
import com.gas.utils.FileDownloadUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Api("设备档案查询")
@RestController
@RequestMapping("/query/deviceInfo")
@Slf4j
public class DeviceQueryController {

    @Autowired
    private MonitorDeviceService monitorDeviceService;
    @Autowired
    private FileDownloadUtils downloadUtils;

    @ApiOperation("查询设备档案查询")
    @PostMapping("/getPage")
    public ResponseInfo getMonitorPoint(@RequestBody MonitorDeviceRequest request, HttpServletRequest servletRequest) {
        log.info("[设备档案查询] --- 查询设备档案查询 , request= {}", request);

        Page<MonitorDeviceDto> monitorDeviceDtoPage = monitorDeviceService.getDeviceManage(request);

        return ResponseInfo.success(monitorDeviceDtoPage);
    }

    @ApiOperation("查看设备详情")
    @PostMapping("/getDevice")
    public ResponseInfo getDevice(@RequestBody MonitorDeviceRequest request, HttpServletRequest servletRequest) {
        log.info("[设备档案查询] --- 查看设备详情 , request= {}", request);
        if (Objects.isNull(request.getId())) {
            log.warn("[设备档案查询] --- 查看设备详情, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        DeviceAuditDto deviceAudit = monitorDeviceService.doAudit(request);
        return ResponseInfo.success(deviceAudit);
    }

    @ApiOperation("设备档案导出")
    @GetMapping("/download")
    public void downloadExcel(@RequestBody MonitorDeviceRequest request, HttpServletResponse response) throws IOException {
        String fileName = "monitor_device.xlsx";
        Page<MonitorDeviceDto> monitorDeviceDtoPage = monitorDeviceService.getDeviceManage(request);
        List<MonitorDeviceDto> records = monitorDeviceDtoPage.getRecords();
        List<MonitorDeviceExcel> list = new ArrayList<>();
        for (MonitorDeviceDto record : records) {
            MonitorDeviceExcel monitorDeviceExcel = new MonitorDeviceExcel();
            BeanUtils.copyProperties(record, monitorDeviceExcel);

            list.add(monitorDeviceExcel);
        }

        downloadUtils.downloadExcel(fileName, MonitorPointExcel.class, list, response);
    }
}
