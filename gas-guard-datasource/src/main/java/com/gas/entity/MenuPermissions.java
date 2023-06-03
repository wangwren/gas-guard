package com.gas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName(value ="menu_permissions")
@Data
public class MenuPermissions {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column menu_permissions.id
     *
     * @mbggenerated
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column menu_permissions.parent_id
     *
     * @mbggenerated
     */
    private Integer parentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column menu_permissions.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column menu_permissions.permission
     *
     * @mbggenerated
     */
    private String permission;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column menu_permissions.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column menu_permissions.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;
}