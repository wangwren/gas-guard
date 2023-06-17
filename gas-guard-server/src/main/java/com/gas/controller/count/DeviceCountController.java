package com.gas.controller.count;

import com.gas.common.ResponseInfo;
import com.gas.dto.DeviceCountDto;
import com.gas.model.DeviceCountRequest;
import com.gas.service.count.DeviceCountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api("统计分析")
@RestController
@RequestMapping("/count/device")
@Slf4j
public class DeviceCountController {

    @Autowired
    private DeviceCountService deviceCountService;

    @ApiOperation("设备建档统计")
    @PostMapping("/count")
    public ResponseInfo count(@RequestBody DeviceCountRequest request, HttpServletRequest servletRequest) {
        log.info("[统计分析] --- 设备建档统计 request={}", request);

//        if (Objects.isNull(request.getCreateTime()) || Objects.isNull(request.getEndTime())) {
//            log.warn("[统计分析] --- 设备建档统计,开始时间或结束时间为空request={}", request);
//            return ResponseInfo.success();
//        }

        DeviceCountDto dto = deviceCountService.getDeviceCount(request);
        return ResponseInfo.success(dto);
    }
}
