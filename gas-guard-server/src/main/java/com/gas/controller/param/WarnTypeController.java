package com.gas.controller.param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.common.ResponseInfo;
import com.gas.dto.WarnTypeDto;
import com.gas.entity.WarnType;
import com.gas.enums.ErrorCodeEnum;
import com.gas.model.WarnTypeRequest;
import com.gas.service.param.WarnTypeService;
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
import java.util.Objects;

@Api("预警类型配置")
@RestController
@RequestMapping("/param/warn")
@Slf4j
public class WarnTypeController {

    @Autowired
    private WarnTypeService warnTypeService;

    @ApiOperation("查询预警类型")
    //@RequiresPermissions("param:warn:all")
    @PostMapping("/getPage")
    public ResponseInfo getWarnType(@RequestBody WarnTypeRequest request, HttpServletRequest servletRequest) {
        log.info("[预警类型配置] --- 查询预警类型 , request= {}", request);

        Page<WarnTypeDto> warnTypePage = warnTypeService.getWarnType(request);

        return ResponseInfo.success(warnTypePage);
    }

    @ApiOperation("新增或修改预警类型")
    @PostMapping("/addOrUpdate")
    public ResponseInfo addOrUpdate(@RequestBody WarnTypeRequest request, HttpServletRequest servletRequest) {
        log.info("[预警类型配置] --- 新增或修改预警类型 , request= {}", request);

        warnTypeService.addOrUpdate(request);

        return ResponseInfo.success();
    }

    @ApiOperation("根据id查询预警类型")
    @PostMapping("/getById")
    public ResponseInfo getById(@RequestBody WarnTypeRequest request, HttpServletRequest servletRequest) {
        log.info("[预警类型配置] --- 根据Id查询预警类型 , request= {}", request);
        if (Objects.isNull(request.getId())) {
            log.warn("[预警类型配置] --- 根据Id查询预警类型, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        WarnTypeDto warnType = warnTypeService.getById(request.getId());
        return ResponseInfo.success(warnType);
    }

    @ApiOperation("删除预警类型")
    @PostMapping("/delById")
    public ResponseInfo delId(@RequestBody WarnTypeRequest request, HttpServletRequest servletRequest) {
        log.info("[预警类型配置] --- 删除预警类型 , request= {}", request);
        if (Objects.isNull(request.getId())) {
            log.warn("[预警类型配置] --- 删除预警类型, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        warnTypeService.delById(request.getId());

        return ResponseInfo.success();
    }
}
