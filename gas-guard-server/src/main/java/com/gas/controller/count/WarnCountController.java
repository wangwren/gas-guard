package com.gas.controller.count;

import com.gas.common.ResponseInfo;
import com.gas.dto.WarnCountDto;
import com.gas.excel.WarnCountExcel;
import com.gas.model.WarnCountRequest;
import com.gas.service.count.WarnCountService;
import com.gas.utils.FileDownloadUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Api("统计分析")
@RestController
@RequestMapping("/count/warn")
@Slf4j
public class WarnCountController {

    @Autowired
    private WarnCountService warnCountService;
    @Autowired
    private FileDownloadUtils downloadUtils;

    @ApiOperation("预警设备统计")
    @PostMapping("/count")
    public ResponseInfo count(@RequestBody WarnCountRequest request, HttpServletRequest servletRequest) {
        log.info("[统计分析] --- 预警设备统计 request={}", request);

        List<WarnCountDto> countDtos = warnCountService.warnCount(request);
        return ResponseInfo.success(countDtos);
    }

    @ApiOperation("预警设备统计导出")
    @PostMapping("/download")
    public void downloadExcel(@RequestBody WarnCountRequest request, HttpServletResponse response) throws IOException {
        String fileName = "warn_count.xlsx";
        List<WarnCountDto> countDtos = warnCountService.warnCount(request);
        List<WarnCountExcel> list = new ArrayList<>();
        for (WarnCountDto record : countDtos) {
            WarnCountExcel monitorDeviceExcel = new WarnCountExcel();
            BeanUtils.copyProperties(record, monitorDeviceExcel);

            list.add(monitorDeviceExcel);
        }

        downloadUtils.downloadExcel(fileName, WarnCountExcel.class, list, response);
    }
}
