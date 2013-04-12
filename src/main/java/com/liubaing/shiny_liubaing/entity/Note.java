package com.liubaing.shiny_liubaing.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.gson.annotations.Expose;

/**
 * 类说明：笔记实体
 * @author heshuai
 */
@Entity
@Table(name="u_notes_tab")
public class Note extends BaseModel{

	
	private static final long serialVersionUID = -2412030251481282006L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
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
	
	private int start; 					//标示开始的位置
	
	private int length; 				//选中文字的长度
	
	private int style;					//选中文字的样式
	
	@Expose
	private String text;				//笔记内容
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created")
	private Date createDate;			//持久化日期 

	
	/*
	 *  Constructor
	 */
	public Note() {
		super();
	}

	public Note(int courseid, int userid) {
		super();
		this.courseid = courseid;
		this.userid = userid;
	}

	/*
	 * getter setter
	 */
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

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
