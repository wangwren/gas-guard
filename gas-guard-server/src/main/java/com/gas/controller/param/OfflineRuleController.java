package com.gas.controller.param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.common.ResponseInfo;
import com.gas.entity.OfflineRule;
import com.gas.enums.ErrorCodeEnum;
import com.gas.model.OfflineRuleRequest;
import com.gas.service.param.OfflineRuleService;
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

@Api("离线规则配置")
@RestController
@RequestMapping("/param/offline")
@Slf4j
public class OfflineRuleController {

    @Autowired
    private OfflineRuleService offlineRuleService;

    @ApiOperation("查询离线规则")
    //@RequiresPermissions("param:offline:all")
    @PostMapping("/getPage")
    public ResponseInfo getOfflineRule(@RequestBody OfflineRuleRequest request, HttpServletRequest servletRequest) {
        log.info("[离线规则配置] --- 查询离线规则 , request= {}", request);

        Page<OfflineRule> offlineRulePage = offlineRuleService.getOfflineRule(request);

        return ResponseInfo.success(offlineRulePage);
    }

    @ApiOperation("新增或修改离线规则")
    @PostMapping("/addOrUpdate")
    public ResponseInfo addOrUpdate(@RequestBody OfflineRuleRequest request, HttpServletRequest servletRequest) {
        log.info("[离线规则配置] --- 新增或修改离线规则 , request= {}", request);

        offlineRuleService.addOrUpdate(request);

        return ResponseInfo.success();
    }

    @ApiOperation("根据id查询离线规则")
    @PostMapping("/getById")
    public ResponseInfo getById(@RequestBody OfflineRuleRequest request, HttpServletRequest servletRequest) {
        log.info("[离线规则配置] --- 根据id查询离线规则 , request= {}", request);
        if (Objects.isNull(request.getId())) {
            log.warn("[离线规则配置] --- 根据id查询离线规则, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        OfflineRule offlineRule = offlineRuleService.getById(request.getId());
        return ResponseInfo.success(offlineRule);
    }

    @ApiOperation("删除离线规则")
    @PostMapping("/delById")
    public ResponseInfo delId(@RequestBody OfflineRuleRequest request, HttpServletRequest servletRequest) {
        log.info("[离线规则配置] --- 删除离线规则 , request= {}", request);
        if (Objects.isNull(request.getId())) {
            log.warn("[离线规则配置] --- 删除离线规则, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        offlineRuleService.delById(request.getId());

        return ResponseInfo.success();
    }
}
