package com.liubaing.galaxy.exception;

/**
 * 文件上传异常对象
 * @author heshuai
 * @version 10/11/14.
 */
public class FileUploadException extends AppException {

    public FileUploadException() {
    }

    public FileUploadException(String msg) {
        super(msg);
    }

    public FileUploadException(Throwable cause) {
        super(cause);
    }

    public FileUploadException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
