package com.liubaing.shiny_liubaing.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

/**
 * 类说明:课程目录
 * @author heshuai
 * @version Feb 24, 2012
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
@Entity
@Table(name = "c_catalog_tab")
public class Catalog extends BaseModel
{
	private static final long serialVersionUID = -5960016691065399059L;
	
	@Id
	@Column(name = "catalogid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	private Integer  id;       		//id主键

	@Column(name = "name")
	@Expose
	private String name ;     		//类别标题	
	
	private int type;				
	
	@OneToMany(mappedBy = "catalog")
	@Expose
	private List<Course> courses;  	//该目录下的课程

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public List<Course> getCourses() {
		return courses;
	}


}
