package com.liubaing.galaxy.exception;

public class RedisException extends AppException {

    public RedisException() {
        super();
    }

    public RedisException(String msg) {
        super(msg);
    }

    public RedisException(Throwable cause) {
        super(cause);
    }

    public RedisException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
