package com.gas.service.param.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.dao.SysConfigDao;
import com.gas.entity.DataDict;
import com.gas.entity.SysConfig;
import com.gas.model.SysConfigRequest;
import com.gas.service.param.SysConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class SysConfigServiceImpl implements SysConfigService {

    @Autowired
    private SysConfigDao sysConfigDao;

    @Override
    public Page<SysConfig> getSysConfig(SysConfigRequest request) {
        SysConfig sysConfig = new SysConfig();
        BeanUtils.copyProperties(request, sysConfig);

        Page<SysConfig> sysConfigPage = sysConfigDao.selectPage(sysConfig, request.getCurr(), request.getPageSize());

        return sysConfigPage;
    }

    @Override
    public List<SysConfig> getByCond(SysConfigRequest request) {
        SysConfig sysConfig = new SysConfig();
        BeanUtils.copyProperties(request, sysConfig);

        List<SysConfig> list = sysConfigDao.selectCond(sysConfig);
        return list;
    }

    @Override
    @Transactional
    public void addOrUpdate(SysConfigRequest request) {
        SysConfig sysConfig = new SysConfig();
        BeanUtils.copyProperties(request, sysConfig);

        if (Objects.isNull(sysConfig.getId())) {
            //新增
            sysConfigDao.addDataDict(sysConfig);
        } else {
            //修改
            sysConfigDao.updateDataDict(sysConfig);
        }
    }

    @Override
    public SysConfig getById(Integer id) {
        return sysConfigDao.getById(id);
    }

    @Override
    @Transactional
    public void delById(Integer id) {
        sysConfigDao.delDataDict(id);
    }
}
