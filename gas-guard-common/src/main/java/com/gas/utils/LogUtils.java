package com.gas.utils;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;


@Slf4j
public class LogUtils {
    /**
     * 网关日志跟踪标识
     */
    private static final String CHANNEL_TRACE_ID = "CHANNEL_TRACE_ID";
    /**
     * pinpoint日志跟踪标识
     */
    private static final String PIN_POINT_TRACE_ID = "TRACE_ID";

    private static final ThreadLocal<Long> beginTimeStamp = new ThreadLocal<>();

    /**
     * 计时开始
     */
    public static void beginTrace() {
        beginTimeStamp.set(System.currentTimeMillis());
    }

    /**
     * 停止计时
     * @return 计时（单位：秒）
     */
    public static double endTrace() {
        Long beginTime = beginTimeStamp.get();
        if(beginTime != null) {
            long endTime = System.currentTimeMillis();
            //毫秒转秒
            return BigDecimal.valueOf(endTime).subtract(BigDecimal.valueOf(beginTime))
                    .divide(BigDecimal.valueOf(1000)).doubleValue();
        } else {
            return 0D;
        }
    }

    /**
     * 设置日志跟踪标识
     */
    public static void setTraceId() {
        String traceId = UUID.randomUUID().toString();
        if (StrUtil.isBlank(MDC.get(CHANNEL_TRACE_ID))) {
            MDC.put(CHANNEL_TRACE_ID, traceId);
        }
    }

    /**
     * 移除日志跟踪标识
     */
    public static void removeTraceId() {
        MDC.remove(CHANNEL_TRACE_ID);
    }

    /**
     * 获取日志跟踪标识
     * @return
     */
    public static String getTraceId() {
        String traceId = "";
        try{
            if (StrUtil.isNotBlank(MDC.get(LogUtils.PIN_POINT_TRACE_ID)) && !Objects.equals("TRACE_IS_NULL", MDC.get(LogUtils.PIN_POINT_TRACE_ID))) {
                traceId = MDC.get(LogUtils.PIN_POINT_TRACE_ID);
            } else if (StrUtil.isNotBlank(MDC.get(LogUtils.CHANNEL_TRACE_ID))) {
                traceId = MDC.get(LogUtils.CHANNEL_TRACE_ID);
            }
        } catch (Exception e) {
            log.debug("获取traceId失败", e);
        }
        return traceId;
    }

    private LogUtils() {
    }
}
