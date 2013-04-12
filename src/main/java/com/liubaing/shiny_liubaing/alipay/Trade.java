package com.liubaing.shiny_liubaing.alipay;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.liubaing.shiny_liubaing.alipay.PartnerConfig;
import com.liubaing.shiny_liubaing.entity.Order;
import com.liubaing.shiny_liubaing.exception.AlipayTradeException;

/**
 * 
 * 调用支付宝的开放平台创建、支付交易步骤
 * 
 * 1.将业务参数：外部交易号、商品名称、商品总价、卖家帐户、卖家帐户、notify_url这些东西按照xml 的格式放入<req_data></req_data>中
 * 2.将通用参数也放入请求参数中 
 * 3.对以上的参数进行签名，签名结果也放入请求参数中
 * 4.请求支付宝开放平台的alipay.wap.trade.create.direct服务
 * 5.从开放平台返回的内容中取出request_token（对返回的内容要先用私钥解密，再用支付宝的公钥验签名）
 * 6.使用拿到的request_token组装alipay.wap.auth.authAndExecute服务的跳转url
 * 7.根据组装出来的url跳转到支付宝的开放平台页面，交易创建和支付在支付宝的页面上完成
 */
@Component
public class Trade{

	//签名类型
	private static final String SEC_ID = "0001";
	
	//支付成功，跳转连接
	private static final String callbackUrl = "http://apps.longmanenglish.cn/order/callback";
	
	//支付成功，异步调用
	private static final String notifyUrl = "http://apps.longmanenglish.cn/order/notify";
	
	/**
	 * 
	  * 创建交易
	  * 
	 */
	public String trade(Order order)
	{
		Map<String, String> reqParams = prepareTradeRequestParamsMap(order);
		//签名类型 
		String signAlgo = SEC_ID;
		//交易请求URL
		String reqUrl = PartnerConfig.REQ_URL;
		//签名
		String sign = sign(reqParams, signAlgo, PartnerConfig.RSA_PRIVATE);
		//签名结果也放入请求参数中
		reqParams.put("sign", sign);
		ResponseResult resResult = new ResponseResult();
		String businessResult = "";
		try {
			resResult = send(reqParams, reqUrl, signAlgo);
		} catch (Exception e) {
			throw new AlipayTradeException("调用支付宝开放平台的服务失败",e);
		}
		if (resResult.isSuccess()) {
			businessResult = resResult.getBusinessResult();
		}
		DirectTradeCreateRes directTradeCreateRes = null;
		XMapUtil.register(DirectTradeCreateRes.class);
		try {
			directTradeCreateRes = (DirectTradeCreateRes) XMapUtil
					.load(new ByteArrayInputStream(businessResult
							.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {//忽略
		} catch (Exception e) {
			throw new AlipayTradeException("解析支付宝返回结果集失败",e);
		}
		//取出request_token
		String requestToken = directTradeCreateRes.getRequestToken();
		Map<String, String> authParams = prepareAuthParamsMap(requestToken);
		//对调用授权请求数据签名
		String authSign = sign(authParams, signAlgo, PartnerConfig.RSA_PRIVATE);
		authParams.put("sign", authSign);
		//返回重定向地址
		String redirectURL = getRedirectUrl(authParams, reqUrl);
		return redirectURL;
	}
	
	
	
	/**
	 * 准备alipay.wap.trade.create.direct服务的参数
	 * 
	 * @param request
	 * @throws UnsupportedEncodingException
	 */
	private Map<String, String> prepareTradeRequestParamsMap(Order order)
	{
		Map<String, String> requestParams = new HashMap<String, String>();
		// 商品名称
		String subject = order.getOrderName();
		// 商品总价
        String totalFee = order.getTotalPrice()+"";
        // 外部交易号 这里取当前时间，商户可根据自己的情况修改此参数，但保证唯一性
        String outTradeNo = order.getOrderNo();
		// 卖家帐号
		String sellerAccountName = PartnerConfig.SELLER;
		// 接收支付宝发送的通知的url
		//String notifyUrl = "";
		//未完成支付，用户点击链接返回商户url
		//String merchantUrl = "";
		// req_data的内容
		String reqData = "<direct_trade_create_req>" 
				+ "<subject>" + subject
				+ "</subject><out_trade_no>" + outTradeNo
				+ "</out_trade_no><total_fee>" + totalFee
				+ "</total_fee><seller_account_name>" + sellerAccountName
				+ "</seller_account_name><notify_url>" + notifyUrl
				+ "</notify_url><call_back_url>"+callbackUrl
				+ "</call_back_url></direct_trade_create_req>";
		requestParams.put("req_data", reqData);
		requestParams.put("req_id", System.currentTimeMillis() + "");
		requestParams.putAll(prepareCommonParams());
		return requestParams;
	}

	/**
	 * 准备alipay.wap.auth.authAndExecute服务的参数
	 * 
	 * @param requestToken
	 */
	private Map<String, String> prepareAuthParamsMap(String requestToken) {
		Map<String, String> requestParams = new HashMap<String, String>();
		String reqData = "<auth_and_execute_req><request_token>" + requestToken
				+ "</request_token></auth_and_execute_req>";
		requestParams.put("req_data", reqData);
		requestParams.putAll(prepareCommonParams());
        //支付成功跳转链接
		requestParams.put("call_back_url", callbackUrl);
		requestParams.put("service", "alipay.wap.auth.authAndExecute");
		return requestParams;
	}

	/**
	 * 准备通用参数
	 * 
	 * @param request
	 */
	private Map<String, String> prepareCommonParams() {
		Map<String, String> commonParams = new HashMap<String, String>();
		commonParams.put("service", "alipay.wap.trade.create.direct");
		commonParams.put("sec_id", SEC_ID);
		commonParams.put("partner", PartnerConfig.PARTNER);
		commonParams.put("format", "xml");
		commonParams.put("v", "2.0");
		return commonParams;
	}

	/**
	 * 对参数进行签名
	 * 
	 * @param reqParams
	 */
	private String sign(Map<String, String> reqParams,String signAlgo,String key) {
		String signData = ParameterUtil.getSignData(reqParams);
		String sign = RSASignature.sign(signData, key,"utf-8");
		return sign;
	}

	/**
	 * 调用alipay.wap.auth.authAndExecute服务的时候需要跳转到支付宝的页面，组装跳转url
	 * 
	 * @param reqParams
	 */
	private String getRedirectUrl(Map<String, String> reqParams,String reqUrl){
		String redirectUrl = reqUrl + "?";
		try {
			redirectUrl = redirectUrl + ParameterUtil.mapToUrl(reqParams);
		} catch (UnsupportedEncodingException e) {}
		return redirectUrl;
	}

	/**
	 * 调用支付宝开放平台的服务
	 * 
	 * @param reqParams 请求参数
	 * @throws Exception
	 */
	private ResponseResult send(Map<String, String> reqParams,String reqUrl,String secId) throws Exception {
		String response = "";
		String invokeUrl = reqUrl  + "?";
		URL serverUrl = new URL(invokeUrl);
		HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.connect();
		String params = ParameterUtil.mapToUrl(reqParams);
		conn.getOutputStream().write(params.getBytes());
		InputStream is = conn.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		response = URLDecoder.decode(buffer.toString(), "utf-8");
		conn.disconnect();
		return praseResult(response,secId);
	}

	/**
	 * 解析支付宝返回的结果
	 * 
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ResponseResult praseResult(String response,String secId) throws Exception {
		// 调用成功
		HashMap<String, String> resMap = new HashMap<String, String>();
		String v = ParameterUtil.getParameter(response, "v");
		String service = ParameterUtil.getParameter(response, "service");
		String partner = ParameterUtil.getParameter(response, "partner");
		String sign = ParameterUtil.getParameter(response, "sign");
		String reqId = ParameterUtil.getParameter(response, "req_id");
		resMap.put("v", v);
		resMap.put("service", service);
		resMap.put("partner", partner);
		resMap.put("sec_id", secId);
		resMap.put("req_id", reqId);
		String businessResult = "";
		ResponseResult result = new ResponseResult();
		if (response.contains("<err>")) {
			result.setSuccess(false);
			businessResult = ParameterUtil.getParameter(response, "res_error");
			// 转换错误信息
			XMapUtil.register(ErrorCode.class);
			ErrorCode errorCode = (ErrorCode) XMapUtil
					.load(new ByteArrayInputStream(businessResult
							.getBytes("UTF-8")));
			result.setErrorMessage(errorCode);
			resMap.put("res_error", ParameterUtil.getParameter(response,
					"res_error"));
		} else {
		    businessResult = ParameterUtil.getParameter(response, "res_data");
            result.setSuccess(true);
            //对返回的res_data数据先用商户私钥解密
            String resData= RSASignature.decrypt(businessResult, PartnerConfig.RSA_PRIVATE);
            result.setBusinessResult(resData);
            resMap.put("res_data", resData);
		}
		//获取待签名数据
		String verifyData = ParameterUtil.getSignData(resMap);
		//对待签名数据使用支付宝公钥验签名
		boolean verified = RSASignature.doCheck(verifyData, sign, PartnerConfig.RSA_ALIPAY_PUBLIC,"");
		if (!verified) {
			throw new AlipayTradeException("验证签名失败");
		}
		return result;
	}
}
