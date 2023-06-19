package com.gas.dao;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.dao.mapper.DataFlowRecordMapper;
import com.gas.dto.DataFlowRecordDto;
import com.gas.entity.DataFlowRecord;
import com.gas.entity.MonitorDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataFlowRecordDao {

    @Autowired
    private DataFlowRecordMapper mapper;

    public Page<DataFlowRecord> selectPage(DataFlowRecordDto dataFlowRecord, Integer curr, Integer pageSize) {
        //查询第curr页，每页pageSize条
        Page<DataFlowRecord> page = new Page<>(curr,pageSize);

        QueryWrapper<DataFlowRecord> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(dataFlowRecord.getPointName()), "point_name", dataFlowRecord.getPointName());
        wrapper.eq(StrUtil.isNotBlank(dataFlowRecord.getOrganName()), "organ_name", dataFlowRecord.getOrganName());
        wrapper.eq(StrUtil.isNotBlank(dataFlowRecord.getDeviceName()), "device_name", dataFlowRecord.getDeviceName());
        wrapper.eq(StrUtil.isNotBlank(dataFlowRecord.getDeviceNo()), "device_no", dataFlowRecord.getDeviceNo());
        wrapper.ge(dataFlowRecord.getCollectBeginTime() != null, "collect_time", dataFlowRecord.getCollectBeginTime());
        wrapper.le(dataFlowRecord.getCollectEndTime() != null, "collect_time", dataFlowRecord.getCollectEndTime());
        wrapper.eq("enable", 1);

        Page<DataFlowRecord> dataFlowRecordPage = mapper.selectPage(page, wrapper);
        return dataFlowRecordPage;
    }
}
