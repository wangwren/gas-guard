package com.gas.controller.archive;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.common.ResponseInfo;
import com.gas.entity.MonitorPoint;
import com.gas.enums.ErrorCodeEnum;
import com.gas.excel.MonitorPointExcel;
import com.gas.model.BatchIdsRequest;
import com.gas.model.MonitorPointRequest;
import com.gas.service.archive.MonitorPointService;
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

@Api("监测点位建档")
@RestController
@RequestMapping("/archive/monitorPoint")
@Slf4j
public class MonitorPointController {

    @Autowired
    private MonitorPointService monitorPointService;
    @Autowired
    private FileDownloadUtils downloadUtils;

    @ApiOperation("查询监测点位建档")
    @RequiresPermissions("param:archive:all")
    @PostMapping("/getPage")
    public ResponseInfo getMonitorPoint(@RequestBody MonitorPointRequest request, HttpServletRequest servletRequest) {
        log.info("[监测点位建档] --- 查询监测点位建档 , request= {}", request);

        Page<MonitorPoint> monitorPointPage = monitorPointService.getMonitorPoint(request);

        return ResponseInfo.success(monitorPointPage);
    }

//    @ApiOperation("根据条件查询监测点位建档")
//    //@RequiresPermissions("document:read")
//    @PostMapping("/getCond")
//    public ResponseInfo getCond(@RequestBody MonitorPointRequest request, HttpServletRequest servletRequest) {
//        log.info("[监测点位建档] --- 根据条件查询监测点位建档 , request= {}", request);
//
//        List<MonitorPoint> list = monitorPointService.getByCond(request);
//
//        return ResponseInfo.success(list);
//    }

    @ApiOperation("新增或修改监测点位建档")
    @PostMapping("/addOrUpdate")
    public ResponseInfo addOrUpdate(@RequestBody MonitorPointRequest request, HttpServletRequest servletRequest) {
        log.info("[监测点位建档] --- 新增或修改监测点位建档 , request= {}", request);

        //待审核和未通过 状态不允许修改
        monitorPointService.addOrUpdate(request);

        return ResponseInfo.success();
    }

    @ApiOperation("根据id查询监测点位建档")
    @PostMapping("/getById")
    public ResponseInfo getById(@RequestBody MonitorPointRequest request, HttpServletRequest servletRequest) {
        log.info("[监测点位建档] --- 根据id查询监测点位建档 , request= {}", request);
        if (Objects.isNull(request.getId())) {
            log.warn("[监测点位建档] --- 根据id查询监测点位建档, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        MonitorPoint monitorPoint = monitorPointService.getById(request.getId());
        return ResponseInfo.success(monitorPoint);
    }

    @ApiOperation("删除监测点位建档")
    @PostMapping("/delById")
    public ResponseInfo delId(@RequestBody MonitorPointRequest request, HttpServletRequest servletRequest) {
        log.info("[监测点位建档] --- 删除监测点位建档 , request= {}", request);
        if (Objects.isNull(request.getId())) {
            log.warn("[监测点位建档] --- 删除监测点位建档, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        //待审核和未通过不允许删除
        monitorPointService.delById(request.getId());

        return ResponseInfo.success();
    }

    @ApiOperation("批量删除监测点位建档")
    @PostMapping("/delBatchIds")
    public ResponseInfo delBatchIds(@RequestBody BatchIdsRequest request, HttpServletRequest servletRequest) {
        log.info("[监测点位建档] --- 批量删除监测点位建档 , request= {}", request);
        if (CollectionUtils.isEmpty(request.getIds())) {
            log.warn("[监测点位建档] --- 批量删除监测点位建档, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        //待审核不允许删除
        monitorPointService.delBatchIds(request.getIds());

        return ResponseInfo.success();
    }

    @ApiOperation("导出接口demo")
    @GetMapping("/download")
    public void downloadExcel(@RequestBody MonitorPointRequest request, HttpServletResponse response) throws IOException {
        String fileName = "监测点位建档.xlsx";
        Page<MonitorPoint> monitorPointPage = monitorPointService.getMonitorPoint(request);
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
