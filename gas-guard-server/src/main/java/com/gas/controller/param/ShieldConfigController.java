package com.gas.controller.param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.common.ResponseInfo;
import com.gas.entity.MonitorDevice;
import com.gas.entity.ShieldConfig;
import com.gas.enums.ErrorCodeEnum;
import com.gas.model.ShieldConfigRequest;
import com.gas.service.archive.MonitorDeviceService;
import com.gas.service.param.ShieldConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Api("屏蔽策略配置")
@RestController
@RequestMapping("/param/shield")
@Slf4j
public class ShieldConfigController {

    @Autowired
    private ShieldConfigService shieldConfigService;
    @Autowired
    private MonitorDeviceService deviceService;

    @ApiOperation("查询屏蔽策略")
    //@RequiresPermissions("param:shield:all")
    @PostMapping("/getPage")
    public ResponseInfo getShieldConfig(@RequestBody ShieldConfigRequest request, HttpServletRequest servletRequest) {
        log.info("[屏蔽策略配置] --- 查询屏蔽策略 , request= {}", request);

        Page<ShieldConfig> shieldConfigPage = shieldConfigService.getShieldConfig(request);

        return ResponseInfo.success(shieldConfigPage);
    }

    @ApiOperation("新增屏蔽策略")
    @PostMapping("/add")
    public ResponseInfo add(@RequestBody ShieldConfigRequest request, HttpServletRequest servletRequest) {
        log.info("[屏蔽策略配置] --- 新增屏蔽策略 , request= {}", request);

        shieldConfigService.add(request);

        return ResponseInfo.success();
    }

    @ApiOperation("查询屏蔽设备列表")
    @PostMapping("/getDeviceList")
    public ResponseInfo getDeviceList(HttpServletRequest servletRequest) {
        log.info("[屏蔽策略配置] --- 查询屏蔽策略 ");
        List<MonitorDevice> deviceList = deviceService.getDeviceList();
        return ResponseInfo.success(deviceList);
    }

    @ApiOperation("根据id查询屏蔽策略")
    @PostMapping("/getById")
    public ResponseInfo getById(@RequestBody ShieldConfigRequest request, HttpServletRequest servletRequest) {
        log.info("[屏蔽策略配置] --- 根据id查询屏蔽策略 , request= {}", request);
        if (Objects.isNull(request.getId())) {
            log.warn("[屏蔽策略配置] --- 根据id查询屏蔽策略, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        ShieldConfig shieldConfig = shieldConfigService.getById(request.getId());
        return ResponseInfo.success(shieldConfig);
    }

    @ApiOperation("撤销屏蔽策略")
    @PostMapping("/updateById")
    public ResponseInfo updateById(@RequestBody ShieldConfigRequest request, HttpServletRequest servletRequest) {
        log.info("[屏蔽策略配置] --- 删除离线规则 , request= {}", request);
        if (Objects.isNull(request.getId())) {
            log.warn("[屏蔽策略配置] --- 撤销屏蔽策略, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        shieldConfigService.updateById(request.getId());

        return ResponseInfo.success();
    }
}
