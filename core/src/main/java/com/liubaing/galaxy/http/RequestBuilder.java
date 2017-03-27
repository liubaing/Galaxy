package com.liubaing.galaxy.http;

import com.alibaba.fastjson.JSON;
import com.liubaing.galaxy.exception.ValidateException;
import com.liubaing.galaxy.util.Constants;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;

import java.net.URI;
import java.util.*;

/**
 * 构建请求
 *
 * @author heshuai
 * @version 2016/12/28.
 */
public class RequestBuilder {

    private URI uri;                //已经包含scheme,host,path,parameter

    private Method method;          //默认Get

    private ContentType contentType;

    private Map<String, ?> headers;
    private Map<String, ?> params;  //Post中Form参数
    private byte[] body;            //STREAM

    //-1是系统默认值，单位毫秒
    private Integer socketTimeout;
    private Integer connectTimeout;
    private Integer connectionRequestTimeout;

    public RequestBuilder(URI uri) {
        this.uri = uri;
    }

    public static RequestBuilder create(URI uri) {
        return new RequestBuilder(uri);
    }

    public RequestBuilder setMethod(Method method) {
        this.method = method;
        return this;
    }

    public RequestBuilder setContentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    public RequestBuilder setHeaders(Map<String, ?> headers) {
        this.headers = headers;
        return this;
    }

    public RequestBuilder setParams(Map<String, ?> params) {
        this.params = params;
        return this;
    }

    public RequestBuilder setBody(byte[] body) {
        this.body = body;
        return this;
    }

    public RequestBuilder setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
        return this;
    }

    public RequestBuilder setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public RequestBuilder setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
        return this;
    }

    public HttpRequestBase build() {
        if (uri == null) {
            throw new ValidateException("请求URI异常");
        }
        if (method == null) {
            this.method = Method.GET;
        }
        HttpRequestBase httpRequestBase = null;
        switch (method) {
            case GET:
                httpRequestBase = new HttpGet(uri);
                break;
            case POST:
                HttpPost httpPost = new HttpPost(uri);
                HttpEntity entity = null;
                switch (this.contentType) {
                    case FORM:
                        List<NameValuePair> parameters = new ArrayList<>();
                        if (MapUtils.isNotEmpty(params)) {
                            for (Map.Entry<String, ?> entry : params.entrySet()) {
                                //Value 不允许为空
                                if (entry.getValue() == null) {
                                    continue;
                                }
                                Object value = entry.getValue();
                                if (value instanceof Collection) {
                                    Collection<?> values = (Collection<?>) value;
                                    values.stream().forEach(v ->
                                                    parameters.add(new BasicNameValuePair(entry.getKey(), v == null ? null : String.valueOf(v)))
                                    );
                                } else if (value instanceof Map) {
                                    parameters.add(new BasicNameValuePair(entry.getKey(), JSON.toJSONString(entry.getValue())));
                                } else {
                                    parameters.add(new BasicNameValuePair(entry.getKey(), String.valueOf(value)));
                                }
                            }
                            entity = new UrlEncodedFormEntity(parameters, Consts.UTF_8);
                        } else if (ArrayUtils.isNotEmpty(body)) {
                            entity = EntityBuilder.create()
                                    .setBinary(body)
                                    .setContentType(org.apache.http.entity.ContentType.create(ContentType.FORM.getMimeType(), Constants.DEFAULT_CHARSET))
                                    .build();
                        }
                        break;
                    case JSON:
                        EntityBuilder builder = EntityBuilder.create();
                        if (MapUtils.isNotEmpty(params)) {
                            builder.setText(JSON.toJSONString(params));
                        } else if (ArrayUtils.isNotEmpty(body)) {
                            builder.setBinary(body);
                        }
                        entity = builder.setContentType(org.apache.http.entity.ContentType.APPLICATION_JSON)
                                .build();
                        break;
                    case STREAM:
                        entity = EntityBuilder.create()
                                .setBinary(this.body)
                                .build();
                        break;
                    default:
                        throw new ValidateException("POST方法必须明确Content-Type");
                }
                httpPost.setEntity(entity);
                httpRequestBase = httpPost;
                break;
        }
        if (MapUtils.isNotEmpty(headers)) {
            for (Map.Entry<String, ?> entry : headers.entrySet()) {
                httpRequestBase.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        RequestConfig.Builder builder = RequestConfig.copy(HttpClientUtils.REQUEST_CONFIG);
        if (socketTimeout != null) {
            builder.setSocketTimeout(socketTimeout);
        }
        if (connectTimeout != null) {
            builder.setConnectTimeout(connectTimeout);
        }
        if (connectionRequestTimeout != null) {
            builder.setConnectionRequestTimeout(connectionRequestTimeout);
        }
        httpRequestBase.setConfig(builder.build());
        return httpRequestBase;

    }

    public Optional<Response> request() {
        return Optional.ofNullable(HttpClientUtils.execute(this.build()));
    }
}
