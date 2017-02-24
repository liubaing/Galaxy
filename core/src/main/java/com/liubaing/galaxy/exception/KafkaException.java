package com.liubaing.galaxy.exception;

public class KafkaException extends AppException {

    public KafkaException() {
        super();
    }

    public KafkaException(String msg) {
        super(msg);
    }

    public KafkaException(Throwable cause) {
        super(cause);
    }

    public KafkaException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
