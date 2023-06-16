package com.gas.controller.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.common.ResponseInfo;
import com.gas.dto.WarnInfoDto;
import com.gas.enums.ErrorCodeEnum;
import com.gas.excel.MonitorPointExcel;
import com.gas.excel.WarnInfoExcel;
import com.gas.model.WarnInfoRequest;
import com.gas.service.warn.WarnInfoService;
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

@Api("预警记录查询")
@RestController
@RequestMapping("/query/warnQuery")
@Slf4j
public class WarnInfoQueryController {

    @Autowired
    private WarnInfoService warnInfoService;
    @Autowired
    private FileDownloadUtils downloadUtils;

    @ApiOperation("查询预警记录查询")
    @PostMapping("/getPage")
    public ResponseInfo getPage(@RequestBody WarnInfoRequest request, HttpServletRequest servletRequest) {
        log.info("[预警记录查询] --- 查询预警记录查询 , request= {}", request);

        Page<WarnInfoDto> warnInfoDtoPage = warnInfoService.getPage(request);

        return ResponseInfo.success(warnInfoDtoPage);
    }

    @ApiOperation("查询预警信息详情")
    @PostMapping("/getWarnInfoById")
    public ResponseInfo getWarnInfoById(@RequestBody WarnInfoRequest request, HttpServletRequest servletRequest) {
        log.info("[预警记录查询] --- 查询预警信息详情 , request= {}", request);
        if (Objects.isNull(request.getId())) {
            log.warn("[预警记录查询] --- 查询预警信息详情, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }
        WarnInfoDto warnInfoDto = warnInfoService.getWarnInfoDetail(request);

        return ResponseInfo.success(warnInfoDto);
    }

    @ApiOperation("预警信息导出")
    @GetMapping("/download")
    public void download(@RequestBody WarnInfoRequest request, HttpServletResponse response) throws IOException {
        String fileName = "warn_info.xlsx";
        Page<WarnInfoDto> warnFormalInfo = warnInfoService.getPage(request);
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
