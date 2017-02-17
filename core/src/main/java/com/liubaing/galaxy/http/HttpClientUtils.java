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
import org.apache.http.conn.HttpClientConnectionManager;
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
        SocketConfig socketConfig = SocketConfig.custom()
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

        HTTP_CLIENT = HttpClients.custom()
                .setConnectionManager(connManager)
                .setDefaultRequestConfig(REQUEST_CONFIG)
                .disableCookieManagement()
                .disableAutomaticRetries()
                .build();

        final ConnectionMonitorThread closedConnection = new ConnectionMonitorThread(connManager);
        closedConnection.setDaemon(true);
        closedConnection.setContextClassLoader(null);
        closedConnection.start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    closedConnection.interrupt();
                    closedConnection.shutdown();
                    HTTP_CLIENT.close();
                } catch (Exception e) {
                    //ignore
                }
            }
        });
    }

    private HttpClientUtils() {
    }

    /**
     * 关闭无效链接
     */
    private static class ConnectionMonitorThread extends Thread {

        private final HttpClientConnectionManager connMgr;
        private volatile boolean shutdown;

        public ConnectionMonitorThread(HttpClientConnectionManager connMgr) {
            super();
            this.connMgr = connMgr;
        }

        @Override
        public void run() {
            try {
                while (!shutdown) {
                    synchronized (this) {
                        wait(4 * 1000);
                        connMgr.closeExpiredConnections();
                        connMgr.closeIdleConnections(8, TimeUnit.SECONDS);
                    }
                }
            } catch (InterruptedException ex) {
                // terminate
            }
        }

        public void shutdown() {
            shutdown = true;
            synchronized (this) {
                notifyAll();
            }
        }
    }

    public static byte[] invoke(HttpRequestBase method) {
        String url = method.getURI().toString();
        try {
            if (method.getConfig() == null) {
                method.setConfig(REQUEST_CONFIG);
            }
            CloseableHttpResponse response = HTTP_CLIENT.execute(method);
            final StatusLine statusLine = response.getStatusLine();
            final HttpEntity entity = response.getEntity();
            try {
                if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
                    EntityUtils.consumeQuietly(entity);
                    LOGGER.warn("[" + url + "] 请求失败，状态值 [" + statusLine.getStatusCode() + "]");
                } else if (entity != null) {
                    return EntityUtils.toByteArray(entity);
                }
            } finally {
                EntityUtils.consumeQuietly(entity);
            }
        } catch (SocketException | SocketTimeoutException | ConnectTimeoutException | NoHttpResponseException e) {
            LOGGER.warn("[" + url + "] 请求异常，原因 [" + ExceptionUtils.getRootCauseMessage(e) + " ]");
        } catch (Exception e) {
            LOGGER.error("[" + url + "] 请求失败", e);
        }
        return null;
    }
}
