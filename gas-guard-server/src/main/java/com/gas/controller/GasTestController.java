package com.gas.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.common.ResponseInfo;
import com.gas.entity.GasTest;
import com.gas.enums.ErrorCodeEnum;
import com.gas.model.GasTestRequest;
import com.gas.service.GasTestService;
import com.gas.utils.FileDownloadUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Api("测试接口接口")
@RestController
@RequestMapping("/gas/test")
@Slf4j
public class GasTestController {

    @Autowired
    private GasTestService gasTestService;
    @Autowired
    private FileDownloadUtils downloadUtils;

    @ApiOperation("测试接口1")
    @RequiresPermissions("document:read")
    @PostMapping("/byId")
    public ResponseInfo getGasTestById(@RequestBody GasTestRequest request, HttpServletRequest servletRequest) {

        log.info("getGasTestById,req={}",request);

        if (request == null || request.getId() == null) {
            return ResponseInfo.error(ErrorCodeEnum.MISSING_REQUIRED_PARAM);
        }

        GasTest gasTest = gasTestService.getGasTestById(request);

        return ResponseInfo.success(gasTest);
    }


    @ApiOperation("测试接口2")
    @RequiresPermissions("document:write")
    @GetMapping("/list")
    public ResponseInfo list(HttpServletRequest servletRequest) {

        List<GasTest> list = gasTestService.list();

        return ResponseInfo.success(list);
    }


    @ApiOperation("分页接口demo")
    @RequiresPermissions("document:delete")
    @GetMapping("/page")
    public ResponseInfo pgae(HttpServletRequest servletRequest) {

        Page<GasTest> page = gasTestService.page();

        return ResponseInfo.success(page);
    }

    @ApiOperation("导出接口demo")
    @GetMapping("/download")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        String fileName = "test.xlsx";
        downloadUtils.downloadExcel(fileName, GasTest.class, Arrays.asList(
                new GasTest(1,"test1"),
                new GasTest(2, "test2")
        ), response);
    }
}
