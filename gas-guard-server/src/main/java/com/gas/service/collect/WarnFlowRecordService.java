package com.gas.service.collect;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.model.WarnFlowRecordRequest;
import com.gas.model.WarnFlowRecordResponse;

public interface WarnFlowRecordService {
    Page<WarnFlowRecordResponse> getPage(WarnFlowRecordRequest request);
}
