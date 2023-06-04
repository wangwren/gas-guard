package com.gas.controller.param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.common.ResponseInfo;
import com.gas.entity.SysConfig;
import com.gas.enums.ErrorCodeEnum;
import com.gas.model.DataDictRequest;
import com.gas.model.SysConfigRequest;
import com.gas.service.param.SysConfigService;
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

@Api("系统参数配置")
@RestController
@RequestMapping("/param/sys")
@Slf4j
public class SysConfigController {

    @Autowired
    private SysConfigService sysConfigService;

    @ApiOperation("查询系统参数")
    @RequiresPermissions("param:sys:all")
    @PostMapping("/getPage")
    public ResponseInfo getSysConfig(@RequestBody SysConfigRequest request, HttpServletRequest servletRequest) {
        log.info("[系统参数配置] --- 查询系统参数 , request= {}", request);

        Page<SysConfig> sysConfigPage = sysConfigService.getSysConfig(request);

        return ResponseInfo.success(sysConfigPage);
    }

    @ApiOperation("根据条件查询系统参数")
    @PostMapping("/getCond")
    public ResponseInfo getCond(@RequestBody SysConfigRequest request, HttpServletRequest servletRequest) {
        log.info("[系统参数配置] --- 根据条件查询系统参数 , request= {}", request);

        if (Objects.isNull(request.getSysName()) && Objects.isNull(request.getSysKey())) {
            log.warn("[系统参数配置] --- 根据条件查询系统参数,参数有误,req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        List<SysConfig> list = sysConfigService.getByCond(request);

        return ResponseInfo.success(list);
    }

    @ApiOperation("新增或修改系统参数")
    @PostMapping("/addOrUpdate")
    public ResponseInfo addOrUpdate(@RequestBody SysConfigRequest request, HttpServletRequest servletRequest) {
        log.info("[系统参数配置] --- 新增或修改系统参数 , request= {}", request);

        sysConfigService.addOrUpdate(request);

        return ResponseInfo.success();
    }

    @ApiOperation("根据id查询系统参数")
    @PostMapping("/getById")
    public ResponseInfo getById(@RequestBody SysConfigRequest request, HttpServletRequest servletRequest) {
        log.info("[系统参数配置] --- 根据Id查询系统参数 , request= {}", request);
        if (Objects.isNull(request.getId())) {
            log.warn("[系统参数配置] --- 根据Id查询系统参数, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        SysConfig sysConfig = sysConfigService.getById(request.getId());
        return ResponseInfo.success(sysConfig);
    }

    @ApiOperation("删除系统参数")
    @PostMapping("/delById")
    public ResponseInfo delId(@RequestBody DataDictRequest request, HttpServletRequest servletRequest) {
        log.info("[系统参数配置] --- 删除系统参数 , request= {}", request);
        if (Objects.isNull(request.getId())) {
            log.warn("[系统参数配置] --- 删除系统参数, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        sysConfigService.delById(request.getId());

        return ResponseInfo.success();
    }
}
