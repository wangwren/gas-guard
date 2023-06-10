package com.gas.dao;

import com.gas.dao.mapper.AuditInfoMapper;
import com.gas.entity.AuditInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuditInfoDao {

    @Autowired
    private AuditInfoMapper mapper;

    public void add(AuditInfo auditInfo) {
        mapper.insert(auditInfo);
    }
}
