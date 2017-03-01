package com.liubaing.galaxy.http;

import com.liubaing.galaxy.util.ConfigUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.CodingErrorAction;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * HTTP请求工具类
 *
 * @author heshuai
 * @version 15-1-13.
 */
public final class HttpClientUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);

    private static final CloseableHttpClient HTTP_CLIENT;

    static final RequestConfig REQUEST_CONFIG;
    /**
     * 获取连接的最大等待时间(单位毫秒)
     */
    private final static int WAIT_TIMEOUT = 2000;
    /**
     * 连接超时时间
     */
    private final static int CONNECT_TIMEOUT = 2000;
    /**
     * 读取超时时间
     */
    private final static int READ_TIMEOUT = 2000;
    /**
     * 最大连接数
     */
    public final static int MAX_TOTAL_CONNECTIONS = 128;

    static {
        Properties properties = ConfigUtils.loadProperties("config-http-client.properties");
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        int totalConnection = NumberUtils.toInt(properties.getProperty("http.connection.max.total"), MAX_TOTAL_CONNECTIONS);
        connManager.setMaxTotal(totalConnection);
        connManager.setDefaultMaxPerRoute(totalConnection);

        int socketTimeout = NumberUtils.toInt(properties.getProperty("http.connection.socket.timeout.ms"), READ_TIMEOUT);
        int connectTimeout = NumberUtils.toInt(properties.getProperty("http.connection.timeout.ms"), CONNECT_TIMEOUT);
        int connectionRequestTimeout = NumberUtils.toInt(properties.getProperty("http.connection.request.timeout.ms"), WAIT_TIMEOUT);
        REQUEST_CONFIG = RequestConfig.custom()
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .build();

        SocketConfig socketConfig = SocketConfig.custom()
                .setSoTimeout(socketTimeout)
                .setSoKeepAlive(true)
                .setTcpNoDelay(true)
                .build();
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

        HTTP_CLIENT = HttpClients.custom()
                .setConnectionManager(connManager)
                .setConnectionManagerShared(false)
                .setDefaultRequestConfig(REQUEST_CONFIG)
                .disableCookieManagement()
                .disableAutomaticRetries()
                .disableRedirectHandling()
                .evictExpiredConnections()
                .evictIdleConnections(4, TimeUnit.SECONDS)
                .setUserAgent("Galaxy")
                .build();
    }

    private HttpClientUtils() {
    }

    static Response execute(HttpRequestBase method) {
        String url = method.getURI().toString();
        try {
            try (CloseableHttpResponse closeableHttpResponse = HTTP_CLIENT.execute(method)) {
                final int code = closeableHttpResponse.getStatusLine().getStatusCode();
                Response response = new Response(code);
                final Header[] headers = closeableHttpResponse.getAllHeaders();
                for (final Header header : headers) {
                    response.addHeader(header.getName(), header.getValue());
                }
                final HttpEntity entity = closeableHttpResponse.getEntity();
                if (code == HttpStatus.SC_OK) {
                    response.setBody(EntityUtils.toByteArray(entity));
                } else {
                    EntityUtils.consumeQuietly(entity);
                    LOGGER.warn("[" + url + "] 请求失败，状态值 [" + code + "]");
                }
                return response;
            }
        } catch (SocketException | SocketTimeoutException | ConnectTimeoutException | NoHttpResponseException e) {
            LOGGER.warn("[" + url + "] 请求异常，原因 [" + ExceptionUtils.getRootCauseMessage(e) + " ]");
        } catch (Exception e) {
            LOGGER.error("[" + url + "] 请求失败", e);
        }
        return null;
    }
}
