package com.gas.controller.count;

import com.gas.common.ResponseInfo;
import com.gas.dto.OverviewDto;
import com.gas.service.count.OverviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api("统计分析")
@RestController
@RequestMapping("/count")
@Slf4j
public class OverviewController {

    @Autowired
    private OverviewService overviewService;

    @ApiOperation("监测概况统计")
    @PostMapping("/overview")
    public ResponseInfo overview(HttpServletRequest servletRequest) {
        log.info("[统计分析] --- 监测概况统计");

        OverviewDto overviewDto = overviewService.getOverviewAll();
        return ResponseInfo.success(overviewDto);
    }
}
