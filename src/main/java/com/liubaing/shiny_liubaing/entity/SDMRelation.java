package com.liubaing.shiny_liubaing.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Until;

/**
 * 类说明:学习记录POJO
 * @author heshuai
 * @version 2012-3-23
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
@Entity
@Table(name = "s_studyrecord_tab")
public class SDMRelation extends BaseModel{

	
	private static final long serialVersionUID = 6065080708064710700L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;			//主键
	
	@Expose
	@Until(value=1.1)
	private int courseid;		//课程ID
	
	@Expose
	@Until(value=1.1)
	private int userid;			//用户ID
	
	@Expose
	private int lessonid; 		//子课程单元
	
	@Expose
	private String lasttime;	//最后一次学习时间
	
	@Expose
	private String pages;		//学习过的页面列表
	
	@Expose
	private short lastpage;		//最后停留的页面
	
	@Expose
	@Column(name = "totaltime")
	private long totaltimes;	//累计学习时间
	
	@Expose
	private int score;   		//分数
	
	private short completion;	//完成状态   0:未完成   1：已完成

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCourseid() {
		return courseid;
	}

	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getLessonid() {
		return lessonid;
	}

	public void setLessonid(int lessonid) {
		this.lessonid = lessonid;
	}

	public String getLasttime() {
		return lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public short getLastpage() {
		return lastpage;
	}

	public void setLastpage(short lastpage) {
		this.lastpage = lastpage;
	}

	public long getTotaltimes() {
		return totaltimes;
	}

	public void setTotaltimes(long totaltimes) {
		this.totaltimes = totaltimes;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public short getCompletion() {
		return completion;
	}

	public void setCompletion(short completion) {
		this.completion = completion;
	}

		
	
}
