package com.gas.service.collect;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.dto.DataFlowRecordDto;
import com.gas.entity.DataFlowRecord;
import com.gas.model.DataFlowRecordRequest;

public interface DataFlowRecordService {
    Page<DataFlowRecord> getPage(DataFlowRecordRequest request);
}
