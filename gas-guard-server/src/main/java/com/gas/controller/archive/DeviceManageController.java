package com.gas.controller.archive;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.common.ResponseInfo;
import com.gas.dto.MonitorDeviceDto;
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

/**
 * 监测设备管理与监测设备建档差不多
 * 区别：
 *      1. 设备建档不展示已通过数据，设备管理展示档案所有状态数据
 *      2. 待审核状态也允许修改
 *      3. 增加批量恢复接口，设备状态变为正常，对应点位状态变为正常
 */
@Api("监测设备管理")
@RestController
@RequestMapping("/archive/deviceManage")
@Slf4j
public class DeviceManageController {

    @Autowired
    private MonitorDeviceService monitorDeviceService;
    @Autowired
    private FileDownloadUtils downloadUtils;

    @ApiOperation("查询监测设备管理")
    @RequiresPermissions("archive:device_manage:all")
    @PostMapping("/getPage")
    public ResponseInfo getMonitorPoint(@RequestBody MonitorDeviceRequest request, HttpServletRequest servletRequest) {
        log.info("[监测设备管理] --- 查询监测设备管理 , request= {}", request);

        Page<MonitorDeviceDto> monitorDeviceDtoPage = monitorDeviceService.getDeviceManage(request);

        return ResponseInfo.success(monitorDeviceDtoPage);
    }

    @ApiOperation("新增或修改监测设备管理")
    @PostMapping("/addOrUpdate")
    public ResponseInfo addOrUpdate(@RequestBody MonitorDeviceRequest request, HttpServletRequest servletRequest) {
        log.info("[监测设备管理] --- 新增或修改监测设备管理 , request= {}", request);

        //任何状态都允许修改
        monitorDeviceService.addOrUpdateAll(request);

        return ResponseInfo.success();
    }

    @ApiOperation("删除监测设备管理")
    @PostMapping("/delById")
    public ResponseInfo delId(@RequestBody MonitorDeviceRequest request, HttpServletRequest servletRequest) {
        log.info("[监测设备管理] --- 删除监测设备管理 , request= {}", request);
        if (Objects.isNull(request.getId())) {
            log.warn("[监测设备管理] --- 删除监测设备管理, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        //任何状态都允许删除
        monitorDeviceService.delByIdAll(request.getId());

        return ResponseInfo.success();
    }

    @ApiOperation("批量删除监测设备管理")
    @PostMapping("/delBatchIds")
    public ResponseInfo delBatchIds(@RequestBody BatchIdsRequest request, HttpServletRequest servletRequest) {
        log.info("[监测设备管理] --- 批量删除监测设备管理 , request= {}", request);
        if (CollectionUtils.isEmpty(request.getIds())) {
            log.warn("[监测设备管理] --- 批量删除监测设备管理, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        //任何状态都允许删除
        monitorDeviceService.delBatchIdsAll(request.getIds());

        return ResponseInfo.success();
    }

    @ApiOperation("提交监测设备管理")
    @PostMapping("/commit")
    public ResponseInfo commit(@RequestBody MonitorDeviceRequest request, HttpServletRequest servletRequest) {
        log.info("[监测设备管理] --- 提交监测设备管理 , request= {}", request);
        if (Objects.isNull(request.getId())) {
            log.warn("[监测设备管理] --- 提交监测设备管理, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        //非待提交和未通过不允许提交
        monitorDeviceService.commit(request.getId());

        return ResponseInfo.success();
    }

    @ApiOperation("批量提交监测设备管理")
    @PostMapping("/commitBatchIds")
    public ResponseInfo commitBatchIds(@RequestBody BatchIdsRequest request, HttpServletRequest servletRequest) {
        log.info("[监测设备管理] --- 批量提交监测设备管理 , request= {}", request);
        if (CollectionUtils.isEmpty(request.getIds())) {
            log.warn("[监测设备管理] --- 批量提交监测设备管理, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        //非待提交和未通过不允许提交
        monitorDeviceService.commitBatchIds(request.getIds());

        return ResponseInfo.success();
    }

    @ApiOperation("批量设备恢复")
    @PostMapping("/resumeBatchIds")
    public ResponseInfo resumeBatchIds(@RequestBody BatchIdsRequest request, HttpServletRequest servletRequest) {
        log.info("[监测设备管理] --- 批量设备恢复 , request= {}", request);
        if (CollectionUtils.isEmpty(request.getIds())) {
            log.warn("[监测设备管理] --- 批量设备恢复, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        monitorDeviceService.resumeBatchIds(request.getIds());

        return ResponseInfo.success();
    }

    @ApiOperation("监测设备管理导出")
    @GetMapping("/download")
    public void downloadExcel(@RequestBody MonitorDeviceRequest request, HttpServletResponse response) throws IOException {
        String fileName = "monitor_device_manage.xlsx";
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
