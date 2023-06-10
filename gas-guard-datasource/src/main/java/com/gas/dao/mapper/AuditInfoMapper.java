package com.gas.dao.mapper;

import com.gas.entity.AuditInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author weiren
* @description 针对表【audit_info(审核信息表)】的数据库操作Mapper
* @createDate 2023-06-10 19:46:07
* @Entity com.gas.entity.AuditInfo
*/
@Mapper
public interface AuditInfoMapper extends BaseMapper<AuditInfo> {

}




