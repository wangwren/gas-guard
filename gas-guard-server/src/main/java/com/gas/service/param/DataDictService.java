package com.gas.service.param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.entity.DataDict;
import com.gas.model.DataDictRequest;

import java.util.List;


public interface DataDictService {

    Page<DataDict> getDataDict(DataDictRequest request);

    List<DataDict> getByCond(DataDictRequest request);

    void addOrUpdate(DataDictRequest request);

    DataDict getById(Integer id);

    void delById(Integer id);
}
