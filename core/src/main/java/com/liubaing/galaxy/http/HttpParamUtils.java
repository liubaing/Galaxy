package com.liubaing.galaxy.http;

import java.util.Random;

public class HttpParamUtils {

    /**
     * 获取连接的最大等待时间(单位毫秒)
     */
    public final static int WAIT_TIMEOUT = 12000;
    /**
     * 连接超时时间
     */
    public final static int CONNECT_TIMEOUT = 6000;
    /**
     * 读取超时时间
     */
    public final static int READ_TIMEOUT = 6000;

    private static final String[] USER_AGENT = {
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_5) AppleWebKit/600.1.17 (KHTML, like Gecko) Version/7.1 Safari/537.85.10"
    };

    public static String randomUserAgent() {
        return USER_AGENT[new Random().nextInt(USER_AGENT.length)];
    }

}
