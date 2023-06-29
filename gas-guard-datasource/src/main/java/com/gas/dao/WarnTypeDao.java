package com.gas.dao;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.dao.mapper.WarnTypeMapper;
import com.gas.dto.WarnInfoDto;
import com.gas.dto.WarnTypeDto;
import com.gas.entity.DataDict;
import com.gas.entity.WarnInfo;
import com.gas.entity.WarnType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class WarnTypeDao {

    @Autowired
    private WarnTypeMapper mapper;

    public Page<WarnTypeDto> selectPage(WarnType warnType, Integer curr, Integer pageSize) {
        //查询第curr页，每页pageSize条
        Page<WarnType> page = new Page<>(curr,pageSize);

        QueryWrapper<WarnType> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(warnType.getType()), "type", warnType.getType());
        wrapper.eq(StrUtil.isNotBlank(warnType.getBigType()), "big_type", warnType.getBigType());
        wrapper.eq(StrUtil.isNotBlank(warnType.getLevel()), "level", warnType.getLevel());
        wrapper.eq(StrUtil.isNotBlank(warnType.getStatus()), "status", warnType.getStatus());
        wrapper.orderByDesc("create_time");

        //查询可用数据
        wrapper.eq("enable", 1);


        Page<WarnType> warnTypePage = mapper.selectPage(page, wrapper);
        List<WarnTypeDto> warnTypeDtos = new ArrayList<>();
        if (CollectionUtils.isEmpty(warnTypePage.getRecords())) {
            return null;
        }
        List<WarnType> records = warnTypePage.getRecords();
        for (WarnType record : records) {
            WarnTypeDto warnTypeDto = new WarnTypeDto();
            BeanUtils.copyProperties(record, warnTypeDto);
            String notifi = record.getNotifi().substring(1, record.getNotifi().length() - 1); // 去除开始和结束的 [ ]
            warnTypeDto.setNotifi(notifi.split(","));

            warnTypeDtos.add(warnTypeDto);
        }

        Page<WarnTypeDto> warnTypeDtoPage = new Page<>();
        warnTypeDtoPage.setRecords(warnTypeDtos);
        warnTypeDtoPage.setSize(warnTypePage.getSize());
        warnTypeDtoPage.setTotal(warnTypePage.getTotal());
        warnTypeDtoPage.setPages(warnTypePage.getPages());
        warnTypeDtoPage.setCurrent(warnTypePage.getCurrent());
        return warnTypeDtoPage;
    }

    public void addWarnType(WarnType warnType) {
        mapper.insert(warnType);
    }

    public void updateWarnType(WarnType warnType) {
        mapper.updateById(warnType);
    }

    public WarnTypeDto getById(Integer id) {
        WarnType warnType = mapper.selectById(id);
        WarnTypeDto warnTypeDto = new WarnTypeDto();
        BeanUtils.copyProperties(warnType, warnTypeDto);
        String notifi = warnType.getNotifi().substring(1, warnType.getNotifi().length() - 1); // 去除开始和结束的 [ ]
        warnTypeDto.setNotifi(notifi.split(","));
        return warnTypeDto;
    }

    public void delWarnType(Integer id) {
        WarnTypeDto warnTypeDto = this.getById(id);
        warnTypeDto.setEnable(false);
        WarnType warnType = new WarnType();
        BeanUtils.copyProperties(warnTypeDto, warnType);
        mapper.updateById(warnType);
    }
}
