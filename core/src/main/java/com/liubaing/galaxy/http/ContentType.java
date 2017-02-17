package com.liubaing.galaxy.http;

/**
 * HTTP请求响应的数据格式
 *
 * @author heshuai
 * @version 2016/11/11.
 */
public enum ContentType {

    FORM("application/x-www-form-urlencoded"),
    JSON("application/json"),
    STREAM("application/octet-stream");

    ContentType(String mimeType) {
        this.mimeType = mimeType;
    }

    String mimeType;

    public String getMimeType() {
        return mimeType;
    }

}