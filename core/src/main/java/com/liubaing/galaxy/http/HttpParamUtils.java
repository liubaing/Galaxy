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
            "Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:25.0) Gecko/20100101 Firefox/25.0",
            "Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36",
            "Mozilla/5.0 (Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko",
            "Windows-RSS-Platform/2.0 (MSIE 8.0; Windows NT 6.0)",
            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_2_1 like Mac OS X; zh-cn) AppleWebKit/533.17.9 (KHTML, like Gecko) Mobile/8C148"
    };

    public static String randomUserAgent() {

        return USER_AGENT[new Random().nextInt(USER_AGENT.length)];
    }

}
