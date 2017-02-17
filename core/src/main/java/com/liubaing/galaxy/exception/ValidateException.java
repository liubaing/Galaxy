package com.liubaing.galaxy.exception;

/**
 * 校验异常
 *
 * @author heshuai
 * @version 10/11/14.
 */
public class ValidateException extends AppException {

    public ValidateException() {
    }

    public ValidateException(String msg) {
        super(msg);
    }

    public ValidateException(Throwable cause) {
        super(cause);
    }

    public ValidateException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
