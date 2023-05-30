package com.gas.enums;

public enum ErrorCodeEnum {

    SUCCESS(0, "操作成功"),
    SYSTEM_FAIL(10, "系统异常"),
    MISSING_REQUIRED_PARAM(10001, "缺少必填参数"),
    INVALID_PARAM_FORMAT(10002, "参数格式错误"),
    INVALID_PARAM_VALUE(10003, "参数值不合法"),
    NAME_ONLY_NOT_SUPPORT(10004, "暂不支持仅姓名查询"),
    INVALID_REASON_TOO_LONG(10005, "字数过多"),
    UNABLE_IN_CURRENT_STATUS(10006, "当前状态无法注销"),
    USER_NOT_FOUND(10007, "用户不存在"),

    RESOURCE_NOT_FOUND(40001, "资源不存在"),
    RELATED_RESOURCE_NOT_FOUND(40002, "无法找到相关资源");

    private Integer code;

    private String message;

    ErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
