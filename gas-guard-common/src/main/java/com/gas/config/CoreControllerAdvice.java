package com.gas.config;

import com.gas.common.ResponseInfo;
import com.gas.enums.ErrorCodeEnum;
import com.gas.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * controller 增强器
 *
 * 1. 异常统一处理
 */
@RestControllerAdvice
@Slf4j
public class CoreControllerAdvice {

    /**
     * 全局异常捕捉处理
     *
     * @param ex 系统异常
     * @return 统一返回内容数据
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseInfo errorHandler(Exception ex) {
        log.error("exception, url={},  ex", this.doURILog(), ex);
        return ResponseInfo.error();
    }

    /**
     * 权限校验异常处理
     *
     * @param ex 系统异常
     * @return 统一返回内容数据
     */
    @ResponseBody
    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseInfo errorHandler(UnauthorizedException ex) {
        log.error("exception, url={},  ex", this.doURILog(), ex);
        return ResponseInfo.error(HttpStatus.FORBIDDEN);
    }


    /**
     * 接口校验异常捕捉处理
     *
     * @param ex 接口校验一次
     * @return 统一返回内容数据
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseInfo apiHandler(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException, url={},  ex", this.doURILog(), ex);
        StringBuilder errorInfo = new StringBuilder();
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        for (ObjectError error : errors) {
            errorInfo.append(error.getDefaultMessage()).append(",");
        }
        log.error("接口参数验证失败：{}", errorInfo.toString());
        return ResponseInfo.error(ErrorCodeEnum.SYSTEM_FAIL);
    }

    /**s
     * 拦截捕捉自定义异常 PumaException.class
     * @param ex 自定义异常
     * @return 统一返回内容数据
     */
    @ResponseBody
    @ExceptionHandler(value = CommonException.class)
    public ResponseInfo coreErrorHandler(CommonException ex) {
        log.warn("exception, url={},  ex", this.doURILog(), ex);
        return ResponseInfo.error(ex);
    }

    private String doURILog() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取请求的request
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            return request.getRequestURL().toString();
        }
        return null;
    }
}
