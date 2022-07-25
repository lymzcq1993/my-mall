package com.hujian.mall.common.api;


import lombok.ToString;

/**
 * @author hujian
 */
@ToString
public enum ResultCode implements IErrorCode{
    /**
     * 成功码
     */
    SUCCESS("200", "操作成功"),
    FAILED("500", "操作失败"),
    VALIDATE_FAILED("404", "参数检验失败"),
    UNAUTHORIZED("401", "暂未登录或token已经过期"),
    AUTHORIZATION_HEADER_IS_EMPTY("600","请求头中的token为空"),
    GET_TOKEN_KEY_ERROR("601","远程获取TokenKey异常"),
    GEN_PUBLIC_KEY_ERROR("602","生成公钥异常"),
    JWT_TOKEN_EXPIRE("603","token校验异常"),
    TOO_MANY_REQUEST_ERROR("429","后端服务触发流控"),
    BACKGROUND_DEGRADE_ERROR("604","后端服务触发降级"),
    BAD_GATEWAY("502","网关服务异常"),
    FORBIDDEN("403", "没有相关权限");
    /**
     * 错误码
     */
    private final String code;
    /**
     * 错误码
     */
    private final String message;

    private ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
