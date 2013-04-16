package com.ulearning.ulms.appbackend.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 类说明: 班级实体
 * @author heshuai
 * @version 2013-4-2
 * 
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
@Entity
@Table(name = "c_class_tab")
public class Classes extends BaseModel{
	
	private static final long serialVersionUID = 160915317678386898L;
	
	@Id
	@Column(name = "CLASSID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToMany(mappedBy = "classes")
	private Set<User> users = new LinkedHashSet<User>();

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<User> getUsers() {
		return users;
	}
	
}
