package com.gas.controller.param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.common.ResponseInfo;
import com.gas.entity.DataDict;
import com.gas.enums.ErrorCodeEnum;
import com.gas.model.DataDictRequest;
import com.gas.service.param.DataDictService;
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

@Api("数据字典管理")
@RestController
@RequestMapping("/param/dict")
@Slf4j
public class DataDictController {

    @Autowired
    private DataDictService dataDictService;

    @ApiOperation("查询数据字典")
    @RequiresPermissions("param:dict:all")
    @PostMapping("/getPage")
    public ResponseInfo getDataDict(@RequestBody DataDictRequest request, HttpServletRequest servletRequest) {
        log.info("[数据字典管理] --- 查询数据字典 , request= {}", request);

        Page<DataDict> dataDictPage = dataDictService.getDataDict(request);

        return ResponseInfo.success(dataDictPage);
    }

    @ApiOperation("根据条件查询数据字典")
    //@RequiresPermissions("document:read")
    @PostMapping("/getCond")
    public ResponseInfo getCond(@RequestBody DataDictRequest request, HttpServletRequest servletRequest) {
        log.info("[数据字典管理] --- 根据条件查询数据字典 , request= {}", request);

        if (Objects.isNull(request.getType()) && Objects.isNull(request.getTypeName())
                && Objects.isNull(request.getDictKey()) && Objects.isNull(request.getDictValue())) {
            log.warn("[数据字典管理] --- 根据条件查询数据字典,参数有误,req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        List<DataDict> list = dataDictService.getByCond(request);

        return ResponseInfo.success(list);
    }

    @ApiOperation("新增或修改数据字典")
    @PostMapping("/addOrUpdate")
    public ResponseInfo addOrUpdate(@RequestBody DataDictRequest request, HttpServletRequest servletRequest) {
        log.info("[数据字典管理] --- 新增或修改数据字典 , request= {}", request);

        dataDictService.addOrUpdate(request);

        return ResponseInfo.success();
    }

    @ApiOperation("根据id查询数据字典")
    @PostMapping("/getById")
    public ResponseInfo getById(@RequestBody DataDictRequest request, HttpServletRequest servletRequest) {
        log.info("[数据字典管理] --- 根据Id查询数据字典 , request= {}", request);
        if (Objects.isNull(request.getId())) {
            log.warn("[数据字典管理] --- 根据Id查询数据字典, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        DataDict dataDict = dataDictService.getById(request.getId());
        return ResponseInfo.success(dataDict);
    }

    @ApiOperation("删除数据字典")
    @PostMapping("/delById")
    public ResponseInfo delId(@RequestBody DataDictRequest request, HttpServletRequest servletRequest) {
        log.info("[数据字典管理] --- 删除数据字典 , request= {}", request);
        if (Objects.isNull(request.getId())) {
            log.warn("[数据字典管理] --- 删除数据字典, 参数有误 req={}", request);
            return ResponseInfo.error(ErrorCodeEnum.INVALID_PARAM_VALUE);
        }

        dataDictService.delById(request.getId());

        return ResponseInfo.success();
    }
}
