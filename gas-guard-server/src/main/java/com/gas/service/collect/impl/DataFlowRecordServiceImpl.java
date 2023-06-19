package com.gas.service.collect.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.dao.DataFlowRecordDao;
import com.gas.dto.DataFlowRecordDto;
import com.gas.entity.DataFlowRecord;
import com.gas.model.DataFlowRecordRequest;
import com.gas.service.collect.DataFlowRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DataFlowRecordServiceImpl implements DataFlowRecordService {

    @Autowired
    private DataFlowRecordDao dataFlowRecordDao;

    @Override
    public Page<DataFlowRecord> getPage(DataFlowRecordRequest request) {

        DataFlowRecordDto dto = new DataFlowRecordDto();
        BeanUtils.copyProperties(request, dto);

        return dataFlowRecordDao.selectPage(dto, request.getCurr(), request.getPageSize());
    }
}
