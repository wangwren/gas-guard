package com.gas.controller.count;

import com.gas.common.ResponseInfo;
import com.gas.dto.OverviewDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api("统计分析")
@RestController
@RequestMapping("/count/device")
@Slf4j
public class DeviceCountController {

    @ApiOperation("设备建档统计")
    @PostMapping("/count")
    public ResponseInfo count(HttpServletRequest servletRequest) {
        log.info("[统计分析] --- 设备建档统计");

        return ResponseInfo.success();
    }
}
