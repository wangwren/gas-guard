package com.gas.dao;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.common.GlobalConstants;
import com.gas.dao.mapper.MonitorPointMapper;
import com.gas.dto.MonitorPointDto;
import com.gas.entity.MonitorPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class MonitorPointDao {

    @Autowired
    private MonitorPointMapper mapper;

    public Page<MonitorPoint> selectPage(MonitorPointDto monitorPoint, Integer curr, Integer pageSize) {
        //查询第curr页，每页pageSize条
        Page<MonitorPoint> page = new Page<>(curr,pageSize);

        QueryWrapper<MonitorPoint> wrapper = new QueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(monitorPoint.getPointName()), "point_name", monitorPoint.getPointName());
        wrapper.eq(StrUtil.isNotBlank(monitorPoint.getOrganName()), "organ_name", monitorPoint.getOrganName());
        wrapper.like(StrUtil.isNotBlank(monitorPoint.getAddress()), "address", monitorPoint.getAddress());
        wrapper.eq(StrUtil.isNotBlank(monitorPoint.getContract()), "contract", monitorPoint.getContract());
        wrapper.eq(StrUtil.isNotBlank(monitorPoint.getContractMobile()), "contract_mobile", monitorPoint.getContractMobile());
        wrapper.eq(StrUtil.isNotBlank(monitorPoint.getPointStatus()), "point_status", monitorPoint.getPointStatus());
        wrapper.eq(StrUtil.isNotBlank(monitorPoint.getGasCompany()), "gas_company", monitorPoint.getGasCompany());
        wrapper.eq(StrUtil.isNotBlank(monitorPoint.getGasType()), "gas_type", monitorPoint.getGasType());
        wrapper.eq(StrUtil.isNotBlank(monitorPoint.getUserType()), "user_type", monitorPoint.getUserType());
        wrapper.eq(StrUtil.isNotBlank(monitorPoint.getArchiveStatus()), "archive_status", monitorPoint.getArchiveStatus());
        wrapper.ge(monitorPoint.getCreateTime() != null, "create_time", monitorPoint.getCreateTime());
        wrapper.le(monitorPoint.getEndTime() != null, "create_time", monitorPoint.getEndTime());

        //查询可用数据
        wrapper.eq("enable", 1);
        //不包含已通过数据
        wrapper.ne("archive_status", GlobalConstants.ARCHIVE_PASS_STATUS);

        Page<MonitorPoint> monitorPointPage = mapper.selectPage(page, wrapper);
        return monitorPointPage;
    }

    public Page<MonitorPoint> selectPageAll(MonitorPointDto monitorPoint, Integer curr, Integer pageSize) {

        //查询第curr页，每页pageSize条
        Page<MonitorPoint> page = new Page<>(curr,pageSize);

        QueryWrapper<MonitorPoint> wrapper = new QueryWrapper<>();
        //查询可用数据
        wrapper.eq("enable", 1);

        Page<MonitorPoint> monitorPointPage = mapper.selectPage(page, wrapper);
        return monitorPointPage;
    }

    public void addMonitorPoint(MonitorPoint monitorPoint) {
        mapper.insert(monitorPoint);
    }

    public void updateMonitorPoint(MonitorPoint monitorPoint) {
        mapper.updateById(monitorPoint);
    }

    public MonitorPoint getById(Integer id) {
        return mapper.selectById(id);
    }

    public void delById(MonitorPoint monitorPoint) {
        monitorPoint.setEnable(false);
        mapper.updateById(monitorPoint);
    }

    public List<MonitorPoint> selectByIds(List<Integer> ids) {
        QueryWrapper<MonitorPoint> wrapper = new QueryWrapper<>();
        wrapper.in("id",ids);
        List<MonitorPoint> monitorPoints = mapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(monitorPoints)) {
            return null;
        }
        return monitorPoints;
    }

    public void delBatchIds(List<Integer> ids) {
        List<MonitorPoint> monitorPoints = this.selectByIds(ids);
        for (MonitorPoint monitorPoint : monitorPoints) {
            monitorPoint.setEnable(false);
            mapper.updateById(monitorPoint);
        }
    }
}
