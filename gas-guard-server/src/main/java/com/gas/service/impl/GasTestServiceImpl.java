package com.gas.service.impl;

import com.gas.dao.GasTestDao;
import com.gas.entity.GasTest;
import com.gas.model.GasTestRequest;
import com.gas.service.GasTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GasTestServiceImpl implements GasTestService {

    @Autowired
    private GasTestDao gasTestDao;

    @Override
    public GasTest getGasTestById(GasTestRequest request) {
        return gasTestDao.getById(request.getId());
    }

    public List<GasTest> list() {
        return gasTestDao.list();
    }
}
