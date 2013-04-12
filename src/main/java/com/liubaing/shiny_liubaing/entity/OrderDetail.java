package com.liubaing.shiny_liubaing.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 类说明:订单项实体
 * @author heshuai
 * @version 2012-10-9
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
@Entity
@Table(name = "c_orderdetail_tab")
public class OrderDetail extends BaseModel{

	private static final long serialVersionUID = -1439307921937763507L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderDetailID;
	
	@ManyToOne
	@JoinColumn(name = "orderID")	
	private Order order;
	
	private int relationID;		//关联事物ID  这里表示为课程ID
	
	@Column(name = "description")
	private String relationName;//关联事物名称  这里表示为课程名称

	@Column(name = "price")
	private float itemPrice;	//单价

	private float discountPrice;//折后价

	@Column(name = "quantity")
	private int itemCount;		//数量
	
	@Column(name = "createdate")
	private Date createTime;	//创建时间
	
	@Column(name = "updatedate")
	private Date updateTime;	//修改时间
	
//	@Column(name="remark1")
//	private String period;		//课时
//	
//	@Column(name="remark2")
//	private String type;		//商品类型 0 课程 1 班级

	public OrderDetail(){
		super();
	}
	
	public OrderDetail(Course course){
		super();
		this.relationID = course.getId();
		this.relationName = course.getTitle();
		this.itemPrice = course.getPrice();
		this.discountPrice = course.getPrice();
		this.itemCount = 1;
//		this.period = Float.toString(course.getPeriod());
//		this.type = "0";
		Date date = new Date();
		this.createTime = date;
		this.updateTime = date;
	}
	
	public Integer getOrderDetailID() {
		return orderDetailID;
	}

	public void setOrderDetailID(Integer orderDetailID) {
		this.orderDetailID = orderDetailID;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public int getRelationID() {
		return relationID;
	}

	public void setRelationID(int relationID) {
		this.relationID = relationID;
	}

	public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	public float getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(float itemPrice) {
		this.itemPrice = itemPrice;
	}

	public float getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(float discountPrice) {
		this.discountPrice = discountPrice;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
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

//	public String getPeriod() {
//		return period;
//	}
//
//	public void setPeriod(String period) {
//		this.period = period;
//	}
//
//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}
//	
}
