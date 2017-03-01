package com.liubaing.galaxy.http;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * HTTP响应
 *
 * @author heshuai
 * @version 2017/3/1.
 */
public final class Response {

    public static final String LOCATION = "Location";

    public static final int SC_OK = 200;
    public static final int SC_MOVED_TEMPORARILY = 302;

    private int code;
    private byte[] body;
    private Map<String, String> header;

    public Response(int code) {
        this();
        this.code = code;
    }

    public Response() {
        this.header = Maps.newHashMap();
    }

    public Response setCode(int code) {
        this.code = code;
        return this;
    }

    public Response setBody(byte[] body) {
        this.body = body;
        return this;
    }

    public Response addHeader(String name, String value) {
        this.header.put(name, value);
        return this;
    }

    public int getCode() {
        return code;
    }

    public byte[] getBody() {
        return body;
    }

    public String getHeader(String name) {
        return header.get(name);
    }
}
