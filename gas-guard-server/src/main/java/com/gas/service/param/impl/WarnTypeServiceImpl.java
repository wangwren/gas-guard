package com.gas.service.param.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.dao.WarnTypeDao;
import com.gas.dto.WarnTypeDto;
import com.gas.entity.WarnType;
import com.gas.model.WarnTypeRequest;
import com.gas.service.param.WarnTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Objects;

@Service
public class WarnTypeServiceImpl implements WarnTypeService {

    @Autowired
    private WarnTypeDao warnTypeDao;

    @Override
    public Page<WarnTypeDto> getWarnType(WarnTypeRequest request) {
        WarnType warnType = new WarnType();
        BeanUtils.copyProperties(request, warnType);

        Page<WarnTypeDto> warnTypePage = warnTypeDao.selectPage(warnType, request.getCurr(), request.getPageSize());

        return warnTypePage;
    }

    @Override
    @Transactional
    public void addOrUpdate(WarnTypeRequest request) {
        WarnType warnType = new WarnType();
        BeanUtils.copyProperties(request, warnType);
        warnType.setNotifi(Arrays.toString(request.getNotifi()));

        if (Objects.isNull(warnType.getId())) {
            //新增
            warnTypeDao.addWarnType(warnType);
        } else {
            //修改
            warnTypeDao.updateWarnType(warnType);
        }
    }

    @Override
    public WarnTypeDto getById(Integer id) {
        return warnTypeDao.getById(id);
    }

    @Override
    @Transactional
    public void delById(Integer id) {
        warnTypeDao.delWarnType(id);
    }
}
