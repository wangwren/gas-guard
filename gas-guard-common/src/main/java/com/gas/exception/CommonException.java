package com.gas.exception;

import com.gas.enums.ErrorCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommonException extends RuntimeException {

    private Integer errorCode;

    private String errorMsg;

    public CommonException(ErrorCodeEnum codeEnum) {
        super(codeEnum.getCode() + ":" + codeEnum.getMessage());
        this.errorCode = codeEnum.getCode();
        this.errorMsg = codeEnum.getMessage();
    }

    public CommonException(Integer errorCode) {
        super(String.valueOf(errorCode));
        this.errorCode = errorCode;
    }

    public CommonException(Integer errorCode, String errorMsg) {
        super(errorCode + ":" + errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public CommonException() {
        super();
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
