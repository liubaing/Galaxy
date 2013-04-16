package com.ulearning.ulms.appbackend.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

/**
 * 
 * 类说明:书签实体
 * @author heshuai
 * @version 2012-3-12
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
@Entity
@Table(name = "u_bookmarks_tab")
public class BookMark implements Serializable {
	
	
	private static final long serialVersionUID = -5129007572460534817L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;					//主键
	
	@Column(nullable = false)
	private int courseid;				//课程ID
	
	@Expose
	@Column(nullable = false)
	private int lessonid; 				//子课程ID

	@Column(nullable = false)
	private int userid;					//用户ID
	
	@Expose
	@Column(nullable = false)
	private int page;					//子课程中页码
	
	@Expose
	private String text;				//书签内容
	
	@Expose
	@Column(name = "created")
	private String date;				//日期 

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getCourseid() {
		return courseid;
	}

	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}

	public int getLessonid() {
		return lessonid;
	}

	public void setLessonid(int lessonid) {
		this.lessonid = lessonid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
