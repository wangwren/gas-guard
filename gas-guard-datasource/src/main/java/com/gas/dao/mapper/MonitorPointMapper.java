package com.gas.dao.mapper;

import com.gas.entity.MonitorPoint;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author weiren
* @description 针对表【monitor_point(监测点位建档)】的数据库操作Mapper
* @createDate 2023-06-05 22:45:11
* @Entity com.gas.entity.MonitorPoint
*/
@Mapper
public interface MonitorPointMapper extends BaseMapper<MonitorPoint> {

}




