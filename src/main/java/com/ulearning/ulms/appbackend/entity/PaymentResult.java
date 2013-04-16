package com.ulearning.ulms.appbackend.entity;

/**
 * 类说明:支付结果POJO
 * 		封装返回页面信息
 * @author heshuai
 * @version 2012-10-21
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
public class PaymentResult extends BaseModel{
	
	private static final long serialVersionUID = -3856362483408233430L;
	
	private String paymentStatusInfo;		//页面title
	
	private String orderName;				//订单名称(课程名称)
	
	private float orderPrice;				//订单总价(课程单价)
	
	private String paymentStatus;			//付款状态(已付款/未付款)
	
	public String getPaymentStatusInfo() {
		return paymentStatusInfo;
	}
	public void setPaymentStatusInfo(String paymentStatusInfo) {
		this.paymentStatusInfo = paymentStatusInfo;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public float getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(float orderPrice) {
		this.orderPrice = orderPrice;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
}