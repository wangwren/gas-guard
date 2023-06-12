package com.gas.dao;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.common.GlobalConstants;
import com.gas.dao.mapper.MonitorDeviceMapper;
import com.gas.dao.mapper.MonitorPointMapper;
import com.gas.dao.mapper.WarnDealInfoMapper;
import com.gas.dao.mapper.WarnInfoMapper;
import com.gas.dto.WarnInfoDto;
import com.gas.entity.MonitorDevice;
import com.gas.entity.MonitorPoint;
import com.gas.entity.WarnDealInfo;
import com.gas.entity.WarnInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class WarnInfoDao {

    @Autowired
    private WarnInfoMapper mapper;

    @Autowired
    private MonitorPointMapper pointMapper;

    @Autowired
    private MonitorDeviceMapper deviceMapper;

    @Autowired
    private WarnDealInfoMapper warnDealInfoMapper;

    public Page<WarnInfoDto> selectPage(WarnInfoDto warnInfoDto, Integer curr, Integer pageSize, String dataType) {
        //查询第curr页，每页pageSize条
        Page<WarnInfo> page = new Page<>(curr,pageSize);

        QueryWrapper<WarnInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("data_type", dataType);
        wrapper.in(!CollectionUtils.isEmpty(warnInfoDto.getPointIds()), "point_id", warnInfoDto.getPointIds());
        wrapper.in(!CollectionUtils.isEmpty(warnInfoDto.getDeviceIds()), "device_id", warnInfoDto.getDeviceIds());
        wrapper.eq(StrUtil.isNotBlank(warnInfoDto.getType()), "type", warnInfoDto.getType());
        wrapper.eq(StrUtil.isNotBlank(warnInfoDto.getLevel()), "level", warnInfoDto.getLevel());
        wrapper.eq(StrUtil.isNotBlank(warnInfoDto.getWarnStatus()), "warn_status", warnInfoDto.getWarnStatus());
        wrapper.ge(warnInfoDto.getCreateTime() != null, "create_time", warnInfoDto.getCreateTime());
        wrapper.le(warnInfoDto.getEndTime() != null, "create_time", warnInfoDto.getEndTime());
        wrapper.ge(warnInfoDto.getWarnBeginTime() != null, "warn_time", warnInfoDto.getWarnBeginTime());
        wrapper.le(warnInfoDto.getWarnEndTime() != null, "warn_time", warnInfoDto.getWarnEndTime());

        wrapper.eq("enable", true);
        //不包含已处置数据
        wrapper.ne("warn_status", GlobalConstants.WAIN_INFO_DEAL_OK);

        Page<WarnInfo> warnInfoPage = mapper.selectPage(page, wrapper);
        Page<WarnInfoDto> dtoPage = new Page<>();
        if (CollectionUtils.isEmpty(warnInfoPage.getRecords())) {
            dtoPage.setTotal(warnInfoPage.getTotal());
            dtoPage.setSize(warnInfoPage.getSize());
            dtoPage.setCurrent(warnInfoPage.getCurrent());
            dtoPage.setPages(warnInfoPage.getPages());
        }

        List<WarnInfoDto> list = new ArrayList<>();
        for (WarnInfo record : warnInfoPage.getRecords()) {
            WarnInfoDto dto = new WarnInfoDto();
            BeanUtils.copyProperties(record, dto);

            MonitorPoint monitorPoint = pointMapper.selectById(record.getPointId());
            dto.setMonitorPoint(monitorPoint);

            MonitorDevice monitorDevice = deviceMapper.selectById(record.getDeviceId());
            dto.setMonitorDevice(monitorDevice);

            list.add(dto);
        }

        dtoPage.setRecords(list);
        dtoPage.setTotal(list.size());
        dtoPage.setSize(list.size());
        dtoPage.setCurrent(warnInfoPage.getCurrent());

        return dtoPage;
    }

    public WarnInfoDto selectDtoById(Integer id) {

        WarnInfoDto warnInfoDto = new WarnInfoDto();
        WarnInfo warnInfo = mapper.selectById(id);
        BeanUtils.copyProperties(warnInfo, warnInfoDto);

        MonitorPoint monitorPoint = pointMapper.selectById(warnInfo.getPointId());
        MonitorDevice monitorDevice = deviceMapper.selectById(warnInfo.getDeviceId());

        warnInfoDto.setMonitorPoint(monitorPoint);
        warnInfoDto.setMonitorDevice(monitorDevice);
        return warnInfoDto;
    }

    public WarnInfo selectById(Integer id) {
        return mapper.selectById(id);
    }

    public void updateWarnInfo(WarnInfo warnInfo) {
        mapper.updateById(warnInfo);
    }

    public void addWarnDealInfo(WarnDealInfo warnDealInfo) {
        warnDealInfoMapper.insert(warnDealInfo);
    }

    public List<WarnInfo> selectByDeviceId(Integer id) {

        QueryWrapper<WarnInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("device_id", id);

        List<WarnInfo> warnInfos = mapper.selectList(wrapper);
        return warnInfos;
    }
}
