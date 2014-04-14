package com.liubaing.galaxy.http;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLException;

import com.alibaba.fastjson.JSON;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
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

/**
 * itunes连接客户端（其他连接不建议使用）
 * <p>限制打开连接（标签）数，避免403</p>
 * <p>出现IOException时重试</p>
 * <p>采用默认的Cookie机制</p>
 * <p>由于连接是Keep-alive的，采用默认ConnectionReuse机制</p>
 * @author heshuai
 *
 */
public class HttpClientUtils {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);
	
	private static PoolingHttpClientConnectionManager connManager = null;
	private static CloseableHttpClient httpClient = null;
	
	/**
     * 最大连接数
     */  
    public final static int MAX_TOTAL_CONNECTIONS = 24;
    /** 
     * 指定路由最大连接数
     */  
    public final static int MAX_ROUTE_CONNECTIONS = 12;
    /**
     * 失败重试次数
     */
    public final static int EXECUTION_COUNT = 3;
    
    public final static String HOST_NAME = "";
	
	private HttpClientUtils (){}
	
	static {
		
		HttpRoute itunes = new HttpRoute(new HttpHost(HOST_NAME, 80 , HttpHost.DEFAULT_SCHEME_NAME));
		
		connManager = new PoolingHttpClientConnectionManager();
		// Create socket configuration
		SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
		connManager.setDefaultSocketConfig(socketConfig);
		// Create message constraints
        MessageConstraints messageConstraints = MessageConstraints.custom()
            .setMaxHeaderCount(200)
            .setMaxLineLength(2000)
            .build();
        // Create connection configuration
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
            .setMalformedInputAction(CodingErrorAction.IGNORE)
            .setUnmappableInputAction(CodingErrorAction.IGNORE)
            .setCharset(Consts.UTF_8)
            .setMessageConstraints(messageConstraints)
            .build();
        connManager.setDefaultConnectionConfig(connectionConfig);
        connManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
		connManager.setMaxPerRoute(itunes, MAX_ROUTE_CONNECTIONS);
        connManager.setDefaultMaxPerRoute(MAX_TOTAL_CONNECTIONS - MAX_ROUTE_CONNECTIONS);
		
		HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
			
			public boolean retryRequest(IOException exception, int executionCount,
					HttpContext context) {
				if (executionCount > EXECUTION_COUNT)
					return false;
				if (exception instanceof InterruptedIOException)
					return true;
				if (exception instanceof UnknownHostException)
					return false;
				if (exception instanceof ConnectionPoolTimeoutException)
					return true;
				if (exception instanceof SSLException)
					return false;
				HttpClientContext clientContext = HttpClientContext.adapt(context);
				HttpRequest request = clientContext.getRequest();
				//判断请求类型 当前默认为GET  返回TRUE
				boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
				if (idempotent) return true;
				return false;
			}
		};
		
		/*ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {

            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                // Honor 'keep-alive' header
                HeaderElementIterator it = new BasicHeaderElementIterator(
                        response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        try {
                            return Long.parseLong(value) * 1000;
                        } catch(NumberFormatException ignore) {
                        }
                    }
                }
                HttpHost target = (HttpHost) context.getAttribute(
                        HttpClientContext.HTTP_TARGET_HOST);
                if (HOST_NAME.equalsIgnoreCase(target.getHostName())) {
                    return 30 * 1000;
                } else {
                    // otherwise keep alive for 5 seconds
                    return 5 * 1000;
                }
            }

        };
        
        HttpRoutePlanner routePlanner = new HttpRoutePlanner() {

            public HttpRoute determineRoute(HttpHost target,
                    HttpRequest request, HttpContext context) throws HttpException {
                return new HttpRoute(target, null, new HttpHost("10.0.0.50", 8888),
                        "https".equalsIgnoreCase(target.getSchemeName()));
            }

        };*/
		List<BasicHeader> headers = new ArrayList<BasicHeader>(1);
		headers.add(new BasicHeader(HTTP.USER_AGENT, "iTunes/11.1.3 (Windows; Microsoft Windows 7 Ultimate Edition Service Pack 1 (Build 7601)) AppleWebKit/536.30.1"));
		headers.add(new BasicHeader(HTTP.TARGET_HOST, HOST_NAME));
		
		httpClient = HttpClients.custom()
				.setRetryHandler(myRetryHandler)
				.setConnectionManager(connManager)
				//.setDefaultCookieStore(cookieStore)
				.setDefaultHeaders(headers)
				//.setKeepAliveStrategy(myStrategy)
				//.setRoutePlanner(routePlanner)
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
					LOGGER.error(url +"\n 请求异常，状态值： "+  statusLine.getStatusCode());
				} else if(entity != null){
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
	    if(resp != null){
			try {
				return JSON.parseObject(resp, clazz);
			} catch (Exception e) {
				LOGGER.error("解析映射过程中发生异常，信息："+e.getMessage() ,e);
			}
	    }
	    return null;
	}
	
	public static CloseableHttpClient getItunesHttpClint(){
        return httpClient;
	}
}
