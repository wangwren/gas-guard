package com.gas.dao;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.dao.mapper.SysConfigMapper;
import com.gas.entity.DataDict;
import com.gas.entity.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SysConfigDao {

    @Autowired
    private SysConfigMapper mapper;

    public Page<SysConfig> selectPage(SysConfig sysConfig, Integer curr, Integer pageSize) {
        //查询第curr页，每页pageSize条
        Page<SysConfig> page = new Page<>(curr,pageSize);

        QueryWrapper<SysConfig> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(sysConfig.getSysName()), "sys_name", sysConfig.getSysName());
        wrapper.eq(StrUtil.isNotBlank(sysConfig.getSysKey()), "sys_key", sysConfig.getSysKey());
        wrapper.eq(StrUtil.isNotBlank(sysConfig.getSysValue()), "sys_value", sysConfig.getSysValue());

        //查询可用数据
        wrapper.eq("enable", 1);


        Page<SysConfig> sysConfigPage = mapper.selectPage(page, wrapper);
        return sysConfigPage;
    }

    public List<SysConfig> selectCond(SysConfig sysConfig) {

        QueryWrapper<SysConfig> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(sysConfig.getSysName()), "sys_name", sysConfig.getSysName());
        wrapper.eq(StrUtil.isNotBlank(sysConfig.getSysKey()), "sys_key", sysConfig.getSysKey());
        wrapper.eq(StrUtil.isNotBlank(sysConfig.getSysValue()), "sys_value", sysConfig.getSysValue());

        //查询可用数据
        wrapper.eq("enable", 1);


        List<SysConfig> sysConfigs = mapper.selectList(wrapper);
        return sysConfigs;
    }

    public void addDataDict(SysConfig sysConfig) {
        mapper.insert(sysConfig);
    }

    public void updateDataDict(SysConfig sysConfig) {
        mapper.updateById(sysConfig);
    }

    public SysConfig getById(Integer id) {
        return mapper.selectById(id);
    }

    public void delDataDict(Integer id) {
        SysConfig sysConfig = this.getById(id);
        sysConfig.setEnable(false);

        mapper.updateById(sysConfig);
    }
}
