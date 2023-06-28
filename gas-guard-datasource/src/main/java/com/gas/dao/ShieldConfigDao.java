package com.gas.dao;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gas.dao.mapper.ShieldConfigMapper;
import com.gas.entity.ShieldConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShieldConfigDao {

    @Autowired
    private ShieldConfigMapper mapper;

    public Page<ShieldConfig> selectPage(ShieldConfig shieldConfig, Integer curr, Integer pageSize) {
        //查询第curr页，每页pageSize条
        Page<ShieldConfig> page = new Page<>(curr,pageSize);

        QueryWrapper<ShieldConfig> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(shieldConfig.getPointName()), "point_name", shieldConfig.getPointName());
        wrapper.eq(StrUtil.isNotBlank(shieldConfig.getDeviceName()), "device_name", shieldConfig.getDeviceName());
        wrapper.eq(StrUtil.isNotBlank(shieldConfig.getDeviceNo()), "device_no", shieldConfig.getDeviceNo());
        wrapper.eq(StrUtil.isNotBlank(shieldConfig.getStatus()), "status", shieldConfig.getStatus());
        wrapper.orderByDesc("create_time");

        //查询可用数据
        wrapper.eq("enable", 1);


        Page<ShieldConfig> shieldConfigPage = mapper.selectPage(page, wrapper);
        return shieldConfigPage;
    }

    public void addShieldConfig(ShieldConfig shieldConfig) {
        mapper.insert(shieldConfig);
    }

    public ShieldConfig getById(Integer id) {
        return mapper.selectById(id);
    }

    public void updateShieldConfig(ShieldConfig shieldConfig) {
        mapper.updateById(shieldConfig);
    }
}
