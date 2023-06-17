package com.gas.dao;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.common.GlobalConstants;
import com.gas.dao.mapper.MonitorPointMapper;
import com.gas.dto.MonitorPointDto;
import com.gas.entity.MonitorDevice;
import com.gas.entity.MonitorPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class MonitorPointDao {

    @Autowired
    private MonitorPointMapper pointMapper;
    @Autowired
    private MonitorDeviceDao deviceDao;

    /**
     * 监测点位建档，不包含已通过点位
     */
    public Page<MonitorPoint> selectPage(MonitorPointDto monitorPoint, Integer curr, Integer pageSize) {
        //查询第curr页，每页pageSize条
        Page<MonitorPoint> page = new Page<>(curr,pageSize);

        QueryWrapper<MonitorPoint> wrapper = getPointQueryWrapper(monitorPoint);
        //不包含已通过数据
        wrapper.ne("archive_status", GlobalConstants.ARCHIVE_PASS_STATUS);

        Page<MonitorPoint> monitorPointPage = pointMapper.selectPage(page, wrapper);
        return monitorPointPage;
    }

    /**
     * 正式档案管理，只查询已通过点位
     */
    public Page<MonitorPoint> selectPageFormalPoint(MonitorPointDto monitorPoint, Integer curr, Integer pageSize) {
        //查询第curr页，每页pageSize条
        Page<MonitorPoint> page = new Page<>(curr,pageSize);

        QueryWrapper<MonitorPoint> wrapper = getPointQueryWrapper(monitorPoint);
        //不包含已通过数据
        wrapper.eq("archive_status", GlobalConstants.ARCHIVE_PASS_STATUS);

        Page<MonitorPoint> monitorPointPage = pointMapper.selectPage(page, wrapper);
        return monitorPointPage;
    }

    /**
     * 监测点位管理，包含所有档案状态
     * 增加无设备，多设备限制
     */
    public Page<MonitorPoint> selectPagePointManage(MonitorPointDto monitorPoint, Integer curr, Integer pageSize) {

        //查询第curr页，每页pageSize条
        Page<MonitorPoint> page = new Page<>(curr,pageSize);

        QueryWrapper<MonitorPoint> wrapper = getPointQueryWrapper(monitorPoint);
        Page<MonitorPoint> monitorPointPage = pointMapper.selectPage(page, wrapper);

        if (CollectionUtils.isEmpty(monitorPointPage.getRecords())) {
            return monitorPointPage;
        }


        //查询无设备
        if (monitorPoint.getNoDevice()) {
            List<MonitorPoint> noDeviceList = new ArrayList<>();
            for (MonitorPoint record : monitorPointPage.getRecords()) {
                List<MonitorDevice> monitorDeviceList = deviceDao.getByPointId(record.getId());
                if (CollectionUtils.isEmpty(monitorDeviceList)) {
                    noDeviceList.add(record);
                }
            }
            monitorPointPage.setRecords(noDeviceList);
            monitorPointPage.setTotal(noDeviceList.size());
        }

        //查询多设备
        if (monitorPoint.getManyDevice()) {
            List<MonitorPoint> manyDeviceList = new ArrayList<>();
            for (MonitorPoint record : monitorPointPage.getRecords()) {
                List<MonitorDevice> monitorDeviceList = deviceDao.getByPointId(record.getId());
                if (CollectionUtils.isEmpty(monitorDeviceList)) {
                    continue;
                }

                if (monitorDeviceList.size() > 1) {
                    manyDeviceList.add(record);
                }
            }
            monitorPointPage.setRecords(manyDeviceList);
            monitorPointPage.setTotal(manyDeviceList.size());
        }

        return monitorPointPage;
    }

    private static QueryWrapper<MonitorPoint> getPointQueryWrapper(MonitorPointDto monitorPoint) {
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
        return wrapper;
    }

    public Page<MonitorPoint> selectPageAll(MonitorPointDto monitorPoint, Integer curr, Integer pageSize) {

        //查询第curr页，每页pageSize条
        Page<MonitorPoint> page = new Page<>(curr,pageSize);

        QueryWrapper<MonitorPoint> wrapper = new QueryWrapper<>();
        //查询可用数据
        wrapper.eq("enable", 1);

        Page<MonitorPoint> monitorPointPage = pointMapper.selectPage(page, wrapper);
        return monitorPointPage;
    }

    public List<MonitorPoint> selectByPointName(String pointName) {

        QueryWrapper<MonitorPoint> wrapper = new QueryWrapper<>();
        wrapper.like("point_name", pointName);
        List<MonitorPoint> monitorPoints = pointMapper.selectList(wrapper);
        return monitorPoints;
    }

    public void addMonitorPoint(MonitorPoint monitorPoint) {
        pointMapper.insert(monitorPoint);
    }

    public void updateMonitorPoint(MonitorPoint monitorPoint) {
        pointMapper.updateById(monitorPoint);
    }

    public MonitorPoint getById(Integer id) {
        return pointMapper.selectById(id);
    }

    public void delById(MonitorPoint monitorPoint) {
        monitorPoint.setEnable(false);
        pointMapper.updateById(monitorPoint);
    }

    public List<MonitorPoint> selectByIds(List<Integer> ids) {
        List<MonitorPoint> monitorPoints = pointMapper.selectBatchIds(ids);
        if (CollectionUtils.isEmpty(monitorPoints)) {
            return null;
        }
        return monitorPoints;
    }

    public void delBatchIds(List<MonitorPoint> monitorPoints) {
        for (MonitorPoint monitorPoint : monitorPoints) {
            monitorPoint.setEnable(false);
            pointMapper.updateById(monitorPoint);
        }
    }

    public List<MonitorPoint> selectByPointType(String pointType) {
        QueryWrapper<MonitorPoint> wrapper = new QueryWrapper<>();
        wrapper.eq("point_type", pointType);

        List<MonitorPoint> pointList = pointMapper.selectList(wrapper);
        return pointList;
    }

    public List<MonitorPoint> selectByNePointType(String pointType) {
        QueryWrapper<MonitorPoint> wrapper = new QueryWrapper<>();
        wrapper.ne("point_type", pointType);

        List<MonitorPoint> pointList = pointMapper.selectList(wrapper);
        return pointList;
    }

    public List<MonitorPoint> selectByGasType(String gasType) {
        QueryWrapper<MonitorPoint> wrapper = new QueryWrapper<>();
        wrapper.eq("gas_type", gasType);

        List<MonitorPoint> pointList = pointMapper.selectList(wrapper);
        return pointList;
    }
}
