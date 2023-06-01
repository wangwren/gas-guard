package com.gas.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.common.ResponseInfo;
import com.gas.entity.GasTest;
import com.gas.enums.ErrorCodeEnum;
import com.gas.model.GasTestRequest;
import com.gas.service.GasTestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/gas/test")
@Slf4j
public class GasTestController {

    @Autowired
    private GasTestService gasTestService;

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


    @RequiresPermissions("document:write")
    @GetMapping("/list")
    public ResponseInfo list(HttpServletRequest servletRequest) {

        List<GasTest> list = gasTestService.list();

        return ResponseInfo.success(list);
    }


    @RequiresPermissions("document:delete")
    @GetMapping("/page")
    public ResponseInfo pgae(HttpServletRequest servletRequest) {

        Page<GasTest> page = gasTestService.page();

        return ResponseInfo.success(page);
    }
}
