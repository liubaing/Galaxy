package com.ulearning.ulms.appbackend.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 类说明:用户POJO
 * @author heshuai
 * @version Feb 24, 2012
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */

@Entity
@Table(name = "u_user_tab")
public class User extends BaseModel
{
	
	private static final long serialVersionUID = 1132116198278001891L;

	@Id
	@Column(name = "userid", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer  id;       			//id主键
	
	@Column(name = "loginname", nullable = false)
	private String username ;     		//用户名
	
	@Column(nullable = false)
	private String password ;      		//密码
	
	@Column(name = "name")
	private String name ;      			//姓名
	
	private String token;				//用户令牌
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "catalogid")
	private Organization organization;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, targetEntity = UserRole.class, mappedBy = "user")
	private Set<UserRole> userRoles;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "c_classuser_tab", joinColumns = @JoinColumn(name = "userID"), inverseJoinColumns = @JoinColumn(name = "classid"))
	private Set<Classes> classes = new LinkedHashSet<Classes>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, targetEntity = DeviceToken.class, mappedBy = "user")
	private Set<DeviceToken> deviceTokens;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	@NotBlank
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setClasses(Set<Classes> classes) {
		this.classes = classes;
	}

	public Set<Classes> getClasses() {
		return classes;
	}

	public void setDeviceTokens(Set<DeviceToken> deviceTokens) {
		this.deviceTokens = deviceTokens;
	}

	public Set<DeviceToken> getDeviceTokens() {
		return deviceTokens;
	}
	
	
	
}
