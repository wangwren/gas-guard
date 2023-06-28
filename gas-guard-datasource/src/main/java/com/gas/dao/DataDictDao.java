package com.gas.dao;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.dao.mapper.DataDictMapper;
import com.gas.entity.DataDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataDictDao {

    @Autowired
    private DataDictMapper mapper;

    public Page<DataDict> selectPage(DataDict dataDict, Integer curr, Integer pageSize) {
        //查询第curr页，每页pageSize条
        Page<DataDict> page = new Page<>(curr,pageSize);

        QueryWrapper<DataDict> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(dataDict.getType()), "type", dataDict.getType());
        wrapper.eq(StrUtil.isNotBlank(dataDict.getTypeName()), "type_name", dataDict.getTypeName());
        wrapper.eq(StrUtil.isNotBlank(dataDict.getDictKey()), "dict_key", dataDict.getDictKey());
        wrapper.eq(StrUtil.isNotBlank(dataDict.getDictValue()), "dict_value", dataDict.getDictValue());
        wrapper.orderByDesc("create_time");

        //查询可用数据
        wrapper.eq("enable", 1);


        Page<DataDict> dataDictPage = mapper.selectPage(page, wrapper);
        return dataDictPage;

    }

    public List<DataDict> selectCond(DataDict dataDict) {
        QueryWrapper<DataDict> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(dataDict.getType()), "type", dataDict.getType());
        wrapper.eq(StrUtil.isNotBlank(dataDict.getTypeName()), "type_name", dataDict.getTypeName());
        wrapper.eq(StrUtil.isNotBlank(dataDict.getDictKey()), "dict_key", dataDict.getDictKey());
        wrapper.eq(StrUtil.isNotBlank(dataDict.getDictValue()), "dict_value", dataDict.getDictValue());

        //查询可用数据
        wrapper.eq("enable", 1);
        List<DataDict> list = mapper.selectList(wrapper);
        return list;
    }

    public void addDataDict(DataDict dataDict) {
        mapper.insert(dataDict);
    }

    public void updateDataDict(DataDict dataDict) {
        mapper.updateById(dataDict);
    }

    public DataDict getById(Integer id) {
        return mapper.selectById(id);
    }

    public void delDataDict(Integer id) {
        DataDict dataDict = this.getById(id);
        dataDict.setEnable(false);

        mapper.updateById(dataDict);
    }

    public List<DataDict> selectByTypeName(String typeName) {
        QueryWrapper<DataDict> wrapper = new QueryWrapper<>();
        wrapper.eq("type_name", typeName);
        wrapper.eq("enable", 1);
        List<DataDict> list = mapper.selectList(wrapper);
        return list;
    }

    public List<DataDict> selectByKey(String dictKey) {
        QueryWrapper<DataDict> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_key", dictKey);
        wrapper.eq("enable", 1);
        List<DataDict> dataDicts = mapper.selectList(wrapper);
        return dataDicts;
    }
}
