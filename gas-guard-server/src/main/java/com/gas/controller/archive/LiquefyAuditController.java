package com.gas.controller.archive;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.common.ResponseInfo;
import com.gas.dto.MonitorDeviceDto;
import com.gas.excel.MonitorDeviceExcel;
import com.gas.excel.MonitorPointExcel;
import com.gas.model.MonitorDeviceRequest;
import com.gas.service.archive.MonitorDeviceService;
import com.gas.utils.FileDownloadUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 液化气档案审核
 * 只查询 燃气种类 是 液化天然气，并且档案状态 为 待审核的数据
 *
 * 只有查询和导出接口
 * 其他接口复用设备档案审核 DeviceAuditController
 */
@Api("液化气档案审核")
@RestController
@RequestMapping("/archive/liquefyAudit")
@Slf4j
public class LiquefyAuditController {
    @Autowired
    private MonitorDeviceService monitorDeviceService;
    @Autowired
    private FileDownloadUtils downloadUtils;

    @ApiOperation("查询液化气档案审核(只包含待审核)")
    @RequiresPermissions("archive:liquefy_audit:all")
    @PostMapping("/getPage")
    public ResponseInfo getLiquefyAudit(@RequestBody MonitorDeviceRequest request, HttpServletRequest servletRequest) {
        log.info("[液化气档案审核] --- 查询液化气档案审核(只包含待审核) , request= {}", request);

        Page<MonitorDeviceDto> monitorDevicePage = monitorDeviceService.getLiquefyAudit(request);

        return ResponseInfo.success(monitorDevicePage);
    }

    @ApiOperation("液化气档案审核导出")
    @GetMapping("/download")
    public void downloadExcel(@RequestBody MonitorDeviceRequest request, HttpServletResponse response) throws IOException {
        String fileName = "monitor_liquefy_audit.xlsx";
        Page<MonitorDeviceDto> monitorDeviceDtoPage = monitorDeviceService.getLiquefyAudit(request);
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
