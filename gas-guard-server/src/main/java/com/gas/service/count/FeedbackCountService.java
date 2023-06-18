package com.gas.service.count;

import com.gas.dto.FeedbackCountDto;
import com.gas.model.WarnCountRequest;

import java.util.List;

public interface FeedbackCountService {
    List<FeedbackCountDto> feedBackCount(WarnCountRequest request);
}
