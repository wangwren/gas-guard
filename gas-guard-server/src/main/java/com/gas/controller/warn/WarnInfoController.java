package com.gas.controller.warn;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.common.ResponseInfo;
import com.gas.dto.WarnInfoDto;
import com.gas.enums.ErrorCodeEnum;
import com.gas.excel.MonitorPointExcel;
import com.gas.excel.WarnInfoExcel;
import com.gas.exception.CommonException;
import com.gas.model.WarnDealInfoRequest;
import com.gas.model.WarnInfoRequest;
import com.gas.service.warn.WarnInfoService;
import com.gas.utils.FileDownloadUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Api("预警管理")
@RestController
@RequestMapping("/warn/warnInfo")
@Slf4j
public class WarnInfoController {

    @Autowired
    private WarnInfoService warnInfoService;
    @Autowired
    private FileDownloadUtils downloadUtils;


    @ApiOperation("查询正式预警信息")
    //@RequiresPermissions("warn:warn_formal:all")
    @PostMapping("/getFormalPage")
    public ResponseInfo getWarnFormalInfo(@RequestBody WarnInfoRequest request, HttpServletRequest servletRequest) {
        log.info("[预警管理] --- 查询正式预警信息 , request= {}", request);

        Page<WarnInfoDto> warnInfoDtoPage = warnInfoService.getWarnFormalInfo(request);

        return ResponseInfo.success(warnInfoDtoPage);
    }

    @ApiOperation("查询调试预警信息")
    //@RequiresPermissions("warn:warn_debug:all")
    @PostMapping("/getDebugPage")
    public ResponseInfo getWarnDebugInfo(@RequestBody WarnInfoRequest request, HttpServletRequest servletRequest) {
        log.info("[预警管理] --- 查询调试预警信息 , request= {}", request);

        Page<WarnInfoDto> warnInfoDtoPage = warnInfoService.getWarnDebugInfo(request);

        return ResponseInfo.success(warnInfoDtoPage);
    }

    @ApiOperation("查询预警信息详情")
    @PostMapping("/getWarnInfoDetail")
    public ResponseInfo getWarnInfoDetail(@RequestBody WarnInfoRequest request, HttpServletRequest servletRequest) {
        log.info("[预警管理] --- 查询预警信息详情 , request= {}", request);
        if (Objects.isNull(request.getId())) {
            log.warn("[预警管理] --- 查询预警信息详情, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }
        WarnInfoDto warnInfoDto = warnInfoService.getWarnInfoDetail(request);

        return ResponseInfo.success(warnInfoDto);
    }

    @ApiOperation("预警信息确认")
    @PostMapping("/confirmWarnInfo")
    public ResponseInfo confirmWarnInfo(@RequestBody WarnInfoRequest request, HttpServletRequest servletRequest) {
        log.info("[预警管理] --- 预警信息确认 , request= {}", request);
        if (Objects.isNull(request.getId())) {
            log.warn("[预警管理] --- 预警信息确认, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }
        warnInfoService.confirmWarnInfo(request);

        return ResponseInfo.success();
    }

    @ApiOperation("预警信息提交反馈")
    @PostMapping("/commitWarnInfo")
    public ResponseInfo commitWarnInfo(@RequestBody @Validated WarnDealInfoRequest request, HttpServletRequest servletRequest) {
        log.info("[预警管理] --- 预警信息确认 , request= {}", request);
        if (Objects.equals(request.getBlockPolicy(), "启用") && Objects.isNull(request.getBlockTime())) {
            log.warn("[预警管理] --- 预警信息确认--屏蔽策略启用，屏蔽时长不能为空");
            throw new CommonException(500,"屏蔽策略启用，屏蔽时长不能为空");
        }
        warnInfoService.commitWarnInfo(request);

        return ResponseInfo.success();
    }

    @ApiOperation("预警管理正式数据导出")
    @GetMapping("formal/download")
    public void downloadFormalExcel(@RequestBody WarnInfoRequest request, HttpServletResponse response) throws IOException {
        String fileName = "warn_info_formal.xlsx";
        Page<WarnInfoDto> warnFormalInfo = warnInfoService.getWarnFormalInfo(request);
        List<WarnInfoDto> records = warnFormalInfo.getRecords();
        List<WarnInfoExcel> list = new ArrayList<>();
        for (WarnInfoDto record : records) {
            WarnInfoExcel warnInfoExcel = new WarnInfoExcel();
            BeanUtils.copyProperties(record, warnInfoExcel);

            list.add(warnInfoExcel);
        }

        downloadUtils.downloadExcel(fileName, MonitorPointExcel.class, list, response);
    }

    @ApiOperation("预警管理调试数据导出")
    @PostMapping("debug/download")
    public void downloadDebugExcel(@RequestBody WarnInfoRequest request, HttpServletResponse response) throws IOException {
        String fileName = "warn_info_formal.xlsx";
        Page<WarnInfoDto> warnFormalInfo = warnInfoService.getWarnDebugInfo(request);
        List<WarnInfoDto> records = warnFormalInfo.getRecords();
        List<WarnInfoExcel> list = new ArrayList<>();
        for (WarnInfoDto record : records) {
            WarnInfoExcel warnInfoExcel = new WarnInfoExcel();
            BeanUtils.copyProperties(record, warnInfoExcel);

            list.add(warnInfoExcel);
        }

        downloadUtils.downloadExcel(fileName, MonitorPointExcel.class, list, response);
    }
}
