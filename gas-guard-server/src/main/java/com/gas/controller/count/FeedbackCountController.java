package com.gas.controller.count;

import com.gas.common.ResponseInfo;
import com.gas.dto.FeedbackCountDto;
import com.gas.dto.WarnTimeDto;
import com.gas.model.WarnCountRequest;
import com.gas.service.count.FeedbackCountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api("统计分析")
@RestController
@RequestMapping("/count/feedback")
@Slf4j
public class FeedbackCountController {

    @Autowired
    private FeedbackCountService feedbackCountService;

    @ApiOperation("处置反馈统计")
    @PostMapping("/count")
    public ResponseInfo count(@RequestBody WarnCountRequest request, HttpServletRequest servletRequest) {
        log.info("[统计分析] --- 处置反馈统计 request={}", request);

        List<FeedbackCountDto> feedbackCountDtos = feedbackCountService.feedBackCount(request);
        return ResponseInfo.success(feedbackCountDtos);
    }
}
