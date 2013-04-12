package com.liubaing.shiny_liubaing.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * 用户课程关联实体
 * @author heshuai
 * @version 
 */
@Entity
@Table(name = "c_user_tab")
public class UserCourse extends BaseModel{

	private static final long serialVersionUID = 2874853451361091832L;

	@EmbeddedId
	private UserCoursePK id;		//联合主键
	
	private String state;				//状态
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "applytime")
	private Date applyTime;				//开课时间
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expiretime")
	private Date expireTime;			//过期时间
	
	@Column(name = "paystate")	
	private String payState;			//支付状态

	@Column(name = "downloadcount")
	private int downloadCount;			//下载次数
	
	
	public UserCourse() {
		super();
	}

	public UserCourse(UserCoursePK id, String state, Date applyTime,
			Date expireTime, String payState, int downloadCount) {
		super();
		this.id = id;
		this.state = state;
		this.applyTime = applyTime;
		this.expireTime = expireTime;
		this.payState = payState;
		this.downloadCount = downloadCount;
	}

	public UserCoursePK getId() {
		return id;
	}

	public void setId(UserCoursePK id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}


	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}


	public String getPayState() {
		return payState;
	}

	public void setPayState(String payState) {
		this.payState = payState;
	}


	public int getDownloadCount() {
		return downloadCount;
	}


	public void setDownloadCount(int downloadCount) {
		this.downloadCount = downloadCount;
	}

	
	
}
