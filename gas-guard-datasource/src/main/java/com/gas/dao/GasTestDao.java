package com.gas.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.gas.dao.mapper.GasTestMapper;
import com.gas.entity.GasTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@DS("gas")
public class GasTestDao {

    @Autowired
    private GasTestMapper mapper;

    public GasTest getById(Integer id) {
        return mapper.selectById(id);
    }

    public List<GasTest> list() {
        return mapper.selectList(null);
    }
}
