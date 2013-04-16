package com.ulearning.ulms.appbackend.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * 类说明:设备令牌 持久化
 * @author heshuai
 * @version 2012-3-12
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
@Entity
@Table(name = "u_devicetoken_tab")
public class DeviceToken extends BaseModel {

	private static final long serialVersionUID = -7207120063079557078L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;					//主键
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "userid")
	private User user;					//用户ID
	
	@Column(nullable = false)
	private String devicetoken;			//设备令牌
	
	private int status;					//是否允许推送通知 状态   0：不允许   1：允许
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created")
	private Date date;					//日期 

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDevicetoken() {
		return devicetoken;
	}

	public void setDevicetoken(String devicetoken) {
		this.devicetoken = devicetoken;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	
	/**
	  * 类的构造方法
	  * 创建一个新的实例 DeviceToken.
	  * @param 
	  * @param user
	  * @param devicetoken
	  * @param status
	  */
	public DeviceToken(User user, String devicetoken, int status) {
		super();
		this.user = user;
		this.devicetoken = devicetoken;
		this.status = status;
		this.date = new Date();
	}

	
	/**
	  * 类的构造方法
	  * 创建一个新的实例 DeviceToken.
	  * @param 
	  */
	public DeviceToken() {
		super();
	}
	
}
