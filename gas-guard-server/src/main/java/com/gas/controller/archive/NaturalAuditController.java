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
 * 天然气档案审核
 * 只查询 燃气种类 是 天然气，并且档案状态 为 待审核的数据
 *
 * 只有查询和导出接口
 * 其他接口复用设备档案审核 DeviceAuditController
 */
@Api("天然气档案审核")
@RestController
@RequestMapping("/archive/naturalAudit")
@Slf4j
public class NaturalAuditController {

    @Autowired
    private MonitorDeviceService monitorDeviceService;
    @Autowired
    private FileDownloadUtils downloadUtils;

    @ApiOperation("查询天然气档案审核(只包含待审核)")
    @RequiresPermissions("archive:natural_audit:all")
    @PostMapping("/getPage")
    public ResponseInfo getNaturalAudit(@RequestBody MonitorDeviceRequest request, HttpServletRequest servletRequest) {
        log.info("[天然气档案审核] --- 查询天然气档案审核(只包含待审核) , request= {}", request);

        Page<MonitorDeviceDto> monitorDevicePage = monitorDeviceService.getNaturalAudit(request);

        return ResponseInfo.success(monitorDevicePage);
    }

    @ApiOperation("天然气档案审核导出")
    @GetMapping("/download")
    public void downloadExcel(@RequestBody MonitorDeviceRequest request, HttpServletResponse response) throws IOException {
        String fileName = "monitor_natural_audit.xlsx";
        Page<MonitorDeviceDto> monitorDeviceDtoPage = monitorDeviceService.getNaturalAudit(request);
        List<MonitorDeviceDto> records = monitorDeviceDtoPage.getRecords();
        List<MonitorDeviceExcel> list = new ArrayList<>();
        for (MonitorDeviceDto record : records) {
            MonitorDeviceExcel monitorDeviceExcel = new MonitorDeviceExcel();
            BeanUtils.copyProperties(record, monitorDeviceExcel);

            list.add(monitorDeviceExcel);
        }

        downloadUtils.downloadExcel(fileName, MonitorPointExcel.class, list, response);
    }

    /**
     * TODO相应值还差一个预警记录
     */
//    @ApiOperation("发起审核")
//    @PostMapping("/doAudit")
//    public ResponseInfo doAudit(@RequestBody MonitorDeviceRequest request, HttpServletRequest servletRequest) {
//        log.info("[天然气档案审核] --- 发起审核 , request= {}", request);
//        if (Objects.isNull(request.getId())) {
//            log.warn("[天然气档案审核] --- 发起审核, 参数有误 req={}", request);
//            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
//        }
//
//        DeviceAuditDto deviceAudit = monitorDeviceService.doAudit(request);
//        return ResponseInfo.success(deviceAudit);
//    }
//
//    @ApiOperation("审核页面修改信息")
//    @PostMapping("/updateInfo")
//    public ResponseInfo updateInfo(@RequestBody DeviceAuditRequest request, HttpServletRequest servletRequest) {
//        log.info("[天然气档案审核] --- 审核页面修改信息 , request= {}", request);
//        if (Objects.isNull(request.getPointRequest()) && Objects.isNull(request.getDeviceRequest())) {
//            log.warn("[天然气档案审核] --- 审核页面修改信息, 参数有误 req={}", request);
//            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
//        }
//
//        if (!Objects.isNull(request.getPointRequest()) && !Objects.isNull(request.getPointRequest().getId())) {
//            //点位信息修改
//            monitorPointService.addOrUpdateAll(request.getPointRequest());
//        }
//
//        if (!Objects.isNull(request.getDeviceRequest()) && !Objects.isNull(request.getDeviceRequest().getId())) {
//            //设备信息修改
//            monitorDeviceService.addOrUpdateAll(request.getDeviceRequest());
//        }
//
//        return ResponseInfo.success();
//    }
//
//    @ApiOperation("真正审核")
//    @PostMapping("/auditing")
//    public ResponseInfo auditing(@RequestBody AuditingtRequest request, HttpServletRequest servletRequest) {
//        log.info("[天然气档案审核] --- 真正审核 , request= {}", request);
//        if (Objects.equals(request.getAuditStatus(), GlobalConstants.AUDIT_NO_PASS)
//                && StrUtil.isBlank(request.getAuditFeedback())) {
//            log.warn("[天然气档案审核] 审核状态为 未通过，审核反馈不允许为空 req= {}", request);
//            throw new CommonException(500 , "请输入审核反馈");
//        }
//
//        deviceAuditService.auditing(request);
//        return ResponseInfo.success();
//    }
//
//    /**
//     * 传入id为设备id，通过设备id查询点位id
//     */
//    @ApiOperation("批量通过")
//    @PostMapping("/passBatchIds")
//    public ResponseInfo passBatchIds(@RequestBody BatchIdsRequest request, HttpServletRequest servletRequest) {
//        log.info("[天然气档案审核] --- 批量通过 , request= {}", request);
//        if (CollectionUtils.isEmpty(request.getIds())) {
//            log.warn("[天然气档案审核] --- 批量通过, 参数有误 req={}", request);
//            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
//        }
//
//        deviceAuditService.passBatchIds(request);
//        return ResponseInfo.success();
//    }
//
//    /**
//     * 传入id为设备id，通过设备id查询点位id
//     */
//    @ApiOperation("批量退回")
//    @PostMapping("/noPassBatchIds")
//    public ResponseInfo noPassBatchIds(@RequestBody NoPassBatchIdsRequest request, HttpServletRequest servletRequest) {
//        log.info("[天然气档案审核] --- 批量退回 , request= {}", request);
//        if (CollectionUtils.isEmpty(request.getIds())
//                || StrUtil.isBlank(request.getAuditFeedback())) {
//            log.warn("[天然气档案审核] --- 批量退回, 参数有误 req={}", request);
//            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
//        }
//
//        deviceAuditService.noPassBatchIds(request);
//        return ResponseInfo.success();
//    }


}
