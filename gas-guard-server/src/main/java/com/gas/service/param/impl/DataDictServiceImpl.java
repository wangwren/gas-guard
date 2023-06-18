package com.gas.service.param.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.dao.DataDictDao;
import com.gas.entity.DataDict;
import com.gas.exception.CommonException;
import com.gas.model.DataDictRequest;
import com.gas.service.param.DataDictService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;


@Service
public class DataDictServiceImpl implements DataDictService {

    @Autowired
    private DataDictDao dataDictDao;

    @Override
    public Page<DataDict> getDataDict(DataDictRequest request) {

        DataDict dataDict = new DataDict();
        BeanUtils.copyProperties(request, dataDict);

        Page<DataDict> dataDictPage = dataDictDao.selectPage(dataDict, request.getCurr(), request.getPageSize());

        return dataDictPage;
    }

    @Override
    public List<DataDict> getByCond(DataDictRequest request) {
        DataDict dataDict = new DataDict();
        BeanUtils.copyProperties(request, dataDict);

        List<DataDict> list = dataDictDao.selectCond(dataDict);
        return list;
    }

    @Override
    @Transactional
    public void addOrUpdate(DataDictRequest request) {

        DataDict dataDict = new DataDict();
        BeanUtils.copyProperties(request, dataDict);

        if (Objects.isNull(dataDict.getId())) {
            List<DataDict> dataDicts = dataDictDao.selectByKey(dataDict.getDictKey());
            if (!CollectionUtils.isEmpty(dataDicts)) {
                throw new CommonException(500,"字典key已存在");
            }
            //新增
            dataDictDao.addDataDict(dataDict);
        } else {
            //修改
            dataDictDao.updateDataDict(dataDict);
        }
    }

    @Override
    public DataDict getById(Integer id) {
        return dataDictDao.getById(id);
    }


    @Override
    @Transactional
    public void delById(Integer id) {
        dataDictDao.delDataDict(id);
    }
}
