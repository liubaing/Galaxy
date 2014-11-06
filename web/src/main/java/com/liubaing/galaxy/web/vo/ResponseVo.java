package com.liubaing.galaxy.web.vo;

import com.alibaba.fastjson.annotation.JSONField;

public class ResponseVo<T> {

    @JSONField(name = "error_code")
    public int errorCode;
    @JSONField(name = "error_msg")
    public String errorMsg;
    public T data;

    public ResponseVo(ErrorCode code) {
        this.errorMsg = code.info;
        this.errorCode = code.intValue();
    }

    public ResponseVo<T> setData(T data) {
        this.data = data;
        return this;
    }

    public ResponseVo() {
        this.errorMsg = "操作成功";
    }

    public ResponseVo(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public enum ErrorCode {

        SESSION_EXPIRED(1000, "账户信息过期，请重新登录"),
        INVALID_PARAM(1001, "无效参数"),

        EMPTY_CONTENT(2001, "无内容"),

        SYSTEM_ERROR(3001, "系统异常");

        public int value;
        public String info;

        ErrorCode(int value, String info) {
            this.value = value;
            this.info = info;
        }

        public final int intValue() {
            return value;
        }
    }

}
