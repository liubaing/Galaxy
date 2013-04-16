package com.ulearning.ulms.appbackend.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.stream.JsonReader;
import com.ulearning.ulms.appbackend.alipay.Trade;
import com.ulearning.ulms.appbackend.entity.Course;
import com.ulearning.ulms.appbackend.entity.Order;
import com.ulearning.ulms.appbackend.entity.OrderDetail;
import com.ulearning.ulms.appbackend.entity.PaymentResult;
import com.ulearning.ulms.appbackend.entity.User;
import com.ulearning.ulms.appbackend.exception.AlipayTradeException;
import com.ulearning.ulms.appbackend.exception.ULMSException;
import com.ulearning.ulms.appbackend.repository.jpa.CourseRepository;
import com.ulearning.ulms.appbackend.repository.jpa.OrderRepository;
import com.ulearning.ulms.appbackend.repository.jpa.UserRepository;
import com.ulearning.ulms.appbackend.util.core.Const.payStatus;
import com.ulearning.ulms.appbackend.util.log.LogUtils;

/**
 * 订单处理业务
 * <p>目前开课由平台处理，此处耗时较长</p>
 * @author heshuai
 * @version 2012-10-9
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
@Service
public class OrderManager {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private Trade trade;
	
	@Autowired
	private CourseManager courseManager;
	//开课地址
	//private static final String OPENCOURSEURI = "http://www.statsedu.com/staedu/cart/openCourse.do";
	private static final String OPENCOURSEURI = "http://www.longmanenglish.cn/LEI/order/order.do?operation=judgeChecked";
	
	//private static final String ORDERNO = "orderId";
	private static final String ORDERNO = "orderNo";
	
	public String confirmOrderInfo(String orderInfo)
	{
		Order order = null;
		try {
			order = this.phraseOrderInfo(orderInfo);
		} catch (IOException e) {
			LogUtils.doLog("解析client端订单信息失败", e, orderInfo);
			throw new AlipayTradeException(e);
		}
		orderRepository.save(order);
		order = this.prepareTradeOrder(order);
		return trade.trade(order);
	}
	/**
	 * 
	  * 方法描述：根据客户端POST过来的订单数据<br/>
	  * 	<code>{"userid":xxx,"courseid":xxx}</code>格式化<br/>
	  * 	目前只有一次订单仅有一门课程，暂无折扣价
	  * @author heshuai
	  * @version 2012-10-10 下午03:04:52
	 */
	protected Order phraseOrderInfo(String orderInfo)throws IOException
	{
        JsonReader reader = new JsonReader(new StringReader(orderInfo));
        Set<OrderDetail> orderDetails = new HashSet<OrderDetail>();
        int userID = 0,courseID = 0;
        reader.beginObject();
        while(reader.hasNext()){
            String tagName = reader.nextName();
            if(tagName.equals("userid")){
            	userID = reader.nextInt();
            }    
            else if(tagName.equals("courseid")){
            	courseID = reader.nextInt();
            }
        }
        reader.endObject();    
        User user = userRepository.findOne(userID);
        Course course = courseRepository.findOne(courseID);
        OrderDetail orderDetail = new OrderDetail(course);
        orderDetails.add(orderDetail);
        return new Order(user,orderDetails);
	}
	/**
	 * 
	  * 方法描述：支付完成回调方法<br/>
	  * 		成功则根据订单编号，修改订单状态，并重置用户新的课程<br/>
	  * 		失败则只返回信息
	  * @param outTradeNo 订单编号
	  * @param status 订单状态 
	  * @author heshuai
	  * @version 2012-10-11 上午11:39:35
	 */
	public PaymentResult orderProcessing(String outTradeNo, short status)
	{
		Order order = orderRepository.findByOrderNo(outTradeNo);
		if (order == null) throw new AlipayTradeException(outTradeNo+"订单不存在");
		
		PaymentResult paymentResult = new PaymentResult();
		if (status == payStatus.paid.getStatus()) {
			if (order.getPaymentStatus()!= payStatus.paid.getStatus()) {
				this.openCourse(outTradeNo);
				int userID = order.getUserID();
				courseManager.reset(userID);
			}
			paymentResult.setPaymentStatusInfo("恭喜您，"+payStatus.paid.getMsg());
			paymentResult.setPaymentStatus(payStatus.paid.getMsg());
		}else{
			paymentResult.setPaymentStatusInfo("对不起，"+payStatus.unpaid.getMsg());
			paymentResult.setPaymentStatus(payStatus.unpaid.getMsg());
		}
		order = this.prepareTradeOrder(order);
		paymentResult.setOrderName(order.getOrderName());
		paymentResult.setOrderPrice(order.getTotalPrice());
		return paymentResult;
	}
	/**
	 * 
	  * 方法描述：交易成功，执行开课，重置用户课程
	  * @param 订单编码
	  * @author heshuai
	  * @version 2012-11-29 下午04:25:44
	 */
	public void orderProcessing(String outTradeNo)
	{
		Order order = orderRepository.findByOrderNo(outTradeNo);
		if (order == null) throw new AlipayTradeException(outTradeNo+"订单不存在");
		this.openCourse(outTradeNo);
		int userID = order.getUserID();
		courseManager.reset(userID);
	}
	/**
	 * 
	  * 方法描述：准备alipay支付参数
	  * @author heshuai
	  * @version 2012-10-19 上午10:27:11
	 */
	protected Order prepareTradeOrder(Order order)
	{
		Set<OrderDetail> orderDetails = order.getOrderDetails(); 
		Iterator<OrderDetail> iterator = orderDetails.iterator();
		while (iterator.hasNext()) {
			OrderDetail orderDetail = (OrderDetail) iterator.next();
			order.setOrderName(orderDetail.getRelationName());
			break;
		}
		return order;
	}
	/**
	 * 
	  * 方法描述：开通课程
	  * @param orderNo 订单编号
	  * @author: heshuai
	  * @version: 2012-11-6 下午02:52:34
	 */
	private void openCourse(final String orderNo){
		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
	    	URIBuilder uriBuilder = new URIBuilder(OPENCOURSEURI);
	    	uriBuilder.addParameter(ORDERNO, orderNo);
	    	HttpGet httpGet = new HttpGet(uriBuilder.build());
	    	httpclient.execute(httpGet);
		} catch (Exception e) {
			LogUtils.doLog("开课失败", e, orderNo);
			throw new ULMSException(e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}
}
