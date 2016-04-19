package com.liubaing.galaxy.web.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.liubaing.galaxy.web.support.SpringContext;

public class ResponseVo<T> {

    @JSONField(name = "error_code")
    public int errorCode;
    @JSONField(name = "error_msg")
    public String errorMsg;
    public T data;

    public static <T> ResponseVo<T> newResponse() {
        return new ResponseVo<T>();
    }

    private ResponseVo() {
    }

    private ResponseVo(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public ResponseVo(ErrorCode code) {
        this(code.intValue(), SpringContext.getMessage(code.key));
    }

    public ResponseVo<T> setData(T data) {
        if (data == null) {
            this.buildError(ErrorCode.EMPTY_CONTENT);
        } else {
            this.data = data;
        }
        return this;
    }

    public ResponseVo buildError(ErrorCode code) {
        this.errorMsg = SpringContext.getMessage(code.key);
        this.errorCode = code.intValue();
        return this;
    }

    /**
     * 错误信息
     * 1xxx 请求参数异常
     * 2xxx 请求响应无结果
     * 3xxx 请求过程中服务异常无法正常处理
     *
     * @author heshuai
     */
    public enum ErrorCode {

        SESSION_EXPIRED(1000, "session.expired"),
        DISABLE_ACCOUNT(1100, "disable.account"),
        INCORRECT_VERIFICATION_CODE(1002, "incorrect.verify.code"),
        PASSWORD_INCORRECT(1006, "password.incorrect"),
        INVALID_EMAIL(1205, "email.not.exist"),
        EMAIL_ALREADY_EXISTS(1308, "email.already.exists"),

        EMPTY_CONTENT(2001, "empty.content"),

        SYSTEM_ERROR(3001, "system.error");

        public int value;
        public String key;

        ErrorCode(int value, String key) {
            this.value = value;
            this.key = key;
        }

        public final int intValue() {
            return value;
        }
    }

}
