package com.liubaing.galaxy.exception;

/**
 * @author heshuai
 * @version 2012-11-1
 */
public class CachedException extends AppException {

    private static final long serialVersionUID = 1996646258389107673L;
    private int error_code = 2;

    public CachedException() {
        super();
    }

    public CachedException(String msg) {
        super(msg);
    }

    public CachedException(Throwable cause) {
        super(cause);
    }

    public CachedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
