package com.gas.converter;

import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.gas.utils.LogUtils;

/**
 * 优先拿pinpoint的traceId，如果没有，则使用渠道网关本身的traceId，避免与pinpoint插件冲突
 *
 */
public class ChannelGatewayLogTraceIdConverter extends MessageConverter {

    /**
     * 获取traceId，优先拿pinpoint的traceId，如果没有，则使用渠道网关本身的traceId
     * @param event
     * @return
     */
    @Override
    public String convert(ILoggingEvent event) {
        return LogUtils.getTraceId();
    }
}
