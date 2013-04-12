package com.liubaing.shiny_liubaing.alipay;
/**
 * 支付宝合作商户基本配置信息
 * @author heshuai
 * @version 2012-10-10
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
public class PartnerConfig {
	//合作商户ID。
    public static final String PARTNER = "2088801057214196";
    //商户收款的支付宝账号
    public static final String SELLER = "bill@ulearning.cn";
    //商户（RSA）私钥
    public static final String RSA_PRIVATE ="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMIQs835q1IFuih1idOQxmNOo46iwcg7me7wZGvlOWj/6+6ulUw67hJvFexEgZTLikY9g93HfWP+vt8BaRLeOTtFDBNzyIBeHx9afTQqwmUYOpScZvKXdxlMsCTPUVoJ4hEeBgInV4X8f7HH2fBmMt7zhKPBHMlROoFVjvXF+8XDAgMBAAECgYBehSQOPnxjBscUzarDkNAPrk2bO0qG7ACdkBhZNNwxd/TB2+Z7qngqIlDbMsKqZjiC3ZNMgc1g1sLAez9iITpd+jjxl6m0L6twbd2dmUOF4GuOQiRqjZjU7TobWZZPYTLs5dTHGgwJsdpadrtE2TyKQtbCc7diLrP9ExSCIKS+gQJBAO41GinRV0VKNy2FxsEcDluNOkeVJ0E3jRWEkFG+OhBzWbumgXFCtanEnUMgZzgCDd5I/Q/M7Ssiok4g1Wc7EdUCQQDQj4hcnBYHxJwBvPMRWyZUFY9Ypc6HXjI2ywOWMvDSBP7J6h0kzAaeqgsDloorvdAwjqHcWU7ydXNwgyEZRq03AkA0Uk+j3cp4erux8Q9d/UaeD2A8tuM/bO89SbfVbJ4eXOfZ0tHdydRTLM+KHgTuBW18ZLXJEa8KaXoTBR06pnvdAkEAyIM2dtMZ2x4LXyBqEKWxk+Wmi3PdvAez/znvmNgg78odq8KJgnclZiXVsP1qmeND5Ws78r1qSXDiK6nmiIaKwQJBALc3xPf0uaK0y2aKnf252vVacSm72ZBBPCCSQlXmbMgtgq1m8GLI8xKSKw5iL/urL044WqWGBZAo5sSo1KtHR7U=";
    //支付宝（RSA）公钥 
    public static final String RSA_ALIPAY_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCB2Jn+SkoqvmBbk0YRcwY5M6FqmNeFda/dOduYfUBRZT4f3Pz7r5vI6W8EHZx2c7GR6qn5Dw6ElPy0NOH7fBvl1slAgqK45gak72g3f8ifnGXEzJJsKVm2cUsSaODFxzx4IcX74khXgqBeH84DbWFls1vOqYiy+Oisb+z+86NkjQIDAQAB";
    //商户（MD5）KEY
    public static final String KEY = "lqxjtn6qnhog9cysxs4btwm5jvpre9bn";
    //创建交易请求URL
    public static final String REQ_URL = "https://wappaygw.alipay.com/service/rest.htm";
}
