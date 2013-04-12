package com.liubaing.shiny_liubaing.controller;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.liubaing.shiny_liubaing.alipay.NotifyData;
import com.liubaing.shiny_liubaing.alipay.ParameterUtil;
import com.liubaing.shiny_liubaing.alipay.PartnerConfig;
import com.liubaing.shiny_liubaing.alipay.RSASignature;
import com.liubaing.shiny_liubaing.alipay.XMapUtil;
import com.liubaing.shiny_liubaing.entity.PaymentResult;
import com.liubaing.shiny_liubaing.exception.AlipayTradeException;
import com.liubaing.shiny_liubaing.service.OrderManager;
import com.liubaing.shiny_liubaing.util.core.Const.payStatus;

/**
 * 订单 支付宝回调订单处理
 * @author heshuai
 * @version 2012-10-9
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	
	private static final String COMMON_TRADER_STATUS = "TRADE_FINISHED";
	
	//private static final String TRADER_STATUS = "TRADE_SUCCESS";
	
	
	@Autowired
	private OrderManager orderService;
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody String add(@RequestBody String orderInfo){
		return orderService.confirmOrderInfo(orderInfo);
	}
	
	@RequestMapping(value = "/callback", method = RequestMethod.GET)
	public ModelAndView callBack(@RequestParam String sign,@RequestParam String result,
			@RequestParam String request_token, @RequestParam String out_trade_no, @RequestParam String trade_no)
	{
        Map<String,String> resMap  = new HashMap<String,String>(4);
        resMap.put("result", result);
        resMap.put("request_token", request_token);
        resMap.put("out_trade_no", out_trade_no);
        resMap.put("trade_no", trade_no);
        String verifyData = ParameterUtil.getSignData(resMap);
        boolean verified = false;
        //使用支付宝公钥验签名
        try {
        	verified = RSASignature.doCheck(verifyData, sign, PartnerConfig.RSA_ALIPAY_PUBLIC, "utf-8");
		} catch (Exception e) {
			throw new AlipayTradeException("验证签名失败",e);
		}
		//准备用户提示页面
        ModelAndView paymentView = new ModelAndView("payment");
        short payResultStatus;
        if (!verified || !result.equals("success")) {
        	payResultStatus = payStatus.unpaid.getStatus();
        } else {
        	payResultStatus = payStatus.paid.getStatus();
        }
        PaymentResult paymentResult = orderService.orderProcessing(out_trade_no, payResultStatus);
        paymentView.addObject("paymentResult", paymentResult);
        return paymentView;
	}
	
	/**
	 * 
	  * 方法描述：支付完成，支付宝反馈交易状态信息
	  * @param notify_data中包含此次交易信息
	  * @return 成功则success 
	  * @author heshuai
	  * @version 2012-11-29 下午03:46:48
	 */
	@RequestMapping(value = "/notify", method = RequestMethod.POST)
	@ResponseBody
	public String notify(HttpServletRequest request)
	{
        Map<?, ?> map = request.getParameterMap();
        //获得通知签名
        String sign = (String) ((Object[]) map.get("sign"))[0];
        //获得待验签名的数据
        String service = (String) ((Object[]) map.get("service"))[0];
        String v = (String) ((Object[]) map.get("v"))[0];
        String sec_id = (String) ((Object[]) map.get("sec_id"))[0];
        String notify_data = (String) ((Object[]) map.get("notify_data"))[0];
        try {
            //对返回的notify_data数据用商户私钥解密
            notify_data = RSASignature.decrypt(notify_data, PartnerConfig.RSA_PRIVATE);
        } catch (Exception e) {
        	throw new AlipayTradeException("解密notify_data失败",e);
        }
        String verifyData =  "service=" + service + "&v=" + v 
        						+ "&sec_id=" + sec_id + "&notify_data=" + notify_data;
        boolean verified = false;
        //使用支付宝公钥验签名
        try {
            verified = RSASignature.doCheck(verifyData, sign, PartnerConfig.RSA_ALIPAY_PUBLIC, "utf-8");
        } catch (Exception e) {
        	throw new AlipayTradeException("验证签名失败",e);
        }
        //验证签名通过
        NotifyData notifyData = null;
        if (verified) {
        	XMapUtil.register(NotifyData.class);
    		try {
    			notifyData = (NotifyData) XMapUtil
    					.load(new ByteArrayInputStream(notify_data
    							.getBytes("UTF-8")));
    		} catch (UnsupportedEncodingException e) {//忽略
    		} catch (Exception e) {
    			throw new AlipayTradeException("解析notify_data失败",e);
    		}
        	//根据交易状态处理业务逻辑
    		if (notifyData != null && 
    				COMMON_TRADER_STATUS.equals(notifyData.getTrade_status())) {
    			orderService.orderProcessing(notifyData.getOut_trade_no());
    			return "success";
			}
        } 
        return null;
	}
}
