package com.gas.controller.archive;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.common.ResponseInfo;
import com.gas.dto.MonitorDeviceDto;
import com.gas.model.MonitorDeviceRequest;
import com.gas.service.archive.MonitorDeviceService;
import com.gas.utils.FileDownloadUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api("监测设备建档")
@RestController
@RequestMapping("/archive/monitorDevice")
@Slf4j
public class MonitorDeviceController {

    @Autowired
    private MonitorDeviceService monitorDeviceService;
    @Autowired
    private FileDownloadUtils downloadUtils;

    @ApiOperation("查询监测设备建档(不包含已通过)")
    @RequiresPermissions("archive:device:all")
    @PostMapping("/getPage")
    public ResponseInfo getMonitorDevice(@RequestBody MonitorDeviceRequest request, HttpServletRequest servletRequest) {
        log.info("[监测设备建档] --- 查询监测设备建档 , request= {}", request);

        Page<MonitorDeviceDto> monitorDevicePage = monitorDeviceService.getMonitorDevice(request);

        return ResponseInfo.success(monitorDevicePage);
    }
}
