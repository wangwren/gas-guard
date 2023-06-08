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

/**
 * 监测点位管理与监测点位建档差不多
 * 区别：
 *      1. 列表页查询增加多设备，无设备选项，多设备指有两个及两个以上点位
 *      2. 点位建档不展示已通过数据，点位管理展示档案所有状态数据
 *      3. 待审核状态也允许修改
 *      4. 可以删除，但是点位上有设备，依然不允许删除
 */
@Api("监测点位管理")
@RestController
@RequestMapping("/archive/pointManage")
@Slf4j
public class PointManageController {

    @Autowired
    private MonitorPointService monitorPointService;
    @Autowired
    private FileDownloadUtils downloadUtils;

    @ApiOperation("查询监测点位管理")
    @RequiresPermissions("archive:point_manage:all")
    @PostMapping("/getPage")
    public ResponseInfo getMonitorPoint(@RequestBody MonitorPointRequest request, HttpServletRequest servletRequest) {
        log.info("[监测点位管理] --- 查询监测点位管理 , request= {}", request);

        Page<MonitorPoint> monitorPointPage = monitorPointService.getPointManage(request);

        return ResponseInfo.success(monitorPointPage);
    }

    @ApiOperation("新增或修改监测点位管理")
    @PostMapping("/addOrUpdate")
    public ResponseInfo addOrUpdate(@RequestBody MonitorPointRequest request, HttpServletRequest servletRequest) {
        log.info("[监测点位管理] --- 新增或修改监测点位管理 , request= {}", request);

        //任何状态都可修改
        monitorPointService.addOrUpdateAll(request);

        return ResponseInfo.success();
    }

    @ApiOperation("删除监测点位管理")
    @PostMapping("/delById")
    public ResponseInfo delId(@RequestBody MonitorPointRequest request, HttpServletRequest servletRequest) {
        log.info("[监测点位管理] --- 删除监测点位管理 , request= {}", request);
        if (Objects.isNull(request.getId())) {
            log.warn("[监测点位管理] --- 删除监测点位管理, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        //任何状态都可以删除，但是点位下有设备仍然不允许删除
        monitorPointService.delByIdAll(request.getId());

        return ResponseInfo.success();
    }

    @ApiOperation("批量删除监测点位管理")
    @PostMapping("/delBatchIds")
    public ResponseInfo delBatchIds(@RequestBody BatchIdsRequest request, HttpServletRequest servletRequest) {
        log.info("[监测点位管理] --- 批量删除监测点位管理 , request= {}", request);
        if (CollectionUtils.isEmpty(request.getIds())) {
            log.warn("[监测点位管理] --- 批量删除监测点位管理, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        //任何状态都可以删除，但是点位下有设备仍然不允许删除
        monitorPointService.delBatchIdsAll(request.getIds());

        return ResponseInfo.success();
    }

    @ApiOperation("监测点位建档导出")
    @GetMapping("/download")
    public void downloadExcel(@RequestBody MonitorPointRequest request, HttpServletResponse response) throws IOException {
        String fileName = "monitor_point_manage.xlsx";
        Page<MonitorPoint> monitorPointPage = monitorPointService.getPointManage(request);
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
