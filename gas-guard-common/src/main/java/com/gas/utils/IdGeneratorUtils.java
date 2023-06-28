package com.gas.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public class IdGeneratorUtils {
    private static Snowflake snowflake = IdUtil.getSnowflake(1, 1);

    public static String generateId() {
        return String.valueOf(snowflake.nextId());
    }
}

