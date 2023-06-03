package com.gas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName(value ="warn_type")
@Data
public class WarnType {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column warn_type.id
     *
     * @mbggenerated
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column warn_type.type
     *
     * @mbggenerated
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column warn_type.big_type
     *
     * @mbggenerated
     */
    private String bigType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column warn_type.level
     *
     * @mbggenerated
     */
    private String level;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column warn_type.status
     *
     * @mbggenerated
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column warn_type.whenab
     *
     * @mbggenerated
     */
    private String whenab;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column warn_type.notifi
     *
     * @mbggenerated
     */
    private String notifi;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column warn_type.content
     *
     * @mbggenerated
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column warn_type.enable
     *
     * @mbggenerated
     */
    private Boolean enable;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column warn_type.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column warn_type.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;
}