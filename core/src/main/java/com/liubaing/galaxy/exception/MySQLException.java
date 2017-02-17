package com.liubaing.galaxy.exception;

public class MySQLException extends AppException {

    public MySQLException() {
        super();
    }

    public MySQLException(String msg) {
        super(msg);
    }

    public MySQLException(Throwable cause) {
        super(cause);
    }

    public MySQLException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
