package com.ulearning.ulms.appbackend.entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;

/**
 * 类说明:课程POJO
 * @author heshuai
 * @version Feb 24, 2012
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
@Entity
@Table(name = "c_course_tab")
public class Course extends BaseModel
{
	
	private static final long serialVersionUID = -9000111242260503536L;
	
	public static final int CHARGE_STATUS_ALLOW = 1;

	@Id
	@Column(name = "courseid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	private Integer  id;       		//id主键
	
	@ManyToOne
	@JoinColumn(name="catalogid",referencedColumnName="catalogid")	//外键为目录ID
	private Catalog catalog;   		//目录
			
	@Column(name = "name")
	@Expose
	private String title ;     		//课程标题
	
	@Column(name = "coursecode")
	@Expose
	private String code ;      		//课程编码
	
	@Column(name = "charge")
	@Expose
	private Float price ;     		//价格
	
	@Expose
	private String description ;	//描述
	
	@Expose
	@Transient
	private String cover;			//For iPhone Cover
	
	@Expose
	@Column(name = "logopic")
	private String androidCover;	//For Android Cover
	
	@Expose
	@Column(name = "remark")
	private String link;			//下载地址
	
	@Expose(deserialize = false)
	@Transient
	private String limit;			//学习期限
	
	@Column(name = "aspid")
	private int aspID;				//区域ID
	
	private float period;			//课时
	
	private int isCharge;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Catalog getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getAndroidCover() {
		return androidCover;
	}

	public void setAndroidCover(String androidCover) {
		this.androidCover = androidCover;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public void setPeriod(float period) {
		this.period = period;
	}

	public float getPeriod() {
		return period;
	}

	public void setAspID(int aspID) {
		this.aspID = aspID;
	}

	public int getAspID() {
		return aspID;
	}

	public void setIsCharge(int isCharge) {
		this.isCharge = isCharge;
	}

	public int getIsCharge() {
		return isCharge;
	}
	
	public Course handle4Seri(){
		Pattern pattern = Pattern.compile("<.+?>", Pattern.DOTALL);
		Matcher matcher = pattern.matcher(this.getDescription());
		String temp = matcher.replaceAll("");
		this.setDescription(temp.replaceAll("\\&[a-zA-Z]{1,10};", ""));
		return this;
	}
	
}
