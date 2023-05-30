package com.gas.common;

import com.gas.enums.ErrorCodeEnum;
import com.gas.exception.CommonException;
import lombok.Data;

/**
 * 通用响应
 */
@Data
public class ResponseInfo<T> {

    private boolean success;

    private Integer status;

    private String message;

    private T data;

    public ResponseInfo(boolean success,Integer status, String message, T data) {
        this.success = success;
        this.status = status;
        this.message = message;
        this.data = data;
    }


    public static ResponseInfo error() {
        return error(ErrorCodeEnum.SYSTEM_FAIL);
    }

    public static ResponseInfo error(ErrorCodeEnum codeEnum) {
        return content(false,codeEnum.getCode(), codeEnum.getMessage(), null);
    }

    public static ResponseInfo error(CommonException ex) {
        return content(false,ex.getErrorCode(), ex.getErrorMsg(),null);
    }

    public static ResponseInfo content(Boolean success, Integer code, String message, Object data) {
        return new ResponseInfo(success,code, message, data);
    }

    public static <T> ResponseInfo success(T data) {
        return new ResponseInfo(true,ErrorCodeEnum.SUCCESS.getCode(),ErrorCodeEnum.SUCCESS.getMessage(),data);
    }
}
