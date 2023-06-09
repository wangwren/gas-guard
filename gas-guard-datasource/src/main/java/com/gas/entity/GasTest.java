package com.gas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName(value ="gas_test")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GasTest {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column gas_test.id
     *
     * @mbggenerated
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column gas_test.test
     *
     * @mbggenerated
     */
    private String test;
}