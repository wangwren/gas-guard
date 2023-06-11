package com.gas.controller.archive;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.common.ResponseInfo;
import com.gas.dto.FormalPointDto;
import com.gas.entity.MonitorPoint;
import com.gas.enums.ErrorCodeEnum;
import com.gas.excel.MonitorPointExcel;
import com.gas.model.MonitorDeviceRequest;
import com.gas.model.MonitorPointRequest;
import com.gas.service.archive.MonitorDeviceService;
import com.gas.service.archive.MonitorPointService;
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
import java.util.Objects;

/**
 * 列表页查的是 已通过 点位数据
 */
@Api("正式档案管理")
@RestController
@RequestMapping("/archive/formalPoint")
@Slf4j
public class FormalPointController {

    @Autowired
    private MonitorPointService monitorPointService;
    @Autowired
    private MonitorDeviceService monitorDeviceService;
    @Autowired
    private FileDownloadUtils downloadUtils;

    @ApiOperation("查询正式档案管理(只已通过)")
    @RequiresPermissions("archive:formal:all")
    @PostMapping("/getPage")
    public ResponseInfo getFormalPoint(@RequestBody MonitorPointRequest request, HttpServletRequest servletRequest) {
        log.info("[正式档案管理] --- 查询监测点位建档(不包含已通过) , request= {}", request);

        Page<MonitorPoint> monitorPointPage = monitorPointService.getFormalPoint(request);

        return ResponseInfo.success(monitorPointPage);
    }

    @ApiOperation("根据点位id查询点位和设备信息")
    @PostMapping("/getById")
    public ResponseInfo getById(@RequestBody MonitorPointRequest request, HttpServletRequest servletRequest) {
        log.info("[正式档案管理] --- 根据点位id查询点位和设备信息 , request= {}", request);
        if (Objects.isNull(request.getId())) {
            log.warn("[正式档案管理] --- 根据点位id查询点位和设备信息, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        FormalPointDto formalPointDto = monitorPointService.getPointDeviceById(request.getId());
        return ResponseInfo.success(formalPointDto);
    }

    @ApiOperation("修改正式档案点位信息")
    @PostMapping("/updatePoint")
    public ResponseInfo updatePoint(@RequestBody MonitorPointRequest request, HttpServletRequest servletRequest) {
        log.info("[正式档案管理] --- 修改正式档案点位信息 , request= {}", request);

        if (Objects.isNull(request.getId())) {
            log.warn("[正式档案管理] --- 修改正式档案点位信息, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }
        //任何状态都可修改
        monitorPointService.addOrUpdateAll(request);

        return ResponseInfo.success();
    }

    @ApiOperation("修改正式档案设备信息")
    @PostMapping("/updateDevice")
    public ResponseInfo updateDevice(@RequestBody MonitorDeviceRequest request, HttpServletRequest servletRequest) {
        log.info("[正式档案管理] --- 修改正式档案设备信息 , request= {}", request);
        if (Objects.isNull(request.getId())) {
            log.warn("[正式档案管理] --- 修改正式档案设备信息, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }
        //任何状态都允许修改
        monitorDeviceService.addOrUpdateAll(request);

        return ResponseInfo.success();
    }

    @ApiOperation("删除正式档案点位信息")
    @PostMapping("/delByPointId")
    public ResponseInfo delId(@RequestBody MonitorPointRequest request, HttpServletRequest servletRequest) {
        log.info("[正式档案管理] --- 删除正式档案点位信息 , request= {}", request);
        if (Objects.isNull(request.getId())) {
            log.warn("[正式档案管理] --- 删除正式档案点位信息, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        //任何状态都可以删除，但是点位下有设备仍然不允许删除
        monitorPointService.delByIdAll(request.getId());

        return ResponseInfo.success();
    }

    @ApiOperation("删除正式档案设备信息")
    @PostMapping("/delByDeviceId")
    public ResponseInfo delId(@RequestBody MonitorDeviceRequest request, HttpServletRequest servletRequest) {
        log.info("[正式档案管理] --- 删除正式档案设备信息 , request= {}", request);
        if (Objects.isNull(request.getId())) {
            log.warn("[正式档案管理] --- 删除正式档案设备信息, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        //任何状态都允许删除
        monitorDeviceService.delByIdAll(request.getId());

        return ResponseInfo.success();
    }

    @ApiOperation("正式档案管理导出")
    @GetMapping("/download")
    public void downloadExcel(@RequestBody MonitorPointRequest request, HttpServletResponse response) throws IOException {
        String fileName = "monitor_formal_point.xlsx";
        Page<MonitorPoint> monitorPointPage = monitorPointService.getFormalPoint(request);
        List<MonitorPoint> records = monitorPointPage.getRecords();
        List<MonitorPointExcel> list = new ArrayList<>();
        for (MonitorPoint record : records) {
            MonitorPointExcel monitorPointExcel = new MonitorPointExcel();
            BeanUtils.copyProperties(record, monitorPointExcel);

            list.add(monitorPointExcel);
        }

        downloadUtils.downloadExcel(fileName, MonitorPointExcel.class, list, response);
    }
}
