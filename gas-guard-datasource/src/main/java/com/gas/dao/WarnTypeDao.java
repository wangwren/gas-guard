package com.gas.dao;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.dao.mapper.WarnTypeMapper;
import com.gas.entity.DataDict;
import com.gas.entity.WarnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WarnTypeDao {

    @Autowired
    private WarnTypeMapper mapper;

    public Page<WarnType> selectPage(WarnType warnType, Integer curr, Integer pageSize) {
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
        return warnTypePage;
    }

    public void addWarnType(WarnType warnType) {
        mapper.insert(warnType);
    }

    public void updateWarnType(WarnType warnType) {
        mapper.updateById(warnType);
    }

    public WarnType getById(Integer id) {
        return mapper.selectById(id);
    }

    public void delWarnType(Integer id) {
        WarnType warnType = this.getById(id);
        warnType.setEnable(false);
        mapper.updateById(warnType);
    }
}
