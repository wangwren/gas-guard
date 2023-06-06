package com.gas.dao.mapper;

import com.gas.entity.MonitorDevice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author weiren
* @description 针对表【monitor_device(监测设备建档)】的数据库操作Mapper
* @createDate 2023-06-06 22:39:32
* @Entity com.gas.entity.MonitorDevice
*/
@Mapper
public interface MonitorDeviceMapper extends BaseMapper<MonitorDevice> {

}




