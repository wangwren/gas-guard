package com.gas.controller.archive;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.common.ResponseInfo;
import com.gas.dto.MonitorDeviceDto;
import com.gas.entity.MonitorDevice;
import com.gas.enums.ErrorCodeEnum;
import com.gas.excel.MonitorDeviceExcel;
import com.gas.excel.MonitorPointExcel;
import com.gas.model.BatchIdsRequest;
import com.gas.model.MonitorDeviceRequest;
import com.gas.service.archive.MonitorDeviceService;
import com.gas.utils.FileDownloadUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    //@RequiresPermissions("archive:device:all")
    @PostMapping("/getPage")
    public ResponseInfo getMonitorDevice(@RequestBody MonitorDeviceRequest request, HttpServletRequest servletRequest) {
        log.info("[监测设备建档] --- 查询监测设备建档 , request= {}", request);

        Page<MonitorDeviceDto> monitorDevicePage = monitorDeviceService.getMonitorDevice(request);

        return ResponseInfo.success(monitorDevicePage);
    }

    @ApiOperation("新增或修改监测设备建档")
    @PostMapping("/addOrUpdate")
    public ResponseInfo addOrUpdate(@RequestBody MonitorDeviceRequest request, HttpServletRequest servletRequest) {
        log.info("[监测设备建档] --- 新增或修改监测设备建档 , request= {}", request);

        //待审核 状态不允许修改
        monitorDeviceService.addOrUpdate(request);

        return ResponseInfo.success();
    }

    @ApiOperation("根据id查询监测设备建档")
    @PostMapping("/getById")
    public ResponseInfo getById(@RequestBody MonitorDeviceRequest request, HttpServletRequest servletRequest) {
        log.info("[监测设备建档] --- 根据id查询监测设备建档 , request= {}", request);
        if (Objects.isNull(request.getId())) {
            log.warn("[监测设备建档] --- 根据id查询监测设备建档, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        MonitorDevice monitorDevice = monitorDeviceService.getById(request.getId());
        return ResponseInfo.success(monitorDevice);
    }

    @ApiOperation("删除监测设备建档")
    @PostMapping("/delById")
    public ResponseInfo delId(@RequestBody MonitorDeviceRequest request, HttpServletRequest servletRequest) {
        log.info("[监测设备建档] --- 删除监测设备建档 , request= {}", request);
        if (Objects.isNull(request.getId())) {
            log.warn("[监测设备建档] --- 删除监测设备建档, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        //待审核不允许删除
        monitorDeviceService.delById(request.getId());

        return ResponseInfo.success();
    }

    @ApiOperation("批量删除监测设备建档")
    @PostMapping("/delBatchIds")
    public ResponseInfo delBatchIds(@RequestBody BatchIdsRequest request, HttpServletRequest servletRequest) {
        log.info("[监测设备建档] --- 批量删除监测设备建档 , request= {}", request);
        if (CollectionUtils.isEmpty(request.getIds())) {
            log.warn("[监测设备建档] --- 批量删除监测设备建档, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        //待审核不允许删除
        monitorDeviceService.delBatchIds(request.getIds());

        return ResponseInfo.success();
    }

    @ApiOperation("提交监测设备建档")
    @PostMapping("/commit")
    public ResponseInfo commit(@RequestBody MonitorDeviceRequest request, HttpServletRequest servletRequest) {
        log.info("[监测设备建档] --- 提交监测设备建档 , request= {}", request);
        if (Objects.isNull(request.getId())) {
            log.warn("[监测设备建档] --- 提交监测设备建档, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        //非待提交和未通过不允许提交
        monitorDeviceService.commit(request.getId());

        return ResponseInfo.success();
    }

    @ApiOperation("批量提交监测设备建档")
    @PostMapping("/commitBatchIds")
    public ResponseInfo commitBatchIds(@RequestBody BatchIdsRequest request, HttpServletRequest servletRequest) {
        log.info("[监测设备建档] --- 批量提交监测设备建档 , request= {}", request);
        if (CollectionUtils.isEmpty(request.getIds())) {
            log.warn("[监测设备建档] --- 批量提交监测设备建档, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        //待审核不允许删除
        monitorDeviceService.commitBatchIds(request.getIds());

        return ResponseInfo.success();
    }

    @ApiOperation("监测设备建档导出")
    @GetMapping("/download")
    public void downloadExcel(@RequestBody MonitorDeviceRequest request, HttpServletResponse response) throws IOException {
        String fileName = "monitor_device.xlsx";
        Page<MonitorDeviceDto> monitorDeviceDtoPage = monitorDeviceService.getMonitorDevice(request);
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
