package com.gas.controller.collect;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.common.ResponseInfo;
import com.gas.dto.DataFlowRecordDto;
import com.gas.entity.DataFlowRecord;
import com.gas.model.DataFlowRecordRequest;
import com.gas.service.collect.DataFlowRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api("数据流水记录")
@RestController
@RequestMapping("/collect/dataFlow")
@Slf4j
public class DataFlowRecordController {

    @Autowired
    private DataFlowRecordService dataFlowRecordService;

    @ApiOperation("查询数据流水记录")
    @PostMapping("/getPage")
    public ResponseInfo getPage(@RequestBody DataFlowRecordRequest request, HttpServletRequest servletRequest) {
        log.info("[数据流水记录] --- 查询数据流水记录 , request= {}", request);

        Page<DataFlowRecord> dataFlowRecordDtoPage = dataFlowRecordService.getPage(request);
        return ResponseInfo.success(dataFlowRecordDtoPage);
    }

}
