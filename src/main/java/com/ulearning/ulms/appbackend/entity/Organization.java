package com.ulearning.ulms.appbackend.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 类说明:用户POJO
 * @author heshuai
 * @version Feb 24, 2012
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */

@Entity
@Table(name = "tm_org_tab")
public class Organization extends BaseModel
{
	
	private static final long serialVersionUID = 1132116198278001891L;

	@Id
	@Column(name = "orgid",nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer  id;       			//id主键
	
	@Column(name = "aspid")
	private int aspID;					//区域ID
	
	@OneToMany(mappedBy = "organization")
	private Set<User> users;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setAspID(int aspID) {
		this.aspID = aspID;
	}

	public int getAspID() {
		return aspID;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<User> getUsers() {
		return users;
	}

}
