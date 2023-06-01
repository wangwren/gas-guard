package com.gas.common;

import com.gas.enums.ErrorCodeEnum;
import com.gas.exception.CommonException;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 通用响应
 */
@Data
public class ResponseInfo<T> {

    private boolean success;

    private Integer status;

    private String message;

    private T data;

    public ResponseInfo() {

    }

    public ResponseInfo(boolean success,Integer status, String message) {
        this.success = success;
        this.status = status;
        this.message = message;
    }

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

    public static ResponseInfo error(HttpStatus codeEnum) {
        return content(codeEnum.value(), codeEnum.getReasonPhrase(), null);
    }

    public static ResponseInfo error(CommonException ex) {
        return content(false,ex.getErrorCode(), ex.getErrorMsg(),null);
    }

    private static ResponseInfo content(Boolean success, Integer code, String message, Object data) {
        return new ResponseInfo(success,code, message, data);
    }

    private static ResponseInfo content(Integer status, String msg, Object data) {
        ResponseInfo info = new ResponseInfo();
        info.setStatus(status);
        info.setMessage(msg);
        info.setData(data);
        return info;
    }

    public static <T> ResponseInfo success(T data) {
        return new ResponseInfo(true,ErrorCodeEnum.SUCCESS.getCode(),ErrorCodeEnum.SUCCESS.getMessage(),data);
    }

    public static <T> ResponseInfo success() {
        return new ResponseInfo(true,ErrorCodeEnum.SUCCESS.getCode(),ErrorCodeEnum.SUCCESS.getMessage());
    }
}
