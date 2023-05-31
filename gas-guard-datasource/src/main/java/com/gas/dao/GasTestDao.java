package com.gas.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.dao.mapper.GasTestMapper;
import com.gas.entity.GasTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GasTestDao {

    @Autowired
    private GasTestMapper mapper;

    public GasTest getById(Integer id) {
        return mapper.selectById(id);
    }

    public List<GasTest> list() {
        return mapper.selectList(null);
    }

    public Page<GasTest> page() {
        //查询第一页，每页两条
        Page<GasTest> page = new Page<>(1,2);
        Page<GasTest> gasTestPage = mapper.selectPage(page, null);
        return gasTestPage;
    }
}
