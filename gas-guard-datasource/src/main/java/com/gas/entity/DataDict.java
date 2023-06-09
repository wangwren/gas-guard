package com.gas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName(value ="data_dict")
@Data
public class DataDict {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column data_dict.id
     *
     * @mbggenerated
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column data_dict.type
     *
     * @mbggenerated
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column data_dict.type_name
     *
     * @mbggenerated
     */
    private String typeName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column data_dict.dict_key
     *
     * @mbggenerated
     */
    private String dictKey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column data_dict.dict_value
     *
     * @mbggenerated
     */
    private String dictValue;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column data_dict.remark
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column data_dict.enable
     *
     * @mbggenerated
     */
    private Boolean enable;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column data_dict.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column data_dict.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;
}