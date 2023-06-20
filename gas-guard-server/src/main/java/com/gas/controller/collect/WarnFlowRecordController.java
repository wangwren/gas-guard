package com.gas.controller.collect;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.common.ResponseInfo;
import com.gas.excel.MonitorPointExcel;
import com.gas.excel.WarnFlowRecordExcel;
import com.gas.model.WarnFlowRecordRequest;
import com.gas.model.WarnFlowRecordResponse;
import com.gas.service.collect.WarnFlowRecordService;
import com.gas.utils.FileDownloadUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Api("告警流水记录")
@RestController
@RequestMapping("/collect/warnFlow")
@Slf4j
public class WarnFlowRecordController {

    @Autowired
    private WarnFlowRecordService warnFlowRecordService;
    @Autowired
    private FileDownloadUtils downloadUtils;

    @ApiOperation("查询告警流水记录")
    @PostMapping("/getPage")
    public ResponseInfo getPage(@RequestBody WarnFlowRecordRequest request, HttpServletRequest servletRequest) {
        log.info("[数据流水记录] --- 查询告警流水记录 , request= {}", request);

        Page<WarnFlowRecordResponse> warnFlowRecordResponsePage = warnFlowRecordService.getPage(request);
        return ResponseInfo.success(warnFlowRecordResponsePage);
    }

    @ApiOperation("告警流水记录导出")
    @PostMapping("/download")
    public void downloadExcel(@RequestBody WarnFlowRecordRequest request, HttpServletResponse response) throws IOException {
        String fileName = "warn_flow_record.xlsx";
        Page<WarnFlowRecordResponse> warnFlowRecordResponsePage = warnFlowRecordService.getPage(request);
        List<WarnFlowRecordResponse> records = warnFlowRecordResponsePage.getRecords();
        List<WarnFlowRecordExcel> list = new ArrayList<>();
        for (WarnFlowRecordResponse record : records) {
            WarnFlowRecordExcel excel = new WarnFlowRecordExcel();
            BeanUtils.copyProperties(record, excel);

            list.add(excel);
        }

        downloadUtils.downloadExcel(fileName, MonitorPointExcel.class, list, response);
    }
}
