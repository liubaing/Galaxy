package com.ulearning.ulms.appbackend.entity;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ulearning.ulms.appbackend.util.core.Const.payStatus;

/**
 * 类说明:订单实体
 * @author heshuai
 * @version 2012-10-9
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
@Entity
@Table(name = "c_order_tab")
public class Order extends BaseModel{
	
	private static final long serialVersionUID = -4010591496635522245L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer orderID;
	
	private String orderNo;		//订单编码
	
//	@Transient
//	private int aspID;			//区域ID
	
	private int orgID;			//机构ID
	
	private int userID;			//用户ID
	
//	@Column(name="remark1")
//	private String userName;	//用户姓名
	
	@Column(name = "itemprice")
	private float totalPrice;	//总价
	
	@OneToMany(cascade = {CascadeType.ALL},mappedBy = "order",fetch = FetchType.LAZY)
	private Set<OrderDetail> orderDetails;

	//private short orderType;
	
	private float discountPrice;
	
	//private String discountCode;
	
	//private short orderStatus;
	
	
	@Column(name = "paymentmethod")
	private short paymentType;	//付款类型   0:线下  1:线上（默认）
	
	private short paymentStatus;//付款状态   0:未付款   1:已付款
	
	@Column(name = "createdate")
	private Date createTime;	//创建时间
	
	@Column(name = "updatedate")
	private Date updateTime;	//修改时间
	
	@Transient
	private String orderName;	//课程名称
	
	public Order() {
		super();
	}

	public Order(User user,Set<OrderDetail> orderDetails) {
		super();
		this.orderNo = System.currentTimeMillis()+"";
//		this.aspID = user.getOrganization().getAspID();
		this.orgID = user.getOrganization().getId();
		this.paymentStatus = payStatus.unpaid.getStatus();
		this.paymentType = 1;
		this.userID = user.getId();
//		this.userName = user.getName();
		Date date = new Date();
		this.createTime = date;
		this.updateTime = date;
		Iterator<OrderDetail> iterator = orderDetails.iterator();
		while (iterator.hasNext()) {
			this.totalPrice += iterator.next().getDiscountPrice();
		}
		this.discountPrice = this.totalPrice;
		this.orderDetails = orderDetails;
	}

	public Integer getOrderID() {
		return orderID;
	}

	public void setOrderID(Integer orderID) {
		this.orderID = orderID;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

//	public int getAspID() {
//		return aspID;
//	}
//
//	public void setAspID(int aspID) {
//		this.aspID = aspID;
//	}

	public int getOrgID() {
		return orgID;
	}

	public void setOrgID(int orgID) {
		this.orgID = orgID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

//	public String getUserName() {
//		return userName;
//	}
//
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Set<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public short getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(short paymentType) {
		this.paymentType = paymentType;
	}

	public short getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(short paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public void setDiscountPrice(float discountPrice) {
		this.discountPrice = discountPrice;
	}

	public float getDiscountPrice() {
		return discountPrice;
	}
}
