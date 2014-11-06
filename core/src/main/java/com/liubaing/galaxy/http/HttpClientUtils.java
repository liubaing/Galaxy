package com.liubaing.galaxy.http;

import com.alibaba.fastjson.JSON;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.List;

/**
 * @author heshuai
 */
public class HttpClientUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);

    private static CloseableHttpClient httpClient = null;

    /**
     * 最大连接数
     */
    public final static int MAX_TOTAL_CONNECTIONS = 24;
    /**
     * 指定路由最大连接数
     */
    public final static int MAX_ROUTE_CONNECTIONS = 12;

    private HttpClientUtils() {
    }

    static {

        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
        connManager.setDefaultSocketConfig(socketConfig);
        MessageConstraints messageConstraints = MessageConstraints.custom()
                .setMaxHeaderCount(200)
                .setMaxLineLength(2000)
                .build();
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE)
                .setCharset(Consts.UTF_8)
                .setMessageConstraints(messageConstraints)
                .build();
        connManager.setDefaultConnectionConfig(connectionConfig);
        connManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        connManager.setDefaultMaxPerRoute(MAX_TOTAL_CONNECTIONS - MAX_ROUTE_CONNECTIONS);

        httpClient = HttpClients.custom()
                .setConnectionManager(connManager)
                .build();

        new IdleConnectionMonitorThread(connManager).start();

    }

    public static byte[] invokeGet(String url, HttpContext context) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(HttpParamUtils.READ_TIMEOUT)
                .setConnectTimeout(HttpParamUtils.CONNECT_TIMEOUT)
                .setConnectionRequestTimeout(HttpParamUtils.WAIT_TIMEOUT).build();

        HttpGet get = new HttpGet(url);
        get.setConfig(requestConfig);
        get.addHeader(HTTP.USER_AGENT, HttpParamUtils.randomUserAgent());
        try {
            CloseableHttpResponse response = httpClient.execute(get, context);
            final StatusLine statusLine = response.getStatusLine();
            final HttpEntity entity = response.getEntity();
            try {
                if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
                    EntityUtils.consumeQuietly(entity);
                    LOGGER.error(url + "\n 请求异常，状态值： " + statusLine.getStatusCode());
                } else if (entity != null) {
                    return EntityUtils.toByteArray(entity);
                }
            } finally {
                //关闭HttpConnect
                //response.close();
                EntityUtils.consumeQuietly(entity);
            }
        } catch (Exception e) {
            LOGGER.error(String.format("[HttpUtils Get]invoke get error, url:%s", url), e);
        }
        return null;
    }

    public static <T> T invokeGet(String url, Class<T> clazz, HttpContext context) {
        byte[] resp = invokeGet(url, context);
        if (resp != null) {
            try {
                return JSON.parseObject(resp, clazz);
            } catch (Exception e) {
                LOGGER.error("解析映射过程中发生异常，信息：" + e.getMessage(), e);
            }
        }
        return null;
    }

}
