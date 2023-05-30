package com.gas.service;

import com.gas.entity.GasTest;
import com.gas.model.GasTestRequest;

import java.util.List;

public interface GasTestService {

    GasTest getGasTestById(GasTestRequest request);

    List<GasTest> list();
}
